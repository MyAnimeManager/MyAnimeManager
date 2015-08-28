package util.window;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.AnimeIndex;
import net.miginfocom.swing.MigLayout;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;
import util.Filters;
import util.SearchBar;
import util.SortedListModel;

import javax.swing.ImageIcon;

import java.awt.Font;

public class AddAnimeDialog extends JDialog
{
	private final static String IMAGE_PATH = FileManager.getImageFolderPath();
	private SearchBar searchBar;
	private JList searchedList;
	private JButton btnCerca;
	private DefaultListModel animeModel;
	private HashMap<String,Integer> animeSearched;
	private static JComboBox listToAddAniComboBox;
	private JButton addAniButton;
	private JPanel anilistAddPanel;
	private JPanel normalAddPanel;
	private static JCheckBox keepOpen;
	public SetCheckDialog checkDialog;
	public static JToggleButton checkToggleButton;
	public static JToggleButton listSlectionToggleButton;
	public static boolean checkCompletedList;
	public static boolean checkAiringList;
	public static boolean checkOAVList;
	public static boolean checkFilmList;
	public static boolean checkToSeeList;
	private JTextField nameField;
	private JTextField durationField;
	private JTextField startDayField;
	private JTextField startMonthField;
	private JTextField startYearField;
	private JTextField finishDayField;
	private JTextField finishMonthField;
	private JTextField finishYearField;
	private JTextField currentEpisodeText;
	private JComboBox fansubComboBox;
	private JComboBox exitdayComboBox;
	private static JComboBox listToAdd;
	private JTextField totEpField;
	private JComboBox typeComboBox;
	private JComboBox checkDataConflictComboBox;
	private JComboBox checkDataConflictComboBoxManualAdd;
	private static JTabbedPane tabbedPane;

