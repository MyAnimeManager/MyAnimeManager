package util.window;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

import java.awt.Insets;

import main.AnimeIndex;

import org.apache.commons.lang3.StringEscapeUtils;

import util.MAMUtil;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.Toolkit;


public class SupportersDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();

	public SupportersDialog()
	{
		super(AnimeIndex.frame, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SupportersDialog.class.getResource("/image/donation.png")));
		setTitle("Sostenitori");
		setResizable(false);
		setBounds(100, 100, 340, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{184, 144, 0};
		gbl_contentPanel.rowHeights = new int[]{228, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JTextArea donatorsList = new JTextArea();
				donatorsList.setLineWrap(true);
				donatorsList.setWrapStyleWord(true);
				donatorsList.setEditable(false);
				donatorsList.setOpaque(false);
				scrollPane.setViewportView(donatorsList);
				scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
				scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 11));
				donatorsList.setText(loadSupporters());
			}
		}
		{
			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon(SupportersDialog.class.getResource("/image/Yuki.png")));
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.SOUTHWEST;
			gbc_label.gridx = 1;
			gbc_label.gridy = 0;
			contentPanel.add(label, gbc_label);
		}
		{
			JLabel lblRingraziamoPerLa = new JLabel("RINGRAZIAMO PER LA LORO DONAZIONE :");
			getContentPane().add(lblRingraziamoPerLa, BorderLayout.NORTH);
			lblRingraziamoPerLa.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			JLabel lblFreedonation = new JLabel("");
			lblFreedonation.setToolTipText("Se anche tu gradisci il nostro lavoro e desideri sostenerci");
			getContentPane().add(lblFreedonation, BorderLayout.SOUTH);
			lblFreedonation.setHorizontalAlignment(SwingConstants.CENTER);
			lblFreedonation.setIcon(new ImageIcon(SupportersDialog.class.getResource("/image/Paypal-donate.png")));
			lblFreedonation.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e)
				{
					lblFreedonation.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseReleased(MouseEvent e)
				{
					String link = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RFJLMVCQYZEQG";
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
		}
	}
	private String loadSupporters()
	{
		URL url; // The URL to read
		HttpURLConnection conn = null; // The actual connection to the web page
		BufferedReader rr; // Used to read results from the web page
		String line; // An individual line of the web page HTML
		String result = ""; // A long string containing all the HTML
		String address = "http://myanimemanagerupdate.webstarts.com/supporters.html";
		try
		{
			url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			rr = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((line = rr.readLine()) != null)
				result += line;
			rr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MAMUtil.writeLog(e);
		}
		result = StringEscapeUtils.unescapeJava(result);
		result = result.substring(result.indexOf("[supporters]") + 12, result.indexOf("[/supporters]"));
		return result;
	}
}
