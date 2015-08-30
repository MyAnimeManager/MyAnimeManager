package util;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;



public class ColorProperties
{
	private final static String PROPERTIES_PATH = System.getenv("APPDATA") + File.separator + "MyAnimeManager" + File.separator + "color.properties";
	private static Properties applicationProps;
	public static Properties createProperties() 
	{
		// create and load default properties
		Properties defaultProps = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(PROPERTIES_PATH);
			defaultProps.load(in);
			defaultProps.put("Button_color", "null");
			in.close();
		} 
		catch (FileNotFoundException fe)
		{
			File prop = new File(PROPERTIES_PATH);
			try {
				prop.getParentFile().mkdirs();
				prop.createNewFile();
				defaultProps.put("Button_color", "null");
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
			prop.store(out, "---Start Option---");
			out.close();
		} catch (Exception e) {
		}
	}
	
	public static void setColor(Properties colorProperties)
	{
		if (!colorProperties.getProperty("Button_color").equalsIgnoreCase("null"))
		{
			Color color = new Color(Integer.parseInt(colorProperties.getProperty("Button_color")));
			UIManager.put("Button.background", color);
		}
	}
}
