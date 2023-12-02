package model;

import java.awt.Color;

import transforms.Mat4;
import transforms.Point3D;

/**
* trida reprezentujici jeden bod objektu
* 
* @author Petr
*
*/

public class Vertex {
	private Point3D position;
	private double one;
	
	public Vertex(Point3D position) {
		this.position = new Point3D(position);
		this.one = 1;
	}
	
	public Vertex(Point3D position, Color color) {
		this.position = new Point3D(position);
		this.one = 1;
	}
	
	public Vertex() {
		one = 1;
	}
	
	//metoda pro dehomogenizaci bodu
	public Vertex dehomog() {
		Vertex v = new Vertex();
		double w = position.w; 
		v.position = position.mul(1/w);
		v.one = one/w;
		return v;
	}

	//metoda, ktera nam vrati pouze Point3D z vertexu
	public Point3D getPosition() {
		return position;
	}
	
	public void setPosition(Point3D position) {
		this.position = position;
	}
	
	public double getW() {
		return position.getW();
	}
	
	//metoda, ktera transformuje vertex podle transformacnich matic
	public Vertex transform(Mat4 finalMatrix) {
		return new Vertex(position.mul(finalMatrix));
	}
	
	public Vertex mul(double k) {
		Vertex v = new Vertex();
		v.position = position.mul(k);
		v.one = one*k;
		return v;
	}
	
	public Vertex add(Vertex p) {
		Vertex v = new Vertex();
		v.position = position.add(p.position);
		v.one = one + p.one;
		return v;
 	}
}
