import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Diagnostics {
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        Elevator e1 = new Elevator2();
        Elevator e2 = new Elevator2();

        e1.pressButton(5);
        e2.pressButton(5);

        int nextFloor1 = e1.findNextFloor();
        int nextFloor2 = e2.findNextFloorArcher();

        e1.goToNextFloor(nextFloor1);
        e2.goToNextFloor(nextFloor2);

        nextFloor1 = e1.findNextFloor();
        nextFloor2 = e2.findNextFloorArcher();

        e1.goToNextFloor(nextFloor1);
        e2.goToNextFloor(nextFloor2);

        out.println(e1.toString());
        out.println(e2.toString());

        out.close();

    }
}
