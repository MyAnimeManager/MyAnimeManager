package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.pushingpixels.substance.api.SubstanceColorScheme;
import org.pushingpixels.substance.api.SubstanceColorSchemeBundle;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.SubstanceSkin.ColorSchemes;
import org.pushingpixels.substance.api.fonts.FontPolicy;
import org.pushingpixels.substance.api.fonts.FontSet;
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
	    pbar.setStringPainted(false);
	    pbar.setPreferredSize(new Dimension(310, 20));
    }
    
    public static void main(String args[])throws Exception
    {
    	SwingUtilities.invokeLater(new Runnable() {

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
							if (!pbar.isStringPainted())
								pbar.setStringPainted(true);
							int progress = task.getProgress();
							pbar.setValue(progress);
							switch (progress)
							{
								case 0:
									pbar.setString("Creazione Directory Log");
									break;
								case 5:
									pbar.setString("Caricamento Impostazioni");
									break;
								case 10: case 15:
									pbar.setString("Caricamento Impostazioni Estetiche");
									break;
								case 20: case 25:
									pbar.setString("Caricamento font");
									break;
								case 30:
									pbar.setString("Caricamento fansub");
									break;
								case 35:
									pbar.setString("Caricamento esclusioni");
									break;
								case 40:
									pbar.setString("Caricamento Anime Completati");
									break;
								case 45:
									pbar.setString("Caricamento Anime in Corso");
									break;
								case 50:
									pbar.setString("Caricamento OVA");
									break;
								case 55:
									pbar.setString("Caricamento Film");
									break;
								case 60:
									pbar.setString("Caricamento Anime Completi da Vedere");
									break;
								case 65: case 70:
									pbar.setString("Caricamento Wihslist");
									break;
								case 75: case 80:
									pbar.setString("Caricamento Droplist");
									break;
								case 85:
									pbar.setString("Caricamento Date");
									break;
								case 90:
									pbar.setString("Caricamento Pattern");
									break;
								case 95:
									pbar.setString("Inizializzazione serivizio MyAnimeMusic");
									break;
							}
						}
						
						if (evt.getNewValue().toString().equalsIgnoreCase("done"))
						{
							try
							{
								SubstanceGraphiteGlassLookAndFeel laf = new SubstanceGraphiteGlassLookAndFeel();
								FontUIResource fontUIResource = new FontUIResource(laf.getFontPolicy().getFontSet("Substance", null).getControlFont().deriveFont(12f));
								laf.setFontPolicy(new FontPolicy() {
									
									@Override
									public FontSet getFontSet(String arg0, UIDefaults arg1)
									{
										FontSet fontSet = new FontSet() {
											 public FontUIResource getWindowTitleFont() {
								                return fontUIResource; 
								            }

								            public FontUIResource getTitleFont() {
								                return fontUIResource;
								            }

								            public FontUIResource getSmallFont() {
								                return fontUIResource;
								            }

								            public FontUIResource getMessageFont() {
								                return fontUIResource;
								            }

								            public FontUIResource getMenuFont() {
								                return fontUIResource;
								            }

								            public FontUIResource getControlFont() {
								                return fontUIResource;
								            }
								        };
								        return fontSet;
								    }
								});
								UIManager.setLookAndFeel(laf);
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
