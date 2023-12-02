package renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Vertex;
/**
 * trida zajistujici vykresleni trojuhelniku
 * @author Petr
 *
 */
public class TriangleRenderer {

	private BufferedImage img;
	private Color color;
	public ZBuffer zB;
	
	public TriangleRenderer(BufferedImage img) {
		this.img = img;
		zB = new ZBuffer(img);
	}
	
	public void drawTriangle(Vertex va, Vertex vb, Vertex vc, Graphics g, int h, Color color) {
		this.color = color;
		if(va.getW() < vc.getW()) {
			Vertex pom = va;
			va = vc;
			vc = pom;
		}
		if(va.getW() < vb.getW()) {
			Vertex pom = va;
			va = vb;
			vb = pom;
		}
		if(vb.getW() < vc.getW()) {
			Vertex pom = vb;
			vb = vc;
			vc = pom;
		}
		
		double wmin = 0.01;
		 
		if(va.getW() < wmin) {
			return;
		}
		
		if(vb.getW() < wmin) {
			//podel ab
			double t = (wmin - vb.getW())/(va.getW() - vb.getW());
			Vertex v1 = vb.mul((1 - t)).add(va.mul(t));
			//podel ac
			t = (wmin - vc.getW())/(va.getW() - vc.getW());
			Vertex v2 = vc.mul((1 - t)).add(va.mul(t)); 
	
			draw3DTriangleClip(va, v1, v2, g, h);
		}
		
		if(vc.getW() < wmin) {
			//podel ab
			double t = (wmin - vb.getW())/(va.getW() - vb.getW());
			Vertex v1 = vb.mul((1 - t)).add(va.mul(t));
			//podel ac
			t = (wmin - vc.getW())/(va.getW() - vc.getW());
			Vertex v2 = vc.mul((1 - t)).add(va.mul(t));
			
			draw3DTriangleClip(va, v1, v2, g, h);
			draw3DTriangleClip(va, vb, v1, g, h);
		}
		
		draw3DTriangleClip(va, vb, vc, g, h);
	}
	
	public void draw3DTriangleClip(Vertex vertex, Vertex vertex2, Vertex vertex3, Graphics g, int h) {
		//dehomogenizace
		Vertex va = vertex.dehomog();
		Vertex vb = vertex2.dehomog();
		Vertex vc = vertex3.dehomog();
				
		//transformace do okna obrazovky
		double ax = (img.getWidth() - 1) * (va.getPosition().x + 1)/2;
		double ay = (img.getWidth() - 1) * (-va.getPosition().y + 1)/2;
		double bx = (img.getWidth() - 1) * (vb.getPosition().x + 1)/2;
		double by = (img.getWidth() - 1) * (-vb.getPosition().y + 1)/2;
		double cx = (img.getWidth() - 1) * (vc.getPosition().x + 1)/2;
		double cy = (img.getWidth() - 1) * (-vc.getPosition().y + 1)/2;

		//pokud podminka projde bude se vykreslovat s vyplnenim ploch a resenim viditelnosti
		if (h == 1) {
			//seradit ay<by<cy	
			g.setColor(color);
			
			if(ay > by) {
				double pom = ay;
				ay = by;
				by = pom;
				
				pom = ax;
				ax = bx;
				bx = pom;
				
				Vertex pom2 = va;
				va = vb;
				vb = pom2;
				
			}
			
			if(ay > cy) {
				double pom = ay;
				ay = cy;
				cy = pom;
				
				pom = ax;
				ax = cx;
				cx = pom;
				
				Vertex pom2 = va;
				va = vc;
				vc = pom2;
			}
			
			if(by > cy) {
				double pom = cy;
				cy = by;
				by = pom;
				
				pom = cx;
				cx = bx;
				bx = pom;
				
				Vertex pom2 = vb;
				vb = vc;
				vc = pom2;
			}	
			
			        
					//rasterizace od a do b
					for(int y = Math.max((int)ay + 1,0); y <= Math.min(by, img.getHeight() - 1); y++) {
						double inter = (y - ay)/(by - ay);
						double x1 = ((1 - inter) * ax) + (inter * bx);
						double inter2 = (y - ay)/(cy - ay); 
						double x2 = ((1 - inter2) * ax) + (inter2 * cx);
						double z1 = ((1 - inter) * va.getPosition().getZ()) + (inter * vb.getPosition().getZ());
					    double z2 = ((1 - inter2) * va.getPosition().getZ()) + (inter2 * vc.getPosition().getZ());
						// zajistime ze x1 <= x2, jinak prohazujeme
						if(x1 > x2) {
							double pom = x1;
							x1 = x2;
							x2 = pom;
							
							pom = z2;
			                z2 = z1;
			                z1 = pom;
							
						}
						
						for(int x = Math.max((int)x1 + 1,0); x <= Math.min(x2, img.getWidth() - 1); x++) {	
							//vypocet z souradnice
							double t = (x-x1)/(x2-x1);
			                double z = ((z1*(1-t))+(z2*t));
			                //porovnani souradnice v ZBufferu
			                if(zB.isVisible(x, y, z)) {	
			                	//vykresleni pixelu
								g.drawLine(x, y, x, y);
								zB.setZ(x, y, z);
			                }
						}
					}
					//rasterizace od b do c
					for(int y = Math.max((int)by + 1,0); y <= Math.min(cy, img.getHeight() - 1); y++) {
						double inter = (y - by)/(cy - by);
						double x1 = ((1 - inter) * bx) + (inter * cx);
						double inter2 = (y - ay)/(cy - ay); 
						double x2 = ((1 - inter2) * ax) + (inter2 * cx);
						double z1 = ((1 - inter) * vb.getPosition().getZ()) + (inter * vc.getPosition().getZ());
					    double z2 = ((1 - inter2) * va.getPosition().getZ()) + (inter2 * vc.getPosition().getZ());						
						// zajistime ze x1 <= x2, jinak prohazujeme
						if(x1 > x2) {
							double pom = x1;
							x1 = x2;
							x2 = pom;
							
							pom = z2;
			                z2 = z1;
			                z1 = pom;
						}
						for(int x = Math.max((int)x1 + 1,0); x <= Math.min(x2, img.getWidth() - 1); x++) {
							//vypocet z souradnice
							double t = (x-x1)/(x2-x1);
			                double z = ((z1*(1-t))+(z2*t)); 
			                //porovnani souradnice v ZBufferu
							 if(zB.isVisible(x, y, z)) {
								 //vykresleni pixelu
									g.drawLine(x, y, x, y);	
									zB.setZ(x, y, z);
				                }
						}
						
					}
					
		}else {	
			// vykresli pouze dratovy model objektu bez reseni viditelnosti
			g.drawLine((int)ax, (int)ay, (int)bx, (int)by);
			g.drawLine((int)ax, (int)ay, (int)cx, (int)cy);
		    g.drawLine((int)bx, (int)by, (int)cx, (int)cy);	
		}
	}
}
