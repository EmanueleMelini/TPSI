package ChatBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Una semplice classe per leggere stringhe e numeri dallo standard input.
 */

public class input {

	private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Legge una linea di input. Nell'improbabile caso di una IOException, il programma termina.
	 *
	 * @return restituisce la linea di input che l'utente ha battuto.
	 */

	public static String readLine() {
		String inputLine = "";
		try {
			inputLine = reader.readLine();
		} catch(IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		return inputLine;
	}

	/**
	 * Legge una linea di input e la converte in un intero.
	 * Eventuali spazi bianchi prima e dopo l'intero vengono ignorati.
	 *
	 * @return l'intero dato in input dall'utente
	 */

	public static int readInt() {
		String inputString = readLine();
		inputString = inputString.trim();
		return Integer.parseInt(inputString);
	}

	/**
	 * Legge una linea di input e la converte in un numero in virgola mobile.
	 * Eventuali spazi bianchi prima e dopo il numero vengono ignorati.
	 *
	 * @return il numero dato in input dall'utente
	 */

	public static double readDouble() {
		String inputString = readLine();
		inputString = inputString.trim();
		return Double.parseDouble(inputString);
	}

	/**
	 * Legge una linea di input e ne estrae il primo carattere.
	 *
	 * @return il primo carattere della riga data in input dall'utente
	 */

	public static char readChar() {
		String inputString = readLine();
		return inputString.charAt(0);
	}

}
