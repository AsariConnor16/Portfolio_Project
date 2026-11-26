import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Diagnostics {
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        Elevator e1 = new Elevator2();
        Elevator e2 = new Elevator2();

        Sequence<Integer> e1buttonsIn = new Sequence1L();
        Sequence<Integer> e2buttonsIn = new Sequence1L();

        Sequence<Integer> e1requestsIn = new Sequence1L();
        Sequence<Integer> e2requestsIn = new Sequence1L();

        modelBehavior(e1, e2, e1buttonsIn, e2buttonsIn, e1requestsIn,
                e2requestsIn);

        out.close();
    }

    public static void modelBehavior(Elevator e1, Elevator e2,
            Sequence<Integer> e1buttonsIn, Sequence<Integer> e2buttonsIn,
            Sequence<Integer> e1requestsIn, Sequence<Integer> e2requestsIn) {
        SimpleReader in = new SimpleReader1L(); //initialize input and output streams
        SimpleWriter out = new SimpleWriter1L();

        out.println("Let's model an elevator!");

        String input = "";
        //loop until the user indicates that they want to stop
        while (!input.equals("no")) {
            out.println("\n---e1 CURRENT STATE---");
            out.println(e1.toString()); //print the current state of the elevator
            out.println("\n---e2 CURRENT STATE---");
            out.println(e2.toString() + "\n"); //print the current state of the elevator

            //allow the user to 'press' 0 or more buttons, as many as they choose
            boolean pressingButtons = true;
            while (pressingButtons) {
                out.println(
                        "What button would you like to press? (enter 0 for no buttons)");
                int button = Integer.parseInt(in.nextLine());
                if (button == 0) {
                    //if the user entered 0, stop prompting for more buttons
                    pressingButtons = false;
                } else {
                    //otherwise, add that button to the Set of buttons pressed
                    e1.pressButton(button);
                    e2.pressButton(button);
                }
            }

            //allow the user to 'make' 0 or more requests, as many as they choose
            boolean makingRequests = true;
            while (makingRequests) {
                out.println(
                        "What floor would you like to request (enter 0 for no requests)");
                int floor = Integer.parseInt(in.nextLine());
                if (floor == 0) {
                    //if the user entered 0, stop prompting for more requests
                    makingRequests = false;
                } else {
                    //otherwise, ask the user for the direction
                    //then add the floor,direction pair to the map of requests
                    out.println("What direction would you like to request?");
                    String direction = in.nextLine();
                    if (direction.equals("up")) {
                        e1.makeRequest(floor, Elevator.Direction.UP);
                        e2.makeRequest(floor, Elevator.Direction.UP);
                    } else {
                        e1.makeRequest(floor, Elevator.Direction.DOWN);
                        e2.makeRequest(floor, Elevator.Direction.DOWN);
                    }
                }
            }

            //print the active buttons pressed and requests stored in the elevator
            out.println(
                    "Got it! Here are the active buttons pressed and requests for both elevators");
            out.println("Active buttons: " + e1.getButtonsPressed());
            out.println("Active Requests: " + e1.getRequests());

            out.println("\nModeling elevator behavior . . .");

            //evaluate the next floor to which the elevator will travel and print
            //it to the user
            int e1nextFloor = e1.getCurrentFloor();
            int e2nextFloor = e2.getCurrentFloor();

            e1nextFloor = e1.findNextFloor();
            e2nextFloor = e2.findNextFloorArcher();
            out.println("e1 will travel " + e1.getDirection() + " to floor "
                    + e1nextFloor
                    + ", making stops along the way if necessary");
            out.println("e2 will travel " + e2.getDirection() + " to floor "
                    + e2nextFloor
                    + ", making stops along the way if necessary");

            //move the elevator to previously evaluated nextFloor, then
            //print the new active buttons pressed and requests again
            //(they will have changed)
            e1.goToNextFloor(e1nextFloor);
            e2.goToNextFloor(e2nextFloor);
            out.println("e1 is now at floor " + e1.getCurrentFloor());
            out.println("e2 is now at floor " + e2.getCurrentFloor());
            out.println("Active buttons: " + e1.getButtonsPressed());
            out.println("Active requests: " + e1.getRequests());

            //ask if the user would like to continue modeling the elevator
            out.println(
                    "Would you like to continue modeling the elevator? [yes/no]");
            input = in.nextLine();
        }

        out.close();
        in.close();
    }
}
