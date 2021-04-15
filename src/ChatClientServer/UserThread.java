package ChatClientServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
