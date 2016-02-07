package util.window;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ThanksDialog extends JDialog
{

	public ThanksDialog()
	{
		setTitle("Ringraziamenti");
		setType(Type.POPUP);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setBounds(100, 100, 524, 300);
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
			gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			{
				Component verticalStrut = Box.createVerticalStrut(2);
				GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
				gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
				gbc_verticalStrut.gridx = 0;
				gbc_verticalStrut.gridy = 0;
				panel.add(verticalStrut, gbc_verticalStrut);
			}
			{
				JLabel lblRingraziamoPerIl = new JLabel("Ringraziamo per il supporto :");
				GridBagConstraints gbc_lblRingraziamoPerIl = new GridBagConstraints();
				gbc_lblRingraziamoPerIl.insets = new Insets(0, 0, 5, 0);
				gbc_lblRingraziamoPerIl.gridx = 0;
				gbc_lblRingraziamoPerIl.gridy = 1;
				panel.add(lblRingraziamoPerIl, gbc_lblRingraziamoPerIl);
			}
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
				gbc_banner.gridy = 2;
				panel.add(banner, gbc_banner);
			}
		}
	}

}
