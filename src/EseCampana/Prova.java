package EseCampana;

public class Prova {
    public static void main(String [] args) {
        /*Main t = new Main("prova");
        t.start();*/
        int prov = 2011241746;
        int MM, HH, dd, mm, yy;
        MM = prov % 100;
        prov = prov / 100;

        HH = prov % 100;
        prov = prov / 100;


        dd = prov % 100;
        prov = prov / 100;

        mm = prov % 100;
        prov = prov / 100;

        yy = prov % 100;

        System.out.println(MM);
        System.out.println(HH);
        System.out.println(dd);

        System.out.println(mm);

        System.out.println(yy);
    }
}