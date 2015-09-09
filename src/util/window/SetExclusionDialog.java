package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import main.AnimeIndex;
import util.SearchBar;
import util.SortedListModel;

import java.awt.CardLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JCheckBox;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SetExclusionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private SearchBar searchBarCheck;
	private SearchBar searchBarExclusions;
	private JComboBox comboBox;
	private JButton cancelButton;
	private SortedListModel totalModel = new SortedListModel();
	private SortedListModel excludedModel = new SortedListModel();
	private SortedListModel totalSearchModel = new SortedListModel();
	private SortedListModel excludedSearchModel = new SortedListModel();
	private JButton excludeButton;
	private JButton includeButton;
	private JList listToCheck;
	private JList listToExclude;
	private JPanel totalPane;
	private JList searchListToCheck;
	private JList searchListToExclude;
	private JPanel excludedPane;
	private JLabel lblCampiEsclusi;
	
	private String selectedAnime = "";
	
	private JCheckBox imgExcludeCheck;
	private JCheckBox totEpExcludeCheck;
	private JCheckBox durationExcludeCheck;
	private JCheckBox releaseDateExcludeCheck;
	private JCheckBox finischDateExcludeCheck;
	private JCheckBox typeExcludeCheck;
	private TreeMap<String,boolean[]> exclusionSessionAnime =  new TreeMap<String,boolean[]>();
	private ArrayList<String> checkSessionAnime = new ArrayList<String>();
	/**
	 * Create the dialog..
	 */
	public SetExclusionDialog() {
		super(AnimeIndex.frame,true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				cancelButton.requestFocusInWindow();
				comboBox.setSelectedItem(AnimeIndex.animeTypeComboBox.getSelectedItem());
				loadModel();
				if(AnimeIndex.animeInformation.selectExcludedAnimeAtWindowOpened==true)
				{
					listToExclude.setSelectedValue(AnimeIndex.animeInformation.lblAnimeName.getText(), true);
					imgExcludeCheck.setEnabled(true);
					totEpExcludeCheck.setEnabled(true);
					durationExcludeCheck.setEnabled(true);
					releaseDateExcludeCheck.setEnabled(true);
					finischDateExcludeCheck.setEnabled(true);
					typeExcludeCheck.setEnabled(true);
				}
			}
		});
		setTitle("Esclusioni");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 618, 271);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{15, 196, 16, 103, 106, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{23, 0, 0, 0, 31, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Anime da Controllare");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridwidth = 2;
			gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			Component rigidArea = Box.createRigidArea(new Dimension(1, 90));
			GridBagConstraints gbc_rigidArea = new GridBagConstraints();
			gbc_rigidArea.gridheight = 4;
			gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
			gbc_rigidArea.gridx = 2;
			gbc_rigidArea.gridy = 0;
			contentPanel.add(rigidArea, gbc_rigidArea);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Anime Esclusi");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.gridwidth = 2;
			gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 3;
			gbc_lblNewLabel_1.gridy = 0;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			searchBarCheck = new SearchBar();
			searchBarCheck.setFont(AnimeIndex.segui.deriveFont(11f));
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
			{
				lblCampiEsclusi = new JLabel("Campi Esclusi");
				GridBagConstraints gbc_lblCampiEsclusi = new GridBagConstraints();
				gbc_lblCampiEsclusi.insets = new Insets(0, 0, 5, 0);
				gbc_lblCampiEsclusi.gridx = 5;
				gbc_lblCampiEsclusi.gridy = 1;
				contentPanel.add(lblCampiEsclusi, gbc_lblCampiEsclusi);
			}
			searchBarCheck.setIcon(icon);
			searchBarCheck.setForeground(Color.LIGHT_GRAY);
			searchBarCheck.setBackground(Color.BLACK);
			GridBagConstraints gbc_searchBarCheck = new GridBagConstraints();
			gbc_searchBarCheck.gridwidth = 2;
			gbc_searchBarCheck.insets = new Insets(0, 0, 5, 5);
			gbc_searchBarCheck.fill = GridBagConstraints.HORIZONTAL;
			gbc_searchBarCheck.gridx = 0;
			gbc_searchBarCheck.gridy = 1;
			contentPanel.add(searchBarCheck, gbc_searchBarCheck);
			searchBarCheck.setColumns(10);
			searchBarCheck.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent documentEvent) {
					}
				@Override
				public void insertUpdate(DocumentEvent e)
				{
					searchListToCheck.clearSelection();
					String search = searchBarCheck.getText();
					CardLayout cl = (CardLayout)(totalPane.getLayout());
			        cl.show(totalPane, "searchList");
					searchInList(search, totalModel, totalSearchModel,searchListToCheck);
				}

				@Override
				public void removeUpdate(DocumentEvent e)
				{
					searchListToCheck.clearSelection();
					SortedListModel model = null;
					String search = searchBarCheck.getText();
					JList list = listToCheck;
					list.clearSelection();
					
						if (!search.isEmpty())
						{	
						CardLayout cl = (CardLayout)(totalPane.getLayout());
				        cl.show(totalPane, "searchList");
				        searchInList(search, totalModel, totalSearchModel,searchListToCheck);
						}
						else
						{
							CardLayout cl = (CardLayout)(totalPane.getLayout());
					        cl.show(totalPane, "totalList");
						}
				}
			});
		}
		{
			searchBarExclusions = new SearchBar();
			searchBarExclusions.setFont(AnimeIndex.segui.deriveFont(11f));
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
			searchBarExclusions.setIcon(icon);
			searchBarExclusions.setForeground(Color.LIGHT_GRAY);
			searchBarExclusions.setBackground(Color.BLACK);
			GridBagConstraints gbc_searchBarExlusions = new GridBagConstraints();
			gbc_searchBarExlusions.gridwidth = 2;
			gbc_searchBarExlusions.insets = new Insets(0, 0, 5, 5);
			gbc_searchBarExlusions.fill = GridBagConstraints.HORIZONTAL;
			gbc_searchBarExlusions.gridx = 3;
			gbc_searchBarExlusions.gridy = 1;
			contentPanel.add(searchBarExclusions, gbc_searchBarExlusions);
			searchBarExclusions.setColumns(10);
			searchBarExclusions.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent documentEvent) {
					}
				@Override
				public void insertUpdate(DocumentEvent e)
				{
					searchListToExclude.clearSelection();
					String search = searchBarExclusions.getText();
					CardLayout cl = (CardLayout)(excludedPane.getLayout());
			        cl.show(excludedPane, "excludedSearchedList");
					searchInList(search, excludedModel, excludedSearchModel,searchListToExclude);
				}

				@Override
				public void removeUpdate(DocumentEvent e)
				{
					searchListToExclude.clearSelection();
					String search = searchBarExclusions.getText();
					JList list = listToExclude;
					list.clearSelection();
						if (!search.isEmpty())
						{	
						CardLayout cl = (CardLayout)(excludedPane.getLayout());
				        cl.show(excludedPane, "excludedSearchedList");
				        searchInList(search, excludedModel, excludedSearchModel,searchListToExclude);
						}
						else
						{
							CardLayout cl = (CardLayout)(excludedPane.getLayout());
					        cl.show(excludedPane, "excludedList");
						}
				}
			});
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.gridheight = 7;
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				totalPane = new JPanel();
				scrollPane.setViewportView(totalPane);
				totalPane.setLayout(new CardLayout(0, 0));
				
				listToCheck = new JList(totalModel);
				listToCheck.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						imgExcludeCheck.setEnabled(false);
						totEpExcludeCheck.setEnabled(false);
						durationExcludeCheck.setEnabled(false);
						releaseDateExcludeCheck.setEnabled(false);
						finischDateExcludeCheck.setEnabled(false);
						typeExcludeCheck.setEnabled(false);
						
						imgExcludeCheck.setSelected(false);
						totEpExcludeCheck.setSelected(false);
						durationExcludeCheck.setSelected(false);
						releaseDateExcludeCheck.setSelected(false);
						finischDateExcludeCheck.setSelected(false);
						typeExcludeCheck.setSelected(false);
					}
				});
				listToCheck.setFont(AnimeIndex.segui.deriveFont(12f));
				listToCheck.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						listToExclude.clearSelection();
						searchListToExclude.clearSelection();
					}
				});
				listToCheck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				listToCheck.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						excludeButton.setEnabled(true);
						includeButton.setEnabled(false);
					}
				});
				listToCheck.setBounds(0, 0, 196, 159);
				totalPane.add(listToCheck, "totalList");
				
				searchListToCheck = new JList(totalSearchModel);
				searchListToCheck.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						imgExcludeCheck.setEnabled(false);
						totEpExcludeCheck.setEnabled(false);
						durationExcludeCheck.setEnabled(false);
						releaseDateExcludeCheck.setEnabled(false);
						finischDateExcludeCheck.setEnabled(false);
						typeExcludeCheck.setEnabled(false);
						
						imgExcludeCheck.setSelected(false);
						totEpExcludeCheck.setSelected(false);
						durationExcludeCheck.setSelected(false);
						releaseDateExcludeCheck.setSelected(false);
						finischDateExcludeCheck.setSelected(false);
						typeExcludeCheck.setSelected(false);
					}
				});
				searchListToCheck.setFont(AnimeIndex.segui.deriveFont(12f));
				searchListToCheck.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent arg0) {
					
						listToExclude.clearSelection();
						searchListToExclude.clearSelection();
					}
				});
				searchListToCheck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				searchListToCheck.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						excludeButton.setEnabled(true);
						includeButton.setEnabled(false);							
					}
				});
				searchListToCheck.setBounds(0, 0, 196, 159);
				totalPane.add(searchListToCheck, "searchList");
			}
		}
		{
			excludeButton = new JButton(">>");
			excludeButton.setEnabled(false);
			excludeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name= null;
					if(searchBarCheck.getText().isEmpty())
						name = (String) listToCheck.getSelectedValue();
					else
						name = (String) searchListToCheck.getSelectedValue();
					totalModel.removeElement(name);
					excludedModel.addElement(name);
					listToCheck.clearSelection();
					searchListToCheck.clearSelection();
					listToExclude.clearSelection();
					searchListToExclude.clearSelection();
					if(checkSessionAnime.contains(name))
					{
						checkSessionAnime.remove(name);
					}
					
					boolean[] defaultExcludionFiled = {true,true,true,true,true,true};
					exclusionSessionAnime.put(name, defaultExcludionFiled);
					
					if(searchBarExclusions.getText().isEmpty())
						listToExclude.setSelectedValue(name, true);
					else
					{
						searchInList(searchBarCheck.getText(), totalModel, totalSearchModel,searchListToCheck);
						searchInList(searchBarExclusions.getText(), excludedModel, excludedSearchModel,searchListToExclude);
						searchListToExclude.setSelectedValue(name, true);
					}

					imgExcludeCheck.setEnabled(true);
					totEpExcludeCheck.setEnabled(true);
					durationExcludeCheck.setEnabled(true);
					releaseDateExcludeCheck.setEnabled(true);
					finischDateExcludeCheck.setEnabled(true);
					typeExcludeCheck.setEnabled(true);
					
					excludeButton.setEnabled(false);
					includeButton.setEnabled(true);
				}
			});
			{
				imgExcludeCheck = new JCheckBox("Immagine");
				imgExcludeCheck.setEnabled(false);
				imgExcludeCheck.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(selectedAnime!=null)
							exclusionSessionAnime.put(selectedAnime, setExclusionFields(selectedAnime));
					}
				});
				GridBagConstraints gbc_imgExcludeCheck = new GridBagConstraints();
				gbc_imgExcludeCheck.anchor = GridBagConstraints.WEST;
				gbc_imgExcludeCheck.insets = new Insets(0, 0, 5, 0);
				gbc_imgExcludeCheck.gridx = 5;
				gbc_imgExcludeCheck.gridy = 2;
				contentPanel.add(imgExcludeCheck, gbc_imgExcludeCheck);
			}
			{
				totEpExcludeCheck = new JCheckBox("Episodi Totali");
				totEpExcludeCheck.setEnabled(false);
				totEpExcludeCheck.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(selectedAnime!=null)
							exclusionSessionAnime.put(selectedAnime, setExclusionFields(selectedAnime));
					}
				});
				GridBagConstraints gbc_totEpExcludeCheck = new GridBagConstraints();
				gbc_totEpExcludeCheck.anchor = GridBagConstraints.WEST;
				gbc_totEpExcludeCheck.insets = new Insets(0, 0, 5, 0);
				gbc_totEpExcludeCheck.gridx = 5;
				gbc_totEpExcludeCheck.gridy = 3;
				contentPanel.add(totEpExcludeCheck, gbc_totEpExcludeCheck);
			}
			GridBagConstraints gbc_excludeButton = new GridBagConstraints();
			gbc_excludeButton.insets = new Insets(0, 0, 5, 5);
			gbc_excludeButton.gridx = 2;
			gbc_excludeButton.gridy = 4;
			contentPanel.add(excludeButton, gbc_excludeButton);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 2;
			gbc_scrollPane.gridheight = 7;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 3;
			gbc_scrollPane.gridy = 2;
			contentPanel.add(scrollPane, gbc_scrollPane);
			
			excludedPane = new JPanel();
			scrollPane.setViewportView(excludedPane);
			excludedPane.setLayout(new CardLayout(0, 0));
			
			listToExclude = new JList(excludedModel);
			listToExclude.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					imgExcludeCheck.setEnabled(true);
					totEpExcludeCheck.setEnabled(true);
					durationExcludeCheck.setEnabled(true);
					releaseDateExcludeCheck.setEnabled(true);
					finischDateExcludeCheck.setEnabled(true);
					typeExcludeCheck.setEnabled(true);
				}
			});
			listToExclude.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mousePressed(MouseEvent e) {
					if(searchBarExclusions.getText().isEmpty())
						selectedAnime = (String)listToExclude.getSelectedValue();
					else
						selectedAnime = (String)searchListToExclude.getSelectedValue();
					if(selectedAnime!=null)
						getExclusionFields(selectedAnime);
					listToCheck.clearSelection();
					searchListToCheck.clearSelection();
				}
			});
			listToExclude.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listToExclude.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
										
					excludeButton.setEnabled(false);
					includeButton.setEnabled(true);
					
					if(searchBarExclusions.getText().isEmpty())
						selectedAnime = (String)listToExclude.getSelectedValue();
					else
						selectedAnime = (String)searchListToExclude.getSelectedValue();
					if(selectedAnime!=null)
						getExclusionFields(selectedAnime);
				}
			});
			listToExclude.setFont(AnimeIndex.segui.deriveFont(12f));
			listToExclude.setBounds(0, 0, 176, 159);
			excludedPane.add(listToExclude, "excludedList");
			
			searchListToExclude = new JList(excludedSearchModel);
			searchListToExclude.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					imgExcludeCheck.setEnabled(true);
					totEpExcludeCheck.setEnabled(true);
					durationExcludeCheck.setEnabled(true);
					releaseDateExcludeCheck.setEnabled(true);
					finischDateExcludeCheck.setEnabled(true);
					typeExcludeCheck.setEnabled(true);
				}
			});
			searchListToExclude.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					listToCheck.clearSelection();
					searchListToCheck.clearSelection();
				}
			});
			searchListToExclude.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					excludeButton.setEnabled(false);
					includeButton.setEnabled(true);
				}
			});
			searchListToExclude.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			searchListToExclude.setFont(AnimeIndex.segui.deriveFont(12f));
			searchListToExclude.setBounds(0, 0, 176, 159);
			excludedPane.add(searchListToExclude, "excludedSearchedList");
		}
		{
			includeButton = new JButton("<<");
			includeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String name = null;
					if (searchBarExclusions.getText().isEmpty())
						name = (String) listToExclude.getSelectedValue();
					else
						name = (String) searchListToExclude.getSelectedValue();
					
					checkSessionAnime.add(name);
					if(exclusionSessionAnime.containsKey(name))
						exclusionSessionAnime.remove(name);
					excludedModel.removeElement(name);
					String type = (String) comboBox.getSelectedItem();
					if (type.equalsIgnoreCase("anime completati"))
					{
						if (AnimeIndex.completedMap.containsKey(name))
						{			
							totalModel.addElement(name);
						}
					}
					
					if (type.equalsIgnoreCase("anime in corso"))
					{
						if (AnimeIndex.airingMap.containsKey(name))
						{
							totalModel.addElement(name);
						}
					}
					
					if (type.equalsIgnoreCase("oav"))
					{
						if (AnimeIndex.ovaMap.containsKey(name))
						{
							totalModel.addElement(name);
						}
					}
					
					if (type.equalsIgnoreCase("film"))
					{
						if (AnimeIndex.filmMap.containsKey(name))
						{
							totalModel.addElement(name);
						}
					}
					
					if (type.equalsIgnoreCase("completi da vedere"))
					{
						if (AnimeIndex.completedToSeeMap.containsKey(name))
						{
							totalModel.addElement(name);
						}
					}
					
					if (AnimeIndex.completedMap.containsKey(name))
					{			
						comboBox.setSelectedItem("Anime Completati");
					}
					if (AnimeIndex.airingMap.containsKey(name))
					{
						comboBox.setSelectedItem("Anime in Corso");
					}
					if (AnimeIndex.ovaMap.containsKey(name))
					{
						comboBox.setSelectedItem("OAV");
					}
					if (AnimeIndex.filmMap.containsKey(name))
					{
						comboBox.setSelectedItem("Film");
					}
					if (AnimeIndex.completedToSeeMap.containsKey(name))
					{
						comboBox.setSelectedItem("Completi Da Vedere");
					}
					
					listToExclude.clearSelection();
					searchListToExclude.clearSelection();
					listToCheck.clearSelection();
					searchListToCheck.clearSelection();
					if(searchBarCheck.getText().isEmpty())
						listToCheck.setSelectedValue(name, true);
					else
					{
						searchInList(searchBarExclusions.getText(), excludedModel, excludedSearchModel,searchListToExclude);
						searchInList(searchBarCheck.getText(), totalModel, totalSearchModel,searchListToCheck);
						searchListToCheck.setSelectedValue(name, true);
					}
						
					imgExcludeCheck.setEnabled(false);
					totEpExcludeCheck.setEnabled(false);
					durationExcludeCheck.setEnabled(false);
					releaseDateExcludeCheck.setEnabled(false);
					finischDateExcludeCheck.setEnabled(false);
					typeExcludeCheck.setEnabled(false);
					
					imgExcludeCheck.setSelected(false);
					totEpExcludeCheck.setSelected(false);
					durationExcludeCheck.setSelected(false);
					releaseDateExcludeCheck.setSelected(false);
					finischDateExcludeCheck.setSelected(false);
					typeExcludeCheck.setSelected(false);
					
					includeButton.setEnabled(false);
					excludeButton.setEnabled(true);
					}
			});
			{
				durationExcludeCheck = new JCheckBox("Durata Episodio");
				durationExcludeCheck.setEnabled(false);
				durationExcludeCheck.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(selectedAnime!=null)
							exclusionSessionAnime.put(selectedAnime, setExclusionFields(selectedAnime));
					}
				});
				GridBagConstraints gbc_durationExcludeCheck = new GridBagConstraints();
				gbc_durationExcludeCheck.anchor = GridBagConstraints.WEST;
				gbc_durationExcludeCheck.insets = new Insets(0, 0, 5, 0);
				gbc_durationExcludeCheck.gridx = 5;
				gbc_durationExcludeCheck.gridy = 4;
				contentPanel.add(durationExcludeCheck, gbc_durationExcludeCheck);
			}
			includeButton.setEnabled(false);
			GridBagConstraints gbc_includeButton = new GridBagConstraints();
			gbc_includeButton.insets = new Insets(0, 0, 5, 5);
			gbc_includeButton.gridx = 2;
			gbc_includeButton.gridy = 5;
			contentPanel.add(includeButton, gbc_includeButton);
		}
		{
			releaseDateExcludeCheck = new JCheckBox("Data di Inizio");
			releaseDateExcludeCheck.setEnabled(false);
			releaseDateExcludeCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(selectedAnime!=null)
						exclusionSessionAnime.put(selectedAnime, setExclusionFields(selectedAnime));
				}
			});
			GridBagConstraints gbc_releaseDateExcludeCheck = new GridBagConstraints();
			gbc_releaseDateExcludeCheck.anchor = GridBagConstraints.WEST;
			gbc_releaseDateExcludeCheck.insets = new Insets(0, 0, 5, 0);
			gbc_releaseDateExcludeCheck.gridx = 5;
			gbc_releaseDateExcludeCheck.gridy = 5;
			contentPanel.add(releaseDateExcludeCheck, gbc_releaseDateExcludeCheck);
		}
		{
			Component rigidArea = Box.createRigidArea(new Dimension(1, 20));
			GridBagConstraints gbc_rigidArea = new GridBagConstraints();
			gbc_rigidArea.gridheight = 2;
			gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
			gbc_rigidArea.gridx = 2;
			gbc_rigidArea.gridy = 6;
			contentPanel.add(rigidArea, gbc_rigidArea);
		}
		{
			comboBox = new JComboBox();
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(!listToExclude.isSelectionEmpty() || !searchListToExclude.isSelectionEmpty())
						includeButton.setEnabled(true);
				}
			});
			comboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					excludeButton.setEnabled(false);
					searchBarCheck.setText("");
					if(listToExclude.isSelectionEmpty())
						includeButton.setEnabled(false);
					listToCheck.clearSelection();
					String list = (String) comboBox.getSelectedItem();

					if (list.equalsIgnoreCase("anime completati"))
						changeModel(AnimeIndex.completedModel);
					
					if (list.equalsIgnoreCase("anime in corso"))
						changeModel(AnimeIndex.airingModel);
					
					if (list.equalsIgnoreCase("oav"))
						changeModel(AnimeIndex.ovaModel);
					
					if (list.equalsIgnoreCase("film"))
						changeModel(AnimeIndex.filmModel);
					
					if (list.equalsIgnoreCase("completi da vedere"))
						changeModel(AnimeIndex.completedToSeeModel);
				}
			});
			{
				finischDateExcludeCheck = new JCheckBox("Data di Fine");
				finischDateExcludeCheck.setEnabled(false);
				finischDateExcludeCheck.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(selectedAnime!=null)
							exclusionSessionAnime.put(selectedAnime, setExclusionFields(selectedAnime));
					}
				});
				GridBagConstraints gbc_finischDateExcludeCheck = new GridBagConstraints();
				gbc_finischDateExcludeCheck.anchor = GridBagConstraints.WEST;
				gbc_finischDateExcludeCheck.insets = new Insets(0, 0, 5, 0);
				gbc_finischDateExcludeCheck.gridx = 5;
				gbc_finischDateExcludeCheck.gridy = 6;
				contentPanel.add(finischDateExcludeCheck, gbc_finischDateExcludeCheck);
			}
			{
				typeExcludeCheck = new JCheckBox("Tipo");
				typeExcludeCheck.setEnabled(false);
				typeExcludeCheck.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(selectedAnime!=null)
							exclusionSessionAnime.put(selectedAnime, setExclusionFields(selectedAnime));
					}
				});
				GridBagConstraints gbc_typeExcludeCheck = new GridBagConstraints();
				gbc_typeExcludeCheck.anchor = GridBagConstraints.WEST;
				gbc_typeExcludeCheck.insets = new Insets(0, 0, 5, 0);
				gbc_typeExcludeCheck.gridx = 5;
				gbc_typeExcludeCheck.gridy = 7;
				contentPanel.add(typeExcludeCheck, gbc_typeExcludeCheck);
			}
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere"}));
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.gridwidth = 2;
			gbc_comboBox.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0;
			gbc_comboBox.gridy = 9;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JButton okButton = new JButton("Salva");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Object[] arr = checkSessionAnime.toArray();
					for(int i=0; i<arr.length; i++)
						AnimeIndex.exclusionAnime.remove((String)arr[i]);
					
					AnimeIndex.exclusionAnime.putAll(exclusionSessionAnime);
					
					if(AnimeIndex.animeInformation.selectExcludedAnimeAtWindowOpened==true)
					{
						AnimeIndex.animeInformation.selectExcludedAnimeAtWindowOpened = false;
					}
					JButton but = (JButton) e.getSource();
					JDialog dialog = (JDialog) but.getTopLevelAncestor();
					dialog.dispose();
				}
			});
			GridBagConstraints gbc_okButton = new GridBagConstraints();
			gbc_okButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_okButton.insets = new Insets(0, 0, 0, 5);
			gbc_okButton.gridx = 3;
			gbc_okButton.gridy = 9;
			contentPanel.add(okButton, gbc_okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("Annulla");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(AnimeIndex.animeInformation.selectExcludedAnimeAtWindowOpened==true)
					{
						AnimeIndex.exclusionAnime.remove(AnimeIndex.animeInformation.lblAnimeName.getText());
						AnimeIndex.animeInformation.selectExcludedAnimeAtWindowOpened = false;
					}
					JButton but = (JButton) arg0.getSource();
					JDialog dialog = (JDialog) but.getTopLevelAncestor();
					dialog.dispose();
				}
			});
			GridBagConstraints gbc_cancelButton = new GridBagConstraints();
			gbc_cancelButton.insets = new Insets(0, 0, 0, 5);
			gbc_cancelButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_cancelButton.gridx = 4;
			gbc_cancelButton.gridy = 9;
			contentPanel.add(cancelButton, gbc_cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
	}
	
	private void loadModel()
	{
		String[] excludedArray = AnimeIndex.exclusionAnime.keySet().toArray(new String[0]);
		excludedModel.addAll(excludedArray);
		
		Object[] totalArray = AnimeIndex.completedModel.toArray();
		for (int i = 0; i < totalArray.length; i++) 
		{
			String name = (String) totalArray[i];
			if (!excludedModel.contains(name))
				totalModel.addElement(name);
		}
		
	}
	
	private void changeModel(SortedListModel model)
	{
		totalModel.clear();
		Object[] totalArray = model.toArray();
		for (int i = 0; i < totalArray.length; i++) 
		{
			String name = (String) totalArray[i];
			if (!excludedModel.contains(name))
				totalModel.addElement(name);
		}
		if (model.isEmpty())
			listToCheck.setEnabled(false);
		else 
			listToCheck.setEnabled(true);
	}
	
	private void searchInList(String searchedVal, SortedListModel modelToSearch, SortedListModel searchModel, JList list) 
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
			list.setEnabled(true);	
		if (searchModel.isEmpty())
		{
			imgExcludeCheck.setEnabled(false);
			totEpExcludeCheck.setEnabled(false);
			durationExcludeCheck.setEnabled(false);
			releaseDateExcludeCheck.setEnabled(false);
			finischDateExcludeCheck.setEnabled(false);
			typeExcludeCheck.setEnabled(false);
			
			imgExcludeCheck.setSelected(false);
			totEpExcludeCheck.setSelected(false);
			durationExcludeCheck.setSelected(false);
			releaseDateExcludeCheck.setSelected(false);
			finischDateExcludeCheck.setSelected(false);
			typeExcludeCheck.setSelected(false);
			
			searchModel.addElement("Nessun Anime Corrispondente");
			list.setEnabled(false);	
		}
	}
	private void getExclusionFields(String name)
	{
		if(name!=null)
		{			
			if(exclusionSessionAnime.containsKey(name))
			{
				boolean[] exc = exclusionSessionAnime.get(name);
				if(exc[0]==false)
					imgExcludeCheck.setSelected(false);
				else
					imgExcludeCheck.setSelected(true);
				if(exc[1]==false)
					totEpExcludeCheck.setSelected(false);
				else
					totEpExcludeCheck.setSelected(true);
				if(exc[2]==false)
					durationExcludeCheck.setSelected(false);
				else
					durationExcludeCheck.setSelected(true);
				if(exc[3]==false)
					releaseDateExcludeCheck.setSelected(false);
				else
					releaseDateExcludeCheck.setSelected(true);
				if(exc[4]==false)
					finischDateExcludeCheck.setSelected(false);
				else
					finischDateExcludeCheck.setSelected(true);
				if(exc[5]==false)
					typeExcludeCheck.setSelected(false);
				else
					typeExcludeCheck.setSelected(true);
			}
			else
			{
				boolean[] exc = AnimeIndex.exclusionAnime.get(name);
				if(exc[0]==false)
					imgExcludeCheck.setSelected(false);
				else
					imgExcludeCheck.setSelected(true);
				if(exc[1]==false)
					totEpExcludeCheck.setSelected(false);
				else
					totEpExcludeCheck.setSelected(true);
				if(exc[2]==false)
					durationExcludeCheck.setSelected(false);
				else
					durationExcludeCheck.setSelected(true);
				if(exc[3]==false)
					releaseDateExcludeCheck.setSelected(false);
				else
					releaseDateExcludeCheck.setSelected(true);
				if(exc[4]==false)
					finischDateExcludeCheck.setSelected(false);
				else
					finischDateExcludeCheck.setSelected(true);
				if(exc[5]==false)
					typeExcludeCheck.setSelected(false);
				else
					typeExcludeCheck.setSelected(true);
			}
		}
	}
	
	private boolean[] setExclusionFields(String name)
	{
		boolean[] exc = {false,false,false,false,false,false};
		if(imgExcludeCheck.isSelected())
			exc[0]=true;
		if(totEpExcludeCheck.isSelected())
			exc[1]=true;
		if(durationExcludeCheck.isSelected())
			exc[2]=true;
		if(releaseDateExcludeCheck.isSelected())
			exc[3]=true;
		if(finischDateExcludeCheck.isSelected())
			exc[4]=true;
		if(typeExcludeCheck.isSelected())
			exc[5]=true;
		return exc;
	}
}
