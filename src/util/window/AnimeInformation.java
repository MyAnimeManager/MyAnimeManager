package util.window;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import main.AnimeIndex;
import util.AnimeData;
import util.PatternFilter;

import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class AnimeInformation extends JPanel
{
	public JTextField totalEpisodeText;
	public JTextField currentEpisodeField;
	public JButton minusButton;
	public JButton plusButton;
	public JLabel lblAnimeName;
	public static JComboBox fansubComboBox;
	private JButton finishedButton;
	public JButton btnOpen;
	public JLabel lblEpisode;
	private JButton btnSave;
	public JTextArea noteTextArea;
	private JScrollPane scrollPane;
	private JLabel animeImage;
	private JLabel lblExitDay;
	public JComboBox exitDaycomboBox;
	private JLabel lblNote;
	private JLabel lblNewLabel;
	private Component rigidArea;
	private Component rigidArea_1;
	public JButton fansubButton;
	private JButton setLinkButton;
	private SetLinkDialog setLink;
	public String link;

	/**
	 * Create the panel.
	 * 
	 * 
	 */
	public AnimeInformation()
	{
		setSize(new Dimension(625, 441));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 50, 0, 94, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{60, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 12;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		lblAnimeName = new JLabel("Anime name");
		scrollPane.setViewportView(lblAnimeName);
		lblAnimeName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		//label immagine
		BufferedImage image = null;
		try {
			image = ImageIO.read( ClassLoader.getSystemResource("image/default.png" ));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
//		lblEpisode = new JLabel();
//		if (AnimeIndex.appProp.getProperty("Show_episode_to_see").equals("true"))
//		{
//			lblEpisode.setText("Episodio da vedere :\r\n");
//		}
//		else
//		{
//			lblEpisode.setText("Ultimo episodio :\r\n");
//		}
//		animeImage = new JLabel(new ImageIcon(image));
//		animeImage.setBorder(new LineBorder(new Color(40, 40, 40), 3, true));
//		GridBagConstraints gbc_animeImage = new GridBagConstraints();
//		gbc_animeImage.gridheight = 8;
//		gbc_animeImage.insets = new Insets(0, 0, 5, 5);
//		gbc_animeImage.gridx = 1;
//		gbc_animeImage.gridy = 1;
//		add(animeImage, gbc_animeImage);
//		GridBagConstraints gbc_lblEpisode = new GridBagConstraints();
//		gbc_lblEpisode.anchor = GridBagConstraints.WEST;
//		gbc_lblEpisode.insets = new Insets(0, 0, 5, 5);
//		gbc_lblEpisode.gridx = 8;
//		gbc_lblEpisode.gridy = 2;
//		add(lblEpisode, gbc_lblEpisode);
		
		plusButton = new JButton("+");		
		plusButton.setSize(new Dimension(30, 30));
		plusButton.setMaximumSize(new Dimension(30, 30));
		plusButton.setMinimumSize(new Dimension(30, 30));
		plusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((currentEpisodeField.getText()) != null && (currentEpisodeField.getText().isEmpty()))
				{
					currentEpisodeField.setText("0");
					minusButton.setEnabled(false);
				}
				else 
				{
					int num = Integer.parseInt(currentEpisodeField.getText());
					num++;
					if (num == 1)
					{
						minusButton.setEnabled(true);
					}
					
					if ( (totalEpisodeText.getText()) != null && !(totalEpisodeText.getText().isEmpty()))
					{
						int maxnum = Integer.parseInt(totalEpisodeText.getText());
						if (num == maxnum)
						{
							plusButton.setEnabled(false);
							String listName = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();				
							if (!listName.equalsIgnoreCase("anime completati"))
							finishedButton.setEnabled(true);
						}
					}
					currentEpisodeField.setText(Integer.toString(num));
				}
			}
		});
		
		lblEpisode = new JLabel();
		if (AnimeIndex.appProp.getProperty("Show_episode_to_see").equals("true"))
		{
			lblEpisode.setText("Episodio da vedere :\r\n");
		}
		else
		{
			lblEpisode.setText("Ultimo episodio :\r\n");
		}
		
		rigidArea = Box.createRigidArea(new Dimension(5, 20));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.gridheight = 12;
		gbc_rigidArea.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);
		animeImage = new JLabel(new ImageIcon(image));
		animeImage.setBorder(new LineBorder(new Color(40, 40, 40), 3, true));
		GridBagConstraints gbc_animeImage = new GridBagConstraints();
		gbc_animeImage.gridheight = 9;
		gbc_animeImage.insets = new Insets(0, 0, 5, 5);
		gbc_animeImage.gridx = 1;
		gbc_animeImage.gridy = 2;
		add(animeImage, gbc_animeImage);
		GridBagConstraints gbc_lblEpisode = new GridBagConstraints();
		gbc_lblEpisode.anchor = GridBagConstraints.WEST;
		gbc_lblEpisode.insets = new Insets(0, 0, 5, 5);
		gbc_lblEpisode.gridx = 8;
		gbc_lblEpisode.gridy = 3;
		add(lblEpisode, gbc_lblEpisode);
		
		
		//secondo numero = massimo numero a cui può arrivare
		
		currentEpisodeField = new JTextField();
		currentEpisodeField.setPreferredSize(new Dimension(43, 23));
		currentEpisodeField.setMinimumSize(new Dimension(43, 23));
		GridBagConstraints gbc_currentEpisodeField = new GridBagConstraints();
		gbc_currentEpisodeField.anchor = GridBagConstraints.WEST;
		gbc_currentEpisodeField.insets = new Insets(0, 0, 5, 5);
		gbc_currentEpisodeField.gridx = 9;
		gbc_currentEpisodeField.gridy = 3;
		add(currentEpisodeField, gbc_currentEpisodeField);
		currentEpisodeField.setColumns(3);
		((AbstractDocument)currentEpisodeField.getDocument()).setDocumentFilter( new PatternFilter("\\d{0,4}"));
		GridBagConstraints gbc_plusButton = new GridBagConstraints();
		gbc_plusButton.insets = new Insets(0, 0, 5, 5);
		gbc_plusButton.gridx = 10;
		gbc_plusButton.gridy = 3;
		add(plusButton, gbc_plusButton);
		plusButton.setPreferredSize(new Dimension(30,30));
		
		minusButton = new JButton("-");
		minusButton.setMinimumSize(new Dimension(30, 30));
		minusButton.setMaximumSize(new Dimension(30, 30));
		minusButton.setSize(new Dimension(30, 30));
		minusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = Integer.parseInt(currentEpisodeField.getText());
				if (num != 0)
				{
				num--;
				currentEpisodeField.setText(Integer.toString(num));
					if (num == 0)
					{
						minusButton.setEnabled(false);
					}
					
					if ( (totalEpisodeText.getText()) != null && !(totalEpisodeText.getText().isEmpty()))
					{
						int maxnum = Integer.parseInt(totalEpisodeText.getText());
						if (num < maxnum)
							plusButton.setEnabled(true);					
					}
				}
			}
		});
		GridBagConstraints gbc_minusButton = new GridBagConstraints();
		gbc_minusButton.anchor = GridBagConstraints.EAST;
		gbc_minusButton.insets = new Insets(0, 0, 5, 5);
		gbc_minusButton.gridx = 11;
		gbc_minusButton.gridy = 3;
		add(minusButton, gbc_minusButton);
		minusButton.setPreferredSize(new Dimension(30,30));
		
		rigidArea_1 = Box.createRigidArea(new Dimension(5, 20));
		GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
		gbc_rigidArea_1.gridheight = 11;
		gbc_rigidArea_1.gridx = 12;
		gbc_rigidArea_1.gridy = 1;
		add(rigidArea_1, gbc_rigidArea_1);
		
		
		
		// total episode label e text field
		JLabel lblTotalEpisode = new JLabel("Episodi Totali :");
		GridBagConstraints gbc_lblTotalEpisode = new GridBagConstraints();
		gbc_lblTotalEpisode.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalEpisode.gridx = 8;
		gbc_lblTotalEpisode.gridy = 4;
		add(lblTotalEpisode, gbc_lblTotalEpisode);
		
		totalEpisodeText = new JTextField();
		totalEpisodeText.setMinimumSize(new Dimension(43, 23));
		totalEpisodeText.setPreferredSize(new Dimension(43, 23));
		GridBagConstraints gbc_totalEpisodeText = new GridBagConstraints();
		gbc_totalEpisodeText.anchor = GridBagConstraints.WEST;
		gbc_totalEpisodeText.insets = new Insets(0, 0, 5, 5);
		gbc_totalEpisodeText.gridx = 9;
		gbc_totalEpisodeText.gridy = 4;
		add(totalEpisodeText, gbc_totalEpisodeText);
		totalEpisodeText.setColumns(3);
		((AbstractDocument)totalEpisodeText.getDocument()).setDocumentFilter( new PatternFilter("\\d{0,4}"));
		
		JLabel lblFansub = new JLabel("Fansub:");
		GridBagConstraints gbc_lblFansub = new GridBagConstraints();
		gbc_lblFansub.insets = new Insets(0, 0, 5, 5);
		gbc_lblFansub.gridx = 8;
		gbc_lblFansub.gridy = 5;
		add(lblFansub, gbc_lblFansub);
		
		fansubComboBox = new JComboBox();
		fansubComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String link = AnimeIndex.fansubMap.get((String)fansubComboBox.getSelectedItem());
				if (link != null && !link.isEmpty())
				{
					fansubButton.setEnabled(true);
				}

				else
					fansubButton.setEnabled(false);
				
			}
		});
		fansubComboBox.setModel(new DefaultComboBoxModel(AnimeIndex.getFansubList()));
		GridBagConstraints gbc_fansubComboBox = new GridBagConstraints();
		gbc_fansubComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_fansubComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_fansubComboBox.gridx = 9;
		gbc_fansubComboBox.gridy = 5;
		add(fansubComboBox, gbc_fansubComboBox);
		String[] dayWeek = {"-----","Lunedì","Martedì","Mercoledì","Giovedì","Venerdì","Sabato","Domenica", "Concluso"};
		
		fansubButton = new JButton("Apri Fansub");
		fansubButton.setEnabled(false);
		fansubButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String link = AnimeIndex.fansubMap.get((String)fansubComboBox.getSelectedItem());
				if (link != null && !link.isEmpty())
				{
					try {
						URI uriLink = new URI(link);
						Desktop.getDesktop().browse(uriLink);
					} catch (URISyntaxException e) {
						JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
							    "Link non valido",
							    "Errore",
							    JOptionPane.ERROR_MESSAGE);
						fansubButton.setEnabled(false);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
							    "Link non valido",
							    "Errore",
							    JOptionPane.ERROR_MESSAGE);
						fansubButton.setEnabled(false);
					}
				}
				else
					fansubButton.setEnabled(false);
			}
		});
		GridBagConstraints gbc_fansubButton = new GridBagConstraints();
		gbc_fansubButton.gridwidth = 2;
		gbc_fansubButton.insets = new Insets(0, 0, 5, 5);
		gbc_fansubButton.gridx = 10;
		gbc_fansubButton.gridy = 5;
		add(fansubButton, gbc_fansubButton);
		
		JLabel lblFansubLink = new JLabel("Link :");
		GridBagConstraints gbc_lblFansubLink = new GridBagConstraints();
		gbc_lblFansubLink.insets = new Insets(0, 0, 5, 5);
		gbc_lblFansubLink.gridx = 8;
		gbc_lblFansubLink.gridy = 6;
		add(lblFansubLink, gbc_lblFansubLink);
		
		btnOpen = new JButton("Apri");
		btnOpen.setEnabled(false);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String link = getLink();
				if (!link.isEmpty())
				{
					try {
						URI uriLink = new URI(link);
						Desktop.getDesktop().browse(uriLink);
					} catch (URISyntaxException e) {
						JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
							    "Link non valido",
							    "Errore",
							    JOptionPane.ERROR_MESSAGE);
						btnOpen.setEnabled(false);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(AnimeIndex.animeDialog,
							    "Link non valido",
							    "Errore",
							    JOptionPane.ERROR_MESSAGE);
						btnOpen.setEnabled(false);
					}
				}
				else
					btnOpen.setEnabled(false);
			}
				
		});
		
		setLinkButton = new JButton("Imposta Link");
		setLinkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLink = new SetLinkDialog();
				setLink.setLocationRelativeTo(AnimeIndex.mainFrame);
				setLink.setVisible(true);
				
			}
		});
		GridBagConstraints gbc_btnScegliLink = new GridBagConstraints();
		gbc_btnScegliLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnScegliLink.insets = new Insets(0, 0, 5, 5);
		gbc_btnScegliLink.gridx = 9;
		gbc_btnScegliLink.gridy = 6;
		add(setLinkButton, gbc_btnScegliLink);
		GridBagConstraints gbc_btnOpen = new GridBagConstraints();
		gbc_btnOpen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOpen.gridwidth = 2;
		gbc_btnOpen.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpen.gridx = 10;
		gbc_btnOpen.gridy = 6;
		add(btnOpen, gbc_btnOpen);
		
		lblExitDay = new JLabel("Giorno di Uscita");
		GridBagConstraints gbc_lblExitDay = new GridBagConstraints();
		gbc_lblExitDay.insets = new Insets(0, 0, 5, 5);
		gbc_lblExitDay.gridx = 8;
		gbc_lblExitDay.gridy = 7;
		add(lblExitDay, gbc_lblExitDay);
		
		exitDaycomboBox = new JComboBox();
		exitDaycomboBox.setModel(new DefaultComboBoxModel(dayWeek));
		GridBagConstraints gbc_exitDaycomboBox = new GridBagConstraints();
		gbc_exitDaycomboBox.anchor = GridBagConstraints.WEST;
		gbc_exitDaycomboBox.insets = new Insets(0, 0, 5, 5);
		gbc_exitDaycomboBox.gridx = 9;
		gbc_exitDaycomboBox.gridy = 7;
		add(exitDaycomboBox, gbc_exitDaycomboBox);
		
		btnSave = new JButton("Salva");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = lblAnimeName.getText();
				String currEp = currentEpisodeField.getText();
				String totEp = totalEpisodeText.getText();
				String fansub = (String) fansubComboBox.getSelectedItem();
				String fansubLink = getLink();
				String note = noteTextArea.getText();
				String day = (String) exitDaycomboBox.getSelectedItem();
				
				String list = AnimeIndex.getList();
				if (list.equalsIgnoreCase("Anime Completati"))
					{
					String image = AnimeIndex.completedMap.get(name).getImageName();
					AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day);
					AnimeIndex.completedMap.put(name, data);
					}
				else if (list.equalsIgnoreCase("Anime in Corso"))
					{
					String image = AnimeIndex.airingMap.get(name).getImageName();
					AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day);
					AnimeIndex.airingMap.put(name, data);
					}
				else if (list.equalsIgnoreCase("OAV"))
					{
					String image = AnimeIndex.ovaMap.get(name).getImageName();
					AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day);
					AnimeIndex.ovaMap.put(name, data);
					}
				else if (list.equalsIgnoreCase("Film"))
				{
					String image = AnimeIndex.filmMap.get(name).getImageName();
					AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day);
					AnimeIndex.filmMap.put(name, data);
				}
				else if (list.equalsIgnoreCase("Completi Da Vedere"))
				{
					String image = AnimeIndex.completedToSeeMap.get(name).getImageName();
					AnimeData data = new AnimeData(currEp, totEp, fansub, fansubLink, note, image, day);
					AnimeIndex.completedToSeeMap.put(name, data);
				}
			}
		});
		
		lblNote = new JLabel("Note:");
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblNote.gridx = 8;
		gbc_lblNote.gridy = 9;
		add(lblNote, gbc_lblNote);
		
		JScrollPane noteScrollPane = new JScrollPane();
		noteScrollPane.setSize(new Dimension(350, 50));
		noteScrollPane.setMinimumSize(new Dimension(350, 50));
		noteScrollPane.setMaximumSize(new Dimension(350, 50));
		GridBagConstraints gbc_noteScrollPane = new GridBagConstraints();
		gbc_noteScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_noteScrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_noteScrollPane.gridwidth = 4;
		gbc_noteScrollPane.gridx = 8;
		gbc_noteScrollPane.gridy = 10;
		add(noteScrollPane, gbc_noteScrollPane);
		
		noteTextArea = new JTextArea();
		noteTextArea.setSize(new Dimension(350, 10));
		noteTextArea.setMinimumSize(new Dimension(350, 50));
		noteTextArea.setMaximumSize(new Dimension(350, 50));
		noteTextArea.setPreferredSize(new Dimension(350, 50));
		noteTextArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		noteScrollPane.setViewportView(noteTextArea);
		noteTextArea.setLineWrap(true);
		noteTextArea.setWrapStyleWord(true);
		
		finishedButton = new JButton("Concluso");
		finishedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = lblAnimeName.getText();
				String type = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();
				
				DefaultListModel model = null;
				JList list = null;
				TreeMap<String,AnimeData> map = null;								
				if (type.equalsIgnoreCase("anime in corso"))
				{
					model = AnimeIndex.airingModel;
					list = AnimeIndex.airingList;
					map = AnimeIndex.airingMap;
				}
				else if (type.equalsIgnoreCase("oav"))
				{
					model = AnimeIndex.ovaModel;
					list = AnimeIndex.ovaList;
					map = AnimeIndex.ovaMap;
				}
				else if (type.equalsIgnoreCase("film"))
				{
					model = AnimeIndex.filmModel;
					list = AnimeIndex.filmList;
					map = AnimeIndex.filmMap;
				}
				else if (type.equalsIgnoreCase("completi da vedere"))
				{
					model = AnimeIndex.completedToSeeModel;
					list = AnimeIndex.completedToSeeList;
					map = AnimeIndex.completedToSeeMap;
				}
				AnimeData oldData = map.get(name);
				AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), oldData.getTotalEpisode(), oldData.getFansub(), oldData.getFansubLink(), oldData.getNote(), oldData.getImageName(), "Concluso");
				map.remove(name);
				AnimeIndex.completedMap.put(name, newData);
				int index = list.getSelectedIndex();
				model.removeElementAt(index);
				AnimeIndex.completedModel.addElement(name);
			}
		});
		GridBagConstraints gbc_finishedButton = new GridBagConstraints();
		gbc_finishedButton.anchor = GridBagConstraints.WEST;
		gbc_finishedButton.insets = new Insets(0, 0, 0, 5);
		gbc_finishedButton.gridx = 8;
		gbc_finishedButton.gridy = 11;
		finishedButton.setEnabled(false);
		add(finishedButton, gbc_finishedButton);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.EAST;
		gbc_btnSave.gridwidth = 2;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 10;
		gbc_btnSave.gridy = 11;
		add(btnSave, gbc_btnSave);
	}
	
	public void setAnimeName(String name)
	{
		lblAnimeName.setText(name);
	}
	
	public void setCurrentEp(String episode)
	{
		currentEpisodeField.setText(episode);
	}
	
	public void setTotalEp(String totEpisode)
	{
		totalEpisodeText.setText(totEpisode);
	}
	
	public void setFansub(String fansub)
	{
		fansubComboBox.setSelectedItem(fansub);
	}
		
	public static void setFansubComboBox()
	{
		fansubComboBox.setModel(new DefaultComboBoxModel(AnimeIndex.getFansubList()));
	}

	public void setNote(String note)
	{
		noteTextArea.setText(note);
	}

	public void setImage(String path)
	{
		try {
			BufferedImage image = null;
			if (path.equals("deafult"))
			{
				image = ImageIO.read(ClassLoader.getSystemResource("image/default.png"));
			}
			else
			{
				image = ImageIO.read(new File(path));
			}
			animeImage.setIcon(new ImageIcon(image));
		} catch (IIOException e) {
			System.out.println("Immagine mancante");
		}
	 catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	public void setDay(String day)
	{
		exitDaycomboBox.setSelectedItem(day);
	}
	
	public void setLink(String linkToSet)
	{
		link = linkToSet;
	}
	
	public String getLink()
	{
		return link;
	}
}

