/**
 * 
 */
package com.wegames.engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import com.wegames.engine.Display;

/**
 * @author kevinharty
 *
 */
public class Mouse implements MouseInputListener, MouseWheelListener {
	
	public boolean[] button = new boolean[4];
	public int clickX=0, clickY=0;
	
	public int x=0, y=0;
	
	public Display display;
	
	public Mouse(Display display) {
		this.display=display;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		clickX=e.getX();
		clickY=e.getY();
		button[e.getButton()]=true;
		System.out.println(x+"|"+y+"|"+clickX+"|"+clickY);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		clickX=0;
		clickY=0;
		button[e.getButton()]=false;
		System.out.println(x+"|"+y+"|"+clickX+"|"+clickY);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		System.out.println(x+"|"+y+"|"+clickX+"|"+clickY);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		//System.out.println(x+"|"+y+"|"+clickX+"|"+clickY);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int i = e.getUnitsToScroll();
		display.ui.toolBarPos+=i;
		if(display.ui.toolBarPos>=display.ui.toolBarSize)display.ui.toolBarPos=0;
		if(display.ui.toolBarPos<0)display.ui.toolBarPos=display.ui.toolBarSize-1;
		//display.ui.toolBarPos+=5;
		System.out.println(display.ui.toolBarPos);
	}
}
