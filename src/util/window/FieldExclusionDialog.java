package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JCheckBox;

import main.AnimeIndex;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FieldExclusionDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JCheckBox startDateCheckBox;
	private JCheckBox durationCheckBox;
	private JCheckBox typeCheckBox;
	private JCheckBox finishDateCheckBox;
	private JCheckBox totalEpCheckBox;
	private JButton deselectAll;
	private JButton cancelButton;
	private JPanel panel;
	private JCheckBox imageCheckBox;

	/**
	 * Create the dialog.
	 */
	public FieldExclusionDialog()
	{
		super(AnimeIndex.frame, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FieldExclusionDialog.class.getResource("/image/refresh-icon15.png")));
		setResizable(false);
		setTitle("Esclusione campi");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				if(AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("false"))
				{
					String name = AnimeIndex.animeInformation.lblAnimeName.getText();
					if(AnimeIndex.exclusionAnime.containsKey(name))
					{
						boolean[] exc = AnimeIndex.exclusionAnime.get(name);
						
						if (exc[1]==false)
							totalEpCheckBox.setSelected(true);
						
						if (exc[2]==false)
							durationCheckBox.setSelected(true);
						
						if (exc[3]==false)
							startDateCheckBox.setSelected(true);
						
						if (exc[4]==false)
							finishDateCheckBox.setSelected(true);
						
						if (exc[5]==false)
							typeCheckBox.setSelected(true);
						
						if (exc[0]==false)
							imageCheckBox.setSelected(true);
						
						if(AnimeIndex.animeInformation.typeComboBox.getSelectedItem().equals("Blu-ray"))
						{
							typeCheckBox.setSelected(true);
							typeCheckBox.setEnabled(false);
						}
					}
					else
					{
						if (AnimeIndex.appProp.getProperty("excludeTotalEp").equalsIgnoreCase("true"))
							totalEpCheckBox.setSelected(true);
						
						if (AnimeIndex.appProp.getProperty("excludeDuration").equalsIgnoreCase("true"))
							durationCheckBox.setSelected(true);
						
						if (AnimeIndex.appProp.getProperty("excludeStartingDate").equalsIgnoreCase("true"))
							startDateCheckBox.setSelected(true);
						
						if (AnimeIndex.appProp.getProperty("excludeFinishDate").equalsIgnoreCase("true"))
							finishDateCheckBox.setSelected(true);
						
						if (AnimeIndex.appProp.getProperty("excludeType").equalsIgnoreCase("true"))
							typeCheckBox.setSelected(true);
						
						if (AnimeIndex.appProp.getProperty("excludeImage").equalsIgnoreCase("true"))
							imageCheckBox.setSelected(true);
						
						if(AnimeIndex.animeInformation.typeComboBox.getSelectedItem().equals("Blu-ray"))
						{
							typeCheckBox.setSelected(true);
							typeCheckBox.setEnabled(false);
						}
					}
				}
				else
				{
					String name = AnimeIndex.animeInformation.lblAnimeName.getText();
					boolean[] exc = AnimeIndex.exclusionAnime.get(name);
					
					if (exc[1]==false)
						totalEpCheckBox.setSelected(true);
					
					if (exc[2]==false)
						durationCheckBox.setSelected(true);
					
					if (exc[3]==false)
						startDateCheckBox.setSelected(true);
					
					if (exc[4]==false)
						finishDateCheckBox.setSelected(true);
					
					if (exc[5]==false)
						typeCheckBox.setSelected(true);
					
					if (exc[0]==false)
						imageCheckBox.setSelected(true);
					
					if(AnimeIndex.animeInformation.typeComboBox.getSelectedItem().equals("Blu-ray"))
					{
						typeCheckBox.setSelected(true);
						typeCheckBox.setEnabled(false);
					}
				}
			}
		});
		setBounds(100, 100, 219, 157);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 43, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 19, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			durationCheckBox = new JCheckBox("Durata");
			GridBagConstraints gbc_durationCheckBox = new GridBagConstraints();
			gbc_durationCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_durationCheckBox.gridwidth = 2;
			gbc_durationCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_durationCheckBox.gridx = 0;
			gbc_durationCheckBox.gridy = 0;
			contentPanel.add(durationCheckBox, gbc_durationCheckBox);
		}
		{
			typeCheckBox = new JCheckBox("Tipo");
			GridBagConstraints gbc_typeCheckBox = new GridBagConstraints();
			gbc_typeCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_typeCheckBox.insets = new Insets(0, 0, 5, 0);
			gbc_typeCheckBox.gridwidth = 4;
			gbc_typeCheckBox.gridx = 2;
			gbc_typeCheckBox.gridy = 0;
			contentPanel.add(typeCheckBox, gbc_typeCheckBox);
		}
		{
			startDateCheckBox = new JCheckBox("Data di Inizio");
			GridBagConstraints gbc_startDateCheckBox = new GridBagConstraints();
			gbc_startDateCheckBox.gridwidth = 2;
			gbc_startDateCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_startDateCheckBox.gridx = 0;
			gbc_startDateCheckBox.gridy = 1;
			contentPanel.add(startDateCheckBox, gbc_startDateCheckBox);
		}
		{
			finishDateCheckBox = new JCheckBox("Data di Fine");
			GridBagConstraints gbc_finishDateCheckBox = new GridBagConstraints();
			gbc_finishDateCheckBox.gridwidth = 2;
			gbc_finishDateCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_finishDateCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_finishDateCheckBox.gridx = 2;
			gbc_finishDateCheckBox.gridy = 1;
			contentPanel.add(finishDateCheckBox, gbc_finishDateCheckBox);
		}
		{
			imageCheckBox = new JCheckBox("Immagine");
			GridBagConstraints gbc_imageCheckBox = new GridBagConstraints();
			gbc_imageCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_imageCheckBox.gridwidth = 2;
			gbc_imageCheckBox.insets = new Insets(0, 0, 0, 5);
			gbc_imageCheckBox.gridx = 0;
			gbc_imageCheckBox.gridy = 2;
			contentPanel.add(imageCheckBox, gbc_imageCheckBox);
		}
		{
			totalEpCheckBox = new JCheckBox("Episodi Totali");
			GridBagConstraints gbc_totalEpCheckBox = new GridBagConstraints();
			gbc_totalEpCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_totalEpCheckBox.gridwidth = 4;
			gbc_totalEpCheckBox.gridx = 2;
			gbc_totalEpCheckBox.gridy = 2;
			contentPanel.add(totalEpCheckBox, gbc_totalEpCheckBox);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.CENTER);
			{
			}
			{
				deselectAll = new JButton("Nessun Campo Escluso");
				deselectAll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String name = AnimeIndex.animeInformation.lblAnimeName.getText();
						if(AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("false") && AnimeIndex.exclusionAnime.containsKey(name))
						{
							AnimeIndex.appProp.setProperty("excludeTotalEp", "false");
							AnimeIndex.appProp.setProperty("excludeDuration", "false");
							AnimeIndex.appProp.setProperty("excludeStartingDate", "false");
							AnimeIndex.appProp.setProperty("excludeFinishDate", "false");
						}
						totalEpCheckBox.setSelected(false);
						durationCheckBox.setSelected(false);
						startDateCheckBox.setSelected(false);
						finishDateCheckBox.setSelected(false);
						imageCheckBox.setSelected(false);
						
						if(!AnimeIndex.animeInformation.typeComboBox.getSelectedItem().equals("Blu-ray"))
						{
							typeCheckBox.setSelected(false);
							AnimeIndex.appProp.setProperty("excludeType", "false");
						}
					}
				});
			}
			buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
			buttonPane.add(deselectAll);
		}
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setLayout(new GridLayout(0, 2, 0, 0));
			JButton okButton = new JButton("OK");
			panel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("false"))
					{
						String name = AnimeIndex.animeInformation.lblAnimeName.getText();
						if(AnimeIndex.exclusionAnime.containsKey(name))
						{
							boolean[] exc = {false,false,false,false,false,false};
							if(!imageCheckBox.isSelected())
								exc[0]=true;
							if(!totalEpCheckBox.isSelected())
								exc[1]=true;
							if(!durationCheckBox.isSelected())
								exc[2]=true;
							if(!startDateCheckBox.isSelected())
								exc[3]=true;
							if(!finishDateCheckBox.isSelected())
								exc[4]=true;
							if(!typeCheckBox.isSelected())
								exc[5]=true;
							AnimeIndex.exclusionAnime.put(name, exc);
						}
						else
						{
							if (totalEpCheckBox.isSelected())
								AnimeIndex.appProp.setProperty("excludeTotalEp", "true");
							else
								AnimeIndex.appProp.setProperty("excludeTotalEp", "false");
							
							if (durationCheckBox.isSelected())
								AnimeIndex.appProp.setProperty("excludeDuration", "true");
							else
								AnimeIndex.appProp.setProperty("excludeDuration", "false");
							
							if (startDateCheckBox.isSelected())
								AnimeIndex.appProp.setProperty("excludeStartingDate", "true");
							else
								AnimeIndex.appProp.setProperty("excludeStartingDate", "false");
							
							if (finishDateCheckBox.isSelected())
								AnimeIndex.appProp.setProperty("excludeFinishDate", "true");
							else
								AnimeIndex.appProp.setProperty("excludeFinishDate", "false");
							
							if (typeCheckBox.isSelected())
								AnimeIndex.appProp.setProperty("excludeType", "true");
							else
								AnimeIndex.appProp.setProperty("excludeType", "false");
							
							if (imageCheckBox.isSelected())
								AnimeIndex.appProp.setProperty("excludeImage", "true");
							else
								AnimeIndex.appProp.setProperty("excludeImage", "false");
						}
					}
					else
					{
						String name = AnimeIndex.animeInformation.lblAnimeName.getText();
						boolean[] exc = {false,false,false,false,false,false};
						if(!imageCheckBox.isSelected())
							exc[0]=true;
						if(!totalEpCheckBox.isSelected())
							exc[1]=true;
						if(!durationCheckBox.isSelected())
							exc[2]=true;
						if(!startDateCheckBox.isSelected())
							exc[3]=true;
						if(!finishDateCheckBox.isSelected())
							exc[4]=true;
						if(!typeCheckBox.isSelected())
							exc[5]=true;
						AnimeIndex.exclusionAnime.put(name, exc);
					}
					
					AnimeIndex.activeUpdate = true;
					
					FieldExclusionDialog.this.dispose();
					
					AnimeInformation.dial = new UpdatingAnimeDataDialog();	
					AnimeInformation.dial.setLocationRelativeTo(AnimeIndex.mainFrame);
					AnimeInformation.dial.setVisible(true);
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
			{
				cancelButton = new JButton("Annulla");
				panel.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						FieldExclusionDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
			}
		}
	}
}
