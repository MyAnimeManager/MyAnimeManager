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

import util.SuggestionTaskPane;


public class SuggestionDialog extends JDialog {
	
	/**
	 * Create the dialog.
	 */
	public SuggestionDialog()
	{
		setBounds(100, 100, 450, 290);
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
				gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
				gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
				{
					SuggestionTaskPane suggestionTaskPane = new SuggestionTaskPane((String) null, (String) null);
					GridBagConstraints gbc_suggestionTaskPane = new GridBagConstraints();
					gbc_suggestionTaskPane.insets = new Insets(0, 0, 5, 0);
					gbc_suggestionTaskPane.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionTaskPane.gridx = 0;
					gbc_suggestionTaskPane.gridy = 0;
					panel.add(suggestionTaskPane, gbc_suggestionTaskPane);
				}
				{
					SuggestionTaskPane suggestionTaskPane = new SuggestionTaskPane((String) null, (String) null);
					GridBagConstraints gbc_suggestionTaskPane = new GridBagConstraints();
					gbc_suggestionTaskPane.insets = new Insets(0, 0, 5, 0);
					gbc_suggestionTaskPane.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionTaskPane.gridx = 0;
					gbc_suggestionTaskPane.gridy = 1;
					panel.add(suggestionTaskPane, gbc_suggestionTaskPane);
				}
				{
					SuggestionTaskPane suggestionTaskPane = new SuggestionTaskPane((String) null, (String) null);
					GridBagConstraints gbc_suggestionTaskPane = new GridBagConstraints();
					gbc_suggestionTaskPane.fill = GridBagConstraints.HORIZONTAL;
					gbc_suggestionTaskPane.gridx = 0;
					gbc_suggestionTaskPane.gridy = 2;
					panel.add(suggestionTaskPane, gbc_suggestionTaskPane);
				}
			}
		}
	}
	
}
