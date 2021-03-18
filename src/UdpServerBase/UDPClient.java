package UdpServerBase;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

class UDPClient {

	public static void main(String[] args) throws Exception {
		int portaServer = 2000;
		Scanner tast = new Scanner(System.in);
		//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData;
		byte[] receiveData = new byte[1024];
		//String sentence = inFromUser.readLine();

		String sentence;
		while(true) {
			sentence = tast.nextLine();
			if (sentence.equals("FINE"))
				break;
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portaServer);
			clientSocket.send(sendPacket);
			System.out.println("INVIO al SERVER: " + sentence);

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("RICEVO dal SERVER: " + modifiedSentence);
		}
		clientSocket.close();
	}

}

