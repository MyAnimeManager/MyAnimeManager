package util.window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;


public class ColorDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private static ColorDialog dialog;
	public static Properties colorProperties;
	private static boolean changed = false;

//	public static void main(String[] args)
//	{
//		EventQueue.invokeLater(new Runnable() {
//			public void run()
//			{
//				try {
//			         UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
//			        } catch (Exception e) {
//			          System.out.println("Substance Graphite failed to initialize");
//			        }
//				try {
//			          UIManager.setLookAndFeel(new SubstanceGraphiteGlassLookAndFeel());
//			        } catch (Exception e) {
//			          System.out.println("Substance Graphite failed to initialize");
//			        }
//				colorProperties = ColorProperties.createProperties();
//				if (!colorProperties.getProperty("Button_color").equalsIgnoreCase("null"))
//				{
//					Color color = new Color(Integer.parseInt(colorProperties.getProperty("Button_color")));
//					UIManager.put("Button.background", color);
//				}
//				try {
//					dialog = new ColorDialog();
//					dialog.setVisible(true);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the dialog.
	 */
	public ColorDialog()
	{
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				ColorProperties.saveProperties(colorProperties);
//				System.exit(0);
//			}
//		});
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
			btnSfondo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel();
					int color = customize(panel);
					btnSfondo.setBackground(new Color(color));
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
			btnBottoni.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton butt = new JButton("Prova");
					int color = customize(butt);
					btnBottoni .setBackground(new Color(color));
					colorProperties.setProperty("Button_color", Integer.toString(color));
					
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
			btnCampiDiInput.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTextField text = new JTextField();
					int color = customize(text);
					btnCampiDiInput .setBackground(new Color(color));
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
			btnLabel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JLabel label = new JLabel("Prova");
					int color = customize(label);
					btnLabel .setBackground(new Color(color));
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
			btnCheckbox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox checkBox = new JCheckBox();
					int color = customize(checkBox);
					btnCheckbox .setBackground(new Color(color));
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
			JButton btnRadiobox = new JButton("RadioBox");
			btnRadiobox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JRadioButton radioButton = new JRadioButton();
					int color = customize(radioButton);
					btnRadiobox .setBackground(new Color(color));
					
				}
			});
			GridBagConstraints gbc_btnRadiobox = new GridBagConstraints();
			gbc_btnRadiobox.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnRadiobox.insets = new Insets(0, 0, 5, 0);
			gbc_btnRadiobox.gridx = 1;
			gbc_btnRadiobox.gridy = 2;
			contentPanel.add(btnRadiobox, gbc_btnRadiobox);
		}
		{
			JButton btnListe = new JButton("Liste");
			btnListe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DefaultListModel model = new DefaultListModel();
					model.addElement("Prova 1");
					model.addElement("Prova 2");
					model.addElement("Prova 3");
					model.addElement("Prova 4");
					JList list = new JList(model);
					int color = customize(list);
					btnListe .setBackground(new Color(color));
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
			btnSeparatori.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JSeparator separator = new JSeparator();
					int color = customize(separator);
					btnSeparatori .setBackground(new Color(color));
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
			JButton btnBrareDiCaricamento = new JButton("Brare di Caricamento");
			btnBrareDiCaricamento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JProgressBar bar = new JProgressBar();
					bar.setIndeterminate(true);
					bar.setOrientation(SwingConstants.VERTICAL);
					int color = customize(bar);
					btnBrareDiCaricamento .setBackground(new Color(color));
				}
			});
			GridBagConstraints gbc_btnBrareDiCaricamento = new GridBagConstraints();
			gbc_btnBrareDiCaricamento.insets = new Insets(0, 0, 0, 5);
			gbc_btnBrareDiCaricamento.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnBrareDiCaricamento.gridx = 0;
			gbc_btnBrareDiCaricamento.gridy = 4;
			contentPanel.add(btnBrareDiCaricamento, gbc_btnBrareDiCaricamento);
		}
		{
			JButton btnListeADiscesa = new JButton("Liste a Discesa");
			btnListeADiscesa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox comboBox = new JComboBox();
					int color = customize(comboBox);
					btnListeADiscesa .setBackground(new Color(color));
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
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
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
				if (colorRGB != 0)
					return colorRGB;
				else
					return 0; //TODO far ritornare il valore salvato precedentemente nelle prop

	}

}
