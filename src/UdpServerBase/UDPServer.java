package UdpServerBase;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPServer {

	public static void main(String[] args) throws Exception {
		int portaServer = 2000;
		DatagramSocket serverSocket = new DatagramSocket(portaServer);
		byte[] receiveData = new byte[1024];
		byte[] sendData;
		while(true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("SERVER AVVIATO sulla porta: " + portaServer);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			System.out.println("RICEVO dal CLIENT: " + sentence);
			if(sentence.equals("FINE"))
				break;
			else {
				int port = receivePacket.getPort();
				InetAddress IPAddress = receivePacket.getAddress();
				System.out.println("indirizzo CLIENT: " + IPAddress + " porta CLIENT: " + port);

				String modificaSentence = sentence.toUpperCase();
				sendData = modificaSentence.getBytes();
				// DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.setBroadcast(true);
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), port);
				serverSocket.send(sendPacket);
				System.out.println("INVIO al CLIENT: " + modificaSentence);
			}
		}
		serverSocket.close();
	}

}
