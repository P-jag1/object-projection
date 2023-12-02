package model;

import java.awt.Color;

import transforms.Point3D;

/**
* trida ktera vytvari Tetrahedron (zajistuje naplneni vertex a element bufferu)
* 
* @author Petr
*
*/

public class Tetrahedron extends Solid{

	public Tetrahedron() {
		getVertices().add(new Vertex(new Point3D(0, 0, 0)));
		getVertices().add(new Vertex(new Point3D(10, 10, 0)));
		getVertices().add(new Vertex(new Point3D(10, 0, 10)));
		getVertices().add(new Vertex(new Point3D(0, 10, 10)));	
		
		getElements().add(new TriangleElement(0,1,2, new Color(0,50,200)));
		getElements().add(new TriangleElement(0,1,3, new Color(0,200,50)));
		getElements().add(new TriangleElement(1,2,3, new Color(0,150,100)));
		getElements().add(new TriangleElement(0,2,3, new Color(0,100,150)));
	}
}
