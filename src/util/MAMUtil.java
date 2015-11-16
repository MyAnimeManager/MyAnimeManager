package util;

import java.awt.Font;
import java.awt.FontFormatException;
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

import main.AnimeIndex;

public class MAMUtil {
	
	public static void createLogDirectory()
	{
		File logDirectory = new File(FileManager.getAppDataPath() + File.separator + "log" + File.separator);
		if (!logDirectory.exists())
		{
			logDirectory.mkdirs();
		}
	}
	
	public static void writeLog(Exception e)
	{
		String fileName = "log_" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "_" + Calendar.getInstance().get(Calendar.YEAR)+ ".txt";
		FileWriter fw = null;
		try
		{
			fw = new FileWriter (FileManager.getAppDataPath() + File.separator + "log" + File.separator + fileName, true);
			PrintWriter pw = new PrintWriter (fw);
			pw.println("-------------------------------------------------------------------------");
			e.printStackTrace (pw);	
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
		JList list= null;
			String listName = MAMUtil.getList();
			if (listName.equalsIgnoreCase("anime completati"))
			{	
				list = AnimeIndex.completedList;						
			}				
			else if (listName.equalsIgnoreCase("anime in corso"))
			{
				list = AnimeIndex.airingList;
			}
			else if (listName.equalsIgnoreCase("oav"))
			{
				list = AnimeIndex.ovaList;
			}
			else if (listName.equalsIgnoreCase("film"))
			{
				list = AnimeIndex.filmList;
			}
			else if (listName.equalsIgnoreCase("completi da vedere"))
			{
				list = AnimeIndex.completedToSeeList;
			}
			
		return list;
	}

	public static TreeMap getMap()
	{
		String listName = MAMUtil.getList();
		TreeMap map= null;
		if (listName.equalsIgnoreCase("anime completati"))
		{	
			map = AnimeIndex.completedMap;						
		}				
		else if (listName.equalsIgnoreCase("anime in corso"))
		{
			map = AnimeIndex.airingMap;
		}
		else if (listName.equalsIgnoreCase("oav"))
		{
			map = AnimeIndex.ovaMap;
		}
		else if (listName.equalsIgnoreCase("film"))
		{
			map = AnimeIndex.filmMap;
		}
		else if (listName.equalsIgnoreCase("completi da vedere"))
		{
			map = AnimeIndex.completedToSeeMap;
		}
		return map;
	}

	public static SortedListModel getModel()
	{
		SortedListModel model= null;
			String listName = MAMUtil.getList();
			if (listName.equalsIgnoreCase("anime completati"))
			{	
				model = AnimeIndex.completedModel;						
			}				
			else if (listName.equalsIgnoreCase("anime in corso"))
			{
				model = AnimeIndex.airingModel;
			}
			else if (listName.equalsIgnoreCase("oav"))
			{
				model = AnimeIndex.ovaModel;
			}
			else if (listName.equalsIgnoreCase("film"))
			{
				model = AnimeIndex.filmModel;
			}
			else if (listName.equalsIgnoreCase("completi da vedere"))
			{
				model = AnimeIndex.completedToSeeModel;
			}
			
		return model;
	}

	public static ArrayList<String> getDeletedAnimeArray()
	{
		String listName = MAMUtil.getList();
		ArrayList<String> arrayList= null;
		if (listName.equalsIgnoreCase("anime completati"))
		{	
			arrayList = AnimeIndex.completedDeletedAnime;						
		}				
		else if (listName.equalsIgnoreCase("anime in corso"))
		{
			arrayList = AnimeIndex.airingDeletedAnime;
		}
		else if (listName.equalsIgnoreCase("oav"))
		{
			arrayList = AnimeIndex.ovaDeletedAnime;
		}
		else if (listName.equalsIgnoreCase("film"))
		{
			arrayList = AnimeIndex.filmDeletedAnime;
		}
		else if (listName.equalsIgnoreCase("completi da vedere"))
		{
			arrayList = AnimeIndex.completedToSeeDeletedAnime;
		}
		
		return arrayList;
	}

	public static String getList()
	{
		return (String) AnimeIndex.animeTypeComboBox.getSelectedItem();
	}

	public static Font segui()
	{
		InputStream is = AnimeIndex.class.getResourceAsStream("/font/seguisym.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		} finally{ //fix me
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return font;
	}

	public static String today()
	{
		GregorianCalendar today = new GregorianCalendar();
		int currentDay = today.get(Calendar.DATE);
		int currentMonth = today.get(Calendar.MONTH)+1;
		int currentYear = today.get(Calendar.YEAR);
		String date = currentDay + "/" + currentMonth + "/" + currentYear;
		
		return date;
	}

	public static GregorianCalendar getDate(String date) throws ParseException
	{
		GregorianCalendar day = new GregorianCalendar();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy"); // the day of the week abbreviated
		day.setTime(simpleDateformat.parse(date));
		return day;
	}

}
