package renderer;

import java.awt.Color;




import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import transforms.Camera;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Vec3D;
/**
 * trida ktera se stara o platno (canvas)
 * @author Petr
 *
 */
public class Canvas extends JPanel {

	private static final long serialVersionUID = 1L;

	private Renderer renderer;
	private Camera camera;
	private int scene = 0;
	//promnena slouzici k identifikaci pohledu (pohled z prvni osoby vs. pohled z treti osoby)
	public int personId = 0;
	private int height;
	private int width;

	private double cameraForward = 1;
	private double cameraRight = 0;
	private double cameraLeft = 0;
	private double cameraBackward = 0;
	private double cameraUp = 0;
	private double cameraDown = 0;
	private double cameraAzimuth = Math.toRadians(-163);
	private double cameraZenith = Math.toRadians(-18);

	public Canvas(BufferedImage img) {
		height = img.getHeight();
		width = img.getWidth();	

		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocusInWindow();

		renderer = new Renderer(img);
	}
	
	//metoda, ktera nastavi zpusob vykreslovani na dratovy model
	public void setWire() {
		renderer.setHelpForRender(0);
	}
	//metoda, ktera nastavi zpusob vykreslovani na vyplnovani ploch
	public void setPlocha() {
		renderer.setHelpForRender(1);
	}
	//metoda ktera vycisti canvas od objektu, ponecha transformacni matici
	public void clearCanvas() {
		renderer.clear();
		scene = 0;
		repaint();
	}
	//metoda ktera vycisti canvas od objektu, nastavi transformacni matici na jednotkovou
	public void clearAll() {
		renderer.totalClear();
		scene = 0;
		repaint();

	}
	//metoda pro zmenu velikosti telesa
	public void scaleChange(double x, double y, double z) {
		renderer.solidScaleChange(x, y, z);
	}
	//metoda pro posun telesa
	public void shift(double x, double y, double z) {
		renderer.shiftSolid(x, y, z);
	}
	//metoda pro rotaci telesa
	public void rotate(double alpha, double beta, double gama) {
		renderer.rotateSolid(alpha, beta, gama);
	}
	//metoda pro urceni objektu, se kterym se bude manipulovat (tetrahedron) 
	public void setTria() {
		renderer.setHelpForControl(0);
		repaint();
	}
	//metoda pro urceni objektu, se kterym se bude manipulovat (krychle) 
	public void setCube() {
		renderer.setHelpForControl(1);
		repaint();
	}
	//metoda zajistujici vykresleni objektu
	public void drawObejct() {
		clearCanvas();
		scene = 1;
		renderer.setHelpForControl(0);
		renderer.drawAxis();
		renderer.drawCube();
		renderer.drawTria();
		repaint();
	}
	//metoda zajistujici vykresleni bikubicke plochy
	public void drawKubik() {
		clearCanvas();
		scene = 1;
		renderer.setHelpForControl(2);
		renderer.drawAxis();
		renderer.drawCubic();
		repaint();
	}
	//metoda zajistujici vykresleni tetrahedronu
	public void drawTetraHedron() {
		clearCanvas();
		scene = 1;
		renderer.setHelpForControl(0);
		renderer.drawAxis();
		renderer.drawTria();
		repaint();
	}

	//metoda zajistujici vykresleni do platna, volame pomoci repaint()
	//uvnitr vytvarime kameru, volame metody vykresleni os a sceny a vykreslujeme poznamky
	public void paint(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		if (scene == 1) {
			renderer.drawScene(g);
		} else {
			renderer.drawAxis();
			renderer.drawScene(g);
		}

		if (personId == 0) {
			camera = new Camera();
			camera.setPosition(new Vec3D(100, 40, 35));
			camera.setZenith(cameraZenith);
			camera.setAzimuth(cameraAzimuth);
			camera.forward(cameraForward);
			camera.right(cameraRight);
			camera.left(cameraLeft);
			camera.backward(cameraBackward);
			camera.up(cameraUp);
			camera.up(cameraDown);

			renderer.setViewMatrix(camera.getViewMatrix());
			repaint();
		} else if (personId == 1) {
			camera = new Camera();
			camera.setPosition(new Vec3D(0, 0, 0));
			camera.setFirstPerson(false);
			camera.setZenith(cameraZenith);
			camera.setAzimuth(cameraAzimuth);
			camera.forward(cameraForward);
			camera.setRadius(150);
			camera.right(cameraRight);
			camera.left(cameraLeft);
			camera.backward(cameraBackward);
			camera.up(cameraUp);
			camera.up(cameraDown);

			renderer.setViewMatrix(camera.getViewMatrix());
			repaint();
		}

		g.setFont((new Font("arial", Font.BOLD, 10)));
		g.setColor(Color.WHITE);
		g.drawString("Kameru otacejte pomoci leveho tl. mysi", 10, 30);
		g.drawString("Pohyb kamery pomoci numericke klavesnice", 10, 40);
		g.drawString("Posun telesa pomoci W,S,A,D,Q,E", 10, 50);
		g.drawString("Rotace telesa pomoci U,J,H,K,Z,I", 10, 60);
		g.drawString("Zmena meritka telesa pomoci R,T", 10, 70);
		g.drawString("Vyber objektu Krychle/ Tetrahedron F,G", 10, 80);
	}

	//setry a getry atributu
	public void setPersM() {
		renderer.setProjekce(new Mat4PerspRH((float) Math.PI / 4, 1, 0.01f, 100));
	}

	public void setOrthM() {
		renderer.setProjekce(new Mat4OrthoRH(100, 100, 0.01f, 100));
	}

	public double getCameraForward() {
		return cameraForward;
	}

	public void setCameraForward(double cameraForward) {
		this.cameraForward = cameraForward;
	}

	public void setCameraRight(double cameraRight) {
		this.cameraRight = cameraRight;
	}

	public void setCameraAzimuth(double cameraAzimuth) {
		this.cameraAzimuth = cameraAzimuth;
	}

	public void setCameraZenith(double cameraZenith) {
		this.cameraZenith = cameraZenith;
	}

	public double getCameraRight() {
		return cameraRight;
	}

	public double getCameraAzimuth() {
		return cameraAzimuth;
	}

	public double getCameraZenith() {
		return cameraZenith;
	}

	public double getCameraDown() {
		return cameraDown;
	}

	public void setCameraDown(double cameraDown) {
		this.cameraDown = cameraDown;
	}

	public double getCameraUp() {
		return cameraUp;
	}

	public void setCameraUp(double cameraUp) {
		this.cameraUp = cameraUp;
	}

	public double getCameraLeft() {
		return cameraLeft;
	}

	public void setCameraLeft(double cameraleft) {
		this.cameraLeft = cameraleft;
	}

	public double getCameraBackward() {
		return cameraBackward;
	}

	public void setCameraBackward(double cameraBackward) {
		this.cameraBackward = cameraBackward;
	}
}
