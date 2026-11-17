import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;

/**
 * @convention <pre>
 *   1 <= this.currentFloor && this.currentFloor <= TOP_FLOOR
 *   && for all x: integer
 *        (x in this.buttonsPressed => (1 <= x && x <= TOP_FLOOR))
 *   && for all y: integer
 *        (y in domain(this.requests) => (1 <= y && y <= TOP_FLOOR
 *          && this.requests.value(y) /= empty_set))
 * </pre>
 *
 * @correspondence <pre>
     *   currentFloor = this.currentFloor
     *   && directionTravelling = this.directionTravelling
     *   && buttonsPressed = this.buttonsPressed
     *   && requests = this.requests
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
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.currentFloor = 1;
        this.directionTravelling = Direction.UP;
        this.buttonsPressed = new Set1L<Integer>();
        this.requests = new Map1L<Integer, Set<Direction>>();
    }

    /**
     * No argument constructor.
     */
    public Elevator2() {
        this.createNewRep();
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
     * @requires 1 <= floor <= TOP_FLOOR
     * @updates this
     * @ensures currentFloor = floor
     */
    @Override
    public void setCurrentFloor(int floor) {
        assert 1 <= floor : "Floor must be at least 1";
        assert floor <= TOP_FLOOR : "Floor cannot be greater than TOP_FLOOR";

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
     * @requires 1 <= floor <= TOP_FLOOR
     * @requires buttonsPressed.contains(floor) = false
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
        assert floor <= TOP_FLOOR : "Floor cannot be greater than TOP_FLOOR";

        this.buttonsPressed.add(floor);
    }

    /**
     * Removes {@code floor} from the set of buttons pressed.
     *
     * @requires buttonsPressed.contains(floor) = true
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
     * @requires 1 <= floor <= TOP_FLOOR && (requests.hasKey(floor) = false ||
     *           requests.value(floor).contains(d) = false)
     * @updates this
     * @ensures if floor is already in the domain of requests, add d to its
     *          associated Set<Direction>. Otherwise, add {(floor, {d})} to
     *          requests.
     */
    @Override
    public void makeRequest(int floor, Direction d) {
        assert 1 <= floor : "Floor must be at least 1";
        assert floor <= TOP_FLOOR : "Floor cannot be greater than TOP_FLOOR";
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
}