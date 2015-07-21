package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.DefaultListModel;

import main.AnimeIndex;

public class FileManager
{
//	private final static String PATH = System.getProperty("user.home");

	private final static String APPDATA_PATH = System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator;
	private final static String FANSUB_PATH = APPDATA_PATH + "Fansub.txt";
	private final static String ANIME_PATH = APPDATA_PATH + File.separator + "Anime" + File.separator;
	private final static String IMAGE_PATH = APPDATA_PATH + "Images" + File.separator;
	//fansub
	public static void saveFansubList() throws IOException
	{
		File fansubFile = new File(FANSUB_PATH);
		fansubFile.getParentFile().mkdirs();
		BufferedWriter output = new BufferedWriter(new FileWriter(fansubFile));
		try
		{
						Object[] fansub = AnimeIndex.fansubMap.keySet().toArray();
						Object[] linkFansub = AnimeIndex.fansubMap.values().toArray();
			
			for (int i = 0; i < fansub.length; i++)
				if (fansub[i] != null)
				{
					output.write(fansub[i] + "||" + linkFansub[i] + "||" + System.lineSeparator());
				}
					
		} 
		finally
		{
			if (output != null)
				output.close();
		}

	}
	
	public static String[] loadFansubList()
	{
		File fansubFile = new File(FANSUB_PATH);
		if (fansubFile.isFile()) 
		{
			Scanner scan = null;
			Scanner line = null;
			String[] fansub = null;
			try {
				scan = new Scanner(fansubFile);
				int fansubNum = 0;
				try {
					fansubNum = countLines(fansubFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				fansub = new String[fansubNum];
				int i = 0;
				while (scan.hasNextLine())
				{
					String all = scan.nextLine();
					line = new Scanner(all);
					line.useDelimiter("\\|\\|");
					String fansubString = line.next();
					if (!(fansubString.isEmpty()))
					{
					fansub[i] = fansubString;
					i++;
					}
					String fansubLink = line.next();
					AnimeIndex.fansubMap.put(fansubString, fansubLink);
				}
				
				
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			finally
			{
				scan.close();
				if (line != null)
					line.close();
			}
			return fansub;
		}
			
		else
			{
			 try {
				fansubFile.createNewFile();
			 	} 
			 catch (IOException e) 
			 	{
				e.printStackTrace();
				}
				 return new String[0];
			}
		
	}
	
	//anime
	
	public static void loadAnime(String listName, DefaultListModel list, TreeMap<String,AnimeData> map)
	{
		File fansubFile = new File(ANIME_PATH + listName);
		if (fansubFile.isFile()) 
		{		
			Scanner sc = null;
			Scanner line = null;
			
			try 
			{
				sc = new Scanner(fansubFile);
				while (sc.hasNextLine())
				{
					String all = sc.nextLine();
					line = new Scanner(all);
					line.useDelimiter("\\|\\|");

						String anime = line.next();
						list.addElement(anime);
						
						String currentEp = line.next();						
						String totEp = line.next();						
						String fansub = line.next();						
						String fansublink = line.next();
						String note = line.next();
						String image = line.next();
						String day = line.next();
						
						AnimeData data = new AnimeData(currentEp, totEp, fansub, fansublink, note, image, day);
				
					map.put(anime, data);	
				}
			} 
			
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			if (line != null)
				line.close();
			sc.close();

		}
			
		else
			{
			 try {
				fansubFile.getParentFile().mkdirs();
				fansubFile.createNewFile();
			 	} 
			 catch (IOException e) 
			 	{
				e.printStackTrace();
				}
			}
	}
	
	public static void saveAnimeList(String file, DefaultListModel category, TreeMap<String,AnimeData> map)
	{
		File animeFile = new File(ANIME_PATH + file);
		animeFile.delete();
		animeFile.getParentFile().mkdirs();
		BufferedWriter output;
		try 
		{
			output = new BufferedWriter(new FileWriter(animeFile));
			
			Object[] animeNameArray = category.toArray();
			for (int i = 0; i < animeNameArray.length; i++)
				{
					String name = (String) animeNameArray[i];
					AnimeData data = map.get(name);
					output.write(name + "||" + data.toString() + "||");
					output.write(System.lineSeparator());
				}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	
	//util
	private static boolean checkForFile(String path)
	{
		File a = new File(path);
		return a.isFile();		
	}
	
	private static int countLines(File file) throws IOException {
	    int lines = 0;

	    FileInputStream fis = new FileInputStream(file);
	    byte[] buffer = new byte[8 * 1024]; // BUFFER_SIZE = 8 * 1024
	    int read;

	    while ((read = fis.read(buffer)) != -1) {
	        for (int i = 0; i < read; i++) {
	            if (buffer[i] == '\n') lines++;
	        }
	    }

	    fis.close();
	    return lines;
	}

	public static void saveImage(String imageUrl, String destinationFile) {
	    URL url;
		try {
			url = new URL(imageUrl);
			URLConnection conn =url.openConnection();
			conn.setRequestProperty("User-Agent", "My Anime Index");
			InputStream is = conn.getInputStream();
		    OutputStream os = new FileOutputStream(IMAGE_PATH + destinationFile +".png");

			    byte[] b = new byte[2048];
			    int length;

			    while ((length = is.read(b)) != -1) {
			        os.write(b, 0, length);
			    }

			    is.close();
			    os.close();
		}catch (FileNotFoundException e) {
			File file = new File(IMAGE_PATH);
			file.mkdirs();
			saveImage(imageUrl ,destinationFile);
		}
			catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static void deleteData(File file)	throws IOException
	 {
		 
		    	if(file.isDirectory())
		    	{		 
		    		if(file.list().length==0)		 
		    		   file.delete();		 
		    		else
		    		{
		        	   String files[] = file.list();
		 
		        	   for (String temp : files) 
		        	   {
		        	      File fileDelete = new File(file, temp);
		        	     deleteData(fileDelete);
		        	   }
		        	   if(file.list().length==0)
		           	     file.delete();
		    		}
		 
		    	}
		    	else
		    		file.delete();
    }
	 
	 public static String getAppDataPath()
	 {
		 return APPDATA_PATH;
	 }
	 
	 public static String getAnimeFolderPath()
	 {
		 return ANIME_PATH;
	 }
	 
	 public static String getImageFolderPath()
	 {
		 return IMAGE_PATH;
	 }
	 
	 
}