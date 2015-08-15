package util.window;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;

public class AddImageDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JPanel cardContainer;
	private JList completedList;
	private JList airingList;
	private JList ovaList;
	private JList filmList;
	private JList completedToSeeList;
	private JButton addButton;
	private JComboBox animeTypeComboBox;
	private DefaultListModel completedAdd = new DefaultListModel();
	private DefaultListModel airingAdd = new DefaultListModel();
	private DefaultListModel filmAdd = new DefaultListModel();
	private DefaultListModel ovaAdd = new DefaultListModel();
	private DefaultListModel completedToSeeAdd = new DefaultListModel();
	private final static String IMAGE_PATH = FileManager.getImageFolderPath();
	public static boolean shouldAdd;

	/**
	 * Create the dialog.
	 */
	public AddImageDialog()
	{
		super(AnimeIndex.frame,true);
		setTitle("Aggiungi Immagine");	
		setResizable(false);
		setType(Type.POPUP);
		setModal(true);
		setBounds(100, 100, 450, 300);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			cardContainer = new JPanel();
			cardContainer.setPreferredSize(new Dimension(159, 235));
			cardContainer.setMaximumSize(new Dimension(159, 135));
			contentPanel.add(cardContainer);
			cardContainer.setLayout(new CardLayout(0, 0));
			{
				JPanel completedAnime = new JPanel();
				cardContainer.add(completedAnime, "Anime Completati");
				completedAnime.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane completedScroll = new JScrollPane();
					completedScroll.setMaximumSize(new Dimension(138, 233));
					completedAnime.add(completedScroll, BorderLayout.CENTER);
					{
						Object[] array = AnimeIndex.completedModel.toArray();
						for (int i = 0; i < array.length; i++)
						{
							String name = (String) array[i];
							String data = AnimeIndex.completedMap.get(name).getImageName();
							if (data == null || data.equalsIgnoreCase("null") )
							{
								completedAdd.addElement(name);
							}
						}
						completedList = new JList(completedAdd);
						completedList.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								addButton.setEnabled(true);
								
							}
						});
						completedList.setSize(new Dimension(138, 233));
						completedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						completedList.setPreferredSize(new Dimension(138, 233));
						completedList.setMinimumSize(new Dimension(138, 233));
						completedList.setMaximumSize(new Dimension(157, 233));
						completedScroll.setViewportView(completedList);
					}
				}
			}
			{
				JPanel airingAnime = new JPanel();
				cardContainer.add(airingAnime, "Anime in Corso");
				airingAnime.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane airingPane = new JScrollPane();
					airingPane.setMaximumSize(new Dimension(138, 233));
					airingAnime.add(airingPane, BorderLayout.CENTER);
					{
						Object[] array = AnimeIndex.airingModel.toArray();
						for (int i = 0; i < array.length; i++)
						{
							String name = (String) array[i];
							String data = AnimeIndex.airingMap.get(name).getImageName();
							if (data.equalsIgnoreCase("null") || data == null)
							{
								airingAdd.addElement(name);
							}
						}
						airingList = new JList(airingAdd);
						airingList.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								addButton.setEnabled(true);
							}
						});
						airingList.setSize(new Dimension(138, 233));
						airingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						airingList.setPreferredSize(new Dimension(138, 233));
						airingList.setMinimumSize(new Dimension(138, 233));
						airingList.setMaximumSize(new Dimension(157, 233));
						airingPane.setViewportView(airingList);
					}
				}
			}
			{
				JPanel ova = new JPanel();
				cardContainer.add(ova, "OAV");
				ova.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane ovaPane = new JScrollPane();
					ovaPane.setMaximumSize(new Dimension(138, 233));
					ova.add(ovaPane, BorderLayout.CENTER);
					{
						Object[] array =AnimeIndex.ovaModel.toArray();
						for (int i = 0; i < array.length; i++)
						{
							String name = (String) array[i];
							String data = AnimeIndex.ovaMap.get(name).getImageName();
							if (data.equalsIgnoreCase("null") || data == null)
							{
								ovaAdd.addElement(name);
							}
						}
						ovaList = new JList(ovaAdd);
						ovaList.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								addButton.setEnabled(true);
							}
						});
						ovaList.setSize(new Dimension(138, 233));
						ovaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						ovaList.setPreferredSize(new Dimension(138, 233));
						ovaList.setMinimumSize(new Dimension(138, 233));
						ovaList.setMaximumSize(new Dimension(157, 233));
						ovaPane.setViewportView(ovaList);
					}
				}
			}
			{
				JPanel film = new JPanel();
				cardContainer.add(film, "Film");
				film.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane filmPane = new JScrollPane();
					filmPane.setMaximumSize(new Dimension(138, 233));
					film.add(filmPane, BorderLayout.CENTER);
					{
						Object[] array = AnimeIndex.filmModel.toArray();
						for (int i = 0; i < array.length; i++)
						{
							String name = (String) array[i];
							String data = AnimeIndex.filmMap.get(name).getImageName();
							if (data.equalsIgnoreCase("null") || data == null)
							{
								filmAdd.addElement(name);
							}
						}
						filmList = new JList(filmAdd);
						filmList.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								addButton.setEnabled(true);
								}
						});
						filmList.setSize(new Dimension(138, 233));
						filmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						filmList.setPreferredSize(new Dimension(138, 233));
						filmList.setMinimumSize(new Dimension(138, 233));
						filmList.setMaximumSize(new Dimension(157, 233));
						filmPane.setViewportView(filmList);
					}
				}
			}
			{
				JPanel completedToSee = new JPanel();
				cardContainer.add(completedToSee, "Completi Da Vedere");
				completedToSee.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane completedToSeePane = new JScrollPane();
					completedToSeePane.setMaximumSize(new Dimension(138, 233));
					completedToSee.add(completedToSeePane, BorderLayout.CENTER);
					{
						Object[] array = AnimeIndex.completedToSeeModel.toArray();
						for (int i = 0; i < array.length; i++)
						{
							String name = (String) array[i];
							String data = AnimeIndex.completedToSeeMap.get(name).getImageName();
							if (data.equalsIgnoreCase("null") || data == null)
							{
								completedToSeeAdd.addElement(name);
							}
						}
						completedToSeeList = new JList(completedToSeeAdd);
						completedToSeeList.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								addButton.setEnabled(true);
							}
						});
						completedToSeeList.setSize(new Dimension(138, 233));
						completedToSeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						completedToSeeList.setPreferredSize(new Dimension(138, 233));
						completedToSeeList.setMinimumSize(new Dimension(138, 233));
						completedToSeeList.setMaximumSize(new Dimension(157, 233));
						completedToSeePane.setViewportView(completedToSeeList);
					}
				}
			}
		}
		{
			animeTypeComboBox = new JComboBox();
			animeTypeComboBox.setMaximumSize(new Dimension(138, 20));
			animeTypeComboBox.setMaximumRowCount(2139120439);
			animeTypeComboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					CardLayout cl = (CardLayout)(cardContainer.getLayout());
			        cl.show(cardContainer, (String)evt.getItem());
			        { 	
			        	ovaList.clearSelection();
			        	filmList.clearSelection();
			        	airingList.clearSelection();
			        	completedList.clearSelection();
			        	completedToSeeList.clearSelection();
			        }
			        
				}
			});
			animeTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere"}));

			contentPanel.add(animeTypeComboBox, BorderLayout.NORTH);
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(150);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				addButton = new JButton("Aggiungi");
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String selectedList = (String) animeTypeComboBox.getSelectedItem();
						JList list = getList();
						String name = (String) list.getSelectedValue();
						try {
							ConnectionManager.ConnectAndGetToken();
						} 
						catch (UnknownHostException e2) {
							JOptionPane.showMessageDialog(contentPanel, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
						}
						catch (ConnectException e1) {
							JOptionPane.showMessageDialog(contentPanel, "Errore di connessione", "Errore!", JOptionPane.ERROR_MESSAGE);
						};
						HashMap<String,Integer> map = ConnectionManager.AnimeSearch(name);
						DefaultListModel model = new DefaultListModel();
						Object[] animeArray = map.keySet().toArray();
						
						for (int i = 0; i < animeArray.length; i++)
							model.addElement(animeArray[i]);
						AnimeImageSelection selectionDialog = new AnimeImageSelection(model);
						selectionDialog.setLocationRelativeTo(contentPanel);
						selectionDialog.setVisible(true);
						if (shouldAdd)
						{
						String anime = selectionDialog.getSelectedAnime();
						int id = map.get(anime);
						String dataAni = ConnectionManager.parseAnimeData(id);
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
						
						TreeMap<String, AnimeData> animeMap = null;
						if (selectedList.equalsIgnoreCase("Anime Completati"))
						{
							animeMap = AnimeIndex.completedMap;
							FileManager.saveImage(imageLink, name, "Completed");
						}
						else if (selectedList.equalsIgnoreCase("Anime in Corso"))
						{
							animeMap = AnimeIndex.airingMap;
							FileManager.saveImage(imageLink, name, "Airing");
						}
						else if (selectedList.equalsIgnoreCase("OAV"))
						{
							animeMap = AnimeIndex.ovaMap;
							FileManager.saveImage(imageLink, name, "Ova");
						}
						else if (selectedList.equalsIgnoreCase("Film"))
						{
							animeMap = AnimeIndex.filmMap;
							FileManager.saveImage(imageLink, name,"Film");
						}
						else if (selectedList.equalsIgnoreCase("Completi Da Vedere"))
						{
							animeMap = AnimeIndex.completedToSeeMap;
							FileManager.saveImage(imageLink, name,"Completed to See");
						}
						AnimeData oldData = animeMap.get(name);
						AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), oldData.getTotalEpisode(), oldData.getFansub(), 
								oldData.getNote(), imageName + ".png", "Concluso", oldData.getId(),
								oldData.getLinkName(), oldData.getLink(), oldData.getAnimeType(), oldData.getReleaseDate(), 
								oldData.getFinishDate(), oldData.getDurationEp(), oldData.getBd());
						animeMap.put(name, newData);
						AnimeIndex.animeInformation.setImage(IMAGE_PATH + imageName + ".png");
						AnimeIndex.animeTypeComboBox.setSelectedItem(selectedList);
						AnimeIndex.completedList.setSelectedValue(name, true);
						}
						
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				addButton.setEnabled(false);
				buttonPane.add(addButton);
				getRootPane().setDefaultButton(addButton);
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JList getList()
	{
		String selectedList = (String) animeTypeComboBox.getSelectedItem();
		JList list = null;
		
		if (selectedList.equalsIgnoreCase("Anime Completati"))
		{
			list = completedList;
		}
		else if (selectedList.equalsIgnoreCase("Anime in Corso"))
		{
			list = airingList;
		}
		else if (selectedList.equalsIgnoreCase("OAV"))
		{
			list = ovaList;
		}
		else if (selectedList.equalsIgnoreCase("Film"))
		{
			list = filmList;
		}
		else if (selectedList.equalsIgnoreCase("Completi Da Vedere"))
		{
			list = completedToSeeList;
		}
		return list;
	}
}
