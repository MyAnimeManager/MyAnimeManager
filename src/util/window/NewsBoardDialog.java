package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;

import main.AnimeIndex;
import util.MAMUtil;
import util.task.RSSNewsTask;

public class NewsBoardDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private LinkedHashMap<String, String> animeMap;
	RSSNewsTask task = new RSSNewsTask("http://redanimedatabase.forumcommunity.net/rss.php?c=570618&src=tag");
	private JPanel newsPanel;
	private JLabel titleLabel;
	private JButton updateButton;

	public NewsBoardDialog()
	{
		super(AnimeIndex.frame, false);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e)
			{
				task.execute();
			}
		});
		setUndecorated(true);
		setType(Type.UTILITY);
		setBounds(100, 100, 795, 125);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new MatteBorder(1, 0, 0, 0, new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBorder(null);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				newsPanel = new JPanel();
				newsPanel.setBorder(null);
				scrollPane.setViewportView(newsPanel);
				GridBagLayout gbl_newsPanel = new GridBagLayout();
				gbl_newsPanel.columnWidths = new int[] { 0, 0 };
				gbl_newsPanel.rowHeights = new int[] { 0, 0 };
				gbl_newsPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
				gbl_newsPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
				newsPanel.setLayout(gbl_newsPanel);
			}
		}
		{
			JPanel titlePanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) titlePanel.getLayout();
			flowLayout.setVgap(0);
			contentPanel.add(titlePanel, BorderLayout.NORTH);
			{
				titleLabel = new JLabel("");
				titlePanel.add(titleLabel);
				titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
				titleLabel.setOpaque(true);
				titleLabel.setFont(AnimeIndex.segui.deriveFont(16F));
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				GridBagLayout gbl_buttonPane = new GridBagLayout();
				gbl_buttonPane.columnWidths = new int[] { 397, 0, 0 };
				gbl_buttonPane.rowHeights = new int[] { 8, 0 };
				gbl_buttonPane.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
				gbl_buttonPane.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
				buttonPane.setLayout(gbl_buttonPane);
			}
			JButton okButton = new JButton("         \u2191 \u2191 \u2191 \u2191 \u2191");
			okButton.setFont(MAMUtil.loadFont().deriveFont(12f));
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					new Timer(1, new ActionListener() {

						int size = 125;

						@Override
						public void actionPerformed(ActionEvent e)
						{
							NewsBoardDialog.this.setSize(795, size--);
							AnimeIndex.mainPanel.requestFocus();
							if (NewsBoardDialog.this.getHeight() == 0)
							{
								((Timer) e.getSource()).stop();
								NewsBoardDialog.this.dispose();
							}
						}
					}).start();
				}
			});
			GridBagConstraints gbc_okButton = new GridBagConstraints();
			gbc_okButton.fill = GridBagConstraints.BOTH;
			gbc_okButton.gridx = 0;
			gbc_okButton.gridy = 0;
			buttonPane.add(okButton, gbc_okButton);
			{
				updateButton = new JButton();
				updateButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						newsPanel.removeAll();
						task = new RSSNewsTask("http://redanimedatabase.forumcommunity.net/rss.php?c=570618&src=tag");
						task.addPropertyChangeListener(new PropertyChangeListener() {

							@Override
							public void propertyChange(PropertyChangeEvent evt)
							{
								if (evt.getPropertyName().equals("state"))
								{
									if (evt.getNewValue().toString().equalsIgnoreCase("started"))
									{
										titleLabel.setIcon(null);
										titleLabel.setText("Aggiornamento Dati...");
										updateButton.setEnabled(false);
									}
									if (evt.getNewValue().toString().equalsIgnoreCase("done"))
									{
										setNews();
										titleLabel.setText("");
										titleLabel.setIcon(new ImageIcon(NewsBoardDialog.class.getResource("/image/rdlogos.png")));
										updateButton.setEnabled(true);
									}
								}
							}
						});
						task.execute();
					}
				});
				updateButton.setIcon(new ImageIcon(AnimeInformation.class.getResource("/image/refresh-icon16.png")));
				GridBagConstraints gbc_updateButton = new GridBagConstraints();
				gbc_updateButton.fill = GridBagConstraints.VERTICAL;
				gbc_updateButton.gridx = 1;
				gbc_updateButton.gridy = 0;
				buttonPane.add(updateButton, gbc_updateButton);
			}
		}

		task.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if (evt.getPropertyName().equals("state"))
				{
					if (evt.getNewValue().toString().equalsIgnoreCase("started"))
					{
						titleLabel.setIcon(null);
						titleLabel.setText("Caricamento Dati...");
						updateButton.setEnabled(false);
					}
					if (evt.getNewValue().toString().equalsIgnoreCase("done"))
					{
						setNews();
						titleLabel.setText("");
						titleLabel.setIcon(new ImageIcon(NewsBoardDialog.class.getResource("/image/rdlogos.png")));
						updateButton.setEnabled(true);
					}
				}
			}
		});
	}

	public void setMap(LinkedHashMap<String, String> newsMap)
	{
		animeMap = newsMap;
	}

	private void setNews()
	{
		int row = 0;
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.fill = GridBagConstraints.HORIZONTAL;
		for (Map.Entry<String, String> entry : animeMap.entrySet())
		{
			String name = entry.getKey();
			String link = entry.getValue();

			if (name.endsWith(" By AF &"))
				name = name + " RAD";
			else if (name.endsWith(" By") || name.endsWith(" da") || name.endsWith(" su"))
				name = name.substring(0, name.length() - 3);

			JLabel nameLabel = new JLabel(name);

			if ((row % 2) == 0)
			{
				nameLabel.setOpaque(true);
				nameLabel.setBackground(Color.BLACK);
			}
			if (name.contains("Download") || name.contains("Streaming"))
				nameLabel.setForeground(new Color(0, 65, 255));
			else if (name.contains("Reading Online"))
				nameLabel.setForeground(new Color(0, 180, 180));
			else if (name.contains("BreakingItaly"))
				nameLabel.setForeground(new Color(0, 180, 0));
			else if (name.contains("Anime Hentai"))
				nameLabel.setForeground(Color.PINK);
			else
				nameLabel.setForeground(Color.RED);
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			nameLabel.addMouseListener(getClickLink(link));
			nameLabel.setFont(AnimeIndex.segui.deriveFont(12f));
			gbc_titleLabel.gridy = row;
			newsPanel.add(nameLabel, gbc_titleLabel);
			row++;
		}
		newsPanel.revalidate();
		newsPanel.repaint();
	}

	private MouseAdapter getClickLink(String link)
	{
		MouseAdapter mouse = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					URI uriLink = new URI(link);
					Desktop.getDesktop().browse(uriLink);
				}
				catch (Exception e1)
				{
					MAMUtil.writeLog(e1);
					e1.printStackTrace();
				}
			}
		};

		return mouse;
	}
}
