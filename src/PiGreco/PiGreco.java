package PiGreco;

import java.util.Scanner;

public class PiGreco {
    public final double rad;

    public PiGreco() {
        rad = Math.toRadians(180);
    }

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        PiGreco pi = new PiGreco();
        do {
            System.out.println("Metodo singolo o ciclo (1-2, 3:fine)");
            int scelta = sc.nextInt();
            if (scelta == 1) {
                pi.aproximateN();
            } else if (scelta == 2) {
                pi.aproximateCicle();
            } else if(scelta == 3) {
                break;
            } else {
                System.out.println("Scelta sbagliata");
            }
        } while (true);
    }


    public void aproximateN() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quanti lati?");
        int lati = sc.nextInt();
        System.out.println("L'approssimazione del pi e' :" + lati*Math.sin(rad/lati));
    }

    public void aproximateCicle() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quanti lati?");
        int lati = sc.nextInt();
        for(int i = 0; i < lati; i++) {
            System.out.println("L'approssimazione del pi e' :" + i*Math.sin(rad/i));
        }
    }
}
