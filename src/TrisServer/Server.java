package TrisServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Server {
    private final int portNumber;
    private ServerSocket serverSocket;
    private char[][] gameMatrix;
    private char allyChar;
    private char enemyChar;
    private final Scanner input;

    public Server(int portNumber, Scanner input) {
        this.portNumber = portNumber;
        if(!startServer())
            System.err.println("Errore durante la creazione del Server");
        this.input = input;
    }

    private boolean startServer() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } System.out.println("Server creato con successo!");
        return true;
    }

    public static void main(String[] args){
        Server server = new Server(32710, new Scanner(System.in));
        server.runServer();
    }

    public void runServer() {
        try {
            Socket clientSocket = serverSocket.accept();
            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader msgFromClient = new BufferedReader(inputStreamReader);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8);
            PrintWriter msgToClient = new PrintWriter(outputStreamWriter,true);
            String clientAction;
            String serverAction;
            String[] endGame;

            gameMatrix = new char[3][3];
            Random rand = new Random(); //variable generatrice di numeri casuali


            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    gameMatrix[i][j] = '+';
                }
            }

            int startGame = rand.nextInt(2); //metodo per generare numeri casuali (nella parentesi ci va il range)

            msgToClient.println(startGame); //mando al client l'esito del numero casuale che determinerà chi inizia il gioco

            if (startGame == 1) {
                System.out.println("Inizi tu il gioco!");
            } else {
                System.out.println("Il gioco lo inizia l'avversario!");
            }


            int randomChar = rand.nextInt(2);

            if (randomChar == 0) {
                allyChar = 'X';
                enemyChar = 'O';
            } else {
                allyChar = 'O';
                enemyChar = 'X';
            }

            msgToClient.println(enemyChar);

            System.out.println("Il tuo segno è: " + allyChar);

            while (true) {
                if (startGame == 0) {

                    //Azione del client
                    visualMatrix();

                    System.out.println("Aspettando una risposta dall'altro player...");


                    clientAction = msgFromClient.readLine(); //aspetta la risposta del server


                    if(clientAction == null) {
                        System.out.println("Client Disconnesso");
                        break;
                    }

                    endGame = clientAction.split("-");

                    if ((endGame[0].equals("3")) || (endGame[1].equals("3"))) {
                        System.out.println("L'avversario ha dato forfeit!");
                        msgToClient.println("Sconfitta Forfeit");
                        break;
                    }

                    insertClientAction(clientAction);

                    if (fullMatrix()) {
                        System.out.println("Gioco finito in parità!");
                        msgToClient.println("Parita");
                        break;
                    }

                    if (trisCheck(enemyChar)) {
                        visualMatrix();
                        System.out.println("Hai perso!");
                        msgToClient.println("Hai vinto");
                        break;
                    }

                    //Azione del server
                    serverAction = insertServerAction();

                    if (fullMatrix()) {
                        System.out.println("Gioco finito in parità!");
                        msgToClient.println("Parita");
                        break;
                    }

                    endGame = serverAction.split("-");

                    if ((endGame[0].equals("3")) || (endGame[1].equals("3"))) {
                        System.out.println("Hai perso per forfeit!");
                        msgToClient.println("Vittoria Forfeit");
                        break;
                    }

                    if (trisCheck(allyChar)) {
                        visualMatrix();
                        System.out.println("Hai vinto!");
                        msgToClient.println("Hai perso");
                        break;
                    }

                    msgToClient.println(serverAction);

                } else {

                    //Azione del server
                    serverAction = insertServerAction();

                    if (fullMatrix()) {
                        System.out.println("Gioco finito in parità!");
                        msgToClient.println("Parita");
                        break;
                    }

                    endGame = serverAction.split("-");

                    if ((endGame[0].equals("3")) || (endGame[1].equals("3"))) {
                        System.out.println("Hai perso per forfeit!");
                        msgToClient.println("Vittoria Forfeit");
                        break;
                    }

                    if (trisCheck(allyChar)) {
                        visualMatrix();
                        System.out.println("Hai vinto!");
                        msgToClient.println("Hai perso");
                        break;
                    }

                    msgToClient.println(serverAction);

                    //Azione del client
                    visualMatrix();

                    System.out.println("Aspettando una risposta dall'altro player...");


                    clientAction = msgFromClient.readLine(); //aspetta la risposta del server


                    if(clientAction == null) {
                        System.out.println("Client Disconnesso");
                        break;
                    }

                    endGame = clientAction.split("-");

                    if ((endGame[0].equals("3")) || (endGame[1].equals("3"))) {
                        System.out.println("L'avversario ha dato forfeit!");
                        msgToClient.println("Sconfitta Forfeit");
                        break;
                    }

                    insertClientAction(clientAction);

                    if (fullMatrix()) {
                        System.out.println("Gioco finito in parità!");
                        msgToClient.println("Parita");
                        break;
                    }

                    if (trisCheck(enemyChar)) {
                        visualMatrix();
                        System.out.println("Hai perso!");
                        msgToClient.println("Hai vinto");
                        break;
                    }

                }
            }
            clientSocket.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //funzione per controllare se la matrice è piena
    public  boolean fullMatrix() {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(gameMatrix[i][j] == '+')
                    return false;
            }
        }
        return true;
    }

    public void visualMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(gameMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    //funzione di controllo di tris
    public boolean trisCheck(char segno) {
        int x=0, y, c;
        //orizzontale
        while(x < 3) {
            c=0;
            y=0;
            while(y < 3) {
                if (gameMatrix[x][y] == segno) {
                    c++;
                }
                y++;
                if (c == 3) return true;
            }
            x++;
        }

        //verticale
        y=0;
        while(y < 3) {
            x=0;
            c=0;
            while(x < 3) {
                if (gameMatrix[x][y] == segno) {
                    c++;
                }
                x++;
                if (c == 3) return true;
            }
            y++;
        }

        //prima diagonale
        x=0;
        y=0;
        c=0;
        while(x < 3) {
            if (gameMatrix[x][y] == segno) {
                c++;
            }
            x++;
            y++;
            if (c == 3) return true;
        }

        //seconda diagonale
        x=2;
        y=0;
        c=0;
        while(x >0) {
            if (gameMatrix[x][y] == segno) {
                c++;
            }
            x--;
            y++;
            if (c == 3) return true;
        }
        return false;
    }

    public void insertClientAction(String m) {
        String [] xy = m.split("-");
        gameMatrix[Integer.parseInt(xy[0])][Integer.parseInt(xy[1])] = enemyChar;
    }

    public String insertServerAction() {
        boolean f;
        int x ,y;
        System.out.println("Scegli dove mettere la " + allyChar +" (colonna/riga, un numero da 0 a 2, scrivi 3 per finire il gioco).");
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
            if((x == 3)||(y == 3)) {
                break;
            }else if ((gameMatrix[x][y] == 'X') || (gameMatrix[x][y] == 'O')) {
                System.out.println("Hai scelto una posizione gia usata, scegli ancora.");
                f = true;
            } else {
                gameMatrix[x][y] = allyChar;
            }
        } while (f);

        StringBuilder sb = new StringBuilder();
        sb.append(x)
                .append('-')
                .append(y);
        return sb.toString();
    }
}
