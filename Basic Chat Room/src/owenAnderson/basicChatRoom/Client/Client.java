package owenAnderson.basicChatRoom.Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Client {

	private JFrame frame;
	public static Client window;
	private static String username = "Test", usernameThem;
	private JTextField messageBox;
	private JButton sendMessage;
	JTextArea messageHistory;
	private ConnectionManager con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Client.class.getResource("/owenAnderson/basicChatRoom/Client/res/ClientIcon.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 878, 432);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		messageBox = new JTextField();
		messageBox.setBounds(109, 352, 643, 20);
		frame.getContentPane().add(messageBox);
		messageBox.setColumns(10);
		
		sendMessage = new JButton("Send...");
		sendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(con != null){
					sendMsg();
				}else{
					messageBox.setText("");
					window.printToScreen("ERROR: could not send message not connected to anything (silly)\n");
				}
			}
		});
		sendMessage.setBounds(10, 351, 89, 23);
		frame.getContentPane().add(sendMessage);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(10, 11, 742, 330);
		frame.getContentPane().add(scroll);
		
		messageHistory = new JTextArea();
		messageHistory.setEditable(false);
		scroll.setViewportView(messageHistory);
		messageHistory.setLineWrap(true);
		
		JLabel Face = new JLabel("");
		Face.setIcon(new ImageIcon(Client.class.getResource("/owenAnderson/basicChatRoom/Client/res/Face.png")));
		Face.setBounds(761, 23, 100, 100);
		frame.getContentPane().add(Face);
		
		JLabel Text = new JLabel("");
		Text.setIcon(new ImageIcon(Client.class.getResource("/owenAnderson/basicChatRoom/Client/res/UCLS.png")));
		Text.setBounds(762, 134, 100, 238);
		frame.getContentPane().add(Text);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu Network = new JMenu("Network");
		menuBar.add(Network);
		
		JMenuItem connectToIP = new JMenuItem("Connect");
		connectToIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(con != null){
					con.stopThread();
					window.printToScreen("Ending connection\n");
				}
				try {
					openDialogWindow(Class.forName("owenAnderson.basicChatRoom.Client.ConnectWindow"));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		Network.add(connectToIP);
		
		JMenuItem mntmSimpleHost = new JMenuItem("Simple Host");
		mntmSimpleHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(con != null){
					con.stopThread();
					window.printToScreen("Ending connection\n");
				}
				try {
					openDialogWindow(Class.forName("owenAnderson.basicChatRoom.Client.HostCreate"));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		
		JMenuItem mntmBrowseLanServers = new JMenuItem("Browse Lan Servers");
		Network.add(mntmBrowseLanServers);
		Network.add(mntmSimpleHost);
		
		JMenuItem disconnect = new JMenuItem("Disconnect");
		disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.sendMsg("DISCONNECTED");
				con.stopThread();
				con = null;
			}
		});
		Network.add(disconnect);
		
		JMenu mnContacts = new JMenu("Contacts");
		Network.add(mnContacts);
		
		JMenuItem mntmAddContact = new JMenuItem("Add Contact");
		mnContacts.add(mntmAddContact);
		
		JMenuItem mntmViewContacts = new JMenuItem("View Contacts");
		mnContacts.add(mntmViewContacts);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					openDialogWindow(Class.forName("owenAnderson.basicChatRoom.Client.Settings"));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		Network.add(mntmSettings);
		
		JMenu mnWindow = new JMenu("Window");
		menuBar.add(mnWindow);
		
		JMenuItem mntmClear = new JMenuItem("Clear Text");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearScreen();
			}
		});
		mnWindow.add(mntmClear);
		
		JMenuItem mntmSetFont = new JMenuItem("Set Font");
		mnWindow.add(mntmSetFont);
		frame.getRootPane().setDefaultButton(sendMessage);
	}
	
	/**
	 * opens the window that is used to connect to server and other stuff
	 * @param c The window that will be created. Value obtained from Class.forName(className)
	 */
	void openDialogWindow(Class<?> c){
		try {
			BasicDialogWindow dialog = (BasicDialogWindow) c.newInstance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A method used to print text to the main text area in the client
	 * @param str The string to be printed
	 */
	void printToScreen(String str){
		messageHistory.setText(messageHistory.getText() + str);
	}
	
	/**
	 * A method to clear all text from the screen
	 */
	void clearScreen(){
		messageHistory.setText("");
	}
	
	void connectToServer(String host, int port){
		if(con != null) con.stopThread();
		con = new ConnectionManager(host, port, null, username);
	}

	void processMsg(String string) {
		System.out.println(string);
		if(string.startsWith("MSG")){
			printToScreen(usernameThem + ": " + string.substring(4) + "\n");
		}
		else if(string.startsWith("USERNAME")){
			setTheirUsername(string.substring(9));
		}else if(string.startsWith("INFO")){
			printToScreen("PARTNER DISCONNECTED\n");
		}else if(string.startsWith("DISCONNECTED")){
			Client.window.printToScreen(usernameThem + " disconnected\n");
			con.stopThread();
		}
	}
	
	void failedPass(){
		
	}
	
	void createHost(int port){
		if(con != null) con.stopThread();
		con = new ConnectionManager(port, null, username);
	}
	
	void sendMsg(){
		String msg = messageBox.getText();
		con.sendMsg("MSG " + msg);
		printToScreen(username + ": " + msg + "\n");
		messageBox.setText("");
	}
	
	void setUsername(String user){
		username = user;
	}
	
	void setTheirUsername(String user){
		usernameThem = user;
	}
}
