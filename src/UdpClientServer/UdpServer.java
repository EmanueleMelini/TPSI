package UdpClientServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpServer {

	private final int port;
	private DatagramSocket socket;
	private Scanner input;

	public UdpServer(int port, Scanner input) {
		this.port = port;
		this.input = input;
		try {
			socket = new DatagramSocket(port);
			System.out.println("Server avviato");
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		UdpServer udpServer = new UdpServer(2000, new Scanner(System.in));
		udpServer.runServer();
	}

	public void runServer() {
		try {
			byte[] clientReceive = new byte[1024];
			byte[] serverSend;
			String serverAction;
			String clientAction;
			int clientPort;
			InetAddress clientInetAddress;
			while(true) {
				DatagramPacket receive = new DatagramPacket(clientReceive, clientReceive.length);
				socket.receive(receive);
				clientAction = new String(receive.getData(), receive.getOffset(), receive.getLength());
				System.out.println("Il client scrive " + clientAction);
				if (clientAction.equalsIgnoreCase("Fine")) {
					System.out.println("Fine server");
					break;
				}
				clientPort = receive.getPort();
				clientInetAddress = receive.getAddress();
				serverAction = input.nextLine();
				serverSend = serverAction.getBytes(StandardCharsets.UTF_8);
				DatagramPacket sendDatagramPacket = new DatagramPacket(serverSend, serverAction.length(), clientInetAddress, clientPort);
				socket.send(sendDatagramPacket);
			}
			socket.close();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}
