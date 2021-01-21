package MultiClient;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiServer {

    public static void main(String[] args) {
        MultiServer multiServer = new MultiServer();
        multiServer.startMultiServer();
    }

    public void startMultiServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(32710);
            Scanner input = new Scanner(System.in);
            String end = "No";
            while(true) {
                if(end.equals("Si")) break;
                System.out.println("Attesa di un client ");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Chiudere la connessione dopo questa ?");
                end = input.nextLine();
                System.out.println("Client trovato " + clientSocket);
                Server server = new Server(clientSocket, input);
                server.start();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
