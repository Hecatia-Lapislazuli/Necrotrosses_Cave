/**
 * 
 */
package com.wegames.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author Lord Lucumnox
 *
 */
public class Sprite {
	
	public static Sprite player = new Sprite("res/player.png");
	public static Sprite floor = new Sprite("res/floor.png");
	public static Sprite stone = new Sprite("res/stone.png");
	public static Sprite magma = new Sprite("res/magma.png");
	
	public int[] pixels=new int[32*32];
	
	String path;
	
	public BufferedImage sprite = null;
	
	public int[] scale = new int[16];
	public int[] color = new int[16];
	
	/**
	 * 
	 */
	public Sprite(String path) {
		this.path = path;
		File file = new File(path);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			sprite = ImageIO.read(in);
		} catch (IOException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		if(sprite==null) System.exit(1);
		if(sprite.getWidth()!=32||sprite.getHeight()!=33) {
			System.err.println("Incorect dimensions in image file: "+path+" Dimensions are: "+sprite.getWidth()+" by "+sprite.getHeight()+" Not loading sprite.");
			return;
		}
		scale=sprite.getRGB(0, 0, 16, 1, null, 0, 16);
		color=sprite.getRGB(0, 0, 16, 1, null, 16, 16);
		for (int i = 0; i < color.length; i++) {
			//pixels[i] = color[i];
			//System.out.println(pixels[i] + " " + color[i]);
		}
		//System.out.println(pixels.length);
		pixels=sprite.getRGB(0, 1, 32, 32, null, 0, 32);
		//System.out.println(pixels.length);
		for (int i = 0; i < pixels.length; i++) {
			//System.out.println(pixels[i]);
		}
		
	}
	void render() {
		
	}
}









