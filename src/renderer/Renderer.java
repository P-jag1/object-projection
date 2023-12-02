package renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import transforms.*;
import model.Element;
import model.Kubika;
import model.Solid;
import model.SurfaceWire;
import model.Tetrahedron;
import model.Vertex;
import model.Axis;
import model.Cube;

/**
 * trida zajistujici rendrovani objektu
 * 
 * @author Petr
 *
 */
public class Renderer {
	//modelovaci matice pro bikubiku
	private Mat4 modelMatrix = new Mat4Identity();
	//modelovaci matice pro krychly
	private Mat4 modelMatrixC = new Mat4Identity();
	//modelovaci matice pro tetrahedron
	private Mat4 modelMatrixT = new Mat4Identity();
	private Mat4 viewMatrix = new Mat4Identity();
	private Mat4 projectionMatrix = new Mat4PerspRH((float) Math.PI / 4, 1, 0.01f, 100);
	//finalni matice pro bikubiku
	private Mat4 finalMatrix = new Mat4Identity();
	//finalni matice pro tetrahedron
	private Mat4 finalMatrixT = new Mat4Identity();
	//finalni matice pro krychly
	private Mat4 finalMatrixC = new Mat4Identity();
	//finalni matice pro osy
	private Mat4 matrixForAxis = new Mat4Identity();
	private List<Solid> listOfSolids = new ArrayList<Solid>();
	private BufferedImage img;
	private int helpForAxisColor = 1;
	//atribut, ktery pomaha urcit jaky typ vykreslovani pouzivame
	private int helpForRender = 0;
	//atribut, ktery pomaha urcit s jakym typem telesa pracujeme
	private int helpForControl = 0;
	private Color color;

	LineRenderer lineRenderer;
	TriangleRenderer triangleRenderer;
	Camera camera;

	public Solid helpSolid;
	public Axis axis = new Axis(30);
	public Cube cube = new Cube();
	public Tetrahedron tria = new Tetrahedron();
	public SurfaceWire cubic = new SurfaceWire(new Kubika().makeList());

	public Renderer(BufferedImage img) {
		this.img = img;
		lineRenderer = new LineRenderer(this.img);
		triangleRenderer = new TriangleRenderer(this.img);
	}

	// metoda zajistujici vykresleni teles do platna
	public void drawScene(Graphics g) {
		// projede seznam teles pro vykresleni
		for (int i = 0; i < listOfSolids.size(); i++) {
			helpSolid = listOfSolids.get(i);
			// pokud vybrane teleso jsou osy, nebudeme nasobit transformaci
			// matici
			if (helpSolid.equals(axis)) {
				matrixForAxis = new Mat4Identity();
				matrixForAxis = viewMatrix.mul(projectionMatrix);
				// pokud je vybrane teleso jine vypocitame si finalMatrix a
				// nastavime barvu telesa v dratovem vykreslovani
			} else {
				g.setColor(Color.WHITE);
				finalMatrixT = new Mat4Identity();
				finalMatrixT = modelMatrixT.mul(viewMatrix.mul(projectionMatrix));
				finalMatrixC = new Mat4Identity();
				finalMatrixC = modelMatrixC.mul(viewMatrix.mul(projectionMatrix));
				finalMatrix = new Mat4Identity();
				finalMatrix = modelMatrix.mul(viewMatrix.mul(projectionMatrix));

			}

			//ulozenime si seznam bodu aktualniho objektu do pomocneho listu
			List<Vertex> vertexlist = helpSolid.getVertices();
			// projede seznam bodu objektu a vynasobi je finalMatrixem a posleme do prislusneho rendereru podle typu elementu
			for(Element e: helpSolid.getElements()) {
				if(e.getIndices().length == 2) {
					if (helpSolid.equals(axis)) {
						Vertex a = vertexlist.get(e.getIndices()[0]).transform(matrixForAxis);
						Vertex b = vertexlist.get(e.getIndices()[1]).transform(matrixForAxis);
						if (helpForAxisColor == 1) {
							g.setColor(Color.RED);
							helpForAxisColor++;
						} else if (helpForAxisColor == 2) {
							g.setColor(Color.GREEN);
							helpForAxisColor++;
						} else if (helpForAxisColor == 3) {
							g.setColor(Color.BLUE);
							helpForAxisColor = 1;
						}
						// volani metody drawLine
						lineRenderer.drawLine(a, b, g);
					} else if(helpSolid.equals(cubic)){
						Vertex a = vertexlist.get(e.getIndices()[0]).transform(finalMatrix);
						Vertex b = vertexlist.get(e.getIndices()[1]).transform(finalMatrix);
						// volani metody drawLine
						lineRenderer.drawLine(a, b, g);
					}
				} else if(e.getIndices().length == 3 && helpForControl == 0) {
					if(helpSolid.equals(tria)) {
						//pokud pracujeme s tetrahedronem
						Vertex a = vertexlist.get(e.getIndices()[0]).transform(finalMatrixT);
						Vertex b = vertexlist.get(e.getIndices()[1]).transform(finalMatrixT);
						Vertex c = vertexlist.get(e.getIndices()[2]).transform(finalMatrixT);

						color = e.getColor();
						// volani metody drawTriangle
						triangleRenderer.drawTriangle(a, b, c, g, helpForRender, color);
					}
					if(helpSolid.equals(cube)) {
						Vertex a = vertexlist.get(e.getIndices()[0]).transform(finalMatrixC);
						Vertex b = vertexlist.get(e.getIndices()[1]).transform(finalMatrixC);
						Vertex c = vertexlist.get(e.getIndices()[2]).transform(finalMatrixC);

						color = e.getColor();
						// volani metody drawTriangle
						triangleRenderer.drawTriangle(a, b, c, g, helpForRender, color);	
					}
				} else if(e.getIndices().length == 3 && helpForControl == 1) {
					if(helpSolid.equals(cube)) {
						//pokud pracujeme s cube
						Vertex a = vertexlist.get(e.getIndices()[0]).transform(finalMatrixC);
						Vertex b = vertexlist.get(e.getIndices()[1]).transform(finalMatrixC);
						Vertex c = vertexlist.get(e.getIndices()[2]).transform(finalMatrixC);

						color = e.getColor();
						// volani metody drawTriangle
						triangleRenderer.drawTriangle(a, b, c, g, helpForRender, color);
					}
					if(helpSolid.equals(tria)) {
						Vertex a = vertexlist.get(e.getIndices()[0]).transform(finalMatrixT);
						Vertex b = vertexlist.get(e.getIndices()[1]).transform(finalMatrixT);
						Vertex c = vertexlist.get(e.getIndices()[2]).transform(finalMatrixT);

						color = e.getColor();
						// volani metody drawTriangle
						triangleRenderer.drawTriangle(a, b, c, g, helpForRender, color);	
					}
				} else if(e.getIndices().length == 3 && helpForControl == 2) {
					if(helpSolid.equals(cubic)) {
						//pokud pracujeme s bikubikou
						Vertex a = vertexlist.get(e.getIndices()[0]).transform(finalMatrix);
						Vertex b = vertexlist.get(e.getIndices()[1]).transform(finalMatrix);
						Vertex c = vertexlist.get(e.getIndices()[2]).transform(finalMatrix);

						color = e.getColor();
						// volani metody drawTriangle
						triangleRenderer.drawTriangle(a, b, c, g, helpForRender, color);
					}
				}
			}
		}
		triangleRenderer.zB.clear();
	}
	
