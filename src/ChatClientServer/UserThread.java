package ChatClientServer;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread{

	private final Socket socket;
	private final Server server;
	private PrintWriter printWriter;
	private String userName;

	public UserThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {

		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			OutputStream outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream, true);

			printUsers();

			String user = bufferedReader.readLine();
			server.addUser(user);
			//TODO:finire

		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void printUsers() {
		if(server.hasUsers())
			printWriter.println("Connected users: " + server.getUsers());
		else
			printWriter.println("No other users connected");
	}

	public void sendMessage(String message) {
		printWriter.println(message);
	}

	public String getUserName() {
		return userName;
	}
}
