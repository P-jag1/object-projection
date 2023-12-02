package model;

import java.awt.Color;

/**
* trida LineElement trojuhelnik usecku mezi tremi body, jejich indexy jsou ulozeny v poly indices
* 
* @author Petr
*
*/

public class TriangleElement extends Element {
	
	public TriangleElement() {
		this.indices = new int[3];
	}
	
	public TriangleElement(int a, int b, int c, Color color) {
		this();
		this.indices[0] = a;
		this.indices[1] = b;
		this.indices[2] = c;
		//barva, kterou bude trojuhelnik vyplnenen
		this.color = color;
	}
	
	public int getFirstIndex() {
		return indices[0];
	}
}
