package util.window;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.AnimeIndex;
import util.ConnectionManager;
import util.MAMUtil;
import util.SearchBar;
import util.SortedListModel;

public class WishlistDialog extends JDialog
{

	public final JPanel contentPanel = new JPanel();
	
	public SearchBar searchBar;
	public JButton btnDeleteAnime;
	public JButton btnAggiungiAnime;
	public JButton btnID;
	private Component verticalStrut_1;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private Component horizontalStrut_4;
	private Component verticalStrut;
	private Component horizontalStrut_6;
	private Component horizontalStrut_5;
	private JScrollPane wishlistPane;
	private JScrollPane droplistPane;
	private JScrollPane searchPane;
	private JPanel cardPane;
	private int idLink;
	private String animeName;
	public JComboBox comboBox;
	public JList searchList;
	public JList wishlist;
	public JList droplist;
	public SortedListModel wishListSearchModel = new SortedListModel();
	public SortedListModel dropListSearchModel = new SortedListModel();
	public static SortedListModel dropListModel = new SortedListModel();
	public static SortedListModel wishListModel = new SortedListModel();

	
	public WishlistDialog()
	{
		super(AnimeIndex.frame, false);
		// super(SwingUtilities.windowForComponent(AnimeIndex.frame));
		setUndecorated(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setResizable(false);
		setBounds(100, 100, 181, 471);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new MatteBorder(1, 1, 0, 1, new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			cardPane = new JPanel();
			contentPanel.add(cardPane, BorderLayout.CENTER);
			cardPane.setLayout(new CardLayout(0, 0));
			{
				wishlistPane = new JScrollPane();
				wishlistPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
				wishlistPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
				cardPane.add(wishlistPane, "wishlist");
				{
					wishlist = new JList(WishlistDialog.wishListModel);
					wishlist.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (SwingUtilities.isRightMouseButton(e))
								{
									JList list = (JList) e.getSource();
									int row = list.locationToIndex(e.getPoint());
									Rectangle bound = list.getCellBounds(row, row);
									if (bound!=null && bound.contains(e.getPoint()))
									{
										list.setSelectedIndex(row);
										JPopupMenu menu = new JPopupMenu();
										
										JMenuItem wishListTransfer = new JMenuItem("Aggiungi a una Lista");
										wishListTransfer.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/add-property-xxl.png")));
										wishListTransfer.addActionListener(new ActionListener() {
											
											@Override
											public void actionPerformed(ActionEvent e)
											{
												AnimeIndex.animeDialog = new AddAnimeDialog();
												AnimeIndex.animeDialog.setLocationRelativeTo(AnimeIndex.mainPanel);
												AnimeIndex.animeDialog.doSearch((String)list.getSelectedValue());
												AnimeIndex.animeDialog.setVisible(true);
											}
										});
										menu.add(wishListTransfer);
										menu.show(list, e.getX(), e.getY());
									}
								}
						}
					});
					wishlist.addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e)
						{
							if (WishlistDialog.wishListModel.contains("Nessun Anime Corrispondete"))
							{
								wishlist.setEnabled(false);
								btnDeleteAnime.setEnabled(false);
								btnID.setEnabled(false);
							}
							else
							{
								btnDeleteAnime.setEnabled(true);
								btnID.setEnabled(true);
							}
						}
					});
					wishlist.setFont(AnimeIndex.segui.deriveFont(12f));
					wishlist.setBorder(UIManager.getBorder("CheckBox.border"));
					wishlistPane.setViewportView(wishlist);
				}
			}
			{
				searchPane = new JScrollPane();
				searchPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
				searchPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
				cardPane.add(searchPane, "wishlistSearch");
				{
					searchList = new JList(wishListSearchModel);
					searchList.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (SwingUtilities.isRightMouseButton(e))
							{
								JList list = (JList) e.getSource();
								int row = list.locationToIndex(e.getPoint());
								Rectangle bound = list.getCellBounds(row, row);
								if (bound!=null && bound.contains(e.getPoint()))
								{
									list.setSelectedIndex(row);
									JPopupMenu menu = new JPopupMenu();
									
									JMenuItem searchListTransfer = new JMenuItem("Aggiungi a una Lista");
									searchListTransfer.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/add-property-xxl.png")));
									searchListTransfer.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e)
										{
											AnimeIndex.animeDialog = new AddAnimeDialog();
											AnimeIndex.animeDialog.setLocationRelativeTo(AnimeIndex.mainPanel);
											AnimeIndex.animeDialog.doSearch((String)list.getSelectedValue());
											AnimeIndex.animeDialog.setVisible(true);
										}
									});
									menu.add(searchListTransfer);
									menu.show(list, e.getX(), e.getY());
								}
							}
						}
					});
					searchList.setFont(AnimeIndex.segui.deriveFont(12f));
					searchList.addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e)
						{
							if (wishListSearchModel.contains("Nessun Anime Corrispondente"))
							{
								searchList.setEnabled(false);
								btnDeleteAnime.setEnabled(false);
							}
							else
								btnDeleteAnime.setEnabled(true);
						}
					});
					searchPane.setBorder(UIManager.getBorder("CheckBox.border"));
					searchPane.setViewportView(searchList);
				}
			}
			{
				droplistPane = new JScrollPane();
				droplistPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
				droplistPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
				cardPane.add(droplistPane, "droplist");
				{
					droplist = new JList(WishlistDialog.dropListModel);
					droplist.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (SwingUtilities.isRightMouseButton(e))
							{
								JList list = (JList) e.getSource();
								int row = list.locationToIndex(e.getPoint());
								Rectangle bound = list.getCellBounds(row, row);
								if (bound!=null && bound.contains(e.getPoint()))
								{
									list.setSelectedIndex(row);
									JPopupMenu menu = new JPopupMenu();
									
									JMenuItem dropListTransfer = new JMenuItem("Aggiungi a una Lista");
									dropListTransfer.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/add-property-xxl.png")));
									dropListTransfer.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e)
										{
											AnimeIndex.animeDialog = new AddAnimeDialog();
											AnimeIndex.animeDialog.setLocationRelativeTo(AnimeIndex.mainPanel);
											AnimeIndex.animeDialog.doSearch((String)list.getSelectedValue());
											AnimeIndex.animeDialog.setVisible(true);
										}
									});
									menu.add(dropListTransfer);
									menu.show(list, e.getX(), e.getY());
								}
							}
						}
					});
					droplist.addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent e)
						{
							if (WishlistDialog.dropListModel.contains("Nessun Anime Corrispondente"))
							{
								droplist.setEnabled(false);
								btnDeleteAnime.setEnabled(false);
								btnID.setEnabled(false);
							}
							else
							{
								btnDeleteAnime.setEnabled(true);
								btnID.setEnabled(true);
							}
						}
					});
					droplist.setFont(AnimeIndex.segui.deriveFont(12f));
					droplist.setBorder(UIManager.getBorder("CheckBox.border"));
					droplistPane.setViewportView(droplist);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				verticalStrut_1 = Box.createVerticalStrut(20);
			}

			comboBox = new JComboBox();
			comboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (comboBox.getSelectedItem().equals("WISHLIST"))
					{
						searchList.clearSelection();
						droplist.clearSelection();
						searchBar.setText("");
						CardLayout cl = (CardLayout) (cardPane.getLayout());
						cl.show(cardPane, "wishlist");
					}
					else
					{
						searchList.clearSelection();
						droplist.clearSelection();
						searchBar.setText("");
						CardLayout cl = (CardLayout) (cardPane.getLayout());
						cl.show(cardPane, "droplist");
					}
				}
			});
			((JLabel)comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"WISHLIST", "DROPLIST"}));
			searchBar = new SearchBar();
			searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
			searchBar.setFont(AnimeIndex.segui.deriveFont(11f));
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(AnimeIndex.class.getResource("/image/search.png")));
			searchBar.setIcon(icon);
			searchBar.setDisabledTextColor(Color.LIGHT_GRAY);
			searchBar.setBackground(Color.BLACK);
			searchBar.setForeground(Color.LIGHT_GRAY);
			searchBar.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent documentEvent)
				{
				}

				@Override
				public void insertUpdate(DocumentEvent e)
				{
					if (comboBox.getSelectedItem().equals("WISHLIST"))
					{
						searchList.clearSelection();
						searchList.setModel(wishListSearchModel);
						btnDeleteAnime.setEnabled(false);
						String search = searchBar.getText();
						CardLayout cl = (CardLayout) (cardPane.getLayout());
						cl.show(cardPane, "wishlistSearch");
						searchInList(search, WishlistDialog.wishListModel, wishListSearchModel);
					}
					else
					{
						searchList.clearSelection();
						searchList.setModel(dropListSearchModel);
						btnDeleteAnime.setEnabled(false);
						String search = searchBar.getText();
						CardLayout cl = (CardLayout) (cardPane.getLayout());
						cl.show(cardPane, "wishlistSearch");
						searchInList(search, WishlistDialog.dropListModel, dropListSearchModel);
					}
				}

				@Override
				public void removeUpdate(DocumentEvent e)
				{
					searchList.clearSelection();
					btnDeleteAnime.setEnabled(false);
					String search = searchBar.getText();
					if (comboBox.getSelectedItem().equals("WISHLIST"))
					{
						wishlist.clearSelection();
	
						if (!search.isEmpty())
						{
							CardLayout cl = (CardLayout) (cardPane.getLayout());
							cl.show(cardPane, "wishlistSearch");
							searchInList(search, WishlistDialog.wishListModel, wishListSearchModel);
						}
						else
						{
							CardLayout cl = (CardLayout) (cardPane.getLayout());
							cl.show(cardPane, "wishlist");
						}
					}
					else
					{
						droplist.clearSelection();
						
						if (!search.isEmpty())
						{
							CardLayout cl = (CardLayout) (cardPane.getLayout());
							cl.show(cardPane, "wishlistSearch");
							searchInList(search, WishlistDialog.dropListModel, dropListSearchModel);
						}
						else
						{
							CardLayout cl = (CardLayout) (cardPane.getLayout());
							cl.show(cardPane, "droplist");
						}
					}
				}
			});
			{
				horizontalStrut = Box.createHorizontalStrut(20);
			}
			{
				horizontalStrut_1 = Box.createHorizontalStrut(20);
			}
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(90).addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addComponent(horizontalStrut, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addComponent(horizontalStrut_1, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE).addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)));
			gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(horizontalStrut, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(horizontalStrut_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))));
			panel.setLayout(gl_panel);
		}
		{
			horizontalStrut_6 = Box.createHorizontalStrut(5);
			contentPanel.add(horizontalStrut_6, BorderLayout.WEST);
		}
		{
			horizontalStrut_5 = Box.createHorizontalStrut(6);
			contentPanel.add(horizontalStrut_5, BorderLayout.EAST);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(0, 1, 1, 1, new Color(0, 0, 0)));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnDeleteAnime = new JButton("Elimina Anime");
				btnDeleteAnime.setMargin(new Insets(0, 14, 0, 14));
				btnDeleteAnime.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						SortedListModel model;
						JList list;
						SortedListModel searchModel;
						if (comboBox.getSelectedItem().equals("WISHLIST"))
						{
							model = WishlistDialog.wishListModel;
							list = wishlist;
							searchModel = wishListSearchModel;
						}
						else
						{
							model = WishlistDialog.dropListModel;
							list = droplist;
							searchModel = dropListSearchModel;
						}
						
						if (searchBar.getText().isEmpty())
						{
							int index = list.getSelectedIndex();
							String name = (String) model.getElementAt(index);
							model.removeElementAt(index);
							index -= 1;
							list.clearSelection();
							list.setSelectedIndex(index);
							if (AnimeIndex.wishlistMap.containsKey(name))
								AnimeIndex.wishlistMap.remove(name);
							else if (AnimeIndex.wishlistMALMap.containsKey(name))
								AnimeIndex.wishlistMALMap.remove(name);
							else if (AnimeIndex.droppedMap.containsKey(name))
								AnimeIndex.droppedMap.remove(name);
							else if (AnimeIndex.droppedMALMap.containsKey(name))
								AnimeIndex.droppedMALMap.remove(name);
							
							if (model.isEmpty())
							{
								list.setEnabled(false);
								btnDeleteAnime.setEnabled(false);
							}
						}
						else
						{
							String name = (String) searchList.getSelectedValue();
							model.removeElement(name);
							if (AnimeIndex.wishlistMap.containsKey(name))
								AnimeIndex.wishlistMap.remove(name);
							else if (AnimeIndex.wishlistMALMap.containsKey(name))
								AnimeIndex.wishlistMALMap.remove(name);
							else if (AnimeIndex.droppedMap.containsKey(name))
								AnimeIndex.droppedMap.remove(name);
							else if (AnimeIndex.droppedMALMap.containsKey(name))
								AnimeIndex.droppedMALMap.remove(name);
							searchInList(searchBar.getText(), model, searchModel);
							if (searchModel.isEmpty())
							{
								searchModel.addElement("Nessun Anime Corrispondente");
								searchList.setEnabled(false);
								btnDeleteAnime.setEnabled(false);
							}
						}

					}
				});
				btnDeleteAnime.setEnabled(false);
			}
			{
				btnAggiungiAnime = new JButton("Aggiungi Anime");
				btnAggiungiAnime.setMargin(new Insets(0, 14, 0, 14));
				btnAggiungiAnime.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						searchAnime();
					}
				});
			}
			{
				btnID = new JButton("Visualizza");
				btnID.setEnabled(false);
				btnID.setMargin(new Insets(0, 14, 0, 14));
				btnID.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						animeName = null;
						if (searchBar.getText().isEmpty())
							if(comboBox.getSelectedItem().equals("WISHLIST"))
								animeName = (String) wishlist.getSelectedValue();
							else
								animeName = (String) droplist.getSelectedValue();
						else
							animeName = (String) searchList.getSelectedValue();
						
						JPopupMenu menu = new JPopupMenu();
						JMenuItem aniList = null;
						JMenuItem mal = new JMenuItem("MyAnimeList                   ");
						JMenuItem animeClick = new JMenuItem("AnimeClick");
						JMenuItem hummingbird = new JMenuItem("Hummingbird");
						JMenuItem aniDB = new JMenuItem("AniDB");
						mal.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/MAL.png")));
						animeClick.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/AC.png")));
						hummingbird.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/hummingbird.me.png")));
						aniDB.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/anidb_icon.png")));
						int y;
						idLink = -1;
						try{
							if (AnimeIndex.wishlistMap.containsKey(animeName))
							{
								idLink = AnimeIndex.wishlistMap.get(animeName);
							}
							else if (AnimeIndex.droppedMap.containsKey(animeName))
							{
								idLink = AnimeIndex.droppedMap.get(animeName);
							}
							else if (AnimeIndex.wishlistMALMap.containsKey(animeName))
							{
								idLink = AnimeIndex.wishlistMALMap.get(animeName);
							}
							else if (AnimeIndex.droppedMALMap.containsKey(animeName))
							{
								idLink = AnimeIndex.droppedMALMap.get(animeName);
							}
						}catch(NullPointerException e1)
						{}
						if (idLink != -1)
						{
							aniList = new JMenuItem("AniList");
							aniList.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/anilist.png")));
							menu.add(aniList);
							y = 88;
						}
						else
							y = 67;
						menu.add(mal);
						menu.add(animeClick);
						menu.add(hummingbird);
						menu.add(aniDB);
						menu.show(btnID, btnID.getX()-6,-btnID.getHeight()-y);
						try{
							aniList.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e)
								{
									String link = "https://anilist.co/anime/" + idLink;
									try
									{
										URI uriLink = new URI(link);
										Desktop.getDesktop().browse(uriLink);
									}
									catch (URISyntaxException a)
									{
										MAMUtil.writeLog(a);
									}
									catch (IOException a)
									{
										MAMUtil.writeLog(a);
									}	
								}
							});
						}catch(NullPointerException e1){}
						
						mal.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e)
							{
								String link = "http://myanimelist.net/anime.php?q="+animeName.replace(" ", "+").replace("\"", "").replace("%", "").replace("\\", "").replace("<", "").replace(">", "").replace("^", "").replace("|", "").replace("{", "").replace("}", "");
								try
								{
									URI uriLink = new URI(link);
									Desktop.getDesktop().browse(uriLink);
								}
								catch (URISyntaxException a)
								{
									MAMUtil.writeLog(a);
								}
								catch (IOException a)
								{
									MAMUtil.writeLog(a);
								}
							}
						});
						
						animeClick.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e)
							{
								String link = "http://www.animeclick.it/cerca?tipo=opera&name="+animeName.replace(" ", "+").replace("\"", "").replace("%", "").replace("\\", "").replace("<", "").replace(">", "").replace("^", "").replace("|", "").replace("{", "").replace("}", "");
								try
								{
									URI uriLink = new URI(link);
									Desktop.getDesktop().browse(uriLink);
								}
								catch (URISyntaxException a)
								{
									MAMUtil.writeLog(a);
								}
								catch (IOException a)
								{
									MAMUtil.writeLog(a);
								}
							}
						});
						
						hummingbird.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e)
							{
								String link = "https://hummingbird.me/search?query="+animeName.replace(" ", "+").replace("\"", "").replace("%", "").replace("\\", "").replace("<", "").replace(">", "").replace("^", "").replace("|", "").replace("{", "").replace("}", "")+"&scope=anime";
								try
								{
									URI uriLink = new URI(link);
									Desktop.getDesktop().browse(uriLink);
								}
								catch (URISyntaxException a)
								{
									MAMUtil.writeLog(a);
								}
								catch (IOException a)
								{
									MAMUtil.writeLog(a);
								}
							}
						});
						
						aniDB.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e)
							{
								String link = "http://anidb.net/perl-bin/animedb.pl?adb.search="+animeName.replace(" ", "+").replace("\"", "").replace("%", "").replace("\\", "").replace("<", "").replace(">", "").replace("^", "").replace("|", "").replace("{", "").replace("}", "")+"&show=animelist&do.search=search";
								try
								{
									URI uriLink = new URI(link);
									Desktop.getDesktop().browse(uriLink);
								}
								catch (URISyntaxException a)
								{
									MAMUtil.writeLog(a);
								}
								catch (IOException a)
								{
									MAMUtil.writeLog(a);
								}
							}
						});
					}
				});
			}
			{
				horizontalStrut_2 = Box.createHorizontalStrut(20);
			}
			{
				horizontalStrut_3 = Box.createHorizontalStrut(20);
			}
			{
				horizontalStrut_4 = Box.createHorizontalStrut(20);
			}
			{
				verticalStrut = Box.createVerticalStrut(20);
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING).addGroup(gl_buttonPane.createSequentialGroup().addComponent(horizontalStrut_2, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE).addComponent(btnID, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)).addGroup(gl_buttonPane.createSequentialGroup().addComponent(horizontalStrut_3, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE).addComponent(btnDeleteAnime, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)).addGroup(gl_buttonPane.createSequentialGroup().addComponent(horizontalStrut_4, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE).addComponent(btnAggiungiAnime, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)).addGroup(gl_buttonPane.createSequentialGroup().addGap(90).addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)));
			gl_buttonPane.setVerticalGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING).addGroup(gl_buttonPane.createSequentialGroup().addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING).addComponent(horizontalStrut_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addComponent(btnID, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)).addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING).addComponent(horizontalStrut_3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addComponent(btnDeleteAnime, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)).addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING).addComponent(horizontalStrut_4, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addComponent(btnAggiungiAnime, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)).addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)));
			buttonPane.setLayout(gl_buttonPane);
		}
	}

	private void searchInList(String searchedVal, SortedListModel modelToSearch, SortedListModel searchModel)
	{
		Object[] mainArray = modelToSearch.toArray();
		searchModel.clear();
		for (int i = 0; i < mainArray.length; i++)
		{
			String value = (String) mainArray[i];
			value = value.toLowerCase();
			searchedVal = searchedVal.toLowerCase();
			if (value.contains(searchedVal))
				searchModel.addElement((String) mainArray[i]);
		}
		searchList.setEnabled(true);
		if (searchModel.isEmpty())
		{
			searchModel.addElement("Nessun Anime Corrispondente");
			searchList.setEnabled(false);
			btnDeleteAnime.setEnabled(false);
		}
	}
	
	private void searchAnime()
	{
		String listName = "";
		SortedListModel model;
		TreeMap<String,Integer> map;
		JList list;
		SortedListModel searchModel;
		if (comboBox.getSelectedItem().equals("WISHLIST"))
		{
			listName = "Wishlist";
			model = WishlistDialog.wishListModel;
			map = AnimeIndex.wishlistMap;
			list = wishlist;
			searchModel = wishListSearchModel;
		}
		else
		{
			listName = "Droplist";
			model = WishlistDialog.dropListModel;
			map = AnimeIndex.droppedMap;
			list = droplist;
			searchModel = dropListSearchModel;
		}
		String animeName = null;
		int id = -1;
		String name = null;
		try{
			name = JOptionPane.showInputDialog(AnimeIndex.wishlistDialog, "Nome Anime :", "Aggiungi alla " + listName, JOptionPane.QUESTION_MESSAGE).trim();
		}catch(NullPointerException e)
		{}
		try
		{
			ConnectionManager.ConnectAndGetToken();
		}
		catch (ConnectException | UnknownHostException e1)
		{
			e1.printStackTrace();
			MAMUtil.writeLog(e1);
		}

		if (name != null && !name.isEmpty())
		{
			HashMap<String, Integer> animeMap = ConnectionManager.AnimeSearch(name);
			if (!animeMap.isEmpty() && animeMap.size() > 1)
			{
				String[] animeNames = animeMap.keySet().toArray(new String[0]);
				animeName = (String) JOptionPane.showInputDialog(WishlistDialog.this, "Scegli l'anime da aggiungere :", "Conflitto trovato", JOptionPane.QUESTION_MESSAGE, null, animeNames, animeNames[0]);

				if (animeName != null)
				{
					name = animeName;
					id = animeMap.get(name);
					if (model.contains("Nessun Anime Corrispondente"))
						model.removeElement("Nessun Anime Corrispondente");
					model.addElement(name);
					map.put(name, id);
					list.setEnabled(true);
					searchList.setEnabled(true);
					list.setSelectedValue(name, true);
				}
				else
					animeName = "annulla";
			}
			else if(!animeMap.isEmpty() && animeMap.size() == 1)
			{
				name = animeName = animeMap.keySet().toArray(new String[0])[0];
				id = animeMap.get(name);
				if (model.contains("Nessun Anime Corrispondente"))
					model.removeElement("Nessun Anime Corrispondente");
				model.addElement(name);
				map.put(name, id);
				list.setEnabled(true);
				searchList.setEnabled(true);
				list.setSelectedValue(name, true);
			}
		}
		if (name != null && !name.isEmpty() && animeName == null)
		{
			int choice = JOptionPane.showConfirmDialog(WishlistDialog.this,"L'Anime cercato non è stato trovato.\n\rRipetere la ricerca?" , "Riceca fallita", JOptionPane.YES_NO_OPTION);
			if(choice==0)
			{
				searchAnime();
			}
			else
			{
				if (model.contains("Nessun Anime Corrispondente"))
					model.removeElement("Nessun Anime Corrispondente");
				model.addElement(name);
				map.put(name, id);
				list.setEnabled(true);
				searchList.setEnabled(true);
				list.setSelectedValue(name, true);
			}
		}
		if (!searchBar.getText().isEmpty() && name != null && !name.isEmpty())
		{
			searchInList(searchBar.getText(), model, searchModel);
			searchList.setSelectedValue(name, true);
		}
	}
}
