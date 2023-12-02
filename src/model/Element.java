package model;

import java.awt.Color;

/**
* abstraktni trida element, od ktere dedi LineElement a TriangleElement
* 
* @author Petr
*
*/

public abstract class Element {

	protected int[] indices;
	protected Color color;
	
	public int[] getIndices() {
		return indices;
	}
	
	public Color getColor() {
		return color;
	}
}
