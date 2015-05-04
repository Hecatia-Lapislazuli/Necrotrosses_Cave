/**
 * 
 */
package com.wegames.engine.graphics.ui;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.wegames.engine.Display;
import com.wegames.engine.map.tiles.AirTile;
import com.wegames.engine.map.tiles.MagmaTile;
import com.wegames.engine.map.tiles.Tile;
import com.wegames.engine.util.vector.Vector2D;

/**
 * @author Lord Lucumnox
 *
 */
public class UI {
	
	public int width, height;
	
	public int toolBarPos=0;
	public int toolBarSize=10;
	
	public BufferedImage UI;
	private int[] pixels;
	public Display display;
	
	/**
	 * 
	 */
	public UI(Display display, int width, int height) {
		this.display=display;
		this.width=width;
		this.height=height;
		UI = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)UI.getRaster().getDataBuffer()).getData();
	}
	
	public void tick() {
		if (display.mouse.button[1]) {
			Tile tile = display.map.getTileAtLocation(new Vector2D(display.mouse.clickX, display.mouse.clickY));
			if (tile != null && tile.getClass() ==MagmaTile.class) {
				display.map.tileMap[(display.mouse.clickX+display.screen.focusX)/32+((display.mouse.clickY+display.screen.focusY)/32)*+display.map.width]=
						new AirTile(display.map.tileMap[(display.mouse.clickX+display.screen.focusX)/32+((display.mouse.clickY+display.screen.focusY)/32)*+display.map.width].x,display.map.tileMap[(display.mouse.clickX+display.screen.focusX)/32+((display.mouse.clickY+display.screen.focusY)/32)*+display.map.width].y);
			}
			System.out.println(display.map.getTileAtLocation(new Vector2D(display.mouse.clickX, display.mouse.clickY)));
		}
		if (display.mouse.button[MouseEvent.BUTTON3]) {
			toggleSollectionMenu();
		}
	}

	private void toggleSollectionMenu() {
		
	}
}
