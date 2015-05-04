/**
 * 
 */
package com.wegames.engine.entity;

import com.wegames.engine.Display;
import com.wegames.engine.graphics.Sprite;
import com.wegames.engine.util.vector.Vector2D;

/**
 * @author kevinharty
 *
 */
public class Necromancer extends Mob {
	
	/**
	 * @param display
	 * @param sprite
	 * @param x
	 * @param y
	 */
	public Necromancer(Display display, Sprite sprite, int x, int y) {
		super(display, sprite, x, y);
	}
	
	@Override
	void move(int xTile, int yTile) {
		super.move(xTile, yTile);
		if(x<xTile&&move(new Vector2D( 1, 0))) x++;
		if(x>xTile&&move(new Vector2D(-1, 0))) x--;
		if(y<yTile&&move(new Vector2D( 0, 1))) y++;
		if(y>yTile&&move(new Vector2D( 0,-1))) y--;
	}
	@Override
	public void tick() {
		super.tick();
		move(display.player.x, display.player.y);
	}
}
