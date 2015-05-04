/**
 * 
 */
package com.wegames.engine.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wegames.engine.Display;
import com.wegames.engine.graphics.Sprite;
import com.wegames.engine.map.tiles.AirTile;
import com.wegames.engine.map.tiles.MagmaTile;
import com.wegames.engine.map.tiles.Tile;

/**
 * @author Lord Lucumnox
 *
 */
public abstract class Entity {
	
	public static Random random = new Random();
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	Display display;
	
	Sprite sprite;
	
	public int[] pixels;
	
	public int x, y;
	public int xTile, yTile;
	
	/**
	 * 
	 */
	public Entity(Display display, Sprite sprite, int x, int y) {
		entities.add(this);
		this.display = display;
		this.sprite = sprite;
		this.x=x;
		this.y=y;
		pixels=sprite.pixels;
	}
	
	public void tick() {
		xTile=x/32;
		yTile=y/32;
	}
	
	public void render() {
		display.screen.renderEntity(x, y, pixels, 32, 32, sprite.scale, sprite.color);
	}
	public Rectangle getBoundery(int xOff, int yOff) {
		return new java.awt.Rectangle(x+xOff, y+yOff, 32, 32);
	}
	public boolean collision(Tile tile, int xOff, int yOff) {
		Rectangle tileRect = new Rectangle(tile.x, tile.y, 32, 32);
		if(tile.getClass()==AirTile.class) return false;
		if(tile.getClass()==MagmaTile.class) return false;
		return tileRect.intersects(getBoundery(xOff, yOff));
	}
	List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x=entity.x, y=entity.y;
			int dx=Math.abs(x-e.x);
			int dy=Math.abs(y-e.y);
			double distance=Math.sqrt((dx*dx)+(dy*dy));
			if(distance<=radius)result.add(entity);
		}
		return result;
	}
}
