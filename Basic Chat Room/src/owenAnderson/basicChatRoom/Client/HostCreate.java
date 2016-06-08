package owenAnderson.basicChatRoom.Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class HostCreate extends BasicDialogWindow {

	private static final long serialVersionUID = -8471551805609795885L;
	private final JPanel contentPanel = new JPanel();
	private JTextField portFeild;
	private JPasswordField passwordField;

	/**
	 * Create the dialog.
	 */
	public HostCreate() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HostCreate.class.getResource("/owenAnderson/basicChatRoom/Client/res/ConnectionIcon.png")));
		setTitle("Host Chat");
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 389, 188);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(10, 11, 86, 14);
		contentPanel.add(lblPort);
		
		portFeild = new JTextField();
		portFeild.setBounds(10, 36, 86, 20);
		contentPanel.add(portFeild);
		portFeild.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 67, 86, 14);
		contentPanel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 92, 86, 20);
		contentPanel.add(passwordField);
		
		JCheckBox chckbxBroadcastToLan = new JCheckBox("Broadcast To LAN");
		chckbxBroadcastToLan.setBounds(126, 35, 207, 23);
		contentPanel.add(chckbxBroadcastToLan);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Client.window.createHost(Integer.valueOf(portFeild.getText()));
						closeWindow();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeWindow();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
