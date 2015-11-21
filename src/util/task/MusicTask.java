package util.task;
import javax.swing.SwingWorker;
import util.window.MusicDialog;

public class MusicTask extends SwingWorker{

	@Override
	protected Object doInBackground() throws Exception
	{
		MusicDialog.player.play();
		return null;
	}
	@Override
	public void done()
	{
	}	
}	

