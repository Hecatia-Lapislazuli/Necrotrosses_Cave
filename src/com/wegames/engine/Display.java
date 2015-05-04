/**
 * 
 */
package com.wegames.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.wegames.engine.entity.Entity;
import com.wegames.engine.entity.Player;
import com.wegames.engine.graphics.Screen;
import com.wegames.engine.graphics.Sprite;
import com.wegames.engine.graphics.ui.UI;
import com.wegames.engine.input.Keyboard;
import com.wegames.engine.input.Mouse;
import com.wegames.engine.map.MapGen;

/**
 * @author Lord Lucumnox
 *
 */
public abstract class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public int width, height;
	
	JFrame frame;
	BufferedImage image;
	int[] pixels;
	public Screen screen;
	public UI ui;
	
	Thread thread;
	boolean running = false;
	
	public Player player;
	
	//Input
	public Keyboard keyboarda;
	public Mouse mouse;
	
	/**
	 * 
	 */
	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		
		Dimension dim = new Dimension(width, height);
		frame = new JFrame();
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		screen = new Screen(width, height);
		ui = new UI(this, width, height);
		
		thread = new Thread(this, "Main Thread");
		
		//Input initialization.
		keyboarda = new Keyboard();
		mouse = new Mouse(this);
		
		addKeyListener(keyboarda);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
		frame.add(this);
		frame.pack();
		//frame.setResizable(false);
		frame.setSize(dim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	
		
	}
	
	protected synchronized void start() {
		running=true;
		thread.start();
	}
	
	protected synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running=false;
	}
	
	public void run() {
		System.out.println(Sprite.player.pixels);
		long lT = System.nanoTime();
		long timmer = System.currentTimeMillis();
		final double ns = 1000000000.0/60;
		double delta = 0.0;
		int frames = 0, ticks = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now-lT)/ns;
			lT=now;
			while(delta>=1) {
				tick();
				ticks++;
				delta--;
			}
			frames++;
			render();
			if(System.currentTimeMillis() - timmer > 1000) {
				timmer+=1000;
				//System.out.println("Frames per Second: " + frames + " Ticks per Second: " + ticks);
				//System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"/"+Runtime.getRuntime().maxMemory()/1024/1024);
				frames=0;
				ticks=0;
			}
		}
	}
	
	String txt="";
	
	private void tick() {
		ui.tick();
		screen.focusX=player.x-width/2;
		screen.focusY=player.y-height/2;
		for (int i = 0; i < Entity.entities.size(); i++) {
			Entity.entities.get(i).tick();
		}
		player.tick();
		//System.out.println("Tick!");
		txt="";
		for (int i = 0; i < keyboarda.keys.length; i++) {
			if(keyboarda.keys[i]!=false) txt += KeyEvent.getKeyText(i)+" ";
		}
		//System.out.println(frame.getKeyListeners()+"|"+keyboarda);
	}
	
	public MapGen map = new MapGen(this, 32, 32);
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
			screen.pixels[i]=0x000000;
		}
		
		map.render();
		
		for (int i = 0; i < Entity.entities.size(); i++) {
			Entity.entities.get(i).render();
		}
		
		player.render();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i]=screen.pixels[i];
		}
		
		BufferedImage iab = new BufferedImage(map.width, map.height, BufferedImage.TYPE_INT_RGB);
		int[] iabPixels = ((DataBufferInt)iab.getRaster().getDataBuffer()).getData();
		for (int i = 0; i < map.heightMap.length; i++) {
			if(map.heightMap[i]>=0x999999) {
				//System.out.println("I Here");
				iabPixels[i] = 0xffffff;
			}
			else iabPixels[i] = 0x00;
		}
		
		Graphics g = bs.getDrawGraphics();
		//g.drawImage(iab, 0, 0, iab.getWidth()*32, iab.getHeight()*32, null);
		g.drawImage(image, 0, 0, width, height, null);
		//g.drawImage(ui.UI, 0, 0, width, height, null);
		g.setColor(new Color(255, 255, 255));
		g.drawString(txt, 50, 100);
		g.drawString((ui.toolBarPos+1)+"/"+ui.toolBarSize, 50, 50);
		g.dispose();
		bs.show();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
























