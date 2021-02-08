package EseCampana;

public class SuonaCampane {

	public static void main(String[] args) {
		Thread Din = new Thread(new Campana("Din", 5));
		Thread Don = new Thread(new Campana("Don", 5));
		Thread Dan = new Thread(new Campana("Dan", 5));
		Din.start();
		Don.start();
		Dan.start();
	}

}
