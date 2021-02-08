package PiGreco;

import java.util.Scanner;

public class PiGreco {

	public final double rad;
	private double l;
	private double r;

	public PiGreco() {
		rad = Math.toRadians(180);
		l = 1;
		r = 1;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PiGreco pi = new PiGreco();
		do {
			System.out.println("Metodo seno o calcolo (1-2, 3:fine)");
			int scelta = sc.nextInt();
			if(scelta == 1) {
				pi.aproximateN();
			} else if(scelta == 2) {
				pi.aproximateCicle();
			} else if(scelta == 3) {
				break;
			} else {
				System.out.println("Scelta sbagliata");
			}
		} while(true);
	}

	public void aproximateN() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quanti lati?");
		int lati = sc.nextInt();
		System.out.println("L'approssimazione del pi e' :" + lati * Math.sin(rad / lati));
	}

	public void aproximateCicle() {
		l = 1;
		r = 1;
		Scanner sc = new Scanner(System.in);
		System.out.println("Quante volte?");
		int volte = sc.nextInt();
		int lati = 6;
		double apo;
		double l2 = 0;
		for(int i = 0; i < volte; i++) {
			apo = Math.sqrt(Math.pow(r, 2) - Math.pow((l / 2), 2));
			l2 = Math.sqrt(Math.pow((r - apo), 2) + Math.pow((l / 2), 2));
			lati = lati * 2;
			l = l2;
		}

		double peri = lati * l2;
		double pi = peri / (2 * r);
		System.out.println("L'approssimazione del pi e' :" + pi);
	}

}
