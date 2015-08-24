package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import main.AnimeIndex;

public class CreditDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public CreditDialog()
	{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(new ImageIcon(getClass().getResource("/image/credit.png")));
			GridBagConstraints gbc_imageLabel = new GridBagConstraints();
			gbc_imageLabel.insets = new Insets(0, 0, 0, 5);
			gbc_imageLabel.gridx = 0;
			gbc_imageLabel.gridy = 0;
			contentPanel.add(imageLabel, gbc_imageLabel);
		}
		{
			JTextPane txtpnTestoDiProva = new JTextPane();
			txtpnTestoDiProva.setOpaque(false);
			txtpnTestoDiProva.setBorder(null);
			txtpnTestoDiProva.setBackground(new Color(19,19,19));
			txtpnTestoDiProva.setText("Testo di prova");
			txtpnTestoDiProva.setEditable(false);
			GridBagConstraints gbc_txtpnTestoDiProva = new GridBagConstraints();
			gbc_txtpnTestoDiProva.fill = GridBagConstraints.BOTH;
			gbc_txtpnTestoDiProva.gridx = 1;
			gbc_txtpnTestoDiProva.gridy = 0;
			contentPanel.add(txtpnTestoDiProva, gbc_txtpnTestoDiProva);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton butt = (JButton) e.getSource();
						JDialog dial = (JDialog) butt.getTopLevelAncestor();
						dial.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
			}
		}
	}

}
