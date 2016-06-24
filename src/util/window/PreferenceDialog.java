package util.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;

import main.AnimeIndex;
import util.EpisodeChooserFilter;
import util.FileManager;
import util.MAMUtil;

import java.awt.GridLayout;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JSlider;

public class PreferenceDialog extends JDialog
{
	public final ButtonGroup listGroup = new ButtonGroup();
	public JButton dataCheckButton;
	public SetExclusionDialog exclusionDialog;
	public PatternExceptionDialog patternExceptionDialog;
	private JCheckBox rdbtnLastList;
	private JCheckBox rdbtnChooseList;
	private JCheckBox chckbxDailyRelease;
	private JComboBox choosedList;
	private JCheckBox chckbxApriWishlist;
	private JCheckBox chckbxDroplist;
	private JCheckBox chckbxApriNewsboard;
	private JLabel lblIntroVolume;
	private JSlider introVolume;
	private JButton btnEsporta;
	private JButton btnCancella;
	private JLabel logLabel;
	private JTextField generalPatternTextField;
	private JTextField specialPatternTextField;
	private JTextField mainFolderTextField;
	
	
	public PreferenceDialog()
	{
		super(AnimeIndex.frame, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PreferenceDialog.class.getResource("/image/System-Preferences-icon.png")));
		setTitle("Impostazioni");
		setResizable(false);
		setBounds(100, 100, 335, 390);
		BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		
		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton cancelButton = new JButton("Annulla");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JButton but = (JButton) e.getSource();
				JDialog dialog = (JDialog) but.getTopLevelAncestor();
				dialog.dispose();
			}
		});
		
		JButton okButton = new JButton("Salva");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				AnimeIndex.appProp.setProperty("Mastah",((float)(introVolume.getValue())/100)+"");
				
				if (rdbtnLastList.isSelected())
				{
					AnimeIndex.appProp.setProperty("List_to_visualize_at_start", "Last list");
					AnimeIndex.appProp.setProperty("Last_list", MAMUtil.getList());
				}
				else if (rdbtnChooseList.isSelected())
				{
					String list = (String) choosedList.getSelectedItem();
					AnimeIndex.appProp.setProperty("List_to_visualize_at_start", list);
					AnimeIndex.appProp.setProperty("Last_list", "---");
				}
				else
				{
					AnimeIndex.appProp.setProperty("List_to_visualize_at_start", "Daily");
					AnimeIndex.appProp.setProperty("Last_list", "---");
				}
				
				if (chckbxApriWishlist.isSelected())
					AnimeIndex.appProp.setProperty("Open_Wishlist", "true");
				else
					AnimeIndex.appProp.setProperty("Open_Wishlist", "false");
				
				if(chckbxDroplist.isSelected())
					AnimeIndex.appProp.setProperty("Open_Droplist", "true");
				else
					AnimeIndex.appProp.setProperty("Open_Droplist", "false");
					
				if (chckbxApriNewsboard.isSelected())
					AnimeIndex.appProp.setProperty("Open_NewsBoard", "true");
				else
					AnimeIndex.appProp.setProperty("Open_NewsBoard", "false");
				
				 String generalPattern = generalPatternTextField.getText();
				 AnimeIndex.appProp.setProperty("General_Pattern", generalPattern);
				 
				 String specialPattern = specialPatternTextField.getText();
				 AnimeIndex.appProp.setProperty("Special_Pattern", specialPattern);
				 
				 String mainFolder = mainFolderTextField.getText();
				 File mainFolderFile = new File(mainFolder);
				 if (mainFolderFile.isDirectory() || mainFolder.isEmpty())
					 AnimeIndex.appProp.setProperty("Main_Folder", mainFolder);
				 else
				 {
					 JOptionPane.showMessageDialog(PreferenceDialog.this, "La cartella principale non esiste.", "Errore!", JOptionPane.ERROR_MESSAGE);
				 }
				 
				JButton but = (JButton) e.getSource();
				JDialog dialog = (JDialog) but.getTopLevelAncestor();
				dialog.dispose();
			}
		});
		buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		buttonPane.add(cancelButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
			
		JPanel generalSettingPane = new JPanel();
		tabbedPane.addTab("Impostazioni Generali", null, generalSettingPane, null);
		GridBagLayout gbl_generalSettingPane = new GridBagLayout();
		gbl_generalSettingPane.columnWidths = new int[] { 82, 0, 0 };
		gbl_generalSettingPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_generalSettingPane.columnWeights = new double[] { 1.0, 0.0, 0.0 };
		gbl_generalSettingPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		generalSettingPane.setLayout(gbl_generalSettingPane);
			
		dataCheckButton = new JButton("Disattiva");
		dataCheckButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("true"))
				{
					AnimeIndex.appProp.setProperty("Update_system", "false");
					JOptionPane.showMessageDialog(AnimeIndex.mainPanel, "Controllo Dati Automatico:    DISATTIVATO", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
					dataCheckButton.setText("Attiva");
					AnimeIndex.animeInformation.checkDataButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/refresh-icon15.png")));
				}
				else
				{
					int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainPanel, "Il controllo utilizza Internet.\n\rAssicurarsi che la rete sia disponibile.\n\rContinuare?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (shouldCancel == 0)
					{
						AnimeIndex.appProp.setProperty("Update_system", "true");
						dataCheckButton.setText("Disattiva");
						AnimeIndex.animeInformation.checkDataButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/autorefresh-icon15.png")));
					}
				}
			}
		});
		
		JLabel lblListToVisualize = new JLabel("Lista da visualizzare all'avvio :");
		GridBagConstraints gbc_lblListToVisualize = new GridBagConstraints();
		gbc_lblListToVisualize.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblListToVisualize.insets = new Insets(0, 0, 5, 5);
		gbc_lblListToVisualize.gridx = 0;
		gbc_lblListToVisualize.gridy = 0;
		generalSettingPane.add(lblListToVisualize, gbc_lblListToVisualize);
		
		chckbxDailyRelease = new JCheckBox("Uscite del Giorno");
		chckbxDailyRelease.setToolTipText("Visualizza le uscite del giorno applicando automaticamente il filtro alle liste");
		chckbxDailyRelease.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				choosedList.setEnabled(false);
			}
		});
		GridBagConstraints gbc_chckbxDailyRelease = new GridBagConstraints();
		gbc_chckbxDailyRelease.anchor = GridBagConstraints.WEST;
		gbc_chckbxDailyRelease.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxDailyRelease.gridx = 0;
		gbc_chckbxDailyRelease.gridy = 1;
		generalSettingPane.add(chckbxDailyRelease, gbc_chckbxDailyRelease);
		
		listGroup.add(chckbxDailyRelease);
		
		chckbxApriWishlist = new JCheckBox("Wishlist");
		chckbxApriWishlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxDroplist.isSelected())
					chckbxDroplist.setSelected(false);
			}
		});
		GridBagConstraints gbc_chckbxApriWishlist = new GridBagConstraints();
		gbc_chckbxApriWishlist.anchor = GridBagConstraints.WEST;
		gbc_chckbxApriWishlist.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxApriWishlist.gridx = 1;
		gbc_chckbxApriWishlist.gridy = 1;
		generalSettingPane.add(chckbxApriWishlist, gbc_chckbxApriWishlist);
		
		rdbtnLastList = new JCheckBox("Ultima Lista Visualizzata");
		rdbtnLastList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				choosedList.setEnabled(false);
			}
		});
		
		chckbxDroplist = new JCheckBox("DropList");
		chckbxDroplist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxApriWishlist.isSelected())
					chckbxApriWishlist.setSelected(false);
			}
		});
		GridBagConstraints gbc_chckbxDroplist = new GridBagConstraints();
		gbc_chckbxDroplist.anchor = GridBagConstraints.WEST;
		gbc_chckbxDroplist.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxDroplist.gridx = 2;
		gbc_chckbxDroplist.gridy = 1;
		generalSettingPane.add(chckbxDroplist, gbc_chckbxDroplist);
		
		GridBagConstraints gbc_rdbtnLastList = new GridBagConstraints();
		gbc_rdbtnLastList.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnLastList.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLastList.gridx = 0;
		gbc_rdbtnLastList.gridy = 2;
		generalSettingPane.add(rdbtnLastList, gbc_rdbtnLastList);
		
		listGroup.add(rdbtnLastList);
		
		chckbxApriNewsboard = new JCheckBox("Apri NewsBoard");
		GridBagConstraints gbc_chckbxApriNewsboard = new GridBagConstraints();
		gbc_chckbxApriNewsboard.anchor = GridBagConstraints.WEST;
		gbc_chckbxApriNewsboard.gridwidth = 2;
		gbc_chckbxApriNewsboard.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxApriNewsboard.gridx = 1;
		gbc_chckbxApriNewsboard.gridy = 2;
		generalSettingPane.add(chckbxApriNewsboard, gbc_chckbxApriNewsboard);
		
		rdbtnChooseList = new JCheckBox("Scegli Lista :");
		rdbtnChooseList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				choosedList.setEnabled(true);
			}
		});
		
		GridBagConstraints gbc_rdbtnChooseList = new GridBagConstraints();
		gbc_rdbtnChooseList.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnChooseList.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnChooseList.gridx = 0;
		gbc_rdbtnChooseList.gridy = 3;
		generalSettingPane.add(rdbtnChooseList, gbc_rdbtnChooseList);
		
		listGroup.add(rdbtnChooseList);
			
		choosedList = new JComboBox();
		GridBagConstraints gbc_choosedList = new GridBagConstraints();
		gbc_choosedList.gridwidth = 2;
		gbc_choosedList.insets = new Insets(0, 0, 5, 0);
		gbc_choosedList.fill = GridBagConstraints.HORIZONTAL;
		gbc_choosedList.gridx = 1;
		gbc_choosedList.gridy = 3;
		generalSettingPane.add(choosedList, gbc_choosedList);
		choosedList.setModel(new DefaultComboBoxModel(new String[] { "Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere", "Uscite del Giorno" }));
		
		JSeparator separator5 = new JSeparator();
		GridBagConstraints gbc_separator5 = new GridBagConstraints();
		gbc_separator5.weighty = 0.1;
		gbc_separator5.insets = new Insets(0, 0, 5, 0);
		gbc_separator5.fill = GridBagConstraints.BOTH;
		gbc_separator5.gridwidth = 3;
		gbc_separator5.gridx = 0;
		gbc_separator5.gridy = 4;
		generalSettingPane.add(separator5, gbc_separator5);
		
		JLabel dataControlLabel = new JLabel("Controllo Dati Automatico :");
		GridBagConstraints gbc_dataControlLabel = new GridBagConstraints();
		gbc_dataControlLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataControlLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dataControlLabel.gridx = 0;
		gbc_dataControlLabel.gridy = 5;
		generalSettingPane.add(dataControlLabel, gbc_dataControlLabel);
		GridBagConstraints gbc_dataCheckButton = new GridBagConstraints();
		gbc_dataCheckButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataCheckButton.insets = new Insets(0, 0, 5, 5);
		gbc_dataCheckButton.gridx = 1;
		gbc_dataCheckButton.gridy = 5;
		generalSettingPane.add(dataCheckButton, gbc_dataCheckButton);
			
		JButton btnEsclusioni = new JButton("Esclusioni");
		btnEsclusioni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				exclusionDialog = new SetExclusionDialog();
				exclusionDialog.setLocationRelativeTo(AnimeIndex.mainPanel);
				exclusionDialog.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnEsclusioni = new GridBagConstraints();
		gbc_btnEsclusioni.insets = new Insets(0, 0, 5, 0);
		gbc_btnEsclusioni.gridx = 2;
		gbc_btnEsclusioni.gridy = 5;
		generalSettingPane.add(btnEsclusioni, gbc_btnEsclusioni);		
		
		JSeparator separator3 = new JSeparator();
		GridBagConstraints gbc_separator3 = new GridBagConstraints();
		gbc_separator3.weighty = 0.1;
		gbc_separator3.insets = new Insets(0, 0, 5, 0);
		gbc_separator3.fill = GridBagConstraints.BOTH;
		gbc_separator3.gridwidth = 3;
		gbc_separator3.gridx = 0;
		gbc_separator3.gridy = 6;
		generalSettingPane.add(separator3, gbc_separator3);
			
		JLabel aspectLabel = new JLabel("Aspetto :");
		GridBagConstraints gbc_aspectLabel = new GridBagConstraints();
		gbc_aspectLabel.fill = GridBagConstraints.BOTH;
		gbc_aspectLabel.insets = new Insets(0, 0, 5, 5);
		gbc_aspectLabel.gridx = 0;
		gbc_aspectLabel.gridy = 7;
		generalSettingPane.add(aspectLabel, gbc_aspectLabel);
				
		JLabel colorLabel = new JLabel("               \u2022 Colori :");
		GridBagConstraints gbc_colorLabel = new GridBagConstraints();
		gbc_colorLabel.anchor = GridBagConstraints.WEST;
		gbc_colorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_colorLabel.gridx = 0;
		gbc_colorLabel.gridy = 8;
		generalSettingPane.add(colorLabel, gbc_colorLabel);
				
		JButton btnColor = new JButton("Personalizza");
		btnColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ColorDialog dial = new ColorDialog();
				dial.setLocationRelativeTo(AnimeIndex.mainPanel);
				dial.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnColor = new GridBagConstraints();
		gbc_btnColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnColor.gridwidth = 2;
		gbc_btnColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnColor.gridx = 1;
		gbc_btnColor.gridy = 8;
		generalSettingPane.add(btnColor, gbc_btnColor);
				
		JLabel startImageLabel = new JLabel("               \u2022 Immagine Iniziale :");
		GridBagConstraints gbc_startImageLabel = new GridBagConstraints();
		gbc_startImageLabel.anchor = GridBagConstraints.WEST;
		gbc_startImageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startImageLabel.gridx = 0;
		gbc_startImageLabel.gridy = 9;
		generalSettingPane.add(startImageLabel, gbc_startImageLabel);
			
		JButton defaultImageButton = new JButton("Sfoglia");
		defaultImageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File chooserDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
				JFileChooser fc = new JFileChooser(chooserDir);
				fc.setMultiSelectionEnabled(false);
				fc.addChoosableFileFilter(new EpisodeChooserFilter());
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showDialog(AnimeIndex.mainPanel, "Imposta");
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					String dir = file.getPath();
					
					FileManager.saveDefaultScaledImage(dir, "default");
					JOptionPane.showMessageDialog(AnimeIndex.mainPanel, "Impostazione avvenuta correttamente.", "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
					AnimeIndex.animeInformation.setBlank();	
				}				
			}
		});
		GridBagConstraints gbc_defaultImageButton = new GridBagConstraints();
		gbc_defaultImageButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_defaultImageButton.insets = new Insets(0, 0, 5, 5);
		gbc_defaultImageButton.gridx = 1;
		gbc_defaultImageButton.gridy = 9;
		generalSettingPane.add(defaultImageButton, gbc_defaultImageButton);
			
		JButton removeDefaultImage = new JButton("Rimuovi");
		removeDefaultImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File img = new File(MAMUtil.getDefaultImageFolderPath());
				if (img.isDirectory() == false)
					JOptionPane.showMessageDialog(AnimeIndex.mainPanel, "Nessuna immagine iniziale trovata.", "Errore!", JOptionPane.ERROR_MESSAGE);
				else
				{
					int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainPanel, "L'immagine iniziale impostata dall'utente sara' eliminata.\n\rL'operazione non potra' essere annullata.", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (shouldCancel == 0)
						try
					{
							FileManager.deleteData(img);
							JOptionPane.showMessageDialog(AnimeIndex.mainPanel, "Immagine iniziale rimossa.", "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
							if (MAMUtil.getJList().isSelectionEmpty())
								AnimeIndex.animeInformation.animeImage.setIcon(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("image/default.png"))));
					}
					catch (IOException e1)
					{
						e1.getStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_removeDefaultImage = new GridBagConstraints();
		gbc_removeDefaultImage.fill = GridBagConstraints.HORIZONTAL;
		gbc_removeDefaultImage.insets = new Insets(0, 0, 5, 0);
		gbc_removeDefaultImage.gridx = 2;
		gbc_removeDefaultImage.gridy = 9;
		generalSettingPane.add(removeDefaultImage, gbc_removeDefaultImage);
			
		JSeparator separator4 = new JSeparator();
		GridBagConstraints gbc_separator4 = new GridBagConstraints();
		gbc_separator4.weighty = 0.1;
		gbc_separator4.insets = new Insets(0, 0, 5, 0);
		gbc_separator4.fill = GridBagConstraints.BOTH;
		gbc_separator4.gridwidth = 3;
		gbc_separator4.gridx = 0;
		gbc_separator4.gridy = 10;
		generalSettingPane.add(separator4, gbc_separator4);
			
		btnEsporta = new JButton("Esporta");
		btnEsporta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File folder = new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "log" + File.separator);
				File[] fileList = folder.listFiles();
				if (fileList.length == 0)
					JOptionPane.showMessageDialog(PreferenceDialog.this, "Nessun file di log esistente", "Errore", JOptionPane.WARNING_MESSAGE);
				else
				{
					Object file = JOptionPane.showInputDialog(PreferenceDialog.this, "Quale file vuoi esportare?", "Esportazione", JOptionPane.QUESTION_MESSAGE, null, fileList, fileList[0]);
					if (file != null && file instanceof File)
					{
						File choosedFile = (File) file;
						JFileChooser chooser = new JFileChooser();
						chooser.setMultiSelectionEnabled(false);
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						chooser.setDialogTitle("Esporta in...");
						
						int returnVal = chooser.showDialog(AnimeIndex.mainPanel, "Esporta");
						
						if (returnVal == JFileChooser.APPROVE_OPTION)
						{
							File destination = chooser.getSelectedFile();
							try
							{
								FileUtils.copyFileToDirectory(choosedFile, destination);
							}
							catch (IOException e1)
							{
								MAMUtil.writeLog(e1);
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		introVolume = new JSlider();
		introVolume.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblIntroVolume.setText("Volume Intro :   "+introVolume.getValue()+"%");
			}
		});
		introVolume.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblIntroVolume.setText("Volume Intro :   "+introVolume.getValue()+"%");
			}
		});
		introVolume.setPaintTicks(true);
		introVolume.setMajorTickSpacing(10);
		introVolume.setPreferredSize(new Dimension(210, 26));
		introVolume.setRequestFocusEnabled(false);
		introVolume.setFocusable(false);
		introVolume.setValue(0);
		GridBagConstraints gbc_introVolume = new GridBagConstraints();
		gbc_introVolume.fill = GridBagConstraints.BOTH;
		gbc_introVolume.gridwidth = 2;
		gbc_introVolume.insets = new Insets(0, 0, 5, 5);
		gbc_introVolume.gridx = 1;
		gbc_introVolume.gridy = 11;
		generalSettingPane.add(introVolume, gbc_introVolume);
		
		lblIntroVolume = new JLabel("Volume Intro :   "+(int)(Float.parseFloat(AnimeIndex.appProp.getProperty("Mastah"))*100)+"%");
		GridBagConstraints gbc_lblIntroVolume = new GridBagConstraints();
		gbc_lblIntroVolume.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblIntroVolume.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntroVolume.gridx = 0;
		gbc_lblIntroVolume.gridy = 11;
		generalSettingPane.add(lblIntroVolume, gbc_lblIntroVolume);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.weighty = 0.1;
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 3;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 12;
		generalSettingPane.add(separator, gbc_separator);
		GridBagConstraints gbc_btnEsporta = new GridBagConstraints();
		gbc_btnEsporta.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEsporta.insets = new Insets(0, 0, 0, 5);
		gbc_btnEsporta.gridx = 1;
		gbc_btnEsporta.gridy = 13;
		generalSettingPane.add(btnEsporta, gbc_btnEsporta);
				
		btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int choiche = JOptionPane.showConfirmDialog(PreferenceDialog.this, "Vuoi rimuovere tutti i file di log?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (choiche == JOptionPane.YES_OPTION)
				try
				{
						FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "log" + File.separator));
						btnCancella.setEnabled(false);
						btnEsporta.setEnabled(false);
						logLabel.setText("File di Log : (0Kb)");
				}
				catch (IOException e1)
				{
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnCancella = new GridBagConstraints();
		gbc_btnCancella.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancella.gridx = 2;
		gbc_btnCancella.gridy = 13;
		generalSettingPane.add(btnCancella, gbc_btnCancella);
		
		File folder = new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "log" + File.separator);
		long space = getFolderSize(folder) / 1024;
		if (space == 0)
		{
			btnEsporta.setEnabled(false);
			btnCancella.setEnabled(false);
		}
		logLabel = new JLabel("File di Log : (" + space + "Kb)");
		GridBagConstraints gbc_logLabel = new GridBagConstraints();
		gbc_logLabel.anchor = GridBagConstraints.WEST;
		gbc_logLabel.insets = new Insets(0, 0, 0, 5);
		gbc_logLabel.gridx = 0;
		gbc_logLabel.gridy = 13;
		generalSettingPane.add(logLabel, gbc_logLabel);
		generalSettingPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel episodeSettingPane = new JPanel();
		tabbedPane.addTab("Impostazioni Episodi", null, episodeSettingPane, null);		
		GridBagLayout gbl_episodeSettingPane = new GridBagLayout();
		gbl_episodeSettingPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_episodeSettingPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_episodeSettingPane.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_episodeSettingPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		episodeSettingPane.setLayout(gbl_episodeSettingPane);
		
		JLabel lblPatternGenerale = new JLabel("Pattern Generale : ");
		GridBagConstraints gbc_lblPatternGenerale = new GridBagConstraints();
		gbc_lblPatternGenerale.fill = GridBagConstraints.BOTH;
		gbc_lblPatternGenerale.insets = new Insets(0, 0, 5, 5);
		gbc_lblPatternGenerale.gridx = 0;
		gbc_lblPatternGenerale.gridy = 0;
		episodeSettingPane.add(lblPatternGenerale, gbc_lblPatternGenerale);
		
		generalPatternTextField = new JTextField();
		generalPatternTextField.setText(AnimeIndex.appProp.getProperty("General_Pattern"));
		GridBagConstraints gbc_generalPatternTextField = new GridBagConstraints();
		gbc_generalPatternTextField.gridwidth = 2;
		gbc_generalPatternTextField.insets = new Insets(0, 0, 5, 5);
		gbc_generalPatternTextField.fill = GridBagConstraints.BOTH;
		gbc_generalPatternTextField.gridx = 1;
		gbc_generalPatternTextField.gridy = 0;
		episodeSettingPane.add(generalPatternTextField, gbc_generalPatternTextField);
		generalPatternTextField.setColumns(10);
		
		JLabel lblPatternSpeciali = new JLabel("Pattern Speciali : ");
		GridBagConstraints gbc_lblPatternSpeciali = new GridBagConstraints();
		gbc_lblPatternSpeciali.fill = GridBagConstraints.BOTH;
		gbc_lblPatternSpeciali.insets = new Insets(0, 0, 5, 5);
		gbc_lblPatternSpeciali.gridx = 0;
		gbc_lblPatternSpeciali.gridy = 1;
		episodeSettingPane.add(lblPatternSpeciali, gbc_lblPatternSpeciali);
		
		specialPatternTextField = new JTextField();
		specialPatternTextField.setText(AnimeIndex.appProp.getProperty("Special_Pattern"));
		GridBagConstraints gbc_specialPatternTextField = new GridBagConstraints();
		gbc_specialPatternTextField.gridwidth = 2;
		gbc_specialPatternTextField.insets = new Insets(0, 0, 5, 5);
		gbc_specialPatternTextField.fill = GridBagConstraints.BOTH;
		gbc_specialPatternTextField.gridx = 1;
		gbc_specialPatternTextField.gridy = 1;
		episodeSettingPane.add(specialPatternTextField, gbc_specialPatternTextField);
		specialPatternTextField.setColumns(10);
		
		JButton btnEccezzioni = new JButton("Eccezzioni");
		btnEccezzioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				patternExceptionDialog = new PatternExceptionDialog();
				patternExceptionDialog.setLocationRelativeTo(PreferenceDialog.this);
				patternExceptionDialog.setVisible(true);
			}
		});
		
		JLabel lblPatternExplication = new JLabel("%N% = nome anime\r\n %E% = numero episodio");
		GridBagConstraints gbc_lblPatternExplication = new GridBagConstraints();
		gbc_lblPatternExplication.insets = new Insets(0, 0, 5, 5);
		gbc_lblPatternExplication.gridwidth = 3;
		gbc_lblPatternExplication.gridx = 0;
		gbc_lblPatternExplication.gridy = 2;
		episodeSettingPane.add(lblPatternExplication, gbc_lblPatternExplication);
		
		JLabel lblCartellaPrincipale = new JLabel("Cartella Principale");
		GridBagConstraints gbc_lblCartellaPrincipale = new GridBagConstraints();
		gbc_lblCartellaPrincipale.fill = GridBagConstraints.BOTH;
		gbc_lblCartellaPrincipale.insets = new Insets(0, 0, 5, 5);
		gbc_lblCartellaPrincipale.gridx = 0;
		gbc_lblCartellaPrincipale.gridy = 3;
		episodeSettingPane.add(lblCartellaPrincipale, gbc_lblCartellaPrincipale);
		
		mainFolderTextField = new JTextField();
		mainFolderTextField.setText(AnimeIndex.appProp.getProperty("Main_Folder"));
		GridBagConstraints gbc_mainFolderTextField = new GridBagConstraints();
		gbc_mainFolderTextField.insets = new Insets(0, 0, 5, 5);
		gbc_mainFolderTextField.fill = GridBagConstraints.BOTH;
		gbc_mainFolderTextField.gridx = 1;
		gbc_mainFolderTextField.gridy = 3;
		episodeSettingPane.add(mainFolderTextField, gbc_mainFolderTextField);
		mainFolderTextField.setColumns(10);
		
		JButton btnChooseFolder = new JButton("...");
		btnChooseFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File chooserDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
				JFileChooser fc = new JFileChooser(chooserDir);
				fc.setMultiSelectionEnabled(false);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showDialog(AnimeIndex.mainPanel, "Imposta");
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					mainFolderTextField.setText(fc.getSelectedFile().getPath());
				}
			}
		});
		btnChooseFolder.setPreferredSize(new Dimension(25, 23));
		GridBagConstraints gbc_btnChooseFolder = new GridBagConstraints();
		gbc_btnChooseFolder.insets = new Insets(0, 0, 5, 0);
		gbc_btnChooseFolder.gridx = 2;
		gbc_btnChooseFolder.gridy = 3;
		episodeSettingPane.add(btnChooseFolder, gbc_btnChooseFolder);
		
		GridBagConstraints gbc_btnEccezzioni = new GridBagConstraints();
		gbc_btnEccezzioni.fill = GridBagConstraints.VERTICAL;
		gbc_btnEccezzioni.gridwidth = 3;
		gbc_btnEccezzioni.insets = new Insets(0, 0, 0, 5);
		gbc_btnEccezzioni.gridx = 0;
		gbc_btnEccezzioni.gridy = 4;
		episodeSettingPane.add(btnEccezzioni, gbc_btnEccezzioni);
		
		String listPreference = AnimeIndex.appProp.getProperty("List_to_visualize_at_start");
		if (listPreference.equalsIgnoreCase("last list"))
		{
			rdbtnLastList.setSelected(true);
			choosedList.setEnabled(false);
			choosedList.setSelectedItem(AnimeIndex.appProp.getProperty("Last_list"));
		}
		else if (listPreference.equalsIgnoreCase("Daily"))
		{
			chckbxDailyRelease.setSelected(true);
			choosedList.setEnabled(false);
			choosedList.setSelectedItem("Anime in Corso");
		}
		else
			rdbtnChooseList.setSelected(true);
		if (AnimeIndex.appProp.getProperty("Update_system").equals("true"))
			dataCheckButton.setText("Disattiva");
		else
			dataCheckButton.setText("Attiva");
			
		if (AnimeIndex.appProp.getProperty("Open_Wishlist").equals("true"))
			chckbxApriWishlist.setSelected(true);
		else
			chckbxApriWishlist.setSelected(false);
			
		if (AnimeIndex.appProp.getProperty("Open_NewsBoard").equals("true"))
			chckbxApriNewsboard.setSelected(true);
		else
			chckbxApriNewsboard.setSelected(false);
		if(AnimeIndex.appProp.getProperty("Open_Droplist").equals("true"))
			chckbxDroplist.setSelected(true);
		else
			chckbxDroplist.setSelected(false);
		
		introVolume.setValue((int)(Float.parseFloat(AnimeIndex.appProp.getProperty("Mastah"))*100));
	}
	
	private long getFolderSize(File folder)
	{
		long size = 0;
		if (folder.isDirectory())
			if (folder.list().length > 0)
			{
				File[] files = folder.listFiles();
				
				for (File file : files)
					size += file.length();
			}
		return size;
	}
}
