import components.set.Set;
import components.set.Set1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 *
 * This a file only exists to store a copy of my brainstorming during the early
 * stages of the project.
 *
 * @author Connor Asari
 */
public class ElevatorMVP {
    //1. PRIVATE VARIABLES
    private int currentFloor;
    private int size;
    private boolean travellingUp;
    private boolean doorsOpen;
    private Set<Integer> buttonsPressedInside;

    private final int maxCapacity = 20;

    //2. INITIAL STATE
    public ElevatorMVP() {
        this.currentFloor = 1;
        this.size = 0;
        this.travellingUp = true;
        this.doorsOpen = false;
        this.buttonsPressedInside = new Set1L();
    }

    //3. SOME KERNEL METHODS
    //Note: almost all of these need requires clauses, but I will tackle that later
    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public void setCurrentFloor(int floor) {
        this.currentFloor = floor;
    }

    public int size() {
        return this.size;
    }

    public boolean areDoorsOpen() {
        return this.doorsOpen;
    }

    public void setDoorsOpen(boolean input) {
        this.doorsOpen = input;
    }

    public void pressButton(int button) {
        this.buttonsPressedInside.add(button);
    }

    public Set<Integer> getButtonsPressedInside() {
        return this.buttonsPressedInside;
    }

    //4. SOME SECONDARY METHODS
    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.maxCapacity;
    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        ElevatorMVP elevator = new ElevatorMVP();
        out.println("Size = " + elevator.size());
        out.println("Current Floor = " + elevator.getCurrentFloor());

        elevator.pressButton(2);
        elevator.pressButton(4);
        out.print("Buttons Pressed Inside = ");
        out.println(elevator.getButtonsPressedInside());

        elevator.setDoorsOpen(true);
        out.println("DoorsOpen = " + elevator.areDoorsOpen());

        out.close();
    }
}
