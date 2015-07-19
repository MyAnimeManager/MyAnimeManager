package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import javax.swing.JToggleButton;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import java.awt.Color;

public class SetFilterDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SetFilterDialog dialog = new SetFilterDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SetFilterDialog() {
		setBounds(100, 100, 223, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JRadioButton rdbtnBlueray = new JRadioButton("Blu-Ray");
			GridBagConstraints gbc_rdbtnBlueray = new GridBagConstraints();
			gbc_rdbtnBlueray.anchor = GridBagConstraints.WEST;
			gbc_rdbtnBlueray.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnBlueray.gridx = 0;
			gbc_rdbtnBlueray.gridy = 0;
			contentPanel.add(rdbtnBlueray, gbc_rdbtnBlueray);
		}
		{
			JRadioButton rdbtnOavRilasciati = new JRadioButton("OAV Rilasciati");
			GridBagConstraints gbc_rdbtnOavRilasciati = new GridBagConstraints();
			gbc_rdbtnOavRilasciati.anchor = GridBagConstraints.WEST;
			gbc_rdbtnOavRilasciati.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnOavRilasciati.gridx = 1;
			gbc_rdbtnOavRilasciati.gridy = 0;
			contentPanel.add(rdbtnOavRilasciati, gbc_rdbtnOavRilasciati);
		}
		{
			JRadioButton rdbtnStoppate = new JRadioButton("Sospese");
			GridBagConstraints gbc_rdbtnStoppate = new GridBagConstraints();
			gbc_rdbtnStoppate.anchor = GridBagConstraints.WEST;
			gbc_rdbtnStoppate.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnStoppate.gridx = 0;
			gbc_rdbtnStoppate.gridy = 1;
			contentPanel.add(rdbtnStoppate, gbc_rdbtnStoppate);
		}
		{
			JRadioButton rdbtnOavInUscita = new JRadioButton("OAV in Uscita");
			GridBagConstraints gbc_rdbtnOavInUscita = new GridBagConstraints();
			gbc_rdbtnOavInUscita.anchor = GridBagConstraints.WEST;
			gbc_rdbtnOavInUscita.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnOavInUscita.gridx = 1;
			gbc_rdbtnOavInUscita.gridy = 1;
			contentPanel.add(rdbtnOavInUscita, gbc_rdbtnOavInUscita);
		}
		{
			JRadioButton rdbtnAcquistate = new JRadioButton("Acquistate");
			GridBagConstraints gbc_rdbtnAcquistate = new GridBagConstraints();
			gbc_rdbtnAcquistate.anchor = GridBagConstraints.WEST;
			gbc_rdbtnAcquistate.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnAcquistate.gridx = 0;
			gbc_rdbtnAcquistate.gridy = 2;
			contentPanel.add(rdbtnAcquistate, gbc_rdbtnAcquistate);
		}
		{
			JRadioButton rdbtnFilmRilasciati = new JRadioButton("Film Rilasciati");
			GridBagConstraints gbc_rdbtnFilmRilasciati = new GridBagConstraints();
			gbc_rdbtnFilmRilasciati.anchor = GridBagConstraints.WEST;
			gbc_rdbtnFilmRilasciati.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnFilmRilasciati.gridx = 1;
			gbc_rdbtnFilmRilasciati.gridy = 2;
			contentPanel.add(rdbtnFilmRilasciati, gbc_rdbtnFilmRilasciati);
		}
		{
			JRadioButton rdbtnRilascioIrregolare = new JRadioButton("Irregolari");
			GridBagConstraints gbc_rdbtnRilascioIrregolare = new GridBagConstraints();
			gbc_rdbtnRilascioIrregolare.anchor = GridBagConstraints.WEST;
			gbc_rdbtnRilascioIrregolare.insets = new Insets(0, 0, 0, 5);
			gbc_rdbtnRilascioIrregolare.gridx = 0;
			gbc_rdbtnRilascioIrregolare.gridy = 3;
			contentPanel.add(rdbtnRilascioIrregolare, gbc_rdbtnRilascioIrregolare);
		}
		{
			JRadioButton rdbtnFilmInUscita = new JRadioButton("Film in Uscita");
			GridBagConstraints gbc_rdbtnFilmInUscita = new GridBagConstraints();
			gbc_rdbtnFilmInUscita.anchor = GridBagConstraints.WEST;
			gbc_rdbtnFilmInUscita.gridx = 1;
			gbc_rdbtnFilmInUscita.gridy = 3;
			contentPanel.add(rdbtnFilmInUscita, gbc_rdbtnFilmInUscita);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton okButton = new JButton("Applica");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
