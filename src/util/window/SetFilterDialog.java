package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
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
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetFilterDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup filterGroup = new ButtonGroup();
	private JRadioButton rdbtnBlueray;
	private JRadioButton rdbtnSospese;
	private JRadioButton rdbtnAcquistate;
	private JRadioButton rdbtnOavRilasciati;
	private JRadioButton rdbtnOavInUscita;
	private JRadioButton rdbtnFilmRilasciati;
	private JRadioButton rdbtnFilmInUscita;
	private JRadioButton rdbtnUsciteDelGiorno;
	private JRadioButton rdbtnIrregolari;

	/**
	 * Create the dialog.
	 */
	public SetFilterDialog() {
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.POPUP);
		setTitle("Filtri");
		setBounds(100, 100, 190, 189);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			rdbtnBlueray = new JRadioButton("Blu-Ray");
			GridBagConstraints gbc_rdbtnBlueray = new GridBagConstraints();
			gbc_rdbtnBlueray.anchor = GridBagConstraints.WEST;
			gbc_rdbtnBlueray.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnBlueray.gridx = 0;
			gbc_rdbtnBlueray.gridy = 0;
			contentPanel.add(rdbtnBlueray, gbc_rdbtnBlueray);
		}
		{
			rdbtnOavRilasciati = new JRadioButton("OAV Rilasciati");
			GridBagConstraints gbc_rdbtnOavRilasciati = new GridBagConstraints();
			gbc_rdbtnOavRilasciati.anchor = GridBagConstraints.WEST;
			gbc_rdbtnOavRilasciati.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnOavRilasciati.gridx = 1;
			gbc_rdbtnOavRilasciati.gridy = 0;
			contentPanel.add(rdbtnOavRilasciati, gbc_rdbtnOavRilasciati);
		}
		{
			rdbtnSospese = new JRadioButton("Sospese");
			GridBagConstraints gbc_rdbtnSospese = new GridBagConstraints();
			gbc_rdbtnSospese.anchor = GridBagConstraints.WEST;
			gbc_rdbtnSospese.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnSospese.gridx = 0;
			gbc_rdbtnSospese.gridy = 1;
			contentPanel.add(rdbtnSospese, gbc_rdbtnSospese);
		}
		{
			rdbtnOavInUscita = new JRadioButton("OAV in Uscita");
			GridBagConstraints gbc_rdbtnOavInUscita = new GridBagConstraints();
			gbc_rdbtnOavInUscita.anchor = GridBagConstraints.WEST;
			gbc_rdbtnOavInUscita.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnOavInUscita.gridx = 1;
			gbc_rdbtnOavInUscita.gridy = 1;
			contentPanel.add(rdbtnOavInUscita, gbc_rdbtnOavInUscita);
		}
		{
			rdbtnIrregolari = new JRadioButton("Irregolari");
			GridBagConstraints gbc_rdbtnIrregolari = new GridBagConstraints();
			gbc_rdbtnIrregolari.anchor = GridBagConstraints.WEST;
			gbc_rdbtnIrregolari.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnIrregolari.gridx = 0;
			gbc_rdbtnIrregolari.gridy = 2;
			contentPanel.add(rdbtnIrregolari, gbc_rdbtnIrregolari);
		}
		{
			rdbtnFilmRilasciati = new JRadioButton("Film Rilasciati");
			GridBagConstraints gbc_rdbtnFilmRilasciati = new GridBagConstraints();
			gbc_rdbtnFilmRilasciati.anchor = GridBagConstraints.WEST;
			gbc_rdbtnFilmRilasciati.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnFilmRilasciati.gridx = 1;
			gbc_rdbtnFilmRilasciati.gridy = 2;
			contentPanel.add(rdbtnFilmRilasciati, gbc_rdbtnFilmRilasciati);
		}
		{
			rdbtnAcquistate = new JRadioButton("Acquistate");
			GridBagConstraints gbc_rdbtnAcquistate = new GridBagConstraints();
			gbc_rdbtnAcquistate.anchor = GridBagConstraints.WEST;
			gbc_rdbtnAcquistate.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnAcquistate.gridx = 0;
			gbc_rdbtnAcquistate.gridy = 3;
			contentPanel.add(rdbtnAcquistate, gbc_rdbtnAcquistate);
		}
		{
			rdbtnFilmInUscita = new JRadioButton("Film in Uscita");
			GridBagConstraints gbc_rdbtnFilmInUscita = new GridBagConstraints();
			gbc_rdbtnFilmInUscita.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnFilmInUscita.anchor = GridBagConstraints.WEST;
			gbc_rdbtnFilmInUscita.gridx = 1;
			gbc_rdbtnFilmInUscita.gridy = 3;
			contentPanel.add(rdbtnFilmInUscita, gbc_rdbtnFilmInUscita);
		}
		{
			rdbtnUsciteDelGiorno = new JRadioButton("Uscite del Giorno");
			GridBagConstraints gbc_rdbtnUsciteDelGiorno = new GridBagConstraints();
			gbc_rdbtnUsciteDelGiorno.gridwidth = 2;
			gbc_rdbtnUsciteDelGiorno.gridx = 0;
			gbc_rdbtnUsciteDelGiorno.gridy = 4;
			contentPanel.add(rdbtnUsciteDelGiorno, gbc_rdbtnUsciteDelGiorno);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton okButton = new JButton("Applica");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Rimuovi");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		filterGroup.add(rdbtnBlueray);
		filterGroup.add(rdbtnFilmInUscita);
		filterGroup.add(rdbtnFilmRilasciati);
		filterGroup.add(rdbtnOavInUscita);
		filterGroup.add(rdbtnOavRilasciati);
		filterGroup.add(rdbtnSospese);
		filterGroup.add(rdbtnIrregolari);
		filterGroup.add(rdbtnUsciteDelGiorno);
		filterGroup.add(rdbtnAcquistate);
	}

}
