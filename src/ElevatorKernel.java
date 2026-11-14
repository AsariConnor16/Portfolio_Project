//package components.naturalnumber;

import components.map.Map;
import components.set.Set;
import components.standard.Standard;

/**
 * Elevator kernel component interface.
 *
 * @mathmodel {@code
 * type ElevatorKernel is modeled by tuple
 *   currentFloor: integer
 *   directionTravelling: Direction
 *   buttonsPressed: set of integer
 *   requests: map of integer and set of Direction
 * }
 * @init {@code
 *   currentFloor = 1
 *   directionTravelling = UP
 *   buttonsPressed = {}
 *   requests = empty_map
 * }
 *
 * @author Connor Asari
 */
public interface ElevatorKernel extends Standard<Elevator> {
    /**
     * Direction of an elevator request or movement.
     */
    enum Direction {
        UP, DOWN
    }

    /**
     * A constant used to denote the top floor that the elevator can reach.
     */
    int TOP_FLOOR = 10;

    /**
     * /** Returns the current floor that the elevator is on.
     *
     * @return the current floor of the elevator
     */
    int getCurrentFloor();

    /**
     * Sets the current floor of the elevator to {@code floor}.
     *
     * @param floor
     *            the floor of which to set currentFloor
     * @requires 1 <= floor <= TOP_FLOOR
     * @updates this
     * @ensures currentFloor = floor
     */
    void setCurrentFloor(int floor);

    /**
     * Returns the direction in which the elevator is currently travelling.
     *
     * @return UP iff directionTravelling = UP, DOWN otherwise
     */
    Direction getDirection();

    /**
     * Sets directionTravelling to the value of {@code d}.
     *
     * @param d
     *            the direction of which to set directionTravelling
     * @updates this
     * @ensures directionTravelling = d
     */
    void setDirection(Direction d);

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
    void pressButton(int floor);

    /**
     * Removes {@code floor} from the set of buttons pressed.
     *
     * @requires buttonsPressed.contains(floor) = true
     * @updates this
     * @param floor
     *            the floor to remove from buttonsPressed
     * @ensures buttonsPressed = #buttonsPressed \ {floor}
     */
    void unpressButton(int floor);

    /**
     * Returns buttonsPressed.
     *
     * @return buttonsPressed
     */
    Set<Integer> getButtonsPressed();

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
    void makeRequest(int floor, Direction d);

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
    void unmakeRequest(int floor, Direction d);

    /**
     * Returns requests.
     *
     * @return requests
     */
    Map<Integer, Set<Direction>> getRequests();
}