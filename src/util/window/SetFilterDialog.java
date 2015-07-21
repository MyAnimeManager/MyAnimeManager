package util.window;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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

import main.AnimeIndex;

import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

public class SetFilterDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static final ButtonGroup filterGroup = new ButtonGroup();
	private JCheckBox blueray;
	private JCheckBox sospese;
	private JCheckBox acquistate;
	private JCheckBox oavRilasciati;
	private JCheckBox oavInUscita;
	private JCheckBox filmRilasciati;
	private JCheckBox filmInUscita;
	private JCheckBox usciteDelGiorno;
	private JCheckBox irregolari;

	/**
	 * Create the dialog.
	 */
	public SetFilterDialog() {
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setType(Type.POPUP);
		setTitle("Filtri");
		setBounds(100, 100, 202, 189);
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
			blueray = new JCheckBox("Blu-Ray");
			blueray.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[0] = true;
				}
			});
			GridBagConstraints gbc_rdbtnBlueray = new GridBagConstraints();
			gbc_rdbtnBlueray.anchor = GridBagConstraints.WEST;
			gbc_rdbtnBlueray.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnBlueray.gridx = 0;
			gbc_rdbtnBlueray.gridy = 0;
			contentPanel.add(blueray, gbc_rdbtnBlueray);
		}
		{
			oavRilasciati = new JCheckBox("OAV Rilasciati");
			oavRilasciati.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[4] = true;
				}
			});
			GridBagConstraints gbc_rdbtnOavRilasciati = new GridBagConstraints();
			gbc_rdbtnOavRilasciati.anchor = GridBagConstraints.WEST;
			gbc_rdbtnOavRilasciati.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnOavRilasciati.gridx = 1;
			gbc_rdbtnOavRilasciati.gridy = 0;
			contentPanel.add(oavRilasciati, gbc_rdbtnOavRilasciati);
		}
		{
			sospese = new JCheckBox("Sospese");
			sospese.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[1] = true;
				}
			});
			GridBagConstraints gbc_rdbtnSospese = new GridBagConstraints();
			gbc_rdbtnSospese.anchor = GridBagConstraints.WEST;
			gbc_rdbtnSospese.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnSospese.gridx = 0;
			gbc_rdbtnSospese.gridy = 1;
			contentPanel.add(sospese, gbc_rdbtnSospese);
		}
		{
			oavInUscita = new JCheckBox("OAV in Uscita");
			oavInUscita.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[5] = true;
				}
			});
			GridBagConstraints gbc_rdbtnOavInUscita = new GridBagConstraints();
			gbc_rdbtnOavInUscita.anchor = GridBagConstraints.WEST;
			gbc_rdbtnOavInUscita.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnOavInUscita.gridx = 1;
			gbc_rdbtnOavInUscita.gridy = 1;
			contentPanel.add(oavInUscita, gbc_rdbtnOavInUscita);
		}
		{
			irregolari = new JCheckBox("Irregolari");
			irregolari.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[2] = true;
				}
			});
			GridBagConstraints gbc_rdbtnIrregolari = new GridBagConstraints();
			gbc_rdbtnIrregolari.anchor = GridBagConstraints.WEST;
			gbc_rdbtnIrregolari.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnIrregolari.gridx = 0;
			gbc_rdbtnIrregolari.gridy = 2;
			contentPanel.add(irregolari, gbc_rdbtnIrregolari);
		}
		{
			filmRilasciati = new JCheckBox("Film Rilasciati");
			filmRilasciati.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[6] = true;
				}
			});
			GridBagConstraints gbc_rdbtnFilmRilasciati = new GridBagConstraints();
			gbc_rdbtnFilmRilasciati.anchor = GridBagConstraints.WEST;
			gbc_rdbtnFilmRilasciati.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnFilmRilasciati.gridx = 1;
			gbc_rdbtnFilmRilasciati.gridy = 2;
			contentPanel.add(filmRilasciati, gbc_rdbtnFilmRilasciati);
		}
		{
			acquistate = new JCheckBox("Acquistate");
			acquistate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[3] = true;
				}
			});
			GridBagConstraints gbc_rdbtnAcquistate = new GridBagConstraints();
			gbc_rdbtnAcquistate.anchor = GridBagConstraints.WEST;
			gbc_rdbtnAcquistate.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnAcquistate.gridx = 0;
			gbc_rdbtnAcquistate.gridy = 3;
			contentPanel.add(acquistate, gbc_rdbtnAcquistate);
		}
		{
			filmInUscita = new JCheckBox("Film in Uscita");
			filmInUscita.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[7] = true;
				}
			});
			GridBagConstraints gbc_rdbtnFilmInUscita = new GridBagConstraints();
			gbc_rdbtnFilmInUscita.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnFilmInUscita.anchor = GridBagConstraints.WEST;
			gbc_rdbtnFilmInUscita.gridx = 1;
			gbc_rdbtnFilmInUscita.gridy = 3;
			contentPanel.add(filmInUscita, gbc_rdbtnFilmInUscita);
		}
		{
			usciteDelGiorno = new JCheckBox("Uscite del Giorno");
			usciteDelGiorno.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < AnimeIndex.filterArray.length; i++)
						AnimeIndex.filterArray[i] = false;
					AnimeIndex.filterArray[7] = true;
				}
			});
			GridBagConstraints gbc_rdbtnUsciteDelGiorno = new GridBagConstraints();
			gbc_rdbtnUsciteDelGiorno.gridwidth = 2;
			gbc_rdbtnUsciteDelGiorno.gridx = 0;
			gbc_rdbtnUsciteDelGiorno.gridy = 4;
			contentPanel.add(usciteDelGiorno, gbc_rdbtnUsciteDelGiorno);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton okButton = new JButton("Applica");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(filterGroup.getSelection() != null){
							AnimeIndex.setFilterButton.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/ellipse_icon1.png")));}
//TODO applica il filtro					
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Rimuovi");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						filterGroup.clearSelection();
						AnimeIndex.setFilterButton.setIcon(new ImageIcon(AnimeIndex.class.getResource("/image/ellipse_icon3.png")));
						String listName = AnimeIndex.getList();
						CardLayout cl = (CardLayout)(AnimeIndex.cardContainer.getLayout());
				        cl.show(AnimeIndex.cardContainer, listName);
						JButton but = (JButton) e.getSource();
						JDialog dialog = (JDialog) but.getTopLevelAncestor();
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		filterGroup.add(blueray);
		filterGroup.add(filmInUscita);
		filterGroup.add(filmRilasciati);
		filterGroup.add(oavInUscita);
		filterGroup.add(oavRilasciati);
		filterGroup.add(sospese);
		filterGroup.add(irregolari);
		filterGroup.add(usciteDelGiorno);
		filterGroup.add(acquistate);
		
		JCheckBox[] buttonArray ={blueray, sospese, irregolari, acquistate, oavRilasciati, oavInUscita ,filmRilasciati, filmInUscita, usciteDelGiorno};
		for (int i = 0; i < AnimeIndex.filterArray.length; i++)
		{
			if (AnimeIndex.filterArray[i])
			{
				buttonArray[i].setSelected(true);
			}
		}
	}
}
