package UdpClientServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpCLient {

	private final int port;
	private final String host;
	private DatagramSocket socket;
	private final Scanner input;

	public UdpCLient(int port, String host, Scanner input) {
		this.port = port;
		this.host = host;
		try {
			socket = new DatagramSocket();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		this.input = input;
	}

	public static void main(String[] args) {
		UdpCLient udpCLient = new UdpCLient(2000, "localhost", new Scanner(System.in));
		udpCLient.runClient();
	}

	public void runClient() {
		try {
			socket.connect(new InetSocketAddress(host, port));

			byte[] clientSend;
			byte[] serverReceive = new byte[1024];
			String clientAction;
			String serverAction;
			while(true) {
				clientAction = input.nextLine();
				clientSend = clientAction.getBytes(StandardCharsets.UTF_8);
				socket.send(new DatagramPacket(clientSend, clientAction.length()));
				if (clientAction.equalsIgnoreCase("Fine"))
					break;

				DatagramPacket receive = new DatagramPacket(serverReceive, serverReceive.length);
				socket.receive(receive);
				serverReceive = receive.getData();
				serverAction = new String(receive.getData(), receive.getOffset(), receive.getLength());
				System.out.println("Il server scrive " + serverAction);
			}
			socket.close();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}

}