	/**
	 * Create the dialog.
	 */
	public AddAnimeDialog()
	{
		super(AnimeIndex.frame,true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				searchBar.requestFocus();
				if(AnimeIndex.appProp.getProperty("Check_Data_Conflict").equals("active"))
				{
					checkDataConflictComboBox.setSelectedItem("Attivo");
					checkDataConflictComboBoxManualAdd.setSelectedItem("Attivo");
				}
				else
				{
					checkDataConflictComboBox.setSelectedItem("Disattivo");
					checkDataConflictComboBoxManualAdd.setSelectedItem("Disattivo");
				}
			}
		});
		setTitle("Aggiungi anime");
		setResizable(false);
		setBounds(100, 100, 585, 354);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			{
				normalAddPanel = new JPanel();
				{
					String[] dayWeek = {"?????","Luned�","Marted�","Mercoled�","Gioved�","Venerd�","Sabato","Domenica", "Concluso"};
//					if (!((String)AnimeIndex.animeTypeComboBox.getSelectedItem()).equalsIgnoreCase("anime in corso"))
//					{
//						exitDayComboBox.setSelectedItem("Concluso");
//						exitDayComboBox.setEnabled(false);
//					}

				}
				{
					String type = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();
				}
				GridBagLayout gbl_normalAddPanel = new GridBagLayout();
				gbl_normalAddPanel.columnWidths = new int[]{263, 0};
				gbl_normalAddPanel.rowHeights = new int[]{252, 9, 17, 0};
				gbl_normalAddPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
				gbl_normalAddPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
				normalAddPanel.setLayout(gbl_normalAddPanel);
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
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
									}
									catch (UnknownHostException e2) {
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
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
								listToAddAniComboBox.setToolTipText("Lista dove si vuole inserire l'anime");
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
														JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente", "Errore!", JOptionPane.ERROR_MESSAGE);
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
														JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente", "Errore!", JOptionPane.ERROR_MESSAGE);
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
														JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente", "Errore!", JOptionPane.ERROR_MESSAGE);
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
														JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente", "Errore!", JOptionPane.ERROR_MESSAGE);
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
														JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente", "Errore!", JOptionPane.ERROR_MESSAGE);
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
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
									}
									catch (ConnectException e1) {
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
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
					JPanel dataPanel = new JPanel();
					GridBagConstraints gbc_dataPanel = new GridBagConstraints();
					gbc_dataPanel.insets = new Insets(0, 0, 5, 0);
					gbc_dataPanel.fill = GridBagConstraints.BOTH;
					gbc_dataPanel.gridx = 0;
					gbc_dataPanel.gridy = 0;
					normalAddPanel.add(dataPanel, gbc_dataPanel);
					GridBagLayout gbl_dataPanel = new GridBagLayout();
					gbl_dataPanel.columnWidths = new int[]{90, 34, -3, 38, -3, 56, 64, 24, 4, 26, 4, 52, 0, 51, 51, 0};
					gbl_dataPanel.rowHeights = new int[]{20, 20, 0, 20, 20, 20, 20, 0, 20, 0, 0};
					gbl_dataPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
					gbl_dataPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
					dataPanel.setLayout(gbl_dataPanel);
					{
						JLabel lblNome = new JLabel("Nome :");
						GridBagConstraints gbc_lblNome = new GridBagConstraints();
						gbc_lblNome.insets = new Insets(0, 0, 5, 5);
						gbc_lblNome.gridx = 0;
						gbc_lblNome.gridy = 0;
						dataPanel.add(lblNome, gbc_lblNome);
					}
					{
						nameField = new JTextField();
						GridBagConstraints gbc_nameField = new GridBagConstraints();
						gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
						gbc_nameField.insets = new Insets(0, 0, 5, 5);
						gbc_nameField.gridwidth = 10;
						gbc_nameField.gridx = 1;
						gbc_nameField.gridy = 0;
						dataPanel.add(nameField, gbc_nameField);
						nameField.setColumns(10);
					}
					{
						listToAdd = new JComboBox();
						listToAdd.setToolTipText("Lista dove si vuole inserire l'anime");
						GridBagConstraints gbc_listToAdd = new GridBagConstraints();
						gbc_listToAdd.gridwidth = 3;
						gbc_listToAdd.insets = new Insets(0, 0, 5, 0);
						gbc_listToAdd.fill = GridBagConstraints.HORIZONTAL;
						gbc_listToAdd.gridx = 12;
						gbc_listToAdd.gridy = 0;
						dataPanel.add(listToAdd, gbc_listToAdd);
						listToAdd.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere"}));
					}
					{
						JLabel lblTipo = new JLabel("Tipo :");
						GridBagConstraints gbc_lblTipo = new GridBagConstraints();
						gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
						gbc_lblTipo.gridx = 0;
						gbc_lblTipo.gridy = 1;
						dataPanel.add(lblTipo, gbc_lblTipo);
					}
					{
						typeComboBox = new JComboBox();
						GridBagConstraints gbc_typeComboBox = new GridBagConstraints();
						gbc_typeComboBox.fill = GridBagConstraints.HORIZONTAL;
						gbc_typeComboBox.insets = new Insets(0, 0, 5, 5);
						gbc_typeComboBox.gridwidth = 3;
						gbc_typeComboBox.gridx = 1;
						gbc_typeComboBox.gridy = 1;
						dataPanel.add(typeComboBox, gbc_typeComboBox);
						typeComboBox.setModel(new DefaultComboBoxModel(new String[] {"?????", "TV", "Movie", "Special", "OVA", "ONA", "TV Short", "Blu-ray"}));
					}
					{
						JLabel lblEpisodioCorrente = new JLabel("Episodio Corrente :");
						GridBagConstraints gbc_lblEpisodioCorrente = new GridBagConstraints();
						gbc_lblEpisodioCorrente.anchor = GridBagConstraints.EAST;
						gbc_lblEpisodioCorrente.insets = new Insets(0, 0, 5, 5);
						gbc_lblEpisodioCorrente.gridx = 0;
						gbc_lblEpisodioCorrente.gridy = 2;
						dataPanel.add(lblEpisodioCorrente, gbc_lblEpisodioCorrente);
					}
					{
						currentEpisodeText = new JTextField();
						GridBagConstraints gbc_currentEpisodeText = new GridBagConstraints();
						gbc_currentEpisodeText.insets = new Insets(0, 0, 5, 5);
						gbc_currentEpisodeText.fill = GridBagConstraints.HORIZONTAL;
						gbc_currentEpisodeText.gridx = 1;
						gbc_currentEpisodeText.gridy = 2;
						dataPanel.add(currentEpisodeText, gbc_currentEpisodeText);
						currentEpisodeText.setColumns(10);
					}
					{
						JLabel lblControllaIn_1 = new JLabel("Controlla in :");
						lblControllaIn_1.setToolTipText("Verifica la presenza di conflitti all'interno delle liste");
						GridBagConstraints gbc_lblControllaIn_1 = new GridBagConstraints();
						gbc_lblControllaIn_1.anchor = GridBagConstraints.SOUTH;
						gbc_lblControllaIn_1.gridwidth = 3;
						gbc_lblControllaIn_1.insets = new Insets(0, 0, 5, 0);
						gbc_lblControllaIn_1.gridx = 12;
						gbc_lblControllaIn_1.gridy = 2;
						dataPanel.add(lblControllaIn_1, gbc_lblControllaIn_1);
					}
					{
						JLabel lblEpisodiTotali = new JLabel("Episodi Totali :");
						GridBagConstraints gbc_lblEpisodiTotali = new GridBagConstraints();
						gbc_lblEpisodiTotali.insets = new Insets(0, 0, 5, 5);
						gbc_lblEpisodiTotali.gridx = 0;
						gbc_lblEpisodiTotali.gridy = 3;
						dataPanel.add(lblEpisodiTotali, gbc_lblEpisodiTotali);
					}
					{
						totEpField = new JTextField();
						GridBagConstraints gbc_totEpField = new GridBagConstraints();
						gbc_totEpField.fill = GridBagConstraints.HORIZONTAL;
						gbc_totEpField.insets = new Insets(0, 0, 5, 5);
						gbc_totEpField.gridx = 1;
						gbc_totEpField.gridy = 3;
						dataPanel.add(totEpField, gbc_totEpField);
						totEpField.setColumns(10);
					}
					{
						listSlectionToggleButton = new JToggleButton("Seleziona Lista");
						listSlectionToggleButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								checkDialog = new SetCheckDialog();
								checkDialog.setLocationRelativeTo(searchedList);
								checkDialog.setVisible(true);
							}
						});
						GridBagConstraints gbc_listSlectionToggleButton = new GridBagConstraints();
						gbc_listSlectionToggleButton.fill = GridBagConstraints.HORIZONTAL;
						gbc_listSlectionToggleButton.gridwidth = 3;
						gbc_listSlectionToggleButton.insets = new Insets(0, 0, 5, 0);
						gbc_listSlectionToggleButton.gridx = 12;
						gbc_listSlectionToggleButton.gridy = 3;
						dataPanel.add(listSlectionToggleButton, gbc_listSlectionToggleButton);
					}
					{
						JLabel lblDurataEpisodio = new JLabel("Durata Episodio :");
						GridBagConstraints gbc_lblDurataEpisodio = new GridBagConstraints();
						gbc_lblDurataEpisodio.insets = new Insets(0, 0, 5, 5);
						gbc_lblDurataEpisodio.gridx = 0;
						gbc_lblDurataEpisodio.gridy = 4;
						dataPanel.add(lblDurataEpisodio, gbc_lblDurataEpisodio);
					}
					{
						durationField = new JTextField();
						GridBagConstraints gbc_durationField = new GridBagConstraints();
						gbc_durationField.gridwidth = 3;
						gbc_durationField.fill = GridBagConstraints.HORIZONTAL;
						gbc_durationField.insets = new Insets(0, 0, 5, 5);
						gbc_durationField.gridx = 1;
						gbc_durationField.gridy = 4;
						dataPanel.add(durationField, gbc_durationField);
						durationField.setColumns(10);
					}
					{
						JLabel lblControlloDatiAnime_1 = new JLabel("Controllo Dati Anime :");
						lblControlloDatiAnime_1.setToolTipText("Controlla la coerenza dei dati dell'anime che si sta aggiungendo con la lista in cui si sta inserendo");
						GridBagConstraints gbc_lblControlloDatiAnime_1 = new GridBagConstraints();
						gbc_lblControlloDatiAnime_1.gridwidth = 3;
						gbc_lblControlloDatiAnime_1.insets = new Insets(0, 0, 5, 0);
						gbc_lblControlloDatiAnime_1.gridx = 12;
						gbc_lblControlloDatiAnime_1.gridy = 4;
						dataPanel.add(lblControlloDatiAnime_1, gbc_lblControlloDatiAnime_1);
					}
					{
						JLabel lblFansub = new JLabel("Fansub :");
						GridBagConstraints gbc_lblFansub = new GridBagConstraints();
						gbc_lblFansub.insets = new Insets(0, 0, 5, 5);
						gbc_lblFansub.gridx = 0;
						gbc_lblFansub.gridy = 5;
						dataPanel.add(lblFansub, gbc_lblFansub);
					}
					{
						fansubComboBox = new JComboBox();
						GridBagConstraints gbc_fansubComboBox = new GridBagConstraints();
						gbc_fansubComboBox.fill = GridBagConstraints.HORIZONTAL;
						gbc_fansubComboBox.insets = new Insets(0, 0, 5, 5);
						gbc_fansubComboBox.gridwidth = 5;
						gbc_fansubComboBox.gridx = 1;
						gbc_fansubComboBox.gridy = 5;
						fansubComboBox.setModel(new DefaultComboBoxModel(AnimeIndex.getFansubList()));
						dataPanel.add(fansubComboBox, gbc_fansubComboBox);
						fansubComboBox.setSelectedItem("?????");
					}
					{
						checkDataConflictComboBoxManualAdd = new JComboBox();
						checkDataConflictComboBoxManualAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(checkDataConflictComboBoxManualAdd.getSelectedItem().equals("Attivo"))
								{
									AnimeIndex.appProp.setProperty("Check_Data_Conflict", "active");
									checkDataConflictComboBox.setSelectedItem("Attivo");
								}
								else
								{
									AnimeIndex.appProp.setProperty("Check_Data_Conflict", "disactive");
									checkDataConflictComboBox.setSelectedItem("Disattivo");
								}
							}
						});
						checkDataConflictComboBoxManualAdd.setModel(new DefaultComboBoxModel(new String[] {"Attivo", "Disattivo"}));
						GridBagConstraints gbc_checkDataConflictComboBoxManualAdd = new GridBagConstraints();
						gbc_checkDataConflictComboBoxManualAdd.fill = GridBagConstraints.HORIZONTAL;
						gbc_checkDataConflictComboBoxManualAdd.gridwidth = 3;
						gbc_checkDataConflictComboBoxManualAdd.insets = new Insets(0, 0, 5, 0);
						gbc_checkDataConflictComboBoxManualAdd.gridx = 12;
						gbc_checkDataConflictComboBoxManualAdd.gridy = 5;
						dataPanel.add(checkDataConflictComboBoxManualAdd, gbc_checkDataConflictComboBoxManualAdd);
					}
					{
						JLabel lblDataDiInizio = new JLabel("Data di Inizio :");
						GridBagConstraints gbc_lblDataDiInizio = new GridBagConstraints();
						gbc_lblDataDiInizio.insets = new Insets(0, 0, 5, 5);
						gbc_lblDataDiInizio.gridx = 0;
						gbc_lblDataDiInizio.gridy = 6;
						dataPanel.add(lblDataDiInizio, gbc_lblDataDiInizio);
					}
					{
						startDayField = new JTextField();
						GridBagConstraints gbc_startDayField = new GridBagConstraints();
						gbc_startDayField.fill = GridBagConstraints.HORIZONTAL;
						gbc_startDayField.insets = new Insets(0, 0, 5, 5);
						gbc_startDayField.gridx = 1;
						gbc_startDayField.gridy = 6;
						dataPanel.add(startDayField, gbc_startDayField);
						startDayField.setColumns(10);
					}
					{
						JLabel label = new JLabel("/");
						GridBagConstraints gbc_label = new GridBagConstraints();
						gbc_label.anchor = GridBagConstraints.EAST;
						gbc_label.insets = new Insets(0, 0, 5, 5);
						gbc_label.gridx = 2;
						gbc_label.gridy = 6;
						dataPanel.add(label, gbc_label);
					}
					{
						startMonthField = new JTextField();
						GridBagConstraints gbc_startMonthField = new GridBagConstraints();
						gbc_startMonthField.fill = GridBagConstraints.HORIZONTAL;
						gbc_startMonthField.insets = new Insets(0, 0, 5, 5);
						gbc_startMonthField.gridx = 3;
						gbc_startMonthField.gridy = 6;
						dataPanel.add(startMonthField, gbc_startMonthField);
						startMonthField.setColumns(10);
					}
					{
						JLabel label = new JLabel("/");
						GridBagConstraints gbc_label = new GridBagConstraints();
						gbc_label.anchor = GridBagConstraints.WEST;
						gbc_label.insets = new Insets(0, 0, 5, 5);
						gbc_label.gridx = 4;
						gbc_label.gridy = 6;
						dataPanel.add(label, gbc_label);
					}
					{
						startYearField = new JTextField();
						GridBagConstraints gbc_startYearField = new GridBagConstraints();
						gbc_startYearField.fill = GridBagConstraints.HORIZONTAL;
						gbc_startYearField.insets = new Insets(0, 0, 5, 5);
						gbc_startYearField.gridx = 5;
						gbc_startYearField.gridy = 6;
						dataPanel.add(startYearField, gbc_startYearField);
						startYearField.setColumns(10);
					}
					{
						JLabel lblDataDiFine = new JLabel("Data di Fine :");
						GridBagConstraints gbc_lblDataDiFine = new GridBagConstraints();
						gbc_lblDataDiFine.insets = new Insets(0, 0, 5, 5);
						gbc_lblDataDiFine.gridx = 0;
						gbc_lblDataDiFine.gridy = 7;
						dataPanel.add(lblDataDiFine, gbc_lblDataDiFine);
					}
					{
						finishDayField = new JTextField();
						GridBagConstraints gbc_finishDayField = new GridBagConstraints();
						gbc_finishDayField.fill = GridBagConstraints.HORIZONTAL;
						gbc_finishDayField.insets = new Insets(0, 0, 5, 5);
						gbc_finishDayField.gridx = 1;
						gbc_finishDayField.gridy = 7;
						dataPanel.add(finishDayField, gbc_finishDayField);
						finishDayField.setColumns(10);
					}
					{
						JLabel label = new JLabel("/");
						GridBagConstraints gbc_label = new GridBagConstraints();
						gbc_label.anchor = GridBagConstraints.EAST;
						gbc_label.insets = new Insets(0, 0, 5, 5);
						gbc_label.gridx = 2;
						gbc_label.gridy = 7;
						dataPanel.add(label, gbc_label);
					}
					{
						finishMonthField = new JTextField();
						GridBagConstraints gbc_finishMonthField = new GridBagConstraints();
						gbc_finishMonthField.fill = GridBagConstraints.HORIZONTAL;
						gbc_finishMonthField.insets = new Insets(0, 0, 5, 5);
						gbc_finishMonthField.gridx = 3;
						gbc_finishMonthField.gridy = 7;
						dataPanel.add(finishMonthField, gbc_finishMonthField);
						finishMonthField.setColumns(10);
					}
					{
						JLabel label = new JLabel("/");
						GridBagConstraints gbc_label = new GridBagConstraints();
						gbc_label.anchor = GridBagConstraints.WEST;
						gbc_label.insets = new Insets(0, 0, 5, 5);
						gbc_label.gridx = 4;
						gbc_label.gridy = 7;
						dataPanel.add(label, gbc_label);
					}
					{
						finishYearField = new JTextField();
						GridBagConstraints gbc_finishYearField = new GridBagConstraints();
						gbc_finishYearField.fill = GridBagConstraints.HORIZONTAL;
						gbc_finishYearField.insets = new Insets(0, 0, 5, 5);
						gbc_finishYearField.gridx = 5;
						gbc_finishYearField.gridy = 7;
						dataPanel.add(finishYearField, gbc_finishYearField);
						finishYearField.setColumns(10);
					}
					{
						JTextPane txtpnConLaggiuntaManuale = new JTextPane();
						txtpnConLaggiuntaManuale.setOpaque(false);
						txtpnConLaggiuntaManuale.setEditable(false);
						txtpnConLaggiuntaManuale.setText("Con l'aggiunta manuale la funzione di controllo e aggiornamento dati sar\u00E0 disattivata. Anche il collegamento alla pagina AniList dell'anime non sar\u00E0 disponibile.");
						GridBagConstraints gbc_txtpnConLaggiuntaManuale = new GridBagConstraints();
						gbc_txtpnConLaggiuntaManuale.gridwidth = 6;
						gbc_txtpnConLaggiuntaManuale.gridheight = 4;
						gbc_txtpnConLaggiuntaManuale.fill = GridBagConstraints.BOTH;
						gbc_txtpnConLaggiuntaManuale.gridx = 9;
						gbc_txtpnConLaggiuntaManuale.gridy = 6;
						dataPanel.add(txtpnConLaggiuntaManuale, gbc_txtpnConLaggiuntaManuale);
					}
					{
						JLabel lblGiornoDiUscita = new JLabel("Giorno di Uscita :");
						GridBagConstraints gbc_lblGiornoDiUscita = new GridBagConstraints();
						gbc_lblGiornoDiUscita.insets = new Insets(0, 0, 5, 5);
						gbc_lblGiornoDiUscita.gridx = 0;
						gbc_lblGiornoDiUscita.gridy = 8;
						dataPanel.add(lblGiornoDiUscita, gbc_lblGiornoDiUscita);
					}
					{
						exitdayComboBox = new JComboBox();
						GridBagConstraints gbc_exitdayComboBox = new GridBagConstraints();
						gbc_exitdayComboBox.fill = GridBagConstraints.HORIZONTAL;
						gbc_exitdayComboBox.insets = new Insets(0, 0, 5, 5);
						gbc_exitdayComboBox.gridwidth = 5;
						gbc_exitdayComboBox.gridx = 1;
						gbc_exitdayComboBox.gridy = 8;
						dataPanel.add(exitdayComboBox, gbc_exitdayComboBox);
						exitdayComboBox.setModel(new DefaultComboBoxModel(new String[] {"?????", "Luned\u00EC", "Marted\u00EC", "Mercoled\u00EC", "Gioved\u00EC", "Venerd\u00EC", "Sabato", "Domenica", "Concluso", "Irregolare", "Sospesa"}));
					}
				}
				{
					JSeparator separator = new JSeparator();
					GridBagConstraints gbc_separator = new GridBagConstraints();
					gbc_separator.fill = GridBagConstraints.BOTH;
					gbc_separator.insets = new Insets(0, 0, 5, 0);
					gbc_separator.gridx = 0;
					gbc_separator.gridy = 1;
					normalAddPanel.add(separator, gbc_separator);
				}
				{
					JPanel buttonPane = new JPanel();
					GridBagConstraints gbc_buttonPane = new GridBagConstraints();
					gbc_buttonPane.anchor = GridBagConstraints.SOUTH;
					gbc_buttonPane.fill = GridBagConstraints.HORIZONTAL;
					gbc_buttonPane.gridx = 0;
					gbc_buttonPane.gridy = 2;
					normalAddPanel.add(buttonPane, gbc_buttonPane);
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
									else if ((totEpField.getText().equalsIgnoreCase("") && currentEpisodeText.getText().equalsIgnoreCase("")) || totEpField.getText().equalsIgnoreCase(""))
									{ 
										JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
											    "Episodi totali o episodio corrente non inseriti/o",
											    "Errore",
											    JOptionPane.ERROR_MESSAGE);
									}
									else if ((!currentEpisodeText.getText().equalsIgnoreCase("") && (Integer.parseInt(totEpField.getText()) < Integer.parseInt(currentEpisodeText.getText()))))
									{
										JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
											    "Il numero totale di episodi non pu� essere inferiore al numero attuale",
											    "Errore",
											    JOptionPane.ERROR_MESSAGE);
									}
									else
									{
										
										if ((!(totEpField.getText().equalsIgnoreCase("")) && currentEpisodeText.getText().equalsIgnoreCase("")))
											{
											currentEpisodeText.setText("1");
											}
										
										String name = nameField.getText();
										String type = (String)typeComboBox.getSelectedItem();
										String currentEp =currentEpisodeText.getText();
										String totEp = totEpField.getText();
										String duration = durationField.getText();
										String fansub = (String)fansubComboBox.getSelectedItem();
										String startDay = startDayField.getText() + "/" + startMonthField.getText() + "/" + startYearField.getText();
										String finishDay = finishDayField.getText() + "/" + finishMonthField.getText() + "/" + finishYearField.getText();
										String exitDay = (String)exitdayComboBox.getSelectedItem();
										boolean bd = false;
										if (type.equalsIgnoreCase("blu-ray"))
											bd = true;
										AnimeData data = new AnimeData(currentEp, totEp, fansub, "", "", exitDay, "", "", "", startDay, startDay, finishDay, duration, bd);
										
										//TODO controllo e aggiunta nella lista corrispondente
										if (finishDay.equalsIgnoreCase("//") || type.equalsIgnoreCase("?????"))
										{
											String listName = (String) listToAdd.getSelectedItem();
											JList list = AddAnimeDialog.getJList(listName);
											SortedListModel model = AddAnimeDialog.getModel(listName);
											TreeMap<String,AnimeData> map = AddAnimeDialog.getMap(listName);
											
											map.put(name, data);
											model.addElement(name);
											AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
											list.clearSelection();
											list.setSelectedValue(name, true);
											AddAnimeDialog.this.dispose();
										}
										else
										{
											manualAnimeAdd(name, data, finishDay, type);
										}
										
									}
							}
						});

						buttonPane.add(addButton);
					}
				}
				{
					JPanel button2Panel = new JPanel();
					anilistAddPanel.add(button2Panel, BorderLayout.SOUTH);
					{
						button2Panel.setLayout(new MigLayout("", "[74.00px][159.00px,fill][361.00][][143.00px]", "[23px,grow]"));
					}
					{
						JLabel lblControllaIn = new JLabel("Controlla in :");
						lblControllaIn.setToolTipText("Verifica la presenza di conflitti all'interno delle liste");
						lblControllaIn.setFont(new Font("Tahoma", Font.PLAIN, 11));
						button2Panel.add(lblControllaIn, "cell 0 0");
					}
					{
						checkToggleButton = new JToggleButton("Seleziona Lista");
						checkToggleButton.setMinimumSize(new Dimension(122, 21));
						checkToggleButton.setMaximumSize(new Dimension(122, 21));
						checkToggleButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								checkDialog = new SetCheckDialog();
								checkDialog.setLocationRelativeTo(searchedList);
								checkDialog.setVisible(true);
							}
						});
						button2Panel.add(checkToggleButton, "cell 1 0,growx,aligny center");
					}
					addAniButton = new JButton("          Aggiungi          ");
					button2Panel.add(addAniButton, "cell 2 0,alignx center");
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
							if(checkCompletedList==false && checkAiringList==false && checkOAVList==false && checkFilmList==false && checkToSeeList==false)
							{
								if(!AnimeIndex.completedMap.containsKey(name) && !AnimeIndex.airingMap.containsKey(name) && !AnimeIndex.ovaMap.containsKey(name) && !AnimeIndex.filmMap.containsKey(name) && !AnimeIndex.completedToSeeMap.containsKey(name))
									AnimeIndex.sessionAddedAnime.add(name);
								
								if (listName.equalsIgnoreCase("anime completati"))
								{
									AnimeIndex.completedModel.addElement(name);
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
									
									AnimeIndex.completedList.clearSelection();
									AnimeIndex.completedList.setSelectedValue(name, true);
									String imagePath = AnimeIndex.completedMap.get(name).getImagePath("anime completati");
									AnimeIndex.completedSessionAnime.add(imagePath);
									if (AnimeIndex.completedDeletedAnime.contains(imagePath))
									{
										AnimeIndex.completedDeletedAnime.remove(imagePath);
									}
								}				
								else if (listName.equalsIgnoreCase("anime in corso"))
								{
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
									
									AnimeIndex.airingList.clearSelection();
									AnimeIndex.airingList.setSelectedValue(name, true);
									String imagePath = AnimeIndex.airingMap.get(name).getImagePath("anime in corso");
									AnimeIndex.airingSessionAnime.add(imagePath);
									if (AnimeIndex.airingDeletedAnime.contains(imagePath))
									{
										AnimeIndex.airingDeletedAnime.remove(imagePath);
									}
								}
								else if (listName.equalsIgnoreCase("oav"))
								{
									AnimeIndex.ovaModel.addElement(name);
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
									AnimeIndex.appProp.setProperty("Date_Release", "none");
									AnimeIndex.ovaList.clearSelection();
									AnimeIndex.ovaList.setSelectedValue(name, true);
									String imagePath = AnimeIndex.ovaMap.get(name).getImagePath("oav");
									AnimeIndex.ovaSessionAnime.add(imagePath);
									if (AnimeIndex.ovaDeletedAnime.contains(imagePath))
									{
										AnimeIndex.ovaDeletedAnime.remove(imagePath);
									}
								}
								else if (listName.equalsIgnoreCase("film"))
								{
									AnimeIndex.filmModel.addElement(name);
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
									FileManager.saveImage(imageLink, imageName, "Film");
									AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, Integer.toString(id), 
											"", "", animeType, releaseDate, finishDate, durationEp, false);
	
									AnimeIndex.filmMap.put(name, data);
									AnimeIndex.appProp.setProperty("Date_Release", "none");
									AnimeIndex.filmList.clearSelection();
									AnimeIndex.filmList.setSelectedValue(name, true);
									String imagePath = AnimeIndex.filmMap.get(name).getImagePath("film");
									AnimeIndex.filmSessionAnime.add(imagePath);
									if (AnimeIndex.filmDeletedAnime.contains(imagePath))
									{
										AnimeIndex.filmDeletedAnime.remove(imagePath);
									}
								}
								else if (listName.equalsIgnoreCase("completi da vedere"))
								{
									AnimeIndex.completedToSeeModel.addElement(name);
									AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
							else
							{
								boolean ok = true;
								if (checkCompletedList==true)
								{
									if (AnimeIndex.completedMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente in \"Anime Completati\"", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
										ok = false;
									}
								}
								if (checkAiringList==true)
								{
									if (AnimeIndex.airingMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente in \"Anime in Corso\"", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
										ok = false;
									}
								}
								if (checkOAVList==true)
								{
									if (AnimeIndex.ovaMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente in \"OAV\"", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
										ok = false;
									}
								}
								if (checkFilmList==true)
								{
									if (AnimeIndex.filmMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente in \"Film\"", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
										ok = false;
									}
								}
								if (checkToSeeList==true)
								{
									if (AnimeIndex.completedToSeeMap.containsKey(name))
									{
										JOptionPane.showMessageDialog(AddAnimeDialog.this, "Anime gi� presente in \"Completi da Vedere\"", "Errore!", JOptionPane.ERROR_MESSAGE);
										contains = true;
										ok = false;
									}
								}
								if(ok==true)
								{	
									String imagePath="";
									if(!AnimeIndex.completedMap.containsKey(name) && !AnimeIndex.airingMap.containsKey(name) && !AnimeIndex.ovaMap.containsKey(name) && !AnimeIndex.filmMap.containsKey(name) && !AnimeIndex.completedToSeeMap.containsKey(name))
										AnimeIndex.sessionAddedAnime.add(name);
									
									if (listName.equalsIgnoreCase("anime completati"))
									{
										AnimeIndex.completedModel.addElement(name);
										AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
										
										AnimeIndex.completedList.clearSelection();
										AnimeIndex.completedList.setSelectedValue(name, true);
										imagePath = AnimeIndex.completedMap.get(name).getImagePath("anime completati");
										AnimeIndex.completedSessionAnime.add(imagePath);
										if (AnimeIndex.completedDeletedAnime.contains(imagePath))
										{
											AnimeIndex.completedDeletedAnime.remove(imagePath);
										}
									}
									else if (listName.equalsIgnoreCase("anime in corso"))
									{
										AnimeIndex.airingModel.addElement(name);
										AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
										
										AnimeIndex.airingList.clearSelection();
										AnimeIndex.airingList.setSelectedValue(name, true);
										imagePath = AnimeIndex.airingMap.get(name).getImagePath("anime in corso");
										AnimeIndex.airingSessionAnime.add(imagePath);
										if (AnimeIndex.airingDeletedAnime.contains(imagePath))
										{
											AnimeIndex.airingDeletedAnime.remove(imagePath);
										}
									}
									else if (listName.equalsIgnoreCase("oav"))
									{
										AnimeIndex.ovaModel.addElement(name);
										AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
										AnimeIndex.appProp.setProperty("Date_Release", "none");
										AnimeIndex.ovaList.clearSelection();
										AnimeIndex.ovaList.setSelectedValue(name, true);
										imagePath = AnimeIndex.ovaMap.get(name).getImagePath("oav");
										AnimeIndex.ovaSessionAnime.add(imagePath);
										if (AnimeIndex.ovaDeletedAnime.contains(imagePath))
										{
											AnimeIndex.ovaDeletedAnime.remove(imagePath);
										}
									}
									else if (listName.equalsIgnoreCase("film"))
									{
										AnimeIndex.filmModel.addElement(name);
										AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
										AnimeIndex.appProp.setProperty("Date_Release", "none");
										AnimeIndex.filmList.clearSelection();
										AnimeIndex.filmList.setSelectedValue(name, true);
										imagePath = AnimeIndex.filmMap.get(name).getImagePath("film");
										AnimeIndex.filmSessionAnime.add(imagePath);
										if (AnimeIndex.filmDeletedAnime.contains(imagePath))
										{
											AnimeIndex.filmDeletedAnime.remove(imagePath);
										}
									}
									else if (listName.equalsIgnoreCase("completi da vedere"))
									{
										AnimeIndex.completedToSeeModel.addElement(name);
										AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
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
										
										AnimeIndex.completedToSeeList.clearSelection();
										AnimeIndex.completedToSeeList.setSelectedValue(name, true);
										imagePath = AnimeIndex.completedToSeeMap.get(name).getImagePath("completi da vedere");
										AnimeIndex.completedToSeeSessionAnime.add(imagePath);
										if (AnimeIndex.completedToSeeDeletedAnime.contains(imagePath))
										{
											AnimeIndex.completedToSeeDeletedAnime.remove(imagePath);
										}
									}
								}
							}
							if(AnimeIndex.filtro != 9)
						    {
							    Filters.removeFilters();
						    }
							if (!contains && !keepOpen.isSelected())
							{
							JButton but = (JButton) e.getSource();
							JDialog dialog = (JDialog) but.getTopLevelAncestor();
							dialog.dispose();
							}
							AddAnimeDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					});
					{
						JLabel lblControlloDatiAnime = new JLabel("Controllo Dati Anime :");
						lblControlloDatiAnime.setToolTipText("Controlla la coerenza dei dati dell'anime che si sta aggiungendo con la lista in cui si sta inserendo");
						lblControlloDatiAnime.setFont(new Font("Tahoma", Font.PLAIN, 11));
						button2Panel.add(lblControlloDatiAnime, "cell 3 0,alignx right");
					}
					{
						checkDataConflictComboBox = new JComboBox();
						checkDataConflictComboBox.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent arg0) {
								if(checkDataConflictComboBox.getSelectedItem().equals("Attivo"))
								{
									AnimeIndex.appProp.setProperty("Check_Data_Conflict", "active");
									checkDataConflictComboBoxManualAdd.setSelectedItem("Attivo");
								}
								else
								{
									AnimeIndex.appProp.setProperty("Check_Data_Conflict", "disactive");
									checkDataConflictComboBoxManualAdd.setSelectedItem("Disattivo");
								}
							}
						});
						checkDataConflictComboBox.setModel(new DefaultComboBoxModel(new String[] {"Attivo", "Disattivo"}));
						button2Panel.add(checkDataConflictComboBox, "cell 4 0,growx");
					}
				}
			}
		}
		
		listToAddAniComboBox.setSelectedItem(AnimeIndex.getList());
		listToAdd.setSelectedItem(AnimeIndex.getList());
		
		if (AnimeIndex.appProp.getProperty("List_to_Check").equalsIgnoreCase("all"))
		{
			checkCompletedList = true;
			checkAiringList = true;
			checkOAVList = true;
			checkFilmList = true;
			checkToSeeList = true;
			checkToggleButton.setText("Tutte le liste");
			checkToggleButton.setSelected(true);
			listSlectionToggleButton.setText("Tutte le liste");
			listSlectionToggleButton.setSelected(true);
		}
		else
		{
			if (AnimeIndex.addToPreviousList != null && AnimeIndex.addToPreviousList.equalsIgnoreCase((String)listToAddAniComboBox.getSelectedItem()) && AddAnimeDialog.checkCompletedList == false && AddAnimeDialog.checkAiringList == false && AddAnimeDialog.checkOAVList == false && AddAnimeDialog.checkFilmList == false && AddAnimeDialog.checkToSeeList == false)
			{
				checkToggleButton.setText("Nessuna Lista");
				listSlectionToggleButton.setText("Nessuna Lista");
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
				listSlectionToggleButton.setText("Anime Completati");
				listSlectionToggleButton.setSelected(true);
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
				listSlectionToggleButton.setText("Anime in Corso");
				listSlectionToggleButton.setSelected(true);
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
				listSlectionToggleButton.setText("OAV");
				listSlectionToggleButton.setSelected(true);
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
				listSlectionToggleButton.setText("Film");
				listSlectionToggleButton.setSelected(true);
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
				listSlectionToggleButton.setText("Completi Da Vedere");
				listSlectionToggleButton.setSelected(true);
			}
		}
	}
	
	
	private static String checkDataConflict(String finishDate, String type)
	{
		String map = "";
		
		String listName = (String) listToAdd.getSelectedItem();
		
		if (listName.equalsIgnoreCase("anime completati"))
		{
			map = "Anime Completati";
			Date today = new Date();
			Date finish = null;
			
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				finish = format.parse(finishDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (today.before(finish) && (type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � ancora in corso. Vuoi aggiungerlo agli \"Anime in Corso\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (choiche == 0)
					map = "Anime in Corso";
				else
					map = "Anime Completati";
			}
			
			else if (!(type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				if (type.equalsIgnoreCase("Movie"))
				{
					int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un film. Vuoi aggiungerlo ai \"Film\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "Film";
					else
						map = "Anime Completati";
				}
				
				if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
				{
					int choiche;
					if(type.equalsIgnoreCase("Special"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � uno Special. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else if(type.equalsIgnoreCase("Ova"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un OAV. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un ONA. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "OAV";
					else
						map = "Anime Completati";
				}
			}
		}
		
		else if (listName.equalsIgnoreCase("anime in corso"))
		{
			map = "Anime in Corso";
			Date today = new Date();
			Date finish = null;
			
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				finish = format.parse(finishDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (today.after(finish) && (type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � concluso. Vuoi aggiungerlo agli anime \"Completi da Vedere\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (choiche == 0)
					map = "Completi Da Vedere";
				else
					map = "Anime in Corso";
			}
			
			else if (!(type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				if (type.equalsIgnoreCase("Movie"))
				{
					int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un film. Vuoi aggiungerlo ai \"Film\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "Film";
					else
						map = "Anime in Corso";
				}
				
				if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
				{
					int choiche;
					if(type.equalsIgnoreCase("Special"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � uno Special. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else if(type.equalsIgnoreCase("Ova"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un OAV. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un ONA. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "OAV";
					else
						map = "Anime in Corso";
				}
			}
		}
		
		else if (listName.equalsIgnoreCase("oav"))
		{
			map = "OAV";
			if (!(type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				if (type.equalsIgnoreCase("Movie"))
				{
					int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un film. Vuoi aggiungerlo ai \"Film\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "Film";
					else
						map = "OAV";
				}

			}
			
			else if ((type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				Date today = new Date();
				Date finish = null;
				
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					finish = format.parse(finishDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (today.before(finish))
				{
				int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime non � un OAV o uno Special. Vuoi aggiungerlo agli \"Anime in Corso\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (choiche == 0)
					map = "Anime in Corso";
				else
					map = "OAV";
				}
				else if (today.after(finish))
				{
					int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime non � un OAV o uno Special. Vuoi aggiungerlo agli anime \"Completi da Vedere\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "Completi Da Vedere";
					else
						map = "OAV";
				}
			}

		}
		
		else if (listName.equalsIgnoreCase("film"))
		{
			
			map = "Film";
			
			if (!(type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
				{
					int choiche;
					if(type.equalsIgnoreCase("Special"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � uno Special. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else if(type.equalsIgnoreCase("Ova"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un OAV. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un ONA. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "OAV";
					else
						map = "Film";
				}

			}
			
			else if ((type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				Date today = new Date();
				Date finish = null;
				
				try {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					finish = format.parse(finishDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (today.before(finish))
				{
				int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime non � un OAV o uno Special. Vuoi aggiungerlo agli \"Anime in Corso\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (choiche == 0)
					map = "Anime in Corso";
				else
					map = "Film";
				}
				else if (today.after(finish))
				{
					int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime non � un OAV o uno Special. Vuoi aggiungerlo agli anime \"Completi da Vedere\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "Completi Da Vedere";
					else
						map = "Film";
				}
			}

		}
		
		else if (listName.equalsIgnoreCase("completi da vedere"))
		{
			map = "Completi Da Vedere";
			Date today = new Date();
			Date finish = null;
			
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				finish = format.parse(finishDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (today.before(finish) && (type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � ancora in corso. Vuoi aggiungerlo agli \"Anime in Corso\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (choiche == 0)
					map = "Anime in Corso";
				else
					map = "Completi Da Vedere";
			}
			
			else if (!(type.equalsIgnoreCase("tv") ||type.equalsIgnoreCase("tv short")))
			{
				if (type.equalsIgnoreCase("Movie"))
				{
					int choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un film. Vuoi aggiungerlo ai \"Film\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "Film";
					else
						map = "Completi Da Vedere";
				}
				
				if (type.equalsIgnoreCase("Special") || type.equalsIgnoreCase("Ova") || type.equalsIgnoreCase("Ona"))
				{
					int choiche;
					if(type.equalsIgnoreCase("Special"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � uno Special. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else if(type.equalsIgnoreCase("Ova"))
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un OAV. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					else
						choiche = JOptionPane.showConfirmDialog(AddAnimeDialog.listToAddAniComboBox, "L'anime � un ONA. Vuoi aggiungerlo alla sezione \"OAV\"?", "Conflitto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (choiche == 0)
						map = "OAV";
					else
						map = "Completi Da Vedere";
				}
			}

		}
		
		return map;	
	}
		
	private static JList getJList(String listName)
	{
		JList list= null;
			if (listName.equalsIgnoreCase("anime completati"))
			{	
				list = AnimeIndex.completedList;						
			}				
			else if (listName.equalsIgnoreCase("anime in corso"))
			{
				list = AnimeIndex.airingList;
			}
			else if (listName.equalsIgnoreCase("oav"))
			{
				list = AnimeIndex.ovaList;
			}
			else if (listName.equalsIgnoreCase("film"))
			{
				list = AnimeIndex.filmList;
			}
			else if (listName.equalsIgnoreCase("completi da vedere"))
			{
				list = AnimeIndex.completedToSeeList;
			}
			
		return list;
	}
	
	private static TreeMap getMap(String listName)
	{
		TreeMap map= null;
		if (listName.equalsIgnoreCase("anime completati"))
		{	
			map = AnimeIndex.completedMap;						
		}				
		else if (listName.equalsIgnoreCase("anime in corso"))
		{
			map = AnimeIndex.airingMap;
		}
		else if (listName.equalsIgnoreCase("oav"))
		{
			map = AnimeIndex.ovaMap;
		}
		else if (listName.equalsIgnoreCase("film"))
		{
			map = AnimeIndex.filmMap;
		}
		else if (listName.equalsIgnoreCase("completi da vedere"))
		{
			map = AnimeIndex.completedToSeeMap;
		}
		return map;
	}
	
	private static SortedListModel getModel(String listName)
	{
		SortedListModel model= null;
			if (listName.equalsIgnoreCase("anime completati"))
			{	
				model = AnimeIndex.completedModel;						
			}				
			else if (listName.equalsIgnoreCase("anime in corso"))
			{
				model = AnimeIndex.airingModel;
			}
			else if (listName.equalsIgnoreCase("oav"))
			{
				model = AnimeIndex.ovaModel;
			}
			else if (listName.equalsIgnoreCase("film"))
			{
				model = AnimeIndex.filmModel;
			}
			else if (listName.equalsIgnoreCase("completi da vedere"))
			{
				model = AnimeIndex.completedToSeeModel;
			}
			
		return model;
	}

	private static void checkAnimeAlreadyAdded(String name, String listName, AnimeData data)
	{
		JList list = AddAnimeDialog.getJList(listName);
		SortedListModel model = AddAnimeDialog.getModel(listName);
		TreeMap<String,AnimeData> map = AddAnimeDialog.getMap(listName);
		
		boolean contains = false;
		if(checkCompletedList==false && checkAiringList==false && checkOAVList==false && checkFilmList==false && checkToSeeList==false)
		{
			if(!AnimeIndex.completedMap.containsKey(name) && !AnimeIndex.airingMap.containsKey(name) && !AnimeIndex.ovaMap.containsKey(name) && !AnimeIndex.filmMap.containsKey(name) && !AnimeIndex.completedToSeeMap.containsKey(name))
				AnimeIndex.sessionAddedAnime.add(name);
			
			map.put(name, data);
			model.addElement(name);
			AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
			list.clearSelection();
			list.setSelectedValue(name, true);				
			
		}
		
		else
		{
			boolean ok = true;
			if (checkCompletedList==true)
			{
				if (AnimeIndex.completedMap.containsKey(name))
				{
					JOptionPane.showMessageDialog(AddAnimeDialog.tabbedPane, "Anime gi� presente in \"Anime Completati\"", "Errore!", JOptionPane.ERROR_MESSAGE);
					contains = true;
					ok = false;
				}
			}
			if (checkAiringList==true)
			{
				if (AnimeIndex.airingMap.containsKey(name))
				{
					JOptionPane.showMessageDialog(AddAnimeDialog.tabbedPane, "Anime gi� presente in \"Anime in Corso\"", "Errore!", JOptionPane.ERROR_MESSAGE);
					contains = true;
					ok = false;
				}
			}
			if (checkOAVList==true)
			{
				if (AnimeIndex.ovaMap.containsKey(name))
				{
					JOptionPane.showMessageDialog(AddAnimeDialog.tabbedPane, "Anime gi� presente in \"OAV\"", "Errore!", JOptionPane.ERROR_MESSAGE);
					contains = true;
					ok = false;
				}
			}
			if (checkFilmList==true)
			{
				if (AnimeIndex.filmMap.containsKey(name))
				{
					JOptionPane.showMessageDialog(AddAnimeDialog.tabbedPane, "Anime gi� presente in \"Film\"", "Errore!", JOptionPane.ERROR_MESSAGE);
					contains = true;
					ok = false;
				}
			}
			if (checkToSeeList==true)
			{
				if (AnimeIndex.completedToSeeMap.containsKey(name))
				{
					JOptionPane.showMessageDialog(AddAnimeDialog.tabbedPane, "Anime gi� presente in \"Completi da Vedere\"", "Errore!", JOptionPane.ERROR_MESSAGE);
					contains = true;
					ok = false;
				}
			}
			
			if(ok==true)
			{	
				if(!AnimeIndex.completedMap.containsKey(name) && !AnimeIndex.airingMap.containsKey(name) && !AnimeIndex.ovaMap.containsKey(name) && !AnimeIndex.filmMap.containsKey(name) && !AnimeIndex.completedToSeeMap.containsKey(name))
					AnimeIndex.sessionAddedAnime.add(name);
					
				map.put(name, data);
				model.addElement(name);
				AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
				list.clearSelection();
				list.setSelectedValue(name, true);	

			}
			
			if(AnimeIndex.filtro != 9)
		    {
			    Filters.removeFilters();
		    }
			AnimeIndex.animeDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if (!contains && !keepOpen.isSelected())
			{
			AnimeIndex.animeDialog.dispose();
			}
		}
	}
	
	private static void manualAnimeAdd(String name, AnimeData data, String finishDay, String type)
	{
		String list = AddAnimeDialog.checkDataConflict(finishDay, type);
		AddAnimeDialog.checkAnimeAlreadyAdded(name, list, data);
	}
}
