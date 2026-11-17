import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Main {
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("Let's model an elevator!");
        out.println(
                "How many floors would you like the elevator to be able to travel to?");
        int top = Integer.parseInt(in.nextLine());

        Elevator e = new Elevator2(top);
        out.println("Creating elevator . . .\nElevator created!\n");

        out.println(
                "Now let's model the continuous operation of the elevator!");
        String input = "";
        while (!input.equals("no")) {
            out.println("\n---CURRENT STATE---");
            out.println(e.toString());

            boolean pressingButtons = true;
            while (pressingButtons) {
                out.println(
                        "What button would you like to press? (enter 0 for no buttons)");
                int button = Integer.parseInt(in.nextLine());
                if (button == 0) {
                    pressingButtons = false;
                } else {
                    e.pressButton(button);
                }
            }

            boolean makingRequests = true;
            while (makingRequests) {
                out.println(
                        "What floor would you like to request (enter 0 for no requests)");
                int floor = Integer.parseInt(in.nextLine());
                if (floor == 0) {
                    makingRequests = false;
                } else {
                    out.println("What direction would you like to request?");
                    String direction = in.nextLine();
                    if (direction.equals("up")) {
                        e.makeRequest(floor, Elevator.Direction.UP);
                    } else {
                        e.makeRequest(floor, Elevator.Direction.DOWN);
                    }
                }
            }

            out.println(
                    "Got it! Here are the active buttons pressed and requests");
            out.println("Active buttons: " + e.getButtonsPressed());
            out.println("Active Requests: " + e.getRequests());

            out.println("\nModeling elevator behavior . . .");

            int nextFloor = e.findNextFloor();
            out.println("Elevator will travel " + e.getDirection()
                    + " to floor " + nextFloor
                    + ", making stops along the way if necessary");
            e.goToNextFloor(nextFloor);
            out.println("Elevator is now at floor " + e.getCurrentFloor());
            out.println("Active buttons: " + e.getButtonsPressed());
            out.println("Active requests: " + e.getRequests());

            out.println(
                    "Would you like to continue modeling the elevator? [yes/no]");
            input = in.nextLine();
        }

        out.close();
        in.close();
    }
}
