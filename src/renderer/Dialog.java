package renderer;
/**
 * trida ktera se stara o dialogove okno s informacemi
 * @author Petr
 *
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;

import javax.swing.JDialog;


public class Dialog extends JDialog{	
	
	public void showNew() {
		initGui();
	}
	
	public Dialog() {
		setModal(true); // modalni rezim okna
	}
	
	private void initGui() {

		setTitle("Informace");

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);


		// pridame potrebne komponenty

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		add(new Label("Vytvořeno během studia jako školní projekt."), c);


		c.gridx = 0;
		c.gridy = 1;
		add(new Label("Autor: Jagoš Petr"), c);

		c.gridx = 0;
		c.gridy = 2;
		add(new Label("Naposledy upraveno: 02.12.2023"), c);
		
		pack();

		setVisible(true);

	}

	}

