package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import util.SearchBar;
import util.window.AddAnimeDialog;
import util.window.AddFansubDialog;
import util.window.AddImageDialog;
import util.window.AnimeInformation;
import util.window.ExitSaveDialog;
import util.window.PreferenceDialog;
import util.window.SetAnimeNameDialog;
import util.window.SetFilterDialog;

import javax.swing.JToggleButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
//import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;
//kemomimi OP
public class AnimeIndex extends JFrame
{

	public static JPanel mainFrame;
	public static JPanel cardContainer;
	public static AnimeInformation animeInformation;
	
	public static JList completedToSeeList;
	public static JList filmList;
	public static JList airingList;
	public static JList completedList;
	public static JList ovaList;
	private JList dayExitList;
	private JList searchList;
	private JList filterList;
	
	public static DefaultListModel completedModel = new DefaultListModel();
	public static DefaultListModel airingModel = new DefaultListModel();
	public static DefaultListModel ovaModel = new DefaultListModel();
	public static DefaultListModel filmModel = new DefaultListModel();
	public static DefaultListModel completedToSeeModel = new DefaultListModel();
	public static DefaultListModel dayExitModel = new DefaultListModel();
	private static DefaultListModel searchModel = new DefaultListModel();
	private static DefaultListModel filterModel = new DefaultListModel();
	
	private static String[] fansubList = {};
	public static TreeMap<String,String> fansubMap = new TreeMap<String,String>();
	public static Properties appProp;
	public static TreeMap<String,AnimeData> completedMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> airingMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> ovaMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> filmMap = new TreeMap<String,AnimeData>();
	public static TreeMap<String,AnimeData> completedToSeeMap = new TreeMap<String,AnimeData>();
	
	public static ArrayList<String> completedSessionAnime = new ArrayList();
	public static ArrayList<String> airingSessionAnime = new ArrayList();
	public static ArrayList<String> ovaSessionAnime = new ArrayList();
	public static ArrayList<String> filmSessionAnime = new ArrayList();
	public static ArrayList<String> comletedToSeeSessionAnime = new ArrayList();
	
	private JButton addButton;
	private JButton deleteButton;
	public static JComboBox animeTypeComboBox;
	public static AddAnimeDialog animeDialog;
	private SearchBar searchBar;
	public static AddFansubDialog fansubDialog;
	public static JButton setFilterButton;
	public static boolean[] filterArray = {false, false, false, false, false, false, false, false, false};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		appProp = AnimeIndexProperties.createProperties();
		FileManager.loadAnime("completed.txt" , completedModel, completedMap);
		FileManager.loadAnime("airing.txt", airingModel, AnimeIndex.airingMap);
		FileManager.loadAnime("ova.txt", ovaModel, AnimeIndex.ovaMap);
		FileManager.loadAnime("film.txt", filmModel, AnimeIndex.filmMap);
		FileManager.loadAnime("toSee.txt", completedToSeeModel, AnimeIndex.completedToSeeMap);
		
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
					AnimeIndex frame = new AnimeIndex();
					frame.setVisible(true);
					completedList.setSelectedIndex(0);
					
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
		setTitle("My Anime Manager");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/icon.png")));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {			
				ExitSaveDialog exitDialog = new ExitSaveDialog();
				exitDialog.setLocationRelativeTo(mainFrame);
				exitDialog.setVisible(true);
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
		
		JMenuItem mntmAggiungiFansub = new JMenuItem("Aggiungi Fansub");
		mntmAggiungiFansub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fansubDialog = new AddFansubDialog();
				fansubDialog.setLocationRelativeTo(mainFrame);
				fansubDialog.setVisible(true);
			}
		});
		
		JMenuItem mntmAddImage = new JMenuItem("Aggiungi Immagine");
		mntmAddImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddImageDialog imageDialog = new AddImageDialog();
				imageDialog.setLocationRelativeTo(mainFrame);
				imageDialog.setVisible(true);
			}
		});
		mnMenu.add(mntmAddImage);
		mnMenu.add(mntmAggiungiFansub);
		
		JMenuItem mntmModificaNome = new JMenuItem("Modifica Nome Anime");
		mntmModificaNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Si consiglia di lasciare invariato\n\ril nome dell'anime per una\n\rmaggiore compatibilita' coi dati\n\rdel server AniList.\n\rAlcune funzioni potrebbero\n\ressere disattivate. Continuare?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(shouldCancel==0){
					SetAnimeNameDialog animeNameDialog = new SetAnimeNameDialog();
				animeNameDialog.setLocationRelativeTo(mainFrame);
				animeNameDialog.setVisible(true);
				}
