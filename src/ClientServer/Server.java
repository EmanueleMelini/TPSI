package ClientServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
    public static void main(String[] args){
        try{
            String messaggioDaTrasmettere;
            String messaggioRicevuto;
            int portNumber=32710;
            Scanner tastiera = new Scanner(System.in);

            ServerSocket s= new ServerSocket(portNumber);
            System.out.println("SERVER avviato");

            Socket socket = s.accept();
            InputStream inputStream =socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader inputMes = new BufferedReader(reader);

            OutputStream streamInUscita = socket.getOutputStream();
            PrintWriter out = new PrintWriter(streamInUscita,true);

            while(true){
                messaggioRicevuto = inputMes.readLine();
                System.out.println("Il SERVER riceve: "+messaggioRicevuto);

                if (messaggioRicevuto.equals("FINE")) break;

                int cont=0;
                System.out.println("Scrivi risposta: ");
                for(int i=0;i<messaggioRicevuto.length();i++) {
                    if((((((((((messaggioRicevuto.charAt(i) == 'a')||
                            (messaggioRicevuto.charAt(i) == 'e'))||
                            (messaggioRicevuto.charAt(i) == 'i'))||
                            (messaggioRicevuto.charAt(i) == 'o'))||
                            (messaggioRicevuto.charAt(i) == 'u'))||
                            (messaggioRicevuto.charAt(i) == 'A'))||
                            (messaggioRicevuto.charAt(i) == 'E'))||
                            (messaggioRicevuto.charAt(i) == 'I'))||
                            (messaggioRicevuto.charAt(i) == 'O'))||
                            (messaggioRicevuto.charAt(i) == 'U'))
                        cont++;
                }
                //messaggioDaTrasmettere = tastiera.nextLine();
                out.println(cont);
            }
            socket.close();
        }catch(Exception e){e.printStackTrace();}
    }
}