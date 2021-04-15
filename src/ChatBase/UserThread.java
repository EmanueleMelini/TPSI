package ChatBase;

import java.io.*;
import java.net.Socket;

/**
 * This thread handles connection for each connected client, so the server can handle multiple clients at the same time.
 *
 * @author www.codejava.net
 */

public class UserThread extends Thread {

	private final Socket socket;
	private final ChatServer server;
	private PrintWriter writer;
	private String user_name;

	public UserThread(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			printUsers();

			String userName = reader.readLine();
			server.addUserName(userName);
			user_name = userName;
			this.setName(user_name);

			String serverMessage = "New user connected: " + userName;
			server.broadcast(serverMessage, this);

			String clientMessage;


			do {
				clientMessage = reader.readLine();
				if(clientMessage.equals("bye")) {
					serverMessage = "[" + userName + "]: " + clientMessage;
					server.broadcast(serverMessage, this);
				} else {
					String[] str = clientMessage.split(":");
					//serverMessage = "[" + userName + "]: " + clientMessage;
					serverMessage = "[" + userName + "]: " + str[0];
					if(str[1].equals("broadcast"))
						server.broadcast(serverMessage, this);
					else
						server.unicast(serverMessage, this, str[1]);
				}

			} while(!clientMessage.equals("bye"));

			serverMessage = userName + " has quitted.";
			server.broadcast(serverMessage, this);

			server.removeUser(userName, this);
			socket.close();

		} catch(IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Sends a list of online users to the newly connected user.
	 */

	void printUsers() {
		if(server.hasUsers()) {
			writer.println("Connected users: " + server.getUserNames());
		} else {
			writer.println("No other users connected");
		}
	}

	/**
	 * Sends a message to the client.
	 */

	void sendMessage(String message) {
		writer.println(message);
	}

	public String GetUserName() {
		return user_name;
	}

}