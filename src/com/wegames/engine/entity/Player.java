/**
 * 
 */
package com.wegames.engine.entity;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.wegames.engine.Display;
import com.wegames.engine.graphics.Sprite;
import com.wegames.engine.input.Keyboard;
import com.wegames.engine.util.vector.Vector2D;

/**
 * @author Lord Lucumnox
 *
 */
public class Player extends Mob {
	
	Keyboard keyboard;
	
	/**
	 * 
	 */
	public Player(Display display, Sprite sprite, int x, int y) {
		super(display, sprite, x, y);
		this.keyboard=display.keyboarda;
		entities.remove(this);
		maxHealth = 100;
		maxMana = 20;
	}
	public void tick() {
		super.tick();
		List<Entity>aba=getEntities(this, 64);
		for (int i = 0; i < aba.size(); i++) {
			//System.out.println(aba.get(i));
		}
		if((keyboard.keys[KeyEvent.VK_W]||keyboard.keys[KeyEvent.VK_UP])&&move(new Vector2D(0, -1))) y--;
		if((keyboard.keys[KeyEvent.VK_S]||keyboard.keys[KeyEvent.VK_DOWN])&&move(new Vector2D(0, 1))) y++;
		if((keyboard.keys[KeyEvent.VK_A]||keyboard.keys[KeyEvent.VK_LEFT])&&move(new Vector2D(-1, 0))) x--;
		if((keyboard.keys[KeyEvent.VK_D]||keyboard.keys[KeyEvent.VK_RIGHT])&&move(new Vector2D(1, 0))) x++;
		//System.out.println(move(new Vector2D(xTile+1, yTile)));
	}
	boolean move(Vector2D a) {
		for (int i = 0; i < display.map.tileMap.length; i++) {
			if(collision(display.map.tileMap[i], a.x, a.y)) return false;
		}
		return true;
	}
	public Rectangle getBoundery(int xOff, int yOff) {
		return new java.awt.Rectangle(x+xOff+8, y+yOff+8, 16, 24);
	}
}
