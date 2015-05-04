/**
 * 
 */
package com.wegames.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Lord Lucumnox
 *
 */
public class Keyboard implements KeyListener {
	
	public boolean keys[] = new boolean[Character.MAX_VALUE];
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		String txt="";
		for (int i = 0; i < keys.length; i++) {
			if(keys[i]!=false) txt += KeyEvent.getKeyText(i)+" ";
			//System.out.println(txt);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		
	}

}
