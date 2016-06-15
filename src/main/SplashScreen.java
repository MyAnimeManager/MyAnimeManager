package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.SubstanceColorScheme;
import org.pushingpixels.substance.api.SubstanceColorSchemeBundle;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.SubstanceSkin.ColorSchemes;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;

import util.task.LoadingTask;
import util.window.NewsBoardDialog;
import util.window.WishlistDialog;


public class SplashScreen extends JWindow {
	 
    private JLabel imglabel;
    private static JProgressBar pbar;
 
    public SplashScreen() {
    	setSize(799,335);
        setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon(getClass().getResource("/image/splash.png"));
                        
                        JPanel panel = new JPanel();
                        panel.setBounds(0, 0, 10, 10);
                        getContentPane().add(panel);
                        panel.setLayout(new BorderLayout(0, 0));
                        imglabel = new JLabel(img);
                        panel.add(imglabel, BorderLayout.CENTER);
                        imglabel.setPreferredSize(new Dimension(799,315));
                        imglabel.setMinimumSize(new Dimension(799,315));
                        pbar = new JProgressBar();
                        pbar.setBackground(Color.BLACK);
                        panel.add(pbar, BorderLayout.SOUTH);
                        pbar.setMinimum(0);
                        pbar.setMaximum(100);
                        pbar.setStringPainted(true);
                        pbar.setBorderPainted(false);
                        pbar.setPreferredSize(new Dimension(310, 20));

    }
    
    public static void main(String args[])throws Exception
    {
    	EventQueue.invokeLater(new Runnable() {

			@Override
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
				}
				catch (Exception e)
				{
					System.out.println("Substance Graphite failed to initialize");
				}
				try
				{
					UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
				}
				catch (Exception e)
				{
					System.out.println("Substance Graphite failed to initialize");
				}				
				ColorSchemes schemes = SubstanceSkin.getColorSchemes(SplashScreen.class.getClassLoader().getResource("graph.colorschemes"));
				SubstanceColorScheme determinateBorderScheme = schemes.get("Graphite Selected");
				SubstanceColorScheme bgBorderScheme = schemes.get("Graphite Background");
				SubstanceColorScheme activeBorderScheme = schemes.get("Graphite Active");	
			
				SubstanceColorSchemeBundle defaultSchemeBundle = new SubstanceColorSchemeBundle(determinateBorderScheme,bgBorderScheme,activeBorderScheme);			
				SubstanceLookAndFeel.getCurrentSkin().registerDecorationAreaSchemeBundle(defaultSchemeBundle, SubstanceLookAndFeel.getDecorationType(pbar));
				SplashScreen s = new SplashScreen();
		        s.setVisible(true);
		        
			    
		        LoadingTask task = new LoadingTask();
		        task.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt)
					{
						if (evt.getPropertyName().equalsIgnoreCase("progress"))
						{
							pbar.setValue(task.getProgress());
						}
						
						if (evt.getNewValue().toString().equalsIgnoreCase("done"))
						{
							try
							{
								UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
							}
							catch (Exception e)
							{
								System.out.println("Substance Graphite failed to initialize");
							}
							try
							{
								UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
							}
							catch (Exception e)
							{
								System.out.println("Substance Graphite failed to initialize");
							}
							AnimeIndex.frame = new AnimeIndex();
							AnimeIndex.frame.setVisible(true);
							AnimeIndex.wishlistDialog = new WishlistDialog();
							AnimeIndex.newsBoardDialog = new NewsBoardDialog();
					        s.dispose();
						}
						
					}
				});
		        task.execute();
		        
			}
		});
    	
    }
}
