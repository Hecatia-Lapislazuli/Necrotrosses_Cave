/**
 * 
 */
package com.wegames.engine.graphics;

import com.wegames.engine.map.tiles.Tile;

/**
 * @author kevinharty
 *
 */
public class Screen {
	
	public int width, height;
	public int[] pixels;
	
	/**
	 * 
	 */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
	}
	
	public void renderEntity(int xOffset, int yOffset, int[] pixels, int eWidth, int eHeight, int[] scale, int[] color) {
		int xOffsetA=xOffset;
		int yOffsetA=yOffset;
		if(Sprite.player.pixels!=pixels) {
			xOffsetA=xOffset-focusX;
			yOffsetA=yOffset-focusY;
		} else {
			xOffsetA=xOffset-focusX;//+width/2;
			yOffsetA=yOffset-focusY;//+height/2;
		}
		for (int y = 0; y < eHeight; y++) {
			if(!(y+yOffsetA>height||y+yOffsetA<0)) {
				for (int x = 0; x < eWidth; x++) {
					if(!(x+xOffsetA>width||x+xOffsetA<0)) {
						if(pixels[x+y*eWidth]!=scale[15]) this.pixels[(x+xOffsetA)+(y+yOffsetA)*width]=pixels[x+y*eWidth];
					}
				}
			}
		}
	}
	public int focusX = 0, focusY=0;
	public void renderTile(int xOffset, int yOffset, Tile tile, int tWidth, int tHeight) {
		int xOffsetA=xOffset-focusX;
		int yOffsetA=yOffset-focusY;
		for (int y = 0; y < tHeight; y++) {
			if(y+yOffsetA>height||y+yOffsetA<0) continue;
			for (int x = 0; x < tWidth; x++) {
				if(x+xOffsetA>width||x+xOffsetA<0) continue;
				//System.out.println(tile.pixels!=null);
				//if(tile.pixels[x+y*width]!=0xff00ff) 
					if((x+xOffsetA)+(y+yOffsetA)*width<pixels.length)this.pixels[(x+xOffsetA)+(y+yOffsetA)*width]=
					tile.pixels[x+y*tWidth];
			}
		}
	}
}
