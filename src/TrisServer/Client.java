package TrisServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private final int portNumber;
    private final String host;
    private final Socket socket;
    private char[][] gameMatrix;
    private char allyChar;
    private char enemyChar;
    private final Scanner input;


    public Client(int portNumber, String host, Scanner input) {
        this.portNumber = portNumber;
        this.host = host;
        socket = new Socket();
        this.input = input;
    }

    public static void main(String[] args){
        Client client = new Client(32710, "127.0.0.1", new Scanner(System.in));
        client.runClient();
    }

    public void runClient() {
        try{
            socket.connect(new InetSocketAddress(host, portNumber)); // creo la connessione al server
            InputStream inputStream = socket.getInputStream(); // creo il canale relativo al socket
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8); // creo il flusso di lettura per il client, contenente i dati inviati dal server
            BufferedReader msgFromServer = new BufferedReader(inputStreamReader); // bufferizzazione del flusso di lettura
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            PrintWriter msgToServer = new PrintWriter(outputStreamWriter,true); // creo il flusso di scrittura del client, da inviare al server, il parametro true esegue Auto-flush()
            String serverAction;
            String clientAction; //creo uno string builder per creare una stringa composta
            gameMatrix = new char[3][3]; //creo la matrice per il gioco


            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    gameMatrix[i][j] = '+';
                }
            } //inizializzo la matrice con un carattere a scelta

            int startGame = Integer.parseInt(msgFromServer.readLine());

            if (startGame == 0) {
                System.out.println("Inizi tu il gioco!");
            } else {
                System.out.println("Il gioco lo inizia l'avversario!");
            }

            allyChar = msgFromServer.readLine().charAt(0);
            if(allyChar == 'O')
                enemyChar = 'X';
            else
                enemyChar = 'O';

            System.out.println("Il tuo segno è: " + allyChar);

            //comincia il gioco
            while (true) {
                if (startGame == 0) {

                    //blocco di istruzioni che viene eseguito se il client è il primo a giocare
                    clientAction = insertClientAction(); //funzione creata per inserire dentro la matrice il carattere

                    //System.out.println(clientAction);
                    msgToServer.println(clientAction); //manda al server la posizione del carattere inserito


                    visualMatrix(); //funzione per visualizzare la matrice

                    System.out.println("Aspettando una risposta dall'altro player...");


                    serverAction = msgFromServer.readLine(); //aspetta la risposta del server


                    if(serverAction == null) {
                        System.out.println("Server Disconnesso");
                        break;
                    } else if (serverAction.equals("Disconnesso")) {
                        System.out.println("Sono disconnesso");
                        break;
                    } else if (serverAction.equals("Parita")) {
                        System.out.println("Gioco finito in parità");
                        break;
                    } else if (serverAction.equals("Sconfitta Forfeit")) {
                        System.out.println("Hai perso per forfeit!");
                        break;
                    } else if (serverAction.equals("Vittoria Forfeit")) {
                        System.out.println("L'avversario ha dato forfeit!");
                        break;
                    } else if (serverAction.equals("Hai vinto")) {
                        visualMatrix();
                        System.out.println("Hai vinto!");
                        break;
                    } else if (serverAction.equals("Hai perso")) {
                        visualMatrix();
                        System.out.println("Hai perso!");
                        break;
                    }

                    insertServerAction(serverAction); //funzione per salvare nella matrice la risposta del server

                } else {

                    //blocco di istruzioni che viene eseguito se il client è il secondo a giocare
                    visualMatrix();

                    System.out.println("Aspettando una risposta dall'altro player...");


                    serverAction = msgFromServer.readLine();


                    if(serverAction == null) {
                        System.out.println("Server Disconnesso");
                        break;
                    } else if (serverAction.equals("Disconnesso")) {
                        System.out.println("Sono disconnesso");
                        break;
                    } else if (serverAction.equals("Parita")) {
                        System.out.println("Gioco finito in parità!");
                        break;
                    } else if (serverAction.equals("Forfeit")) {
                        System.out.println("L'avversario ha dato forfeit!");
                        break;
                    } else if (serverAction.equals("Hai vinto")) {
                        visualMatrix();
                        System.out.println("Hai vinto!");
                        break;
                    } else if (serverAction.equals("Hai perso")) {
                        visualMatrix();
                        System.out.println("Hai perso!");
                        break;
                    }

                    insertServerAction(serverAction);


                    clientAction = insertClientAction();

                    System.out.println(clientAction);
                    msgToServer.println(clientAction);

                }
            }
            socket.close(); //chiusura del socket
        } catch (Exception E) {
            E.printStackTrace();
        }

    }

    //funzione per visualizzare la matrice
    public void visualMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(gameMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    //funzione per salvare nella matrice la risposta del server
    public void insertServerAction(String m) {
        String [] xy = m.split("-");
        gameMatrix[Integer.parseInt(xy[0])][Integer.parseInt(xy[1])] = enemyChar;
    }

    //funzione per salvare nella matrice l'azione del client
    public String insertClientAction() {
        boolean f;
        int x,y;
        System.out.println("Scegli dove mettere la " + allyChar + " (colonna/riga, un numero da 0 a 2, scrivi 3 per finire il gioco).");
        do {
            f = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(gameMatrix[i][j]);
                }
                System.out.print("\n");
            }
            do {
                x = input.nextInt(); //metodo di classe scanner per salvare un intero da tastiera
                y = input.nextInt();
            }while(((x<0)||(x>3))||((y<0)||(y>3)));
            if ((x == 3)||(y == 3)) {
                break;
            } else if ((gameMatrix[x][y] == 'X') || (gameMatrix[x][y] == 'O')) {
                System.out.println("Hai scelto una posizione gia usata, scegli ancora.");
                f = true;
            } else {
                gameMatrix[x][y] = allyChar;
            }
        } while (f);

        StringBuilder sb = new StringBuilder(); //stringa composta
        sb.append(x)
                .append('-')
                .append(y); //metodo della classe string builder per comporre una stringa composta
        return sb.toString();
    }
}
