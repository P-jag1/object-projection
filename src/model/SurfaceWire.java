package model;

import java.awt.Color;

import transforms.Bicubic;
import transforms.Cubic;
import transforms.Point3D;

/**
* trida SurfaceWire vytvari seznam elementu a vertexu, ktere jsou pouzity pro vykresleni bikubicke plochy
* 
* @author Petr
*
*/

public class SurfaceWire extends Solid{

	Bicubic bicubic = new Bicubic(Cubic.Type.BEZIER);
	private static final int K = 10;
	
	public SurfaceWire(Point3D[] points) {
		bicubic.init(points);
		
		for(double v = 0; v <= 1; v += 1./K) {
			for(double u = 0; u <= 1; u += 1./K){
				Point3D p = bicubic.compute(u, v);
				getVertices().add(new Vertex(p));
				int lastprvek = getVertices().size() - 1;
				//LineElement l = new LineElement(lastprvek, lastprvek + 1);
				//getElements().add(l);
				//l = new LineElement(lastprvek, lastprvek + K + 2);
				//getElements().add(l);
				TriangleElement te = new TriangleElement(lastprvek,lastprvek+1,lastprvek+K+2, new Color(255,120,0));
				getElements().add(te);
				te = new TriangleElement(lastprvek+1,lastprvek+K+2,lastprvek + K + 3,new Color(255,190,0));
				getElements().add(te);
			}
			
			Point3D p = bicubic.compute(1.0, v);
			getVertices().add(new Vertex(p));
		}
		
			for(double u = 0; u < 1; u += 1./K){
				Point3D p = bicubic.compute(u, 1.0);
				getVertices().add(new Vertex(p));
				int lastprvek = getVertices().size() - 1;
				LineElement l = new LineElement(lastprvek, lastprvek + 1);
				getElements().add(l);
			}
			
			Point3D p = bicubic.compute(1.0, 1.0);
			getVertices().add(new Vertex(p));
		}
		
}

