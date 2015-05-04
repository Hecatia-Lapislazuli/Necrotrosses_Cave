/**
 * 
 */
package com.wegames.engine.graphics.ui;

import java.util.ArrayList;
import java.util.List;

import com.wegames.engine.graphics.Screen;

/**
 * @author kevinharty
 *
 */
public class Menu {
	
	List<Button> options = new ArrayList<Button>();
	
	/**
	 * 
	 */
	public Menu() {
	}
	
	void render(Screen screen) {
		
	}
	
	void addButton(Button button) {
		options.add(button);
	}
}
