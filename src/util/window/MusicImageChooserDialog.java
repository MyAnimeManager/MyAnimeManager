package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import main.AnimeIndex;
import util.MAMUtil;


public class MusicImageChooserDialog extends JDialog
{
	
	private final JPanel contentPanel = new JPanel();
	private JCheckBox image1;
	private JCheckBox image2;
	private JCheckBox image3;
	private JCheckBox image4;
	private JCheckBox image5;
	private JCheckBox image6;
	private JCheckBox image7;
	private JCheckBox image8;
	/**
	 * Create the dialog.
	 */
	public MusicImageChooserDialog()
	{
		super(AnimeIndex.musicDialog, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Imposta immagini");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				String imageListString = AnimeIndex.appProp.getProperty("Default_Music_Images");
				String[] imageArray = imageListString.split(":");
				for (String image : imageArray)
				{
					switch (image) {
						case "miku_mem": image1.setSelected(true);
							break;
						case "Headphone..": image2.setSelected(true);
							break;
						case "Hatsune-Miku-Vocaloid..": image3.setSelected(true);
							break;
						case "Hatsune-Miku-Vocaloid...": image4.setSelected(true);
							break;
						case "hatsune-miku-vocaloid-1715": image5.setSelected(true);
							break;
						case "hmny": image6.setSelected(true);
							break;
						case "Hatsune-Miku-Vocaloid": image7.setSelected(true);
							break;
						case "Headphone": image8.setSelected(true);
							break;
					}
				}
			}
		});
		setBounds(100, 100, 488, 356);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				JPanel panel = new JPanel();
				scrollPane.setViewportView(panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0};
				gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
				{
					image1 = new JCheckBox();
					image1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image1.isSelected())
							{
								if (!(!image2.isSelected() && !image3.isSelected() && !image4.isSelected() && !image5.isSelected() && !image6.isSelected() && !image7.isSelected() && !image8.isSelected()))
								{  
									image1.setIcon(new ImageIcon(getImage("miku_mem")));
				                }
							}
							else
							{
								image1.setIcon(new ImageIcon(getImage("miku_mem_obscured")));              
							}
						}
					});
					GridBagConstraints gbc_image1 = new GridBagConstraints();
					gbc_image1.insets = new Insets(0, 0, 5, 5);
					gbc_image1.gridx = 0;
					gbc_image1.gridy = 0;
					image1.setIcon(new ImageIcon(getImage("miku_mem")));
					panel.add(image1, gbc_image1);

					image2 = new JCheckBox();
					image2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image2.isSelected())
							{
								if (!(!image1.isSelected() && !image3.isSelected() && !image4.isSelected() && !image5.isSelected() && !image6.isSelected() && !image7.isSelected() && !image8.isSelected()))
								{  
									image2.setIcon(new ImageIcon(getImage("Headphone..")));
				                }
							}
							else
							{
								image2.setIcon(new ImageIcon(getImage("Headphone.._obscured")));              
							}
						}
					});
					GridBagConstraints gbc_image2 = new GridBagConstraints();
					gbc_image2.insets = new Insets(0, 0, 5, 5);
					gbc_image2.gridx = 1;
					gbc_image2.gridy = 0;
					image2.setIcon(new ImageIcon(getImage("Headphone..")));
					panel.add(image2, gbc_image2);

					image3 = new JCheckBox();
					image3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image3.isSelected())
							{
								if (!(!image1.isSelected() && !image2.isSelected() && !image4.isSelected() && !image5.isSelected() && !image6.isSelected() && !image7.isSelected() && !image8.isSelected()))
								{  
									image3.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid..")));
				                }
							}
							else
							{
								image3.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid.._obscured")));
							}
						}
					});
					GridBagConstraints gbc_image3 = new GridBagConstraints();
					gbc_image3.insets = new Insets(0, 0, 5, 5);
					gbc_image3.gridx = 2;
					gbc_image3.gridy = 0;
					image3.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid..")));
					panel.add(image3, gbc_image3);

					image4 = new JCheckBox();
					image4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image4.isSelected())
							{
								if (!(!image1.isSelected() && !image2.isSelected() && !image3.isSelected() && !image5.isSelected() && !image6.isSelected() && !image7.isSelected() && !image8.isSelected()))
								{  
									image4.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid...")));
				                }
							}
							else
							{
								image4.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid..._obscured")));
							}
						}
					});
					GridBagConstraints gbc_image4 = new GridBagConstraints();
					gbc_image4.insets = new Insets(0, 0, 5, 0);
					gbc_image4.gridx = 3;
					gbc_image4.gridy = 0;
					image4.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid...")));
					panel.add(image4, gbc_image4);

					image5 = new JCheckBox();
					image5.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image5.isSelected())
							{
								if (!(!image1.isSelected() && !image2.isSelected() && !image3.isSelected() && !image4.isSelected() && !image6.isSelected() && !image7.isSelected() && !image8.isSelected()))
								{  
									image5.setIcon(new ImageIcon(getImage("hatsune-miku-vocaloid-1715")));
				                }
							}
							else
							{
								image5.setIcon(new ImageIcon(getImage("hatsune-miku-vocaloid-1715_obscured")));
							}
						}
					});
					GridBagConstraints gbc_image5 = new GridBagConstraints();
					gbc_image5.insets = new Insets(0, 0, 0, 5);
					gbc_image5.gridx = 0;
					gbc_image5.gridy = 1;
					image5.setIcon(new ImageIcon(getImage("hatsune-miku-vocaloid-1715")));
					panel.add(image5, gbc_image5);

					image6 = new JCheckBox();
					image6.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image6.isSelected())
							{
								if (!(!image1.isSelected() && !image2.isSelected() && !image3.isSelected() && !image4.isSelected() && !image5.isSelected() && !image7.isSelected() && !image8.isSelected()))
								{  
									image6.setIcon(new ImageIcon(getImage("hmny")));
				                }
							}
							else
							{
								image6.setIcon(new ImageIcon(getImage("hmny_obscured")));
							}
						}
					});
					GridBagConstraints gbc_image6 = new GridBagConstraints();
					gbc_image6.insets = new Insets(0, 0, 0, 5);
					gbc_image6.gridx = 1;
					gbc_image6.gridy = 1;
					image6.setIcon(new ImageIcon(getImage("hmny")));
					panel.add(image6, gbc_image6);

					image7 = new JCheckBox();
					image7.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image7.isSelected())
							{
								if (!(!image1.isSelected() && !image2.isSelected() && !image3.isSelected() && !image4.isSelected() && !image5.isSelected() && !image6.isSelected() && !image8.isSelected()))
								{  
									image7.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid")));
				                }
							}
							else
							{
								image7.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid_obscured")));
							}
						}
					});
					GridBagConstraints gbc_image7 = new GridBagConstraints();
					gbc_image7.insets = new Insets(0, 0, 0, 5);
					gbc_image7.gridx = 2;
					gbc_image7.gridy = 1;
					image7.setIcon(new ImageIcon(getImage("Hatsune-Miku-Vocaloid")));
					panel.add(image7, gbc_image7);
					
					image8 = new JCheckBox();
					image8.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (image8.isSelected())
							{
								if (!(!image1.isSelected() && !image2.isSelected() && !image3.isSelected() && !image4.isSelected() && !image5.isSelected() && !image6.isSelected() && !image7.isSelected()))
								{  
									image8.setIcon(new ImageIcon(getImage("Headphone")));
				                }
							}
							else
							{
								image8.setIcon(new ImageIcon(getImage("Headphone_obscured")));
							}
						}
					});
					GridBagConstraints gbc_image8 = new GridBagConstraints();
					gbc_image8.gridx = 3;
					gbc_image8.gridy = 1;
					image8.setIcon(new ImageIcon(getImage("Headphone")));
					panel.add(image8, gbc_image8);
				}
	
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("Salva");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String selectedImage = "";
							
							if (image1.isSelected())
								selectedImage += "miku_mem" + ":";
							if (image2.isSelected())
								selectedImage += "Headphone.." + ":";
							if (image3.isSelected())
								selectedImage += "Hatsune-Miku-Vocaloid.." + ":";
							if (image4.isSelected())
								selectedImage += "Hatsune-Miku-Vocaloid..." + ":";
							if (image5.isSelected())
								selectedImage += "hatsune-miku-vocaloid-1715" + ":";
							if (image6.isSelected())
								selectedImage += "hmny" + ":";
							if (image7.isSelected())
								selectedImage += "Hatsune-Miku-Vocaloid" + ":";
							if (image8.isSelected())
								selectedImage += "Headphone";
								
							AnimeIndex.appProp.setProperty("Default_Music_Images", selectedImage);
							MusicImageChooserDialog.this.dispose();
						}
					});
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
			}
		}
	}
	
	private BufferedImage getImage(String name)
	{
		File file = null;
		try
		{
			file = new File(MusicImageChooserDialog.class.getResource("/image/" + name + ".png").toURI());
		}
		catch (URISyntaxException e2)
		{
			MAMUtil.writeLog(e2);
			e2.printStackTrace();
		}
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(file);
		}
		catch (IOException e1)
		{
			MAMUtil.writeLog(e1);
			e1.printStackTrace();
		}
		BufferedImage preview = MAMUtil.getScaledImage(image, 100, 100);
		
		return preview;
	}
}
