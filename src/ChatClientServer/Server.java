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

}
