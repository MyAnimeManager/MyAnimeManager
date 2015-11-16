package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class MAMUtil {
	
	public static void writeLog(Exception e)
	{
		String fileName = "log_" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "_" + Calendar.getInstance().get(Calendar.YEAR)+ ".txt";
		FileWriter fw = null;
		try
		{
			fw = new FileWriter (FileManager.getAppDataPath() + File.separator + fileName, true);
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

}
