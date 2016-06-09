package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import javax.swing.JList;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import main.AnimeIndex;

public class MAMUtil
{
	private final static String APPDATA_PATH = System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator;
	private final static String FANSUB_PATH = APPDATA_PATH + "Fansub.anaconda";
	private final static String ANIME_PATH = APPDATA_PATH + File.separator + "Anime" + File.separator;
	private final static String IMAGE_PATH = APPDATA_PATH + "Images" + File.separator;
	private final static String DEFAULT_IMAGE_PATH = APPDATA_PATH + "Default Image" + File.separator;
	private final static String MUSICS_PATH = APPDATA_PATH + "Musica" + File.separator;
	private final static String TEMP_DOWNLOAD_PATH = APPDATA_PATH + "Temp" + File.separator;
	private final static String PROPERTIES_PATH = MAMUtil.getAppDataPath() + "properties.properties";
	private static final String PATTERN_PATH = APPDATA_PATH + "pattern.anaconda";

	
	public static void createLogDirectory()
	{
		File logDirectory = new File(MAMUtil.getAppDataPath() + File.separator + "log" + File.separator);
		if (!logDirectory.exists())
			logDirectory.mkdirs();
	}

	public static void writeLog(Exception e)
	{
		String fileName = "log_" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "_" + Calendar.getInstance().get(Calendar.YEAR) + ".txt";
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(MAMUtil.getAppDataPath() + File.separator + "log" + File.separator + fileName, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("-------------------------------------------------------------------------");
			e.printStackTrace(pw);
			pw.println("-------------------------------------------------------------------------");
			pw.close();
			fw.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public static JList getJList()
	{
		JList list = null;
		String listName = MAMUtil.getList();
		if (listName.equalsIgnoreCase("anime completati"))
			list = AnimeIndex.completedList;
		else if (listName.equalsIgnoreCase("anime in corso"))
			list = AnimeIndex.airingList;
		else if (listName.equalsIgnoreCase("oav"))
			list = AnimeIndex.ovaList;
		else if (listName.equalsIgnoreCase("film"))
			list = AnimeIndex.filmList;
		else if (listName.equalsIgnoreCase("completi da vedere"))
			list = AnimeIndex.completedToSeeList;
			
		return list;
	}

	public static TreeMap getMap()
	{
		String listName = MAMUtil.getList();
		TreeMap map = null;
		if (listName.equalsIgnoreCase("anime completati"))
			map = AnimeIndex.completedMap;
		else if (listName.equalsIgnoreCase("anime in corso"))
			map = AnimeIndex.airingMap;
		else if (listName.equalsIgnoreCase("oav"))
			map = AnimeIndex.ovaMap;
		else if (listName.equalsIgnoreCase("film"))
			map = AnimeIndex.filmMap;
		else if (listName.equalsIgnoreCase("completi da vedere"))
			map = AnimeIndex.completedToSeeMap;
		return map;
	}

	public static SortedListModel getModel()
	{
		SortedListModel model = null;
		String listName = MAMUtil.getList();
		if (listName.equalsIgnoreCase("anime completati"))
			model = AnimeIndex.completedModel;
		else if (listName.equalsIgnoreCase("anime in corso"))
			model = AnimeIndex.airingModel;
		else if (listName.equalsIgnoreCase("oav"))
			model = AnimeIndex.ovaModel;
		else if (listName.equalsIgnoreCase("film"))
			model = AnimeIndex.filmModel;
		else if (listName.equalsIgnoreCase("completi da vedere"))
			model = AnimeIndex.completedToSeeModel;
			
		return model;
	}

	public static ArrayList<String> getDeletedAnimeArray()
	{
		String listName = MAMUtil.getList();
		ArrayList<String> arrayList = null;
		if (listName.equalsIgnoreCase("anime completati"))
			arrayList = AnimeIndex.completedDeletedAnime;
		else if (listName.equalsIgnoreCase("anime in corso"))
			arrayList = AnimeIndex.airingDeletedAnime;
		else if (listName.equalsIgnoreCase("oav"))
			arrayList = AnimeIndex.ovaDeletedAnime;
		else if (listName.equalsIgnoreCase("film"))
			arrayList = AnimeIndex.filmDeletedAnime;
		else if (listName.equalsIgnoreCase("completi da vedere"))
			arrayList = AnimeIndex.completedToSeeDeletedAnime;
			
		return arrayList;
	}

	public static String getList()
	{
		return (String) AnimeIndex.animeTypeComboBox.getSelectedItem();
	}

	public static Font loadFont()
	{
		InputStream is = AnimeIndex.class.getResourceAsStream("/font/seguisym.ttf");
		Font font = null;
		try
		{
			font = Font.createFont(Font.TRUETYPE_FONT, is);
		}
		catch (FontFormatException | IOException e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				MAMUtil.writeLog(e);
			}
		}
		return font;
	}

	public static String today()
	{
		GregorianCalendar today = new GregorianCalendar();
		int currentDay = today.get(Calendar.DATE);
		int currentMonth = today.get(Calendar.MONTH) + 1;
		int currentYear = today.get(Calendar.YEAR);
		String date = currentDay + "/" + currentMonth + "/" + currentYear;

		return date;
	}

	public static GregorianCalendar getDate(String date)
	{
		GregorianCalendar day = new GregorianCalendar();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			day.setTime(simpleDateformat.parse(date));
		}
		catch (ParseException e)
		{
			MAMUtil.writeLog(e);
			e.printStackTrace();
		}
		return day;
	}

