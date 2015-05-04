package com.wegames.engine.map.tiles;

import com.wegames.engine.graphics.Sprite;

public class AirTile extends Tile {
	public Sprite tile = Sprite.player;
	public AirTile(Sprite tile, int x, int y) {
		super(tile, x, y);
		collide=false;
	}

	public AirTile(int x, int y) {
		super(x, y);
		tile = Sprite.floor;
		pixels=tile.pixels;
		collide=false;
	}

}
