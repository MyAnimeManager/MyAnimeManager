package util.window;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import main.AnimeIndex;
import util.AnimeData;
import util.MAMUtil;
import util.PatternFilter;

public class SetLinkDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	public JTextField linkField;
	public JTextField linkNameField;

	/**
	 * Create the dialog.
	 */
	public SetLinkDialog()
	{
		super(AnimeIndex.frame, true);
		setType(Type.POPUP);
		setTitle("Link");
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setBounds(100, 100, 342, 116);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 38, 326, 0 };
		gbl_contentPanel.rowHeights = new int[] { 31, 25, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUrl = new JLabel("URL :");
			GridBagConstraints gbc_lblUrl = new GridBagConstraints();
			gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
			gbc_lblUrl.anchor = GridBagConstraints.WEST;
			gbc_lblUrl.gridx = 0;
			gbc_lblUrl.gridy = 0;
			contentPanel.add(lblUrl, gbc_lblUrl);
		}
		{
			linkField = new JTextField(AnimeIndex.animeInformation.getLink());
			GridBagConstraints gbc_linkField = new GridBagConstraints();
			gbc_linkField.insets = new Insets(0, 0, 5, 0);
			gbc_linkField.fill = GridBagConstraints.BOTH;
			gbc_linkField.gridx = 1;
			gbc_linkField.gridy = 0;
			contentPanel.add(linkField, gbc_linkField);
			linkField.setColumns(10);
		}
		{
			JLabel lblNome = new JLabel("Nome :");
			GridBagConstraints gbc_lblNome = new GridBagConstraints();
			gbc_lblNome.insets = new Insets(0, 0, 0, 5);
			gbc_lblNome.anchor = GridBagConstraints.EAST;
			gbc_lblNome.gridx = 0;
			gbc_lblNome.gridy = 1;
			contentPanel.add(lblNome, gbc_lblNome);
		}
		{
			linkNameField = new JTextField();
			GridBagConstraints gbc_linkNameField = new GridBagConstraints();
			gbc_linkNameField.fill = GridBagConstraints.BOTH;
			gbc_linkNameField.gridx = 1;
			gbc_linkNameField.gridy = 1;
			contentPanel.add(linkNameField, gbc_linkNameField);
			linkNameField.setColumns(10);
			((AbstractDocument) linkNameField.getDocument()).setDocumentFilter(new PatternFilter("[\\p{IsAlphabetic}\\p{IsDigit}\\p{IsWhite_Space}]{0,16}"));
		}
		if (AnimeIndex.animeInformation.setLinkButton.getText().equals("Imposta Link") == false)
			if (AnimeIndex.animeInformation.setLinkButton.getText().equals("Link") == false)
				linkNameField.setText(AnimeIndex.animeInformation.setLinkButton.getText());
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					JButton cancelButton = new JButton("Annulla");
					cancelButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
						{
							JButton but = (JButton) e.getSource();
							JDialog dialog = (JDialog) but.getTopLevelAncestor();
							dialog.dispose();
						}
					});
					JButton saveButton = new JButton("Salva");
					saveButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e)
						{
							AnimeIndex.animeInformation.setLink(linkField.getText());
							if (linkField.getText() != null && !(linkField.getText().isEmpty()))
							{
								AnimeIndex.animeInformation.btnOpen.setEnabled(true);
								if (linkNameField.getText() != null && !(linkNameField.getText().isEmpty()))
									AnimeIndex.animeInformation.setLinkButton.setText(linkNameField.getText());
								else
									AnimeIndex.animeInformation.setLinkButton.setText("Link");

								TreeMap<String, AnimeData> map = MAMUtil.getMap();
								String name = AnimeIndex.animeInformation.lblAnimeName.getText();
								AnimeData oldData = map.get(name);
								AnimeData data = new AnimeData(oldData.getCurrentEpisode(), oldData.getTotalEpisode(), oldData.getFansub(), oldData.getNote(), oldData.getImageName(), oldData.getDay(), oldData.getId(), AnimeIndex.animeInformation.setLinkButton.getText(), linkField.getText(), oldData.getAnimeType(), oldData.getReleaseDate(), oldData.getFinishDate(), oldData.getDurationEp(), oldData.getBd());
								map.put(name, data);

								JButton but = (JButton) e.getSource();
								JDialog dialog = (JDialog) but.getTopLevelAncestor();
								dialog.dispose();
							}
							else if (linkNameField.getText() == null && linkNameField.getText().isEmpty())
								AnimeIndex.animeInformation.btnOpen.setEnabled(false);
							else
								JOptionPane.showMessageDialog(AnimeIndex.mainFrame, "Nessun link impostato.", "Errore!", JOptionPane.ERROR_MESSAGE);

						}
					});
					buttonPane.setLayout(new GridLayout(0, 3, 0, 0));
					{
						JButton btnRimuovi = new JButton("Rimuovi");
						btnRimuovi.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e)
							{
								AnimeIndex.animeInformation.setLinkButton.setText("Imposta Link");
								linkNameField.setText("");
								linkField.setText("");
								JList list = MAMUtil.getJList();
								TreeMap<String, AnimeData> map = MAMUtil.getMap();
								String name = (String) list.getSelectedValue();
								AnimeData oldData = map.get(name);
								AnimeData newData = new AnimeData(oldData.getCurrentEpisode(), oldData.getTotalEpisode(), oldData.getFansub(), oldData.getNote(), oldData.getImageName(), oldData.getDay(), oldData.getId(), "", "", oldData.getAnimeType(), oldData.getReleaseDate(), oldData.getFinishDate(), oldData.getDurationEp(), oldData.getBd());
								map.put(name, newData);
								AnimeIndex.animeInformation.link = "";
								AnimeIndex.animeInformation.btnOpen.setEnabled(false);
								JButton but = (JButton) e.getSource();
								JDialog dialog = (JDialog) but.getTopLevelAncestor();
								dialog.dispose();
							}
						});
						buttonPane.add(btnRimuovi);
					}
					saveButton.setActionCommand("OK");
					buttonPane.add(saveButton);
					getRootPane().setDefaultButton(saveButton);
					buttonPane.add(cancelButton);
				}
			}
		}
	}

	public String getLinkField()
	{
		return linkField.getText();
	}
}
