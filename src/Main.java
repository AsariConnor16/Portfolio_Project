import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Main {
    public static void main(String[] args) {
        Elevator e1 = new Elevator2(14);
        SimpleWriter out = new SimpleWriter1L();

        e1.pressButton(13);

    }
}
