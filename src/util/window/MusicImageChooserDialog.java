package util.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.border.MatteBorder;
import java.awt.Color;


public class MusicImageChooserDialog extends JDialog
{
	
	private final JPanel contentPanel = new JPanel();
	private DefaultListModel imageModel = new DefaultListModel();
	private DefaultListModel activeModel = new DefaultListModel();
	private JList activeList;
	private JList imageList;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			MusicImageChooserDialog dialog = new MusicImageChooserDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public MusicImageChooserDialog()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				imageModel.addElement( "miku_mem");
				imageModel.addElement( "Headphone..");
				imageModel.addElement( "Hatsune-Miku-Vocaloid..");
				imageModel.addElement( "Hatsune-Miku-Vocaloid..");
				imageModel.addElement( "hatsune-miku-vocaloid-1715");
				imageModel.addElement( "hmny");
				imageModel.addElement( "Hatsune-Miku-Vocaloid");
				imageModel.addElement( "Headphone");
				
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
				activeModel.addElement( "X");
			}
		});
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportBorder(new MatteBorder(0, 1, 0, 0,new Color(0, 0, 0)));
			contentPanel.add(scrollPane, BorderLayout.WEST);
			{
				imageList = new JList(imageModel);
				imageList.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2)
						{
							int index = imageList.getSelectedIndex();
							activeModel.remove(index);
							activeModel.add(index, "V");
						}
					}
				});
				imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(imageList);
			}
			{
				activeList = new JList(activeModel);
				activeList.setAutoscrolls(false);
				activeList.setMaximumSize(new Dimension(15, 4));
				activeList.setPreferredSize(new Dimension(20, 4));
				activeList.setSelectionModel(new DisabledItemSelectionModel());
				scrollPane.setRowHeaderView(activeList);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setName("Antepriam dell'immagine selezionata");
			contentPanel.add(panel, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
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
	}
	
	class DisabledItemSelectionModel extends DefaultListSelectionModel {

	    @Override
	    public void setSelectionInterval(int index0, int index1) {
	        super.setSelectionInterval(-1, -1);
	    }
	}
	
}
