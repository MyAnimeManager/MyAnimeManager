package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JRadioButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import main.AnimeIndex;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PreferenceDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	public static final ButtonGroup listGroup = new ButtonGroup();
	private JRadioButton rdbtnLastList;
	private JRadioButton rdbtnChooseList;
	private JComboBox choosedList;

	/**
	 * Create the dialog.
	 */
	public PreferenceDialog()
	{
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblListToVisualize = new JLabel("Lista da visualizzare all'avvio :");
			GridBagConstraints gbc_lblListToVisualize = new GridBagConstraints();
			gbc_lblListToVisualize.insets = new Insets(0, 0, 5, 5);
			gbc_lblListToVisualize.gridx = 0;
			gbc_lblListToVisualize.gridy = 0;
			contentPanel.add(lblListToVisualize, gbc_lblListToVisualize);
		}
		{
			rdbtnLastList = new JRadioButton("Ultima Lista");
			rdbtnLastList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				choosedList.setEnabled(false);
				}
			});
			GridBagConstraints gbc_rdbtnLastList = new GridBagConstraints();
			gbc_rdbtnLastList.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnLastList.gridx = 0;
			gbc_rdbtnLastList.gridy = 1;
			contentPanel.add(rdbtnLastList, gbc_rdbtnLastList);
		}
		{
			rdbtnChooseList = new JRadioButton("Scegli Lista :");
			rdbtnChooseList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					choosedList.setEnabled(true);
				}
			});
			GridBagConstraints gbc_rdbtnChooseList = new GridBagConstraints();
			gbc_rdbtnChooseList.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnChooseList.gridx = 0;
			gbc_rdbtnChooseList.gridy = 2;
			contentPanel.add(rdbtnChooseList, gbc_rdbtnChooseList);
		}
		{
			choosedList = new JComboBox();
			GridBagConstraints gbc_choosedList = new GridBagConstraints();
			gbc_choosedList.fill = GridBagConstraints.HORIZONTAL;
			gbc_choosedList.gridx = 1;
			gbc_choosedList.gridy = 2;
			contentPanel.add(choosedList, gbc_choosedList);
			choosedList.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere","Uscite del Giorno"}));
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(100);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Salva");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (rdbtnLastList.isSelected())
						{
							AnimeIndex.appProp.setProperty("List_to_visualize_at_start", "Last list");
							AnimeIndex.appProp.setProperty("Last_list", AnimeIndex.getList());
						}
						else
						{
							String list = (String) choosedList.getSelectedItem();
							AnimeIndex.appProp.setProperty("List_to_visualize_at_start", list);
							AnimeIndex.appProp.setProperty("Last_list", "---");
						}
						
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
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
				buttonPane.add(cancelButton);
			}
		}
		
		String listPreference = AnimeIndex.appProp.getProperty("List_to_visualize_at_start");
		if (listPreference.equalsIgnoreCase("last list"))
		{
			rdbtnLastList.setSelected(true);
			choosedList.setEnabled(false);
		}
		else
			rdbtnChooseList.setSelected(true);
		listGroup.add(rdbtnChooseList);
		listGroup.add(rdbtnLastList);
	}

}
