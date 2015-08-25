package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;

import util.AnimeData;
import util.AnimeIndexProperties;
import util.FileManager;
import util.Filters;
import util.ImageChooserFilter;
import util.SearchBar;
import util.SortedListModel;
import util.Updater;
import util.task.CheckUpdateTask;
import util.task.LoadingTask;
import util.task.ReleasedAnimeTask;
import util.task.UpdateAnimeDataTask;
import util.window.AddAnimeDialog;
import util.window.AddFansubDialog;
import util.window.AnimeInformation;
import util.window.CreditDialog;
import util.window.ExitSaveDialog;
import util.window.PreferenceDialog;
import util.window.SetFilterDialog;
import util.window.UpdateDialog;
import util.window.WishlistDialog;
//import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;

//TODO fixare "IL BUG"
//TODO fare sistema di controllo (controllo inserimento anime)
//TODO finire aggiunta anime manuale (anche disattivazione dei pulsanti anilistInfo e aggiornamento dati)
public class AnimeIndex extends JFrame
{
	public static final String VERSION = "1.0.0";
	public static final String NEW_VERSION = "MyAnimeManager_Setup.exe";
	public static JPanel mainFrame;
	public static JPanel cardContainer;
	public static AnimeInformation animeInformation;
	public static AnimeIndex frame;
	
	public static JList completedToSeeList;
	public static JList filmList;
	public static JList airingList;
	public static JList completedList;
	public static JList ovaList;
	public static JList searchList;
	public static JList filterList;
	
	public static SortedListModel completedModel = new SortedListModel();
	public static SortedListModel airingModel = new SortedListModel();
	public static SortedListModel ovaModel = new SortedListModel();
	public static SortedListModel filmModel = new SortedListModel();
	public static SortedListModel completedToSeeModel = new SortedListModel();
	public static SortedListModel searchModel = new SortedListModel();
	public static SortedListModel filterModel = new SortedListModel();
	
	private static String[] fansubList = {};
	public static TreeMap<String,String> fansubMap = new TreeMap<String,String>();
	public static Properties appProp;
	public static TreeMap<String,AnimeData> completedMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> airingMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> ovaMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> filmMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> completedToSeeMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,Integer> wishlistMap = new TreeMap<String,Integer>();
	public static TreeMap<String,String> shiftsRegister = new TreeMap<String,String>();
	
	public static ArrayList<String> completedSessionAnime = new ArrayList();
	public static ArrayList<String> airingSessionAnime = new ArrayList();
	public static ArrayList<String> ovaSessionAnime = new ArrayList();
	public static ArrayList<String> filmSessionAnime = new ArrayList();
	public static ArrayList<String> completedToSeeSessionAnime = new ArrayList();
	public static ArrayList<String> sessionAddedAnime = new ArrayList();
	
	public static ArrayList<String> completedDeletedAnime = new ArrayList();
	public static ArrayList<String> airingDeletedAnime = new ArrayList();
	public static ArrayList<String> ovaDeletedAnime = new ArrayList();
	public static ArrayList<String> filmDeletedAnime = new ArrayList();
	public static ArrayList<String> completedToSeeDeletedAnime = new ArrayList();
	
	public static ArrayList<String> exclusionAnime = new ArrayList();
	
