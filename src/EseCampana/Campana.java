package EseCampana;

public class Campana implements Runnable {

	String verso;
	Integer volte;

	public Campana(String verso, Integer volte) {
		this.verso = verso;
		this.volte = volte;
	}

	@Override
	public void run() {
		for(int i = 0; i < volte; i++) {
			System.out.println(verso);
		}
	}

}
