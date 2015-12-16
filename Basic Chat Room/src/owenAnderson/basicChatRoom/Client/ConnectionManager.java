package owenAnderson.basicChatRoom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionManager {
	
	private int port;
	private String password;
	private Socket sock;
	private ServerSocket sSock;
	private PrintWriter out;
	private BufferedReader in;
	private String username;
	private boolean running = true;
	private Thread th;
	
	/**
	 * The construter for the ConnectionManager class if the client is connecting to a server. 
	 * If there is no password make pass null
	 * @param host The host's address
	 * @param port the port to connect to
	 * @param pass the password for the server
	 */
	public ConnectionManager(String host, int port, String pass, String username){
		this.port = port;
		this.username = username;
		if(pass != null){
			password = pass;
		}
		
		try {
			InetAddress address = InetAddress.getByName(host);
			sock = new Socket(address, this.port);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
		} catch (UnknownHostException e) {
			System.out.println("IP not found");
			Client.window.printToScreen("Error with finding ip");
			e.printStackTrace();
		} catch (IOException e) {
			Client.window.printToScreen("Could not connect");
			System.out.println("Connection Error");
			e.printStackTrace();
		}
		
		Runnable reader = startListener();
		th = new Thread(reader, "readerThread");
		th.start();
		
		
	}
	
	public ConnectionManager(int port, String pass, String username){
		this.port = port;
		this.username = username;
		if(pass != null){
			password = pass;
		}
		
		try {
			sSock = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		Runnable reader = startListener();
		th = new Thread(reader, "readerThread");
		th.start();
	}
	
	public Runnable startListener(){
		Runnable reader = new Runnable() {
			private boolean firstRun = true;
			public void run() {
				while (running){
					if(firstRun){
						firstRun = false;
						if(sock == null){
							try {
								Client.window.printToScreen("Waiting for connection...\n");
								sock = sSock.accept();
								Client.window.printToScreen("Connection made!");
							} catch (IOException e) {
								e.printStackTrace();
							}
							firstRun = true;
							continue;
						}else if(out == null && in == null){
							try {
								out = new PrintWriter(sock.getOutputStream(), true);
								in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						sendMsg("USERNAME " + username);
					}
					
					try {
						Client.window.processMsg(in.readLine());
					} catch (IOException e) {	
						endConnection();
					}
				}
			}
		};
		return reader;
	}
	
	/**
	 * a method that sends a message to the connected server see the file Message headings
	 * in res for the types of headers you can have
	 * @param msg the message
	 */
	public void sendMsg(String msg){
		out.println(msg);
	}
	
	/**
	 * A message that shuts down the connection
	 */
	public void endConnection(){
		running = false;
		
		try {
			out.close();
			in.close(); //TODO get the connection manager to stop right
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(NullPointerException e){
			System.out.println("Closed server");
		}
	}
	
	public void stopThread(){
		Client.window.printToScreen("Ending connection");
		endConnection();
		running = false;
	}
}
