package ChatBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This thread is responsible for reading server's input and printing it to the console.
 * It runs in an infinite loop until the client disconnects from the server.
 *
 * @author www.codejava.net
 */

public class ReadThread extends Thread {

	private BufferedReader reader;
	private final Socket socket;
	private final ChatClient client;

	public ReadThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;

		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch(IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
       /*
       while (true ) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);
 
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }*/

		String response;

		do {
			try {
				response = reader.readLine();
				System.out.println("\n" + response);

				// prints the username after displaying the server's message
				if(client.getUserName() != null && !response.equals("bye")) {
					System.out.print("[" + client.getUserName() + "]: ");
				}
			} catch(IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}

		} while(!response.equals(client.getUserName() + " has quitted."));

		try {
			socket.close();
		} catch(IOException ex) {
			System.out.println("Error writing to server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}