	private JButton addButton;
	public static JButton deleteButton;
	public static JComboBox animeTypeComboBox;
	public static AddAnimeDialog animeDialog;
	public static SearchBar searchBar;
	public static AddFansubDialog fansubDialog;
	public static JButton setFilterButton;
	private String list;
	public static boolean[] filterArray = {false, false, false, false, false, false, false, false, false};
	public static int filtro = 9;
	public static Font segui;
	public static String addToPreviousList;
	public static WishlistDialog wishlistDialog;
	public static boolean openReleaseDialog;
	public static boolean activeUpdate;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		appProp = AnimeIndexProperties.createProperties();
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
			         UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
			        } catch (Exception e) {
			          System.out.println("Substance Graphite failed to initialize");
			        }
				try {
			          UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
			        } catch (Exception e) {
			          System.out.println("Substance Graphite failed to initialize");
			        }
				try {
					segui = segui();
					frame = new AnimeIndex();
					frame.setVisible(true);
					wishlistDialog = new WishlistDialog();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AnimeIndex()
	{
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				if (wishlistDialog.isShowing())
					wishlistDialog.setLocation(AnimeIndex.mainFrame.getLocationOnScreen().x - 182,AnimeIndex.mainFrame.getLocationOnScreen().y);
			}
		});
		setTitle("My Anime Manager");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/icon.png")));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {	
				try{
					saveModifiedInformation();
					ExitSaveDialog exitDialog = new ExitSaveDialog();
					exitDialog.setLocationRelativeTo(mainFrame);
					exitDialog.setVisible(true);
				}
				catch (Exception e)
				{
					ExitSaveDialog exitDialog = new ExitSaveDialog();
					exitDialog.setLocationRelativeTo(mainFrame);
					exitDialog.setVisible(true);
				}
				
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
				LoadingTask loadTask = new LoadingTask();
				loadTask.execute();
				File file = new File(FileManager.getAppDataPath() + File.separator + "Update" + File.separator + NEW_VERSION);
				if(file.isFile())
					file.delete();
				CheckUpdateTask updateTask = new CheckUpdateTask();
				try {
					updateTask.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//setBounds(100, 100, 700, 385);
		setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() /5, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() /7, 800, 520);
		this.setMinimumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Opzioni");
		menuBar.add(mnMenu);
		
		JMenuItem mntmPreferenze = new JMenuItem("Preferenze");
		mntmPreferenze.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/System-Preferences-icon.png")));
		mntmPreferenze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreferenceDialog preference = new PreferenceDialog();
				preference.setLocationRelativeTo(mainFrame);
				preference.setVisible(true);
			}
		});
		mnMenu.add(mntmPreferenze);
		
		JSeparator separator = new JSeparator();
		mnMenu.add(separator);
		
		JMenu mnElimina = new JMenu("Elimina");
		mnElimina.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/DeleteRed.png")));
		mnMenu.add(mnElimina);
		
		JMenuItem mntmDeleteImage = new JMenuItem("Tutti i Dati");
		mnElimina.add(mntmDeleteImage);
		
		JMenuItem mntmEliminaFansub = new JMenuItem("Tutti i Fansub");
		mntmEliminaFansub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti i Fansub?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Fansub.anaconda"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				String[] newFansub = {};
				fansubList = newFansub;
				fansubMap.clear();
				animeInformation.fansubComboBox.removeAllItems();
				addToFansub("?????");
				animeInformation.setFansubComboBox();
				animeInformation.setBlank();
				JOptionPane.showMessageDialog(mainFrame, "Fansub eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JSeparator separator_9 = new JSeparator();
		mnElimina.add(separator_9);
		mnElimina.add(mntmEliminaFansub);		
		
		JSeparator separator_10 = new JSeparator();
		mnElimina.add(separator_10);
		JMenu mnEliminaLista = new JMenu("Tutta la Lista");
		mnElimina.add(mnEliminaLista);
		
		JMenuItem mntmAnimeCompletati = new JMenuItem("Anime Completati");
		mntmAnimeCompletati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti gli Anime Completati?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Anime" + File.separator + "completed.anaconda"));
					FileManager.deleteData(new File(FileManager.getImageFolderPath() + "Completed"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				completedModel.clear();
				
				completedMap.clear();
				
				animeInformation.setBlank();
				JOptionPane.showMessageDialog(mainFrame, "Anime Completati eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnEliminaLista.add(mntmAnimeCompletati);
		
		JMenuItem mntmAnimeInCorso = new JMenuItem("Anime in Corso");
		mntmAnimeInCorso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti gli Anime in Corso?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Anime" + File.separator + "airing.anaconda"));
					FileManager.deleteData(new File(FileManager.getImageFolderPath() + "Airing"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				airingModel.clear();
				
				airingMap.clear();
				
				animeInformation.setBlank();
				JOptionPane.showMessageDialog(mainFrame, "Anime in Corso eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JSeparator separator_11 = new JSeparator();
		mnEliminaLista.add(separator_11);
		mnEliminaLista.add(mntmAnimeInCorso);
		
		JMenuItem mntmOav = new JMenuItem("OAV");
		mntmOav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti gli OAV?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Anime" + File.separator + "ova.anaconda"));
					FileManager.deleteData(new File(FileManager.getImageFolderPath() + "Ova"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ovaModel.clear();
				
				ovaMap.clear();
				
				animeInformation.setBlank();
				JOptionPane.showMessageDialog(mainFrame, "OAV eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JSeparator separator_12 = new JSeparator();
		mnEliminaLista.add(separator_12);
		mnEliminaLista.add(mntmOav);
		
		JMenuItem mntmFilm = new JMenuItem("Film");
		mntmFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti i Film", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Anime" + File.separator + "film.anaconda"));
					FileManager.deleteData(new File(FileManager.getImageFolderPath() + "Film"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				filmModel.clear();
				
				filmMap.clear();
				
				animeInformation.setBlank();
				JOptionPane.showMessageDialog(mainFrame, "Film eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JSeparator separator_13 = new JSeparator();
		mnEliminaLista.add(separator_13);
		mnEliminaLista.add(mntmFilm);
		
		JMenuItem mntmCompletiDaVedere = new JMenuItem("Completi da Vedere");
		mntmCompletiDaVedere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti gli Anime Completi da Vedere", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Anime" + File.separator + "toSee.anaconda"));
					FileManager.deleteData(new File(FileManager.getImageFolderPath() + "Completed to See"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				completedToSeeModel.clear();
				
				completedToSeeMap.clear();
				
				animeInformation.setBlank();
				JOptionPane.showMessageDialog(mainFrame, "Completi da Vedere eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JSeparator separator_14 = new JSeparator();
		mnEliminaLista.add(separator_14);
		mnEliminaLista.add(mntmCompletiDaVedere);
		
		JSeparator separator_15 = new JSeparator();
		mnEliminaLista.add(separator_15);
		
		JMenuItem mntmWishlist_1 = new JMenuItem("Wishlist");
		mntmWishlist_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti gli Anime nella Wishlist?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "Anime" + File.separator + "wishlist.anaconda"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				WishlistDialog.wishListModel.clear();
				WishlistDialog.wishListSearchModel.clear();
				
				wishlistMap.clear();
				
				JOptionPane.showMessageDialog(mainFrame, "Anime nella Wishlist eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnEliminaLista.add(mntmWishlist_1);
		mntmDeleteImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Vuoi cancellare tutti i dati?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (shouldCancel == 0)
				{
				try {
					FileManager.deleteData(new File(System.getenv("APPDATA") + File.separator + "MyAnimeManager"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				completedModel.clear();
				airingModel.clear();
				completedToSeeModel.clear();
				filmModel.clear();
				ovaModel.clear();
				
				completedMap.clear();
				airingModel.clear();
				completedToSeeMap.clear();
				filmMap.clear();
				ovaMap.clear();

				String[] newFansub = {};
				fansubList = newFansub;
				
				exclusionAnime.clear();
				WishlistDialog.wishListModel.clear();
				
				JList list = getJList();
				list.clearSelection();
				animeInformation.setBlank();
				animeInformation.fansubComboBox.removeAllItems();
				JOptionPane.showMessageDialog(mainFrame, "Dati eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JMenu mnModifica = new JMenu("Modifica");
		menuBar.add(mnModifica);
		
		JMenuItem mntmImmagineAnime = new JMenuItem("Immagine Anime");
		mntmImmagineAnime.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/picture.png")));
		mntmImmagineAnime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = AnimeIndex.animeInformation.lblAnimeName.getText();
				if(!name.equalsIgnoreCase("Anime") && name!=null)
				{
				int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainFrame, "La modifica sarà applicata all'anime attualmente selezinato.\n\rL'operazione non potra' essere annullata.\n\rContinuare?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(shouldCancel==0){
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
					try {
						BufferedImage bufimg = ImageIO.read (file);
						String imageName = name.replaceAll("\\\\", "_");
						imageName = imageName.replaceAll("/", "_");
						imageName = imageName.replaceAll(":", "_");
						imageName = imageName.replaceAll("\\*", "_");
						imageName = imageName.replaceAll("\\?", "_");
						imageName = imageName.replaceAll("\"", "_");
						imageName = imageName.replaceAll(">", "_");
						imageName = imageName.replaceAll("<", "_");
						String listName = AnimeIndex.getList();
						String folder = "";
						if (listName.equalsIgnoreCase("anime completati"))
							folder = "Completed";
						else if (listName.equalsIgnoreCase("anime in corso"))
							folder = "Airing";
						else if (listName.equalsIgnoreCase("oav"))
							folder = "Ova";
						else if (listName.equalsIgnoreCase("film"))
							folder = "Film";
						else if (listName.equalsIgnoreCase("completi da vedere"))
							folder = "Completed to See";
						FileManager.saveNewImage(dir, imageName, folder);
						if(!exclusionAnime.contains(name))
						{	
							JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Impostazione avvenuta correttamente.\n\rAl fine di mantenere l'immagine da voi impostata\n\rl'anime è stato aggiunto alla lista\n\rdegli esclusi dal Controllo Dati Automatico.", "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
							exclusionAnime.add(name);
						}
						else
							JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Impostazione avvenuta correttamente.", "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
						TreeMap<String,AnimeData> map = AnimeIndex.getMap();
						String list = AnimeIndex.getList();
						AnimeData data = map.get(name);
						String path = data.getImagePath(list);
						File imgFile = new File(path);
						if(imgFile.exists())
							AnimeIndex.animeInformation.setImage(path);
						else
							AnimeIndex.animeInformation.setImage("default");
					} catch (IOException e1) {
						e1.printStackTrace();
					}					
				}
				}
				}
				else
					JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nessun anime selezionato.\n\rSelezionarne uno da una delle liste e riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
		});
		mnModifica.add(mntmImmagineAnime);
		
		JMenu mnVisualizza = new JMenu("Visualizza");
		menuBar.add(mnVisualizza);
		
		JMenuItem mntmNuoveUscite = new JMenuItem("Nuove Uscite");
		mntmNuoveUscite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openReleaseDialog = true;
				ReleasedAnimeTask task = new ReleasedAnimeTask();
				task.execute();
			}
		});
		mnVisualizza.add(mntmNuoveUscite);
		
		JSeparator separator_2 = new JSeparator();
		mnVisualizza.add(separator_2);
		
		JMenuItem mntmWishlist = new JMenuItem("WishList");
		mnVisualizza.add(mntmWishlist);
		mntmWishlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if (!wishlistDialog.isShowing())
				{
					wishlistDialog.setLocation(AnimeIndex.mainFrame.getLocationOnScreen().x ,AnimeIndex.mainFrame.getLocationOnScreen().y);
					wishlistDialog.setVisible(true);
					 new Timer(1, new ActionListener() {
			               public void actionPerformed(ActionEvent e) {
			            	   wishlistDialog.setLocation(wishlistDialog.getLocationOnScreen().x - 2, AnimeIndex.mainFrame.getLocationOnScreen().y);
			            	   if (wishlistDialog.getLocationOnScreen().x == AnimeIndex.mainFrame.getLocationOnScreen().x - 182) {
			                     ((Timer) e.getSource()).stop();
			            }
			               }
			            }).start();
					 
//					wishlistDialog.setLocation(AnimeIndex.mainFrame.getLocationOnScreen().x - 175,AnimeIndex.mainFrame.getLocationOnScreen().y);
//					wishlistDialog.setVisible(true);
				}
				else
				{
					new Timer(1, new ActionListener() {
			               public void actionPerformed(ActionEvent e) {

			            	   wishlistDialog.setLocation(wishlistDialog.getLocationOnScreen().x + 2, AnimeIndex.mainFrame.getLocationOnScreen().y);
			            	   AnimeIndex.mainFrame.requestFocus();
			            	   if (wishlistDialog.getLocationOnScreen().x == AnimeIndex.mainFrame.getLocationOnScreen().x) {
			                     ((Timer) e.getSource()).stop();
			 					wishlistDialog.dispose();
			            }
			               }
			            }).start();
				}
			}
		});
		
		JMenu mnAggiungi = new JMenu("Aggiungi");
		menuBar.add(mnAggiungi);
		
		JMenuItem mntmAggiungiFansub = new JMenuItem("Nuovo Fansub");
		mntmAggiungiFansub.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/Aegisub.png")));
		mnAggiungi.add(mntmAggiungiFansub);
		mntmAggiungiFansub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fansubDialog = new AddFansubDialog();
				fansubDialog.setLocationRelativeTo(mainFrame);
				fansubDialog.setVisible(true);
			}
		});
		
		JMenu mnAnichart = new JMenu("Apri");
		menuBar.add(mnAnichart);
		
		JMenuItem mntmAnichart = new JMenuItem("AniChart");
		mntmAnichart.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/AniC.png")));
		mnAnichart.add(mntmAnichart);
		mntmAnichart.setHorizontalAlignment(SwingConstants.LEFT);
		
		JSeparator separator_4 = new JSeparator();
		mnAnichart.add(separator_4);
		
		JMenuItem mntmAnilist = new JMenuItem("AniList");
		mntmAnilist.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/anilist.png")));
		mntmAnilist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String link = "https://anilist.co/";
				try {
					URI uriLink = new URI(link);
					Desktop.getDesktop().browse(uriLink);
				} catch (URISyntaxException a) {
				} catch (IOException a) {
			}
			}
		});
		mnAnichart.add(mntmAnilist);
		
		JSeparator separator_5 = new JSeparator();
		mnAnichart.add(separator_5);
		
		JMenuItem mntmMyAnimeList = new JMenuItem("MyAnimeList");
		mntmMyAnimeList.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/MAL.png")));
		mntmMyAnimeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String link = "http://myanimelist.net/";
				try {
					URI uriLink = new URI(link);
					Desktop.getDesktop().browse(uriLink);
				} catch (URISyntaxException a) {
				} catch (IOException a) {
			}
			}
		});
		mnAnichart.add(mntmMyAnimeList);
		
		JSeparator separator_6 = new JSeparator();
		mnAnichart.add(separator_6);
		
		JMenuItem mntmAnimeclick = new JMenuItem("AnimeClick");
		mntmAnimeclick.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/AC.png")));
		mntmAnimeclick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String link = "http://www.animeclick.it/";
				try {
					URI uriLink = new URI(link);
					Desktop.getDesktop().browse(uriLink);
				} catch (URISyntaxException a) {
				} catch (IOException a) {
			}
			}
		});
		mnAnichart.add(mntmAnimeclick);
		
		JSeparator separator_8 = new JSeparator();
		mnAnichart.add(separator_8);
		
		JMenuItem mntmHummingbird = new JMenuItem("Hummingbird");
		mntmHummingbird.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/hummingbird.me.png")));
		mntmHummingbird.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String link = "https://hummingbird.me/";
				try {
					URI uriLink = new URI(link);
					Desktop.getDesktop().browse(uriLink);
				} catch (URISyntaxException a) {
				} catch (IOException a) {
			}
			}
		});
		mnAnichart.add(mntmHummingbird);
		
		JMenuItem mntmAnidb = new JMenuItem("AniDB");
		mntmAnidb.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/anidb_icon.png")));
		mntmAnidb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String link = "http://anidb.net/";
				try {
					URI uriLink = new URI(link);
					Desktop.getDesktop().browse(uriLink);
				} catch (URISyntaxException a) {
				} catch (IOException a) {
			}
			}
		});
		
		JSeparator separator_3 = new JSeparator();
		mnAnichart.add(separator_3);
		mnAnichart.add(mntmAnidb);
		
		JMenu mnInfo = new JMenu("Info");
		menuBar.add(mnInfo);
		
		JMenuItem mntmControlloAggiornamenti = new JMenuItem("Controllo aggiornamenti");
		mntmControlloAggiornamenti.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/Update.png")));
		mnInfo.add(mntmControlloAggiornamenti);
		
		JSeparator separator_1 = new JSeparator();
		mnInfo.add(separator_1);
		
		JMenu mnHelp = new JMenu("Aiuto");
		mnHelp.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/icon2.png")));
		mnInfo.add(mnHelp);
		
		JMenuItem mntmCredit = new JMenuItem("Crediti");
		mnHelp.add(mntmCredit);
		
		JSeparator separator_7 = new JSeparator();
		mnHelp.add(separator_7);
		
		JMenuItem mntmSupporters = new JMenuItem("Supporters");
		mnHelp.add(mntmSupporters);
		mntmCredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreditDialog credit = new CreditDialog();
				credit.setLocationRelativeTo(AnimeIndex.mainFrame);
				credit.setVisible(true);
				
			}
		});
		mntmControlloAggiornamenti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String updatedVersion = null;
					try {
						updatedVersion = Updater.getLatestVersion();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (updatedVersion.equalsIgnoreCase(VERSION))
						JOptionPane.showMessageDialog(mainFrame, "Nessun Aggiornamento Trovato", "Aggiornamento", JOptionPane.INFORMATION_MESSAGE);
					else
					{
					UpdateDialog update = new UpdateDialog(Updater.getWhatsNew());
					update.setLocationRelativeTo(AnimeIndex.mainFrame);
					update.setVisible(true);
					}
			}
		});
		mntmAnichart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String link = "http://anichart.net/";
				try {
					URI uriLink = new URI(link);
					Desktop.getDesktop().browse(uriLink);
				} catch (URISyntaxException a) {
				} catch (IOException a) {
			}
		  }
		});
				
		mainFrame = new JPanel();
		mainFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainFrame);
		mainFrame.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(138, 233));
		mainFrame.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel animeSelectionPanel = new JPanel();
		animeSelectionPanel.setMaximumSize(new Dimension(138, 233));
		panel.add(animeSelectionPanel, BorderLayout.NORTH);
		animeSelectionPanel.setLayout(new BorderLayout(0, 0));
		
		animeTypeComboBox = new JComboBox();
		animeTypeComboBox.setMaximumSize(new Dimension(138, 20));
		animeTypeComboBox.setMaximumRowCount(2139120439);
		animeTypeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				saveModifiedInformation(list);
				searchBar.setText("");
				CardLayout cl = (CardLayout)(cardContainer.getLayout());
		        cl.show(cardContainer, (String)evt.getItem());
		        if (deleteButton.isEnabled())
		        { 
		        	deleteButton.setEnabled(false);	
		        	ovaList.clearSelection();
		        	filmList.clearSelection();
		        	airingList.clearSelection();
		        	completedList.clearSelection();
		        	completedToSeeList.clearSelection();
		        	searchBar.setText("");
		        	}
		        AnimeIndex.animeInformation.setBlank();
		        String type = getList();
		        list = type;
		        appProp.setProperty("Last_list", type);
		        if(type.equalsIgnoreCase("anime completati"))
		        	{
		        		AnimeIndex.animeInformation.exitDaycomboBox.setSelectedItem("Concluso");
		        		AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(false);
		        	}
		        if(animeInformation.lblAnimeName.getText().equals("Anime"))
		        {
		        	AnimeIndex.animeInformation.addToSeeButton.setEnabled(false);
		        	AnimeIndex.animeInformation.finishedButton.setEnabled(false);
		        }
		        else
		        {
		        if (type.equalsIgnoreCase("anime in corso") || type.equalsIgnoreCase("anime completati") || type.equalsIgnoreCase("oav") || type.equalsIgnoreCase("film"))
		        	AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
		        else
		        	AnimeIndex.animeInformation.addToSeeButton.setEnabled(false);
		        
		        if (type.equalsIgnoreCase("anime in corso") || type.equalsIgnoreCase("oav") || type.equalsIgnoreCase("film") || type.equalsIgnoreCase("completi da vedere"))
		        	AnimeIndex.animeInformation.finishedButton.setEnabled(true);
		        else
		        	AnimeIndex.animeInformation.finishedButton.setEnabled(false);
		         }
		        Filters.toFileteredList();
		     }
		});
		animeTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere"}));
		animeSelectionPanel.add(animeTypeComboBox, BorderLayout.NORTH);
		
		searchBar = new SearchBar();
		searchBar.setFont(segui.deriveFont(11f));
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
        searchBar.setIcon(icon);
		searchBar.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
				}
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				saveModifiedInformation();
				getJList().clearSelection();
				searchList.clearSelection();
				String search = searchBar.getText();
				SortedListModel model=null;
				if(filtro==9)
					model = getModel();
				else
					model = filterModel;
				CardLayout cl = (CardLayout)(cardContainer.getLayout());
		        cl.show(cardContainer, "Ricerca");
				SearchInList(search, model);
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				saveModifiedInformation();
				searchList.clearSelection();
				SortedListModel model = null;
				String search = searchBar.getText();
				JList list = new JList();
				list=getJList();
				list.clearSelection();
				if(filtro==9)
				{
					String listName = getList();
					
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
					if (!search.isEmpty())
					{	
					searchList.setEnabled(true);
					CardLayout cl = (CardLayout)(cardContainer.getLayout());
			        cl.show(cardContainer, "Ricerca");
					SearchInList(search, model);
					}
					else
					{
						CardLayout cl = (CardLayout)(cardContainer.getLayout());
				        cl.show(cardContainer, listName);
				        if(!animeInformation.lblAnimeName.equals("Anime"))
				        	list.setSelectedValue(animeInformation.lblAnimeName.getText(), true);
					}
				}
				else
				{
					filterList.clearSelection();
					model = filterModel;
					if (!search.isEmpty())
					{	
					searchList.setEnabled(true);
					CardLayout cl = (CardLayout)(cardContainer.getLayout());
			        cl.show(cardContainer, "Ricerca");
					SearchInList(search, model);
					}
					else
					{
						CardLayout cl = (CardLayout)(cardContainer.getLayout());
				        cl.show(cardContainer, "Filtri");
				        if(!animeInformation.lblAnimeName.equals("Anime"))
				        	filterList.setSelectedValue(animeInformation.lblAnimeName.getText(), true);
					}
				}
			}
		});
		searchBar.setDisabledTextColor(Color.LIGHT_GRAY);
		searchBar.setBackground(Color.BLACK);
		searchBar.setForeground(Color.LIGHT_GRAY);
		animeSelectionPanel.add(searchBar, BorderLayout.SOUTH);
		
		cardContainer = new JPanel();
		cardContainer.setPreferredSize(new Dimension(159, 235));
		cardContainer.setMaximumSize(new Dimension(159, 135));
		panel.add(cardContainer, BorderLayout.CENTER);
		cardContainer.setLayout(new CardLayout(0, 0));
		
		JPanel completedAnime = new JPanel();
		cardContainer.add(completedAnime, "Anime Completati");
		completedAnime.setLayout(new BorderLayout(0, 0));
		
		JScrollPane completedAnimeScroll = new JScrollPane();
