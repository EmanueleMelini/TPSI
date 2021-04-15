package ChatClientServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	private final String hostname;
	private final int port;
	private String userName;
	private InputStream inputStream;
	private BufferedReader bufferedReader;

	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void startClient() {
		try {
			Socket socket = new Socket(hostname, port);
			System.out.println("Connected to the server");

			try {
				inputStream = socket.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			} catch(Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			String serverAction;
			do {
				try {
					serverAction = bufferedReader.readLine();
					System.out.println(serverAction);
					if(this.getUserName() != null && !serverAction.equals("end"))
						System.out.println("<" + this.getUserName() + ">");

				}  catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					break;
				}
			} while(!serverAction.equals(this.getUserName() + " disconnected"));

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
