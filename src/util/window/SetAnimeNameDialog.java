//TODO dubbia utilita' di tale funzione
package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JTextField;

import main.AnimeIndex;

import java.awt.Window.Type;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetAnimeNameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField animeNameField;
	/**
	 * Create the dialog.
	 */
	public SetAnimeNameDialog() {
		setTitle("Nome Anime");
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.POPUP);
		setBounds(100, 100, 342, 88);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			animeNameField = new JTextField();
			contentPanel.add(animeNameField);
			animeNameField.setColumns(10);
			animeNameField.setText(AnimeIndex.animeInformation.lblAnimeName.getText());
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton okButton = new JButton("Salva");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AnimeIndex.animeInformation.lblAnimeName.setText(animeNameField.getText());
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
