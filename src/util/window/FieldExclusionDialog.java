package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JCheckBox;

import main.AnimeIndex;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FieldExclusionDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JCheckBox currentEpCheckBox;
	private JCheckBox startDateCheckBox;
	private JCheckBox durationCheckBox;
	private JCheckBox typeCheckBox;
	private JCheckBox finishDateCheckBox;
	private JCheckBox totalEpCheckBox;

	/**
	 * Create the dialog.
	 */
	public FieldExclusionDialog()
	{
		super(AnimeIndex.frame, true);
		setResizable(false);
		setTitle("Esclusione campi");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				if (AnimeIndex.appProp.getProperty("excludeCurrentEp").equalsIgnoreCase("true"))
					currentEpCheckBox.setSelected(true);
				
				if (AnimeIndex.appProp.getProperty("excludeTotalEp").equalsIgnoreCase("true"))
					totalEpCheckBox.setSelected(true);
				
				if (AnimeIndex.appProp.getProperty("excludeDuration").equalsIgnoreCase("true"))
					durationCheckBox.setSelected(true);
				
				if (AnimeIndex.appProp.getProperty("excludeStartingDate").equalsIgnoreCase("true"))
					startDateCheckBox.setSelected(true);
				
				if (AnimeIndex.appProp.getProperty("excludeFinishDate").equalsIgnoreCase("true"))
					finishDateCheckBox.setSelected(true);
				
				if (AnimeIndex.appProp.getProperty("excludeType").equalsIgnoreCase("true"))
					typeCheckBox.setSelected(true);
				
			}
		});
		setBounds(100, 100, 253, 168);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			currentEpCheckBox = new JCheckBox("Episodio Corrente");
			GridBagConstraints gbc_currentEpCheckBox = new GridBagConstraints();
			gbc_currentEpCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_currentEpCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_currentEpCheckBox.gridx = 0;
			gbc_currentEpCheckBox.gridy = 0;
			contentPanel.add(currentEpCheckBox, gbc_currentEpCheckBox);
		}
		{
			totalEpCheckBox = new JCheckBox("Episodi Totali");
			GridBagConstraints gbc_totalEpCheckBox = new GridBagConstraints();
			gbc_totalEpCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_totalEpCheckBox.insets = new Insets(0, 0, 5, 0);
			gbc_totalEpCheckBox.gridx = 1;
			gbc_totalEpCheckBox.gridy = 0;
			contentPanel.add(totalEpCheckBox, gbc_totalEpCheckBox);
		}
		{
			startDateCheckBox = new JCheckBox("Data di Inizio");
			GridBagConstraints gbc_startDateCheckBox = new GridBagConstraints();
			gbc_startDateCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_startDateCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_startDateCheckBox.gridx = 0;
			gbc_startDateCheckBox.gridy = 1;
			contentPanel.add(startDateCheckBox, gbc_startDateCheckBox);
		}
		{
			finishDateCheckBox = new JCheckBox("Data di fine");
			GridBagConstraints gbc_finishDateCheckBox = new GridBagConstraints();
			gbc_finishDateCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_finishDateCheckBox.insets = new Insets(0, 0, 5, 0);
			gbc_finishDateCheckBox.gridx = 1;
			gbc_finishDateCheckBox.gridy = 1;
			contentPanel.add(finishDateCheckBox, gbc_finishDateCheckBox);
		}
		{
			durationCheckBox = new JCheckBox("Durata");
			GridBagConstraints gbc_durationCheckBox = new GridBagConstraints();
			gbc_durationCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_durationCheckBox.insets = new Insets(0, 0, 0, 5);
			gbc_durationCheckBox.gridx = 0;
			gbc_durationCheckBox.gridy = 2;
			contentPanel.add(durationCheckBox, gbc_durationCheckBox);
		}
		{
			typeCheckBox = new JCheckBox("Tipo");
			GridBagConstraints gbc_typeCheckBox = new GridBagConstraints();
			gbc_typeCheckBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_typeCheckBox.gridx = 1;
			gbc_typeCheckBox.gridy = 2;
			contentPanel.add(typeCheckBox, gbc_typeCheckBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (currentEpCheckBox.isSelected())
							AnimeIndex.appProp.setProperty("excludeCurrentEp", "true");
						else
							AnimeIndex.appProp.setProperty("excludeCurrentEp", "false");
						
						if (totalEpCheckBox.isSelected())
							AnimeIndex.appProp.setProperty("excludeTotalEp", "true");
						else
							AnimeIndex.appProp.setProperty("excludeTotalEp", "false");
						
						if (durationCheckBox.isSelected())
							AnimeIndex.appProp.setProperty("excludeDuration", "true");
						else
							AnimeIndex.appProp.setProperty("excludeDuration", "false");
						
						if (startDateCheckBox.isSelected())
							AnimeIndex.appProp.setProperty("excludeStartingDate", "true");
						else
							AnimeIndex.appProp.setProperty("excludeStartingDate", "false");
						
						if (finishDateCheckBox.isSelected())
							AnimeIndex.appProp.setProperty("excludeFinishDate", "true");
						else
							AnimeIndex.appProp.setProperty("excludeFinishDate", "false");
						
						if (typeCheckBox.isSelected())
							AnimeIndex.appProp.setProperty("excludeType", "true");
						else
							AnimeIndex.appProp.setProperty("excludeType", "false");

						FieldExclusionDialog.this.dispose();
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						FieldExclusionDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
