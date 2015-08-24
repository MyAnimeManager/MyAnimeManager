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

public class CreditDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public CreditDialog()
	{
		super(AnimeIndex.frame,true);
		setTitle("Crediti");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CreditDialog.class.getResource("/image/icon.png")));
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{21, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(new ImageIcon(getClass().getResource("/image/credit.png")));
			GridBagConstraints gbc_imageLabel = new GridBagConstraints();
			gbc_imageLabel.fill = GridBagConstraints.VERTICAL;
			gbc_imageLabel.gridheight = 6;
			gbc_imageLabel.insets = new Insets(0, 0, 0, 5);
			gbc_imageLabel.gridx = 0;
			gbc_imageLabel.gridy = 0;
			contentPanel.add(imageLabel, gbc_imageLabel);
		}
		{
			JTextPane txtpnTestoDiProva = new JTextPane();
			txtpnTestoDiProva.setOpaque(false);
			txtpnTestoDiProva.setBorder(null);
			txtpnTestoDiProva.setBackground(new Color(19,19,19));
			txtpnTestoDiProva.setText("Sviluppato da :     ");
			txtpnTestoDiProva.setEditable(false);
			GridBagConstraints gbc_txtpnTestoDiProva = new GridBagConstraints();
			gbc_txtpnTestoDiProva.gridwidth = 2;
			gbc_txtpnTestoDiProva.insets = new Insets(0, 0, 5, 0);
			gbc_txtpnTestoDiProva.fill = GridBagConstraints.BOTH;
			gbc_txtpnTestoDiProva.gridx = 1;
			gbc_txtpnTestoDiProva.gridy = 0;
			StyledDocument doc = txtpnTestoDiProva.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
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
					label.setIcon(new ImageIcon(CreditDialog.class.getResource("/image/unknown avatar.jpg")));
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.insets = new Insets(0, 0, 5, 5);
					gbc_label.gridx = 1;
					gbc_label.gridy = 1;
					contentPanel.add(label, gbc_label);
				}
				{
					JLabel lblNewLabel = new JLabel("Yesod30");
					GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
					gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
					gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
					gbc_lblNewLabel.gridx = 2;
					gbc_lblNewLabel.gridy = 1;
					contentPanel.add(lblNewLabel, gbc_lblNewLabel);
				}
				{
					JLabel label = new JLabel("");
					label.setBorder(new LineBorder(new Color(40, 40, 40), 1, true));
					label.setIcon(new ImageIcon(CreditDialog.class.getResource("/image/unknown avatar.jpg")));
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.insets = new Insets(0, 0, 5, 5);
					gbc_label.gridx = 1;
					gbc_label.gridy = 2;
					contentPanel.add(label, gbc_label);
				}
				{
					JLabel lblItto = new JLabel("iTTo");
					GridBagConstraints gbc_lblItto = new GridBagConstraints();
					gbc_lblItto.anchor = GridBagConstraints.WEST;
					gbc_lblItto.insets = new Insets(0, 0, 5, 0);
					gbc_lblItto.gridx = 2;
					gbc_lblItto.gridy = 2;
					contentPanel.add(lblItto, gbc_lblItto);
				}
				{
					JTextPane txtpnCreditiContatti = new JTextPane();
					txtpnCreditiContatti.setText("Crediti :\r\n\r\nContatti :");
					txtpnCreditiContatti.setOpaque(false);
					txtpnCreditiContatti.setEditable(false);
					txtpnCreditiContatti.setBorder(null);
					txtpnCreditiContatti.setBackground(new Color(19, 19, 19));
					GridBagConstraints gbc_txtpnCreditiContatti = new GridBagConstraints();
					gbc_txtpnCreditiContatti.gridwidth = 2;
					gbc_txtpnCreditiContatti.insets = new Insets(0, 0, 5, 0);
					gbc_txtpnCreditiContatti.fill = GridBagConstraints.BOTH;
					gbc_txtpnCreditiContatti.gridx = 1;
					gbc_txtpnCreditiContatti.gridy = 3;
					contentPanel.add(txtpnCreditiContatti, gbc_txtpnCreditiContatti);
				}
				patreonLabel.setIcon(new ImageIcon(getClass().getResource("/image/support-us-on-patreon.png")));
				GridBagConstraints gbc_patreonLabel = new GridBagConstraints();
				gbc_patreonLabel.gridwidth = 2;
				gbc_patreonLabel.anchor = GridBagConstraints.SOUTH;
				gbc_patreonLabel.insets = new Insets(0, 0, 5, 0);
				gbc_patreonLabel.gridx = 1;
				gbc_patreonLabel.gridy = 4;
				contentPanel.add(patreonLabel, gbc_patreonLabel);
			}
			GridBagConstraints gbc_btnOk = new GridBagConstraints();
			gbc_btnOk.gridwidth = 2;
			gbc_btnOk.gridx = 1;
			gbc_btnOk.gridy = 5;
			contentPanel.add(btnOk, gbc_btnOk);
		}
	}

}
