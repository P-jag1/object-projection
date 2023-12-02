package model;

import transforms.Point3D;

/**
* trida ktera vytvari Bikubickou plochu (vytvari list 3D bodu)
* 
* @author Petr
*
*/

public class Kubika {
	Point3D[] pointsPom = new Point3D[16];
	
	public Kubika() {
		makeList();
	}
	
	public Point3D[] makeList() {
		pointsPom[0] = new Point3D(-20,-20,10);
		pointsPom[1] = new Point3D(-10,-20,0);
		pointsPom[2] = new Point3D(10,-20,0);
		pointsPom[3] = new Point3D(20,-20,10);
		pointsPom[4] = new Point3D(-20,-10,0);
		pointsPom[5] = new Point3D(-10,-10,25);
		pointsPom[6] = new Point3D(10,-10,25);
		pointsPom[7] = new Point3D(20,-10,0);
		pointsPom[8] = new Point3D(-20,10,0);
		pointsPom[9] = new Point3D(-10,10,25);
		pointsPom[10] = new Point3D(10,10,25);
		pointsPom[11] = new Point3D(20,10,0);
		pointsPom[12] = new Point3D(-20,20,10);
		pointsPom[13] = new Point3D(-10,20,0);
		pointsPom[14] = new Point3D(10,20,0);
		pointsPom[15] = new Point3D(20,20,10);
		return pointsPom;
	}
}
