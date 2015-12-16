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

public class ConnectWindow extends BasicDialogWindow {

	private static final long serialVersionUID = 5255938882270783147L;
	private final JPanel contentPanel = new JPanel();
	private JTextField hostFeild;
	private JPasswordField passwordField;
	private JTextField portFeild;

	/**
	 * Create the dialog.
	 */
	public ConnectWindow() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConnectWindow.class.getResource("/owenAnderson/basicChatRoom/Client/res/ConnectionIcon.png")));
		setTitle("Connect");
		setBounds(100, 100, 389, 185);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblEnterIp = new JLabel("Enter Host");
			lblEnterIp.setBounds(10, 11, 64, 14);
			contentPanel.add(lblEnterIp);
		}
		
		hostFeild = new JTextField();
		hostFeild.setBounds(10, 36, 86, 20);
		contentPanel.add(hostFeild);
		hostFeild.setColumns(10);
		
		JLabel lblPassoword = new JLabel("Password");
		lblPassoword.setBounds(10, 67, 112, 14);
		contentPanel.add(lblPassoword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 87, 86, 20);
		contentPanel.add(passwordField);
		
		JLabel lblifNoneLeave = new JLabel("(If none leave blank)");
		lblifNoneLeave.setBounds(106, 92, 131, 14);
		contentPanel.add(lblifNoneLeave);
		
		portFeild = new JTextField();
		portFeild.setBounds(106, 36, 86, 20);
		contentPanel.add(portFeild);
		portFeild.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(106, 11, 46, 14);
		contentPanel.add(lblPort);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Connect");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Client.window.connectToServer(hostFeild.getText(), Integer.valueOf(portFeild.getText()));
						closeWindow(); 
					}
				});
				okButton.setActionCommand("");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Exit");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeWindow();
					}
				});
				cancelButton.setActionCommand("");
				buttonPane.add(cancelButton);
			}
		}
	}
}
