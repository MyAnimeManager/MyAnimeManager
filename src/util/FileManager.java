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
import java.util.Scanner;
import java.util.TreeMap;

import util.window.WishlistDialog;
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
	
	public static void saveAnimeList(String file, SortedListModel category, TreeMap<String,AnimeData> map)
	{
		File animeFile = new File(ANIME_PATH + file);
		animeFile.delete();
		animeFile.getParentFile().mkdirs();
		BufferedWriter output;
		try 
		{
			output = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream(animeFile), "UTF-8"));
			
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

	public static void loadExclusionList()
	{
		File exclusionFile = new File(ANIME_PATH + "exclusion.anaconda");
		if (exclusionFile.isFile()) 
		{
			Scanner scan = null;
			try {
				scan = new Scanner(exclusionFile);
				
				while (scan.hasNextLine())
				{
					String excludedAnime = scan.nextLine();
					AnimeIndex.exclusionAnime.add(excludedAnime);
				}							
			} 
			catch (FileNotFoundException e) {
			} 
			finally
			{
				scan.close();
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
			
			Object[] exclusionNameArray = AnimeIndex.exclusionAnime.toArray();
			for (int i = 0; i < exclusionNameArray.length; i++)
				{
					String name = (String) exclusionNameArray[i];
					output.write(name);
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
				scan = new Scanner(wishlistFile);
				
				while (scan.hasNextLine())
				{
					
					String wishListLine = scan.nextLine();
					line = new Scanner(wishListLine);
					line.useDelimiter("\\|\\|");

					
						String name = line.next();
						int id = line.nextInt();
						WishlistDialog.wishListModel.addElement(name);
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
			
			Object[] wishListArray = WishlistDialog.wishListModel.toArray();
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
	public static void saveDefaultImage(String imageUrl, String destinationFile) {
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