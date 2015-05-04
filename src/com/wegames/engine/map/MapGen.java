/**
 * 
 */
package com.wegames.engine.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.TileObserver;
import java.awt.image.WritableRenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.wegames.engine.Display;
import com.wegames.engine.entity.Entity;
import com.wegames.engine.map.tiles.AirTile;
import com.wegames.engine.map.tiles.MagmaTile;
import com.wegames.engine.map.tiles.StoneTile;
import com.wegames.engine.map.tiles.Tile;
import com.wegames.engine.util.vector.Vector2D;

/**
 * @author kevinharty
 *
 */
public class MapGen {
	
	Display display;
	Random random = new Random();
	
	public int width, height;
	
	public int[] heightMap;
	
	public BufferedImage heightMapImg;
	int[] hMIpixels;
	
	public Tile[] tileMap;
	
	public int[] pathMap;
	public int[] itemMap;
	
	public double[] heatMap;
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	/**
	 * 
	 */
	public MapGen(Display display, int height, int width) {
		this.display = display;
		this.width = width;
		this.height = height;
		heightMapImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		hMIpixels = ((DataBufferInt)heightMapImg.getRaster().getDataBuffer()).getData();
		heightMap = new int[width*height];
		pathMap = new int[width*height];
		itemMap = new int[width*height];
		
		long aaa=System.nanoTime();
		
		boolean pathStatus=false;
		boolean tilePathStatus=false;
		int tries=0;
		//while(!tilePathStatus) {
		while(!pathStatus) {
		tries++;
		int[] i = genHightMap(genPointMap());
		heightMap=hMIpixels;
		for (int j = 0; j < i.length; j++) {
			hMIpixels[j] = ((i[j])+(i[j]<<8)+(i[j]<<16));
			//System.out.println(Integer.toHexString(i[j])+" | "+Integer.toHexString(i[j]<<8)+" | "+Integer.toHexString(i[j]<<16)+" | "+Integer.toHexString(((i[j])+(i[j]<<8)+(i[j]<<16))));
			//System.out.println(heightMap[j]);
			if(heightMap[j]>=0x999999) {
				pathMap[j] = 1;
			} else pathMap[j] = 0;
		}
		pathStatus=checkPath();
		System.out.println(pathMap.length);
		System.out.println("Path Status: " + pathStatus + " | Tries: " + tries);
		}
		for (int i = 0; i < itemMap.length; i++) {
			itemMap[i] = 0;
			if(pathMap[i]==1) itemMap[i] = -1;
		}
		//while() {
		tileMap=createTileMap();
		tileMap=genLake(tileMap, MagmaTile.class, 0x666666);
		tileMap=genLake(tileMap, MagmaTile.class, 0x505050);
		//}
		//tilePathStatus=checkPath();
		//System.out.println("FINI "+tilePathStatus);
		if(!tilePathStatus) {
			//tileMap=null;
			//pathStatus=false;
			//tries=0;
		}
		//}
		
		BufferedImage imageHeight = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] pixelsHeight = ((DataBufferInt)imageHeight.getRaster().getDataBuffer()).getData();
		int[] asdw = genHightMap(genPointMap());
		for (int i = 0; i < pixelsHeight.length; i++) {
			pixelsHeight[i]=asdw[i];
		}
		
