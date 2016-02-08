package util.window;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Toolkit;

public class ThanksDialog extends JDialog
{

	public ThanksDialog()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(ThanksDialog.class.getResource("/image/partners-icon.png")));
		setTitle("Collaboratori");
		setType(Type.POPUP);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setBounds(100, 100, 524, 256);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 518, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			{
				JLabel banner = new JLabel("");
				banner.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseEntered(MouseEvent e)
					{
						banner.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseClicked(MouseEvent e)
					{
						String link = "http://redanimedatabase.forumcommunity.net/";
						try
						{
							URI uriLink = new URI(link);
							Desktop.getDesktop().browse(uriLink);
						}
						catch (URISyntaxException a)
						{
						}
						catch (IOException a)
						{
						}
					}
				});
				banner.setIcon(new ImageIcon(ThanksDialog.class.getResource("/image/RAD_Banner.png")));
				GridBagConstraints gbc_banner = new GridBagConstraints();
				gbc_banner.gridx = 0;
				gbc_banner.gridy = 0;
				panel.add(banner, gbc_banner);
			}
		}
	}

}
