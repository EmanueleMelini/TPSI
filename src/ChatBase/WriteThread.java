package ChatBase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This thread is responsible for reading user's input and send it to the server.
 * It runs in an infinite loop until the user types 'bye' to quit.
 *
 * @author www.codejava.net
 */

public class WriteThread extends Thread {

	private PrintWriter writer;
	private final Socket socket;
	private final ChatClient client;

	public WriteThread(@NotNull Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;

		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch(IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
 
        /*
        Console console = System.console();
        String userName = console.readLine("\nEnter your name: ");
        */

		System.out.println("\nEnter your name: ");
		String userName = input.readLine();
		client.setUserName(userName);
		writer.println(userName);

		String text;

		do {
			//text = console.readLine("[" + userName + "]: ");
			System.out.println("[" + userName + "]: ");
			text = input.readLine();
			writer.println(text);

		} while(!text.equals("bye"));
 
        /*
        try {
        	synchronized (this) { socket.close(); }
        	socket.close();
             
         } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
        */
	}

}