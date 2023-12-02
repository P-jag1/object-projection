package model;

import transforms.Point3D;

/**
 * trida ktera vytvari osy (zajistuje naplneni vertex a element bufferu)
 * 
 * @author Petr
 *
 */
public class Axis extends Solid {

	public Axis(int axisLength) {

		getVertices().add(new Vertex(new Point3D(0, 0, 0)));
		getVertices().add(new Vertex(new Point3D(axisLength, 0, 0)));
		getVertices().add(new Vertex(new Point3D(0, axisLength, 0)));
		getVertices().add(new Vertex(new Point3D(0, 0, axisLength)));

		getElements().add(new LineElement(0,1));
		getElements().add(new LineElement(0,2));
		getElements().add(new LineElement(0,3));
	}
}
