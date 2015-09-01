package util.task;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import util.Updater;
import main.AnimeIndex;


public class AutoDataCheckTask extends SwingWorker{
	
	@Override
	public Object doInBackground() throws Exception
	{
		UpdateAnimeDataTask task = new UpdateAnimeDataTask();
		AnimeIndex.appThread = new Thread() {
		     public void run() {
		         try {		        	 
		             SwingUtilities.invokeLater(task);
		         }
		         catch (Exception e) {
		             e.printStackTrace();
		         }
		     }
		 };

		 AnimeIndex.appThread.start();
		return null;
	}
}
