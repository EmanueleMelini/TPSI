package ChatBase;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class ChatClient {

	private final String hostname;
	private final int port;
	private String userName;

	public ChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void execute() {

		try {
			Socket socket = new Socket(hostname, port);
			System.out.println("Connected to the chat server");

			new ReadThread(socket, this).start();
			new WriteThread(socket, this).start();

		} catch(UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
			ex.printStackTrace();
		} catch(IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	void setUserName(String userName) {
		this.userName = userName;
	}

	String getUserName() {
		return this.userName;
	}

	public static void main(String[] args) {
        /*if (args.length < 2) return;
 
        //String hostname = args[0];
        int port = Integer.parseInt(args[1]);*/
		String hostname = "127.0.0.1";
		int port = 8989;
		ChatClient client = new ChatClient(hostname, port);
		client.execute();
	}

}