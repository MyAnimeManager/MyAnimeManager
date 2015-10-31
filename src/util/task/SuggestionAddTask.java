package util.task;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.TreeMap;

import javax.swing.JList;
import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.SortedListModel;
import util.window.AddAnimeDialog;


public class SuggestionAddTask extends SwingWorker {
	
	String list;
	String id;
	
	public SuggestionAddTask(String list, String id)
	{
		this.list = list;
		this.id = id;
	}

	@Override
	protected Object doInBackground() throws Exception
	{
		addAnimeToList(list,  id);
		return null;
	}

	private void addAnimeToList(String listName, String id)
	{
		try
		{
			ConnectionManager.ConnectAndGetToken();
		}
		catch (ConnectException | UnknownHostException e)
		{
			e.printStackTrace();
		}
		String dataAni = ConnectionManager.parseAnimeData(Integer.parseInt(id));
		String name = ConnectionManager.getAnimeData("title_romaji", dataAni);
		String totEp = ConnectionManager.getAnimeData("total_episodes", dataAni);
		String currentEp = "1";
		String fansub = "";
		String link = ""; 
		String animeType = ConnectionManager.getAnimeData("type", dataAni);
		String releaseDate = ConnectionManager.getAnimeData("start_date", dataAni);
		String finishDate = ConnectionManager.getAnimeData("end_date", dataAni);
		String durationEp = ConnectionManager.getAnimeData("duration", dataAni);
		if(totEp != null && !totEp.isEmpty())
		{
			if(totEp.equals("null")||totEp.equals("0"))
				totEp = "??";
		}
		
		if (durationEp != null && !durationEp.isEmpty())
		{
			if(durationEp.equals("null")||durationEp.equals("0"))
				durationEp = "?? min";
			else
				durationEp += " min";
		}
		if (releaseDate != null && !releaseDate.isEmpty())
		{
			if (releaseDate.equals("null"))
				releaseDate = "??/??/????";
			else if(releaseDate.length()==4)
				releaseDate = "??/??/" + releaseDate;
			else if(releaseDate.length()==7)
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
		}
		if (finishDate != null && !finishDate.isEmpty())
		{
			if (finishDate.equals("null"))
				finishDate = "??/??/????";
			else if(finishDate.length()==4)
				finishDate = "??/??/" + finishDate;
			else if(finishDate.length()==7)
			{
				String monthEnd = finishDate.substring(5, 7);
				String yearEnd = finishDate.substring(0, 4);
				finishDate = "??/" + monthEnd + "/" + yearEnd;
			}
			else if (finishDate.length() > 7)
			{
				String dayEnd = finishDate.substring(8, 10);
				String monthEnd= finishDate.substring(5, 7);
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
		String imageName = AddAnimeDialog.addSaveImage(name, dataAni, listName);
		AnimeData data = new AnimeData(currentEp, totEp, fansub, "", imageName + ".png" , exitDay, id, 
				"", "", animeType, releaseDate, finishDate, durationEp, false); 
		JList list = AddAnimeDialog.getJList(listName);
		SortedListModel model = AddAnimeDialog.getModel(listName);
		TreeMap map = AddAnimeDialog.getMap(listName);
		if (!map.containsKey(name))
		{
			AnimeIndex.shouldUpdate = false;
			AnimeIndex.sessionAddedAnime.add(name);
			map.put(name, data);
			model.addElement(name);
			AnimeIndex.animeTypeComboBox.setSelectedItem(listName);
			list.clearSelection();
			list.setSelectedValue(name, true);
			AnimeIndex.shouldUpdate = true;
			
		}
	}
}
