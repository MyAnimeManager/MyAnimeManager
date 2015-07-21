package util;

import java.io.File;

public class AnimeData
{
	String currentEpisode;
	String totalEpisode;
	String fansub;
	String fansubLink;
	String note;
	String imageName;
	String day;
	String id;
	String linkName;
	String link;
	String animeType;
	String releaseDate;
	String finishDate;
	//tipo anime, data di inizio e fine
	public String getId()
	{
		return id;
	}

	private final static String IMAGE_PATH = FileManager.getImageFolderPath();
		
	public AnimeData(String currentEpisode, String totalEpisode, String fansub, String fansubLink, String note, String image, 
					 String day, String id, String linkName, String link, String animeType, String releaseDate, String finishDate)
	{
		this.currentEpisode = currentEpisode;
		this.totalEpisode = totalEpisode;
		this.fansub = fansub;
		this.fansubLink = fansubLink;
		this.note = note;
		this.imageName = image;
		this.day = day;
		this.id = id;
		this.linkName = linkName;
		this.link = link;
		this.animeType = animeType;
		this.releaseDate = releaseDate;
		this.finishDate = finishDate;
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

	public String getFansubLink()
	{
		return fansubLink;
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

	public void setDay(String day)
	{
		this.day = day;
	}

	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}

	public void setCurrentEpisode(String string)
	{
		this.currentEpisode = string;
	}

	public void setTotalEpisode(String string)
	{
		this.totalEpisode = string;
	}

	public void setFansub(String fansub)
	{
		this.fansub = fansub;
	}

	public void setFansubLink(String fansubLink)
	{
		this.fansubLink = fansubLink;
	}

	public void setNote(String note)
	{
		this.note = note;
	}
	
	public String toString()
	{
		String string = this.getCurrentEpisode() + "||" + this.getTotalEpisode() + "||" + this.getFansub() + "||" + this.getFansubLink() + "||" + this.getNote() + "||" + this.getImageName() + "||" + this.getDay() + "||" + this.getId() + "||" + this.linkName + "||" + this.link + "||" + this.animeType + "||" + this.releaseDate + "||" + this.finishDate;
		return string;
	}
}
