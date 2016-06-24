package util.task;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.SwingWorker;

import javazoom.jl.player.Player;
import main.AnimeIndex;


public class AudioIntroTask extends SwingWorker
{
	@Override
	protected Object doInBackground() throws Exception
	{
		String[] prop = AnimeIndex.appProp.getProperty("Mastah").split(":");
		if(prop[0].equals("true"))
		{
			Mixer.Info[] mixers = AudioSystem.getMixerInfo();  
			//System.out.println("There are " + mixers.length + " mixer info objects");  
			for (Mixer.Info mixerInfo : mixers)  
			{  
				//System.out.println("Mixer name: " + mixerInfo.getName());  
			    Mixer mixer = AudioSystem.getMixer(mixerInfo);  
			    Line.Info[] lineInfos = mixer.getTargetLineInfo(); // target, not source  
			    for (Line.Info lineInfo : lineInfos)  
			    {  
			        //System.out.println("  Line.Info: " + lineInfo);  
			        Line line = null;  
			        boolean opened = true;  
			        try  
			        {  
			            line = mixer.getLine(lineInfo);  
			            opened = line.isOpen() || line instanceof Clip;  
			            if (!opened)  
			            {  
			                line.open();  
			            }  
			            ((FloatControl)line.getControl(FloatControl.Type.VOLUME)).setValue(Float.parseFloat(prop[1])); 
			           // System.out.println("    volCtrl.getValue() = " + volCtrl.getValue());
			        }  
			        catch (LineUnavailableException e)  
			        {  
			          //  e.printStackTrace();  
			        }  
			        catch (IllegalArgumentException iaEx)  
			        {  
			           // System.out.println("    " + iaEx);  
			        }  
			        finally  
			        {  
			            if (line != null && !opened)  
			            {  
			                line.close();  
			            }  
			        }  
			    }  
			}
			InputStream fis = this.getClass().getClassLoader().getResourceAsStream("Mastah.mp3");
			BufferedInputStream buff = new BufferedInputStream(fis);
			Player player = new Player(buff);
			player.play();
			fis.close();
			buff.close();
		}
		return null;
	}
}