		BufferedImage imageHeightA = new BufferedImage(width*2, height*2, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = imageHeightA.getGraphics();
		g.drawImage(imageHeight, 0, 0, null);
		g.drawImage(imageHeight, 0, imageHeight.getHeight(), null);
		g.drawImage(imageHeight, imageHeight.getWidth(), 0, null);
		g.drawImage(imageHeight, imageHeight.getWidth(), imageHeight.getHeight(), null);
		g.dispose();
		
		try {
			ImageIO.write(imageHeightA, "png", new File("test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		createUtilMaps();
		
		System.out.println(tileMap[0].pixels[0]);
		System.out.println("Done, in "+((System.nanoTime()-aaa)/1000000000.0)+" seconds!");
		
	}
	
	void createUtilMaps() {
		heatMap = new double[width*height];
		for (int i = 0; i < heatMap.length; i++) {
			heatMap[i]=0.0d;
		}
		for (int i = 0; i < heatMap.length; i++) {
			heatMap[i] = tileMap[i].heat;
		}
	}
	Tile[] createTileMap() {
		Tile[] aa = new Tile[pathMap.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(pathMap[x+y*width]==1)aa[x+y*width]=new StoneTile(x*32, y*32);
				if(heightMap[x+y*width]<0x333333)aa[x+y*width]=new MagmaTile(x*32, y*32);
				if(aa[x+y*width]==null) aa[x+y*width]=new AirTile(x*32, y*32);
				//System.out.println("Red Like Roses.");
			}
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(aa[x+y*width]==null) System.err.println("Null");
			}
		}
		return aa;
	}
	@SuppressWarnings("rawtypes")
	Tile[] genLake(Tile[] aa, Class tile, int height) {
		Tile[] ab = aa;
		ArrayList<Tile> ac = new ArrayList<Tile>();
		for (int i = 0; i < ab.length; i++) {
			if(ab[i].getClass()==tile) {
				ac.add(ab[i]);
			}
		}
		ArrayList<Tile> ba = new ArrayList<Tile>();
		while(ac.size()!=0) {
			//System.out.println(ac.size()+" Math...");
			ba.addAll(checkNearbyTile(new Vector2D(ac.get(0).x, ac.get(0).y), height));
			ac.remove(0);
			if (ac.size()==0) {
				System.out.println(ba.size()+"Hiya");
				for (int i = 0; i < ba.size(); i++) {
					for (int j = 0; j < tileMap.length; j++) {
						if((int) (tileMap[j].x/32)==(int) (ba.get(i).x/32)&&tileMap[j].y/32==ba.get(i).y/32) {
							//System.out.println("Gene!");
							ab[j]=new MagmaTile(ba.get(i).x, ba.get(i).y);
						}
					}
				}
			}
		}
		return ab;
	}
	int[] genPointMap() {
		int[] points = new int[(width+1)*(height+1)];
		for (int i = 0; i < points.length; i++) {
			points[i]=(int) (random.nextFloat()*256);
		}
		return points;
	}
	int[] genHightMap(int[] pointMap) {
		int[] heightMap = new int[(width)*(height)];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				heightMap[x+y*width]=((pointMap[x+y*width])+(pointMap[(x+1)+y*width])+(pointMap[x+(y+1)*width])+(pointMap[(x+1)+(y+1)*width]))/4;
				//System.out.print(heightMap[x+y*width] + " ");
			}
			//System.out.println();
		}
		return heightMap;
	}
	int[] genHightMap(int[] pointMap, boolean wrap) {
		if(!wrap) return genHightMap(pointMap);
		int[] heightMap = new int[(width)*(height)];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				heightMap[x+y*width]=((pointMap[x+y*width])+(pointMap[(x+1)+y*width])+(pointMap[x+(y+1)*width])+(pointMap[(x+1)+(y+1)*width]))/4;
				//System.out.print(heightMap[x+y*width] + " ");
			}
			//System.out.println();
		}
		return heightMap;
	}
	
	public List<Node> findPath(Entity e, Vector2D destination) {
		List<Node> result = new ArrayList<Node>();
		List<Node> closed = new ArrayList<Node>();
		List<Node> open = new ArrayList<Node>();
		Vector2D start = new Vector2D(e.x, e.y);
		Node current = new Node(start, 0, getDistance(start, destination));
		Node goal = new Node(destination, 0, 0);
		open.add(current);
		while (!open.isEmpty()) {
			Collections.sort(open, nodeSorter);
			current = open.get(0);
			if (current.equals(goal)) {
				// Success! Path found
				System.out.println("We founds thee pathses!");
			}
			open.remove(0);
			closed.add(current);
			for (int y = -1; y <= 1; y++) {
				for (int x = -1; x <= 1; x++) {
					if (x == 0 && y == 0) continue;
					
					int x0 = current.location.x + x;
					int y0 = current.location.y + y;
					
					Node a = new Node(new Vector2D(x0, y0), 1, 0);
					Tile tile = getTile(a.location);
					if (tile == null) continue;
					if (tile.collide) continue;
					if (closed.contains(a)) continue;

					double gCost = current.gCost + 1; // Right now diagonal == horizontal/vertical
					double hCost = getDistance(a.location, destination);
					a.setCost(gCost, hCost);
					if (closed.contains(a)) continue;
					if (!open.contains(a)) open.add(a);
				}				
			}
			System.out.println("Current X: " + current.location.x + " | Current Y: " + current.location.y);
			try {
			//Thread.sleep(1000);
			} catch (Exception e2) {}
		}
		return null;
	}
	
	public double getDistance(Vector2D start, Vector2D finish) {
		double dx = start.x - finish.x;
		double dy = start.y - finish.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	boolean checkPath() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				//System.out.print(pathMap[x+y*width]);
			}
			//System.out.println();
		}
		if(pathMap[0]!=0) return false;
		ArrayList<Vector2D> pathTiles = new ArrayList<Vector2D>();
		pathTiles.add(new Vector2D(0, 0));
		while(true) {
			if(pathMap[pathMap.length-1]==2||pathTiles.size()==0) {
				break;
			}
			//System.out.println(pathTiles.size());// + " | " + pathTiles.get(0).x);
			pathTiles.addAll(checkNearbyTile(pathTiles.get(0)));
			//boolean k=false;
			//System.out.println(pathTiles.size()+"For Loop");
			pathTiles.remove(0);
			int i = pathTiles.size();
			while(i!=0) {
				//System.out.println(i+" HI | "+pathTiles.size());
				if(pathTiles.get(i-1).toString()==pathTiles.get(i-1).toString()) {
					pathTiles.remove(i-1);
				}
				i--;
			}
		}
		System.out.println();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				//System.out.print(pathMap[x+y*width]);
			}
			//System.out.println();
		}
		return pathMap[pathMap.length-1]==2;
	}
	
	/*public ArrayList<Vector2D> checkNearbyTile(int x, int y) {
		/*Vector2D[] vector = new Vector2D[8];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if((x-1)+(y-1)*width>0&&pathMap[(x-1)+(y-1)*width]==0) vector[0] = new Vector2D(x, y);
				if((x)+(y-1)*width>0&&pathMap[(x)+(y-1)*width]==0) vector[1] = new Vector2D(x, y);
				if((x+1)+(y-1)*width>0&&pathMap[(x+1)+(y-1)*width]==0) vector[2] = new Vector2D(x, y);
				if((x-1)+(y)*width>0&&pathMap[(x-1)+(y)*width]==0) vector[3] = new Vector2D(x, y);
				if((x+1)+(y)*width<width*height&&pathMap[(x+1)+(y)*width]==0) vector[5] = new Vector2D(x, y);
				if((x-1)+(y+1)*width<width*height&&pathMap[(x-1)+(y+1)*width]==0) vector[6] = new Vector2D(x, y);
				if((x)+(y+1)*width<width*height&&pathMap[(x)+(y+1)*width]==0) vector[7] = new Vector2D(x, y);
				if((x+1)+(y+1)*width<width*height&&pathMap[(x+1)+(y+1)*width]==0) vector[8] = new Vector2D(x, y);
			}
		}
		pathMap[xa+ya*width]=2;
		ArrayList<Vector2D> vectora = new ArrayList<Vector2D>();
		for (int i = 0; i < vector.length; i++) {
			vectora.add(vector[i]);
		}
		return vectora;
		
		ArrayList<Vector2D> vectora = new ArrayList<Vector2D>();
		Vector2D[] vector = new Vector2D[8];
		//for (int y = 0; y < height; y++) {
			//for (int x = 0; x < width; x++) {
		if((x-1)+(y-1)*width>0&&pathMap[(x-1)+(y-1)*width]==0) {
			vector[0] = new Vector2D(x-1, y-1);
			System.out.println("1");
		}
		if((x)+(y-1)*width>0&&pathMap[(x)+(y-1)*width]==0) {
			vector[1] = new Vector2D(x, y-1);
			System.out.println("2");
		}
		if((x+1)+(y-1)*width>0&&pathMap[(x+1)+(y-1)*width]==0) {
			vector[2] = new Vector2D(x+1, y-1);
			System.out.println("3");
		}
		if((x-1)+(y)*width>0&&pathMap[(x-1)+(y)*width]==0) {
			vector[3] = new Vector2D(x-1, y);
			System.out.println("4");
		}
		if((x+1)+(y)*width<width*height&&pathMap[(x+1)+(y)*width]==0) {
			vector[5] = new Vector2D(x+1, y);
			System.out.println("5");
		}
		if((x-1)+(y+1)*width<width*height&&pathMap[(x-1)+(y+1)*width]==0) {
			vector[6] = new Vector2D(x-1, y+1);
			System.out.println("6");
		}
		if((x)+(y+1)*width<width*height&&pathMap[(x)+(y+1)*width]==0) {
			vector[7] = new Vector2D(x, y+1);
			System.out.println("7");
		}
		if((x+1)+(y+1)*width<width*height&&pathMap[(x+1)+(y+1)*width]==0) {
			vector[8] = new Vector2D(x+1, y+1);
			System.out.println("8");
		}
			//}
		//}
		pathMap[x+y*width]=2;
		
		for (int i = 0; i < vector.length; i++) {
			vectora.add(vector[i]);
		}
		return vectora;
	}*/
	
	public ArrayList<Vector2D> checkNearbyTile(Vector2D pos) {
		int x=pos.x, y=pos.y;
		ArrayList<Vector2D> vector = new ArrayList<Vector2D>();
		if(pathMap[x+y*width]!=0) return vector;
		if(tileMap==null) { 
		//for (int y = 0; y < height; y++) {
			//for (int x = 0; x < width; x++) {
				//System.out.println(x + " | " + y);
				if((x-1)+(y-1)*width>0&&pathMap[(x-1)+(y-1)*width]==0&&!(x-1>width)&&!(x-1<0)) {
					//vector.add(new Vector2D(x-1, y-1));
					//System.out.println("1");
				}
				if((x)+(y-1)*width>0&&pathMap[(x)+(y-1)*width]==0&&!(x>width)&&!(x<0)) {
					vector.add(new Vector2D(x, y-1));
					//System.out.println("2");
				}
				if((x+1)+(y-1)*width>0&&pathMap[(x+1)+(y-1)*width]==0&&!(x+1>width)&&!(x+1<0)) {
					//vector.add(new Vector2D(x+1, y-1));
					//System.out.println("3");
				}
				if((x-1)+(y)*width>0&&pathMap[(x-1)+(y)*width]==0&&!(x-1>width)&&!(x-1<0)) {
					vector.add(new Vector2D(x-1, y));
					//System.out.println("4");
				}
				if((x+1)+(y)*width<width*height&&pathMap[(x+1)+(y)*width]==0&&!(x+1>width)&&!(x+1<0)) {
					vector.add(new Vector2D(x+1, y));
					//System.out.println("5");
				}
				if((x-1)+(y+1)*width<width*height&&pathMap[(x-1)+(y+1)*width]==0&&!(x-1>width)&&!(x-1<0)) {
					//vector.add(new Vector2D(x-1, y+1));
					//System.out.println("6");
				}
				if((x)+(y+1)*width<width*height&&pathMap[(x)+(y+1)*width]==0&&!(x>width)&&!(x<0)) {
					vector.add(new Vector2D(x, y+1));
					//System.out.println("7");
				}
				if((x+1)+(y+1)*width<width*height&&pathMap[(x+1)+(y+1)*width]==0&&!(x+1>width)&&!(x+1<0)) {
					//vector.add(new Vector2D(x+1, y+1));
					//System.out.println("8");
				}
			//}
		//}
		//System.out.println(pathMap[(x)+(y)*width]+"Me 1!");
		pathMap[x+y*width]=2;
		//System.out.println((pathMap[(x)+(y)*width]==0)+"Me 2!");
		} else {
			if((x-1)+(y-1)*width>0&&!tileMap[(x-1)+(y-1)*width].collide&&!(x-1>width)&&!(x-1<0)) {
				//vector.add(new Vector2D(x-1, y-1));
				//System.out.println("1");
			}
			if((x)+(y-1)*width>0&&!tileMap[(x)+(y-1)*width].collide&&!(x>width)&&!(x<0)) {
				vector.add(new Vector2D(x, y-1));
				System.out.println("2");
			}
			if((x+1)+(y-1)*width>0&&!tileMap[(x+1)+(y-1)*width].collide&&!(x+1>width)&&!(x+1<0)) {
				//vector.add(new Vector2D(x+1, y-1));
				//System.out.println("3");
			}
			if((x-1)+(y)*width>0&&!tileMap[(x-1)+(y)*width].collide&&!(x-1>width)&&!(x-1<0)) {
				vector.add(new Vector2D(x-1, y));
				System.out.println("4");
			}
			if((x+1)+(y)*width<width*height&&!tileMap[(x+1)+(y)*width].collide&&!(x+1>width)&&!(x+1<0)) {
				vector.add(new Vector2D(x+1, y));
				//System.out.println("5");
			}
			if((x-1)+(y+1)*width<width*height&&!tileMap[(x-1)+(y+1)*width].collide&&!(x-1>width)&&!(x-1<0)) {
				//vector.add(new Vector2D(x-1, y+1));
				//System.out.println("6");
			}
			if((x)+(y+1)*width<width*height&&!tileMap[(x)+(y+1)*width].collide&&!(x>width)&&!(x<0)) {
				vector.add(new Vector2D(x, y+1));
				//System.out.println("7");
			}
			if((x+1)+(y+1)*width<width*height&&!tileMap[(x+1)+(y+1)*width].collide&&!(x+1>width)&&!(x+1<0)) {
				//vector.add(new Vector2D(x+1, y+1));
				//System.out.println("8");
			}
		}
		return vector;
	}
	
	public ArrayList<Tile> checkNearbyTile(Vector2D pos, int heightA) {
		int x=pos.x/32, y=pos.y/32;
		ArrayList<Tile> vector = new ArrayList<Tile>();
		//System.out.println(x+" "+y);
		//System.out.println(((x-1)+(y-1)*width>=0)+" "+((x-1)+(y-1)*width<tileMap.length));
		//System.out.println((x-1)+(y-1)*width<width*height);
		if((x-1)+(y-1)*width>=0&&(x-1)+(y-1)*width<width*height&&heightMap[(x-1)+(y-1)*width]<heightA) {
			vector.add(tileMap[(x-1)+(y-1)*width]);
			//System.out.println("1A");
		}
		if((x)+(y-1)*width>0&&!((x)+(y-1)*width>=width*height)&&heightMap[(x)+(y-1)*width]<heightA) {
			vector.add(tileMap[x+(y-1)*width]);
			//System.out.println("2A");
		}
		if((x+1)+(y-1)*width>0&&!((x+1)+(y-1)*width>=width*height)&&heightMap[(x+1)+(y-1)*width]<heightA) {
			vector.add(tileMap[x+1+(y-1)*width]);
			//System.out.println("3A");
		}
		if((x-1)+(y)*width>0&&!((x-1)+(y)*width>=width*height)&&heightMap[(x-1)+(y)*width]<heightA) {
			vector.add(tileMap[x-1+y*width]);
			//System.out.println("4A");
		}
		if((x+1)+(y)*width>0&&!((x+1)+(y)*width>=width*height)&&heightMap[(x+1)+(y)*width]<heightA) {
			vector.add(tileMap[x+1+y*width]);
			//System.out.println("5A");
		}
		if((x-1)+(y+1)*width>0&&!((x-1)+(y+1)*width>=width*height)&&heightMap[(x-1)+(y+1)*width]<heightA) {
			vector.add(tileMap[x-1+(y+1)*width]);
			//System.out.println("6A");
		}
		if((x)+(y+1)*width>0&&!((x)+(y+1)*width>=width*height)&&heightMap[(x)+(y+1)*width]<heightA) {
			vector.add(tileMap[x+(y+1)*width]);
			//System.out.println("7A");
		}
		if((x+1)+(y+1)*width>0&&!((x+1)+(y+1)*width>=width*height)&&heightMap[(x+1)+(y+1)*width]<heightA) {
			vector.add(tileMap[x+1+(y+1)*width]);
			//System.out.println("8A");
		}
		//System.out.println(pathMap[(x)+(y)*width]+"Me 1!");
		//System.out.println((pathMap[(x)+(y)*width]==0)+"Me 2!");
		return vector;
	}
	
	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				display.screen.renderTile(tileMap[x+y*width].x, tileMap[x+y*width].y, tileMap[x+y*width], 32, 32);
			}
		}
	}
	
	public Tile getTile(Vector2D loc) {
		if (loc.x < 0 || loc.x >= 32 || loc.y < 0 || loc.y >= 32)
			return null;
		return tileMap[loc.x + loc.y * width];
	}
	
	public Tile getTileAtLocation(Vector2D loc) {
		int x = (loc.x+display.screen.focusX)/32;
		int y = (loc.y+display.screen.focusY)/32;
		if (x < 0 || x >= 32 || y < 0 || y >= 32)
			return null;

		Tile tile=tileMap[x + y * (width)];
		return tile;
	}
}














