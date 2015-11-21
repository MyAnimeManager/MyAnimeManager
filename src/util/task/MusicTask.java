package util.task;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.swing.SwingWorker;
import util.window.MusicDialog;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MusicTask extends SwingWorker{
	
	public static String packNumber;
	int pausedOnFrame=0;
	public AdvancedPlayer player;
	
	@Override
	protected Object doInBackground() throws Exception
	{
		try{
			FileInputStream fis = null;
		    BufferedInputStream buff = null;
			if(MusicDialog.isRunning==false)
			{
			    fis = new FileInputStream("C:\\Users\\Denis\\Desktop\\Extra\\Soundtrack\\OP\\MP3\\02. Red Alert Carpet.mp3");
			    buff = new BufferedInputStream(fis);
			    player = new AdvancedPlayer(buff);
			    MusicDialog.isRunning = true;
			    player.play();
			}
			else
			{
				player.stop();
				MusicDialog.isRunning = false;
			}
			fis.close();
		    buff.close();
		}
		catch(Exception exc){
		    exc.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void done()
	{
	}	
}	

