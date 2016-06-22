package util;

import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import main.AnimeIndex;
import util.window.SetExclusionDialog;

public class UtilEvent
{

	public static MouseAdapter exclusionPopUpMenu()
	{
		MouseAdapter mouse = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e))
					if (e.getSource() instanceof JList)
					{
						JList list = (JList) e.getSource();
						int row = list.locationToIndex(e.getPoint());
						Rectangle bound = list.getCellBounds(row, row);
						if (bound.contains(e.getPoint()))
						{
							list.setSelectedIndex(row);
							JPopupMenu menu = new JPopupMenu();
							
							JMenuItem copy = new JMenuItem("Copia");
							copy.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/copy-icon.png")));
							copy.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e)
								{
									Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
									clpbrd.setContents(new StringSelection(AnimeIndex.animeInformation.lblAnimeName.getText()), null);
								}
							});
							
							JMenuItem add = new JMenuItem("Aggiungi alle Esclusioni");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] exc = { true, true, true, true, true, true };
									AnimeIndex.exclusionAnime.put(AnimeIndex.animeInformation.lblAnimeName.getText(), exc);
									AnimeIndex.animeInformation.selectExcludedAnimeAtWindowOpened = true;
									AnimeIndex.preferenceDialog.exclusionDialog = new SetExclusionDialog();
									AnimeIndex.preferenceDialog.exclusionDialog.setLocationRelativeTo(AnimeIndex.mainPanel);
									AnimeIndex.preferenceDialog.exclusionDialog.setVisible(true);
								}
							});
							JMenuItem remove = new JMenuItem("Rimuovi dalle Esclusioni");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									AnimeIndex.exclusionAnime.remove(list.getSelectedValue());
								}
							});
							if (AnimeIndex.exclusionAnime.containsKey(list.getSelectedValue()))
								menu.add(remove);
							else
								menu.add(add);
							menu.add(copy);
							menu.show(list, e.getX(), e.getY());
						}
					}

					else if (e.getSource().equals(AnimeIndex.animeInformation.animeImage))
					{
						JList list = MAMUtil.getJList();
						if (!list.isSelectionEmpty())
						{
							String name = (String) list.getSelectedValue();
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Escludi Immagine dall'aggiornamento");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									if (AnimeIndex.exclusionAnime.containsKey(name))
									{
										boolean[] bool = AnimeIndex.exclusionAnime.get(name);
										bool[0] = true;
										AnimeIndex.exclusionAnime.put(name, bool);
									}
									else
									{
										boolean[] bool = { true, false, false, false, false, false };
										AnimeIndex.exclusionAnime.put(name, bool);
									}
								}
							});
							JMenuItem remove = new JMenuItem("Includi Immagine nell'aggiornamento");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] bool = AnimeIndex.exclusionAnime.get(name);
									bool[0] = false;
									if (isAllFalse(bool))
										AnimeIndex.exclusionAnime.remove(name);
									else
										AnimeIndex.exclusionAnime.put(name, bool);
								}
							});
							if (AnimeIndex.exclusionAnime.get(name) != null && AnimeIndex.exclusionAnime.get(name)[0])
								menu.add(remove);
							else
								menu.add(add);
							menu.show(AnimeIndex.animeInformation.animeImage, e.getX(), e.getY());
						}
					}

					else if (e.getSource().equals(AnimeIndex.animeInformation.totalEpisodeText))
					{
						JList list = MAMUtil.getJList();
						if (!list.isSelectionEmpty())
						{
							String name = (String) list.getSelectedValue();
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Escludi Episodi Totali dall'aggiornamento");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									if (AnimeIndex.exclusionAnime.containsKey(name))
									{
										boolean[] bool = AnimeIndex.exclusionAnime.get(name);
										bool[1] = true;
										AnimeIndex.exclusionAnime.put(name, bool);
									}
									else
									{
										boolean[] bool = { false, true, false, false, false, false };
										AnimeIndex.exclusionAnime.put(name, bool);
									}
								}
							});
							JMenuItem remove = new JMenuItem("Includi Episodi Totali nell'aggiornamento");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] bool = AnimeIndex.exclusionAnime.get(name);
									bool[1] = false;
									if (isAllFalse(bool))
										AnimeIndex.exclusionAnime.remove(name);
									else
										AnimeIndex.exclusionAnime.put(name, bool);
								}
							});
							if (AnimeIndex.exclusionAnime.get(name) != null && AnimeIndex.exclusionAnime.get(name)[1])
								menu.add(remove);
							else
								menu.add(add);
							menu.show(AnimeIndex.animeInformation.totalEpisodeText, e.getX(), e.getY());
						}
					}

					else if (e.getSource().equals(AnimeIndex.animeInformation.durationField))
					{
						JList list = MAMUtil.getJList();
						if (!list.isSelectionEmpty())
						{
							String name = (String) list.getSelectedValue();
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Escludi Durata dall'aggiornamento");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									if (AnimeIndex.exclusionAnime.containsKey(name))
									{
										boolean[] bool = AnimeIndex.exclusionAnime.get(name);
										bool[2] = true;
										AnimeIndex.exclusionAnime.put(name, bool);
									}
									else
									{
										boolean[] bool = { false, false, true, false, false, false };
										AnimeIndex.exclusionAnime.put(name, bool);
									}
								}
							});
							JMenuItem remove = new JMenuItem("Includi Durata nell'aggiornamento");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] bool = AnimeIndex.exclusionAnime.get(name);
									bool[2] = false;
									if (isAllFalse(bool))
										AnimeIndex.exclusionAnime.remove(name);
									else
										AnimeIndex.exclusionAnime.put(name, bool);
								}
							});
							if (AnimeIndex.exclusionAnime.get(name) != null && AnimeIndex.exclusionAnime.get(name)[2])
								menu.add(remove);
							else
								menu.add(add);
							menu.show(AnimeIndex.animeInformation.durationField, e.getX(), e.getY());
						}
					}

					else if (e.getSource().equals(AnimeIndex.animeInformation.releaseDateField))
					{
						JList list = MAMUtil.getJList();
						if (!list.isSelectionEmpty())
						{
							String name = (String) list.getSelectedValue();
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Escludi Data di Inizio dall'aggiornamento");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									if (AnimeIndex.exclusionAnime.containsKey(name))
									{
										boolean[] bool = AnimeIndex.exclusionAnime.get(name);
										bool[3] = true;
										AnimeIndex.exclusionAnime.put(name, bool);
									}
									else
									{
										boolean[] bool = { false, false, false, true, false, false };
										AnimeIndex.exclusionAnime.put(name, bool);
									}
								}
							});
							JMenuItem remove = new JMenuItem("Includi Data di Inizio nell'aggiornamento");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] bool = AnimeIndex.exclusionAnime.get(name);
									bool[3] = false;
									if (isAllFalse(bool))
										AnimeIndex.exclusionAnime.remove(name);
									else
										AnimeIndex.exclusionAnime.put(name, bool);
								}
							});
							if (AnimeIndex.exclusionAnime.get(name) != null && AnimeIndex.exclusionAnime.get(name)[3])
								menu.add(remove);
							else
								menu.add(add);
							menu.show(AnimeIndex.animeInformation.releaseDateField, e.getX(), e.getY());
						}
					}
					else if (e.getSource().equals(AnimeIndex.animeInformation.finishDateField))
					{
						JList list = MAMUtil.getJList();
						if (!list.isSelectionEmpty())
						{
							String name = (String) list.getSelectedValue();
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Escludi Data di Fine dall'aggiornamento");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									if (AnimeIndex.exclusionAnime.containsKey(name))
									{
										boolean[] bool = AnimeIndex.exclusionAnime.get(name);
										bool[4] = true;
										AnimeIndex.exclusionAnime.put(name, bool);
									}
									else
									{
										boolean[] bool = { false, false, false, false, true, false };
										AnimeIndex.exclusionAnime.put(name, bool);
									}
								}
							});
							JMenuItem remove = new JMenuItem("Includi Data di Fine nell'aggiornamento");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] bool = AnimeIndex.exclusionAnime.get(name);
									bool[4] = false;
									if (isAllFalse(bool))
										AnimeIndex.exclusionAnime.remove(name);
									else
										AnimeIndex.exclusionAnime.put(name, bool);
								}
							});
							if (AnimeIndex.exclusionAnime.get(name) != null && AnimeIndex.exclusionAnime.get(name)[4])
								menu.add(remove);
							else
								menu.add(add);
							menu.show(AnimeIndex.animeInformation.finishDateField, e.getX(), e.getY());
						}
					}
					else if (e.getSource().equals(AnimeIndex.animeInformation.typeComboBox))
					{
						JList list = MAMUtil.getJList();
						if (!list.isSelectionEmpty())
						{
							String name = (String) list.getSelectedValue();
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Escludi Tipo dall'aggiornamento");
							add.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									if (AnimeIndex.exclusionAnime.containsKey(name))
									{
										boolean[] bool = AnimeIndex.exclusionAnime.get(name);
										bool[5] = true;
										AnimeIndex.exclusionAnime.put(name, bool);
									}
									else
									{
										boolean[] bool = { false, false, false, false, false, true };
										AnimeIndex.exclusionAnime.put(name, bool);
									}
								}
							});
							JMenuItem remove = new JMenuItem("Includi Tipo nell'aggiornamento");
							remove.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e)
								{
									boolean[] bool = AnimeIndex.exclusionAnime.get(name);
									bool[5] = false;
									if (isAllFalse(bool))
										AnimeIndex.exclusionAnime.remove(name);
									else
										AnimeIndex.exclusionAnime.put(name, bool);
								}
							});
							if (AnimeIndex.exclusionAnime.get(name) != null && AnimeIndex.exclusionAnime.get(name)[5])
								menu.add(remove);
							else
								menu.add(add);
							menu.show(AnimeIndex.animeInformation.typeComboBox, e.getX(), e.getY());
						}
					}
			}
		};
		return mouse;
	}

	public static KeyAdapter cancDeleteAnime()
	{
		KeyAdapter key = new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e)
			{
				int key = e.getKeyChar();
				if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE)
				{
					SortedListModel model = MAMUtil.getModel();
					SortedListModel secondModel = null;
					SortedListModel thirdModel = null;
					JList list = null;
					if (AnimeIndex.searchBar.getText().isEmpty() && AnimeIndex.filtro == 9)
						list = MAMUtil.getJList();
					else if (AnimeIndex.searchBar.getText().isEmpty() && AnimeIndex.filtro != 9)
					{
						list = AnimeIndex.filterList;
						secondModel = AnimeIndex.filterModel;
					}
					else if (!AnimeIndex.searchBar.getText().isEmpty() && AnimeIndex.filtro != 9)
					{
						list = AnimeIndex.searchList;
						secondModel = AnimeIndex.searchModel;
						thirdModel = AnimeIndex.filterModel;
					}
					else
					{
						list = AnimeIndex.searchList;
						secondModel = AnimeIndex.searchModel;
					}

					if (!list.isSelectionEmpty())
					{
						String listName = MAMUtil.getList();
						TreeMap<String, AnimeData> map = MAMUtil.getMap();
						ArrayList<String> arrayList = MAMUtil.getDeletedAnimeArray();
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
						if (AnimeIndex.sessionAddedAnime.contains(name))
							AnimeIndex.sessionAddedAnime.remove(name);

						if (!list.isSelectionEmpty())
							AnimeIndex.deleteButton.setEnabled(true);
						else
						{
							AnimeIndex.deleteButton.setEnabled(false);
							AnimeIndex.animeInformation.setBlank();
						}
					}
					else
						AnimeIndex.animeInformation.setBlank();
				}
			}
		};

		return key;
	}
	
	public static MouseAdapter lblAnimeNamePopUpMenu()
	{
		MouseAdapter mouse = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e))
				{
					if(e.getSource().equals(AnimeIndex.animeInformation.lblAnimeName));
					{
						if (!AnimeIndex.animeInformation.lblAnimeName.getText().equalsIgnoreCase("Anime"))
						{
							JPopupMenu menu = new JPopupMenu();
							JMenuItem add = new JMenuItem("Copia");
							add.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/copy-icon.png")));
							add.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e)
								{
									Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
									clpbrd.setContents(new StringSelection(AnimeIndex.animeInformation.lblAnimeName.getText()), null);
								}
							});
							JMenuItem addd = new JMenuItem("Cerca in Rete");
							addd.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/net.png")));
							addd.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e)
								{
									try
									{
										String link = "https://www.google.it/search?q=" + URLEncoder.encode(AnimeIndex.animeInformation.lblAnimeName.getText(), "UTF-8");
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
							menu.add(add);
							menu.add(addd);
							menu.show(AnimeIndex.animeInformation.lblAnimeName, e.getX(), e.getY());
						}
					}
				}
			}
		};
		return mouse;
	}

	private static boolean isAllFalse(boolean[] bool)
	{
		boolean allFalse = false;
		int falseNumber = 0;
		for (int i = 0; i < bool.length; i++)
			if (bool[i] == false)
				falseNumber++;
				
		if (falseNumber == bool.length)
			allFalse = true;
		return allFalse;
	}
}
