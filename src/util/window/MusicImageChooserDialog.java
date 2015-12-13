package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import util.MAMUtil;


public class MusicImageChooserDialog extends JDialog
{
	
	private final JPanel contentPanel = new JPanel();
	private DefaultListModel imageModel = new DefaultListModel();
	private DefaultListModel activeModel = new DefaultListModel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			MusicImageChooserDialog dialog = new MusicImageChooserDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public MusicImageChooserDialog()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				imageModel.addElement( "miku_mem");
				imageModel.addElement( "Headphone..");
				imageModel.addElement( "Hatsune-Miku-Vocaloid..");
				imageModel.addElement( "Hatsune-Miku-Vocaloid..");
				imageModel.addElement( "hatsune-miku-vocaloid-1715");
				imageModel.addElement( "hmny");
				imageModel.addElement( "Hatsune-Miku-Vocaloid");
				imageModel.addElement( "Headphone");
				
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
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
					JCheckBox image1 = new JCheckBox();
					GridBagConstraints gbc_image1 = new GridBagConstraints();
					gbc_image1.insets = new Insets(0, 0, 5, 5);
					gbc_image1.gridx = 0;
					gbc_image1.gridy = 0;
					image1.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image1, gbc_image1);

					JCheckBox image2 = new JCheckBox();
					GridBagConstraints gbc_image2 = new GridBagConstraints();
					gbc_image2.insets = new Insets(0, 0, 5, 5);
					gbc_image2.gridx = 1;
					gbc_image2.gridy = 0;
					image2.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image2, gbc_image2);

					JCheckBox image3 = new JCheckBox();
					GridBagConstraints gbc_image3 = new GridBagConstraints();
					gbc_image3.insets = new Insets(0, 0, 5, 5);
					gbc_image3.gridx = 2;
					gbc_image3.gridy = 0;
					image3.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image3, gbc_image3);

					JCheckBox image4 = new JCheckBox();
					GridBagConstraints gbc_image4 = new GridBagConstraints();
					gbc_image4.insets = new Insets(0, 0, 5, 0);
					gbc_image4.gridx = 3;
					gbc_image4.gridy = 0;
					image4.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image4, gbc_image4);

					JCheckBox image5 = new JCheckBox();
					GridBagConstraints gbc_image5 = new GridBagConstraints();
					gbc_image5.insets = new Insets(0, 0, 0, 5);
					gbc_image5.gridx = 0;
					gbc_image5.gridy = 1;
					image5.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image5, gbc_image5);

					JCheckBox image6 = new JCheckBox();
					GridBagConstraints gbc_image6 = new GridBagConstraints();
					gbc_image6.insets = new Insets(0, 0, 0, 5);
					gbc_image6.gridx = 1;
					gbc_image6.gridy = 1;
					image6.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image6, gbc_image6);

					JCheckBox image7 = new JCheckBox();
					GridBagConstraints gbc_image7 = new GridBagConstraints();
					gbc_image7.insets = new Insets(0, 0, 0, 5);
					gbc_image7.gridx = 2;
					gbc_image7.gridy = 1;
					image7.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image7, gbc_image7);
					
					JCheckBox image8 = new JCheckBox();
					GridBagConstraints gbc_image8 = new GridBagConstraints();
					gbc_image8.gridx = 3;
					gbc_image8.gridy = 1;
					image8.setIcon(new ImageIcon(getImage("Headphone")));
					image1.setSelectedIcon(new ImageIcon(getImage("hmny")));
					panel.add(image8, gbc_image8);
			}
		}
		{
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
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