//		completedAnimeScroll.setMaximumSize(new Dimension(138, 233));
		completedAnime.add(completedAnimeScroll, BorderLayout.CENTER);
		completedAnimeScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		completedAnimeScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));

		completedList = new JList(completedModel);
		completedList.addMouseListener(AnimeIndex.exclusionPopUpMenu());
		completedList.setFont(segui.deriveFont(12f));
		completedList.setMaximumSize(new Dimension(157, 233));
		completedList.setMinimumSize(new Dimension(138, 233));
		completedList.setSize(new Dimension(138, 233));
		completedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		completedList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			applyListSelectionChange(AnimeIndex.completedList);
			AnimeIndex.animeInformation.minusButton.setEnabled(false);
			AnimeIndex.animeInformation.currentEpisodeField.setEnabled(false);
			AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
			AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
			AnimeIndex.animeInformation.releaseDateField.setEnabled(false);
			AnimeIndex.animeInformation.finishDateField.setEnabled(false);
			AnimeIndex.animeInformation.durationField.setEnabled(false);
			AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
			AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
			AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
			AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
			AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(false);
			AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
			AnimeInformation.fansubComboBox.setEnabled(true);
			String name = (String) completedList.getSelectedValue();
			if(name!=null && !name.equalsIgnoreCase("Anime"))
			{
				if(completedMap.get(name).getLink() !=null && !completedMap.get(name).getLink().isEmpty())
					AnimeIndex.animeInformation.btnOpen.setEnabled(true);
			}
			String link = (String)animeInformation.fansubComboBox.getSelectedItem();
			if(link!=null && !link.isEmpty())
			{
				if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
					AnimeIndex.animeInformation.fansubButton.setEnabled(true);
			}
			
			UpdateAnimeDataTask task = new UpdateAnimeDataTask();
			task.execute();
			
			}
		});

		completedAnimeScroll.setViewportView(completedList);

		
		JPanel airingAnime = new JPanel();
		cardContainer.add(airingAnime, "Anime in Corso");
		airingAnime.setLayout(new BorderLayout(0, 0));
		
		JScrollPane airingAnimeScroll = new JScrollPane();
		airingAnime.add(airingAnimeScroll, BorderLayout.CENTER);
		airingAnimeScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		airingAnimeScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		
		airingList = new JList(airingModel);
		airingList.addMouseListener(AnimeIndex.exclusionPopUpMenu());
		airingList.setFont(segui.deriveFont(12f));
		airingList.setMaximumSize(new Dimension(157, 233));
		airingList.setMinimumSize(new Dimension(138, 233));
		airingList.setSize(new Dimension(138, 233));
		airingList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				applyListSelectionChange(AnimeIndex.airingList);
				AnimeIndex.animeInformation.minusButton.setEnabled(true);
				AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
				AnimeIndex.animeInformation.finishDateField.setEnabled(true);
				AnimeIndex.animeInformation.durationField.setEnabled(true);
				AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
				AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
				AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
				AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
				AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
				AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
				String ep = animeInformation.currentEpisodeField.getText();
				String fep = animeInformation.totalEpisodeText.getText();
				if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
					AnimeIndex.animeInformation.finishedButton.setEnabled(true);
				else
					AnimeIndex.animeInformation.finishedButton.setEnabled(false); 
				AnimeInformation.fansubComboBox.setEnabled(true);
				String name = (String) airingList.getSelectedValue();
				if(name!=null && !name.equalsIgnoreCase("Anime"))
				{
					if(airingMap.get(name).getLink() !=null && !airingMap.get(name).getLink().isEmpty())
						AnimeIndex.animeInformation.btnOpen.setEnabled(true);
				}
				String link = (String)animeInformation.fansubComboBox.getSelectedItem();
				if(link!=null && !link.isEmpty())
				{
					if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
						AnimeIndex.animeInformation.fansubButton.setEnabled(true);
				}
				
				UpdateAnimeDataTask task = new UpdateAnimeDataTask();
				task.execute();
			}
		});
		airingAnimeScroll.setViewportView(airingList);
		
		JPanel ova = new JPanel();
		cardContainer.add(ova, "OAV");
		ova.setLayout(new BorderLayout(0, 0));
		
		JScrollPane ovaScroll = new JScrollPane();
		ova.add(ovaScroll, BorderLayout.CENTER);
		ovaScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		ovaScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		
		
		ovaList = new JList(ovaModel);
		ovaList.addMouseListener(AnimeIndex.exclusionPopUpMenu());
		ovaList.setFont(segui.deriveFont(12f));
		ovaList.setMaximumSize(new Dimension(138, 233));
		ovaList.setMinimumSize(new Dimension(138, 233));
		ovaList.setSize(new Dimension(138, 233));
		ovaList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				applyListSelectionChange(AnimeIndex.ovaList);
				AnimeIndex.animeInformation.minusButton.setEnabled(true);
				AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
				AnimeIndex.animeInformation.finishDateField.setEnabled(true);
				AnimeIndex.animeInformation.durationField.setEnabled(true);
				AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
				AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
				AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
				AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
				AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
				AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
				String ep = animeInformation.currentEpisodeField.getText();
				String fep = animeInformation.totalEpisodeText.getText();
				if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
					AnimeIndex.animeInformation.finishedButton.setEnabled(true);
				else
					AnimeIndex.animeInformation.finishedButton.setEnabled(false);
				AnimeInformation.fansubComboBox.setEnabled(true);
				String name = (String) ovaList.getSelectedValue();
				if(name!=null && !name.equalsIgnoreCase("Anime"))
				{
					if(ovaMap.get(name).getLink() !=null && !ovaMap.get(name).getLink().isEmpty())
						AnimeIndex.animeInformation.btnOpen.setEnabled(true);
				}
				String link = (String)animeInformation.fansubComboBox.getSelectedItem();
				if(link!=null && !link.isEmpty())
				{
					if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
						AnimeIndex.animeInformation.fansubButton.setEnabled(true);
				}
				
				UpdateAnimeDataTask task = new UpdateAnimeDataTask();
				task.execute();
			}
		});
		ovaScroll.setViewportView(ovaList);
		
		JPanel film = new JPanel();
		cardContainer.add(film, "Film");
		film.setLayout(new BorderLayout(0, 0));
		
		JScrollPane filmScroll = new JScrollPane();
		film.add(filmScroll, BorderLayout.CENTER);
		filmScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		filmScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		
		filmList = new JList(filmModel);
		filmList.addMouseListener(AnimeIndex.exclusionPopUpMenu());
		filmList.setFont(segui.deriveFont(12f));
		filmList.setMaximumSize(new Dimension(138, 233));
		filmList.setMinimumSize(new Dimension(138, 233));
		filmList.setSize(new Dimension(138, 233));
		filmList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				applyListSelectionChange(AnimeIndex.filmList);
				AnimeIndex.animeInformation.minusButton.setEnabled(true);
				AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
				AnimeIndex.animeInformation.finishDateField.setEnabled(true);
				AnimeIndex.animeInformation.durationField.setEnabled(true);
				AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
				AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
				AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
				AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
				AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
				AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
				String ep = animeInformation.currentEpisodeField.getText();
				String fep = animeInformation.totalEpisodeText.getText();
				if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
					AnimeIndex.animeInformation.finishedButton.setEnabled(true);
				else
					AnimeIndex.animeInformation.finishedButton.setEnabled(false); 
				AnimeInformation.fansubComboBox.setEnabled(true);
				String name = (String) filmList.getSelectedValue();
				if(name!=null && !name.equalsIgnoreCase("Anime"))
				{
					if(filmMap.get(name).getLink() !=null && !filmMap.get(name).getLink().isEmpty())
						AnimeIndex.animeInformation.btnOpen.setEnabled(true);
				}
				String link = (String)animeInformation.fansubComboBox.getSelectedItem();
				if(link!=null && !link.isEmpty())
				{
					if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
						AnimeIndex.animeInformation.fansubButton.setEnabled(true);
				}
				
				UpdateAnimeDataTask task = new UpdateAnimeDataTask();
				task.execute();
			}
		});
		filmScroll.setViewportView(filmList);
		
		JPanel completedToSeeAnime = new JPanel();
		cardContainer.add(completedToSeeAnime, "Completi Da Vedere");
		completedToSeeAnime.setLayout(new BorderLayout(0, 0));
		
		JScrollPane completedToSeeScroll = new JScrollPane();
		completedToSeeAnime.add(completedToSeeScroll, BorderLayout.CENTER);
		completedToSeeScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		completedToSeeScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		
		completedToSeeList = new JList(completedToSeeModel);
		completedToSeeList.addMouseListener(AnimeIndex.exclusionPopUpMenu());
		completedToSeeList.setFont(segui.deriveFont(12f));
		completedToSeeList.setMaximumSize(new Dimension(138, 233));
		completedToSeeList.setMinimumSize(new Dimension(138, 233));
		completedToSeeList.setSize(new Dimension(138, 233));
		completedToSeeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				applyListSelectionChange(AnimeIndex.completedToSeeList);
				AnimeIndex.animeInformation.minusButton.setEnabled(true);
				AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
				AnimeIndex.animeInformation.addToSeeButton.setEnabled(false);
				AnimeIndex.animeInformation.releaseDateField.setEnabled(false);
				AnimeIndex.animeInformation.finishDateField.setEnabled(false);
				AnimeIndex.animeInformation.durationField.setEnabled(true);
				AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
				AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
				AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
				AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
				AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(false);
				AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
				String ep = animeInformation.currentEpisodeField.getText();
				String fep = animeInformation.totalEpisodeText.getText();
				if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
					AnimeIndex.animeInformation.finishedButton.setEnabled(true);
				else
					AnimeIndex.animeInformation.finishedButton.setEnabled(false); 
				AnimeInformation.fansubComboBox.setEnabled(true);
				String name = (String) completedToSeeList.getSelectedValue();
				if(name!=null && !name.equalsIgnoreCase("Anime"))
				{
					if(completedToSeeMap.get(name).getLink() !=null && !completedToSeeMap.get(name).getLink().isEmpty())
						AnimeIndex.animeInformation.btnOpen.setEnabled(true);
				}
				String link = (String)animeInformation.fansubComboBox.getSelectedItem();
				if(link!=null && !link.isEmpty())
				{
					if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
						AnimeIndex.animeInformation.fansubButton.setEnabled(true);
				}
				
				UpdateAnimeDataTask task = new UpdateAnimeDataTask();
				task.execute();
			}
		});
		completedToSeeScroll.setViewportView(completedToSeeList);
		
		JPanel searchCard = new JPanel();
		cardContainer.add(searchCard, "Ricerca");
		searchCard.setLayout(new BorderLayout(0, 0));
		
		JScrollPane searchScroll = new JScrollPane();
		searchCard.add(searchScroll, BorderLayout.CENTER);	
		searchScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		searchScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		searchList = new JList(searchModel);
		searchList.addMouseListener(AnimeIndex.exclusionPopUpMenu());
		searchList.setFont(segui.deriveFont(12f));
		searchList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				applyListSelectionChange(AnimeIndex.searchList);
				String cBox = (String)animeTypeComboBox.getSelectedItem();
				if(cBox.equalsIgnoreCase("Anime Completati"))
				{
					AnimeIndex.animeInformation.minusButton.setEnabled(false);
					AnimeIndex.animeInformation.currentEpisodeField.setEnabled(false);
					AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
					AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
					AnimeIndex.animeInformation.releaseDateField.setEnabled(false);
					AnimeIndex.animeInformation.finishDateField.setEnabled(false);
					AnimeIndex.animeInformation.durationField.setEnabled(false);
					AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
					AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
					AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
					AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
					AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(false);
					AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
					AnimeInformation.fansubComboBox.setEnabled(true);
					String name = (String) completedList.getSelectedValue();
					if(name!=null && !name.equalsIgnoreCase("Anime"))
					{
						if(completedMap.get(name).getLink() !=null && !completedMap.get(name).getLink().isEmpty())
							AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
					{
						if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
							AnimeIndex.animeInformation.fansubButton.setEnabled(true);
					}
				}
					else if(cBox.equalsIgnoreCase("Anime in Corso"))
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
						AnimeIndex.animeInformation.finishDateField.setEnabled(true);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false);
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) airingList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(airingMap.get(name).getLink() !=null && !airingMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
						
					}
					else if(cBox.equalsIgnoreCase("OAV"))
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
						AnimeIndex.animeInformation.finishDateField.setEnabled(true);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false);
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) ovaList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(ovaMap.get(name).getLink() !=null && !ovaMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
					}
					else if(cBox.equalsIgnoreCase("Film"))
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
						AnimeIndex.animeInformation.finishDateField.setEnabled(true);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false); 
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) filmList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(filmMap.get(name).getLink() !=null && !filmMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
					}
					else
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(false);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(false);
						AnimeIndex.animeInformation.finishDateField.setEnabled(false);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(false);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false); 
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) completedToSeeList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(completedToSeeMap.get(name).getLink() !=null && !completedToSeeMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
					}
				
				UpdateAnimeDataTask task = new UpdateAnimeDataTask();
				task.execute();
			}
		});
		searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchList.setSize(new Dimension(138, 233));
		searchList.setMinimumSize(new Dimension(138, 233));
		searchList.setMaximumSize(new Dimension(138, 233));
		searchScroll.setViewportView(searchList);
		
		JPanel filterCard = new JPanel();
		cardContainer.add(filterCard, "Filtri");
		filterCard.setLayout(new BorderLayout(0, 0));
		
		JScrollPane filterScroll = new JScrollPane();
		filterCard.add(filterScroll, BorderLayout.CENTER);
		filterScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		filterScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		
		filterList = new JList(filterModel);
		filterList.addMouseListener(AnimeIndex.exclusionPopUpMenu());
		filterList.setFont(segui.deriveFont(12f));
		filterList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				applyListSelectionChange(AnimeIndex.filterList);
				String cBox = (String)animeTypeComboBox.getSelectedItem();
				if(cBox.equalsIgnoreCase("Anime Completati"))
				{
					AnimeIndex.animeInformation.minusButton.setEnabled(false);
					AnimeIndex.animeInformation.currentEpisodeField.setEnabled(false);
					AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
					AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
					AnimeIndex.animeInformation.releaseDateField.setEnabled(false);
					AnimeIndex.animeInformation.finishDateField.setEnabled(false);
					AnimeIndex.animeInformation.durationField.setEnabled(false);
					AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
					AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
					AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
					AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
					AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(false);
					AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
					AnimeInformation.fansubComboBox.setEnabled(true);
					String name = (String) completedList.getSelectedValue();
					if(name!=null && !name.equalsIgnoreCase("Anime"))
					{
						if(completedMap.get(name).getLink() !=null && !completedMap.get(name).getLink().isEmpty())
							AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
					{
						if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
							AnimeIndex.animeInformation.fansubButton.setEnabled(true);
					}
				}
					else if(cBox.equalsIgnoreCase("Anime in Corso"))
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
						AnimeIndex.animeInformation.finishDateField.setEnabled(true);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false); 
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) airingList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(airingMap.get(name).getLink() !=null && !airingMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
						
					}
					else if(cBox.equalsIgnoreCase("OAV"))
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
						AnimeIndex.animeInformation.finishDateField.setEnabled(true);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false); 
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) ovaList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(ovaMap.get(name).getLink() !=null && !ovaMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
					}
					else if(cBox.equalsIgnoreCase("Film"))
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(true);
						AnimeIndex.animeInformation.finishDateField.setEnabled(true);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(true);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false);
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) filmList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(filmMap.get(name).getLink() !=null && !filmMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
					}
					else
					{
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(false);
						AnimeIndex.animeInformation.releaseDateField.setEnabled(false);
						AnimeIndex.animeInformation.finishDateField.setEnabled(false);
						AnimeIndex.animeInformation.durationField.setEnabled(true);
						AnimeIndex.animeInformation.noteTextArea.setEnabled(true);
						AnimeIndex.animeInformation.typeComboBox.setEnabled(true);
						AnimeIndex.animeInformation.btnAnilistInfo.setEnabled(true);
						AnimeIndex.animeInformation.setLinkButton.setEnabled(true);
						AnimeIndex.animeInformation.exitDaycomboBox.setEnabled(false);
						AnimeIndex.animeInformation.checkDataButton.setEnabled(true);
						String ep = animeInformation.currentEpisodeField.getText();
						String fep = animeInformation.totalEpisodeText.getText();
						if(ep!=null && fep!=null && !fep.equalsIgnoreCase("??") && ep.equalsIgnoreCase(fep))
							AnimeIndex.animeInformation.finishedButton.setEnabled(true);
						else
							AnimeIndex.animeInformation.finishedButton.setEnabled(false);
						AnimeInformation.fansubComboBox.setEnabled(true);
						String name = (String) completedToSeeList.getSelectedValue();
						if(name!=null && !name.equalsIgnoreCase("Anime"))
						{
							if(completedToSeeMap.get(name).getLink() !=null && !completedToSeeMap.get(name).getLink().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
						}
						String link = (String)animeInformation.fansubComboBox.getSelectedItem();
						if(link!=null && !link.isEmpty())
						{
							if(fansubMap.get(link) != null && !fansubMap.get(link).isEmpty())
								AnimeIndex.animeInformation.fansubButton.setEnabled(true);
						}
					}
				
				UpdateAnimeDataTask task = new UpdateAnimeDataTask();
				task.execute();
			}
		});
		filterList.setSize(new Dimension(138, 233));
		filterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filterList.setMinimumSize(new Dimension(138, 233));
		filterList.setMaximumSize(new Dimension(138, 233));
		filterScroll.setViewportView(filterList);
		
		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));

		deleteButton = new JButton("Elimina Anime");
		deleteButton.setPreferredSize(new Dimension(159, 21));
		deleteButton.setMaximumSize(new Dimension(159, 21));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String type = (String) animeTypeComboBox.getSelectedItem();

				SortedListModel model = getModel();
				SortedListModel secondModel = null;
				SortedListModel thirdModel = null;
				JList list = null;
				if (searchBar.getText().isEmpty() && filtro == 9)
					list = getJList();
				else if (searchBar.getText().isEmpty() && filtro != 9)
				{
					list = filterList;
					secondModel = filterModel;
				}
				else if(!searchBar.getText().isEmpty() && filtro != 9)
				{
					list = searchList;
					secondModel = searchModel;
					thirdModel = filterModel;
				}
				else
				{
					list = searchList;
					secondModel = searchModel;
				}
				
				String listName = getList();
				TreeMap<String,AnimeData> map = getMap();
				ArrayList<String> arrayList = getDeletedAnimeArray();
				int index = list.getSelectedIndex();
				String name = (String) list.getSelectedValue();
				model.removeElement(name);
				if (secondModel != null)
					secondModel.removeElement(name);
				if (thirdModel != null)
					thirdModel.removeElement(name);
				index -= 1;
				list.clearSelection();
				list.setSelectedIndex(index);
				
				String image = map.get(name).getImagePath(listName);
				arrayList.add(image);
								
				map.remove(name);
				if(sessionAddedAnime.contains(name))
					sessionAddedAnime.remove(name);
				
				if (!list.isSelectionEmpty())					
					deleteButton.setEnabled(true);
				else
				{
					deleteButton.setEnabled(false);
					animeInformation.setBlank();
				}
			}
		});
		deleteButton.setEnabled(false);
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		
		
		addButton = new JButton("Aggiungi Anime");
		addButton.setPreferredSize(new Dimension(159, 21));
		addButton.setMaximumSize(new Dimension(159, 21));
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {	
				animeDialog = new AddAnimeDialog();
				animeDialog.setLocationRelativeTo(mainFrame);
				animeDialog.setVisible(true);
			}
		});
		buttonPanel.add(addButton, BorderLayout.SOUTH);
		
	    setFilterButton = new JButton("Filtro");
	    setFilterButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		SetFilterDialog filterDialog = new SetFilterDialog();
				filterDialog.setLocationRelativeTo(animeInformation.animeImage);
				filterDialog.setVisible(true);
				if(!searchBar.getText().isEmpty())
					searchBar.setText("");
	    	}
	    });
	    setFilterButton.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/ellipse_icon3.png")));
		buttonPanel.add(setFilterButton, BorderLayout.NORTH);
		
		fansubList = FileManager.loadFansubList();
		animeInformation = new AnimeInformation();
		mainFrame.add(animeInformation, BorderLayout.CENTER);
		addToFansub("?????");
		AnimeIndex.animeInformation.setFansubComboBox();
		animeInformation.setBlank();
		if (appProp.getProperty("List_to_visualize_at_start").equalsIgnoreCase("Last list"))
		{
			list = appProp.getProperty("Last_list");
			animeTypeComboBox.setSelectedItem(list);
		}
		else if (appProp.getProperty("List_to_visualize_at_start").equalsIgnoreCase("Daily"))
		{
			animeTypeComboBox.setSelectedItem("Anime in Corso");
			Filters.setFilter(8);
		}
		else
		{
			list = appProp.getProperty("List_to_visualize_at_start");
		}
		
		animeTypeComboBox.setSelectedItem(list);
		}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public static  String[] getFansubList()
	{
		return fansubList;
	}
	
	public void addToFansub(String fansub)
	
	{
		String[] oldList = fansubList;
		String[] newList = new String[oldList.length + 1];
		for (int i = 0; i < oldList.length; i++)
		{
			newList[i] = oldList[i];
		}

		newList[oldList.length] = fansub; 
		fansubList = newList;
	}
	
	public static void setFansubList(Object[] arrayToSet)
	{
		String[] fansubArray = new String[arrayToSet.length];
		for (int i = 0; i < fansubArray.length; i++) {
			if (arrayToSet[i]!= null)
				fansubArray[i] = arrayToSet[i].toString();
		}
		fansubList = fansubArray;
		AnimeInformation.setFansubComboBox();
	}
	
	public void addToAnime(String animeName)
	{
		completedModel.addElement(animeName);
	}
	
	public static String getList()
	{
		return (String) animeTypeComboBox.getSelectedItem();
	}
	
	public void SearchInList(String searchedVal, SortedListModel modelToSearch) 
	{
		Object[] mainArray = modelToSearch.toArray();			
			searchModel.clear();
			for (int i = 0; i < mainArray.length; i++) {
				String value = (String) mainArray[i];
				value = value.toLowerCase();
				searchedVal = searchedVal.toLowerCase();
				if (value.contains(searchedVal))
					searchModel.addElement((String)mainArray[i]);
			}
		if (searchModel.isEmpty())
		{
			searchModel.addElement("Nessun Anime Corrispondente");
			searchList.setEnabled(false);
			deleteButton.setEnabled(false);
		}
		}

	public static JList getJList()
	{
		JList list= null;
			String listName = getList();
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
	
	public static TreeMap getMap()
	{
		String listName = getList();
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
	
	public static SortedListModel getModel()
	{
		SortedListModel model= null;
			String listName = getList();
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
	
	public static ArrayList<String> getDeletedAnimeArray()
	{
		String listName = getList();
		ArrayList<String> arrayList= null;
		if (listName.equalsIgnoreCase("anime completati"))
		{	
			arrayList = AnimeIndex.completedDeletedAnime;						
		}				
		else if (listName.equalsIgnoreCase("anime in corso"))
		{
			arrayList = AnimeIndex.airingDeletedAnime;
		}
		else if (listName.equalsIgnoreCase("oav"))
		{
			arrayList = AnimeIndex.ovaDeletedAnime;
		}
		else if (listName.equalsIgnoreCase("film"))
		{
			arrayList = AnimeIndex.filmDeletedAnime;
		}
		else if (listName.equalsIgnoreCase("completi da vedere"))
		{
			arrayList = AnimeIndex.completedToSeeDeletedAnime;
		}
		
		return arrayList;
	}

	public static void saveModifiedInformation()
	{
		if(!animeInformation.lblAnimeName.getText().equalsIgnoreCase("Anime"))
		{
			String name = animeInformation.lblAnimeName.getText();
			String currEp = animeInformation.currentEpisodeField.getText();
			String totEp = animeInformation.totalEpisodeText.getText();
			String fansub = (String) animeInformation.fansubComboBox.getSelectedItem();
			String fansubLink = animeInformation.getLink();
			String note = animeInformation.noteTextArea.getText();
			String day = (String) animeInformation.exitDaycomboBox.getSelectedItem();
			String linkName = animeInformation.setLinkButton.getText();
			String animeType = (String)animeInformation.typeComboBox.getSelectedItem();
			String releaseDate = animeInformation.releaseDateField.getText();
			String finishDate = animeInformation.finishDateField.getText();
			String durationEp = animeInformation.durationField.getText();
			
			String list = AnimeIndex.getList();
			if (list.equalsIgnoreCase("Anime Completati"))
				{
				String image = AnimeIndex.completedMap.get(name).getImageName();
				String id = AnimeIndex.completedMap.get(name).getId();
				String link = AnimeIndex.completedMap.get(name).getLink();
				Boolean bd = AnimeIndex.completedMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.completedMap.put(name, data);
				}
			else if (list.equalsIgnoreCase("Anime in Corso"))
				{
				String image = AnimeIndex.airingMap.get(name).getImageName();
				String id = AnimeIndex.airingMap.get(name).getId();
				String link = AnimeIndex.airingMap.get(name).getLink();
				Boolean bd = AnimeIndex.airingMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.airingMap.put(name, data);
				}
			else if (list.equalsIgnoreCase("OAV"))
				{
				String image = AnimeIndex.ovaMap.get(name).getImageName();
				String id = AnimeIndex.ovaMap.get(name).getId();
				String link = AnimeIndex.ovaMap.get(name).getLink();
				Boolean bd = AnimeIndex.ovaMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.ovaMap.put(name, data);
				}
			else if (list.equalsIgnoreCase("Film"))
			{
				String image = AnimeIndex.filmMap.get(name).getImageName();
				String id = AnimeIndex.filmMap.get(name).getId();
				String link = AnimeIndex.filmMap.get(name).getLink();
				Boolean bd = AnimeIndex.filmMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.filmMap.put(name, data);
			}
			else if (list.equalsIgnoreCase("Completi Da Vedere"))
			{
				String image = AnimeIndex.completedToSeeMap.get(name).getImageName();
				String id = AnimeIndex.completedToSeeMap.get(name).getId();
				String link = AnimeIndex.completedToSeeMap.get(name).getLink();
				Boolean bd = AnimeIndex.completedToSeeMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.completedToSeeMap.put(name, data);				
			}
		}
	}

	public void saveModifiedInformation(String list)
	{
		if(!animeInformation.lblAnimeName.getText().equalsIgnoreCase("Anime"))
		{
			String name = animeInformation.lblAnimeName.getText();
			String currEp = animeInformation.currentEpisodeField.getText();
			String totEp = animeInformation.totalEpisodeText.getText();
			String fansub = (String) animeInformation.fansubComboBox.getSelectedItem();
			String fansubLink = animeInformation.getLink();
			String note = animeInformation.noteTextArea.getText();
			String day = (String) animeInformation.exitDaycomboBox.getSelectedItem();
			String linkName = animeInformation.setLinkButton.getText();
			String animeType = (String)animeInformation.typeComboBox.getSelectedItem();
			String releaseDate = animeInformation.releaseDateField.getText();
			String finishDate = animeInformation.finishDateField.getText();
			String durationEp = animeInformation.durationField.getText();
			
			if (list.equalsIgnoreCase("Anime Completati"))
				{
				String image = AnimeIndex.completedMap.get(name).getImageName();
				String id = AnimeIndex.completedMap.get(name).getId();
				String link = AnimeIndex.completedMap.get(name).getLink();
				Boolean bd = AnimeIndex.completedMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.completedMap.put(name, data);
				}
			else if (list.equalsIgnoreCase("Anime in Corso"))
				{
				String image = AnimeIndex.airingMap.get(name).getImageName();
				String id = AnimeIndex.airingMap.get(name).getId();
				String link = AnimeIndex.airingMap.get(name).getLink();
				Boolean bd = AnimeIndex.airingMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.airingMap.put(name, data);
				}
			else if (list.equalsIgnoreCase("OAV"))
				{
				String image = AnimeIndex.ovaMap.get(name).getImageName();
				String id = AnimeIndex.ovaMap.get(name).getId();
				String link = AnimeIndex.ovaMap.get(name).getLink();
				Boolean bd = AnimeIndex.ovaMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.ovaMap.put(name, data);
				}
			else if (list.equalsIgnoreCase("Film"))
			{
				String image = AnimeIndex.filmMap.get(name).getImageName();
				String id = AnimeIndex.filmMap.get(name).getId();
				String link = AnimeIndex.filmMap.get(name).getLink();
				Boolean bd = AnimeIndex.filmMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansubLink, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.filmMap.put(name, data);
			}
			else if (list.equalsIgnoreCase("Completi Da Vedere"))
			{
				String image = AnimeIndex.completedToSeeMap.get(name).getImageName();
				String id = AnimeIndex.completedToSeeMap.get(name).getId();
				String link = AnimeIndex.completedToSeeMap.get(name).getLink();
				Boolean bd = AnimeIndex.completedToSeeMap.get(name).getBd();
				AnimeData data = new AnimeData(currEp, totEp, fansubLink, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				AnimeIndex.completedToSeeMap.put(name, data);
			}
		}
	}
	
	public void setFansubMap(TreeMap<String, String> fansubMap) 
	{
		AnimeIndex.fansubMap.putAll(fansubMap);
	}
	
	private void applyListSelectionChange(JList jList)
	{
		try
		{
			saveModifiedInformation();
		}
		catch (NullPointerException e1)
		{
			deleteButton.setEnabled(true);
			JList list = jList;
			String anime = (String) list.getSelectedValue();
			if (anime != null || !jList.isSelectionEmpty())
			{
			TreeMap<String,AnimeData> map = getMap();			
			AnimeData data = map.get(anime);
			animeInformation.setAnimeName(anime);
			animeInformation.setCurrentEp(data.getCurrentEpisode());
			animeInformation.setTotalEp(data.getTotalEpisode());
			animeInformation.setDurationEp(data.getDurationEp());
			animeInformation.setFansub(data.getFansub());
			animeInformation.setLink(data.getLink());
			if (data.getLink() != null && !(data.getLink().isEmpty()))
			{
				if (data.getLinkName() != null && !(data.getLinkName().isEmpty()))
					animeInformation.setlinkName(data.getLinkName());
				else
					animeInformation.setlinkName("Link");
			}
			else
				animeInformation.setlinkName("Imposta Link");
			animeInformation.setReleaseDate(data.getReleaseDate());
			animeInformation.setFinishDate(data.getFinishDate());
			animeInformation.setDay(data.getDay());
			animeInformation.setType(data.getAnimeType());
			String listName = getList();
			String path = data.getImagePath(listName);
			File file = new File(path);
			if (file.exists())
				animeInformation.setImage(data.getImagePath(listName));
			else
			{
				animeInformation.setImage("default");
			}
			
			if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
				animeInformation.plusButton.setEnabled(false);
			else
				animeInformation.plusButton.setEnabled(true);
			}
		}
		
		deleteButton.setEnabled(true);
		JList list = jList;
		String anime = (String) list.getSelectedValue();
		if (anime != null || !jList.isSelectionEmpty())
		{	
			TreeMap<String,AnimeData> map = getMap();
			AnimeData data = map.get(anime);
			animeInformation.setAnimeName(anime);
			animeInformation.setCurrentEp(data.getCurrentEpisode());
			animeInformation.setTotalEp(data.getTotalEpisode());
			animeInformation.setDurationEp(data.getDurationEp());
			animeInformation.setFansub(data.getFansub());
			animeInformation.setLink(data.getLink());
			if (data.getLink() != null && !(data.getLink().isEmpty()))
			{
				if (data.getLinkName() != null && !(data.getLinkName().isEmpty()))
					animeInformation.setlinkName(data.getLinkName());
				else
					animeInformation.setlinkName("Link");
			}
			else
				animeInformation.setlinkName("Imposta Link");
			animeInformation.setReleaseDate(data.getReleaseDate());
			animeInformation.setFinishDate(data.getFinishDate());
			animeInformation.setDay(data.getDay());
			animeInformation.setType(data.getAnimeType());
			String listName = getList();
			String path = data.getImagePath(listName);
			File file = new File(path);
			if (file.exists())
				animeInformation.setImage(data.getImagePath(listName));
			else
			{
				animeInformation.setImage("deafult");
			}
		
			if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
				animeInformation.plusButton.setEnabled(false);
			else
				animeInformation.plusButton.setEnabled(true);
		
			if (data.getFansub() != null)
			{
				if(fansubMap.get(data.getFansub()) != null && !(fansubMap.get(data.getFansub())).isEmpty())
	                AnimeIndex.animeInformation.fansubButton.setEnabled(true);
				else
					 AnimeIndex.animeInformation.fansubButton.setEnabled(false);
			} 
			else
			{
				 AnimeIndex.animeInformation.fansubButton.setEnabled(false);
			}
		}
	}
		
	private static Font segui()
	{
		InputStream is = AnimeIndex.class.getResourceAsStream("/font/seguisym.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return font;
	}
	
	private static MouseAdapter exclusionPopUpMenu()
	{
		MouseAdapter mouse = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( SwingUtilities.isRightMouseButton(e) )
		        {
		            JList list = (JList)e.getSource();
		            int row = list.locationToIndex(e.getPoint());
		            Rectangle bound = list.getCellBounds(row, row);
		            if (bound.contains(e.getPoint()))
		            {
	            	list.setSelectedIndex(row);
					JPopupMenu menu = new JPopupMenu();
	                JMenuItem add = new JMenuItem("Aggiungi alle Esclusioni");
	                add.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                        exclusionAnime.add((String)list.getSelectedValue());
	                    }
	                });
	                JMenuItem remove = new JMenuItem("Rimuovi dalle Esclusioni");
	                remove.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                        exclusionAnime.remove((String)list.getSelectedValue());
	                    }
	                });
	                if (exclusionAnime.contains((String)list.getSelectedValue()))
	                	menu.add(remove);
	                else
	                	menu.add(add);
	                menu.show(list, e.getX(),e.getY());
		            }
					
		        }
			}
		};
		return mouse;
	}
}