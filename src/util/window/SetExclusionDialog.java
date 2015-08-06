package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dialog.ModalityType;
import java.awt.Window.Type;
import java.awt.GridBagLayout;
import java.awt.Component;

import javax.swing.Box;

import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import java.awt.Insets;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;

import java.awt.Color;

import javax.swing.JList;

import util.SearchBar;
import main.AnimeIndex;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetExclusionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private SearchBar searchBarCheck;
	private SearchBar searchBarExlusions;

	/**
	 * Create the dialog.
	 */
	public SetExclusionDialog() {
		setTitle("Esclusioni");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 260);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{15, 196, 16, 95, 106, 0};
		gbl_contentPanel.rowHeights = new int[]{23, 0, 0, 31, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Anime da Controllare");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridwidth = 2;
			gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
			GridBagConstraints gbc_rigidArea = new GridBagConstraints();
			gbc_rigidArea.gridheight = 3;
			gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
			gbc_rigidArea.gridx = 2;
			gbc_rigidArea.gridy = 0;
			contentPanel.add(rigidArea, gbc_rigidArea);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Anime Esclusi");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.gridwidth = 2;
			gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_1.gridx = 3;
			gbc_lblNewLabel_1.gridy = 0;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			searchBarCheck = new SearchBar();
			searchBarCheck.setFont(AnimeIndex.segui.deriveFont(11f));
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
			searchBarCheck.setIcon(icon);
			searchBarCheck.setForeground(Color.LIGHT_GRAY);
			searchBarCheck.setBackground(Color.BLACK);
			GridBagConstraints gbc_searchBarCheck = new GridBagConstraints();
			gbc_searchBarCheck.gridwidth = 2;
			gbc_searchBarCheck.insets = new Insets(0, 0, 5, 5);
			gbc_searchBarCheck.fill = GridBagConstraints.HORIZONTAL;
			gbc_searchBarCheck.gridx = 0;
			gbc_searchBarCheck.gridy = 1;
			contentPanel.add(searchBarCheck, gbc_searchBarCheck);
			searchBarCheck.setColumns(10);
		}
		{
			searchBarExlusions = new SearchBar();
			searchBarExlusions.setFont(AnimeIndex.segui.deriveFont(11f));
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
			searchBarExlusions.setIcon(icon);
			searchBarExlusions.setForeground(Color.LIGHT_GRAY);
			searchBarExlusions.setBackground(Color.BLACK);
			GridBagConstraints gbc_searchBarExlusions = new GridBagConstraints();
			gbc_searchBarExlusions.gridwidth = 2;
			gbc_searchBarExlusions.insets = new Insets(0, 0, 5, 0);
			gbc_searchBarExlusions.fill = GridBagConstraints.HORIZONTAL;
			gbc_searchBarExlusions.gridx = 3;
			gbc_searchBarExlusions.gridy = 1;
			contentPanel.add(searchBarExlusions, gbc_searchBarExlusions);
			searchBarExlusions.setColumns(10);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.gridheight = 6;
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JList listToCheck = new JList();
				listToCheck.setFont(AnimeIndex.segui.deriveFont(12f));
				scrollPane.setViewportView(listToCheck);
			}
		}
		{
			JButton button = new JButton(">>");
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridx = 2;
			gbc_button.gridy = 3;
			contentPanel.add(button, gbc_button);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.gridheight = 6;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 3;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JList listToExclude = new JList();
				listToExclude.setFont(AnimeIndex.segui.deriveFont(12f));
				scrollPane.setViewportView(listToExclude);
			}
		}
		{
			JButton button = new JButton("<<");
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridx = 2;
			gbc_button.gridy = 4;
			contentPanel.add(button, gbc_button);
		}
		{
			Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
			GridBagConstraints gbc_rigidArea = new GridBagConstraints();
			gbc_rigidArea.gridheight = 4;
			gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
			gbc_rigidArea.gridx = 2;
			gbc_rigidArea.gridy = 5;
			contentPanel.add(rigidArea, gbc_rigidArea);
		}
		{
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere"}));
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.gridwidth = 2;
			gbc_comboBox.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0;
			gbc_comboBox.gridy = 8;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JButton okButton = new JButton("Salva");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton but = (JButton) e.getSource();
					JDialog dialog = (JDialog) but.getTopLevelAncestor();
					dialog.dispose();
				}
			});
			GridBagConstraints gbc_okButton = new GridBagConstraints();
			gbc_okButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_okButton.insets = new Insets(0, 0, 0, 5);
			gbc_okButton.gridx = 3;
			gbc_okButton.gridy = 8;
			contentPanel.add(okButton, gbc_okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Annulla");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JButton but = (JButton) arg0.getSource();
					JDialog dialog = (JDialog) but.getTopLevelAncestor();
					dialog.dispose();
				}
			});
			GridBagConstraints gbc_cancelButton = new GridBagConstraints();
			gbc_cancelButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_cancelButton.gridx = 4;
			gbc_cancelButton.gridy = 8;
			contentPanel.add(cancelButton, gbc_cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
	}

}