//TODO la modifica del nome si potrebbe applicare solo alla label e nn alla treeMap così da nn perdere le fuzioni di update. ma bisogna modificarlo anche nella lista della categoria				
			}
		});
		mnMenu.add(mntmModificaNome);
		
		JSeparator separator_3 = new JSeparator();
		mnMenu.add(separator_3);
		
		JCheckBoxMenuItem rdbtnmntmControlloDati = new JCheckBoxMenuItem("Controllo Dati");
		if(appProp.getProperty("Update_system").equals("true"))
			rdbtnmntmControlloDati.setSelected(true);
		else
			rdbtnmntmControlloDati.setSelected(false);
		
		rdbtnmntmControlloDati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!rdbtnmntmControlloDati.isSelected())
				{
					appProp.setProperty("Update_system", "false");
					JOptionPane.showMessageDialog(mainFrame, "Controllo Dati :      DISATTIVATO", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
					//se il marcatore e' acceso nn viene mostrato il warning e il sistema di update viene disattivato.
				}
				else
				{
					int shouldCancel = JOptionPane.showConfirmDialog(mainFrame, "Il sistema utilizza Internet.\n\rAssicurarsi che la rete sia\n\rdisponibile al fine di evitare\n\ril blocco del programma.\n\rContinuare?", "Attenzione!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(shouldCancel==1)
					{
						rdbtnmntmControlloDati.setSelected(false);
					}
					else
					{
						appProp.setProperty("Update_system", "true");
						//attiva il sistema di update fino alla disattivazione da parte dell'utente
					}
				}
		}	
		});
		mnMenu.add(rdbtnmntmControlloDati);
		
		JSeparator separator = new JSeparator();
		mnMenu.add(separator);
		
		JMenuItem mntmPreferenze = new JMenuItem("Preferenze");
		mntmPreferenze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreferenceDialog preference = new PreferenceDialog();
				preference.setLocationRelativeTo(mainFrame);
				preference.setVisible(true);
			}
		});
		mnMenu.add(mntmPreferenze);
		
		JSeparator separator_2 = new JSeparator();
		mnMenu.add(separator_2);
		
		JMenuItem mntmDeleteImage = new JMenuItem("Elimina tutti i dati");
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
				dayExitModel.clear();
				
				completedMap.clear();
				airingModel.clear();
				completedToSeeMap.clear();
				filmMap.clear();
				ovaMap.clear();

				String[] newFansub = {};
				fansubList = newFansub;
				
				animeInformation.setImage("deafult");
				animeInformation.setAnimeName("Anime");
				animeInformation.setCurrentEp("");
				animeInformation.setTotalEp("");
				animeInformation.setLink("");
				animeInformation.setDay("-----");
				animeInformation.fansubComboBox.removeAllItems();
				JOptionPane.showMessageDialog(mainFrame, "Dati eliminati", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnMenu.add(mntmDeleteImage);
		
		JSeparator separator_1 = new JSeparator();
		mnMenu.add(separator_1);
		
		JMenu mnHelp = new JMenu("Aiuto");
		mnMenu.add(mnHelp);
		
		JMenuItem mntmAboutMyAnime = new JMenuItem("Versione Programma");
		mntmAboutMyAnime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainFrame, "My Anime Manager             1.0", "Versione", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmAboutMyAnime);
		
		JMenuItem mntmCredit = new JMenuItem("Crediti");
		mnHelp.add(mntmCredit);
		
		JMenu mnAnichart = new JMenu("AniChart");
		menuBar.add(mnAnichart);
		
		JMenuItem mntmAnichart = new JMenuItem("Apri AniChart");
		mnAnichart.add(mntmAnichart);
		mntmAnichart.setHorizontalAlignment(SwingConstants.LEFT);
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
		mntmCredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = System.getProperty("user.home");
				if (name.equalsIgnoreCase("C:\\Users\\Denis"))
					JOptionPane.showMessageDialog(mainFrame, "Creato da tu sai chi, merdina che non fa i dizionari", "Crediti", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(mainFrame, "Creato da Yesod30", "Crediti", JOptionPane.INFORMATION_MESSAGE);
				
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
		        	dayExitList.clearSelection();
		        	searchBar.setText("");
		        	
		        }
		        
		        JList list = getJList();
		        list.setSelectedIndex(0);
		        String type = (String) animeTypeComboBox.getSelectedItem();
		        // disabilitazione pulsanti vari
		        if(type.equalsIgnoreCase("uscite del giorno"))
		        	addButton.setEnabled(false);
		        else
		        	addButton.setEnabled(true);
		        
		        if (type.equalsIgnoreCase("anime in corso") || type.equalsIgnoreCase("anime completati") || type.equalsIgnoreCase("oav") || type.equalsIgnoreCase("film"))
		        	AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
		        else
		        	AnimeIndex.animeInformation.addToSeeButton.setEnabled(false);
		        
		        if (type.equalsIgnoreCase("anime in corso") || type.equalsIgnoreCase("oav") || type.equalsIgnoreCase("film") || type.equalsIgnoreCase("completi da vedere"))
		        	AnimeIndex.animeInformation.finishedButton.setEnabled(true);
		        else
		        	AnimeIndex.animeInformation.finishedButton.setEnabled(false);
			}
		});
		animeTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere","Uscite del Giorno"}));
		animeSelectionPanel.add(animeTypeComboBox, BorderLayout.NORTH);
		
		searchBar = new SearchBar();
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
        searchBar.setIcon(icon);
		searchBar.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
				
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				String search = searchBar.getText();
				String listName = getList();
				DefaultListModel model = getModel();
				
				CardLayout cl = (CardLayout)(cardContainer.getLayout());
		        cl.show(cardContainer, "Ricerca");
				SearchInList(search, model);
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				String search = searchBar.getText();
				String listName = getList();
				DefaultListModel model = null;
				
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
				CardLayout cl = (CardLayout)(cardContainer.getLayout());
		        cl.show(cardContainer, "Ricerca");
				SearchInList(search, model);
				}
				else
				{
					CardLayout cl = (CardLayout)(cardContainer.getLayout());
			        cl.show(cardContainer, listName);
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
		completedAnimeScroll.setMaximumSize(new Dimension(138, 233));
		completedAnime.add(completedAnimeScroll, BorderLayout.CENTER);

		completedList = new JList(completedModel);
		completedList.setMaximumSize(new Dimension(157, 233));
		completedList.setMinimumSize(new Dimension(138, 233));
		completedList.setPreferredSize(new Dimension(138, 233));
		completedList.setSize(new Dimension(138, 233));
		completedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		completedList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) completedList.getSelectedValue();
					if (anime != null)
					{
					AnimeData data = completedMap.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay("Concluso");
					animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
				    AnimeIndex.animeInformation.minusButton.setEnabled(false);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(false);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
				    AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
					}
				}
				
				deleteButton.setEnabled(true);
				String anime = (String) completedList.getSelectedValue();
				if (anime != null)
				{
				AnimeData data = completedMap.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setDay("Concluso");
				animeInformation.exitDaycomboBox.setEnabled(false);
				String path = data.getImagePath();
				File file = new File(path);
				if (file.exists())
					animeInformation.setImage(data.getImagePath());
				else
				{
					animeInformation.setImage("deafult");
				}
				
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				
				if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
				else
					 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
				    
				}
				
			}
		});

		completedAnimeScroll.setViewportView(completedList);

		
		JPanel airingAnime = new JPanel();
		cardContainer.add(airingAnime, "Anime in Corso");
		airingAnime.setLayout(new BorderLayout(0, 0));
		
		JScrollPane airingAnimeScroll = new JScrollPane();
		airingAnime.add(airingAnimeScroll, BorderLayout.CENTER);
		
		airingList = new JList(airingModel);
		airingList.setMaximumSize(new Dimension(157, 233));
		airingList.setMinimumSize(new Dimension(138, 233));
		airingList.setPreferredSize(new Dimension(138, 233));
		airingList.setSize(new Dimension(138, 233));
		airingList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) airingList.getSelectedValue();
					if (anime != null)
					{
					AnimeData data = airingMap.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay("Concluso");
					animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
					if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
	                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					else
						 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					
					AnimeIndex.animeInformation.minusButton.setEnabled(true);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				    AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				    AnimeIndex.animeInformation.finishedButton.setEnabled(true);
					}
				}
				deleteButton.setEnabled(true);
				String anime = (String) airingList.getSelectedValue();
				if (anime != null)
				{
				AnimeData data = airingMap.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setImage(data.getImagePath());
				animeInformation.setDay(data.getDay());
				animeInformation.exitDaycomboBox.setEnabled(true);
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				}
			}
		});
		airingAnimeScroll.setViewportView(airingList);
		
		JPanel ova = new JPanel();
		cardContainer.add(ova, "OAV");
		ova.setLayout(new BorderLayout(0, 0));
		
		JScrollPane ovaScroll = new JScrollPane();
		ova.add(ovaScroll, BorderLayout.CENTER);
		
		ovaList = new JList(ovaModel);
		ovaList.setMaximumSize(new Dimension(138, 233));
		ovaList.setMinimumSize(new Dimension(138, 233));
		ovaList.setPreferredSize(new Dimension(138, 233));
		ovaList.setSize(new Dimension(138, 233));
		ovaList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) ovaList.getSelectedValue();
					if (anime != null)
					{
					AnimeData data = ovaMap.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay("Concluso");
					animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
					if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
	                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					else
						 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					
					AnimeIndex.animeInformation.minusButton.setEnabled(true);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				    AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				    AnimeIndex.animeInformation.finishedButton.setEnabled(true);
					}
				}
				deleteButton.setEnabled(true);
				String anime = (String) ovaList.getSelectedValue();
				if (anime != null)
				{
				AnimeData data = ovaMap.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setImage(data.getImagePath());
				animeInformation.setDay("Concluso");
				animeInformation.exitDaycomboBox.setEnabled(false);
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				}
			}
		});
		ovaScroll.setViewportView(ovaList);
		
		JPanel film = new JPanel();
		cardContainer.add(film, "Film");
		film.setLayout(new BorderLayout(0, 0));
		
		JScrollPane filmScroll = new JScrollPane();
		film.add(filmScroll, BorderLayout.CENTER);
		
		filmList = new JList(filmModel);
		filmList.setMaximumSize(new Dimension(138, 233));
		filmList.setMinimumSize(new Dimension(138, 233));
		filmList.setPreferredSize(new Dimension(138, 233));
		filmList.setSize(new Dimension(138, 233));
		filmList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) filmList.getSelectedValue();
					if (anime != null)
					{
					AnimeData data = filmMap.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay("Concluso");
					animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
					if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
	                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					else
						 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					
					AnimeIndex.animeInformation.minusButton.setEnabled(true);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				    AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				    AnimeIndex.animeInformation.finishedButton.setEnabled(true);
					}
				}
				deleteButton.setEnabled(true);
				String anime = (String) filmList.getSelectedValue();
				if (anime != null)
				{
				AnimeData data = filmMap.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setImage(data.getImagePath());
				animeInformation.setDay("Concluso");
				animeInformation.exitDaycomboBox.setEnabled(false);
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				}
			}
		});
		filmScroll.setViewportView(filmList);
		
		JPanel completedToSeeAnime = new JPanel();
		cardContainer.add(completedToSeeAnime, "Completi Da Vedere");
		completedToSeeAnime.setLayout(new BorderLayout(0, 0));
		
		JScrollPane completedToSeeScroll = new JScrollPane();
		completedToSeeAnime.add(completedToSeeScroll, BorderLayout.CENTER);
		
		completedToSeeList = new JList(completedToSeeModel);
		completedToSeeList.setMaximumSize(new Dimension(138, 233));
		completedToSeeList.setMinimumSize(new Dimension(138, 233));
		completedToSeeList.setPreferredSize(new Dimension(138, 233));
		completedToSeeList.setSize(new Dimension(138, 233));
		completedToSeeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) completedToSeeList.getSelectedValue();
					if (anime != null)
					{
					AnimeData data = completedToSeeMap.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay("Concluso");
					animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
					if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
	                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					else
						 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					
					AnimeIndex.animeInformation.minusButton.setEnabled(true);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
				    AnimeIndex.animeInformation.finishedButton.setEnabled(true);
					}
				}
				deleteButton.setEnabled(true);
				String anime = (String) completedToSeeList.getSelectedValue();
				if (anime != null)
				{
				AnimeData data = completedToSeeMap.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setImage(data.getImagePath());
				animeInformation.setDay("Concluso");
				animeInformation.exitDaycomboBox.setEnabled(false);
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				}
			}
		});
		completedToSeeScroll.setViewportView(completedToSeeList);
		
		JPanel dayExitAnime = new JPanel();
		cardContainer.add(dayExitAnime, "Uscite del Giorno");
		dayExitAnime.setLayout(new BorderLayout(0, 0));
		
		JScrollPane dayExitScroll = new JScrollPane();
		dayExitAnime.add(dayExitScroll, BorderLayout.CENTER);
		
		
		Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week abbreviated
		String currentDay = simpleDateformat.format(now);
		
		Object[] airingArray = airingModel.toArray();
		for (int i = 0; i < airingArray.length; i++)
		{
			String anime = (String) airingArray[i];
			AnimeData data = airingMap.get(anime);
			if (data.getDay().equalsIgnoreCase(currentDay))
			{
				dayExitModel.addElement(anime);
			}
		}
		dayExitList = new JList(dayExitModel);
		dayExitList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) dayExitList.getSelectedValue();
					if (anime != null)
					{
					AnimeData data = airingMap.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay("Concluso");
					animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
					if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
	                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					else
						 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					
					AnimeIndex.animeInformation.minusButton.setEnabled(true);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
					}
				}
				deleteButton.setEnabled(true);
				String anime = (String) dayExitList.getSelectedValue();
				if (anime != null)
				{
				AnimeData data = airingMap.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setImage(data.getImagePath());
				animeInformation.setDay(data.getDay());
				if (data.getDay().equalsIgnoreCase("concluso"))
					animeInformation.exitDaycomboBox.setEnabled(false);
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				}
			}
		});
		dayExitList.setSize(new Dimension(138, 233));
		dayExitList.setPreferredSize(new Dimension(138, 233));
		dayExitList.setMinimumSize(new Dimension(138, 233));
		dayExitList.setMaximumSize(new Dimension(138, 233));
		dayExitScroll.setViewportView(dayExitList);
		
		JPanel searchCard = new JPanel();
		cardContainer.add(searchCard, "Ricerca");
		searchCard.setLayout(new BorderLayout(0, 0));
		
		JScrollPane searchScroll = new JScrollPane();
		searchCard.add(searchScroll, BorderLayout.CENTER);
		
		searchList = new JList(searchModel);
		searchList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) searchList.getSelectedValue();
					if (anime != null)
					{
					TreeMap<String,AnimeData> map = getMap();
					AnimeData data = map.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay(data.getDay());
					if (data.getDay().equalsIgnoreCase("concluso"))
						animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
					if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
	                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					else
						 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					
					AnimeIndex.animeInformation.minusButton.setEnabled(true);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				    AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				    AnimeIndex.animeInformation.finishedButton.setEnabled(true);
					}
				}
				deleteButton.setEnabled(true);
				String anime = (String) searchList.getSelectedValue();
				if (anime != null)
				{
				TreeMap<String,AnimeData> map = getMap();
				AnimeData data = map.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setImage(data.getImagePath());
				animeInformation.setDay(data.getDay());
				animeInformation.exitDaycomboBox.setEnabled(true);
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				}
			}
		});
		searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchList.setSize(new Dimension(138, 233));
		searchList.setPreferredSize(new Dimension(138, 233));
		searchList.setMinimumSize(new Dimension(138, 233));
		searchList.setMaximumSize(new Dimension(138, 233));
		searchScroll.setViewportView(searchList);
		
		JPanel filterCard = new JPanel();
		cardContainer.add(filterCard, "Filtri");
		filterCard.setLayout(new BorderLayout(0, 0));
		
		JScrollPane filterScroll = new JScrollPane();
		filterCard.add(filterScroll, BorderLayout.CENTER);
		
		filterList = new JList(filterModel);
		filterList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try
				{
					saveModifiedInformation();
				}
				catch (NullPointerException e1)
				{
					deleteButton.setEnabled(true);
					String anime = (String) searchList.getSelectedValue();
					if (anime != null)
					{
					TreeMap<String,AnimeData> map = getMap();
					AnimeData data = map.get(anime);
					animeInformation.setAnimeName(anime);
					animeInformation.setCurrentEp(data.getCurrentEpisode());
					animeInformation.setTotalEp(data.getTotalEpisode());
					animeInformation.setFansub(data.getFansub());
					animeInformation.setLink(data.getFansubLink());
					animeInformation.setNote(data.getNote());
					animeInformation.setDay(data.getDay());
					if (data.getDay().equalsIgnoreCase("concluso"))
						animeInformation.exitDaycomboBox.setEnabled(false);
					String path = data.getImagePath();
					File file = new File(path);
					if (file.exists())
						animeInformation.setImage(data.getImagePath());
					else
					{
						animeInformation.setImage("deafult");
					}
					
					if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
						animeInformation.plusButton.setEnabled(false);
					else
						animeInformation.plusButton.setEnabled(true);
					
					if(data.getFansubLink() != null && !(data.getFansubLink().isEmpty()))
	                    AnimeIndex.animeInformation.btnOpen.setEnabled(true);
					else
						 AnimeIndex.animeInformation.btnOpen.setEnabled(false);
					
					AnimeIndex.animeInformation.minusButton.setEnabled(true);
				    AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
				    AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
				    AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);
				    AnimeIndex.animeInformation.finishedButton.setEnabled(true);
					}
				}
				deleteButton.setEnabled(true);
				String anime = (String) searchList.getSelectedValue();
				if (anime != null)
				{
				TreeMap<String,AnimeData> map = getMap();
				AnimeData data = map.get(anime);
				animeInformation.setAnimeName(anime);
				animeInformation.setCurrentEp(data.getCurrentEpisode());
				animeInformation.setTotalEp(data.getTotalEpisode());
				animeInformation.setFansub(data.getFansub());
				animeInformation.setLink(data.getFansubLink());
				animeInformation.setNote(data.getNote());
				animeInformation.setImage(data.getImagePath());
				animeInformation.setDay(data.getDay());
				animeInformation.exitDaycomboBox.setEnabled(true);
				if(data.getCurrentEpisode().equals(data.getTotalEpisode()))
					animeInformation.plusButton.setEnabled(false);
				else
					animeInformation.plusButton.setEnabled(true);
				}
			}
		});
		filterList.setSize(new Dimension(138, 233));
		filterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filterList.setPreferredSize(new Dimension(138, 233));
		filterList.setMinimumSize(new Dimension(138, 233));
		filterList.setMaximumSize(new Dimension(138, 233));
		filterScroll.setViewportView(filterList);
		
		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));

		deleteButton = new JButton("Elimina");
		deleteButton.setPreferredSize(new Dimension(159, 21));
		deleteButton.setMaximumSize(new Dimension(159, 21));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String type = (String) animeTypeComboBox.getSelectedItem();
				DefaultListModel model = null;
				JList list = null;
				TreeMap<String,AnimeData> map = null;
				String name = null;
				if (type.equalsIgnoreCase("anime completati"))
				{
					model = completedModel;
					list = completedList;
					map = completedMap;
					int index = list.getSelectedIndex();
					name = (String) model.getElementAt(index);
					model.removeElementAt(index);
					index -= 1;
					completedList.setSelectedIndex(index);
				}				
				else if (type.equalsIgnoreCase("anime in corso"))
				{
					model = airingModel;
					list = airingList;
					map = airingMap;
					int index = list.getSelectedIndex();
					name = (String) model.getElementAt(index);
					model.removeElementAt(index);
					index -= 1;
					airingList.setSelectedIndex(index);
				}
				else if (type.equalsIgnoreCase("oav"))
				{
					model = ovaModel;
					list = ovaList;
					map = ovaMap;
					int index = list.getSelectedIndex();
					name = (String) model.getElementAt(index);
					model.removeElementAt(index);
					index -= 1;
					ovaList.setSelectedIndex(index);
				}
				else if (type.equalsIgnoreCase("film"))
				{
					model = filmModel;
					list = filmList;
					map = filmMap;
					int index = list.getSelectedIndex();
					name = (String) model.getElementAt(index);
					model.removeElementAt(index);
					index -= 1;
					filmList.setSelectedIndex(index);
				}
				else if (type.equalsIgnoreCase("completi da vedere"))
				{
					model = completedToSeeModel;
					list = completedToSeeList;
					map = completedToSeeMap;
					int index = list.getSelectedIndex();
					name = (String) model.getElementAt(index);
					model.removeElementAt(index);
					index -= 1;
					completedToSeeList.setSelectedIndex(index);
				}
