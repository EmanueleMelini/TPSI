package ClientServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try{

            int portNumber=32710;
            String host="127.0.0.1"; //  parametri porta e ip del server
            Socket socket = new Socket(host, portNumber); // creo il socket (presa) per la connessione al server
            InputStream inputStream =socket.getInputStream(); // creo il canale relativo al socket
            InputStreamReader reader = new InputStreamReader(inputStream); // creo il flusso di lettura per il client, contenente i dati inviati dal server
            BufferedReader in = new BufferedReader(reader); // bufferizzazione del flusso di lettura

            Scanner input = new Scanner (System.in); // creo il canale tra la tastiera e macchina virtuale java
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true); // creo il flusso di scrittura del client, da inviare al server, il parametro true esegue Auto-flush()
            String mess = null;
            String m = null;
            while(true){
                System.out.println("Scrivi messaggio da tastiera");
                mess = input.nextLine(); // leggo una stringa da tastiera
                System.out.println("sto scrivendo buffer");
                out.println(mess); // scrivo il messaggio sul canale di output
                if (mess.equals("FINE")) break;
                //else{
                m = in.readLine(); // leggo la risposta del server sul canale di ingresso del client
                System.out.println("Il Server risponde");
                System.out.println(m);
                //}
            }
            socket.close();
        }catch(Exception E){E.printStackTrace();}
    }
}
