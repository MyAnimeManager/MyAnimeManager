package util.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;

import main.AnimeIndex;
import util.AnimeData;
import util.FileDrop;
import util.FileManager;
import util.Filters;
import util.MAMUtil;
import util.PatternFilter;
import util.SortedListModel;
import util.UtilEvent;

public class AnimeInformation extends JPanel
{
	public JTextField totalEpisodeText;
	public JTextField currentEpisodeField;
	public JButton minusButton;
	public JButton plusButton;
	public JLabel lblAnimeName;
	public static JComboBox fansubComboBox;
	public JButton finishedButton;
	public JButton btnOpen;
	public JLabel lblEpisode;
	public JButton btnAnilistInfo;
	public JTextArea noteTextArea;
	private JScrollPane scrollPane;
	public JLabel animeImage;
	private JLabel lblExitDay;
	public JComboBox exitDaycomboBox;
	private JLabel lblNote;
	private Component rigidArea;
	private Component rigidArea_1;
	public JButton fansubButton;
	public JButton setLinkButton;
	public SetLinkDialog setLink;
	public String link;
	private JLabel lblTipo;
	public JButton addToSeeButton;
	public JComboBox typeComboBox;
	private JLabel lblInizio;
	private JLabel lblFine;
	public JTextField finishDateField;
	public JTextField releaseDateField;
	private JLabel lblDurata;
	public JTextField durationField;
	public JButton checkDataButton;
	public static UpdatingAnimeDataDialog dial;
	public boolean selectExcludedAnimeAtWindowOpened = false;
	public JButton btnFolder;
	public SynchronizingDialog SynchroDial;

