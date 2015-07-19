package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;

import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window.Type;

import javax.swing.JTextField;

import main.AnimeIndex;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetLinkDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField linkField;

	/**
	 * Create the dialog.
	 */
	public SetLinkDialog() {
		setType(Type.POPUP);
		setTitle("Link");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setBounds(100, 100, 342, 88);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			linkField = new JTextField(AnimeIndex.animeInformation.getLink());
			contentPanel.add(linkField);
			linkField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton saveButton = new JButton("Salva");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AnimeIndex.animeInformation.setLink(linkField.getText());
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
						if (linkField.getText() != null && !(linkField.getText().isEmpty()))
							AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						else
							AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					}
				});
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
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
				buttonPane.add(cancelButton);
			}
		}
	}

}
