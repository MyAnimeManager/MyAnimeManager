package util.window;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import main.AnimeIndex;


public class ChristmasDialog extends JDialog {
	
	/**
	 * Create the dialog.
	 */
	public ChristmasDialog()
	{
		super(AnimeIndex.frame, true);
		setTitle("Buon Natale!");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		
		JLabel christmasLabel = new JLabel();
		christmasLabel.setIcon(new ImageIcon(ChristmasDialog.class.getResource("/image/merry_christmas.png")));
		getContentPane().add(christmasLabel, BorderLayout.CENTER);
		pack();
		
	}
	
}
