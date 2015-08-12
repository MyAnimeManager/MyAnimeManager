package util.task;

import java.util.TreeMap;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.window.AnimeInformation;

public class UpdateAnimeDataTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		AnimeIndex.saveModifiedInformation();
		ConnectionManager.ConnectAndGetToken();
		String name = (String) AnimeIndex.getJList().getSelectedValue();
		TreeMap<String,AnimeData> map = AnimeIndex.getMap();
		AnimeData oldData = map.get(name);
		//TODO if per le esclusioni
		String totalEp = oldData.getTotalEpisode();
		String duration = oldData.getDurationEp();
		String startDate = oldData.getReleaseDate();
		String finishDate = oldData.getFinishDate();
		String type = oldData.getAnimeType();
				
		int id = Integer.parseInt(oldData.getId());
		String data = ConnectionManager.parseAnimeData(id);
		
		if (totalEp.contains("??"))
		{
			totalEp = ConnectionManager.getAnimeData("total_episodes", data);
			if(totalEp.equals("null")||totalEp.equals("0"))
				totalEp = "??";
		}
		
		if (duration.contains("?? min"))
		{
			duration = ConnectionManager.getAnimeData("duration", data);
			
			if(duration.equals("null")||duration.equals("0"))
				duration = "?? min";
			else
				duration += " min";
		}
		
		if (startDate.contains("??/??/????") && startDate.contains("??/??"))
		{
			startDate = ConnectionManager.getAnimeData("start_date", data);
			
			if (startDate.equals("null"))
				startDate = "??/??/????";
			else if(startDate.length()==4)
				startDate = "??/??/" + startDate;
			else if(startDate.length()==7)
			{
				String monthStart = startDate.substring(5, 7);
				String yearStart = startDate.substring(0, 4);
				startDate = "??/" + monthStart + "/" + yearStart;
			}
			else if (startDate.length() > 7)
			{
				String dayStart = startDate.substring(8, 10);
				String monthStart = startDate.substring(5, 7);
				String yearStart = startDate.substring(0, 4);
				startDate = dayStart + "/" + monthStart + "/" + yearStart;
			}
		}
		
		if (finishDate.contains("?"))
		{
			finishDate = ConnectionManager.getAnimeData("end_date", data);
			
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
			if (totalEp.equals("1"))
				finishDate = startDate;
		}
		
		if (type.contains("?") && !type.equalsIgnoreCase("blu-ray"))
		{
			type = ConnectionManager.getAnimeData("type", data);
		}
				
		AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), totalEp, oldData.getFansub(), 
			    oldData.getNote(), oldData.getImageName(), oldData.getDay(), oldData.getId(),
				oldData.getLinkName(), oldData.getLink(), type, startDate, 
				finishDate, duration, oldData.getBd());
		map.put(name, newData);
		
		AnimeIndex.animeInformation.totalEpisodeText.setText(totalEp);
		AnimeIndex.animeInformation.durationField.setText(duration);
		AnimeIndex.animeInformation.releaseDateField.setText(startDate);
		AnimeIndex.animeInformation.finishDateField.setText(finishDate);
		AnimeIndex.animeInformation.typeComboBox.setSelectedItem(type);
		
		done();
		return null;
	}
	
	@Override
	protected void done()
	{
		AnimeInformation.dial.dispose();
	}
}
