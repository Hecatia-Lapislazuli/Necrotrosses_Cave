package com.wegames.engine.map;

import com.wegames.engine.util.vector.Vector2D;

public class Node {
	
	public double fCost, gCost, hCost;
	public Vector2D location;
	
	public Node(Vector2D location, double gCost, double hCost) {
		this.location= location;
		setCost(gCost, hCost);
	}
	
	public void setCost(double gCost, double hCost) {
		this.gCost = gCost;
		this.hCost = hCost;
		fCost = gCost + hCost;
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof Node))
			return false;
		
		Node node = (Node) object;
		return location.equals(node.location);
	}

}
