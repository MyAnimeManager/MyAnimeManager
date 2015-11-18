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
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import main.AnimeIndex;


public class FileManager
{
//	private final static String PATH = System.getProperty("user.home");

	private final static String APPDATA_PATH = System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator;
	private final static String FANSUB_PATH = APPDATA_PATH + "Fansub.anaconda";
	private final static String ANIME_PATH = APPDATA_PATH + File.separator + "Anime" + File.separator;
	private final static String IMAGE_PATH = APPDATA_PATH + "Images" + File.separator;
	private final static String DEFAULT_IMAGE_PATH = APPDATA_PATH + "Default Image" + File.separator;
	//fansub
	public static void saveFansubList() throws IOException
	{
		File fansubFile = new File(FANSUB_PATH);
		fansubFile.getParentFile().mkdirs();
		BufferedWriter output = new BufferedWriter(new FileWriter(fansubFile));
		try
		{			
			for (Map.Entry<String, String> entry : AnimeIndex.fansubMap.entrySet())
			{
				output.write(entry.getKey() + "||" + entry.getValue() + "||" + System.lineSeparator());
			}
					
		} 
		finally
		{
				output.close();
		}

	}
	
	public static void loadFansubList()
	{
		File fansubFile = new File(FANSUB_PATH);
		if (fansubFile.isFile()) 
		{
			Scanner scan = null;
			Scanner line = null;
			try {
				scan = new Scanner(fansubFile);
				while (scan.hasNextLine())
				{
					String all = scan.nextLine();
					line = new Scanner(all);
					line.useDelimiter("\\|\\|");
					String fansubString = line.next();
					String fansubLink = line.next();
					AnimeIndex.fansubMap.put(fansubString, fansubLink);
					addDefaultFansub();
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
	
	//anime
	
	public static void loadAnime(String listName, SortedListModel list, TreeMap<String,AnimeData> map)
	{
		File fansubFile = new File(ANIME_PATH + listName);
		if (fansubFile.isFile()) 
		{		
			Scanner sc = null;
			Scanner line = null;
			
			try 
			{
				sc = new Scanner(fansubFile,"UTF-8");
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
						String note = line.next();
						String image = line.next();
						String day = line.next();
						String id = line.next();
						String linkName = line.next();
						String link = line.next();
						String animeType = line.next();
						String releaseDate = line.next();
						String finishDate = line.next();
						String durationEp = line.next();
						Boolean bd = line.nextBoolean();
						
						AnimeData data = new AnimeData(currentEp, totEp, fansub, note, image, day, id, linkName, link, animeType, releaseDate, finishDate, durationEp, bd);
				
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
	
	public static void saveAnimeList(String file, TreeMap<String,AnimeData> map)
	{
		File animeFile = new File(ANIME_PATH + file);
		animeFile.delete();
		animeFile.getParentFile().mkdirs();
		BufferedWriter output;
		try 
		{
			output = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(animeFile), "UTF-8"));
			
			for (Map.Entry<String,AnimeData> entry : map.entrySet())
			{
				output.write(entry.getKey() + "||" + entry.getValue().toString() + "||");
				output.write(System.lineSeparator());
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	public static void loadExclusionList()
	{
		File exclusionFile = new File(ANIME_PATH + "exclusion.anaconda");
		if (exclusionFile.isFile()) 
		{
			Scanner scan = null;
			Scanner line = null;
			try {
				scan = new Scanner(exclusionFile, "UTF-8");
				
				while (scan.hasNextLine())
				{
					String excludedAnime = scan.nextLine();
					line = new Scanner(excludedAnime);
					line.useDelimiter("\\|\\|");
					String name = line.next();
					boolean[] bool = new boolean[6];
					int i = 0;
					while (line.hasNext())
					{
						bool[i] = line.nextBoolean();
						i++;
					}
					AnimeIndex.exclusionAnime.put(name,bool);
				}							
			} 
			catch (FileNotFoundException e) {
			} 
			finally
			{
				scan.close();
				if (line != null)
						line.close();
			}
		}
			
		else
			{
			 try {
				 exclusionFile.createNewFile();
			 	} 
			 catch (IOException e) 
			 	{
				}
			}
	}
	
	public static void saveExclusionList()
	{
		File exclusionFile = new File(ANIME_PATH + "exclusion.anaconda");
		exclusionFile.delete();
		exclusionFile.getParentFile().mkdirs();
		BufferedWriter output;
		try 
		{
			output = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(exclusionFile), "UTF-8"));
			
			for (Map.Entry<String,boolean[]> entry : AnimeIndex.exclusionAnime.entrySet())
			{
				String line = entry.getKey() + "||";
				for (boolean bool : entry.getValue())
					line += bool + "||";
				output.write(line);				
				output.write(System.lineSeparator());
					
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadWishList()
	{
		File wishlistFile = new File(ANIME_PATH + "wishlist.anaconda");
		if (wishlistFile.isFile()) 
		{
			Scanner scan = null;
			Scanner line = null;
			try {
				scan = new Scanner(wishlistFile, "UTF-8");
				
				while (scan.hasNextLine())
				{
					
					String wishListLine = scan.nextLine();
					line = new Scanner(wishListLine);
					line.useDelimiter("\\|\\|");

					
						String name = line.next();
						int id = line.nextInt();
						AnimeIndex.wishlistDialog.wishListModel.addElement(name);
						AnimeIndex.wishlistMap.put(name, id);
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
		}
			
		else
			{
			 try {
				 wishlistFile.createNewFile();
			 	} 
			 catch (IOException e) 
			 	{
				e.printStackTrace();
				}
			}
	}
	
	public static void saveWishList()
	{
		File wishlistFile = new File(ANIME_PATH + "wishlist.anaconda");
		wishlistFile.delete();
		wishlistFile.getParentFile().mkdirs();
		BufferedWriter output;
		try 
		{
			output = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(wishlistFile), "UTF-8"));
			
			Object[] wishListArray = AnimeIndex.wishlistDialog.wishListModel.toArray();
			for (int i = 0; i < wishListArray.length; i++)
				{
					String name = (String) wishListArray[i];
					int id = AnimeIndex.wishlistMap.get(name);
					output.write(name + "||" + id);
					output.write(System.lineSeparator());
				}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Treemap date
	public static void saveDateMap()
	{
		File dateFile = new File(ANIME_PATH + "date.anaconda");
		dateFile.delete();
		dateFile.getParentFile().mkdirs();
		BufferedWriter output;
		// data --> stringa
		try 
		{
			output = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(dateFile), "UTF-8"));
			
			for (Map.Entry<String,String> entry : AnimeIndex.exitDateMap.entrySet())
			{
				output.write(entry.getKey() + "||" + entry.getValue());
				output.write(System.lineSeparator());
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDateMap()
	{
		File exclusionFile = new File(ANIME_PATH + "date.anaconda");
		if (exclusionFile.isFile()) 
		{
			Scanner scan = null;
			Scanner line = null;
			try {
				scan = new Scanner(exclusionFile, "UTF-8");
				
				while (scan.hasNextLine())
				{
					String excludedAnime = scan.nextLine();
					line = new Scanner(excludedAnime);
					line.useDelimiter("\\|\\|");
					String name = line.next();
					String date = line.next();
					AnimeIndex.exitDateMap.put(name,date);
				}							
			} 
			catch (Exception e) {
			} 
			finally
			{
				scan.close();
				if (line != null)
						line.close();
			}
		}
			
		else
			{
			 try {
				 exclusionFile.createNewFile();
			 	} 
			 catch (IOException e) 
			 	{
				}
			}
	}
	
	//util
	
	private static void addDefaultFansub()
	{
		if(!AnimeIndex.fansubMap.containsKey("?????"))
		{
			AnimeIndex.fansubMap.put("?????", "");
		}		
		if(!AnimeIndex.fansubMap.containsKey("Dynit"))
		{
			AnimeIndex.fansubMap.put("Dynit", "");
		}
		if(!AnimeIndex.fansubMap.containsKey("Yamato Animation"))
		{
			AnimeIndex.fansubMap.put("Yamato Animation", "");
		}
		if(!AnimeIndex.fansubMap.containsKey("Crunchyroll"))
		{
			AnimeIndex.fansubMap.put("Crunchyroll", "");
		}
		
	}
	
	public static void saveImage(String imageUrl, String destinationFile, String folderName) {
	    URL url;
		try {
			url = new URL(imageUrl);
			URLConnection conn =url.openConnection();
			conn.setRequestProperty("User-Agent", "My Anime Index");
			InputStream is = conn.getInputStream();
		    OutputStream os = new FileOutputStream(IMAGE_PATH + folderName + File.separator + destinationFile +".png");

			    byte[] b = new byte[2048];
			    int length;

			    while ((length = is.read(b)) != -1) {
			        os.write(b, 0, length);
			    }

			    is.close();
			    os.close();
		}catch (FileNotFoundException e) {
			File file = new File(IMAGE_PATH + folderName + File.separator);
			file.mkdirs();
			saveImage(imageUrl ,destinationFile, folderName);
		}
			catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void saveDefaultImage(String imageUrl, String destinationFile) 
	{
	    File url;
		try {
			url = new File(imageUrl);
			
			InputStream is = new FileInputStream(url);
		    OutputStream os = new FileOutputStream(DEFAULT_IMAGE_PATH + destinationFile +".png");

			    byte[] b = new byte[2048];
			    int length;
			    
			    while ((length = is.read(b)) != -1) {
			        os.write(b, 0, length);
			    }

			    is.close();
			    os.close();
			        
		}catch (FileNotFoundException e) {
			File file = new File(DEFAULT_IMAGE_PATH);
			file.mkdirs();
			saveDefaultImage(imageUrl ,destinationFile);
		}
			catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveNewImage(String imageUrl, String destinationFile, String folderName) {
	    File url;
		try {
			url = new File(imageUrl);
			
			InputStream is = new FileInputStream(url);
		    OutputStream os = new FileOutputStream(IMAGE_PATH + folderName + File.separator + destinationFile +".png");

			    byte[] b = new byte[2048];
			    int length;

			    while ((length = is.read(b)) != -1) {
			        os.write(b, 0, length);
			    }

			    is.close();
			    os.close();
		}catch (FileNotFoundException e) {
			File file = new File(IMAGE_PATH + folderName + File.separator);
			file.mkdirs();
			saveNewImage(imageUrl ,destinationFile, folderName);
		}
			catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public static void deleteData(File file)	throws IOException
	 {
		 File program = new File(FileManager.getAppDataPath() + AnimeIndex.CURRENT_VERSION);
		 File restart = new File(FileManager.getAppDataPath() + "MAMRestart.jar");
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
		        	      if (!fileDelete.equals(program) && !fileDelete.equals(restart))
		        	    	  deleteData(fileDelete);

		        	   }
		        	   if(file.list().length==0)
		        	   {
		        			file.delete();
		        	   }
		    		}
		 
		    	}
		    	else
		    		file.delete();
    }
	 
	 public static void moveImage(String imgPathFrom, String folderTo, String imgName)
	 {
		 File url = new File(imgPathFrom);
		 if(url.isFile())
		 {
		 InputStream is = null;
		 OutputStream os =null;
		 try{
			 try{
				try {
					is = new FileInputStream(url);
				    os = new FileOutputStream(IMAGE_PATH + folderTo + File.separator + imgName);
	
					    byte[] b = new byte[2048];
					    int length;
	
					    while ((length = is.read(b)) != -1) {
					        os.write(b, 0, length);
					    }			   
				}catch (FileNotFoundException e) {
					File file = new File(IMAGE_PATH + folderTo + File.separator);
					file.mkdirs();
					moveImage(imgPathFrom , folderTo, imgName);
				}
					catch (Exception e) {
					e.printStackTrace();
				}
			 }finally 
			 {
				 try{
				    is.close();
				    if(os!=null)
				    	os.close();}
				 catch(Exception e1){
				 e1.printStackTrace();}
			 }
			 }finally
			 {
				 try {
					deleteData(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		 }
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
	 
	 public static String getDefaultImageFolderPath()
	 {
		 return DEFAULT_IMAGE_PATH;
	 }
}