import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Diagnostics {
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        Elevator e1 = new Elevator2();
        Elevator e2 = new Elevator2();

        modelBehavior(e1, e2);

        out.println("Elevator 1:\n" + e1.toString() + "\n");
        out.println("Elevator 2:\n" + e2.toString());

        out.close();
    }

    public static void modelBehavior(Elevator e1, Elevator e2) {
        e1.pressButton(5);
        e1.pressButton(7);
        e2.pressButton(5);
        e2.pressButton(7);
        e1.makeRequest(8, Elevator.Direction.UP);
        e2.makeRequest(8, Elevator.Direction.UP);
        e1.makeRequest(10, Elevator.Direction.DOWN);
        e2.makeRequest(10, Elevator.Direction.DOWN);
        //buttonsPressed = {5, 7}
        //requests = {{8, UP}, {10, DOWN}}

        int e1NextFloor = e1.findNextFloor();
        int e2NextFloor = e2.findNextFloorArcher();
        //nextFloor = 10

        e1.goToNextFloor(e1NextFloor);
        e2.goToNextFloor(e2NextFloor);
        //current floor = 10

        e1.pressButton(3);
        e2.pressButton(3);
        //buttonsPressed = {3}
        //requests = {}

        e1NextFloor = e1.findNextFloor();
        e2NextFloor = e2.findNextFloorArcher();
        //nextFloor = 3

        e1.goToNextFloor(e1NextFloor);
        e2.goToNextFloor(e2NextFloor);
        //current floor = 3

        e1NextFloor = e1.findNextFloor();
        e2NextFloor = e2.findNextFloorArcher();
        //e1 nextFloor = 3
        //e2 nextFloor = 1

        e1.goToNextFloor(e1NextFloor);
        e2.goToNextFloor(e2NextFloor);
        //e1 current floor = 3
        //e2 current floor = 1
    }
}
