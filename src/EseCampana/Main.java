package EseCampana;

public class Main extends Thread {
    String message;

    public Main(String message) {
        this.message = message;
    }

    public void run() {
        this.setName(message);
        System.out.println("Sono il thread " + this.getName());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

