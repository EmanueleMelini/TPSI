package ControlloA;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {

			int portNumber = 32710;
			String host = "127.0.0.1";
			Socket socket = new Socket(host, portNumber);
			InputStream inputStream = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader in = new BufferedReader(reader);

			Scanner input = new Scanner(System.in);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String mess;
			String m;
			while(true) {
				System.out.println("Scrivi messaggio da tastiera");
				mess = input.nextLine();
				System.out.println("sto scrivendo buffer");
				out.println(mess);
				if(mess.equals("FINE"))
					break;
				m = in.readLine();
				System.out.println("Il Server risponde");
				System.out.println(m);
			}
			socket.close();
		} catch(Exception E) {
			E.printStackTrace();
		}
	}

}