	public static BufferedImage getScaledImage(BufferedImage image, int width, int height)
	{
		image = Scalr.resize(image, Method.ULTRA_QUALITY, width, height);
		return image;
		// int imageWidth = image.getWidth();
		// int imageHeight = image.getHeight();
		// double scaleX = (double)width/imageWidth;
		// double scaleY = (double)height/imageHeight;
		// AffineTransform scaleTransform =
		// AffineTransform.getScaleInstance(scaleX, scaleY);
		// AffineTransformOp bilinearScaleOp = new
		// AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		// return bilinearScaleOp.filter(image, new BufferedImage(width, height,
		// image.getType()));
	}
	
	public static String convertPatter(String animeName,String episode, String pattern)
	{
		String fileName = pattern.replace("%N%", animeName);
		fileName = pattern.replace("%n%", animeName);
		fileName = pattern.replace("%E%", episode);
		fileName = pattern.replace("%e%", episode);
		return fileName;
	}
	
	public static String getExtension(File f)
	{
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1)
			ext = s.substring(i).toLowerCase();
		return ext;
	}

	public static boolean christmas()
	{
		if (today().substring(0, 5).equalsIgnoreCase("25/12"))
			return true;
		return false;
	}
	
	public static void saveData()
	{
		try
		{
			FileManager.saveFansubList();
			FileManager.savePatternList();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
			MAMUtil.writeLog(e1);
		}
		FileManager.saveAnimeListGson("completed.JConda", AnimeIndex.completedMap);
		FileManager.saveAnimeListGson("airing.JConda", AnimeIndex.airingMap);
		FileManager.saveAnimeListGson("ova.JConda", AnimeIndex.ovaMap);
		FileManager.saveAnimeListGson("film.JConda", AnimeIndex.filmMap);
		FileManager.saveAnimeListGson("toSee.JConda", AnimeIndex.completedToSeeMap);
		FileManager.saveWishList();
		FileManager.saveExclusionList();
		FileManager.saveDateMap();

		try
		{
			FileManager.deleteData(new File(MAMUtil.getTempDownloadPath()));
		}
		catch (IOException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		
		deleteUselessImage(AnimeIndex.completedDeletedAnime);
		deleteUselessImage(AnimeIndex.airingDeletedAnime);
		deleteUselessImage(AnimeIndex.ovaDeletedAnime);
		deleteUselessImage(AnimeIndex.filmDeletedAnime);
		deleteUselessImage(AnimeIndex.completedToSeeDeletedAnime);
		deleteUselessImage(AnimeIndex.sessionAddedAnime);

		AnimeIndexProperties.saveProperties(AnimeIndex.appProp);
		ColorProperties.saveProperties(AnimeIndex.colorProp);

		System.exit(0);
	}
	

	public static void deleteUselessImage(ArrayList<String> arrayList)
	{
		for (int i = 0; i < arrayList.size(); i++)
		{
			String animeImagePath = arrayList.get(i);
			File image = new File(animeImagePath);
			try
			{
				FileManager.deleteData(image);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				writeLog(e);
			}
		}
	}
	
	public static String getAppDataPath()
	{
		return MAMUtil.APPDATA_PATH;
	}

	public static String getAnimeFolderPath()
	{
		return MAMUtil.ANIME_PATH;
	}

	public static String getImageFolderPath()
	{
		return MAMUtil.IMAGE_PATH;
	}

	public static String getDefaultImageFolderPath()
	{
		return MAMUtil.DEFAULT_IMAGE_PATH;
	}
	
	public static String getFansubPath()
	{
		return MAMUtil.FANSUB_PATH;
	}
	
	public static String getMusicPath()
	{
		return MAMUtil.MUSICS_PATH;
	}
	
	public static String getTempDownloadPath()
	{
		return MAMUtil.TEMP_DOWNLOAD_PATH;
	}
	
	public static String getPropertiesPath()
	{
		return MAMUtil.PROPERTIES_PATH;
	}
	
	public static String getPatternPath()
	{
		return MAMUtil.PATTERN_PATH;
	}

}
