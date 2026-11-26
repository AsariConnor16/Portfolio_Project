import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;

/**
 * @convention <pre>
 *   1 <= currentFloor <= topFloor
 *   && for all x: integer
 *        (x in this.buttonsPressed => (1 <= x <= topFloor))
 *   && for all y: integer
 *        (y in domain(this.requests) => (1 <= y <= topFloor
 *          && this.requests.value(y) /= empty_set))
 * </pre>
 *
 * @correspondence <pre>
     *   currentFloor = this.currentFloor
     *   && directionTravelling = this.directionTravelling
     *   && buttonsPressed = this.buttonsPressed
     *   && requests = this.requests
     *   && topFloor = the highest floor that the elevator can reach
     * [this.buttonsPressed is the mathematical set of all floor integers whose
     *  corresponding buttons have been pressed on the interior of the elevator;
     *  and this.requests is a map from floor integers (domain) to non-empty sets
     *  of Directions (values), representing active outside calls where
     *  passengers on a given floor are requesting travel.]
     * </pre>
 *
 * @author Connor Asari
 */

public class Elevator2 extends ElevatorSecondary {
    /**
     * The current floor of the elevator.
     */
    private int currentFloor;

    /**
     * The direction the elevator is currently travelling.
     */
    private Direction directionTravelling;

    /**
     * The Set containing the floor numbers of buttons pressed inside the
     * elevator.
     */
    private Set<Integer> buttonsPressed;

    /**
     * The Map<K,V> of requested floors where K is the requested floor number
     * and V is the non-empty Set of Directions requested.
     */
    private Map1L<Integer, Set<Direction>> requests;

    /**
     * The highest floor to which the elevator can travel.
     */
    private int topFloor;

    /**
     * The number of floors the elevator has travelled.
     */
    private int floorsTravelled;

    /**
     * Pairs of floors and the floorsTravelled at the time of un-pressing.
     */
    private Sequence<Integer> buttonResponses;

