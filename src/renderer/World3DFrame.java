package renderer;

import java.awt.BorderLayout;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 * trida ktera se stara o celkovy frame
 * 
 * @author Petr
 *
 */
public class World3DFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private Canvas canvas;
	private MyMouseAdapter mA;
	private MyMouseListener mL;
	private JButton btnCleanCanvas = new JButton("Vyèistit plátno");
	private JButton btnObject = new JButton("Objekty");
	private JButton btnKubika = new JButton("Kubika");
	private JButton btnTet = new JButton("Tetrahedron");
	private JButton btnMod1 = new JButton("Drátový model");
	private JButton btnMod2 = new JButton("Vyplnìné plochy");
	private JButton btnPers = new JButton("Perspektivní pohled");
	private JButton btnOrt = new JButton("Ortogonální pohled");
	private JButton btnFp = new JButton("First Person");
	private JButton btnTp = new JButton("Third Person");
	private JButton btnInfo = new JButton("Info");
	private JPanel canvasPanel = new JPanel();
	private JToolBar tlb = new JToolBar();
	private BufferedImage img;
	//promnena, slouzici k identifikaci pohledove matice
	public int btnIndex = 0;
	public int controlIndex = 0;
	private static final double CAMERA_MOVE = 1;
	private static final double CAMERA_RAD_MOVE = Math.toRadians(0.5);
	private int beginX, beginY;
	boolean mouseClicked = false;

	// tridy zajistujici reakce po kliknuti na mys
	public class MyMouseAdapter extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			mouseClicked = true;
			beginX = e.getX();
			beginY = e.getY();
		}

		public void mouseReleased(MouseEvent e) {
			mouseClicked = false;
		}
	}

	public class MyMouseListener extends MouseMotionAdapter {

		public void mouseDragged(MouseEvent e) {
			if (mouseClicked) {
				double alpha = (e.getY() - beginY) * CAMERA_RAD_MOVE;
				double beta = (e.getX() - beginX) * CAMERA_RAD_MOVE;

				beginX = e.getX();
				beginY = e.getY();

				double a = canvas.getCameraAzimuth();
				canvas.setCameraAzimuth(a += beta);
				double z = canvas.getCameraZenith();
				canvas.setCameraZenith(z += alpha);

				repaint();
			}
		}
	}

	public World3DFrame(int width, int height) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
		setResizable(false);
		requestFocusInWindow();
		setTitle("PRGF úloha 3");
		// tvorba tlacitek
		tlb.add(btnObject);
		btnObject.setFocusable(false);
		btnObject.addActionListener(e -> {
			canvas.drawObejct();
			controlIndex = 0;
			repaint();
		});

		
		tlb.add(btnTet);
		btnTet.setFocusable(false);
		btnTet.addActionListener(e -> {
			canvas.drawTetraHedron();
			canvas.setTria();
			controlIndex = 0;
			repaint();
		});
		
		tlb.add(btnKubika);
		btnKubika.setFocusable(false);
		btnKubika.addActionListener(e -> {
			canvas.drawKubik();
			controlIndex = 1;
			repaint();
		});
		
		tlb.add(btnMod1);
		btnMod1.setFocusable(false);
		btnMod1.addActionListener(e -> {
			canvas.setWire();
			repaint();
		});
		
		tlb.add(btnMod2);
		btnMod2.setFocusable(false);
		btnMod2.addActionListener(e -> {
			canvas.setPlocha();
			repaint();
		});
		
		tlb.add(btnFp);
		btnFp.setFocusable(false);
		btnFp.addActionListener(e -> {
			canvas.personId = 0;
			repaint();
		});

		tlb.add(btnTp);
		btnTp.setFocusable(false);
		btnTp.addActionListener(e -> {
			canvas.personId = 1;
			repaint();
		});

		tlb.add(btnPers);
		btnPers.setFocusable(false);
		btnPers.addActionListener(e -> {
			btnIndex = 0;
			if (btnIndex == 0) {
				canvas.setPersM();
			}
			repaint();
		});

		tlb.add(btnOrt);
		btnOrt.setFocusable(false);
		btnOrt.addActionListener(e -> {
			btnIndex = 1;
			if (btnIndex == 1) {
				canvas.setOrthM();
			}
			repaint();
		});

		tlb.add(btnCleanCanvas);
		btnCleanCanvas.setFocusable(false);
		btnCleanCanvas.addActionListener(e -> {
			canvas.clearAll();
			repaint();
		});
		
		tlb.add(btnInfo);
		btnInfo.setFocusable(false);
		btnInfo.addActionListener(e -> {
			Dialog d = new Dialog();
			d.showNew();
			
		});

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// vytvoreni instance platna(canvasu)
		canvas = new Canvas(img);
		canvasPanel.add(canvas);

		add(tlb, BorderLayout.NORTH);
		add(canvasPanel, BorderLayout.CENTER);

		mA = new MyMouseAdapter();
		canvasPanel.addMouseListener(mA);

		mL = new MyMouseListener();
		canvasPanel.addMouseMotionListener(mL);

		pack();
		setVisible(true);
		// implementace keyListeneru
		addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				// klavesove zkratky pro rotaci telesa
				switch (e.getKeyCode()) {
				case KeyEvent.VK_F:
					if(controlIndex == 0) {
						canvas.setCube();
					}
					break;
				case KeyEvent.VK_G :
					if(controlIndex == 0) {
						canvas.setTria();
					}
					break;
				case KeyEvent.VK_H:
					canvas.rotate(0.1, 0, 0);
					break;
				case KeyEvent.VK_K:
					canvas.rotate(-0.1, 0, 0);
					break;
				case KeyEvent.VK_J:
					canvas.rotate(0, 0.1, 0);
					break;
				case KeyEvent.VK_U:
					canvas.rotate(0, -0.1, 0);
					break;
				case KeyEvent.VK_I:
					canvas.rotate(0, 0, 0.1);
					break;
				case KeyEvent.VK_Z:
					canvas.rotate(0, 0, -0.1);
					break;
				// klavesove zkratky pro posun telesa
				case KeyEvent.VK_S:
					canvas.shift(1, 0, 0);
					break;
				case KeyEvent.VK_W:
					canvas.shift(-1, 0, 0);
					break;
				case KeyEvent.VK_D:
					canvas.shift(0, 1, 0);
					break;
				case KeyEvent.VK_A:
					canvas.shift(0, -1, 0);
					break;
				case KeyEvent.VK_Q:
					canvas.shift(0, 0, 1);
					break;
				case KeyEvent.VK_E:
					canvas.shift(0, 0, -1);
					break;
				// klavesove zkratky pro zmenu velikosti telesa
				case KeyEvent.VK_R:
					canvas.scaleChange(1.1, 1.1, 1.1);
					break;
				case KeyEvent.VK_T:
					canvas.scaleChange(0.9, 0.9, 0.9);
					break;
				// klavesove zkratky pro pohyb camery
				case KeyEvent.VK_NUMPAD8:
					double pom1 = canvas.getCameraForward();
					canvas.setCameraForward(pom1 += CAMERA_MOVE);
					break;
				case KeyEvent.VK_NUMPAD5:
					double pom2 = canvas.getCameraBackward();
					canvas.setCameraBackward(pom2 += CAMERA_MOVE);
					break;
				case KeyEvent.VK_NUMPAD4:
					double pom3 = canvas.getCameraLeft();
					canvas.setCameraLeft(pom3 += CAMERA_MOVE);
					break;
				case KeyEvent.VK_NUMPAD6:
					double pom4 = canvas.getCameraRight();
					canvas.setCameraRight(pom4 += CAMERA_MOVE);
					break;
				case KeyEvent.VK_NUMPAD7:
					double pom5 = canvas.getCameraAzimuth();
					canvas.setCameraAzimuth(pom5 -= CAMERA_RAD_MOVE);
					break;
				case KeyEvent.VK_NUMPAD9:
					double pom6 = canvas.getCameraAzimuth();
					canvas.setCameraAzimuth(pom6 += CAMERA_RAD_MOVE);
					break;
				case KeyEvent.VK_NUMPAD1:
					double pom7 = canvas.getCameraUp();
					canvas.setCameraUp(pom7 += CAMERA_MOVE);
					break;
				case KeyEvent.VK_NUMPAD3:
					double pom8 = canvas.getCameraDown();
					canvas.setCameraDown(pom8 -= CAMERA_MOVE);
					break;

				default:
					break;
				}

				repaint();
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingUtilities.invokeLater(() -> {
				SwingUtilities.invokeLater(() -> {
					SwingUtilities.invokeLater(() -> {
						new World3DFrame(1000, 800);
					});
				});
			});
		});
	}

}
