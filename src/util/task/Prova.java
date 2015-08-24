package util.task;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingWorker;

public class Prova extends SwingWorker
{

	@Override
	protected Object doInBackground() throws Exception
	{
		playSound();
		return null;
	}

	private void playSound()
	{
		      AudioInputStream audioInputStream;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/prova.wav"));
				Clip clip = AudioSystem.getClip();
			     clip.open(audioInputStream);
			     clip.start( );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
	}
}
