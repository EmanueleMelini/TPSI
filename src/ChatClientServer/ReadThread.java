package ChatClientServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {

	private BufferedReader bufferedReader;
	private InputStream inputStream;
	private final Socket socket;
	private final Client client;

	public ReadThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
	}

	public void run() {
		try {
			inputStream = socket.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


			String serverAction;
			do {
				try {
					serverAction = bufferedReader.readLine();
					System.out.println("\n" + serverAction);
					if(client.getUserName() != null && !serverAction.equals("end"))
						System.out.print("<" + client.getUserName() + ">");

				} catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					break;
				}
			} while(!serverAction.equals(client.getUserName() + " disconnected"));

			socket.close();

		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
