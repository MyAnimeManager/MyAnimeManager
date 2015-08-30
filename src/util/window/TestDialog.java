package util.window;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;


public class TestDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try {
			TestDialog dialog = new TestDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TestDialog()
	{
		setModal(true);
		setBounds(100, 100, 450, 365);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JButton btnNewButton = new JButton("Prova");
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
			gbc_btnNewButton.gridx = 0;
			gbc_btnNewButton.gridy = 0;
			contentPanel.add(btnNewButton, gbc_btnNewButton);
		}
		{
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 0;
			gbc_textField.gridy = 1;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
		}
		{
			JSeparator separator = new JSeparator();
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.insets = new Insets(0, 0, 5, 0);
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 2;
			contentPanel.add(separator, gbc_separator);
		}
		{
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Prova 1", "Prova 2"}));
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0;
			gbc_comboBox.gridy = 3;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JCheckBox chckbxNewCheckBox = new JCheckBox("Prova");
			GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
			gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox.gridx = 0;
			gbc_chckbxNewCheckBox.gridy = 4;
			contentPanel.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		}
		{
			JRadioButton rdbtnNewRadioButton = new JRadioButton("Prova");
			GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
			gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnNewRadioButton.gridx = 0;
			gbc_rdbtnNewRadioButton.gridy = 6;
			contentPanel.add(rdbtnNewRadioButton, gbc_rdbtnNewRadioButton);
		}
		{
			JProgressBar progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.insets = new Insets(0, 0, 5, 0);
			gbc_progressBar.gridx = 0;
			gbc_progressBar.gridy = 7;
			contentPanel.add(progressBar, gbc_progressBar);
		}
		{
			JLabel Prova = new JLabel("Prova");
			GridBagConstraints gbc_Prova = new GridBagConstraints();
			gbc_Prova.insets = new Insets(0, 0, 5, 0);
			gbc_Prova.gridx = 0;
			gbc_Prova.gridy = 8;
			contentPanel.add(Prova, gbc_Prova);
		}
		{
			JList list = new JList();
			list.setModel(new AbstractListModel() {
				String[] values = new String[] {"Prova 1", "Prova 2", "Prova 3"};
				public int getSize() {
					return values.length;
				}
				public Object getElementAt(int index) {
					return values[index];
				}
			});
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 0;
			gbc_list.gridy = 9;
			contentPanel.add(list, gbc_list);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Chiudi");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TestDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
