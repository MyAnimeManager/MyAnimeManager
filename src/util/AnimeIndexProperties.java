package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AnimeIndexProperties
{
	//private boolean prova = true;
	private final static String APPDATA_PATH = System.getenv("APPDATA") + File.separator + "MyAnimeIndex" + File.separator;
	private final static String PROPERTIES_PATH = APPDATA_PATH + "properties.properties";
	private static Properties applicationProps;
	public static Properties createProperties() 
	{
		// create and load default properties
		Properties defaultProps = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(PROPERTIES_PATH);
			defaultProps.load(in);
			defaultProps.setProperty("Show_episode_to_see", "true");
			in.close();
		} 
		catch (FileNotFoundException fe)
		{
			File prop = new File(PROPERTIES_PATH);
			try {
				prop.getParentFile().mkdirs();
				prop.createNewFile();
				defaultProps.setProperty("Show_episode_to_see", "true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		applicationProps = new Properties(defaultProps);

		// now load properties 
		// from last invocation
		try {
			in = new FileInputStream(PROPERTIES_PATH);
			applicationProps.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		};
		return applicationProps;
	}
	
	public static void saveProperties(Properties prop)
	{
		FileOutputStream out;
		try {
			out = new FileOutputStream(PROPERTIES_PATH);
			prop.store(out, "---Boolean---");
			out.close();
		} catch (Exception e) {
		}
	}
}
