package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.jdesktop.swingx.JXTaskPane;
import javax.swing.JLabel;


public class SuggestionDialogV1 extends JDialog {
	
	/**
	 * Create the dialog.
	 */
	public SuggestionDialogV1()
	{
		setBounds(100, 100, 450, 222);
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
				panel.setLayout(new GridLayout(5, 0, 0, 0));
				{
					JXTaskPane taskPane = new JXTaskPane();
					taskPane.setTitle("Anime 1");
					panel.add(taskPane);
					{
						JLabel lblPaasdaadasdas = new JLabel("Paasdaadasdas");
						taskPane.add(lblPaasdaadasdas, BorderLayout.SOUTH);
					}
				}
				{
					JXTaskPane taskPane = new JXTaskPane();
					taskPane.setTitle("Anime 2");
					panel.add(taskPane);
				}
				{
					JXTaskPane taskPane = new JXTaskPane();
					taskPane.setCollapsed(true);
					panel.add(taskPane);
				}
				{
					JXTaskPane taskPane = new JXTaskPane();
					taskPane.setCollapsed(true);
					panel.add(taskPane);
				}
				{
					JXTaskPane taskPane = new JXTaskPane();
					taskPane.setCollapsed(true);
					panel.add(taskPane);
				}
			}
		}
	}
	
}
