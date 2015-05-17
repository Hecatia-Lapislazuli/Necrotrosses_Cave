/**
 * 
 */
package com.wegames.engine.entity;

import com.wegames.engine.Display;
import com.wegames.engine.graphics.Sprite;
import com.wegames.engine.util.vector.Vector2D;

/**
 * @author Lord Lucumnox
 *
 */
public class Mob extends Entity {
	
	public int level;
	public int xp;
	
	public int health;
	public int mana;
	
	public int maxHealth;
	public int maxMana;
	
	public int[] curPathMap;
	
	public int sightRange = 64;
	
	/**
	 * 
	 */
	public Mob(Display display, Sprite sprite, int x, int y) {
		super(display, sprite, x, y);
		curPathMap=display.map.pathMap;
	}
	
	public int sinceLastMove=0;
	
	public void tick() {
		if(xp>=100+20*level-1) {
			level++;
			xp-=100+20*level-1;
			
			maxHealth+=20;
			maxMana+=4;
		}
	}

	void move(int xTile, int yTile) {
		//if(xTile<this.xTile)
		sinceLastMove=0;
	}
	
	boolean move(Vector2D loc) {
		for (int i = 0; i < display.map.tileMap.length; i++) {
			if(collision(display.map.tileMap[i], loc.x, loc.y)) return false;
		}
		return true;
	}
	
	void wander() {
		if(random.nextInt(20)==0) {
			boolean moved=false;
			while(!moved) {
				
			}
		}
	}
	
	boolean hostileInRange() {
		return false;
	}
}
