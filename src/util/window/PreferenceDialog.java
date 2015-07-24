package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.AnimeIndex;
import util.ChooserFileView;
import util.FileManager;
import util.ImageChooserFilter;

public class PreferenceDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	public static final ButtonGroup listGroup = new ButtonGroup();
	private JCheckBox rdbtnLastList;
	private JCheckBox rdbtnChooseList;
	private JComboBox choosedList;

	/**
	 * Create the dialog.
	 */
	public PreferenceDialog()
	{
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 247);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 205, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
			JLabel imgDimension = new JLabel("       L'immagine deve avere dimensioni :                225 x 310 pixel.");
			GridBagConstraints gbc_imgDimension = new GridBagConstraints();
			gbc_imgDimension.anchor = GridBagConstraints.WEST;
			gbc_imgDimension.insets = new Insets(0, 0, 5, 0);
			gbc_imgDimension.gridwidth = 3;
			gbc_imgDimension.gridx = 0;
			gbc_imgDimension.gridy = 4;
			contentPanel.add(imgDimension, gbc_imgDimension);
		}
		{
			JLabel lblPercorsoFile = new JLabel(" Immagine :");
			GridBagConstraints gbc_lblPercorsoFile = new GridBagConstraints();
			gbc_lblPercorsoFile.insets = new Insets(0, 0, 5, 5);
			gbc_lblPercorsoFile.gridx = 0;
			gbc_lblPercorsoFile.gridy = 5;
			contentPanel.add(lblPercorsoFile, gbc_lblPercorsoFile);
		}
		{
			JButton DefaultImageButton = new JButton("Sfoglia");
			DefaultImageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File chooserDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
					JFileChooser fc = new JFileChooser(chooserDir);
					fc.setMultiSelectionEnabled(false);
					fc.addChoosableFileFilter(new ImageChooserFilter());
					fc.setAcceptAllFileFilterUsed(false);
					fc.setFileView(new ChooserFileView());
					int returnVal = fc.showDialog(AnimeIndex.mainFrame, "Imposta");
					
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						File file = fc.getSelectedFile();
						String dir = file.getPath();
						try {
							BufferedImage bufimg = ImageIO.read (file);
							FileManager.saveDefaultImage(dir, "default");
							JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Impostazione avvenuta correttamente.", "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
							AnimeIndex.animeInformation.setBlank();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}
				}
			});
			GridBagConstraints gbc_DefaultImageButton = new GridBagConstraints();
			gbc_DefaultImageButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_DefaultImageButton.insets = new Insets(0, 0, 5, 5);
			gbc_DefaultImageButton.gridx = 1;
			gbc_DefaultImageButton.gridy = 5;
			contentPanel.add(DefaultImageButton, gbc_DefaultImageButton);
		}
		{
			JLabel lblRimuoviAttuale = new JLabel("Rimuovi Attuale :");
			GridBagConstraints gbc_lblRimuoviAttuale = new GridBagConstraints();
			gbc_lblRimuoviAttuale.insets = new Insets(0, 0, 0, 5);
			gbc_lblRimuoviAttuale.gridx = 0;
			gbc_lblRimuoviAttuale.gridy = 6;
			contentPanel.add(lblRimuoviAttuale, gbc_lblRimuoviAttuale);
		}
		{
			JButton removeDefaultImage = new JButton("Rimuovi");
			removeDefaultImage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File img = new File(FileManager.getDefaultImageFolderPath());
					if(img.isDirectory()==false)
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nessuna immagine iniziale trovata.", "Errore!", JOptionPane.ERROR_MESSAGE);
					else{
					int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainFrame, "L'immagine iniziale attuale sara' eliminata.\n\rL'operazione non potra' essere annullata.", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(shouldCancel==0){
					try{
						FileManager.deleteData(img);
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Immagine iniziale rimossa.", "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
						if(AnimeIndex.getJList().isSelectionEmpty())
							AnimeIndex.animeInformation.animeImage.setIcon(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("image/default.png"))));						
					}catch(IOException e1)
					{	e1.getStackTrace();}}}
				}
			});
			GridBagConstraints gbc_removeDefaultImage = new GridBagConstraints();
			gbc_removeDefaultImage.fill = GridBagConstraints.HORIZONTAL;
			gbc_removeDefaultImage.insets = new Insets(0, 0, 0, 5);
			gbc_removeDefaultImage.gridx = 1;
			gbc_removeDefaultImage.gridy = 6;
			contentPanel.add(removeDefaultImage, gbc_removeDefaultImage);
		}
	}

}
