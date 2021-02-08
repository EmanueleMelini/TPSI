package MultiClient;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

	private final int portNumber;
	private final String host;
	private final Socket socket;
	private final Scanner input;

	public Client(int portNumber, String host, Scanner input) {
		this.portNumber = portNumber;
		this.host = host;
		this.input = input;
		socket = new Socket();
	}

	public static void main(String[] args) {
		Client client = new Client(32710, "127.0.0.1", new Scanner(System.in));
		client.runClient();
	}

	public void runClient() {
		try {
			socket.connect(new InetSocketAddress(host, portNumber)); // creo la connessione al server
			System.out.println("Connesso ");
			InputStream inputStream = socket.getInputStream(); // creo il canale relativo al socket
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
					StandardCharsets.UTF_8); // creo il flusso di lettura per il client, contenente i dati inviati dal server
			BufferedReader msgFromServer = new BufferedReader(inputStreamReader); // bufferizzazione del flusso di lettura
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
			PrintWriter msgToServer = new PrintWriter(outputStreamWriter,
					true); // creo il flusso di scrittura del client, da inviare al server, il parametro true esegue Auto-flush()
			String serverAction;
			String clientAction; //creo uno string builder per creare una stringa composta

			while(true) {
				clientAction = input.nextLine();
				msgToServer.println(clientAction);
				if(clientAction.equals("Fine"))
					break;
				serverAction = msgFromServer.readLine();
				System.out.println("Il Server scrive: " + serverAction);
			}

			socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
