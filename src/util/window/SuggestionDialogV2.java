package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.ScrollableSizeHint;


public class SuggestionDialogV2 extends JDialog {
	private JXCollapsiblePane collapsiblePane;
	
	/**
	 * Create the dialog.
	 */
	public SuggestionDialogV2()
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
					JLabel lblNewLabel = new JLabel("New label");
					lblNewLabel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (!collapsiblePane.isCollapsed())
								collapsiblePane.setCollapsed(true);
							else
								collapsiblePane.setCollapsed(false);
						}
					});
					panel.add(lblNewLabel);
				}
				{
					collapsiblePane = new JXCollapsiblePane();
					collapsiblePane.setScrollableHeightHint(ScrollableSizeHint.MINIMUM_STRETCH);
					collapsiblePane.setCollapsed(true);
					panel.add(collapsiblePane);
					{
						JLabel lblAnime = new JLabel("Anime1");
						collapsiblePane.add(lblAnime, BorderLayout.NORTH);
					}
					{
						JLabel lblProva = new JLabel("Prova");
						collapsiblePane.add(lblProva, BorderLayout.SOUTH);
					}
				}
			}
		}
	}
	
}
