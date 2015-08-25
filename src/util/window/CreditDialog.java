package util.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import main.AnimeIndex;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;

public class CreditDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public CreditDialog()
	{
		super(AnimeIndex.frame,true);
		setTitle("My Anime Manager  v"+AnimeIndex.VERSION);
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreditDialog.class.getResource("/image/icon.png")));
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 21, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel_1 = new JLabel("\u2022 Versione :");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 0;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			JLabel label = new JLabel("");
			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					String link = "https://github.com/MyAnimeManager/MyAnimeManager#my-anime-manager";
					try {
						URI uriLink = new URI(link);
						Desktop.getDesktop().browse(uriLink);
					} catch (URISyntaxException a) {
					} catch (IOException a) {
				}
				}
			});
			label.setFont(new Font("Tahoma", Font.PLAIN, 11));
//			label.setForeground(Color.RED);
			label.setText("My Anime Manager   v"+AnimeIndex.VERSION);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.gridwidth = 5;
			gbc_label.insets = new Insets(0, 0, 5, 0);
			gbc_label.gridx = 2;
			gbc_label.gridy = 0;
			contentPanel.add(label, gbc_label);
		}
		{
			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(new ImageIcon(getClass().getResource("/image/credit.png")));
			GridBagConstraints gbc_imageLabel = new GridBagConstraints();
			gbc_imageLabel.fill = GridBagConstraints.VERTICAL;
			gbc_imageLabel.gridheight = 7;
			gbc_imageLabel.insets = new Insets(0, 0, 0, 5);
			gbc_imageLabel.gridx = 0;
			gbc_imageLabel.gridy = 0;
			contentPanel.add(imageLabel, gbc_imageLabel);
		}
		{
			JLabel txtpnTestoDiProva = new JLabel();
			txtpnTestoDiProva.setOpaque(false);
			txtpnTestoDiProva.setBorder(null);
			txtpnTestoDiProva.setBackground(new Color(19,19,19));
			txtpnTestoDiProva.setText("\u2022 Sviluppato da :     ");
			GridBagConstraints gbc_txtpnTestoDiProva = new GridBagConstraints();
			gbc_txtpnTestoDiProva.gridwidth = 6;
			gbc_txtpnTestoDiProva.insets = new Insets(0, 0, 5, 0);
			gbc_txtpnTestoDiProva.fill = GridBagConstraints.BOTH;
			gbc_txtpnTestoDiProva.gridx = 1;
			gbc_txtpnTestoDiProva.gridy = 1;
			contentPanel.add(txtpnTestoDiProva, gbc_txtpnTestoDiProva);
		}
		{
			JButton btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton butt = (JButton) e.getSource();
					JDialog dial = (JDialog) butt.getTopLevelAncestor();
					dial.dispose();
				}
			});
			{
				JLabel patreonLabel = new JLabel("");
				patreonLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						patreonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						String link = "https://www.patreon.com/";
						try {
							URI uriLink = new URI(link);
							Desktop.getDesktop().browse(uriLink);
						} catch (URISyntaxException a) {
						} catch (IOException a) {
					}
					}
				});
				{
					JLabel label = new JLabel("");
					label.setBorder(new LineBorder(new Color(40, 40, 40), 1, true));
					label.setIcon(new ImageIcon(CreditDialog.class.getResource("/image/horo_avatar.jpg")));
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.anchor = GridBagConstraints.EAST;
					gbc_label.insets = new Insets(0, 0, 5, 5);
					gbc_label.gridx = 1;
					gbc_label.gridy = 2;
					contentPanel.add(label, gbc_label);
				}
				{
					JLabel lblNewLabel = new JLabel("Yesod30");
					GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
					gbc_lblNewLabel.gridwidth = 2;
					gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
					gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
					gbc_lblNewLabel.gridx = 2;
					gbc_lblNewLabel.gridy = 2;
					contentPanel.add(lblNewLabel, gbc_lblNewLabel);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
					gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
					gbc_horizontalStrut.gridx = 4;
					gbc_horizontalStrut.gridy = 2;
					contentPanel.add(horizontalStrut, gbc_horizontalStrut);
				}
				{
					JLabel label = new JLabel("");
					label.setBorder(new LineBorder(new Color(40, 40, 40), 1, true));
					label.setIcon(new ImageIcon(CreditDialog.class.getResource("/image/unknown avatar.jpg")));
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.insets = new Insets(0, 0, 5, 5);
					gbc_label.gridx = 5;
					gbc_label.gridy = 2;
					contentPanel.add(label, gbc_label);
				}
				{
					JLabel lblItto = new JLabel("iTTo");
					GridBagConstraints gbc_lblItto = new GridBagConstraints();
					gbc_lblItto.anchor = GridBagConstraints.WEST;
					gbc_lblItto.insets = new Insets(0, 0, 5, 0);
					gbc_lblItto.gridx = 6;
					gbc_lblItto.gridy = 2;
					contentPanel.add(lblItto, gbc_lblItto);
				}
				{
					Component verticalStrut = Box.createVerticalStrut(2);
					GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
					gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
					gbc_verticalStrut.gridx = 1;
					gbc_verticalStrut.gridy = 3;
					contentPanel.add(verticalStrut, gbc_verticalStrut);
				}
				{
					JLabel lblNewLabel_2 = new JLabel("\u2022 Contatti :");
					GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
					gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
					gbc_lblNewLabel_2.gridx = 1;
					gbc_lblNewLabel_2.gridy = 4;
					contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(5);
					GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
					gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
					gbc_horizontalStrut.gridx = 2;
					gbc_horizontalStrut.gridy = 4;
					contentPanel.add(horizontalStrut, gbc_horizontalStrut);
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(CreditDialog.class.getResource("/image/github.png")));
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.anchor = GridBagConstraints.WEST;
					gbc_label.gridwidth = 4;
					gbc_label.insets = new Insets(0, 0, 5, 0);
					gbc_label.gridx = 3;
					gbc_label.gridy = 4;
					contentPanel.add(label, gbc_label);
					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							label.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							String link = "https://github.com/MyAnimeManager/MyAnimeManager#contatti";
							try {
								URI uriLink = new URI(link);
								Desktop.getDesktop().browse(uriLink);
							} catch (URISyntaxException a) {
							} catch (IOException a) {
						}
						}
					});
				}
				patreonLabel.setIcon(new ImageIcon(getClass().getResource("/image/support-us-on-patreon.png")));
				GridBagConstraints gbc_patreonLabel = new GridBagConstraints();
				gbc_patreonLabel.gridwidth = 6;
				gbc_patreonLabel.anchor = GridBagConstraints.SOUTH;
				gbc_patreonLabel.insets = new Insets(0, 0, 5, 0);
				gbc_patreonLabel.gridx = 1;
				gbc_patreonLabel.gridy = 5;
				contentPanel.add(patreonLabel, gbc_patreonLabel);
			}
			GridBagConstraints gbc_btnOk = new GridBagConstraints();
			gbc_btnOk.anchor = GridBagConstraints.SOUTH;
			gbc_btnOk.gridwidth = 6;
			gbc_btnOk.gridx = 1;
			gbc_btnOk.gridy = 6;
			contentPanel.add(btnOk, gbc_btnOk);
		}
	}

}
