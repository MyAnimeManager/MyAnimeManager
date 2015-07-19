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
	private final static String APPDATA_PATH = System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator;
	private final static String IMAGE_PATH = APPDATA_PATH + "Images" + File.separator;
		
	public AnimeData(String currentEpisode, String totalEpisode, String fansub, String fansubLink, String note, String image, String day)
	{
		this.currentEpisode = currentEpisode;
		this.totalEpisode = totalEpisode;
		this.fansub = fansub;
		this.fansubLink = fansubLink;
		this.note = note;
		this.imageName = image;
		this.day = day;
	}
	
	public AnimeData(String currentEpisode, String totalEpisode, String fansub, String fansubLink, String image, String day)
	{
		this.currentEpisode = currentEpisode;
		this.totalEpisode = totalEpisode;
		this.fansub = fansub;
		this.fansubLink = fansubLink;
		this.imageName = image;
		this.note = "";
		this.day = day;
	}
	
	public AnimeData(String currentEpisode, String totalEpisode, String fansub, String fansubLink, String image)
	{
		this.currentEpisode = currentEpisode;
		this.totalEpisode = totalEpisode;
		this.fansub = fansub;
		this.fansubLink = fansubLink;
		this.note = "";
		this.imageName = image;
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
		String string = this.getCurrentEpisode() + "||" + this.getTotalEpisode() + "||" + this.getFansub() + "||" + this.getFansubLink() + "||" + this.getNote() + "||" + this.getImageName() + "||" + this.getDay();
		return string;
	}
}
