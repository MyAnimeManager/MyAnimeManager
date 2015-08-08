package util.window;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.EventObject;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;

import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;
import util.Filters;
import util.PatternFilter;
import util.SearchBar;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.sun.xml.internal.fastinfoset.stax.events.StartDocumentEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;

public class AddAnimeDialog extends JDialog
{
	private final static String IMAGE_PATH = FileManager.getImageFolderPath();
	private final JPanel contentPanel = new JPanel();
	private JTextField nameField;
	private JTextField episodeField;
	private JTextField totalEpisodeField;
	private JTextField fansubLinkField;
	private JComboBox fansubComboBox;
	private JComboBox listToAddComboBox;
	private SearchBar searchBar;
	private JList searchedList;
	private JButton btnCerca;
	private DefaultListModel animeModel;
	private HashMap<String,Integer> animeSearched;
	private JComboBox listToAddAniComboBox;
	private JButton addAniButton;
	private JComboBox exitDayComboBox;
	private JPanel anilistAddPanel;
	private JPanel normalAddPanel;
	private JCheckBox keepOpen;
	public SetCheckDialog checkDialog;
	public static JToggleButton checkToggleButton;
	public static boolean checkCompletedList;
	public static boolean checkAiringList;
	public static boolean checkOAVList;
	public static boolean checkFilmList;
	public static boolean checkToSeeList;

//TODO l'aggiunta manuale e' completamente da rifare
	/**
	 * Create the dialog.
	 */
	public AddAnimeDialog()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				searchBar.requestFocus();
			}
		});
		setTitle("Aggiungi anime");
		setResizable(false);
		setBounds(100, 100, 580, 300);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			{
				normalAddPanel = new JPanel();
				normalAddPanel.setLayout(new BorderLayout(0, 0));
				normalAddPanel.add(contentPanel, BorderLayout.CENTER);
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("left:default"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("left:default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,}));
				{
					JLabel lblName = new JLabel("Name :");
					contentPanel.add(lblName, "2, 2, left, default");
				}
				{
					nameField = new JTextField();
					contentPanel.add(nameField, "4, 2, left, default");
					nameField.setColumns(20);
				}
				{
					exitDayComboBox = new JComboBox();
					String[] dayWeek = {"?????","Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato","Domenica", "Concluso"};
					exitDayComboBox.setModel(new DefaultComboBoxModel(dayWeek));
					if (!((String)AnimeIndex.animeTypeComboBox.getSelectedItem()).equalsIgnoreCase("anime in corso"))
					{
						exitDayComboBox.setSelectedItem("Concluso");
						exitDayComboBox.setEnabled(false);
					}
					contentPanel.add(exitDayComboBox, "4, 12, left, default");

				}
				{
					listToAddComboBox = new JComboBox();
					listToAddComboBox.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							String list = (String) listToAddComboBox.getSelectedItem();
							if (list.equalsIgnoreCase("anime in corso"))
								exitDayComboBox.setEnabled(true);
							else
							{
								exitDayComboBox.setSelectedItem("Concluso");
								exitDayComboBox.setEnabled(false);
							}
						}
					});
					listToAddComboBox.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere"}));
					String type = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();
					listToAddComboBox.setSelectedItem(type);
					contentPanel.add(listToAddComboBox, "8, 2, right, default");
				}
				{
					JLabel lblCurrentEpisode = new JLabel("Current Episode:");
					contentPanel.add(lblCurrentEpisode, "2, 4, right, default");
				}
				{
					episodeField = new JTextField();
					contentPanel.add(episodeField, "4, 4, left, default");
					episodeField.setColumns(3);
					((AbstractDocument)episodeField.getDocument()).setDocumentFilter( new PatternFilter("\\d{0,4}"));
				}
				{
					JLabel lblTotalEpisode = new JLabel("Total Episode:");
					contentPanel.add(lblTotalEpisode, "2, 6, left, default");
				}
				{
					totalEpisodeField = new JTextField();
					contentPanel.add(totalEpisodeField, "4, 6, left, top");
					totalEpisodeField.setColumns(3);
					((AbstractDocument)totalEpisodeField.getDocument()).setDocumentFilter( new PatternFilter("\\d{0,4}"));
				}
				{
					JLabel lblFansub = new JLabel("Fansub");
					contentPanel.add(lblFansub, "2, 8, left, default");
				}
				{
					fansubComboBox = new JComboBox();
					fansubComboBox.setModel(new DefaultComboBoxModel(AnimeIndex.getFansubList()));
					contentPanel.add(fansubComboBox, "4, 8, left, default");
				}
				{
					JLabel lblFansubLink = new JLabel("Fansub Link");
					contentPanel.add(lblFansubLink, "2, 10, left, default");
				}
				{
					fansubLinkField = new JTextField();
					contentPanel.add(fansubLinkField, "4, 10, left, default");
					fansubLinkField.setColumns(20);
				}
				{
					JLabel lblExitDay = new JLabel("Giorno di Uscita");
					contentPanel.add(lblExitDay, "2, 12, right, default");
				}
				{
					JPanel buttonPane = new JPanel();
					normalAddPanel.add(buttonPane, BorderLayout.SOUTH);
					FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
					fl_buttonPane.setHgap(100);
					buttonPane.setLayout(fl_buttonPane);
					{
						JButton addButton = new JButton("Aggiungi");
						addButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								if (nameField.getText().equalsIgnoreCase(""))
								{
									JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
										    "Nome non inserito",
										    "Errore",
										    JOptionPane.ERROR_MESSAGE);
								}
								else if ((totalEpisodeField.getText().equalsIgnoreCase("") && episodeField.getText().equalsIgnoreCase("")) || totalEpisodeField.getText().equalsIgnoreCase(""))
								{ 
									JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
										    "Episodi totali o episodio corrente non inseriti/o",
										    "Errore",
										    JOptionPane.ERROR_MESSAGE);
								}
								else if ((!episodeField.getText().equalsIgnoreCase("") && (Integer.parseInt(AnimeIndex.animeDialog.getTotatEp()) < Integer.parseInt(AnimeIndex.animeDialog.getCurrentEp()))))
								{
									JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
										    "Il numero totale di episodi non può essere inferiore al numero attuale",
										    "Errore",
										    JOptionPane.ERROR_MESSAGE);
								}
								else
								{
									
									if ((!(totalEpisodeField.getText().equalsIgnoreCase("")) && episodeField.getText().equalsIgnoreCase("")))
										{
											episodeField.setText("1");
										}
									
								String name = AnimeIndex.animeDialog.getName();
								String currentEp = AnimeIndex.animeDialog.getCurrentEp();
								String totEp = AnimeIndex.animeDialog.getTotatEp();
								String fansub = AnimeIndex.animeDialog.getFansub();
								String link = AnimeIndex.animeDialog.getFansubLink();
								String day = AnimeIndex.animeDialog.getDay();

								if (currentEp.equals(totEp))
									AnimeIndex.animeInformation.plusButton.setEnabled(false);
								
								AnimeData data = new AnimeData(currentEp, totEp, fansub, "", null, day, "", "", "", "", "", "", "", false);
								
								
								String listName = getListToAdd();
								JList list = null;
								boolean contains = false;
								
								if (listName.equalsIgnoreCase("anime completati"))
								{	
									if (AnimeIndex.completedMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
									}
									else
									{
									AnimeIndex.completedMap.put(name, data);
									AnimeIndex.completedModel.addElement(AnimeIndex.animeDialog.getName());						
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
									AnimeIndex.completedList.setSelectedValue(name, true);
									AnimeIndex.completedSessionAnime.add(name);
									}
								}				
								else if (listName.equalsIgnoreCase("anime in corso"))
								{
									if (AnimeIndex.airingMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
									}
									else
									{
									AnimeIndex.airingMap.put(name, data);
									AnimeIndex.airingModel.addElement(AnimeIndex.animeDialog.getName());
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
									AnimeIndex.airingList.setSelectedValue(name, true);
									AnimeIndex.airingSessionAnime.add(name);
									}
								}
								else if (listName.equalsIgnoreCase("oav"))
								{
									if (AnimeIndex.ovaMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
									}
									else
									{
									AnimeIndex.ovaMap.put(name, data);
									AnimeIndex.ovaModel.addElement(AnimeIndex.animeDialog.getName());
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
									AnimeIndex.ovaList.setSelectedValue(name, true);
									AnimeIndex.ovaSessionAnime.add(name);
									}
								}
								else if (listName.equalsIgnoreCase("film"))
								{
									if (AnimeIndex.filmMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
									}
									else
									{
									AnimeIndex.filmMap.put(name, data);
									AnimeIndex.filmModel.addElement(AnimeIndex.animeDialog.getName());
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
									AnimeIndex.filmList.setSelectedValue(name, true);
									AnimeIndex.filmSessionAnime.add(name);
									}
								}
								else if (listName.equalsIgnoreCase("completi da vedere"))
								{
									if (AnimeIndex.completedToSeeMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
									}
									else
									{
									AnimeIndex.completedToSeeMap.put(name, data);
									AnimeIndex.completedToSeeModel.addElement(AnimeIndex.animeDialog.getName());
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
									AnimeIndex.completedToSeeList.setSelectedValue(name, true);
									AnimeIndex.completedToSeeSessionAnime.add(name);
									}
								}
								
								if (!contains)
								{
								JButton but = (JButton) e.getSource();
								JDialog dialog = (JDialog) but.getTopLevelAncestor();
								dialog.dispose();
								}
							}
							}
						});
						buttonPane.add(addButton);
					}
					{
						JButton cancelButton = new JButton("Esci");
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
			{
				anilistAddPanel = new JPanel();
				anilistAddPanel.setLayout(new BorderLayout(0, 0));
				{
					JPanel searchPanel = new JPanel();
					anilistAddPanel.add(searchPanel, BorderLayout.CENTER);
					searchPanel.setLayout(new BorderLayout(0, 0));
					{
						JPanel searchPanel1 = new JPanel();
						searchPanel.add(searchPanel1, BorderLayout.NORTH);
						GridBagLayout gbl_searchPanel1 = new GridBagLayout();
						gbl_searchPanel1.columnWidths = new int[]{63, 246, 61, 109, 46, 25, 0};
						gbl_searchPanel1.rowHeights = new int[]{27, 0};
						gbl_searchPanel1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						gbl_searchPanel1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
						searchPanel1.setLayout(gbl_searchPanel1);
						{
							searchBar = new SearchBar();
							searchBar.setFont(AnimeIndex.segui.deriveFont(11f));
							searchBar.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									AddAnimeDialog.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
									animeModel.clear();
									try {
										ConnectionManager.ConnectAndGetToken();
									} catch (ConnectException e1) {
										JOptionPane.showMessageDialog(contentPanel, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
									}
									catch (UnknownHostException e2) {
										JOptionPane.showMessageDialog(contentPanel, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
									}
									animeSearched = ConnectionManager.AnimeSearch(searchBar.getText());
									if (animeSearched.isEmpty())
									{
										animeModel.addElement("Nessun anime trovato");
										searchedList.setEnabled(false);
										addAniButton.setEnabled(false);
									}
									else
									{	searchedList.setEnabled(true);
										addAniButton.setEnabled(false);
										Object[] animeArray = animeSearched.keySet().toArray();
										
										for (int i = 0; i < animeArray.length; i++)
											animeModel.addElement(animeArray[i]);											
									}
									AddAnimeDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
								}
							});
							GridBagConstraints gbc_searchBar = new GridBagConstraints();
							gbc_searchBar.fill = GridBagConstraints.HORIZONTAL;
							gbc_searchBar.gridwidth = 2;
							gbc_searchBar.anchor = GridBagConstraints.NORTH;
							gbc_searchBar.insets = new Insets(0, 0, 0, 5);
							gbc_searchBar.gridx = 0;
							gbc_searchBar.gridy = 0;
							searchPanel1.add(searchBar, gbc_searchBar);
							searchBar.setPreferredSize(new Dimension(344, 27));
							searchBar.setMinimumSize(new Dimension(344, 27));
							searchBar.setMaximumSize(new Dimension(344, 27));
							searchBar.setColumns(30);
						}
						{
							btnCerca = new JButton("Cerca");
							GridBagConstraints gbc_btnCerca = new GridBagConstraints();
							gbc_btnCerca.insets = new Insets(0, 0, 0, 5);
							gbc_btnCerca.anchor = GridBagConstraints.WEST;
							gbc_btnCerca.gridx = 2;
							gbc_btnCerca.gridy = 0;
							searchPanel1.add(btnCerca, gbc_btnCerca);
							{
								listToAddAniComboBox = new JComboBox();
								listToAddAniComboBox.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										AddAnimeDialog.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
										if(checkToggleButton!=null)
										{
										if (AnimeIndex.appProp.getProperty("List_to_Check").equalsIgnoreCase("all"))
										{
											checkCompletedList = true;
											checkAiringList = true;
											checkOAVList = true;
											checkFilmList = true;
											checkToSeeList = true;
											checkToggleButton.setText("Tutte le liste");
											checkToggleButton.setSelected(true);
										}
										else
										{
										if (AnimeIndex.addToPreviousList != null && AnimeIndex.addToPreviousList.equalsIgnoreCase((String)listToAddAniComboBox.getSelectedItem()) && AddAnimeDialog.checkCompletedList == false && AddAnimeDialog.checkAiringList == false && AddAnimeDialog.checkOAVList == false && AddAnimeDialog.checkFilmList == false && AddAnimeDialog.checkToSeeList == false)
										{
											checkToggleButton.setText("Nessuna Lista");
										}
										else if (listToAddAniComboBox.getSelectedItem().equals("Anime Completati"))
										{
											checkCompletedList = true;
											checkAiringList = false;
											checkOAVList = false;
											checkFilmList = false;
											checkToSeeList = false;
											checkToggleButton.setText("Anime Completati");
											checkToggleButton.setSelected(true);
										}
										else if (listToAddAniComboBox.getSelectedItem().equals("Anime in Corso"))
										{
											checkCompletedList = false;
											checkAiringList = true;
											checkOAVList = false;
											checkFilmList = false;
											checkToSeeList = false;
											checkToggleButton.setText("Anime in Corso");
											checkToggleButton.setSelected(true);
										}
										else if (listToAddAniComboBox.getSelectedItem().equals("OAV"))
										{
											checkCompletedList = false;
											checkAiringList = false;
											checkOAVList = true;
											checkFilmList = false;
											checkToSeeList = false;
											checkToggleButton.setText("OAV");
											checkToggleButton.setSelected(true);
										}
										else if (listToAddAniComboBox.getSelectedItem().equals("Film"))
										{
											checkCompletedList = false;
											checkAiringList = false;
											checkOAVList = false;
											checkFilmList = true;
											checkToSeeList = false;
											checkToggleButton.setText("Film");
											checkToggleButton.setSelected(true);
										}
										else if (listToAddAniComboBox.getSelectedItem().equals("Completi Da Vedere"))
										{
											checkCompletedList = false;
											checkAiringList = false;
											checkOAVList = false;
											checkFilmList = false;
											checkToSeeList = true;
											checkToggleButton.setText("Completi Da Vedere");
											checkToggleButton.setSelected(true);
										}
										else{}
										}
									}
									AddAnimeDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
									}
								});
								listToAddAniComboBox.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere"}));
								GridBagConstraints gbc_listToAddAniComboBox = new GridBagConstraints();
								gbc_listToAddAniComboBox.fill = GridBagConstraints.HORIZONTAL;
								gbc_listToAddAniComboBox.gridwidth = 2;
								gbc_listToAddAniComboBox.insets = new Insets(0, 0, 0, 5);
								gbc_listToAddAniComboBox.gridx = 3;
								gbc_listToAddAniComboBox.gridy = 0;
								searchPanel1.add(listToAddAniComboBox, gbc_listToAddAniComboBox);
								listToAddAniComboBox.setSelectedItem(AnimeIndex.getList());
							}
							{
								keepOpen = new JCheckBox("");
								keepOpen.setToolTipText("Mantieni questa finestra aperta dopo ogni aggiunta");
								GridBagConstraints gbc_keepOpen = new GridBagConstraints();
								gbc_keepOpen.anchor = GridBagConstraints.EAST;
								gbc_keepOpen.gridx = 5;
								gbc_keepOpen.gridy = 0;
								searchPanel1.add(keepOpen, gbc_keepOpen);
							}
							{
								JScrollPane listPanel = new JScrollPane();
								searchPanel.add(listPanel, BorderLayout.CENTER);
								listPanel.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
								listPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
								{
									animeModel = new DefaultListModel();									
									searchedList = new JList(animeModel);
									searchedList.addKeyListener(new KeyAdapter() {
										@Override
										public void keyPressed(KeyEvent arg0) {
											if(arg0.getKeyCode()==KeyEvent.VK_ENTER && !searchedList.isSelectionEmpty())
											{
												AddAnimeDialog.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
												AnimeIndex.addToPreviousList = (String)listToAddAniComboBox.getSelectedItem();
												String anime = (String) searchedList.getSelectedValue();
												int id = animeSearched.get(anime);
												String dataAni = ConnectionManager.parseAnimeData(id);
											
												String name = ConnectionManager.getAnimeData("title_romaji", dataAni);
												String totEp = ConnectionManager.getAnimeData("total_episodes", dataAni);
												String currentEp = "1";
												String fansub = "";
												String link = ""; 
												String animeType = ConnectionManager.getAnimeData("type", dataAni);
												String releaseDate = ConnectionManager.getAnimeData("start_date", dataAni);
												String finishDate = ConnectionManager.getAnimeData("end_date", dataAni);
												String durationEp = ConnectionManager.getAnimeData("duration", dataAni);
												
												if(totEp != null && !totEp.isEmpty())
												{
													if(totEp.equals("null")||totEp.equals("0"))
														totEp = "??";
												}
												
												if (durationEp != null && !durationEp.isEmpty())
												{
													if(durationEp.equals("null")||durationEp.equals("0"))
														durationEp = "?? min";
													else
														durationEp += " min";
												}
												
												if (releaseDate != null && !releaseDate.isEmpty())
												{
													if (releaseDate.equals("null"))
														releaseDate = "??/??/????";
													else if (releaseDate.length() > 4)
													{
														String dayStart = releaseDate.substring(8, 10);
														String monthStart = releaseDate.substring(5, 7);
														String yearStart = releaseDate.substring(0, 4);
														releaseDate = dayStart + "/" + monthStart + "/" + yearStart;
													}
												}
												
												if (finishDate != null && !finishDate.isEmpty())
												{
													if (finishDate.equals("null"))
														finishDate = "??/??/????";
													else if (finishDate.length() > 4)
													{
														String dayEnd = finishDate.substring(8, 10);
														String monthEnd= finishDate.substring(5, 7);
														String yearEnd = finishDate.substring(0, 4);
														finishDate = dayEnd + "/" + monthEnd + "/" + yearEnd;
													}
													if (totEp.equals("1"))
														finishDate = releaseDate;
												}
											
												String exitDay = "?????";
												if (((String)listToAddAniComboBox.getSelectedItem()).equalsIgnoreCase("anime completati")){
													currentEp = totEp;
													exitDay = "Concluso";
												    }
												if (((String)listToAddAniComboBox.getSelectedItem()).equalsIgnoreCase("completi da vedere"))
													exitDay = "Concluso";
												
																			
												if (currentEp.equals(totEp))
													AnimeIndex.animeInformation.plusButton.setEnabled(false);
												
												String listName = (String) listToAddAniComboBox.getSelectedItem();
												JList list = null;
												boolean contains = false;
												
												if (listName.equalsIgnoreCase("anime completati"))
												{
													if (AnimeIndex.completedMap.containsKey(name))
													{
														JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
														contains = true;
													}
													else
													{
													AnimeIndex.completedModel.addElement(name);
													
													
													String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
													imageLink = imageLink.replaceAll("\\\\/", "/");
													String imageName = name.replaceAll("\\\\", "_");
													imageName = imageName.replaceAll("/", "_");
													imageName = imageName.replaceAll(":", "_");
													imageName = imageName.replaceAll("\\*", "_");
													imageName = imageName.replaceAll("\\?", "_");
													imageName = imageName.replaceAll("\"", "_");
													imageName = imageName.replaceAll(">", "_");
													imageName = imageName.replaceAll("<", "_");
													FileManager.saveImage(imageLink, imageName, "Completed");
													AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
																					"", "", animeType, releaseDate, finishDate, durationEp, false);
													
													AnimeIndex.completedMap.put(name, data);
													AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
													AnimeIndex.completedList.clearSelection();
													AnimeIndex.completedList.setSelectedValue(name, true);
													String imagePath = AnimeIndex.completedMap.get(name).getImagePath("anime completati");
													AnimeIndex.completedSessionAnime.add(imagePath);
													if (AnimeIndex.completedDeletedAnime.contains(imagePath))
													{
														AnimeIndex.completedDeletedAnime.remove(imagePath);
													}
													}
												}				
												else if (listName.equalsIgnoreCase("anime in corso"))
												{
													if (AnimeIndex.airingMap.containsKey(name))
													{
														JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
														contains = true;
													}
													else
													{
													AnimeIndex.airingModel.addElement(name);
													
													String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
													imageLink = imageLink.replaceAll("\\\\/", "/");
													String imageName = name.replaceAll("\\\\", "_");
													imageName = imageName.replaceAll("/", "_");
													imageName = imageName.replaceAll(":", "_");
													imageName = imageName.replaceAll("\\*", "_");
													imageName = imageName.replaceAll("\\?", "_");
													imageName = imageName.replaceAll("\"", "_");
													imageName = imageName.replaceAll(">", "_");
													imageName = imageName.replaceAll("<", "_");
													FileManager.saveImage(imageLink, imageName, "Airing");
													AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
																					"", "", animeType, releaseDate, finishDate, durationEp, false);
													
													AnimeIndex.airingMap.put(name, data);
													AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
													AnimeIndex.airingList.clearSelection();
													AnimeIndex.airingList.setSelectedValue(name, true);
													String imagePath = AnimeIndex.airingMap.get(name).getImagePath("anime in corso");
													AnimeIndex.airingSessionAnime.add(imagePath);
													if (AnimeIndex.airingDeletedAnime.contains(imagePath))
													{
														AnimeIndex.airingDeletedAnime.remove(imagePath);
													}
													}
												}
												else if (listName.equalsIgnoreCase("oav"))
												{
													if (AnimeIndex.ovaMap.containsKey(name))
													{
														JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
														contains = true;
													}
													else
													{
													AnimeIndex.ovaModel.addElement(name);
													
													String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
													imageLink = imageLink.replaceAll("\\\\/", "/");
													String imageName = name.replaceAll("\\\\", "_");
													imageName = imageName.replaceAll("/", "_");
													imageName = imageName.replaceAll(":", "_");
													imageName = imageName.replaceAll("\\*", "_");
													imageName = imageName.replaceAll("\\?", "_");
													imageName = imageName.replaceAll("\"", "_");
													imageName = imageName.replaceAll(">", "_");
													imageName = imageName.replaceAll("<", "_");
													FileManager.saveImage(imageLink, imageName, "Ova");
													AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
															"", "", animeType, releaseDate, finishDate, durationEp, false);
													
													AnimeIndex.ovaMap.put(name, data);
													AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
													AnimeIndex.ovaList.clearSelection();
													AnimeIndex.ovaList.setSelectedValue(name, true);
													String imagePath = AnimeIndex.ovaMap.get(name).getImagePath("oav");
													AnimeIndex.ovaSessionAnime.add(imagePath);
													if (AnimeIndex.ovaDeletedAnime.contains(imagePath))
													{
														AnimeIndex.ovaDeletedAnime.remove(imagePath);
													}
													}
												}
												else if (listName.equalsIgnoreCase("film"))
												{
													if (AnimeIndex.filmMap.containsKey(name))
													{
														JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
														contains = true;
													}
													else
													{
													AnimeIndex.filmModel.addElement(name);
													
													String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
													imageLink = imageLink.replaceAll("\\\\/", "/");
													String imageName = name.replaceAll("\\\\", "_");
													imageName = imageName.replaceAll("/", "_");
													imageName = imageName.replaceAll(":", "_");
													imageName = imageName.replaceAll("\\*", "_");
													imageName = imageName.replaceAll("\\?", "_");
													imageName = imageName.replaceAll("\"", "_");
													imageName = imageName.replaceAll(">", "_");
													imageName = imageName.replaceAll("<", "_");
													FileManager.saveImage(imageLink, imageName, "film");
													AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
															"", "", animeType, releaseDate, finishDate, durationEp, false);
													
													
													AnimeIndex.filmMap.put(name, data);
													AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
													AnimeIndex.filmList.clearSelection();
													AnimeIndex.filmList.setSelectedValue(name, true);
													String imagePath = AnimeIndex.filmMap.get(name).getImagePath("film");
													AnimeIndex.filmSessionAnime.add(imagePath);
													if (AnimeIndex.filmDeletedAnime.contains(imagePath))
													{
														AnimeIndex.filmDeletedAnime.remove(imagePath);
													}
													}
												}
												else if (listName.equalsIgnoreCase("completi da vedere"))
												{
													if (AnimeIndex.completedToSeeMap.containsKey(name))
													{
														JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
														contains = true;
													}
													else
													{
													AnimeIndex.completedToSeeModel.addElement(name);
													
													String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
													imageLink = imageLink.replaceAll("\\\\/", "/");
													String imageName = name.replaceAll("\\\\", "_");
													imageName = imageName.replaceAll("/", "_");
													imageName = imageName.replaceAll(":", "_");
													imageName = imageName.replaceAll("\\*", "_");
													imageName = imageName.replaceAll("\\?", "_");
													imageName = imageName.replaceAll("\"", "_");
													imageName = imageName.replaceAll(">", "_");
													imageName = imageName.replaceAll("<", "_");
													FileManager.saveImage(imageLink, imageName, "Completed to See");
													AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
																					"", "", animeType, releaseDate, finishDate, durationEp,false);
													
													AnimeIndex.completedToSeeMap.put(name, data);
													AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
													AnimeIndex.completedToSeeList.clearSelection();
													AnimeIndex.completedToSeeList.setSelectedValue(name, true);
													String imagePath = AnimeIndex.completedToSeeMap.get(name).getImagePath("completi da vedere");
													AnimeIndex.completedToSeeSessionAnime.add(imagePath);
													if (AnimeIndex.completedToSeeDeletedAnime.contains(imagePath))
													{
														AnimeIndex.completedToSeeDeletedAnime.remove(imagePath);
													}
													}
												}
												
												if (!contains && !keepOpen.isSelected())
												{
												JList but = (JList) arg0.getSource();
												JDialog dialog = (JDialog) but.getTopLevelAncestor();
												dialog.dispose();
												}
												AddAnimeDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
											
											}
										}
									});
									searchedList.setFont(AnimeIndex.segui.deriveFont(12f));
									searchedList.addListSelectionListener(new ListSelectionListener() {
										public void valueChanged(ListSelectionEvent e) {
											addAniButton.setEnabled(true);
										}
									});
									searchedList.setEnabled(false);
									searchedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									listPanel.setViewportView(searchedList);
								}
							}
							btnCerca.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									animeModel.clear();
									try {
										ConnectionManager.ConnectAndGetToken();
									}
									catch (UnknownHostException e2) {
										JOptionPane.showMessageDialog(contentPanel, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
									}
									catch (ConnectException e1) {
										JOptionPane.showMessageDialog(contentPanel, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
									};
									animeSearched = ConnectionManager.AnimeSearch(searchBar.getText());
									if (animeSearched.isEmpty())
									{
										animeModel.addElement("Nessun anime trovato");
										searchedList.setEnabled(false);
										addAniButton.setEnabled(false);
									}
									else
									{	searchedList.setEnabled(true);
										addAniButton.setEnabled(false);
										Object[] animeArray = animeSearched.keySet().toArray();
										
										for (int i = 0; i < animeArray.length; i++)
											animeModel.addElement(animeArray[i]);											
									}
									
								}
							});
						}
					}
				}
				tabbedPane.addTab("Aggiunta da Anilist", null, anilistAddPanel, null);
				tabbedPane.addTab("Aggiunta manuale", null, normalAddPanel, null);
				{
					JPanel button2Panel = new JPanel();
					anilistAddPanel.add(button2Panel, BorderLayout.SOUTH);
					{
						button2Panel.setLayout(new MigLayout("", "[74.00px,grow][159.00px,grow][83.00px,grow][160.00][15.00px,grow][78.00][143.00px,grow]", "[23px,grow]"));
					}
					{
						JLabel lblControllaIn = new JLabel("Controlla in :");
						button2Panel.add(lblControllaIn, "cell 0 0");
					}
					{
						checkToggleButton = new JToggleButton("Seleziona Lista");
						checkToggleButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								checkDialog = new SetCheckDialog();
								checkDialog.setLocationRelativeTo(searchedList);
								checkDialog.setVisible(true);
							}
						});
						button2Panel.add(checkToggleButton, "cell 1 0,growx,aligny center");
					}
					addAniButton = new JButton("Aggiungi");
					button2Panel.add(addAniButton, "cell 3 0,growx");
					addAniButton.setEnabled(false);
					addAniButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							AddAnimeDialog.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							AnimeIndex.addToPreviousList = (String)listToAddAniComboBox.getSelectedItem();
							String anime = (String) searchedList.getSelectedValue();
							int id = animeSearched.get(anime);
							String dataAni = ConnectionManager.parseAnimeData(id);
						
							String name = ConnectionManager.getAnimeData("title_romaji", dataAni);
							String totEp = ConnectionManager.getAnimeData("total_episodes", dataAni);
							String currentEp = "1";
							String fansub = "";
							String link = ""; 
							String animeType = ConnectionManager.getAnimeData("type", dataAni);
							String releaseDate = ConnectionManager.getAnimeData("start_date", dataAni);
							String finishDate = ConnectionManager.getAnimeData("end_date", dataAni);
							String durationEp = ConnectionManager.getAnimeData("duration", dataAni);
							
							if(totEp != null && !totEp.isEmpty())
							{
								if(totEp.equals("null")||totEp.equals("0"))
									totEp = "??";
							}
							
							if (durationEp != null && !durationEp.isEmpty())
							{
								if(durationEp.equals("null")||durationEp.equals("0"))
									durationEp = "?? min";
								else
									durationEp += " min";
							}
							
							if (releaseDate != null && !releaseDate.isEmpty())
							{
								if (releaseDate.equals("null"))
									releaseDate = "??/??/????";
								else if(releaseDate.length()==4)
									releaseDate = "??/??/" + releaseDate;
								else if(releaseDate.length()==7)
								{
									String monthStart = releaseDate.substring(5, 7);
									String yearStart = releaseDate.substring(0, 4);
									releaseDate = "??/" + monthStart + "/" + yearStart;
								}
								else if (releaseDate.length() > 7)
								{
									String dayStart = releaseDate.substring(8, 10);
									String monthStart = releaseDate.substring(5, 7);
									String yearStart = releaseDate.substring(0, 4);
									releaseDate = dayStart + "/" + monthStart + "/" + yearStart;
								}
							}
							
							if (finishDate != null && !finishDate.isEmpty())
							{
								if (finishDate.equals("null"))
									finishDate = "??/??/????";
								else if(finishDate.length()==4)
									finishDate = "??/??/" + finishDate;
								else if(finishDate.length()==7)
								{
									String monthEnd = finishDate.substring(5, 7);
									String yearEnd = finishDate.substring(0, 4);
									finishDate = "??/" + monthEnd + "/" + yearEnd;
								}
								else if (finishDate.length() > 7)
								{
									String dayEnd = finishDate.substring(8, 10);
									String monthEnd= finishDate.substring(5, 7);
									String yearEnd = finishDate.substring(0, 4);
									finishDate = dayEnd + "/" + monthEnd + "/" + yearEnd;
								}
								if (totEp.equals("1"))
									finishDate = releaseDate;
							}
						
							String exitDay = "?????";
							if (((String)listToAddAniComboBox.getSelectedItem()).equalsIgnoreCase("anime completati")){
								currentEp = totEp;
								exitDay = "Concluso";
							    }
							if (((String)listToAddAniComboBox.getSelectedItem()).equalsIgnoreCase("completi da vedere"))
								exitDay = "Concluso";
							
														
							if (currentEp.equals(totEp))
								AnimeIndex.animeInformation.plusButton.setEnabled(false);
							
							String listName = (String) listToAddAniComboBox.getSelectedItem();
							JList list = null;
							boolean contains = false;
							
							if (listName.equalsIgnoreCase("anime completati"))
							{
								if (AnimeIndex.completedMap.containsKey(name))
								{
									JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
									contains = true;
								}
								else
								{
								AnimeIndex.completedModel.addElement(name);
								
								
								String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
								imageLink = imageLink.replaceAll("\\\\/", "/");
								String imageName = name.replaceAll("\\\\", "_");
								imageName = imageName.replaceAll("/", "_");
								imageName = imageName.replaceAll(":", "_");
								imageName = imageName.replaceAll("\\*", "_");
								imageName = imageName.replaceAll("\\?", "_");
								imageName = imageName.replaceAll("\"", "_");
								imageName = imageName.replaceAll(">", "_");
								imageName = imageName.replaceAll("<", "_");
								FileManager.saveImage(imageLink, imageName, "Completed");
								AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
																"", "", animeType, releaseDate, finishDate, durationEp, false);
								
								AnimeIndex.completedMap.put(name, data);
								AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
								AnimeIndex.completedList.clearSelection();
								AnimeIndex.completedList.setSelectedValue(name, true);
								String imagePath = AnimeIndex.completedMap.get(name).getImagePath("anime completati");
								AnimeIndex.completedSessionAnime.add(imagePath);
								if (AnimeIndex.completedDeletedAnime.contains(imagePath))
								{
									AnimeIndex.completedSessionAnime.remove(imagePath);
									AnimeIndex.completedDeletedAnime.remove(imagePath);
								}
								}
							}				
							else if (listName.equalsIgnoreCase("anime in corso"))
							{
								if (AnimeIndex.airingMap.containsKey(name))
								{
									JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
									contains = true;
								}
								else
								{
								AnimeIndex.airingModel.addElement(name);
								
								String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
								imageLink = imageLink.replaceAll("\\\\/", "/");
								String imageName = name.replaceAll("\\\\", "_");
								imageName = imageName.replaceAll("/", "_");
								imageName = imageName.replaceAll(":", "_");
								imageName = imageName.replaceAll("\\*", "_");
								imageName = imageName.replaceAll("\\?", "_");
								imageName = imageName.replaceAll("\"", "_");
								imageName = imageName.replaceAll(">", "_");
								imageName = imageName.replaceAll("<", "_");
								FileManager.saveImage(imageLink, imageName, "Airing");
								AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
																"", "", animeType, releaseDate, finishDate, durationEp, false);
								
								AnimeIndex.airingMap.put(name, data);
								AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
								AnimeIndex.airingList.clearSelection();
								AnimeIndex.airingList.setSelectedValue(name, true);
								String imagePath = AnimeIndex.airingMap.get(name).getImagePath("anime in corso");
								AnimeIndex.airingSessionAnime.add(imagePath);
								if (AnimeIndex.airingDeletedAnime.contains(imagePath))
								{
									AnimeIndex.airingDeletedAnime.remove(imagePath);
									AnimeIndex.airingDeletedAnime.remove(imagePath);
								}
								}
							}
							else if (listName.equalsIgnoreCase("oav"))
							{
								if (AnimeIndex.ovaMap.containsKey(name))
								{
									JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
									contains = true;
								}
								else
								{
								AnimeIndex.ovaModel.addElement(name);
								
								String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
								imageLink = imageLink.replaceAll("\\\\/", "/");
								String imageName = name.replaceAll("\\\\", "_");
								imageName = imageName.replaceAll("/", "_");
								imageName = imageName.replaceAll(":", "_");
								imageName = imageName.replaceAll("\\*", "_");
								imageName = imageName.replaceAll("\\?", "_");
								imageName = imageName.replaceAll("\"", "_");
								imageName = imageName.replaceAll(">", "_");
								imageName = imageName.replaceAll("<", "_");
								FileManager.saveImage(imageLink, imageName, "Ova");
								AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
										"", "", animeType, releaseDate, finishDate, durationEp, false);
								
								AnimeIndex.ovaMap.put(name, data);
								AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
								AnimeIndex.ovaList.clearSelection();
								AnimeIndex.ovaList.setSelectedValue(name, true);
								String imagePath = AnimeIndex.ovaMap.get(name).getImagePath("oav");
								AnimeIndex.ovaSessionAnime.add(imagePath);
								if (AnimeIndex.ovaDeletedAnime.contains(imagePath))
								{
									AnimeIndex.ovaSessionAnime.remove(imagePath);
									AnimeIndex.ovaDeletedAnime.remove(imagePath);
								}
								}
							}
							else if (listName.equalsIgnoreCase("film"))
							{
								if (AnimeIndex.filmMap.containsKey(name))
								{
									JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
									contains = true;
								}
								else
								{
								AnimeIndex.filmModel.addElement(name);
								
								String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
								imageLink = imageLink.replaceAll("\\\\/", "/");
								String imageName = name.replaceAll("\\\\", "_");
								imageName = imageName.replaceAll("/", "_");
								imageName = imageName.replaceAll(":", "_");
								imageName = imageName.replaceAll("\\*", "_");
								imageName = imageName.replaceAll("\\?", "_");
								imageName = imageName.replaceAll("\"", "_");
								imageName = imageName.replaceAll(">", "_");
								imageName = imageName.replaceAll("<", "_");
								FileManager.saveImage(imageLink, imageName, "film");
								AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
										"", "", animeType, releaseDate, finishDate, durationEp, false);
								
								
								AnimeIndex.filmMap.put(name, data);
								AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
								AnimeIndex.filmList.clearSelection();
								AnimeIndex.filmList.setSelectedValue(name, true);
								String imagePath = AnimeIndex.filmMap.get(name).getImagePath("film");
								AnimeIndex.filmSessionAnime.add(imagePath);
								if (AnimeIndex.filmDeletedAnime.contains(imagePath))
								{
									AnimeIndex.filmSessionAnime.remove(imagePath);
									AnimeIndex.filmDeletedAnime.remove(imagePath);
								}
								}
							}
							else if (listName.equalsIgnoreCase("completi da vedere"))
							{
								if (AnimeIndex.completedToSeeMap.containsKey(name))
								{
									JOptionPane.showMessageDialog(contentPanel, "Anime già presente", "Errore!", JOptionPane.ERROR_MESSAGE);
									contains = true;
								}
								else
								{
								AnimeIndex.completedToSeeModel.addElement(name);
								
								String imageLink = ConnectionManager.getAnimeData("image_url_lge", dataAni);
								imageLink = imageLink.replaceAll("\\\\/", "/");
								String imageName = name.replaceAll("\\\\", "_");
								imageName = imageName.replaceAll("/", "_");
								imageName = imageName.replaceAll(":", "_");
								imageName = imageName.replaceAll("\\*", "_");
								imageName = imageName.replaceAll("\\?", "_");
								imageName = imageName.replaceAll("\"", "_");
								imageName = imageName.replaceAll(">", "_");
								imageName = imageName.replaceAll("<", "_");
								FileManager.saveImage(imageLink, imageName, "Completed to See");
								AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
																"", "", animeType, releaseDate, finishDate, durationEp,false);
								
								AnimeIndex.completedToSeeMap.put(name, data);
								AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
								AnimeIndex.completedToSeeList.clearSelection();
								AnimeIndex.completedToSeeList.setSelectedValue(name, true);
								String imagePath = AnimeIndex.completedToSeeMap.get(name).getImagePath("completi da vedere");
								AnimeIndex.completedToSeeSessionAnime.add(imagePath);
								if (AnimeIndex.completedToSeeDeletedAnime.contains(imagePath))
								{
									AnimeIndex.completedToSeeSessionAnime.remove(imagePath);
									AnimeIndex.completedToSeeDeletedAnime.remove(imagePath);
								}
								}
							}
							if(AnimeIndex.filtro != 9)
						    {
							    Filters.removeFilters();
						    }
							AnimeIndex.sessionAddedAnime.add(name);
							if (!contains && !keepOpen.isSelected())
							{
							JButton but = (JButton) e.getSource();
							JDialog dialog = (JDialog) but.getTopLevelAncestor();
							dialog.dispose();
							}
							AddAnimeDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					});
					JButton button = new JButton("Esci");
					button2Panel.add(button, "cell 6 0,growx");
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							keepOpen.setSelected(false);
							JButton but = (JButton) e.getSource();
							JDialog dialog = (JDialog) but.getTopLevelAncestor();
							dialog.dispose();
						}
					});
				}
			}
		}
		//TODO finire sistema di controllo
		if (AnimeIndex.appProp.getProperty("List_to_Check").equalsIgnoreCase("all"))
		{
			checkCompletedList = true;
			checkAiringList = true;
			checkOAVList = true;
			checkFilmList = true;
			checkToSeeList = true;
			checkToggleButton.setText("Tutte le liste");
			checkToggleButton.setSelected(true);
		}
		else
		{
		if (AnimeIndex.addToPreviousList != null && AnimeIndex.addToPreviousList.equalsIgnoreCase((String)listToAddAniComboBox.getSelectedItem()) && AddAnimeDialog.checkCompletedList == false && AddAnimeDialog.checkAiringList == false && AddAnimeDialog.checkOAVList == false && AddAnimeDialog.checkFilmList == false && AddAnimeDialog.checkToSeeList == false)
		{
			checkToggleButton.setText("Nessuna Lista");
		}
		else if (listToAddAniComboBox.getSelectedItem().equals("Anime Completati"))
		{
			checkCompletedList = true;
			checkAiringList = false;
			checkOAVList = false;
			checkFilmList = false;
			checkToSeeList = false;
			checkToggleButton.setText("Anime Completati");
			checkToggleButton.setSelected(true);
		}
		else if (listToAddAniComboBox.getSelectedItem().equals("Anime in Corso"))
		{
			checkCompletedList = false;
			checkAiringList = true;
			checkOAVList = false;
			checkFilmList = false;
			checkToSeeList = false;
			checkToggleButton.setText("Anime in Corso");
			checkToggleButton.setSelected(true);
		}
		else if (listToAddAniComboBox.getSelectedItem().equals("OAV"))
		{
			checkCompletedList = false;
			checkAiringList = false;
			checkOAVList = true;
			checkFilmList = false;
			checkToSeeList = false;
			checkToggleButton.setText("OAV");
			checkToggleButton.setSelected(true);
		}
		else if (listToAddAniComboBox.getSelectedItem().equals("Film"))
		{
			checkCompletedList = false;
			checkAiringList = false;
			checkOAVList = false;
			checkFilmList = true;
			checkToSeeList = false;
			checkToggleButton.setText("Film");
			checkToggleButton.setSelected(true);
		}
		else if (listToAddAniComboBox.getSelectedItem().equals("Completi Da Vedere"))
		{
			checkCompletedList = false;
			checkAiringList = false;
			checkOAVList = false;
			checkFilmList = false;
			checkToSeeList = true;
			checkToggleButton.setText("Completi Da Vedere");
			checkToggleButton.setSelected(true);
		}
		else{}
		}
	}
	
	public String getName()
	{
		return nameField.getText();
	}

	public String getCurrentEp()
	{
		return episodeField.getText();
	}
	
	public String getTotatEp()
	{
		return totalEpisodeField.getText();
	}
	
	public String getFansub()
	{
		return (String) fansubComboBox.getSelectedItem();
	}
	
	public String getFansubLink()
	{
		return fansubLinkField.getText();
	}
	
	public String getListToAdd()
	{
		return (String) listToAddComboBox.getSelectedItem();
	}
	
	public String getDay()
	{
		return (String) exitDayComboBox.getSelectedItem();
	}
}
