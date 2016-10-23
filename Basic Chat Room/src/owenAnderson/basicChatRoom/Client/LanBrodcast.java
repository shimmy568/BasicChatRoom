package owenAnderson.basicChatRoom.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

public class LanBrodcast {

	public static final int DEFAULTBRODCASTPORT = 2554;
	private static final int PACKETSIZE = 100;
	
	

	class Receiver implements Runnable{

		private DatagramSocket socket;
		private DatagramPacket packet;
		
		private boolean running = true;
		
		public Receiver() {
			try {
				socket = new DatagramSocket(DEFAULTBRODCASTPORT);
			} catch (SocketException e) {
				System.out.println("Problem with creating reciver socket on port: " + DEFAULTBRODCASTPORT);
				//e.printStackTrace();
			}
			packet = new DatagramPacket(new byte[PACKETSIZE], 10);
		}
		
		@Override
		public void run() {
			while(running){
				try {
					socket.receive(packet);
					System.out.println("Got packet from " + packet.getAddress() + ":" + packet.getPort());
					
					//create and send reply to request (will have relevant data)
					byte[] outBuffer = new Date().toString().getBytes();
					packet.setData(outBuffer);
					packet.setLength(outBuffer.length);
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void closeBrodcaser(){
			running = false;
		}
		
	}
	
	class Searcher implements Runnable{

		private String hostname = "255.255.255.255";
		private DatagramSocket socket;
		private DatagramPacket packet;
		private Inet4Address host;
		
		public Searcher() {
			try {
				Inet4Address.getByName(hostname);
				socket = new DatagramSocket(null);
				packet = new DatagramPacket(new byte[PACKETSIZE], 0, host, DEFAULTBRODCASTPORT);
			} catch (UnknownHostException e) {
				System.out.println("Host could not be found");
				//e.printStackTrace();
			} catch (SocketException e) {
				System.out.println("Error with socket");
				//e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				socket.send(packet);
				packet.setLength(100);
				socket.receive(packet);
				socket.close();
				byte[] data = packet.getData();
			} catch (IOException e) {
				System.out.println("Could not send packet");
				//e.printStackTrace();
			}
		}
	}
}
