package util;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import main.AnimeIndex;

public class UtilEvent
{
	public static MouseAdapter exclusionPopUpMenu()
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
	                        AnimeIndex.exclusionAnime.add((String)list.getSelectedValue());
	                    }
	                });
	                JMenuItem remove = new JMenuItem("Rimuovi dalle Esclusioni");
	                remove.addActionListener(new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                    	AnimeIndex.exclusionAnime.remove((String)list.getSelectedValue());
	                    }
	                });
	                if (AnimeIndex.exclusionAnime.contains((String)list.getSelectedValue()))
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
	
	public static KeyAdapter cancDeleteAnime()
	{
		KeyAdapter key = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int key = e.getKeyChar();
				if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE)
				{
					String type = (String) AnimeIndex.animeTypeComboBox.getSelectedItem();

					SortedListModel model = AnimeIndex.getModel();
					SortedListModel secondModel = null;
					SortedListModel thirdModel = null;
					JList list = null;
					if (AnimeIndex.searchBar.getText().isEmpty() && AnimeIndex.filtro == 9)
						list = AnimeIndex.getJList();
					else if (AnimeIndex.searchBar.getText().isEmpty() && AnimeIndex.filtro != 9)
					{
						list = AnimeIndex.filterList;
						secondModel = AnimeIndex.filterModel;
					}
					else if(!AnimeIndex.searchBar.getText().isEmpty() && AnimeIndex.filtro != 9)
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
						String listName = AnimeIndex.getList();
						TreeMap<String,AnimeData> map = AnimeIndex.getMap();
						ArrayList<String> arrayList = AnimeIndex.getDeletedAnimeArray();
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
						if(AnimeIndex.sessionAddedAnime.contains(name))
							AnimeIndex.sessionAddedAnime.remove(name);
						
						if (!list.isSelectionEmpty())					
							AnimeIndex.deleteButton.setEnabled(true);
						else
						{
							AnimeIndex.deleteButton.setEnabled(false);
							AnimeIndex.animeInformation.setBlank();
						}
					}
				}
					
			}
		};
		
		return key;
	}
}