    /**
     * Pairs of floors and the floorsTravelled at the time of un-requesting.
     */
    private Sequence<Integer> requestResponses;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.currentFloor = 1;
        this.directionTravelling = Direction.UP;
        this.buttonsPressed = new Set1L<Integer>();
        this.requests = new Map1L<Integer, Set<Direction>>();
        this.topFloor = 10;
        this.floorsTravelled = 0;
        this.buttonResponses = new Sequence1L();
        this.requestResponses = new Sequence1L();
    }

    /**
     * No argument constructor. This is the default constructor.
     */
    public Elevator2() {
        this.createNewRep();
    }

    /**
     * Constructor that sets {@code topFloor} to the value of {@code top}.
     *
     * @param top
     *            the value of which to set as the topFloor the elevator can
     *            reach.
     * @requires 1 <= top
     */
    public Elevator2(int top) {
        assert 1 <= top : "ERROR: top must be at least 1";

        this.createNewRep(); //call create new rep
        this.topFloor = top; //overwrite topFloor
    }

    @Override
    public Elevator newInstance() {
        try {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass().getName());
        }
    }

    @Override
    public void clear() {
        this.createNewRep();
    }

    @Override
    public void transferFrom(Elevator e) {
        assert e != null : "Parameter cannot be null";
        assert e != this : "Parameter cannot be the same object as this";
        assert e instanceof Elevator2 : "Parameter must be same dynamic type as this";

        Elevator2 localE = (Elevator2) e; //cast e as an instance of Elevator2

        //set all internal variables of this equal to those of localE
        this.currentFloor = localE.currentFloor;
        this.directionTravelling = localE.directionTravelling;
        this.buttonsPressed = localE.buttonsPressed;
        this.requests = localE.requests;
        this.topFloor = localE.topFloor;
        this.floorsTravelled = localE.floorsTravelled;
        this.buttonResponses = localE.buttonResponses;
        this.requestResponses = localE.requestResponses;

        localE.createNewRep(); //clear localE
    }

    /**
     * Returns the current floor that the elevator is on.
     *
     * @return the current floor of the elevator
     */
    @Override
    public int getCurrentFloor() {
        return this.currentFloor;
    }

    /**
     * Sets the current floor of the elevator to {@code floor}.
     *
     * @param floor
     *            the floor of which to set currentFloor
     * @requires 1 <= floor <= topFloor
     * @updates this
     * @ensures currentFloor = floor
     */
    @Override
    public void setCurrentFloor(int floor) {
        assert 1 <= floor : "Floor must be at least 1";
        assert floor <= this.topFloor : "Floor cannot be greater than topFloor";

        this.currentFloor = floor;
    }

    /**
     * Returns the direction in which the elevator is currently travelling.
     *
     * @return UP iff directionTravelling = UP, DOWN otherwise
     */
    @Override
    public Direction getDirection() {
        return this.directionTravelling;
    }

    /**
     * Sets directionTravelling to the value of {@code d}.
     *
     * @param d
     *            the direction of which to set directionTravelling
     * @updates this
     * @ensures directionTravelling = d
     */
    @Override
    public void setDirection(Direction d) {
        this.directionTravelling = d;
    }

    /**
     * Adds {@code floor} to the set of buttons pressed.
     *
     * @requires [1 <= floor <= topFloor] && [!buttonsPressed.contains(floor)]
     * @updates this
     * @param floor
     *            the floor to add to buttonsPressed
     * @ensures buttonsPressed = #buttonsPressed union {floor}
     */
    @Override
    public void pressButton(int floor) {
        assert !this.buttonsPressed
                .contains(floor) : "Floor has already been pressed";
        assert 1 <= floor : "Floor must be at least 1";
        assert floor <= this.topFloor : "Floor cannot be greater than topFloor";

        this.buttonsPressed.add(floor);
    }

    /**
     * Removes {@code floor} from the set of buttons pressed.
     *
     * @requires buttonsPressed.contains(floor)
     * @updates this
     * @param floor
     *            the floor to remove from buttonsPressed
     * @ensures buttonsPressed = #buttonsPressed \ {floor}
     */
    @Override
    public void unpressButton(int floor) {
        assert this.buttonsPressed
                .contains(floor) : "Floor is not already pressed";

        this.buttonsPressed.remove(floor);
    }

    /**
     * Returns buttonsPressed.
     *
     * @return buttonsPressed
     */
    @Override
    public Set<Integer> getButtonsPressed() {
        return this.buttonsPressed;
    }

    /**
     * Adds {@code floor}, {@code d} to the Map<Integer, Set<Direction>> of
     * requests.
     *
     * @param floor
     *            the floor requested
     * @param d
     *            the direction requested
     * @requires 1 <= floor <= topFloor && [!requests.hasKey(floor) ||
     *           !requests.value(floor).contains(d))]
     * @updates this
     * @ensures if floor is already in the domain of requests, add d to its
     *          associated Set<Direction>. Otherwise, add {(floor, {d})} to
     *          requests.
     */
    @Override
    public void makeRequest(int floor, Direction d) {
        assert 1 <= floor : "Floor must be at least 1";
        assert floor <= this.topFloor : "Floor cannot be greater than topFloor";
        assert !this.requests.hasKey(floor)
                || !this.requests.value(floor).contains(
                        d) : "A request for this floor and direciton already exists";

        if (this.requests.hasKey(floor)) {
            this.requests.value(floor).add(d);
        } else {
            Set<Direction> temp = new Set1L<Direction>();
            temp.add(d);
            this.requests.add(floor, temp);
        }
    }

    /**
     * Removes {@code d} from the directions requested at {@code floor}. If no
     * directions remain at {@code floor}, the key {@code floor} is removed from
     * requests.
     *
     * @param floor
     *            the floor whose Set<Direction> at which to look
     * @param d
     *            the direction to remove
     * @requires requests.contains(floor) = true &&
     *           requests.value(floor).contains(d)
     * @updates this
     * @ensures if #requests.value(floor) = {d} then requests = #requests
     *          without key floor else requests = #requests with value(floor)
     *          replaced by #requests.value(floor) \ {d}
     */
    @Override
    public void unmakeRequest(int floor, Direction d) {
        assert this.requests
                .hasKey(floor) : "No existing requests at this floor";
        assert this.requests.value(floor).contains(
                d) : "That direction is not an existing request at this floor";

        //remove the direction from its Set
        this.requests.value(floor).remove(d);
        //if that Set is now empty, remove the entire pair
        if (this.requests.value(floor).size() == 0) {
            this.requests.remove(floor);
        }
    }

    /**
     * Returns requests.
     *
     * @return requests
     */
    @Override
    public Map1L<Integer, Set<Direction>> getRequests() {
        return this.requests;
    }

    /**
     * Returns the topFloor.
     *
     * @return topFloor
     */
    @Override
    public int getTopFloor() {
        return this.topFloor;
    }

    /**
     * Returns the floors travelled by the elevator.
     *
     * @return the floors travelled by the elevator
     */
    @Override
    public int getFloorsTravelled() {
        return this.floorsTravelled;
    }

    /**
     * Adds x to floorsTravelled.
     *
     * @updates this
     * @ensures floorsTravlled = #floorsTravlled + x
     */
    @Override
    public void addFloorsTravelled(int x) {
        this.floorsTravelled += x;
    }

    /**
     * Returns the response times for buttons pressed in the elevator.
     *
     * @return buttonResponses
     */
    @Override
    public Sequence<Integer> getButtonResponses() {
        return this.buttonResponses;
    }

    /**
     * Adds floor and the current value of floorsTravelled to the end of
     * buttonResponses
     *
     * @param floor
     *            the floor to add to buttonResponses
     */
    @Override
    public void addToButtonResponses(int floor) {
        this.buttonResponses.add(this.buttonResponses.length(), floor);
        this.buttonResponses.add(this.buttonResponses.length(),
                this.floorsTravelled);
    }

    /**
     * Returns the response times for request made in the elevator.
     *
     * @return requestResponses
     */
    @Override
    public Sequence<Integer> getRequestResponses() {
        return this.requestResponses;
    }

    /**
     * Adds floor and the current value of floorsTravelled to the end of
     * requestResponses
     *
     * @param floor
     *            the floor to add to requestResponses
     */
    @Override
    public void addToRequestResponses(int floor) {
        this.requestResponses.add(this.requestResponses.length(), floor);
        this.requestResponses.add(this.requestResponses.length(),
                this.floorsTravelled);
    }
}