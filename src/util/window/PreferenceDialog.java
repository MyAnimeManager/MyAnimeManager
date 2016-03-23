package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

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
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;

import main.AnimeIndex;
import util.FileManager;
import util.ImageChooserFilter;
import util.ImportExportFileFilter;
import util.MAMUtil;
import util.task.BackupImportExportTask;

public class PreferenceDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	public final ButtonGroup listGroup = new ButtonGroup();
	private JCheckBox rdbtnLastList;
	private JCheckBox rdbtnChooseList;
	private JCheckBox chckbxDailyRelease;
	private JComboBox choosedList;
	public JButton dataCheckButton;
	public SetExclusionDialog exclusionDialog;
	private JCheckBox chckbxApriWishlist;
	private JCheckBox chckbxApriNewsboard;
	private JButton btnEsporta;
	private JButton btnCancella;
	private JLabel lblFileDiLog;

	/**
	 * Create the dialog.
	 */
	public PreferenceDialog()
	{
		super(AnimeIndex.frame, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PreferenceDialog.class.getResource("/image/System-Preferences-icon.png")));
		setTitle("Preferenze");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 380, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.EAST);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 197, 90, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblListToVisualize = new JLabel("Lista da visualizzare all'avvio :");
			GridBagConstraints gbc_lblListToVisualize = new GridBagConstraints();
			gbc_lblListToVisualize.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblListToVisualize.insets = new Insets(0, 0, 5, 5);
			gbc_lblListToVisualize.gridx = 0;
			gbc_lblListToVisualize.gridy = 0;
			contentPanel.add(lblListToVisualize, gbc_lblListToVisualize);
		}
		{
			rdbtnChooseList = new JCheckBox("Scegli Lista :");
			rdbtnChooseList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					choosedList.setEnabled(true);
				}
			});
			{
				rdbtnLastList = new JCheckBox("Ultima Lista Visualizzata");
				rdbtnLastList.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						choosedList.setEnabled(false);
					}
				});
				{
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
					contentPanel.add(chckbxDailyRelease, gbc_chckbxDailyRelease);
				}
				listGroup.add(chckbxDailyRelease);
				{
					chckbxApriWishlist = new JCheckBox("Apri Wishlist");
					GridBagConstraints gbc_chckbxApriWishlist = new GridBagConstraints();
					gbc_chckbxApriWishlist.anchor = GridBagConstraints.WEST;
					gbc_chckbxApriWishlist.gridwidth = 2;
					gbc_chckbxApriWishlist.insets = new Insets(0, 0, 5, 0);
					gbc_chckbxApriWishlist.gridx = 1;
					gbc_chckbxApriWishlist.gridy = 1;
					contentPanel.add(chckbxApriWishlist, gbc_chckbxApriWishlist);
				}
				GridBagConstraints gbc_rdbtnLastList = new GridBagConstraints();
				gbc_rdbtnLastList.fill = GridBagConstraints.HORIZONTAL;
				gbc_rdbtnLastList.insets = new Insets(0, 0, 5, 5);
				gbc_rdbtnLastList.gridx = 0;
				gbc_rdbtnLastList.gridy = 2;
				contentPanel.add(rdbtnLastList, gbc_rdbtnLastList);
			}
			listGroup.add(rdbtnLastList);
			{
				chckbxApriNewsboard = new JCheckBox("Apri NewsBoard");
				GridBagConstraints gbc_chckbxApriNewsboard = new GridBagConstraints();
				gbc_chckbxApriNewsboard.anchor = GridBagConstraints.WEST;
				gbc_chckbxApriNewsboard.gridwidth = 2;
				gbc_chckbxApriNewsboard.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxApriNewsboard.gridx = 1;
				gbc_chckbxApriNewsboard.gridy = 2;
				contentPanel.add(chckbxApriNewsboard, gbc_chckbxApriNewsboard);
			}
			GridBagConstraints gbc_rdbtnChooseList = new GridBagConstraints();
			gbc_rdbtnChooseList.fill = GridBagConstraints.HORIZONTAL;
			gbc_rdbtnChooseList.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnChooseList.gridx = 0;
			gbc_rdbtnChooseList.gridy = 3;
			contentPanel.add(rdbtnChooseList, gbc_rdbtnChooseList);
		}
		{
			choosedList = new JComboBox();
			GridBagConstraints gbc_choosedList = new GridBagConstraints();
			gbc_choosedList.gridwidth = 2;
			gbc_choosedList.insets = new Insets(0, 0, 5, 0);
			gbc_choosedList.fill = GridBagConstraints.HORIZONTAL;
			gbc_choosedList.gridx = 1;
			gbc_choosedList.gridy = 3;
			contentPanel.add(choosedList, gbc_choosedList);
			choosedList.setModel(new DefaultComboBoxModel(new String[] { "Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere", "Uscite del Giorno" }));
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

					@Override
					public void actionPerformed(ActionEvent e)
					{
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

						if (chckbxApriNewsboard.isSelected())
							AnimeIndex.appProp.setProperty("Open_NewsBoard", "true");
						else
							AnimeIndex.appProp.setProperty("Open_NewsBoard", "false");

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

					@Override
					public void actionPerformed(ActionEvent e)
					{
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		listGroup.add(rdbtnChooseList);
		{
			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.fill = GridBagConstraints.BOTH;
			gbc_separator.gridwidth = 3;
			gbc_separator.insets = new Insets(0, 0, 5, 0);
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 4;
			contentPanel.add(separator, gbc_separator);
		}
		{
			JLabel lblControlloDatiAutomatico = new JLabel("Controllo Dati Automatico :");
			GridBagConstraints gbc_lblControlloDatiAutomatico = new GridBagConstraints();
			gbc_lblControlloDatiAutomatico.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblControlloDatiAutomatico.insets = new Insets(0, 0, 5, 5);
			gbc_lblControlloDatiAutomatico.gridx = 0;
			gbc_lblControlloDatiAutomatico.gridy = 5;
			contentPanel.add(lblControlloDatiAutomatico, gbc_lblControlloDatiAutomatico);
		}
		{
			dataCheckButton = new JButton("Disattiva");
			dataCheckButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					if (AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("true"))
					{
						AnimeIndex.appProp.setProperty("Update_system", "false");
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Controllo Dati Automatico:    DISATTIVATO", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
						dataCheckButton.setText("Attiva");
						AnimeIndex.animeInformation.checkDataButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/refresh-icon15.png")));
					}
					else
					{
						int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainFrame, "Il controllo utilizza Internet.\n\rAssicurarsi che la rete sia disponibile.\n\rContinuare?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (shouldCancel == 0)
						{
							AnimeIndex.appProp.setProperty("Update_system", "true");
							dataCheckButton.setText("Disattiva");
							AnimeIndex.animeInformation.checkDataButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/autorefresh-icon15.png")));
						}
					}
				}
			});
			GridBagConstraints gbc_dataCheckButton = new GridBagConstraints();
			gbc_dataCheckButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_dataCheckButton.insets = new Insets(0, 0, 5, 5);
			gbc_dataCheckButton.gridx = 1;
			gbc_dataCheckButton.gridy = 5;
			contentPanel.add(dataCheckButton, gbc_dataCheckButton);
		}
		{
			JButton btnEsclusioni = new JButton("Esclusioni");
			btnEsclusioni.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					exclusionDialog = new SetExclusionDialog();
					exclusionDialog.setLocationRelativeTo(AnimeIndex.mainFrame);
					exclusionDialog.setVisible(true);
				}
			});
			GridBagConstraints gbc_btnEsclusioni = new GridBagConstraints();
			gbc_btnEsclusioni.insets = new Insets(0, 0, 5, 0);
			gbc_btnEsclusioni.gridx = 2;
			gbc_btnEsclusioni.gridy = 5;
			contentPanel.add(btnEsclusioni, gbc_btnEsclusioni);
		}
		{
			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.gridwidth = 3;
			gbc_separator.fill = GridBagConstraints.BOTH;
			gbc_separator.insets = new Insets(0, 0, 5, 0);
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 6;
			contentPanel.add(separator, gbc_separator);
		}
		{
			JLabel lblAspetto = new JLabel("Aspetto :");
			GridBagConstraints gbc_lblAspetto = new GridBagConstraints();
			gbc_lblAspetto.anchor = GridBagConstraints.WEST;
			gbc_lblAspetto.insets = new Insets(0, 0, 5, 5);
			gbc_lblAspetto.gridx = 0;
			gbc_lblAspetto.gridy = 7;
			contentPanel.add(lblAspetto, gbc_lblAspetto);
		}
		{
			{
				JLabel lblColori = new JLabel("               \u2022 Colori :");
				GridBagConstraints gbc_lblColori = new GridBagConstraints();
				gbc_lblColori.anchor = GridBagConstraints.WEST;
				gbc_lblColori.insets = new Insets(0, 0, 5, 5);
				gbc_lblColori.gridx = 0;
				gbc_lblColori.gridy = 8;
				contentPanel.add(lblColori, gbc_lblColori);
			}
		}
		JButton btnColor = new JButton("Personalizza");
		btnColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				ColorDialog dial = new ColorDialog();
				dial.setLocationRelativeTo(AnimeIndex.mainFrame);
				dial.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnColor = new GridBagConstraints();
		gbc_btnColor.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnColor.gridwidth = 2;
		gbc_btnColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnColor.gridx = 1;
		gbc_btnColor.gridy = 8;
		contentPanel.add(btnColor, gbc_btnColor);
		{
			{
				JLabel lblImmagineIniziale = new JLabel("               \u2022 Immagine Iniziale :");
				GridBagConstraints gbc_lblImmagineIniziale = new GridBagConstraints();
				gbc_lblImmagineIniziale.anchor = GridBagConstraints.WEST;
				gbc_lblImmagineIniziale.insets = new Insets(0, 0, 5, 5);
				gbc_lblImmagineIniziale.gridx = 0;
				gbc_lblImmagineIniziale.gridy = 9;
				contentPanel.add(lblImmagineIniziale, gbc_lblImmagineIniziale);
			}
		}
		JButton DefaultImageButton = new JButton("Sfoglia");
		DefaultImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				File chooserDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
				JFileChooser fc = new JFileChooser(chooserDir);
				fc.setMultiSelectionEnabled(false);
				fc.addChoosableFileFilter(new ImageChooserFilter());
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showDialog(AnimeIndex.mainFrame, "Imposta");

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					String dir = file.getPath();

					FileManager.saveDefaultScaledImage(dir, "default");
					JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Impostazione avvenuta correttamente.", "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
					AnimeIndex.animeInformation.setBlank();

				}
			}
		});
		GridBagConstraints gbc_DefaultImageButton = new GridBagConstraints();
		gbc_DefaultImageButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_DefaultImageButton.insets = new Insets(0, 0, 5, 5);
		gbc_DefaultImageButton.gridx = 1;
		gbc_DefaultImageButton.gridy = 9;
		contentPanel.add(DefaultImageButton, gbc_DefaultImageButton);
		{
			JButton removeDefaultImage = new JButton("Rimuovi");
			removeDefaultImage.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					File img = new File(MAMUtil.getDefaultImageFolderPath());
					if (img.isDirectory() == false)
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nessuna immagine iniziale trovata.", "Errore!", JOptionPane.ERROR_MESSAGE);
					else
					{
						int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainFrame, "L'immagine iniziale impostata dall'utente sara' eliminata.\n\rL'operazione non potra' essere annullata.", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (shouldCancel == 0)
							try
							{
								FileManager.deleteData(img);
								JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Immagine iniziale rimossa.", "Eliminazione completata", JOptionPane.INFORMATION_MESSAGE);
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
			contentPanel.add(removeDefaultImage, gbc_removeDefaultImage);
		}
		{
			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.insets = new Insets(0, 0, 5, 0);
			gbc_separator.fill = GridBagConstraints.BOTH;
			gbc_separator.gridwidth = 3;
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 10;
			contentPanel.add(separator, gbc_separator);
		}
		{
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
							JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + File.separator + "Desktop");
							chooser.setMultiSelectionEnabled(false);
							chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							chooser.setDialogTitle("Esporta in...");

							int returnVal = chooser.showDialog(AnimeIndex.mainFrame, "Esporta");

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
			GridBagConstraints gbc_btnEsporta = new GridBagConstraints();
			gbc_btnEsporta.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnEsporta.insets = new Insets(0, 0, 5, 5);
			gbc_btnEsporta.gridx = 1;
			gbc_btnEsporta.gridy = 11;
			contentPanel.add(btnEsporta, gbc_btnEsporta);
		}
		{
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
							lblFileDiLog.setText("File di Log : (0Kb)");
						}
						catch (IOException e1)
						{
							MAMUtil.writeLog(e1);
							e1.printStackTrace();
						}
				}
			});
			GridBagConstraints gbc_btnCancella = new GridBagConstraints();
			gbc_btnCancella.insets = new Insets(0, 0, 5, 0);
			gbc_btnCancella.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnCancella.gridx = 2;
			gbc_btnCancella.gridy = 11;
			contentPanel.add(btnCancella, gbc_btnCancella);
		}
		{
			File folder = new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "log" + File.separator);
			long space = getFolderSize(folder) / 1024;
			lblFileDiLog = new JLabel("File di Log : (" + space + "Kb)");
			GridBagConstraints gbc_lblFileDiLog = new GridBagConstraints();
			gbc_lblFileDiLog.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblFileDiLog.insets = new Insets(0, 0, 5, 5);
			gbc_lblFileDiLog.gridx = 0;
			gbc_lblFileDiLog.gridy = 11;
			contentPanel.add(lblFileDiLog, gbc_lblFileDiLog);
			if (space == 0)
			{
				btnEsporta.setEnabled(false);
				btnCancella.setEnabled(false);
			}
		}
		{
			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.fill = GridBagConstraints.BOTH;
			gbc_separator.gridwidth = 3;
			gbc_separator.insets = new Insets(0, 0, 5, 0);
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 12;
			contentPanel.add(separator, gbc_separator);
		}
		{
			JLabel lblEsportaListe = new JLabel("Backup Liste :");
			GridBagConstraints gbc_lblEsportaListe = new GridBagConstraints();
			gbc_lblEsportaListe.anchor = GridBagConstraints.WEST;
			gbc_lblEsportaListe.insets = new Insets(0, 0, 5, 5);
			gbc_lblEsportaListe.gridx = 0;
			gbc_lblEsportaListe.gridy = 13;
			contentPanel.add(lblEsportaListe, gbc_lblEsportaListe);
		}
		{
			JButton exportButton = new JButton("Esporta");
			exportButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File chooserDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
					JFileChooser fc = new JFileChooser(chooserDir);
					fc.setMultiSelectionEnabled(false);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fc.setFileFilter(new ImportExportFileFilter());
					fc.setSelectedFile(new File(chooserDir + File.separator + "liste.zip"));
					int returnVal = fc.showDialog(AnimeIndex.mainFrame, "Esporta");
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						LinkedHashMap<File,String> fileToZip = new LinkedHashMap<File,String>();
						File animeFolder = new File(MAMUtil.getAnimeFolderPath());
						File[] fileList = animeFolder.listFiles();
						for (int i = 0; i < fileList.length; i++)
						{
							fileToZip.put(fileList[i], "Anime" + File.separator);
						}
						File imageAnimeFolder = new File(MAMUtil.getImageFolderPath());
						
						File[] folderImage = imageAnimeFolder.listFiles();
						for (int i = 0; i < folderImage.length; i++)
						{
							File[] fileImage = folderImage[i].listFiles();
							for (int j = 0; j < fileImage.length; j++)
							{
								fileToZip.put(fileImage[j], "Images" + File.separator + folderImage[i].getName() + File.separator);
							}
						}
						
						File fansub = new File(MAMUtil.getFansubPath());
						fileToZip.put(fansub, "");
						
						BackupImportExportTask task = new BackupImportExportTask(fc.getSelectedFile(), fileToZip);
						WaitDialog waitForZip = new WaitDialog("Esportando...", "Esportando i dati", task, PreferenceDialog.this);
						waitForZip.setLocationRelativeTo(PreferenceDialog.this);
						waitForZip.setVisible(true);

					}

				}
			});
			GridBagConstraints gbc_exportButton = new GridBagConstraints();
			gbc_exportButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_exportButton.insets = new Insets(0, 0, 5, 5);
			gbc_exportButton.gridx = 1;
			gbc_exportButton.gridy = 13;
			contentPanel.add(exportButton, gbc_exportButton);
		}
		{
			JButton importButton = new JButton("Importa");
			importButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File chooserDir = new File(System.getProperty("user.home") + File.separator + "Desktop");
					JFileChooser fc = new JFileChooser(chooserDir);
					fc.setMultiSelectionEnabled(false);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fc.setFileFilter(new ImportExportFileFilter());
					int returnVal = fc.showDialog(AnimeIndex.mainFrame, "Importa");
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						File zipFile = fc.getSelectedFile();
						BackupImportExportTask task = new BackupImportExportTask(zipFile);
						WaitDialog waitForZip = new WaitDialog("Importando...", "Importando i dati", task, PreferenceDialog.this);
						waitForZip.setLocationRelativeTo(PreferenceDialog.this);
						waitForZip.setVisible(true);
					}
				}
			});
			GridBagConstraints gbc_importButton = new GridBagConstraints();
			gbc_importButton.fill = GridBagConstraints.BOTH;
			gbc_importButton.insets = new Insets(0, 0, 5, 0);
			gbc_importButton.gridx = 2;
			gbc_importButton.gridy = 13;
			contentPanel.add(importButton, gbc_importButton);
		}

		{
			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.fill = GridBagConstraints.BOTH;
			gbc_separator.gridwidth = 3;
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 14;
			contentPanel.add(separator, gbc_separator);
		}

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