//				int index = list.getSelectedIndex();
//				String name = (String) model.getElementAt(index);
				String path = map.get(name).getImagePath();
				File image = new File(path);
				try {
					FileManager.deleteData(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
				map.remove(name);
				if (!list.isSelectionEmpty())					
					deleteButton.setEnabled(true);
				else
					deleteButton.setEnabled(false);
			}
		});
		deleteButton.setEnabled(false);
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		
		
		addButton = new JButton("Aggiungi");
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
	    	}
	    });
	    setFilterButton.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/ellipse_icon3.png")));
		buttonPanel.add(setFilterButton, BorderLayout.NORTH);
		
		fansubList = FileManager.loadFansubList();
		animeInformation = new AnimeInformation();
		mainFrame.add(animeInformation, BorderLayout.CENTER);
		
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
	
	public void SearchInList(String searchedVal, DefaultListModel modelToSearch) 
	{
		Object[] mainArray = modelToSearch.toArray();			
			searchModel.clear();
			for (int i = 0; i < mainArray.length; i++) {
				String value = (String) mainArray[i];
				value = value.toLowerCase();
				searchedVal = searchedVal.toLowerCase();
				if (value.contains(searchedVal))
					searchModel.addElement(mainArray[i]);
			}
		if (searchModel.isEmpty())
			searchModel.addElement("Nessun Anime Corrispondente");
		}

	public static JList getJList()
	{
		String listName = getList();
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
	
	public static DefaultListModel getModel()
	{
		String listName = getList();
		DefaultListModel model= null;
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

	public void saveModifiedInformation()
	{
		String name = animeInformation.lblAnimeName.getText();
		String currEp = animeInformation.currentEpisodeField.getText();
		String totEp = animeInformation.totalEpisodeText.getText();
		String fansub = (String) animeInformation.fansubComboBox.getSelectedItem();
		String fansubLink = animeInformation.getLink();
		String note = animeInformation.noteTextArea.getText();
		String day = (String) animeInformation.exitDaycomboBox.getSelectedItem();
		//TODO mettere che prende il link e il nome del linke li mette dentro all'oggetto data.
		
		String list = AnimeIndex.getList();
		if (list.equalsIgnoreCase("Anime Completati"))
			{
			String image = AnimeIndex.completedMap.get(name).getImageName();
			String id = AnimeIndex.completedMap.get(name).getId();
			AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day, id);
			AnimeIndex.completedMap.put(name, data);
			}
		else if (list.equalsIgnoreCase("Anime in Corso"))
			{
			String image = AnimeIndex.airingMap.get(name).getImageName();
			String id = AnimeIndex.airingMap.get(name).getId();
			AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day, id);
			AnimeIndex.airingMap.put(name, data);
			}
		else if (list.equalsIgnoreCase("OAV"))
			{
			String image = AnimeIndex.ovaMap.get(name).getImageName();
			String id = AnimeIndex.ovaMap.get(name).getId();
			AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day, id);
			AnimeIndex.ovaMap.put(name, data);
			}
		else if (list.equalsIgnoreCase("Film"))
		{
			String image = AnimeIndex.filmMap.get(name).getImageName();
			String id = AnimeIndex.filmMap.get(name).getId();
			AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day, id);
			AnimeIndex.filmMap.put(name, data);
		}
		else if (list.equalsIgnoreCase("Completi Da Vedere"))
		{
			String image = AnimeIndex.completedToSeeMap.get(name).getImageName();
			String id = AnimeIndex.completedToSeeMap.get(name).getId();
			AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day, id);
			AnimeIndex.completedToSeeMap.put(name, data);
		}
	}

	public void setFansubMap(TreeMap<String, String> fansubMap) 
	{
		AnimeIndex.fansubMap.putAll(fansubMap);
	}
	
}

