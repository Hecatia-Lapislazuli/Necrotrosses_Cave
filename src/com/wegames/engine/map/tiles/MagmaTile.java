/**
 * 
 */
package com.wegames.engine.map.tiles;

import com.wegames.engine.graphics.Sprite;

/**
 * @author kevinharty
 *
 */
public class MagmaTile extends Tile {

	/**
	 * @param tile
	 * @param x
	 * @param y
	 */
	public MagmaTile(Sprite tile, int x, int y) {
		super(tile, x, y);
		heat=150.0d;
	}

	/**
	 * @param x
	 * @param y
	 */
	public MagmaTile(int x, int y) {
		super(x, y);
		heat=150.0d;
		tile = Sprite.magma;
		pixels=tile.pixels;
		//System.out.println(x/32+" "+y/32);
	}

}
