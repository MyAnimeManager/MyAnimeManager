package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.ColorSchemeAssociationKind;
import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.ComponentStateFacet;
import org.pushingpixels.substance.api.DecorationAreaType;
import org.pushingpixels.substance.api.SubstanceColorScheme;
import org.pushingpixels.substance.api.SubstanceColorSchemeBundle;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.SubstanceSkin.ColorSchemes;
import org.pushingpixels.substance.api.fonts.SubstanceFontUtilities;
import org.pushingpixels.substance.api.painter.decoration.SubstanceDecorationPainter;
import org.pushingpixels.substance.api.skin.GraphiteGlassSkin;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;
import org.pushingpixels.substance.internal.utils.SubstanceColorUtilities;


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
				
				
				
				ComponentState determinateState = new ComponentState("determinate",
				        new ComponentStateFacet[] { ComponentStateFacet.ENABLE,
				            ComponentStateFacet.DETERMINATE }, null);
				ComponentState indeterminateState = new ComponentState("indeterminate",
				        new ComponentStateFacet[] { ComponentStateFacet.ENABLE },
				        new ComponentStateFacet[] { ComponentStateFacet.DETERMINATE });
				
				ColorSchemes schemes = SubstanceSkin.getColorSchemes(SplashScreen.class
				            .getClassLoader()
				            .getResource(
				                "graph.colorschemes"));
				SubstanceColorScheme determinateBorderScheme = schemes.get("Graphite Selected");
				SubstanceColorScheme bgBorderScheme = schemes.get("Graphite Background");
				SubstanceColorScheme activeBorderScheme = schemes.get("Graphite Active");
				
				
				
																								
				SubstanceColorSchemeBundle defaultSchemeBundle = new SubstanceColorSchemeBundle(determinateBorderScheme,bgBorderScheme,activeBorderScheme);
				SubstanceColorScheme selectedBorderScheme = schemes.get("Graphite Border");
				defaultSchemeBundle.registerColorScheme(selectedBorderScheme,
				    ColorSchemeAssociationKind.BORDER);
				
				SubstanceLookAndFeel.getCurrentSkin().registerDecorationAreaSchemeBundle(defaultSchemeBundle, SubstanceLookAndFeel.getDecorationType(pbar));
				 SplashScreen s = new SplashScreen();
		        s.setVisible(true);
		        pbar.setValue(50);
		        
//		        s.dispose();
//		        System.exit(0);
			}
		});
    	
    }
}
