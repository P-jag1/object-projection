package model;

import transforms.Point3D;
import java.awt.Color;

/**
* trida ktera vytvari krychly (zajistuje naplneni vertex a element bufferu)
* 
* @author Petr
*
*/

public class Cube extends Solid {

	public Cube() {
		getVertices().add(new Vertex(new Point3D(-5, 5, -5)));
		getVertices().add(new Vertex(new Point3D(5, 5, -5)));
		getVertices().add(new Vertex(new Point3D(5, 5, 5)));
		getVertices().add(new Vertex(new Point3D(-5, 5, 5)));
		getVertices().add(new Vertex(new Point3D(-5, -5, -5)));
		getVertices().add(new Vertex(new Point3D(5, -5, -5)));
		getVertices().add(new Vertex(new Point3D(5, -5, 5)));
		getVertices().add(new Vertex(new Point3D(-5, -5, 5)));
		
		getElements().add(new TriangleElement(0,1,3, new Color(200, 100, 0)));
		getElements().add(new TriangleElement(1,2,3, new Color(200, 110, 0)));
		getElements().add(new TriangleElement(1,2,6, new Color(200, 150, 0)));
		getElements().add(new TriangleElement(1,5,6, new Color(200, 160, 0)));
		getElements().add(new TriangleElement(0,3,7, new Color(200, 230, 0)));
		getElements().add(new TriangleElement(3,6,7, new Color(200, 250, 0)));
		getElements().add(new TriangleElement(2,3,6, new Color(200, 25, 0)));
		getElements().add(new TriangleElement(5,6,7, new Color(200, 50, 0)));
		getElements().add(new TriangleElement(0,4,7, new Color(200, 75, 0)));
		getElements().add(new TriangleElement(0,1,5, new Color(200, 10, 0)));
		getElements().add(new TriangleElement(0,4,5, new Color(200, 175, 0)));
		getElements().add(new TriangleElement(4,5,7, new Color(200, 200, 0)));
	}
}
