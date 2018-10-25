package util.task;

import java.io.File;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import com.google.gson.JsonObject;

import main.AnimeIndex;
import util.AnimeData;
import util.ConnectionManager;
import util.FileManager;
import util.MAMUtil;
import util.window.AnimeInformation;

public class AutoUpdateAnimeDataTask extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		if (AnimeIndex.appProp.getProperty("Update_system").equalsIgnoreCase("true"))
		{
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

			if (!AnimeIndex.exclusionAnime.containsKey(name))
			{
				int id = Integer.parseInt(oldData.getId());
				JsonObject jo = ConnectionManager.getAnimeData(id);

				String totEp = jo.get("episodes").getAsString();
				if (totEp != null && !totEp.isEmpty())
					if (totEp.equals("null") || totEp.equals("0"))
						totEp = "??";
				
				String duration = jo.get("duration").getAsString();
				if (duration != null && !duration.isEmpty())
					if (duration.equals("null") || duration.equals("0"))
						duration = "?? min";
					else
						duration += " min";
				
				JsonObject releaseDateJson = jo.get("startDate").getAsJsonObject();
				String releaseDate = "";
				if (releaseDateJson.get("day").isJsonNull())
					releaseDate = "??/";
				else
				{
					releaseDate = releaseDateJson.get("day").getAsString();
					if (releaseDate.length() == 1)
						releaseDate = "0" + releaseDate;
					releaseDate = releaseDate + "/";
				}
				
				if (releaseDateJson.get("month").isJsonNull())
					releaseDate = releaseDate + "??/";
				else
				{
					String releaseDateMonth = releaseDateJson.get("month").getAsString();
					if (releaseDateMonth.length() == 1)
						releaseDateMonth = "0" + releaseDateMonth;
					releaseDate = releaseDate + releaseDateMonth + "/";
				}
				if (releaseDateJson.get("year").isJsonNull())
					releaseDate = releaseDate + "????";
				else
					releaseDate = releaseDate + releaseDateJson.get("year").getAsString();

				JsonObject finishDateJson = jo.get("endDate").getAsJsonObject();
				String finishDate = "";
				if (finishDateJson.get("day").isJsonNull())
					finishDate = "??/";
				else
				{
					finishDate = finishDateJson.get("day").getAsString();
					if (finishDate.length() == 1)
						finishDate = "0" + finishDate;
					finishDate = finishDate + "/";
				}
				
				if (finishDateJson.get("month").isJsonNull())
					finishDate = finishDate + "??/";
				else
				{
					String finishDateMonth = finishDateJson.get("month").getAsString();
					if (finishDateMonth.length() == 1)
						finishDateMonth = "0" + finishDateMonth;
					finishDate = finishDate + finishDateMonth + "/";
				}
				
				if (finishDateJson.get("year").isJsonNull())
					finishDate = finishDate + "????";
				else
					finishDate = finishDate + finishDateJson.get("year").getAsString();

				String type = (String) AnimeIndex.animeInformation.typeComboBox.getSelectedItem();
				if (!type.equals("Blu-ray"))
					type = jo.get("format").getAsString();
				if (type.equalsIgnoreCase("MOVIE"))
					type = "Movie";
				if (type.equalsIgnoreCase("TV_SHORT"))
					type = "TV Short";
				if (type.equalsIgnoreCase("SPECIAL"))
					type = "Special";
				
				String imageLink = jo.get("coverImage").getAsJsonObject().get("large").getAsString();
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

				AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), totEp, oldData.getFansub(), oldData.getNote(), oldData.getImageName(), oldData.getDay(), oldData.getId(), oldData.getLinkName(), oldData.getLink(), type, releaseDate, finishDate, duration, oldData.getBd());

				map.put(name, newData);
			}
			else if (AnimeIndex.exclusionAnime.containsKey(name))
			{
				String totalEp = oldData.getTotalEpisode();
				String duration = oldData.getDurationEp();
				String releaseDate = oldData.getReleaseDate();
				String finishDate = oldData.getFinishDate();
				String type = oldData.getAnimeType();
				int id = Integer.parseInt(oldData.getId());

				boolean[] exclusionArray = AnimeIndex.exclusionAnime.get(name);
				JsonObject jo = ConnectionManager.getAnimeData(id);;

				if (exclusionArray[1] == false)
				{
					totalEp = jo.get("episodes").getAsString();
					if (totalEp.equals("null") || totalEp.equals("0"))
						totalEp = "??";
				}

				if (exclusionArray[2] == false)
				{
					duration = jo.get("duration").getAsString();
					if (duration.equals("null") || duration.equals("0"))
						duration = "?? min";
					else
						duration += " min";
				}

				if (exclusionArray[3] == false)
				{
					JsonObject releaseDateJson = jo.get("startDate").getAsJsonObject();
					if (releaseDateJson.get("day").isJsonNull())
						releaseDate = "??/";
					else
					{
						releaseDate = releaseDateJson.get("day").getAsString();
						if (releaseDate.length() == 1)
							releaseDate = "0" + releaseDate;
						releaseDate = releaseDate + "/";
					}
					
					if (releaseDateJson.get("month").isJsonNull())
						releaseDate = releaseDate + "??/";
					else
					{
						String releaseDateMonth = releaseDateJson.get("month").getAsString();
						if (releaseDateMonth.length() == 1)
							releaseDateMonth = "0" + releaseDateMonth;
						releaseDate = releaseDate + releaseDateMonth + "/";
					}
					if (releaseDateJson.get("year").isJsonNull())
						releaseDate = releaseDate + "????";
					else
						releaseDate = releaseDate + releaseDateJson.get("year").getAsString();
				}

				if (exclusionArray[4] == false)
					if (totalEp.equals("1"))
					{
						if (exclusionArray[3] == false)
							finishDate = releaseDate;
						else
						{
							JsonObject releaseDateJson = jo.get("startDate").getAsJsonObject();
							if (releaseDateJson.get("day").isJsonNull())
								releaseDate = "??/";
							else
							{
								releaseDate = releaseDateJson.get("day").getAsString();
								if (releaseDate.length() == 1)
									releaseDate = "0" + releaseDate;
								releaseDate = releaseDate + "/";
							}
							
							if (releaseDateJson.get("month").isJsonNull())
								releaseDate = releaseDate + "??/";
							else
							{
								String releaseDateMonth = releaseDateJson.get("month").getAsString();
								if (releaseDateMonth.length() == 1)
									releaseDateMonth = "0" + releaseDateMonth;
								releaseDate = releaseDate + releaseDateMonth + "/";
							}
							if (releaseDateJson.get("year").isJsonNull())
								releaseDate = releaseDate + "????";
							else
								releaseDate = releaseDate + releaseDateJson.get("year").getAsString();
							finishDate = releaseDate;
						}
					}
					else
					{
						JsonObject finishDateJson = jo.get("endDate").getAsJsonObject();
						if (finishDateJson.get("day").isJsonNull())
							finishDate = "??/";
						else
						{
							finishDate = finishDateJson.get("day").getAsString();
							if (finishDate.length() == 1)
								finishDate = "0" + finishDate;
							finishDate = finishDate + "/";
						}
						
						if (finishDateJson.get("month").isJsonNull())
							finishDate = finishDate + "??/";
						else
						{
							String finishDateMonth = finishDateJson.get("month").getAsString();
							if (finishDateMonth.length() == 1)
								finishDateMonth = "0" + finishDateMonth;
							finishDate = finishDate + finishDateMonth + "/";
						}
						
						if (finishDateJson.get("year").isJsonNull())
							finishDate = finishDate + "????";
						else
							finishDate = finishDate + finishDateJson.get("year").getAsString();
					}
					
				if (exclusionArray[5] == false && !type.equalsIgnoreCase("Blu-ray"))
					type = jo.get("format").getAsString();
					
				if (exclusionArray[0] == false)
				{
					String imageLink = jo.get("coverImage").getAsJsonObject().get("large").getAsString();
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
				AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), totalEp, oldData.getFansub(), oldData.getNote(), oldData.getImageName(), oldData.getDay(), oldData.getId(), oldData.getLinkName(), oldData.getLink(), type, releaseDate, finishDate, duration, oldData.getBd());
				map.put(name, newData);

			}
			AnimeIndex.animeInformation.totalEpisodeText.setText(map.get(name).getTotalEpisode());
			AnimeIndex.animeInformation.durationField.setText(map.get(name).getDurationEp());
			AnimeIndex.animeInformation.releaseDateField.setText(map.get(name).getReleaseDate());
			AnimeIndex.animeInformation.finishDateField.setText(map.get(name).getFinishDate());
			AnimeIndex.animeInformation.typeComboBox.setSelectedItem(map.get(name).getAnimeType());
			
			AnimeInformation.dial.dispose();
		}
		return null;
	}
}
