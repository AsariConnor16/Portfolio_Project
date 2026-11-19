import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class Main {
    /**
     * This main method allows a user to interactively model the behavior of the
     * Elevator component. It first asks the user to enter a top floor, then
     * instantiates an elevator with that many floors. Starting at the initial
     * state of the elevator, the program asks the user to press 0 or more
     * buttons and make 0 or more requests. Then, the elevator's behavior is
     * emplified. That's to say, the elevator evaluates the next floor to which
     * to travel, then travels to that floor (making stops along the way if
     * necessary), and updates the active buttons pressed and requests
     * accordingly. Then, the program asks the user if they want to continue
     * modeling the elevator, if yes, the process of asking for buttons and
     * requests begins again, this time the elevator's current state carries
     * over from the end of the previous loop iteration.
     *
     * @param args
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L(); //initialize input and output streams
        SimpleWriter out = new SimpleWriter1L();

        out.println("Let's model an elevator!");
        out.print("What floor would you like to set as the top floor?");
        out.println(
                "Elevator will be able to travel to any floors between 1 and topFloor.");

        int top = Integer.parseInt(in.nextLine());

        //construct the elevator with topFloor = top
        Elevator e = new Elevator2(top);
        out.println("Creating elevator . . .\nElevator created!\n");

        out.println(
                "Now let's model the continuous operation of the elevator!");
        String input = "";
        //loop until the user indicates that they want to stop
        while (!input.equals("no")) {
            out.println("\n---CURRENT STATE---");
            out.println(e.toString()); //print the current state of the elevator

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
                    e.pressButton(button);
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
                        e.makeRequest(floor, Elevator.Direction.UP);
                    } else {
                        e.makeRequest(floor, Elevator.Direction.DOWN);
                    }
                }
            }

            //print the active buttons pressed and requests stored in the elevator
            out.println(
                    "Got it! Here are the active buttons pressed and requests");
            out.println("Active buttons: " + e.getButtonsPressed());
            out.println("Active Requests: " + e.getRequests());

            out.println("\nModeling elevator behavior . . .");

            //evaluate the next floor to which the elevator will travel and print
            //it to the user
            int nextFloor = e.findNextFloor();
            out.println("Elevator will travel " + e.getDirection()
                    + " to floor " + nextFloor
                    + ", making stops along the way if necessary");

            //move the elevator to previously evaluated nextFloor, then
            //print the new active buttons pressed and requests again
            //(they will have changed)
            e.goToNextFloor(nextFloor);
            out.println("Elevator is now at floor " + e.getCurrentFloor());
            out.println("Active buttons: " + e.getButtonsPressed());
            out.println("Active requests: " + e.getRequests());

            //ask if the user would like to continue modeling the elevator
            out.println(
                    "Would you like to continue modeling the elevator? [yes/no]");
            input = in.nextLine();
        }

        out.close();
        in.close();
    }
}
