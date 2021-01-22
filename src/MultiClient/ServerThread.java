package MultiClient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerThread extends Thread {
    private final Socket clientSocket;
    private final Scanner input;

    public ServerThread(Socket clientSocket, Scanner input) {
        this.clientSocket = clientSocket;
        this.input = input;
    }

    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader msgFromClient = new BufferedReader(inputStreamReader);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8);
            PrintWriter msgToClient = new PrintWriter(outputStreamWriter, true);
            String clientAction;
            String serverAction;

            while (true) {
                clientAction = msgFromClient.readLine();
                System.out.println("Il Client " + clientSocket + " scrive: " + clientAction);
                if (clientAction.equals("Fine")) break;
                serverAction = input.nextLine();
                msgToClient.println(serverAction);
            }
            System.out.println("Client disconnesso, in attesa di un nuovo client");
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
