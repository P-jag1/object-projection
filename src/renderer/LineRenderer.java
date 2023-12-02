package renderer;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.Vertex;
/**
 * trida zajistuji vykresleni usecky bez reseni viditelnosti
 * @author Petr
 *
 */
public class LineRenderer {
	
	private BufferedImage img;
	
	public LineRenderer(BufferedImage img) {
		this.img = img;
	}

	public void drawLine(Vertex x, Vertex y, Graphics g) {
		// ořezání v homogeních souřadnicích
		if (x.getW() < y.getW()) {
			Vertex a = x;
			x = y;
			y = a;
		}

		double wmin = 0.01;
		
		if (x.getW() < wmin)
			return;
		if (y.getW() < wmin) {
			double t = (wmin - y.getW())/(x.getW() - y.getW());
			x = x.mul((1 - t)).add(y.mul(t));
		}
		// volani metody draw3DLineClip
		draw3DLineClip(x, y, g);
	}

	public void draw3DLineClip(Vertex x, Vertex y, Graphics g) {
		// dehomogenizace
		Vertex vA = x.dehomog();
		Vertex vB = y.dehomog();
		// transformace do okna
		double ax = (img.getWidth() - 1) * (vA.getPosition().x + 1)/2;
		double ay = (img.getWidth() - 1) * (-vA.getPosition().y + 1)/2;
		double bx = (img.getWidth() - 1) * (vB.getPosition().x + 1)/2;
		double by = (img.getWidth() - 1) * (-vB.getPosition().y + 1)/2;
		
		
		//vykresleni usecky bez reseni viditelnosti
		g.drawLine((int)ax, (int)ay, (int)bx, (int)by);

	}

}
