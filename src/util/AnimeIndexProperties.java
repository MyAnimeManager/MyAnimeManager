package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import main.AnimeIndex;

public class AnimeIndexProperties
{
	private static Properties applicationProps;

	public static Properties createProperties()
	{
		// create and load default properties
		Properties defaultProps = new Properties();
		FileInputStream in;
		try
		{
			in = new FileInputStream(MAMUtil.getPropertiesPath());
			defaultProps.load(in);
			defaultProps.setProperty("List_to_visualize_at_start", "Anime completati");
			defaultProps.setProperty("Update_system", "false");
			defaultProps.setProperty("List_to_Check", "None");
			defaultProps.setProperty("Date_Release", "none");
			defaultProps.setProperty("Open_Wishlist", "false");
			defaultProps.setProperty("Open_Droplist", "false");
			defaultProps.setProperty("Check_Data_Conflict", "true");
			defaultProps.setProperty("Stay_open_after_anime_add", "false");
			defaultProps.setProperty("Session_Number", "0");
			defaultProps.setProperty("Ask_for_donation", "true");
			defaultProps.setProperty("Suggestions_Pack_Number", "0");
			defaultProps.setProperty("Open_NewsBoard", "true");
			defaultProps.setProperty("Last_Music_Check", "null");
			defaultProps.setProperty("Music_Dialog_Default_Image_Counter", "0");
			defaultProps.setProperty("Reset_Music_List", "0");
			defaultProps.setProperty("New_Musics", "0");
			defaultProps.setProperty("Default_Music_Images", "miku_mem:Headphone..:Hatsune-Miku-Vocaloid..:Hatsune-Miku-Vocaloid...:hatsune-miku-vocaloid-1715:hmny:Hatsune-Miku-Vocaloid:Headphone");
			defaultProps.setProperty("General_Pattern", "%T% - %N%");
			defaultProps.setProperty("Special_Pattern", "%T% - %N%");
			defaultProps.setProperty("Main_Folder", "");
			defaultProps.setProperty("Username", "");
			defaultProps.setProperty("Password", "");
			defaultProps.setProperty("Mastah", "0.5");
			in.close();
		}
		catch (FileNotFoundException fe)
		{
			File prop = new File(MAMUtil.getPropertiesPath());
			try
			{
				prop.getParentFile().mkdirs();
				prop.createNewFile();
				defaultProps.setProperty("Update_system", "false");
				defaultProps.setProperty("List_to_visualize_at_start", "Anime completati");
				defaultProps.setProperty("Update_system", "false");
				defaultProps.setProperty("List_to_Check", "None");
				defaultProps.setProperty("Date_Release", "none");
				defaultProps.setProperty("Open_Wishlist", "false");
				defaultProps.setProperty("Open_Droplist", "false");
				defaultProps.setProperty("Check_Data_Conflict", "true");
				defaultProps.setProperty("Stay_open_after_anime_add", "false");
				defaultProps.setProperty("Session_Number", "0");
				defaultProps.setProperty("Ask_for_donation", "true");
				defaultProps.setProperty("Suggestions_Pack_Number", "0");
				defaultProps.setProperty("Open_NewsBoard", "true");
				defaultProps.setProperty("Last_Music_Check", "null");
				defaultProps.setProperty("Music_Dialog_Default_Image_Counter", "0");
				defaultProps.setProperty("Reset_Music_List", "0");
				defaultProps.setProperty("New_Musics", "0");
				defaultProps.setProperty("Default_Music_Images", "miku_mem:Headphone..:Hatsune-Miku-Vocaloid..:Hatsune-Miku-Vocaloid...:hatsune-miku-vocaloid-1715:hmny:Hatsune-Miku-Vocaloid:Headphone");
				defaultProps.setProperty("General_Pattern", "%T% - %N%");
				defaultProps.setProperty("Special_Pattern", "%T% - %N%");
				defaultProps.setProperty("Episode_Folder", "null");
				defaultProps.setProperty("Username", "");
				defaultProps.setProperty("Password", "");
				defaultProps.setProperty("Mastah", "0.5");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				MAMUtil.writeLog(e);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}

		applicationProps = new Properties(defaultProps);

		// now load properties
		// from last invocation
		try
		{
			in = new FileInputStream(MAMUtil.getPropertiesPath());
			applicationProps.load(in);
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		};

		return applicationProps;
	}

	public static void saveProperties(Properties prop)
	{
		FileOutputStream out;
		try
		{
			out = new FileOutputStream(MAMUtil.getPropertiesPath());
			prop.store(out, "---Start Option---");
			out.close();
		}
		catch (Exception e)
		{
		}
	}
}
