package ChatClientServer;

import java.net.Socket;

public class Client {

	private final String hostname;
	private final int port;
	private String userName;

	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void startClient() {
		try {
			Socket socket = new Socket(hostname, port);
			System.out.println("Connected to the server");

			ReadThread readThread = new ReadThread(socket, this);
			readThread.start();
			WriteThread writeThread = new WriteThread(socket, this);
			writeThread.start();

		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public static void main(String[] args) {
		String hostname = "127.0.0.1";
		int port = 8989;
		Client client = new Client(hostname, port);
		client.startClient();
	}

}
