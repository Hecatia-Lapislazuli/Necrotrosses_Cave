/**
 * 
 */
package com.wegames.engine.map.tiles;

import com.wegames.engine.graphics.Sprite;

/**
 * @author kevinharty
 *
 */
public class Tile {
	
	public Sprite tile;
	
	public int[] pixels;
	
	public int x, y;
	
	public boolean collide = true;
	
	public double heat=0.0d;
	
	/**
	 * 
	 */
	public Tile(Sprite tile, int x, int y) {
		this.tile = tile;
		this.x = x;
		this.y = y;
		pixels=tile.pixels;
		//tile = Sprite.player;
		//pixels=Sprite.player.pixels;
	}
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		if(tile==null) tile=Sprite.floor;
		pixels=tile.pixels;
		//tile = Sprite.player;
		//pixels=Sprite.player.pixels;
	}
}
