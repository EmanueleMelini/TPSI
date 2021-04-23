package ChatClientServer;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {

	private PrintWriter printWriter;
	private final Socket socket;
	private final Client client;
	private final Scanner scanner;

	public WriteThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
		scanner = new Scanner(System.in);

		try {
			OutputStream outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream, true);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.print("Insert you username: ");
		String userName = scanner.nextLine();
		client.setUserName(userName);
		printWriter.println(userName);

		String clientAction;

		do {
			System.out.print("<" + userName + ">");
			clientAction = scanner.nextLine();
			printWriter.println(clientAction);
		} while(!clientAction.equals("end"));
	}

}
