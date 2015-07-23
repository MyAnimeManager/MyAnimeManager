package util;

import java.io.File;

public class AnimeData
{
	String currentEpisode;
	String totalEpisode;
	String fansub;
	String note;
	String imageName;
	String day;
	String id;
	String linkName;
	String link;
	String animeType;
	String releaseDate;
	String finishDate;
	String durationEp;
	//tipo anime, data di inizio e fine

	private final static String IMAGE_PATH = FileManager.getImageFolderPath();
		
	public AnimeData(String currentEpisode, String totalEpisode, String fansub, String note, String image, 
					 String day, String id, String linkName, String link, String animeType, String releaseDate, String finishDate, String durationEp)
	{
		this.currentEpisode = currentEpisode;
		this.totalEpisode = totalEpisode;
		this.fansub = fansub;
		this.note = note;
		this.imageName = image;
		this.day = day;
		this.id = id;
		this.linkName = linkName;
		this.link = link;
		this.animeType = animeType;
		this.releaseDate = releaseDate;
		this.finishDate = finishDate;
		this.durationEp = durationEp;
	}
	
	public String getLinkName()
	{
		return linkName;
	}

	public String getLink()
	{
		return link;
	}

	public String getAnimeType()
	{
		return animeType;
	}

	public String getReleaseDate()
	{
		return releaseDate;
	}

	public String getFinishDate()
	{
		return finishDate;
	}

	public String getCurrentEpisode()
	{
		return currentEpisode;
	}

	public String getTotalEpisode()
	{
		return totalEpisode;
	}

	public String getFansub()
	{
		return fansub;
	}

	public String getNote()
	{
		return note;
	}

	public String getImageName()
	{
		return imageName;
	}
	
	public String getImagePath()
	{
		return IMAGE_PATH + this.getImageName();
	}

	public String getDay()
	{
		return day;
	}
	
	public String getId()
	{
		return id;
	}

	public String getDurationEp()
	{
		return durationEp;
	}
	public String toString()
	{
		String string = this.getCurrentEpisode() + "||" + this.getTotalEpisode() + "||" + this.getFansub() + "||" + this.getNote() + "||" + this.getImageName() + "||" + this.getDay() + "||" + this.getId() + "||" + this.getLinkName() + "||" + this.getLink() + "||" + this.getAnimeType() + "||" + this.getReleaseDate() + "||" + this.getFinishDate() + "||" + this.getDurationEp();
		return string;
	}
}
