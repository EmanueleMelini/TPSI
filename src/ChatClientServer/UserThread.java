package ChatClientServer;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {

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
			userName = user;
			this.setName(userName);

			String serverAction = "New user connected: " + userName;
			server.broadcast(serverAction, this);

			String clientAction;

			do {
				clientAction = bufferedReader.readLine();
				if(clientAction.equals("end")) {
					serverAction = "<" + userName + ">" + clientAction;
					server.broadcast(serverAction, this);
				} else {
					String[] clientMessage = clientAction.split(":");
					String message = clientMessage[0];
					String destUser = clientMessage[1];
					serverAction = "<" + userName + ">" + message;
					if(destUser.equals("broadcast")) {
						server.broadcast(serverAction, this);
					} else {
						server.unicast(serverAction, this, destUser);
					}
				}
			} while(!clientAction.equals("end"));

			serverAction = userName + " disconnected";
			server.broadcast(serverAction);
			server.removeUser(userName, this);

			socket.close();

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
