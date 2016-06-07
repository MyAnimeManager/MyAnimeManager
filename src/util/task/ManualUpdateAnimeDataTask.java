package util.task;

import java.io.File;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;
import util.MAMUtil;
import util.window.AnimeInformation;

public class ManualUpdateAnimeDataTask extends SwingWorker
{

	boolean[] exclusionArray;

	public ManualUpdateAnimeDataTask(boolean[] exclusion)
	{
		exclusionArray = exclusion;
	}

	@Override
	protected Object doInBackground() throws Exception
	{
		AnimeIndex.saveModifiedInformation();
		ConnectionManager.ConnectAndGetToken();
		String nome = "";
		if (AnimeIndex.filtro != 9)
		{
			nome = (String) AnimeIndex.filterList.getSelectedValue();
			MAMUtil.getJList().setSelectedValue(nome, true);
		}
		if (!AnimeIndex.searchBar.getText().isEmpty())
		{
			nome = (String) AnimeIndex.searchList.getSelectedValue();
			MAMUtil.getJList().setSelectedValue(nome, true);
		}
		String name = (String) MAMUtil.getJList().getSelectedValue();
		TreeMap<String, AnimeData> map = MAMUtil.getMap();
		AnimeData oldData = map.get(name);

		String totalEp = oldData.getTotalEpisode();
		String duration = oldData.getDurationEp();
		String startDate = oldData.getReleaseDate();
		String finishDate = oldData.getFinishDate();
		String type = oldData.getAnimeType();
		int id = Integer.parseInt(oldData.getId());

		if (exclusionArray[1])
		{
			totalEp = ConnectionManager.getAnimeDataGson("total_episodes", id);
			if (totalEp.equals("null") || totalEp.equals("0"))
				totalEp = "??";
		}

		if (exclusionArray[2])
		{
			duration = ConnectionManager.getAnimeDataGson("duration", id);
			if (duration.equals("null") || duration.equals("0"))
				duration = "?? min";
			else
				duration += " min";
		}

		if (exclusionArray[3])
		{
			startDate = ConnectionManager.getAnimeDataGson("start_date", id);

			if (startDate.equals("null"))
				startDate = "??/??/????";
			else if (startDate.length() == 4)
				startDate = "??/??/" + startDate;
			else if (startDate.length() == 7)
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

		if (exclusionArray[4])
			if (totalEp.equals("1"))
			{
				if (exclusionArray[3] == false)
				{
					startDate = ConnectionManager.getAnimeDataGson("start_date", id);

					if (startDate.equals("null"))
						startDate = "??/??/????";
					else if (startDate.length() == 4)
						startDate = "??/??/" + startDate;
					else if (startDate.length() == 7)
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
				finishDate = startDate;
			}
			else
			{
				finishDate = ConnectionManager.getAnimeDataGson("end_date", id);

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
			}
			
		if (exclusionArray[5])
			type = ConnectionManager.getAnimeDataGson("type", id);
			
		if (exclusionArray[0])
		{
			String imageLink = ConnectionManager.getAnimeDataGson("image_url_lge", id);
			imageLink = imageLink.replaceAll("\\\\/", "/");
			String imageName = name.replaceAll("\\\\", "_");
			imageName = imageName.replaceAll("/", "_");
			imageName = imageName.replaceAll(":", "_");
			imageName = imageName.replaceAll("\\*", "_");
			imageName = imageName.replaceAll("\\?", "_");
			imageName = imageName.replaceAll("\"", "_");
			imageName = imageName.replaceAll(">", "_");
			imageName = imageName.replaceAll("<", "_");
			String list = MAMUtil.getList();
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
			if (imgFile.exists())
				AnimeIndex.animeInformation.setImage(path);
			else
				AnimeIndex.animeInformation.setImage("default");
		}
		AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), totalEp, oldData.getFansub(), oldData.getNote(), oldData.getImageName(), oldData.getDay(), oldData.getId(), oldData.getLinkName(), oldData.getLink(), type, startDate, finishDate, duration, oldData.getBd());
		map.put(name, newData);

		AnimeIndex.animeInformation.totalEpisodeText.setText(map.get(name).getTotalEpisode());
		AnimeIndex.animeInformation.durationField.setText(map.get(name).getDurationEp());
		AnimeIndex.animeInformation.releaseDateField.setText(map.get(name).getReleaseDate());
		AnimeIndex.animeInformation.finishDateField.setText(map.get(name).getFinishDate());
		AnimeIndex.animeInformation.typeComboBox.setSelectedItem(map.get(name).getAnimeType());

		AnimeInformation.dial.dispose();
		return null;
	}
}
