/**
 * 
 */
package com.wegames.engine.util.vector;

/**
 * @author kevinharty
 *
 */
public class Vector2D {
	
	public int x, y;
	
	/**
	 * 
	 */
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof Vector2D))
			return false;
		
		Vector2D vector = (Vector2D) object;
		return x == vector.x && y == vector.y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "@"+x+"|"+y;
	}
}
