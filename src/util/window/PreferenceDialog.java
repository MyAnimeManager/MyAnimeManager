package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import util.FileManager;

public class PreferenceDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	public static final ButtonGroup listGroup = new ButtonGroup();
	private JCheckBox rdbtnLastList;
	private JCheckBox rdbtnChooseList;
	private JComboBox choosedList;
	private JTextField defaultImageDirectoryField;

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
		gbl_contentPanel.columnWidths = new int[]{0, 205, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
			rdbtnLastList = new JCheckBox("Ultima Lista");
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
			rdbtnChooseList = new JCheckBox("Scegli Lista :");
			rdbtnChooseList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					choosedList.setEnabled(true);
				}
			});
			GridBagConstraints gbc_rdbtnChooseList = new GridBagConstraints();
			gbc_rdbtnChooseList.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnChooseList.gridx = 0;
			gbc_rdbtnChooseList.gridy = 2;
			contentPanel.add(rdbtnChooseList, gbc_rdbtnChooseList);
		}
		{
			choosedList = new JComboBox();
			GridBagConstraints gbc_choosedList = new GridBagConstraints();
			gbc_choosedList.gridwidth = 2;
			gbc_choosedList.insets = new Insets(0, 0, 5, 0);
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
		{
			JLabel lblImmagineIniziale = new JLabel("Immagine Iniziale :");
			GridBagConstraints gbc_lblImmagineIniziale = new GridBagConstraints();
			gbc_lblImmagineIniziale.anchor = GridBagConstraints.WEST;
			gbc_lblImmagineIniziale.insets = new Insets(0, 0, 5, 5);
			gbc_lblImmagineIniziale.gridx = 0;
			gbc_lblImmagineIniziale.gridy = 3;
			contentPanel.add(lblImmagineIniziale, gbc_lblImmagineIniziale);
		}
		{
			JLabel lblPercorsoFile = new JLabel("Percorso File :");
			GridBagConstraints gbc_lblPercorsoFile = new GridBagConstraints();
			gbc_lblPercorsoFile.insets = new Insets(0, 0, 5, 5);
			gbc_lblPercorsoFile.gridx = 0;
			gbc_lblPercorsoFile.gridy = 4;
			contentPanel.add(lblPercorsoFile, gbc_lblPercorsoFile);
		}
		{
			JButton DefaultImageButton = new JButton("Imposta");
			DefaultImageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(defaultImageDirectoryField.getText() != null && !(defaultImageDirectoryField.getText().isEmpty()))
						{
						if(defaultImageDirectoryField.getText().substring(defaultImageDirectoryField.getText().length()-4).equals(".png") || defaultImageDirectoryField.getText().substring(defaultImageDirectoryField.getText().length()-5).equals(".png\"")){
							File image = new File(defaultImageDirectoryField.getText());
							try{
							BufferedImage bufimg = ImageIO.read (image);

							int width = bufimg.getWidth ();
							int height = bufimg.getHeight ();
					    
							if(width < 225 && height < 310){
								FileManager.saveDefaultImage(defaultImageDirectoryField.getText(), "default");
								JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Impostazione avvenuta correttamente.", "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);}
								
							else
								JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Le dimensioni dell'immagine non sono corrette.", "Errore!", JOptionPane.ERROR_MESSAGE);
							}
						catch(IOException e2){
							e2.printStackTrace();}
						}
						else
							JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Il formato dell'immagine deve essere .png!\n\rSe si e' sicuri che il formato dell'immagine\n\rsia .png, assicurarsi che tale estensione\n\rsegua il nome dell'immagine nella dichiarazione\n\rdel percorso.", "Errore!", JOptionPane.ERROR_MESSAGE);}					
					else
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nessuna immagine trovata.", "Errore!", JOptionPane.ERROR_MESSAGE);					
				}
			});
			{
				JScrollPane defaultImageDirectoryScrollPane = new JScrollPane();
				GridBagConstraints gbc_defaultImageDirectoryScrollPane = new GridBagConstraints();
				gbc_defaultImageDirectoryScrollPane.insets = new Insets(0, 0, 5, 5);
				gbc_defaultImageDirectoryScrollPane.fill = GridBagConstraints.BOTH;
				gbc_defaultImageDirectoryScrollPane.gridx = 1;
				gbc_defaultImageDirectoryScrollPane.gridy = 4;
				contentPanel.add(defaultImageDirectoryScrollPane, gbc_defaultImageDirectoryScrollPane);
				{
					defaultImageDirectoryField = new JTextField();
					defaultImageDirectoryScrollPane.setViewportView(defaultImageDirectoryField);
					defaultImageDirectoryField.setColumns(10);
				}
			}
			GridBagConstraints gbc_DefaultImageButton = new GridBagConstraints();
			gbc_DefaultImageButton.insets = new Insets(0, 0, 5, 0);
			gbc_DefaultImageButton.gridx = 2;
			gbc_DefaultImageButton.gridy = 4;
			contentPanel.add(DefaultImageButton, gbc_DefaultImageButton);
		}
		{
			JLabel lblRimuoviAttuale = new JLabel("Rimuovi Attuale :");
			GridBagConstraints gbc_lblRimuoviAttuale = new GridBagConstraints();
			gbc_lblRimuoviAttuale.insets = new Insets(0, 0, 0, 5);
			gbc_lblRimuoviAttuale.gridx = 0;
			gbc_lblRimuoviAttuale.gridy = 5;
			contentPanel.add(lblRimuoviAttuale, gbc_lblRimuoviAttuale);
		}
		{
			JButton removeDefaultImage = new JButton("Rimuovi");
			GridBagConstraints gbc_removeDefaultImage = new GridBagConstraints();
			gbc_removeDefaultImage.fill = GridBagConstraints.HORIZONTAL;
			gbc_removeDefaultImage.insets = new Insets(0, 0, 0, 5);
			gbc_removeDefaultImage.gridx = 1;
			gbc_removeDefaultImage.gridy = 5;
			contentPanel.add(removeDefaultImage, gbc_removeDefaultImage);
		}
	}

}
