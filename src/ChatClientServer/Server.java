package ChatClientServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

	private final int port;
	private final Set<String> users = new HashSet<>();
	private final Set<UserThread> userThreads = new HashSet<>();

	public Server(int port) {
		this.port = port;
	}

	public void startServer() {

		try(ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server listening on port " + port);

			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("User connected");
				UserThread userThread = new UserThread(socket, this);
				userThreads.add(userThread);
				userThread.start();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 8989;
		Server server = new Server(port);
		server.startServer();
	}

	public void broadcast(String message, UserThread excludedUser) {
		userThreads.forEach(userThread -> {
			if(excludedUser != userThread)
				userThread.sendMessage(message);
		});
	}

	public void broadcast(String message) {
		userThreads.forEach(userThread -> userThread.sendMessage(message));
	}

	public void unicast(String message, UserThread excludedUser, String destUser) {
		userThreads.forEach(userThread -> {
			if(excludedUser != userThread)
				if(userThread.getUserName()
						.equals(destUser))
					userThread.sendMessage(message);
		});
	}

	public void addUser(String user) {
		users.add(user);
	}

	public void removeUser(String user, UserThread userThread) {
		boolean removeUser = users.remove(user);
		if(removeUser) {
			userThreads.remove(userThread);
			System.out.println("The user " + user + " disconnected from the server");
		}
	}

	public Set<String> getUsers() {
		return this.users;
	}

	public boolean hasUsers() {
		return !this.users.isEmpty();
	}

}
