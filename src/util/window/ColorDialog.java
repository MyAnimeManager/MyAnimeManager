package util.window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.AnimeIndexProperties;
import util.ColorProperties;
import util.FileManager;
import main.AnimeIndex;


public class ColorDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private static ColorDialog dialog;
	private static boolean changed = false;


//				colorProperties = ColorProperties.createProperties();
//				if (!colorProperties.getProperty("Button_color").equalsIgnoreCase("null"))
//				{
//					Color color = new Color(Integer.parseInt(colorProperties.getProperty("Button_color")));
//					UIManager.put("Button.background", color);
//				}


	/**
	 * Create the dialog.
	 */
	public ColorDialog()
	{
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Personalizzazione Colori");
		setBounds(100, 100, 404, 226);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JButton btnSfondo = new JButton("Sfondo");
			if (AnimeIndex.colorProp.getProperty("Background_color") != null && !AnimeIndex.colorProp.getProperty("Background_color").equalsIgnoreCase("null"))
				btnSfondo.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("Background_color"))));
			btnSfondo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel();
					int color = customize(panel);
					if (color != 0)
					{
					btnSfondo.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("Background_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnSfondo = new GridBagConstraints();
			gbc_btnSfondo.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnSfondo.insets = new Insets(0, 0, 5, 5);
			gbc_btnSfondo.gridx = 0;
			gbc_btnSfondo.gridy = 0;
			contentPanel.add(btnSfondo, gbc_btnSfondo);
		}
		{
			JButton btnBottoni = new JButton("Bottoni");
			if (AnimeIndex.colorProp.getProperty("Button_color") != null && !AnimeIndex.colorProp.getProperty("Button_color").equalsIgnoreCase("null"))
				btnBottoni.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("Button_color"))));
			btnBottoni.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton butt = new JButton("Prova");
					int color = customize(butt);
					if (color != 0)
					{
					btnBottoni.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("Button_color", Integer.toString(color));
					changed = true;
					}
					
				}
			});
			GridBagConstraints gbc_btnBottoni = new GridBagConstraints();
			gbc_btnBottoni.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnBottoni.insets = new Insets(0, 0, 5, 0);
			gbc_btnBottoni.gridx = 1;
			gbc_btnBottoni.gridy = 0;
			contentPanel.add(btnBottoni, gbc_btnBottoni);
		}
		{
			JButton btnCampiDiInput = new JButton("Campi di Input");
			if (AnimeIndex.colorProp.getProperty("TextField_color") != null && !AnimeIndex.colorProp.getProperty("TextField_color").equalsIgnoreCase("null"))
				btnCampiDiInput.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("TextField_color"))));
			btnCampiDiInput.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTextField text = new JTextField();
					int color = customize(text);
					if (color != 0)
					{
					btnCampiDiInput.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("TextField_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnCampiDiInput = new GridBagConstraints();
			gbc_btnCampiDiInput.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnCampiDiInput.insets = new Insets(0, 0, 5, 5);
			gbc_btnCampiDiInput.gridx = 0;
			gbc_btnCampiDiInput.gridy = 1;
			contentPanel.add(btnCampiDiInput, gbc_btnCampiDiInput);
		}
		{
			JButton btnLabel = new JButton("Label");
			if (AnimeIndex.colorProp.getProperty("Label_color") != null && !AnimeIndex.colorProp.getProperty("Label_color").equalsIgnoreCase("null"))
				btnLabel.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("Label_color"))));
			btnLabel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JLabel label = new JLabel("Prova");
					int color = customize(label);
					if (color != 0)
					{
						btnLabel.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("Label_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnLabel = new GridBagConstraints();
			gbc_btnLabel.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnLabel.insets = new Insets(0, 0, 5, 0);
			gbc_btnLabel.gridx = 1;
			gbc_btnLabel.gridy = 1;
			contentPanel.add(btnLabel, gbc_btnLabel);
		}
		{
			JButton btnCheckbox = new JButton("CheckBox");
			if (AnimeIndex.colorProp.getProperty("CheckBox_color") != null && !AnimeIndex.colorProp.getProperty("CheckBox_color").equalsIgnoreCase("null"))
				btnCheckbox.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("CheckBox_color"))));
			btnCheckbox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox checkBox = new JCheckBox();
					int color = customize(checkBox);
					if (color != 0)
					{
					btnCheckbox.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("CheckBox_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnCheckbox = new GridBagConstraints();
			gbc_btnCheckbox.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnCheckbox.insets = new Insets(0, 0, 5, 5);
			gbc_btnCheckbox.gridx = 0;
			gbc_btnCheckbox.gridy = 2;
			contentPanel.add(btnCheckbox, gbc_btnCheckbox);
		}
		{
			JButton btnMenu = new JButton("Menu");
			if (AnimeIndex.colorProp.getProperty("Menu_color") != null && !AnimeIndex.colorProp.getProperty("Menu_color").equalsIgnoreCase("null"))
				btnMenu.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("Menu_color"))));
			btnMenu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel();
					int color = customize(panel);
					if (color != 0)
					{
					btnMenu.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("Menu_color", Integer.toString(color));
					changed = true;
					}
					
				}
			});
			GridBagConstraints gbc_btnRadiobox = new GridBagConstraints();
			gbc_btnRadiobox.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnRadiobox.insets = new Insets(0, 0, 5, 0);
			gbc_btnRadiobox.gridx = 1;
			gbc_btnRadiobox.gridy = 2;
			contentPanel.add(btnMenu, gbc_btnRadiobox);
		}
		{
			JButton btnListe = new JButton("Liste");
			if (AnimeIndex.colorProp.getProperty("List_color") != null && !AnimeIndex.colorProp.getProperty("List_color").equalsIgnoreCase("null"))
				btnListe.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("List_color"))));
			btnListe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel model = new DefaultListModel();
					model.addElement("Prova 1");
					model.addElement("Prova 2");
					model.addElement("Prova 3");
					model.addElement("Prova 4");
					JList list = new JList(model);
					int color = customize(list);
					if (color != 0)
					{
					btnListe.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("List_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnListe = new GridBagConstraints();
			gbc_btnListe.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnListe.insets = new Insets(0, 0, 5, 5);
			gbc_btnListe.gridx = 0;
			gbc_btnListe.gridy = 3;
			contentPanel.add(btnListe, gbc_btnListe);
		}
		{
			JButton btnSeparatori = new JButton("Separatori");
			if (AnimeIndex.colorProp.getProperty("Separator_color") != null && !AnimeIndex.colorProp.getProperty("Separator_color").equalsIgnoreCase("null"))
				btnSeparatori.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("Separator_color"))));
			btnSeparatori.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JSeparator separator = new JSeparator();
					int color = customize(separator);
					if (color != 0)
					{
					btnSeparatori.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("Separator_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnSeparatori = new GridBagConstraints();
			gbc_btnSeparatori.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnSeparatori.insets = new Insets(0, 0, 5, 0);
			gbc_btnSeparatori.gridx = 1;
			gbc_btnSeparatori.gridy = 3;
			contentPanel.add(btnSeparatori, gbc_btnSeparatori);
		}
		{
			JButton btnBarreDiCaricamento = new JButton("Barre di Caricamento");
			if (AnimeIndex.colorProp.getProperty("ProgressBar_color") != null && !AnimeIndex.colorProp.getProperty("ProgressBar_color").equalsIgnoreCase("null"))
				btnBarreDiCaricamento.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("ProgressBar_color"))));
			btnBarreDiCaricamento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JProgressBar bar = new JProgressBar();
					bar.setIndeterminate(true);
					bar.setOrientation(SwingConstants.VERTICAL);
					int color = customize(bar);
					if (color != 0)
					{
					btnBarreDiCaricamento.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("ProgressBar_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnBrareDiCaricamento = new GridBagConstraints();
			gbc_btnBrareDiCaricamento.insets = new Insets(0, 0, 0, 5);
			gbc_btnBrareDiCaricamento.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnBrareDiCaricamento.gridx = 0;
			gbc_btnBrareDiCaricamento.gridy = 4;
			contentPanel.add(btnBarreDiCaricamento, gbc_btnBrareDiCaricamento);
		}
		{
			JButton btnListeADiscesa = new JButton("Liste a Discesa");
			if (AnimeIndex.colorProp.getProperty("ComboBox_color") != null && !AnimeIndex.colorProp.getProperty("ComboBox_color").equalsIgnoreCase("null"))
				btnListeADiscesa.setBackground(new Color(Integer.parseInt(AnimeIndex.colorProp.getProperty("ComboBox_color"))));
			btnListeADiscesa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox comboBox = new JComboBox();
					int color = customize(comboBox);
					if (color != 0)
					{
					btnListeADiscesa.setBackground(new Color(color));
					AnimeIndex.colorProp.setProperty("ComboBox_color", Integer.toString(color));
					changed = true;
					}
				}
			});
			GridBagConstraints gbc_btnListeADiscesa = new GridBagConstraints();
			gbc_btnListeADiscesa.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnListeADiscesa.gridx = 1;
			gbc_btnListeADiscesa.gridy = 4;
			contentPanel.add(btnListeADiscesa, gbc_btnListeADiscesa);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnTest = new JButton("Test");
				btnTest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TestDialog test = new TestDialog();
						test.setLocationRelativeTo(ColorDialog.dialog);
						test.setVisible(true);
					}
				});
				buttonPane.add(btnTest);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (changed)
						{
							int choiche = JOptionPane.showConfirmDialog(ColorDialog.this, "Per applicare le modifiche è necessario un riavvio. Riavviare ora?", "Riavvio richiesto", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							if (choiche == 0)
							{
								saveData();
								System.exit(0);
							}
							else
								changed = false;
						}
						else
							ColorDialog.this.dispose();
					}
				});
				{
					JButton btnReset = new JButton("Reset");
					btnReset.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							AnimeIndex.colorProp.clear();
							int choiche = JOptionPane.showConfirmDialog(ColorDialog.this, "Per applicare le modifiche è necessario un riavvio. Riavviare ora?", "Riavvio richiesto", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
							if (choiche == 0)
							{
								saveData();
								System.exit(0);
							}
						}
					});
					buttonPane.add(btnReset);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ColorDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private static int customize(JComponent componen)
	{		
				CustomizingDialog dial = new CustomizingDialog(componen);
				dial.setLocationRelativeTo(ColorDialog.dialog);
				dial.setVisible(true);
				int colorRGB = dial.color;
				return colorRGB;

	}

	private static void saveData()
	{
		FileManager.saveAnimeList("completed.anaconda", AnimeIndex.completedModel, AnimeIndex.completedMap);
		FileManager.saveAnimeList("airing.anaconda", AnimeIndex.airingModel, AnimeIndex.airingMap);
		FileManager.saveAnimeList("ova.anaconda", AnimeIndex.ovaModel, AnimeIndex.ovaMap);
		FileManager.saveAnimeList("film.anaconda", AnimeIndex.filmModel, AnimeIndex.filmMap);
		FileManager.saveAnimeList("toSee.anaconda", AnimeIndex.completedToSeeModel, AnimeIndex.completedToSeeMap);
		FileManager.saveWishList();
		FileManager.saveExclusionList();
		
		ExitSaveDialog.deleteUselessImage(AnimeIndex.completedDeletedAnime);
		ExitSaveDialog.deleteUselessImage(AnimeIndex.airingDeletedAnime);
		ExitSaveDialog.deleteUselessImage(AnimeIndex.ovaDeletedAnime);
		ExitSaveDialog.deleteUselessImage(AnimeIndex.filmDeletedAnime);
		ExitSaveDialog.deleteUselessImage(AnimeIndex.completedToSeeDeletedAnime);
		AnimeIndexProperties.saveProperties(AnimeIndex.appProp);
		ColorProperties.saveProperties(AnimeIndex.colorProp);
	}
}
