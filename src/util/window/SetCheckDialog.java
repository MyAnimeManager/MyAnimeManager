package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JCheckBox;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JSeparator;

import main.AnimeIndex;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class SetCheckDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JCheckBox checkCompleted;
	private JCheckBox checkAiring;
	private JCheckBox checkOAV;
	private JCheckBox checkFilm;
	private JCheckBox checkToSee;
	private JButton allCheckButton;
	private JButton noneCheckButton;

	/**
	 * Create the dialog.
	 */
	public SetCheckDialog() {
		super(AnimeIndex.frame,true);
		setTitle("Liste da Controllare");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 232, 198);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
	
		{
			checkCompleted = new JCheckBox("Anime Completati");
			checkCompleted.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(checkCompleted.isSelected() && checkAiring.isSelected() && checkOAV.isSelected() && checkFilm.isSelected() && checkToSee.isSelected())
					{
						allCheckButton.setEnabled(false);
						noneCheckButton.setEnabled(true);
					}
					if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
					{
						noneCheckButton.setEnabled(false);
						allCheckButton.setEnabled(true);
					}
				}
			});
			GridBagConstraints gbc_checkCompleted = new GridBagConstraints();
			gbc_checkCompleted.anchor = GridBagConstraints.WEST;
			gbc_checkCompleted.insets = new Insets(0, 0, 5, 5);
			gbc_checkCompleted.gridx = 0;
			gbc_checkCompleted.gridy = 0;
			contentPanel.add(checkCompleted, gbc_checkCompleted);
		}
		{
			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.gridheight = 5;
			gbc_separator.insets = new Insets(0, 0, 5, 5);
			gbc_separator.gridx = 1;
			gbc_separator.gridy = 0;
			contentPanel.add(separator, gbc_separator);
		}
		{
		    checkAiring = new JCheckBox("Anime in Corso");
		    checkAiring.addItemListener(new ItemListener() {
		    	public void itemStateChanged(ItemEvent e) {
		    		if(checkCompleted.isSelected() && checkAiring.isSelected() && checkOAV.isSelected() && checkFilm.isSelected() && checkToSee.isSelected())
		    		{
		    			allCheckButton.setEnabled(false);
		    			noneCheckButton.setEnabled(true);
		    		}
		    		if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
		    		{
		    			noneCheckButton.setEnabled(false);
		    			allCheckButton.setEnabled(true);
		    		}
		    	}
		    });
			GridBagConstraints gbc_checkAiring = new GridBagConstraints();
			gbc_checkAiring.anchor = GridBagConstraints.WEST;
			gbc_checkAiring.insets = new Insets(0, 0, 5, 5);
			gbc_checkAiring.gridx = 0;
			gbc_checkAiring.gridy = 1;
			contentPanel.add(checkAiring, gbc_checkAiring);
		}
		{
			allCheckButton = new JButton("Tutte");
			allCheckButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					checkCompleted.setSelected(true);
					checkAiring.setSelected(true);
					checkOAV.setSelected(true);
					checkFilm.setSelected(true);
					checkToSee.setSelected(true);
					allCheckButton.setEnabled(false);
					noneCheckButton.setEnabled(true);
				}
			});
			GridBagConstraints gbc_allCheckButton = new GridBagConstraints();
			gbc_allCheckButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_allCheckButton.insets = new Insets(0, 0, 5, 0);
			gbc_allCheckButton.gridx = 2;
			gbc_allCheckButton.gridy = 1;
			contentPanel.add(allCheckButton, gbc_allCheckButton);
		}
		{
		    checkOAV = new JCheckBox("OAV");
		    checkOAV.addItemListener(new ItemListener() {
		    	public void itemStateChanged(ItemEvent e) {
		    		if(checkCompleted.isSelected() && checkAiring.isSelected() && checkOAV.isSelected() && checkFilm.isSelected() && checkToSee.isSelected())
		    		{
		    			allCheckButton.setEnabled(false);
		    			noneCheckButton.setEnabled(true);
		    		}
		    		if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
		    		{
		    			noneCheckButton.setEnabled(false);
		    			allCheckButton.setEnabled(true);
		    		}
		    	}
		    });
			GridBagConstraints gbc_checkOAV = new GridBagConstraints();
			gbc_checkOAV.anchor = GridBagConstraints.WEST;
			gbc_checkOAV.insets = new Insets(0, 0, 5, 5);
			gbc_checkOAV.gridx = 0;
			gbc_checkOAV.gridy = 2;
			contentPanel.add(checkOAV, gbc_checkOAV);
		}
		{
			checkFilm = new JCheckBox("Film");
			checkFilm.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(checkCompleted.isSelected() && checkAiring.isSelected() && checkOAV.isSelected() && checkFilm.isSelected() && checkToSee.isSelected())
					{
						allCheckButton.setEnabled(false);
						noneCheckButton.setEnabled(true);
					}
					if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
					{
						noneCheckButton.setEnabled(false);
						allCheckButton.setEnabled(true);
					}
				}
			});
			GridBagConstraints gbc_checkFilm = new GridBagConstraints();
			gbc_checkFilm.anchor = GridBagConstraints.WEST;
			gbc_checkFilm.insets = new Insets(0, 0, 5, 5);
			gbc_checkFilm.gridx = 0;
			gbc_checkFilm.gridy = 3;
			contentPanel.add(checkFilm, gbc_checkFilm);
		}
		{
			noneCheckButton = new JButton("Nessuna");
			noneCheckButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkCompleted.setSelected(false);
					checkAiring.setSelected(false);
					checkOAV.setSelected(false);
					checkFilm.setSelected(false);
					checkToSee.setSelected(false);
					noneCheckButton.setEnabled(false);
					allCheckButton.setEnabled(true);
				}
			});
			GridBagConstraints gbc_noneCheckButton = new GridBagConstraints();
			gbc_noneCheckButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_noneCheckButton.insets = new Insets(0, 0, 5, 0);
			gbc_noneCheckButton.gridx = 2;
			gbc_noneCheckButton.gridy = 3;
			contentPanel.add(noneCheckButton, gbc_noneCheckButton);
		}
		{
			checkToSee = new JCheckBox("Completi da Vedere");
			checkToSee.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(checkCompleted.isSelected() && checkAiring.isSelected() && checkOAV.isSelected() && checkFilm.isSelected() && checkToSee.isSelected())
					{
						allCheckButton.setEnabled(false);
						noneCheckButton.setEnabled(true);
					}
					if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
					{
						noneCheckButton.setEnabled(false);
						allCheckButton.setEnabled(true);
					}
				}
			});
			GridBagConstraints gbc_checkToSee = new GridBagConstraints();
			gbc_checkToSee.anchor = GridBagConstraints.WEST;
			gbc_checkToSee.insets = new Insets(0, 0, 0, 5);
			gbc_checkToSee.gridx = 0;
			gbc_checkToSee.gridy = 4;
			contentPanel.add(checkToSee, gbc_checkToSee);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkCompleted.isSelected() && checkAiring.isSelected() && checkOAV.isSelected() && checkFilm.isSelected() && checkToSee.isSelected())
						{	
							AddAnimeDialog.checkToggleButton.setText("Tutte le Liste");
							AddAnimeDialog.checkToggleButton.setSelected(true);
							AddAnimeDialog.checkCompletedList = AddAnimeDialog.checkAiringList = AddAnimeDialog.checkOAVList = AddAnimeDialog.checkFilmList = AddAnimeDialog.checkToSeeList = true;
							AnimeIndex.appProp.setProperty("List_to_Check", "All");
					    }
						else if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
						{
							AddAnimeDialog.checkToggleButton.setText("Nessuna Lista");
							AddAnimeDialog.checkToggleButton.setSelected(false);
							AddAnimeDialog.checkCompletedList = AddAnimeDialog.checkAiringList = AddAnimeDialog.checkOAVList = AddAnimeDialog.checkFilmList = AddAnimeDialog.checkToSeeList = false;
							AnimeIndex.appProp.setProperty("List_to_Check", "None");
						}
						else if(checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
						{
							AddAnimeDialog.checkToggleButton.setText("Anime Completati");
							AddAnimeDialog.checkToggleButton.setSelected(true);
							AddAnimeDialog.checkCompletedList = true;
							AddAnimeDialog.checkAiringList = AddAnimeDialog.checkOAVList = AddAnimeDialog.checkFilmList = AddAnimeDialog.checkToSeeList = false;
							AnimeIndex.appProp.setProperty("List_to_Check", "Anime Completati");
						}
						else if(!checkCompleted.isSelected() && checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
						{
							AddAnimeDialog.checkToggleButton.setText("Anime in Corso");
							AddAnimeDialog.checkToggleButton.setSelected(true);
							AddAnimeDialog.checkAiringList = true;
							AddAnimeDialog.checkCompletedList = AddAnimeDialog.checkOAVList = AddAnimeDialog.checkFilmList = AddAnimeDialog.checkToSeeList = false;
							AnimeIndex.appProp.setProperty("List_to_Check", "Anime in Corso");
						}
						else if(!checkCompleted.isSelected() && !checkAiring.isSelected() && checkOAV.isSelected() && !checkFilm.isSelected() && !checkToSee.isSelected())
						{
							AddAnimeDialog.checkToggleButton.setText("OAV");
							AddAnimeDialog.checkToggleButton.setSelected(true);
							AddAnimeDialog.checkOAVList = true;
							AddAnimeDialog.checkCompletedList = AddAnimeDialog.checkAiringList = AddAnimeDialog.checkFilmList = AddAnimeDialog.checkToSeeList = false;
							AnimeIndex.appProp.setProperty("List_to_Check", "OAV");
						}
						else if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && checkFilm.isSelected() && !checkToSee.isSelected())
						{
							AddAnimeDialog.checkToggleButton.setText("Film");
							AddAnimeDialog.checkToggleButton.setSelected(true);
							AddAnimeDialog.checkFilmList = true;
							AddAnimeDialog.checkCompletedList = AddAnimeDialog.checkAiringList = AddAnimeDialog.checkOAVList = AddAnimeDialog.checkToSeeList = false;
							AnimeIndex.appProp.setProperty("List_to_Check", "Film");
						}
						else if(!checkCompleted.isSelected() && !checkAiring.isSelected() && !checkOAV.isSelected() && !checkFilm.isSelected() && checkToSee.isSelected())
						{
							AddAnimeDialog.checkToggleButton.setText("Completi da Vedere");
							AddAnimeDialog.checkToggleButton.setSelected(true);
							AddAnimeDialog.checkToSeeList = true;
							AddAnimeDialog.checkCompletedList = AddAnimeDialog.checkAiringList = AddAnimeDialog.checkOAVList = AddAnimeDialog.checkFilmList = false;
							AnimeIndex.appProp.setProperty("List_to_Check", "Completi Da vedere");
						}
						else
						{
							AddAnimeDialog.checkToggleButton.setText("Più Liste");
							AddAnimeDialog.checkToggleButton.setSelected(true);
							
							if(checkCompleted.isSelected())
								AddAnimeDialog.checkCompletedList = true;
							else
								AddAnimeDialog.checkCompletedList = false;
							
							if(checkAiring.isSelected())
								AddAnimeDialog.checkAiringList = true;
							else
								AddAnimeDialog.checkAiringList = false;
							
							if(checkOAV.isSelected())
								AddAnimeDialog.checkOAVList = true;
							else
								AddAnimeDialog.checkOAVList = false;
							
							if(checkFilm.isSelected())
								AddAnimeDialog.checkFilmList = true;
							else
								AddAnimeDialog.checkFilmList = false;
							
							if(checkToSee.isSelected())
								AddAnimeDialog.checkToSeeList = true;
							else
								AddAnimeDialog.checkToSeeList = false;
							
							AnimeIndex.appProp.setProperty("List_to_Check", "Multi Check");
						}
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
						if(AddAnimeDialog.checkToggleButton.getText().equalsIgnoreCase("Nessuna Lista"))
							AddAnimeDialog.checkToggleButton.setSelected(false);
						else
							AddAnimeDialog.checkToggleButton.setSelected(true);
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		if (AnimeIndex.appProp.getProperty("List_to_Check").equalsIgnoreCase("all"))
		{
			checkCompleted.setSelected(true); 
			checkAiring.setSelected(true);
			checkOAV.setSelected(true);
			checkFilm.setSelected(true); 
			checkToSee.setSelected(true);
			allCheckButton.setEnabled(false);
		}
		else if (AddAnimeDialog.checkCompletedList == false && AddAnimeDialog.checkAiringList == false && AddAnimeDialog.checkOAVList == false && AddAnimeDialog.checkFilmList == false && AddAnimeDialog.checkToSeeList == false)
		{
			checkCompleted.setSelected(false); 
			checkAiring.setSelected(false);
			checkOAV.setSelected(false);
			checkFilm.setSelected(false); 
			checkToSee.setSelected(false);
			noneCheckButton.setEnabled(false);
		}
		else
		{
			if (AddAnimeDialog.checkCompletedList)
				checkCompleted.setSelected(true);
			else
				checkCompleted.setSelected(false);
			
			if (AddAnimeDialog.checkAiringList)
				checkAiring.setSelected(true);
			else
				checkAiring.setSelected(false);
			
			if (AddAnimeDialog.checkOAVList)
				checkOAV.setSelected(true);
			else
				checkOAV.setSelected(false);
			
			if (AddAnimeDialog.checkFilmList)
				checkFilm.setSelected(true);
			else
				checkFilm.setSelected(false);
			
			if (AddAnimeDialog.checkToSeeList)
				checkToSee.setSelected(true);
			else
				checkToSee.setSelected(false);
		}
	}

}
