package util.window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.MAMUtil;
import util.SortedListModel;
import util.SuggestionHelper;
import util.SuggestionTaskPane;
import util.task.SuggestionFetcherTask;

public class SuggestionDialog extends JDialog
{

	private SuggestionTaskPane suggestionOne = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionTwo = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionThree = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionFuor = new SuggestionTaskPane();
	private SuggestionTaskPane suggestionFive = new SuggestionTaskPane();
	private SuggestionTaskPane[] taskPaneArray = { suggestionOne, suggestionTwo, suggestionThree, suggestionFuor, suggestionFive };
	private JButton btnClose;
	private static String[] linkArray = new String[5];
	private static String[] idArray = new String[5];
	public SuggestionWaitDialog waitDialog = new SuggestionWaitDialog();
	private JButton btnOpen;
	private JButton btnAdd;

	/**
	 * Create the dialog.
	 */
	public SuggestionDialog()
	{
		super(AnimeIndex.frame, true);
		setResizable(false);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e)
			{
				btnClose.requestFocusInWindow();
				waitDialog.setLocationRelativeTo(SuggestionDialog.this);
				waitDialog.setVisible(true);
				SuggestionFetcherTask task = new SuggestionFetcherTask();
				task.execute();
			}
		});
		setTitle("Anime Consigliati");
		setBounds(100, 100, 470, 210);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(50);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			btnAdd = new JButton("Aggiungi");
			btnAdd.setEnabled(false);
			btnAdd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					int taskPaneNumber = getSelectedTaskPane();
					String id = idArray[taskPaneNumber];
					String[] listArray = { "Anime Completati", "Anime in Corso", "OAV", "Film", "Completi Da Vedere", "Wishlist" };
					String list = (String) JOptionPane.showInputDialog(SuggestionDialog.this, "A quale lista vuoi aggiungerlo", "Aggiungi a...", JOptionPane.QUESTION_MESSAGE, null, listArray, listArray[0]);
					if (list != null)
						if (list.equalsIgnoreCase("wishlist"))
							addToWishlist(id);
						else
							addAnimeToList(list, id);
				}
			});
			buttonPane.add(btnAdd);

			btnOpen = new JButton("Apri");
			btnOpen.setEnabled(false);
			btnOpen.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					int taskPaneNumber = getSelectedTaskPane();
					try
					{
						URI uriLink = new URI(linkArray[taskPaneNumber]);
						Desktop.getDesktop().browse(uriLink);
					}
					catch (Exception ex)
					{

					}
				}
			});
			buttonPane.add(btnOpen);

			btnClose = new JButton("Cronologia");
			btnClose.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					JTextArea textArea = new JTextArea(20, 25);
					try
					{
					    textArea.setText(SuggestionHelper.getOldSuggestions(SuggestionHelper.getData()));
					}
					catch (Exception e1)
					{
						MAMUtil.writeLog(e1);
						e1.printStackTrace();
					}
					textArea.setEditable(false);
					textArea.setOpaque(false);
					JScrollPane scrollPane = new JScrollPane(textArea);
				    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
					scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
				    JOptionPane.showMessageDialog(AnimeIndex.mainFrame, scrollPane, "Cronologia Anime Consigliati", JOptionPane.PLAIN_MESSAGE);
				}
			});
			buttonPane.add(btnClose);
		}

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
			scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			{
				JPanel panel_1 = new JPanel();
				scrollPane.setViewportView(panel_1);
				GridBagLayout gbl_panel_1 = new GridBagLayout();
				gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
				gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
				gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
				gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
				panel_1.setLayout(gbl_panel_1);

				suggestionOne.addPropertyChangeListener(OpenCloseListener(1));
				GridBagConstraints gbc_suggestionOne = new GridBagConstraints();
				gbc_suggestionOne.insets = new Insets(0, 0, 5, 5);
				gbc_suggestionOne.fill = GridBagConstraints.HORIZONTAL;
				gbc_suggestionOne.gridx = 0;
				gbc_suggestionOne.gridy = 0;
				panel_1.add(suggestionOne, gbc_suggestionOne);

				suggestionTwo.addPropertyChangeListener(OpenCloseListener(2));
				GridBagConstraints gbc_suggestionTwo = new GridBagConstraints();
				gbc_suggestionTwo.insets = new Insets(0, 0, 5, 5);
				gbc_suggestionTwo.fill = GridBagConstraints.HORIZONTAL;
				gbc_suggestionTwo.gridx = 0;
				gbc_suggestionTwo.gridy = 1;
				panel_1.add(suggestionTwo, gbc_suggestionTwo);

				suggestionThree.addPropertyChangeListener(OpenCloseListener(3));

				Component horizontalStrut = Box.createHorizontalStrut(0);
				GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
				gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_horizontalStrut.gridx = 1;
				gbc_horizontalStrut.gridy = 1;
				panel_1.add(horizontalStrut, gbc_horizontalStrut);
				GridBagConstraints gbc_suggestionThree = new GridBagConstraints();
				gbc_suggestionThree.insets = new Insets(0, 0, 5, 5);
				gbc_suggestionThree.fill = GridBagConstraints.HORIZONTAL;
				gbc_suggestionThree.gridx = 0;
				gbc_suggestionThree.gridy = 2;
				panel_1.add(suggestionThree, gbc_suggestionThree);

				suggestionFuor.addPropertyChangeListener(OpenCloseListener(4));
				GridBagConstraints gbc_suggestionFuor = new GridBagConstraints();
				gbc_suggestionFuor.insets = new Insets(0, 0, 5, 5);
				gbc_suggestionFuor.fill = GridBagConstraints.HORIZONTAL;
				gbc_suggestionFuor.gridx = 0;
				gbc_suggestionFuor.gridy = 3;
				panel_1.add(suggestionFuor, gbc_suggestionFuor);

				suggestionFive.addPropertyChangeListener(OpenCloseListener(5));
				GridBagConstraints gbc_suggestionFive = new GridBagConstraints();
				gbc_suggestionFive.insets = new Insets(0, 0, 0, 5);
				gbc_suggestionFive.fill = GridBagConstraints.HORIZONTAL;
				gbc_suggestionFive.gridx = 0;
				gbc_suggestionFive.gridy = 4;
				panel_1.add(suggestionFive, gbc_suggestionFive);

			}
		}
	}

	private void addToWishlist(String id)
	{
		SuggestionDialog.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if (AnimeIndex.wishlistDialog.wishListModel.contains("Nessun Anime Corrispondente"))
			AnimeIndex.wishlistDialog.wishListModel.removeElement("Nessun Anime Corrispondente");

		try
		{
			ConnectionManager.ConnectAndGetToken();
		}
		catch (ConnectException | UnknownHostException e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		String data = ConnectionManager.parseAnimeData(Integer.parseInt(id));
		String name = ConnectionManager.getAnimeData("title_romaji", data);

		AnimeIndex.wishlistDialog.wishListModel.addElement(name);
		AnimeIndex.wishlistMap.put(name, Integer.parseInt(id));
		AnimeIndex.wishlistDialog.wishlist.setEnabled(true);
		AnimeIndex.wishlistDialog.searchList.setEnabled(true);
		SuggestionDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		// SuggestionDialog.this.dispose();
	}

	private PropertyChangeListener OpenCloseListener(int suggestionPaneNumber)
	{
		PropertyChangeListener propListener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if (evt.getPropertyName().equalsIgnoreCase("collapsed"))
				{
					if (!taskPaneArray[suggestionPaneNumber - 1].isCollapsed())
					{
						btnAdd.setEnabled(true);
						btnOpen.setEnabled(true);
						for (SuggestionTaskPane taskPane : taskPaneArray)
							if (!taskPane.equals(taskPaneArray[suggestionPaneNumber - 1]))
								taskPane.setCollapsed(true);
					}

					if (taskPaneArray[0].isCollapsed() && taskPaneArray[1].isCollapsed() && taskPaneArray[2].isCollapsed() && taskPaneArray[3].isCollapsed() && taskPaneArray[4].isCollapsed())
					{
						btnAdd.setEnabled(false);
						btnOpen.setEnabled(false);
					}
				}
			}
		};
		return propListener;
	}

	public void storeSuggestion(int suggestionNumber, String data)
	{
		try
		{
			String name = SuggestionHelper.getSuggestion(suggestionNumber + 1, data);
			String description = SuggestionHelper.getDescription(suggestionNumber + 1, data);
			String link = SuggestionHelper.getLink(suggestionNumber + 1, data);
			String id = SuggestionHelper.getId(suggestionNumber + 1, data);

			taskPaneArray[suggestionNumber].setTitle(name);
			taskPaneArray[suggestionNumber].setText(description);
			linkArray[suggestionNumber] = link;
			idArray[suggestionNumber] = id;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
	}

	private int getSelectedTaskPane()
	{
		for (int i = 0; i < taskPaneArray.length; i++)
			if (!taskPaneArray[i].isCollapsed())
				return i;
		return -1;
	}

	private void addAnimeToList(String listName, String id)
	{
		SuggestionDialog.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try
		{
			ConnectionManager.ConnectAndGetToken();
		}
		catch (ConnectException | UnknownHostException e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		String animeData = ConnectionManager.parseAnimeData(Integer.parseInt(id));
		String name = ConnectionManager.getAnimeData("title_romaji", animeData);
		String totEp = ConnectionManager.getAnimeData("total_episodes", animeData);
		String currentEp = "1";
		String fansub = "";
		String animeType = ConnectionManager.getAnimeData("type", animeData);
		String releaseDate = ConnectionManager.getAnimeData("start_date", animeData);
		String finishDate = ConnectionManager.getAnimeData("end_date", animeData);
		String durationEp = ConnectionManager.getAnimeData("duration", animeData);
		if (totEp != null && !totEp.isEmpty())
			if (totEp.equals("null") || totEp.equals("0"))
				totEp = "??";

		if (durationEp != null && !durationEp.isEmpty())
			if (durationEp.equals("null") || durationEp.equals("0"))
				durationEp = "?? min";
			else
				durationEp += " min";
		if (releaseDate != null && !releaseDate.isEmpty())
			if (releaseDate.equals("null"))
				releaseDate = "??/??/????";
			else if (releaseDate.length() == 4)
				releaseDate = "??/??/" + releaseDate;
			else if (releaseDate.length() == 7)
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
		if (finishDate != null && !finishDate.isEmpty())
		{
			if (finishDate.equals("null"))
				finishDate = "??/??/????";
			else if (finishDate.length() == 4)
				finishDate = "??/??/" + finishDate;
			else if (finishDate.length() == 7)
			{
				String monthEnd = finishDate.substring(5, 7);
				String yearEnd = finishDate.substring(0, 4);
				finishDate = "??/" + monthEnd + "/" + yearEnd;
			}
			else if (finishDate.length() > 7)
			{
				String dayEnd = finishDate.substring(8, 10);
				String monthEnd = finishDate.substring(5, 7);
				String yearEnd = finishDate.substring(0, 4);
				finishDate = dayEnd + "/" + monthEnd + "/" + yearEnd;
			}
			if (totEp.equals("1"))
				finishDate = releaseDate;
		}
		String exitDay = "?????";
		if (listName.equalsIgnoreCase("completi da vedere"))
			exitDay = "Concluso";
		if (currentEp.equals(totEp))
			AnimeIndex.animeInformation.plusButton.setEnabled(false);
		if (listName.equalsIgnoreCase("anime completati"))
		{
			currentEp = totEp;
			exitDay = "Concluso";
		}
		String imageName = AddAnimeDialog.addSaveImage(name, Integer.parseInt(id), listName);
		AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png", exitDay, id, "", "", animeType, releaseDate, finishDate, durationEp, false);
		JList list = AddAnimeDialog.getJList(listName);
		SortedListModel model = AddAnimeDialog.getModel(listName);
		TreeMap<String, AnimeData> map = AddAnimeDialog.getMap(listName);
		if (!map.containsKey(name))
		{
			AnimeIndex.shouldUpdate = false;
			AnimeIndex.sessionAddedAnime.add(name);
			map.put(name, data);
			model.addElement(name);
			if (AddAnimeDialog.getDeletedArrayList(listName).contains(map.get(name).getImagePath(listName)))
				AddAnimeDialog.getDeletedArrayList(listName).remove(map.get(name).getImagePath(listName));
			AddAnimeDialog.getArrayList(listName).add(map.get(name).getImagePath(listName));
			AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
			list.clearSelection();
			list.setSelectedValue(name, true);
			AnimeIndex.shouldUpdate = true;

		}
		SuggestionDialog.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		// SuggestionDialog.this.dispose();
	}
}
