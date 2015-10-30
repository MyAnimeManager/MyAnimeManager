package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import util.SingleSuggestionPanel;


public class SuggestionDialogV2 extends JDialog {
	
	/**
	 * Create the dialog.
	 */
	public SuggestionDialogV2()
	{
		setBounds(100, 100, 450, 292);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				JPanel panel = new JPanel();
				scrollPane.setViewportView(panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{0, 0};
				gbl_panel.rowHeights = new int[]{0, 0, 0};
				gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
				{
					SingleSuggestionPanel singleSuggestionPanel = new SingleSuggestionPanel("Anime 1");
					GridBagConstraints gbc_singleSuggestionPanel = new GridBagConstraints();
					gbc_singleSuggestionPanel.insets = new Insets(0, 0, 5, 0);
					gbc_singleSuggestionPanel.fill = GridBagConstraints.HORIZONTAL;
					gbc_singleSuggestionPanel.gridx = 0;
					gbc_singleSuggestionPanel.gridy = 0;
					panel.add(singleSuggestionPanel, gbc_singleSuggestionPanel);
				}
				{
					SingleSuggestionPanel singleSuggestionPanel = new SingleSuggestionPanel("Anime 2");
					GridBagConstraints gbc_singleSuggestionPanel = new GridBagConstraints();
					gbc_singleSuggestionPanel.fill = GridBagConstraints.HORIZONTAL;
					gbc_singleSuggestionPanel.gridx = 0;
					gbc_singleSuggestionPanel.gridy = 1;
					panel.add(singleSuggestionPanel, gbc_singleSuggestionPanel);
				}
			}
		}
	}
	
}
