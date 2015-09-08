package util.task;

import java.io.File;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;
import util.window.AnimeInformation;
import util.window.PreferenceDialog;

public class UpdateAnimeDataTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		ConnectionManager.ConnectAndGetToken();
		
		String nome = "";
		if(AnimeIndex.filtro!=9)
		{
			nome = (String)AnimeIndex.filterList.getSelectedValue();
			AnimeIndex.getJList().setSelectedValue(nome, true);
		}
		if(!AnimeIndex.searchBar.getText().isEmpty())
		{
			nome = (String)AnimeIndex.searchList.getSelectedValue();
			AnimeIndex.getJList().setSelectedValue(nome, true);
		}
		String name = (String) AnimeIndex.getJList().getSelectedValue();
		TreeMap<String,AnimeData> map = AnimeIndex.getMap();
		AnimeData oldData = map.get(name);
		if(AnimeIndex.activeUpdate == true)
		{
			if(AnimeIndex.exclusionAnime.containsKey(name) || AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("false"))
			{
				String totalEp = oldData.getTotalEpisode();
				String duration = oldData.getDurationEp();
				String startDate = oldData.getReleaseDate();
				String finishDate = oldData.getFinishDate();
				String type = oldData.getAnimeType();
						
				int id = Integer.parseInt(oldData.getId());
				String data = ConnectionManager.parseAnimeData(id);
				
				if (AnimeIndex.appProp.getProperty("excludeTotalEp").equalsIgnoreCase("false"))
				{
					totalEp = ConnectionManager.getAnimeData("total_episodes", data);
					if(totalEp.equals("null")||totalEp.equals("0"))
						totalEp = "??";
				}
				
	//			if (duration.contains("?? min") || duration.isEmpty() || duration.equals("min") || !duration.contains("min") || duration.equals("minuti") || duration.equals("minuto") || duration.contains("??") || duration.contains("?"))
				if(AnimeIndex.appProp.getProperty("excludeDuration").equalsIgnoreCase("false"))
				{
					duration = ConnectionManager.getAnimeData("duration", data);
					
					if(duration.equals("null")||duration.equals("0"))
						duration = "?? min";
					else
						duration += " min";
				}
				
				if (AnimeIndex.appProp.getProperty("excludeStartingDate").equalsIgnoreCase("false"))
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
				
				if (AnimeIndex.appProp.getProperty("excludeFinishDate").equalsIgnoreCase("false"))
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
				
				if (AnimeIndex.appProp.getProperty("excludeType").equalsIgnoreCase("false"))
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
				
				if (AnimeIndex.appProp.getProperty("excludeImage").equalsIgnoreCase("false"))
				{
					String imageLink = ConnectionManager.getAnimeData("image_url_lge", data);
					imageLink = imageLink.replaceAll("\\\\/", "/");
					String imageName = name.replaceAll("\\\\", "_");
					imageName = imageName.replaceAll("/", "_");
					imageName = imageName.replaceAll(":", "_");
					imageName = imageName.replaceAll("\\*", "_");
					imageName = imageName.replaceAll("\\?", "_");
					imageName = imageName.replaceAll("\"", "_");
					imageName = imageName.replaceAll(">", "_");
					imageName = imageName.replaceAll("<", "_");
					String list = AnimeIndex.getList();
					if (list.equalsIgnoreCase("anime completati"))
						FileManager.saveImage(imageLink, imageName, "Completed");
					if (list.equalsIgnoreCase("anime in corso"))
						FileManager.saveImage(imageLink, imageName, "Airing");
					if (list.equalsIgnoreCase("oav"))
						FileManager.saveImage(imageLink, imageName, "Ova");
					if (list.equalsIgnoreCase("film"))
						FileManager.saveImage(imageLink, imageName, "Film");
					if (list.equalsIgnoreCase("completi da vedere"))
						FileManager.saveImage(imageLink, imageName, "Completed to See");
					
					String path = oldData.getImagePath(list);
					File imgFile = new File(path);
					if(imgFile.exists())
						AnimeIndex.animeInformation.setImage(path);
					else
						AnimeIndex.animeInformation.setImage("default");
				}
				
				AnimeInformation.dial.dispose();
			}
			AnimeIndex.activeUpdate = false;
		}
		else if (AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("true") && !AnimeIndex.exclusionAnime.containsKey(name))
		{
			String totalEp = oldData.getTotalEpisode();
			String duration = oldData.getDurationEp();
			String startDate = oldData.getReleaseDate();
			String finishDate = oldData.getFinishDate();
			String type = oldData.getAnimeType();
					
			int id = Integer.parseInt(oldData.getId());
			String data = ConnectionManager.parseAnimeData(id);
			
			totalEp = ConnectionManager.getAnimeData("total_episodes", data);
			if(totalEp.equals("null")||totalEp.equals("0"))
				totalEp = "??";

			duration = ConnectionManager.getAnimeData("duration", data);
			
			if(duration.equals("null")||duration.equals("0"))
				duration = "?? min";
			else
				duration += " min";
			
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

			if(!type.equalsIgnoreCase("Blu-ray"))
				type = ConnectionManager.getAnimeData("type", data);
						
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
			
			String imageLink = ConnectionManager.getAnimeData("image_url_lge", data);
			imageLink = imageLink.replaceAll("\\\\/", "/");
			String imageName = name.replaceAll("\\\\", "_");
			imageName = imageName.replaceAll("/", "_");
			imageName = imageName.replaceAll(":", "_");
			imageName = imageName.replaceAll("\\*", "_");
			imageName = imageName.replaceAll("\\?", "_");
			imageName = imageName.replaceAll("\"", "_");
			imageName = imageName.replaceAll(">", "_");
			imageName = imageName.replaceAll("<", "_");
			String list = AnimeIndex.getList();
			if (list.equalsIgnoreCase("anime completati"))
				FileManager.saveImage(imageLink, imageName, "Completed");
			if (list.equalsIgnoreCase("anime in corso"))
				FileManager.saveImage(imageLink, imageName, "Airing");
			if (list.equalsIgnoreCase("oav"))
				FileManager.saveImage(imageLink, imageName, "Ova");
			if (list.equalsIgnoreCase("film"))
				FileManager.saveImage(imageLink, imageName, "Film");
			if (list.equalsIgnoreCase("completi da vedere"))
				FileManager.saveImage(imageLink, imageName, "Completed to See");
			
			String path = oldData.getImagePath(list);
			File imgFile = new File(path);
			if(imgFile.exists())
				AnimeIndex.animeInformation.setImage(path);
			else
				AnimeIndex.animeInformation.setImage("default");
		}
		
		return null;
	}
}
