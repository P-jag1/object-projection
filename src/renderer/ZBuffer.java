package renderer;

import java.awt.image.BufferedImage;
/**
 * trida, zodpovedna za reseni viditelnosti
 * @author Petr
 *
 */
public class ZBuffer {

	private double[][] pole;
	private int height;
	private int width;
	
	public ZBuffer(BufferedImage img) {
		this.height = img.getHeight();
		this.width =  img.getWidth();
		pole =  new double[width][height];
		clear();
	}
	//nastavuje novou z souradnici
    public void setZ(int x, int y, double z) {	
		pole[x][y] = z;
	}
   
   public double getZ(int x, int y) {	
		return pole[x][y];
	}
   //porovnava z souradnici
	public boolean isVisible(int x, int y, double z) {
		if(x >= width || x < 0) {
			return false;
		}
		
		if(y >= height || y < 0) {
			return false;
		}
		
		if(z >= pole[x][y]) {
			return false;
		}
		
		if(z < pole[x][y] && z >= 0) {
			return true;
		}
		
		return false;
	}
	
	//metado, ktera vycisti ZBuffer
	public void clear() {
		for(int i = 0; i <= width - 1; i++) {
			for(int j = 0; j <= height - 1; j++) {
				pole[i][j] = Double.POSITIVE_INFINITY;
			}
		}
	}
}
