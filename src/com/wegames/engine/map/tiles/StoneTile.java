/**
 * 
 */
package com.wegames.engine.map.tiles;

import com.wegames.engine.graphics.Sprite;

/**
 * @author kevinharty
 *
 */
public class StoneTile extends Tile {
	public Sprite tile = Sprite.stone;
	public StoneTile(int x, int y) {
		super(x, y);
		tile=Sprite.stone;
		pixels=tile.pixels;
	}
}