	// prida objekt do seznamu objektu
	public void addSolid(Solid s) {
		listOfSolids.add(s);
	}

	// metoda ktera prida do seznamu objektu osy
	public void drawAxis() {
		addSolid(axis);
	}
	// metoda ktera prida do seznamu objektu krychly
	public void drawCube() {
		addSolid(cube);
	}
	// metoda ktera prida do seznamu objektu tetrahedron
	public void drawTria() {
		addSolid(tria);
	}
	// metoda ktera prida do seznamu objektu bikubiku
	public void drawCubic() {
		addSolid(cubic);
	}

	// metoda ktera vymaze seznam objektu
	public void clear() {
		listOfSolids.clear();
	}

	// metoda ktera vymaze seznam objektu a nastavuje transformacni matici na
	// jednotkovou
	public void totalClear() {
		modelMatrixT = new Mat4Identity();
		modelMatrixC = new Mat4Identity();
		modelMatrix = new Mat4Identity();
		listOfSolids.clear();
	}

	// metoda pro zmenu velikosti telesa
	public void solidScaleChange(double x, double y, double z) {
		if(helpForControl == 0) {
			modelMatrixT = modelMatrixT.mul(new Mat4Scale(x, y, z));
		} else if (helpForControl == 1) {
			modelMatrixC = modelMatrixC.mul(new Mat4Scale(x, y, z));
		} else if (helpForControl == 2) {
			modelMatrix = modelMatrix.mul(new Mat4Scale(x, y, z));
		}
	}

	// metoda pro pohyb telesa po osach
	public void shiftSolid(double x, double y, double z) {
		if(helpForControl == 0) {
			modelMatrixT = modelMatrixT.mul(new Mat4Transl(x, y, z));
		} else if (helpForControl == 1) {
			modelMatrixC = modelMatrixC.mul(new Mat4Transl(x, y, z));
		} else if (helpForControl == 2) {
			modelMatrix = modelMatrix.mul(new Mat4Transl(x, y, z));
		}
	}

	// metoda pro rotaci telesa
	public void rotateSolid(double alpha, double beta, double gama) {
		if(helpForControl == 0) {
			modelMatrixT = modelMatrixT.mul(new Mat4RotXYZ(alpha, beta, gama));
		} else if (helpForControl == 1) {
			modelMatrixC = modelMatrixC.mul(new Mat4RotXYZ(alpha, beta, gama));
		} else if (helpForControl == 2) {
			modelMatrix = modelMatrix.mul(new Mat4RotXYZ(alpha, beta, gama));
		}
	}

	public void setProjekce(Mat4 projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}

	public void setViewMatrix(Mat4 viewMatrix) {
		this.viewMatrix = viewMatrix;
	}

	public void setHelpForRender(int helpForRender) {
		this.helpForRender = helpForRender;
	}
	
	public void setHelpForControl(int helpForControl) {
		this.helpForControl = helpForControl;
	}

}