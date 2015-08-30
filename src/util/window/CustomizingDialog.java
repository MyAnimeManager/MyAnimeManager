package util.window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.pushingpixels.substance.internal.contrib.randelshofer.quaqua.colorchooser.ColorWheelChooser;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagLayout;
import java.awt.Insets;


public class CustomizingDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JColorChooser colorChooser;
	private Color colorChoosed;
	public int color;

	/**
	 * Create the dialog.
	 */
	public CustomizingDialog(JComponent component)
	{
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 290, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.WEST);
			{
				colorChooser = new JColorChooser();
				colorChooser.setPreferredSize(new Dimension(180, 155));
				AbstractColorChooserPanel panels[] = {new ColorWheelChooser()};
				colorChooser.setChooserPanels(panels);
				
//				System.out.println(Arrays.toString(colorChooser.getChooserPanels()));
				colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e)
					{
						colorChoosed = colorChooser.getColor();
						if (component instanceof JLabel)
							component.setForeground(colorChoosed);
						else
						component.setBackground(colorChoosed);
					}
				});
				panel.add(colorChooser);
			}
		}
		{
			JPanel componentPanel = new JPanel();
			contentPanel.add(componentPanel, BorderLayout.EAST);
			{
				componentPanel.add(component, BorderLayout.CENTER);
				
				if (component instanceof JPanel || component instanceof JSeparator)
				{
					Component area = Box.createRigidArea(new Dimension(40,40));
					component.add(area);
				}
			}
			componentPanel.setLayout(new BorderLayout(0, 0));
			{
				Component verticalStrut = Box.createVerticalStrut(60);
				componentPanel.add(verticalStrut);
			}

			{
				Component verticalStrut = Box.createVerticalStrut(30);
				componentPanel.add(verticalStrut);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (colorChoosed != null)
							color = colorChoosed.getRGB();
						//TODO salvare nel properties
						CustomizingDialog.this.dispose();
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
						CustomizingDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
