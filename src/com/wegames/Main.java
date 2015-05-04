/**
 * 
 */
package com.wegames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.wegames.engine.Display;
import com.wegames.engine.entity.Necromancer;
import com.wegames.engine.entity.Player;
import com.wegames.engine.graphics.Sprite;
import com.wegames.engine.util.vector.Vector2D;
/**
 * @author Lord Lucumnox
 *
 */
public class Main extends Display {
	private static final long serialVersionUID = 1L;

	public Main(int width, int height) {
		super(width, height);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main(1080, 1080*9/16);
		main.setPlayer(new Player(main, Sprite.player, 0, 0));
		main.start();
		
		main.map.findPath(main.player, new Vector2D(4, 4));
		
		new Necromancer(main, Sprite.magma, 64, 64);
	}

}