	public AnimeInformation()
	{
		setSize(new Dimension(625, 441));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 46, 23, 20, 32, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 60, 0, 0, 0, 0, 0, 0, 0, 43, -2, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		scrollPane.setBorder(null);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 15;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		lblAnimeName = new JLabel("Anime");
		scrollPane.setViewportView(lblAnimeName);
		// lblAnimeName.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25));
		lblAnimeName.setFont(AnimeIndex.segui.deriveFont(29f));
		// label immagine
		BufferedImage image = null;
		try
		{
			File img = new File(MAMUtil.getDefaultImageFolderPath() + File.separator + "default.png");
			if (img.isFile())
				image = ImageIO.read(img);
			else
				image = ImageIO.read(ClassLoader.getSystemResource("image/default.png"));
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
			MAMUtil.writeLog(e1);
		}

		plusButton = new JButton("");
		plusButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/plus12.png")));
		plusButton.setSize(new Dimension(30, 30));
		plusButton.setMaximumSize(new Dimension(30, 30));
		plusButton.setMinimumSize(new Dimension(30, 30));
		plusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
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
							minusButton.setEnabled(true);
						if ((totalEpisodeText.getText()) != null && !(totalEpisodeText.getText().isEmpty()) && !totalEpisodeText.getText().equalsIgnoreCase("??"))
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
						// AnimeIndex.setAnimeInformationFields();
					}
			}
		});

		lblEpisode = new JLabel("Episodio Corrente :");

		rigidArea = Box.createRigidArea(new Dimension(5, 20));
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.gridheight = 12;
		gbc_rigidArea.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 0;
		add(rigidArea, gbc_rigidArea);
		animeImage = new JLabel(new ImageIcon(image));
		animeImage.addMouseListener(UtilEvent.exclusionPopUpMenu());
		animeImage.setBorder(new LineBorder(new Color(40, 40, 40), 3, true));
		GridBagConstraints gbc_animeImage = new GridBagConstraints();
		gbc_animeImage.gridheight = 9;
		gbc_animeImage.insets = new Insets(0, 0, 5, 5);
		gbc_animeImage.gridx = 2;
		gbc_animeImage.gridy = 2;
		add(animeImage, gbc_animeImage);
		GridBagConstraints gbc_lblEpisode = new GridBagConstraints();
		gbc_lblEpisode.insets = new Insets(0, 0, 5, 5);
		gbc_lblEpisode.gridx = 10;
		gbc_lblEpisode.gridy = 3;
		add(lblEpisode, gbc_lblEpisode);

		// secondo numero = massimo numero a cui può arrivare

		currentEpisodeField = new JTextField();
		currentEpisodeField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e)
			{
				AnimeIndex.currentEpisodeNumber = currentEpisodeField.getText();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (!e.getOppositeComponent().equals(plusButton))
				{
					if (currentEpisodeField.getText().isEmpty())
						currentEpisodeField.setText(AnimeIndex.currentEpisodeNumber);
				}
				else
				{
					String numero = Integer.parseInt(AnimeIndex.currentEpisodeNumber) - 1 + "";
					currentEpisodeField.setText(numero);
				}
			}
		});
		currentEpisodeField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e)
			{
				if (!currentEpisodeField.getText().contains("?") && !totalEpisodeText.getText().contains("?"))
				{
					char key = e.getKeyChar();
					int currEp = 0;
					if (Character.isDigit(key) && !currentEpisodeField.getText().isEmpty())
					{
						String currText = currentEpisodeField.getText() ;
						int currSelStart = currentEpisodeField.getSelectionStart();
						int currSelEnd = currentEpisodeField.getSelectionEnd();
						if (currentEpisodeField.getSelectedText() != null)
						{
							String unselectedTextBefore = currText.substring(0, currSelStart);
							String unseletedTextAfter = currText.substring(currSelEnd);
							currEp = Integer.parseInt(unselectedTextBefore + key + unseletedTextAfter);
						}
						else
							currEp = Integer.parseInt(currText + key);
						
					}
					else if (!currentEpisodeField.getText().isEmpty())
						currEp = Integer.parseInt(currentEpisodeField.getText());
					else if (Character.isDigit(key) && currentEpisodeField.getText().isEmpty())
						currEp = Integer.parseInt(key + "");

					int totEp = Integer.parseInt(totalEpisodeText.getText());
					if (currEp > totEp)
						e.consume();

					if (currEp < totEp)
					{
						minusButton.setEnabled(true);
						plusButton.setEnabled(true);
						finishedButton.setEnabled(false);
					}

					if (currEp == 0)
					{
						minusButton.setEnabled(false);
						finishedButton.setEnabled(false);
					}

					if (currEp == totEp)
					{
						plusButton.setEnabled(false);
						finishedButton.setEnabled(true);
					}
					// AnimeIndex.setAnimeInformationFields(currEp, totEp);
				}
			}
		});
		currentEpisodeField.setPreferredSize(new Dimension(43, 23));
		currentEpisodeField.setMinimumSize(new Dimension(43, 23));
		GridBagConstraints gbc_currentEpisodeField = new GridBagConstraints();
		gbc_currentEpisodeField.anchor = GridBagConstraints.WEST;
		gbc_currentEpisodeField.insets = new Insets(0, 0, 5, 5);
		gbc_currentEpisodeField.gridx = 11;
		gbc_currentEpisodeField.gridy = 3;
		add(currentEpisodeField, gbc_currentEpisodeField);
		currentEpisodeField.setColumns(3);
		((AbstractDocument) currentEpisodeField.getDocument()).setDocumentFilter(new PatternFilter("\\d{0,4}||\\?{0,2}"));

		GridBagConstraints gbc_plusButton = new GridBagConstraints();
		gbc_plusButton.anchor = GridBagConstraints.WEST;
		gbc_plusButton.insets = new Insets(0, 0, 5, 5);
		gbc_plusButton.gridx = 14;
		gbc_plusButton.gridy = 3;
		add(plusButton, gbc_plusButton);
		plusButton.setPreferredSize(new Dimension(30, 30));

		minusButton = new JButton("");
		minusButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/minus12.png")));
		minusButton.setMinimumSize(new Dimension(30, 30));
		minusButton.setMaximumSize(new Dimension(30, 30));
		minusButton.setSize(new Dimension(30, 30));
		minusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					int num = Integer.parseInt(currentEpisodeField.getText());
					if (num != 0)
					{
						num--;
						currentEpisodeField.setText(Integer.toString(num));
						if (num == 0)
						{
							minusButton.setEnabled(false);
							plusButton.requestFocusInWindow();
						}

						if ((totalEpisodeText.getText()) != null && !(totalEpisodeText.getText().isEmpty()))
							if (!totalEpisodeText.getText().equals("??"))
							{
								int maxnum = Integer.parseInt(totalEpisodeText.getText());
								if (num < maxnum)
								{
									plusButton.setEnabled(true);
									finishedButton.setEnabled(false);
								}
								if (num == maxnum - 1 && Integer.parseInt(totalEpisodeText.getText()) > 1)
									if (MAMUtil.getList().equalsIgnoreCase("OAV") || MAMUtil.getList().equalsIgnoreCase("Film"))
									{
										exitDaycomboBox.setSelectedItem("?????");
										exitDaycomboBox.setEnabled(true);
									}
								// AnimeIndex.setAnimeInformationFields();
							}
					}
				}
			}
		});
		GridBagConstraints gbc_minusButton = new GridBagConstraints();
		gbc_minusButton.anchor = GridBagConstraints.EAST;
		gbc_minusButton.insets = new Insets(0, 0, 5, 5);
		gbc_minusButton.gridx = 15;
		gbc_minusButton.gridy = 3;
		add(minusButton, gbc_minusButton);
		minusButton.setPreferredSize(new Dimension(30, 30));

		rigidArea_1 = Box.createRigidArea(new Dimension(5, 20));
		GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
		gbc_rigidArea_1.gridheight = 11;
		gbc_rigidArea_1.gridx = 16;
		gbc_rigidArea_1.gridy = 1;
		add(rigidArea_1, gbc_rigidArea_1);

		// total episode label e text field
		JLabel lblTotalEpisode = new JLabel("Episodi Totali :");
		GridBagConstraints gbc_lblTotalEpisode = new GridBagConstraints();
		gbc_lblTotalEpisode.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalEpisode.gridx = 10;
		gbc_lblTotalEpisode.gridy = 4;
		add(lblTotalEpisode, gbc_lblTotalEpisode);

		totalEpisodeText = new JTextField();
		totalEpisodeText.addMouseListener(UtilEvent.exclusionPopUpMenu());
		totalEpisodeText.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e)
			{
				AnimeIndex.totalEpNumber = totalEpisodeText.getText();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (totalEpisodeText.getText().isEmpty())
					totalEpisodeText.setText(AnimeIndex.totalEpNumber);
				else if (totalEpisodeText.getText().equals(currentEpisodeField.getText()))
					finishedButton.setEnabled(true);
				if(!totalEpisodeText.getText().contains("?"))
				{
					if (Integer.parseInt(totalEpisodeText.getText()) > Integer.parseInt(currentEpisodeField.getText()) && !plusButton.isEnabled())
						plusButton.setEnabled(true);
					else if (Integer.parseInt(totalEpisodeText.getText()) < Integer.parseInt(currentEpisodeField.getText()))
					{
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Il numero degli \"Episodi Totali\" non può essere inferiore al numero dell'\"Episodio Corrente\"", "Errore!", JOptionPane.ERROR_MESSAGE);
						if (totalEpisodeText.requestFocusInWindow())
							totalEpisodeText.requestFocusInWindow();
						else
							totalEpisodeText.requestFocus();
					}
				}
			}
		});
		totalEpisodeText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e)
			{
				// if (!currentEpisodeField.getText().contains("?") &&
				// !totalEpisodeText.getText().contains("?"))
				// {
				// char key = e.getKeyChar();
				// int totEp = 0;
				// if (Character.isDigit(key) &&
				// !totalEpisodeText.getText().isEmpty())
				// totEp = Integer.parseInt(totalEpisodeText.getText() + key);
				// else if (!totalEpisodeText.getText().isEmpty())
				// totEp = Integer.parseInt(totalEpisodeText.getText());
				// else if (Character.isDigit(key) &&
				// totalEpisodeText.getText().isEmpty())
				// totEp = Integer.parseInt(key + "");
				// int currEp = Integer.parseInt(currentEpisodeField.getText());
				//
				// AnimeIndex.setAnimeInformationFields(currEp, totEp);
				// }
			}
		});
		totalEpisodeText.setMinimumSize(new Dimension(43, 23));
		totalEpisodeText.setPreferredSize(new Dimension(43, 23));
		GridBagConstraints gbc_totalEpisodeText = new GridBagConstraints();
		gbc_totalEpisodeText.anchor = GridBagConstraints.WEST;
		gbc_totalEpisodeText.insets = new Insets(0, 0, 5, 5);
		gbc_totalEpisodeText.gridx = 11;
		gbc_totalEpisodeText.gridy = 4;

		add(totalEpisodeText, gbc_totalEpisodeText);
		totalEpisodeText.setColumns(3);
		((AbstractDocument) totalEpisodeText.getDocument()).setDocumentFilter(new PatternFilter("\\d{0,4}||\\?{0,2}"));

		lblDurata = new JLabel("Durata :");
		GridBagConstraints gbc_lblDurata = new GridBagConstraints();
		gbc_lblDurata.anchor = GridBagConstraints.EAST;
		gbc_lblDurata.gridwidth = 2;
		gbc_lblDurata.insets = new Insets(0, 0, 5, 5);
		gbc_lblDurata.gridx = 12;
		gbc_lblDurata.gridy = 4;
		add(lblDurata, gbc_lblDurata);

		durationField = new JTextField();
		durationField.addMouseListener(UtilEvent.exclusionPopUpMenu());
		durationField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e)
			{
				AnimeIndex.durata = durationField.getText().trim();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (durationField.getText().trim().isEmpty())
					durationField.setText(AnimeIndex.durata);
				if (!durationField.getText().trim().isEmpty() && !durationField.getText().trim().endsWith(" min"))
				{
					String duration = durationField.getText().trim();
					duration = duration.replaceAll("\\p{IsAlphabetic}", "");
					duration = duration.replaceAll(" ", "");
					duration = duration.replaceAll("\\p{IsPunctuation}", "");
					if (duration.isEmpty())
						duration = "?? min";
					else
						duration = duration + " min";
					durationField.setText(duration);
				}
			}
		});
		GridBagConstraints gbc_durationFiled = new GridBagConstraints();
		gbc_durationFiled.gridwidth = 2;
		gbc_durationFiled.insets = new Insets(0, 0, 5, 5);
		gbc_durationFiled.fill = GridBagConstraints.HORIZONTAL;
		gbc_durationFiled.gridx = 14;
		gbc_durationFiled.gridy = 4;
		add(durationField, gbc_durationFiled);
		durationField.setColumns(10);

		JLabel lblFansub = new JLabel("Fansub :");
		GridBagConstraints gbc_lblFansub = new GridBagConstraints();
		gbc_lblFansub.insets = new Insets(0, 0, 5, 5);
		gbc_lblFansub.gridx = 10;
		gbc_lblFansub.gridy = 5;
		add(lblFansub, gbc_lblFansub);

		fansubComboBox = new JComboBox();
		fansubComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0)
			{
				String fansubName = (String) fansubComboBox.getSelectedItem();
				if (fansubName != null && !fansubName.isEmpty())
				{
					String link = AnimeIndex.fansubMap.get(fansubName);
					if (link != null && !link.isEmpty())
						fansubButton.setEnabled(true);
					else
						fansubButton.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_fansubComboBox = new GridBagConstraints();
		gbc_fansubComboBox.gridwidth = 3;
		gbc_fansubComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_fansubComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_fansubComboBox.gridx = 11;
		gbc_fansubComboBox.gridy = 5;
		add(fansubComboBox, gbc_fansubComboBox);
		fansubButton = new JButton("Apri");
		fansubButton.setToolTipText("Apri il sito web del fansub");
		fansubButton.setEnabled(false);
		fansubButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					String link = AnimeIndex.fansubMap.get(fansubComboBox.getSelectedItem());
					if (link != null && !link.isEmpty())
						try
						{
							URI uriLink = new URI(link);
							Desktop.getDesktop().browse(uriLink);
						}
						catch (URISyntaxException e)
						{
							JOptionPane.showMessageDialog(AnimeIndex.animeDialog, "Link non valido", "Errore", JOptionPane.ERROR_MESSAGE);
							fansubButton.setEnabled(false);
						}
						catch (IOException e)
						{
							JOptionPane.showMessageDialog(AnimeIndex.animeDialog, "Link non valido", "Errore", JOptionPane.ERROR_MESSAGE);
							fansubButton.setEnabled(false);
						}
					else
						fansubButton.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_fansubButton = new GridBagConstraints();
		gbc_fansubButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_fansubButton.gridwidth = 2;
		gbc_fansubButton.insets = new Insets(0, 0, 5, 5);
		gbc_fansubButton.gridx = 14;
		gbc_fansubButton.gridy = 5;
		add(fansubButton, gbc_fansubButton);

		JLabel lblFansubLink = new JLabel("Link :");
		GridBagConstraints gbc_lblFansubLink = new GridBagConstraints();
		gbc_lblFansubLink.insets = new Insets(0, 0, 5, 5);
		gbc_lblFansubLink.gridx = 10;
		gbc_lblFansubLink.gridy = 6;
		add(lblFansubLink, gbc_lblFansubLink);

		btnOpen = new JButton("Apri");
		btnOpen.setToolTipText("Apri il link impostato");
		btnOpen.setEnabled(false);
		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					String link = getLink();
					if (!link.isEmpty())
						try
						{
							URI uriLink = new URI(link);
							Desktop.getDesktop().browse(uriLink);
						}
						catch (URISyntaxException e)
						{
							JOptionPane.showMessageDialog(AnimeIndex.animeDialog, "Link non valido", "Errore", JOptionPane.ERROR_MESSAGE);
							btnOpen.setEnabled(false);
						}
						catch (IOException e)
						{
							JOptionPane.showMessageDialog(AnimeIndex.animeDialog, "Link non valido", "Errore", JOptionPane.ERROR_MESSAGE);
							btnOpen.setEnabled(false);
						}
					else
						btnOpen.setEnabled(false);
				}
			}
		});

		setLinkButton = new JButton("Imposta Link");
		setLinkButton.setToolTipText("Aggiungi un link rapido");
		setLinkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					setLink = new SetLinkDialog();
					setLink.setLocationRelativeTo(AnimeIndex.mainFrame);
					setLink.setVisible(true);
				}
			}
		});
		GridBagConstraints gbc_btnScegliLink = new GridBagConstraints();
		gbc_btnScegliLink.gridwidth = 3;
		gbc_btnScegliLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnScegliLink.insets = new Insets(0, 0, 5, 5);
		gbc_btnScegliLink.gridx = 11;
		gbc_btnScegliLink.gridy = 6;
		add(setLinkButton, gbc_btnScegliLink);
		GridBagConstraints gbc_btnOpen = new GridBagConstraints();
		gbc_btnOpen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOpen.gridwidth = 2;
		gbc_btnOpen.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpen.gridx = 14;
		gbc_btnOpen.gridy = 6;
		add(btnOpen, gbc_btnOpen);

		lblInizio = new JLabel("Inizio :");
		GridBagConstraints gbc_lblInizio = new GridBagConstraints();
		gbc_lblInizio.insets = new Insets(0, 0, 5, 5);
		gbc_lblInizio.gridx = 10;
		gbc_lblInizio.gridy = 7;
		add(lblInizio, gbc_lblInizio);

		releaseDateField = new JTextField();
		releaseDateField.addMouseListener(UtilEvent.exclusionPopUpMenu());
		((AbstractDocument) releaseDateField.getDocument()).setDocumentFilter(new PatternFilter("[\\p{IsDigit}\\/\\?]{0,10}"));
		releaseDateField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e)
			{
				AnimeIndex.startDate = releaseDateField.getText().trim();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (e.getOppositeComponent() != null)
				{
					if (releaseDateField.getText().trim().isEmpty())
						releaseDateField.setText(AnimeIndex.startDate);
					if (releaseDateField.getText().trim().length() != 10 && !releaseDateField.getText().trim().isEmpty())
					{
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "La data deve essere del tipo giorno/mese/anno. (Esempio: 13/09/1995)", "Errore!", JOptionPane.ERROR_MESSAGE);
						if (!releaseDateField.requestFocusInWindow())
							releaseDateField.requestFocus();
						else
							releaseDateField.requestFocusInWindow();
					}
					else if (releaseDateField.getText().trim().substring(6, 10).equals("????"))
						releaseDateField.setText("??/??/????");
					else if (releaseDateField.getText().trim().substring(3, 5).equals("??"))
						releaseDateField.setText("??/??/" + releaseDateField.getText().trim().substring(6, 10));
					else
						releaseDateField.setText(releaseDateField.getText().trim());
					// AnimeIndex.setAnimeInformationFields();
				}
			}
		});
		GridBagConstraints gbc_releaseDateField = new GridBagConstraints();
		gbc_releaseDateField.gridwidth = 2;
		gbc_releaseDateField.insets = new Insets(0, 0, 5, 5);
		gbc_releaseDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_releaseDateField.gridx = 11;
		gbc_releaseDateField.gridy = 7;
		add(releaseDateField, gbc_releaseDateField);
		releaseDateField.setColumns(10);

		lblFine = new JLabel("Fine :");
		GridBagConstraints gbc_lblFine = new GridBagConstraints();
		gbc_lblFine.anchor = GridBagConstraints.EAST;
		gbc_lblFine.insets = new Insets(0, 0, 5, 5);
		gbc_lblFine.gridx = 13;
		gbc_lblFine.gridy = 7;
		add(lblFine, gbc_lblFine);

		finishDateField = new JTextField();
		finishDateField.addMouseListener(UtilEvent.exclusionPopUpMenu());
		((AbstractDocument) finishDateField.getDocument()).setDocumentFilter(new PatternFilter("[\\p{IsDigit}\\/\\?]{0,10}"));
		finishDateField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e)
			{
				AnimeIndex.endDate = finishDateField.getText().trim();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (e.getOppositeComponent() != null)
				{
					if (finishDateField.getText().trim().isEmpty())
						finishDateField.setText(AnimeIndex.endDate);
					if (finishDateField.getText().trim().length() != 10 && !finishDateField.getText().trim().isEmpty())
					{
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "La data deve essere del tipo giorno/mese/anno. (Esempio: 13/09/1995)", "Errore!", JOptionPane.ERROR_MESSAGE);
						if(!finishDateField.requestFocusInWindow())
							finishDateField.requestFocus();
						else
							finishDateField.requestFocusInWindow();
					}
					else if (finishDateField.getText().trim().substring(6, 10).equals("????"))
						finishDateField.setText("??/??/????");
					else if (finishDateField.getText().trim().substring(3, 5).equals("??"))
						finishDateField.setText("??/??/" + finishDateField.getText().trim().substring(6, 10));
					else
						finishDateField.setText(finishDateField.getText().trim());
					// AnimeIndex.setAnimeInformationFields();
				}
			}
		});
		GridBagConstraints gbc_finishDateField = new GridBagConstraints();
		gbc_finishDateField.gridwidth = 2;
		gbc_finishDateField.insets = new Insets(0, 0, 5, 5);
		gbc_finishDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_finishDateField.gridx = 14;
		gbc_finishDateField.gridy = 7;
		add(finishDateField, gbc_finishDateField);
		finishDateField.setColumns(10);

		lblExitDay = new JLabel("Giorno di Uscita :");
		GridBagConstraints gbc_lblExitDay = new GridBagConstraints();
		gbc_lblExitDay.insets = new Insets(0, 0, 5, 5);
		gbc_lblExitDay.gridx = 10;
		gbc_lblExitDay.gridy = 8;
		add(lblExitDay, gbc_lblExitDay);

		exitDaycomboBox = new JComboBox();
		exitDaycomboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// AnimeIndex.setAnimeInformationFields();
			}
		});
		exitDaycomboBox.setEnabled(false);
		exitDaycomboBox.setModel(new DefaultComboBoxModel(new String[] { "?????", "Luned\u00EC", "Marted\u00EC", "Mercoled\u00EC", "Gioved\u00EC", "Venerd\u00EC", "Sabato", "Domenica", "Concluso", "Irregolare", "Sospesa", "Rilasciato" }));
		GridBagConstraints gbc_exitDaycomboBox = new GridBagConstraints();
		gbc_exitDaycomboBox.gridwidth = 2;
		gbc_exitDaycomboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_exitDaycomboBox.insets = new Insets(0, 0, 5, 5);
		gbc_exitDaycomboBox.gridx = 11;
		gbc_exitDaycomboBox.gridy = 8;
		add(exitDaycomboBox, gbc_exitDaycomboBox);

		lblTipo = new JLabel("Tipo :");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.gridx = 13;
		gbc_lblTipo.gridy = 8;
		add(lblTipo, gbc_lblTipo);

		typeComboBox = new JComboBox();
		typeComboBox.addMouseListener(UtilEvent.exclusionPopUpMenu());
		typeComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String bdType = (String) typeComboBox.getSelectedItem();
				String section = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();
				if (section.equalsIgnoreCase("anime completati") || section.equalsIgnoreCase("Completi Da Vedere"))
				{
					String nome = lblAnimeName.getText();
					if (!nome.equalsIgnoreCase("Anime"))
					{
						TreeMap<String, AnimeData> map1 = MAMUtil.getMap();
						AnimeData old1 = map1.get(nome);
						if (old1.getBd() == false)
							if (bdType.equalsIgnoreCase("blu-ray"))
							{
								int shouldCancel = JOptionPane.showConfirmDialog(AnimeIndex.mainFrame, "Spostare l'Anime in \"Anime in Corso\" come tipo: Blu-ray?", "Richiesta", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (shouldCancel == 0)
								{
									String name = lblAnimeName.getText();
									if (AnimeIndex.airingMap.containsKey(name))
									{
										TreeMap<String, AnimeData> map = null;
										if (section.equalsIgnoreCase("Completi Da Vedere"))
											map = AnimeIndex.completedToSeeMap;
										else
											map = AnimeIndex.completedMap;
										AnimeData oldData = map.get(name);
										typeComboBox.setSelectedItem(oldData.getAnimeType());
										JOptionPane.showMessageDialog(AnimeInformation.this, "Anime già presente in \"Anime in Corso\"", "Errore!", JOptionPane.ERROR_MESSAGE);
									}
									else
									{
										AnimeIndex.saveModifiedInformation();
										if (AnimeIndex.filtro != 9 && !AnimeIndex.searchBar.getText().isEmpty() && !name.equals("Anime"))
											AnimeIndex.filterList.setSelectedValue(name, true);

										if (AnimeIndex.filtro != 9 && !name.equals("Anime"))
											MAMUtil.getJList().setSelectedValue(name, true);
										if (!AnimeIndex.searchBar.getText().isEmpty() && !name.equals("Anime"))
											MAMUtil.getJList().setSelectedValue(name, true);
										SortedListModel model = null;
										JList list = null;
										TreeMap<String, AnimeData> map = null;
										if (section.equalsIgnoreCase("Completi Da Vedere"))
										{
											model = AnimeIndex.completedToSeeModel;
											list = AnimeIndex.completedToSeeList;
											map = AnimeIndex.completedToSeeMap;
										}
										else
										{
											model = AnimeIndex.completedModel;
											list = AnimeIndex.completedList;
											map = AnimeIndex.completedMap;
										}
										AnimeData oldData = map.get(name);
										AnimeData newData = new AnimeData("1", oldData.getTotalEpisode(), oldData.getFansub(), oldData.getNote(), oldData.getImageName(), "Irregolare", oldData.getId(), oldData.getLinkName(), oldData.getLink(), "Blu-ray", oldData.getReleaseDate(), oldData.getFinishDate(), oldData.getDurationEp(), true);

										FileManager.moveImage(oldData.getImagePath(section), "Airing", oldData.getImageName());
										map.remove(name);
										AnimeIndex.airingMap.put(name, newData);

										int index;
										int filterIndex;
										int searchIndex;
										if (AnimeIndex.filtro != 9)
										{
											filterIndex = AnimeIndex.filterList.getSelectedIndex();
											AnimeIndex.filterModel.removeElementAt(filterIndex);
										}
										else
											filterIndex = -1;

										if (!AnimeIndex.searchBar.getText().isEmpty())
										{
											searchIndex = AnimeIndex.searchList.getSelectedIndex();
											AnimeIndex.searchModel.removeElementAt(searchIndex);
										}
										else
											searchIndex = -1;

										index = list.getSelectedIndex();
										model.removeElementAt(index);
										list.clearSelection();
										if (AnimeIndex.filtro != 9 && !AnimeIndex.searchBar.getText().isEmpty())
											AnimeIndex.filterList.clearSelection();

										AnimeIndex.airingModel.addElement(name);
										if (!AnimeIndex.shiftsRegister.containsKey(name))
											AnimeIndex.shiftsRegister.put(name, section);
										if (AnimeIndex.sessionAddedAnime.contains(name))
											AnimeIndex.sessionAddedAnimeImagesShiftsRegister.put(name, MAMUtil.getImageFolderPath() + "Airing" + File.separator + name + ".png");
										AnimeIndex.animeInformation.minusButton.setEnabled(true);
										AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
										AnimeIndex.animeInformation.totalEpisodeText.setEnabled(true);
										AnimeIndex.animeInformation.addToSeeButton.setEnabled(true);

										setBlank();

										if (AnimeIndex.filtro != 9)
										{
											Filters.removeFilters();
											AnimeIndex.setFilterButton.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/ellipse_icon3.png")));
										}
										AnimeIndex.animeTypeComboBox.setSelectedItem("Anime in Corso");
										AnimeIndex.airingList.setSelectedValue(name, true);
										AnimeIndex.lastSelection = name;
									}
								}
								else if (shouldCancel == 1)
								{
									AnimeIndex.saveModifiedInformation();
									String name = lblAnimeName.getText();
									TreeMap<String, AnimeData> map = null;
									if (section.equalsIgnoreCase("Completi Da Vedere"))
										map = AnimeIndex.completedToSeeMap;
									else
										map = AnimeIndex.completedMap;
									AnimeData oldData = map.get(name);
									AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), oldData.getTotalEpisode(), oldData.getFansub(), oldData.getNote(), oldData.getImageName(), "Irregolare", oldData.getId(), oldData.getLinkName(), oldData.getLink(), "Blu-ray", oldData.getReleaseDate(), oldData.getFinishDate(), oldData.getDurationEp(), true);
									map.put(name, newData);

									if (AnimeIndex.filtro != 9)
									{
										Filters.removeFilters();
										String lbl = AnimeIndex.animeInformation.lblAnimeName.getText();
										if (section.equalsIgnoreCase("Completi Da Vedere") && !lbl.equals("Anime"))
											AnimeIndex.completedToSeeList.setSelectedValue(lbl, true);
										else if (!lbl.equals("Anime"))
											AnimeIndex.completedList.setSelectedValue(lbl, true);
									}
								}
								else
								{
									String name = lblAnimeName.getText();
									TreeMap<String, AnimeData> map = null;
									if (section.equalsIgnoreCase("Completi Da Vedere"))
										map = AnimeIndex.completedToSeeMap;
									else
										map = AnimeIndex.completedMap;
									AnimeData oldData = map.get(name);
									typeComboBox.setSelectedItem(oldData.getAnimeType());
								}
							}
					}
				}
			}
		});

		typeComboBox.setModel(new DefaultComboBoxModel(new String[] { "?????", "TV", "Movie", "Special", "OVA", "ONA", "TV Short", "Blu-ray" }));
		GridBagConstraints gbc_typeComboBox = new GridBagConstraints();
		gbc_typeComboBox.gridwidth = 2;
		gbc_typeComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_typeComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_typeComboBox.gridx = 14;
		gbc_typeComboBox.gridy = 8;
		add(typeComboBox, gbc_typeComboBox);

		lblNote = new JLabel("Note :");
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblNote.gridx = 10;
		gbc_lblNote.gridy = 9;
		add(lblNote, gbc_lblNote);

		checkDataButton = new JButton("");
		checkDataButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					JList list = MAMUtil.getJList();
					if (!list.isSelectionEmpty())
						if (SwingUtilities.isRightMouseButton(e))
						{
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Aggiungi alle Esclusioni");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] exc = { true, true, true, true, true, true };
									AnimeIndex.exclusionAnime.put(lblAnimeName.getText(), exc);
									selectExcludedAnimeAtWindowOpened = true;
									AnimeIndex.preferenceDialog.exclusionDialog = new SetExclusionDialog();
									AnimeIndex.preferenceDialog.exclusionDialog.setLocationRelativeTo(AnimeIndex.mainFrame);
									AnimeIndex.preferenceDialog.exclusionDialog.setVisible(true);
								}
							});
							JMenuItem remove = new JMenuItem("Rimuovi dalle Esclusioni");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									AnimeIndex.exclusionAnime.remove((lblAnimeName.getText()));
								}
							});
							if (AnimeIndex.exclusionAnime.containsKey(lblAnimeName.getText()))
								menu.add(remove);
							else
								menu.add(add);
							menu.show((JButton) e.getSource(), e.getX(), e.getY());
						}
				}
			}
		});
		checkDataButton.setToolTipText("Aggiorna i dati dell'anime");
		checkDataButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					String name = lblAnimeName.getText();
					if (AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("true") && !AnimeIndex.exclusionAnime.containsKey(name))
						JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "I dati di questo anime sono già stati\n\raggiornati dal \"Controllo Dati Automatico\".", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
					else
					{
						FieldExclusionDialog exclusions = new FieldExclusionDialog();
						exclusions.setLocationRelativeTo(exitDaycomboBox);
						exclusions.setVisible(true);
					}
				}
			}
		});
		if (AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("true"))
			checkDataButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/autorefresh-icon15.png")));
		else
			checkDataButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/refresh-icon15.png")));
		
		btnFolder = new JButton("");
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SynchroDial = new SynchronizingDialog("HectorBlaze");
				SynchroDial.setLocationRelativeTo(AnimeIndex.mainFrame);
				SynchroDial.setVisible(true);
				
			}
		});
		btnFolder.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/Folder.png")));
		btnFolder.setToolTipText("Aggiorna i dati dell'anime");
		btnFolder.setSize(new Dimension(30, 30));
		btnFolder.setPreferredSize(new Dimension(30, 30));
		btnFolder.setMinimumSize(new Dimension(30, 30));
		btnFolder.setMaximumSize(new Dimension(30, 30));
		GridBagConstraints gbc_btnFolder = new GridBagConstraints();
		gbc_btnFolder.anchor = GridBagConstraints.WEST;
		gbc_btnFolder.insets = new Insets(0, 0, 5, 5);
		gbc_btnFolder.gridx = 14;
		gbc_btnFolder.gridy = 9;
		add(btnFolder, gbc_btnFolder);
		checkDataButton.setSize(new Dimension(30, 30));
		checkDataButton.setPreferredSize(new Dimension(30, 30));
		checkDataButton.setMinimumSize(new Dimension(30, 30));
		checkDataButton.setMaximumSize(new Dimension(30, 30));
		GridBagConstraints gbc_checkDataButton = new GridBagConstraints();
		gbc_checkDataButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_checkDataButton.insets = new Insets(0, 0, 5, 5);
		gbc_checkDataButton.gridx = 15;
		gbc_checkDataButton.gridy = 9;
		add(checkDataButton, gbc_checkDataButton);

		JScrollPane noteScrollPane = new JScrollPane();
		noteScrollPane.setSize(new Dimension(350, 50));
		noteScrollPane.setMinimumSize(new Dimension(350, 50));
		noteScrollPane.setMaximumSize(new Dimension(350, 50));
		noteScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
		GridBagConstraints gbc_noteScrollPane = new GridBagConstraints();
		gbc_noteScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_noteScrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_noteScrollPane.gridwidth = 6;
		gbc_noteScrollPane.gridx = 10;
		gbc_noteScrollPane.gridy = 10;
		add(noteScrollPane, gbc_noteScrollPane);

		noteTextArea = new JTextArea();
		noteTextArea.setSize(new Dimension(350, 10));
		noteTextArea.setMinimumSize(new Dimension(350, 50));
		noteTextArea.setMaximumSize(new Dimension(350, 50));
		noteTextArea.setPreferredSize(new Dimension(350, 50));
		noteTextArea.setFont(AnimeIndex.segui.deriveFont(13f));
		noteScrollPane.setViewportView(noteTextArea);
		noteTextArea.setLineWrap(true);
		noteTextArea.setWrapStyleWord(true);

		finishedButton = new JButton("Concluso");
		finishedButton.setEnabled(false);
		finishedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					AnimeIndex.saveModifiedInformation();
					String name = lblAnimeName.getText();
					if (AnimeIndex.completedMap.containsKey(name))
						JOptionPane.showMessageDialog(AnimeInformation.this, "Anime già presente in \"Anime Completati\"", "Errore!", JOptionPane.ERROR_MESSAGE);
					else
					{
						if (AnimeIndex.filtro != 9 && !AnimeIndex.searchBar.getText().isEmpty() && !name.equals("Anime"))
							AnimeIndex.filterList.setSelectedValue(name, true);

						if (AnimeIndex.filtro != 9 && !name.equals("Anime"))
							MAMUtil.getJList().setSelectedValue(name, true);
						if (!AnimeIndex.searchBar.getText().isEmpty() && !name.equals("Anime"))
							MAMUtil.getJList().setSelectedValue(name, true);
						String type = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();

						SortedListModel model = null;
						JList list = null;
						TreeMap<String, AnimeData> map = null;
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
						oldData.getAnimeType();
						Boolean bd;
						if (oldData.getAnimeType().equalsIgnoreCase("blu-ray"))
							bd = true;
						else
							bd = false;
						AnimeData newData = new AnimeData(oldData.getTotalEpisode(), oldData.getTotalEpisode(), oldData.getFansub(), oldData.getNote(), oldData.getImageName(), "Concluso", oldData.getId(), oldData.getLinkName(), oldData.getLink(), oldData.getAnimeType(), oldData.getReleaseDate(), oldData.getFinishDate(), oldData.getDurationEp(), bd);
						FileManager.moveImage(oldData.getImagePath(type), "Completed", oldData.getImageName());
						map.remove(name);
						AnimeIndex.completedMap.put(name, newData);
						int index;
						int filterIndex;
						int searchIndex;
						if (AnimeIndex.filtro != 9)
						{
							filterIndex = AnimeIndex.filterList.getSelectedIndex();
							AnimeIndex.filterModel.removeElementAt(filterIndex);
						}
						else
							filterIndex = -1;

						if (!AnimeIndex.searchBar.getText().isEmpty())
						{
							searchIndex = AnimeIndex.searchList.getSelectedIndex();
							AnimeIndex.searchModel.removeElementAt(searchIndex);
						}
						else
							searchIndex = -1;

						index = list.getSelectedIndex();
						model.removeElementAt(index);
						list.clearSelection();
						if (AnimeIndex.filtro != 9 && !AnimeIndex.searchBar.getText().isEmpty())
							AnimeIndex.filterList.clearSelection();

						AnimeIndex.completedModel.addElement(name);
						if (!AnimeIndex.shiftsRegister.containsKey(name))
							AnimeIndex.shiftsRegister.put(name, type);
						if (AnimeIndex.sessionAddedAnime.contains(name))
							AnimeIndex.sessionAddedAnimeImagesShiftsRegister.put(name, MAMUtil.getImageFolderPath() + "Completed" + File.separator + name + ".png");
						if (AnimeIndex.exitDateMap.containsKey(name))
							AnimeIndex.exitDateMap.remove(name);
						AnimeIndex.animeInformation.minusButton.setEnabled(false);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(false);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
						AnimeIndex.animeInformation.finishedButton.setEnabled(false);

						if (AnimeIndex.filtro != 9 && filterIndex - 1 >= 0)
							AnimeIndex.filterList.setSelectedIndex(filterIndex - 1);
						else if (AnimeIndex.filtro != 9 && filterIndex - 1 < 0)
							AnimeIndex.animeInformation.setBlank();

						if (!AnimeIndex.searchBar.getText().isEmpty() && searchIndex - 1 >= 0)
							AnimeIndex.searchList.setSelectedIndex(searchIndex - 1);
						else if (!AnimeIndex.searchBar.getText().isEmpty() && searchIndex - 1 < 0)
							AnimeIndex.animeInformation.setBlank();

						if (AnimeIndex.filtro == 9 && AnimeIndex.searchBar.getText().isEmpty() && index - 1 >= 0)
							list.setSelectedIndex(index - 1);
						else if (AnimeIndex.filtro == 9 && AnimeIndex.searchBar.getText().isEmpty() && index - 1 < 0)
							AnimeIndex.animeInformation.setBlank();

					}
				}
			}
		});

		btnAnilistInfo = new JButton("Pi\u00F9 Informazioni");
		btnAnilistInfo.setToolTipText("Apri la pagina di Anilist dell'anime");
		btnAnilistInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					String anime = (String) MAMUtil.getJList().getSelectedValue();
					AnimeData data = (AnimeData) MAMUtil.getMap().get(anime);
					if (data.getId() != null && !data.getId().isEmpty())
					{
						String link = "https://anilist.co/anime/" + data.getId();
						if (!link.isEmpty())
							try
							{
								URI uriLink = new URI(link);
								Desktop.getDesktop().browse(uriLink);
							}
							catch (URISyntaxException e1)
							{
								JOptionPane.showMessageDialog(AnimeIndex.animeDialog, "Link non valido", "Errore", JOptionPane.ERROR_MESSAGE);
								btnOpen.setEnabled(false);
							}
							catch (IOException e1)
							{
								JOptionPane.showMessageDialog(AnimeIndex.animeDialog, "Link non valido", "Errore", JOptionPane.ERROR_MESSAGE);
							}
						else
							JOptionPane.showMessageDialog(AnimeIndex.animeDialog, "Link non valido", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		GridBagConstraints gbc_btnAnilistInfo = new GridBagConstraints();
		gbc_btnAnilistInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAnilistInfo.gridwidth = 2;
		gbc_btnAnilistInfo.insets = new Insets(0, 0, 0, 5);
		gbc_btnAnilistInfo.gridx = 1;
		gbc_btnAnilistInfo.gridy = 11;
		add(btnAnilistInfo, gbc_btnAnilistInfo);

		GridBagConstraints gbc_finishedButton = new GridBagConstraints();
		gbc_finishedButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_finishedButton.insets = new Insets(0, 0, 0, 5);
		gbc_finishedButton.gridx = 10;
		gbc_finishedButton.gridy = 11;
		add(finishedButton, gbc_finishedButton);

		addToSeeButton = new JButton("Concluso da Vedere");
		addToSeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (!releaseDateField.getText().trim().isEmpty() && releaseDateField.getText().trim().length() == 10 && releaseDateField.getText().trim().length() == 10 && !finishDateField.getText().trim().isEmpty() && finishDateField.getText().trim().length() == 10 && finishDateField.getText().trim().length() == 10)
				{
					AnimeIndex.saveModifiedInformation();
					String name = lblAnimeName.getText();
					if (AnimeIndex.completedToSeeMap.containsKey(name))
						JOptionPane.showMessageDialog(AnimeInformation.this, "Anime già presente in \"Completi da Vedere\"", "Errore!", JOptionPane.ERROR_MESSAGE);
					else
					{
						if (AnimeIndex.filtro != 9 && !AnimeIndex.searchBar.getText().isEmpty() && !name.equals("Anime"))
							AnimeIndex.filterList.setSelectedValue(name, true);

						if (AnimeIndex.filtro != 9 && !name.equals("Anime"))
							MAMUtil.getJList().setSelectedValue(name, true);
						if (!AnimeIndex.searchBar.getText().isEmpty() && !name.equals("Anime"))
							MAMUtil.getJList().setSelectedValue(name, true);
						String type = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();

						SortedListModel model = null;
						JList list = null;
						TreeMap<String, AnimeData> map = null;
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
						else if (type.equalsIgnoreCase("anime completati"))
						{
							model = AnimeIndex.completedModel;
							list = AnimeIndex.completedList;
							map = AnimeIndex.completedMap;
						}
						AnimeData oldData = map.get(name);
						Boolean bd;
						if (oldData.getAnimeType().equalsIgnoreCase("blu-ray"))
							bd = true;
						else
							bd = false;
						AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), oldData.getTotalEpisode(), oldData.getFansub(), oldData.getNote(), oldData.getImageName(), "Concluso", oldData.getId(), oldData.getLinkName(), oldData.getLink(), oldData.getAnimeType(), oldData.getReleaseDate(), oldData.getFinishDate(), oldData.getDurationEp(), bd);
						FileManager.moveImage(oldData.getImagePath(type), "Completed to See", oldData.getImageName());
						map.remove(name);
						AnimeIndex.completedToSeeMap.put(name, newData);

						int index;
						int filterIndex;
						int searchIndex;
						if (AnimeIndex.filtro != 9)
						{
							filterIndex = AnimeIndex.filterList.getSelectedIndex();
							AnimeIndex.filterModel.removeElementAt(filterIndex);
						}
						else
							filterIndex = -1;

						if (!AnimeIndex.searchBar.getText().isEmpty())
						{
							searchIndex = AnimeIndex.searchList.getSelectedIndex();
							AnimeIndex.searchModel.removeElementAt(searchIndex);
						}
						else
							searchIndex = -1;

						index = list.getSelectedIndex();
						model.removeElementAt(index);
						list.clearSelection();
						if (AnimeIndex.filtro != 9 && !AnimeIndex.searchBar.getText().isEmpty())
							AnimeIndex.filterList.clearSelection();

						AnimeIndex.completedToSeeModel.addElement(name);
						if (!AnimeIndex.shiftsRegister.containsKey(name))
							AnimeIndex.shiftsRegister.put(name, type);
						if (AnimeIndex.sessionAddedAnime.contains(name))
							AnimeIndex.sessionAddedAnimeImagesShiftsRegister.put(name, MAMUtil.getImageFolderPath() + "Completed to See" + File.separator + name + ".png");
						if (AnimeIndex.exitDateMap.containsKey(name))
							AnimeIndex.exitDateMap.remove(name);
						AnimeIndex.animeInformation.minusButton.setEnabled(true);
						AnimeIndex.animeInformation.currentEpisodeField.setEnabled(true);
						AnimeIndex.animeInformation.totalEpisodeText.setEnabled(false);
						AnimeIndex.animeInformation.addToSeeButton.setEnabled(false);

						if (AnimeIndex.filtro != 9 && filterIndex - 1 >= 0)
							AnimeIndex.filterList.setSelectedIndex(filterIndex - 1);
						else if (AnimeIndex.filtro != 9 && filterIndex - 1 < 0)
							AnimeIndex.animeInformation.setBlank();

						if (!AnimeIndex.searchBar.getText().isEmpty() && searchIndex - 1 >= 0)
							AnimeIndex.searchList.setSelectedIndex(searchIndex - 1);
						else if (!AnimeIndex.searchBar.getText().isEmpty() && searchIndex - 1 < 0)
							AnimeIndex.animeInformation.setBlank();

						if (AnimeIndex.filtro == 9 && AnimeIndex.searchBar.getText().isEmpty() && index - 1 >= 0)
							list.setSelectedIndex(index - 1);
						else if (AnimeIndex.filtro == 9 && AnimeIndex.searchBar.getText().isEmpty() && index - 1 < 0)
							AnimeIndex.animeInformation.setBlank();
					}
				}
			}
		});
		GridBagConstraints gbc_addToSeeButton = new GridBagConstraints();
		gbc_addToSeeButton.anchor = GridBagConstraints.EAST;
		gbc_addToSeeButton.gridwidth = 5;
		gbc_addToSeeButton.insets = new Insets(0, 0, 0, 5);
		gbc_addToSeeButton.gridx = 11;
		gbc_addToSeeButton.gridy = 11;
		add(addToSeeButton, gbc_addToSeeButton);
		
		LineBorder dragBorder = new LineBorder(new Color(40, 40, 40), 2, true);
		new FileDrop(AnimeIndex.mainFrame, dragBorder, new FileDrop.Listener()
		{   public void filesDropped( File[] files )
			{   
		   		// handle file drop
		    }   // end filesDropped
	    }); // end FileDrop.Listener
	 }

	public void setBlank()
	{
		MAMUtil.getJList().clearSelection();
		if (MAMUtil.getJList().isSelectionEmpty())
		{
			lblAnimeName.setText("Anime");
			currentEpisodeField.setText("??");
			currentEpisodeField.setEnabled(false);
			totalEpisodeText.setText("??");
			totalEpisodeText.setEnabled(false);
			noteTextArea.setText("");
			noteTextArea.setEnabled(false);
			exitDaycomboBox.setSelectedItem("?????");
			exitDaycomboBox.setEnabled(false);
			setLinkButton.setText("Imposta Link");
			setLinkButton.setEnabled(false);
			typeComboBox.setSelectedItem("?????");
			typeComboBox.setEnabled(false);
			releaseDateField.setText("??/??/????");
			releaseDateField.setEnabled(false);
			finishDateField.setText("??/??/????");
			finishDateField.setEnabled(false);
			durationField.setText("?? min");
			durationField.setEnabled(false);
			minusButton.setEnabled(false);
			plusButton.setEnabled(false);
			addToSeeButton.setEnabled(false);
			btnAnilistInfo.setEnabled(false);
			fansubButton.setEnabled(false);
			fansubComboBox.setEnabled(false);
			btnOpen.setEnabled(false);
			finishedButton.setEnabled(false);
			fansubComboBox.setSelectedItem("?????");
			checkDataButton.setEnabled(false);
			btnFolder.setEnabled(false);
			AnimeIndex.deleteButton.setEnabled(false);

			BufferedImage image = null;
			try
			{
				File img = new File(MAMUtil.getDefaultImageFolderPath() + File.separator + "default.png");
				if (img.isFile())
					image = ImageIO.read(img);
				else
					image = ImageIO.read(ClassLoader.getSystemResource("image/default.png"));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				MAMUtil.writeLog(e1);
			}
			animeImage.setIcon(new ImageIcon(image));
		}
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
		if (AnimeIndex.getFansubList() != null)
			fansubComboBox.setModel(new DefaultComboBoxModel(AnimeIndex.getFansubList()));
	}

	public void setNote(String note)
	{
		noteTextArea.setText(note);
	}

	public void setImage(String path)
	{
		try
		{
			BufferedImage image = null;
			if (path.equals("deafult"))
			{
				File img = new File(MAMUtil.getDefaultImageFolderPath() + File.separator + "default.png");
				if (img.isFile())
					image = ImageIO.read(img);
				else
					image = ImageIO.read(ClassLoader.getSystemResource("image/default.png"));
			}
			else
				image = ImageIO.read(new File(path));
			animeImage.setIcon(new ImageIcon(image));
		}
		catch (IIOException e)
		{
			System.out.println("Immagine mancante");
		}
		catch (Exception e)
		{

			e.printStackTrace();
			MAMUtil.writeLog(e);
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

	public void setDurationEp(String durationEp)
	{
		durationField.setText(durationEp);
	}

	public void setReleaseDate(String releaseDate)
	{
		releaseDateField.setText(releaseDate);
	}

	public void setFinishDate(String finishDate)
	{
		finishDateField.setText(finishDate);
	}

	public void setType(String animeType)
	{
		typeComboBox.setSelectedItem(animeType);
	}

	public void setlinkName(String linkName)
	{
		// if (linkName != null && !linkName.isEmpty())
		setLinkButton.setText(linkName);
	}

}
