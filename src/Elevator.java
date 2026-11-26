/**
 * {@code Elevator} enhanced with secondary methods.
 *
 * @author Connor Asari
 */
public interface Elevator extends ElevatorKernel {
    /**
     * Finds the next floor to which the elevator should travel next.
     *
     * @return the next floor to which to travel
     * @ensures IF buttonsPressed is not empty OR requests is not empty,
     *          findNextFloor = the closest floor from current in the direction
     *          the elevator is currently travelling that exists in
     *          buttonsPressed or requests. If no such value exists,
     *          findNextFloor = the closest floor from current in the opposite
     *          direction the elevator is currently travelling. ELSE
     *          findNextFloor = currentFloor
     */
    int findNextFloor();

    /**
     * Finds the next floor to which the elevator should travel next.
     *
     * @return the next floor to which to travel
     * @ensures IF buttonsPressed is not empty OR requests is not empty,
     *          findNextFloor = the closest floor from current in the direction
     *          the elevator is currently travelling that exists in
     *          buttonsPressed or requests. If no such value exists,
     *          findNextFloor = the closest floor from current in the opposite
     *          direction the elevator is currently travelling. ELSE
     *          findNextFloor = 1
     */
    int findNextFloorArcher();

    /**
     * Moves the elevator to floor {@code target} and makes a stop at that
     * floor.
     *
     * @param target
     *            the target floor to which to travel.
     * @updates this
     * @requires 1 <= target <= topFloor
     * @ensures currentFloor = target && for every floor x between #currentFloor
     *          and target, if x mandates a stop, such stop is made.
     */
    void goToNextFloor(int target);

    /**
     * 'stops' the elevator at its current floor. If {@code currentFloor} is in
     * buttonsPressed, it is removed from buttonsPressed. If
     * {@code currentFloor} is in requests and {@code d} is in the
     * Set<Direction> associated with {@code currentFloor}, {@code d} is removed
     * from the Set<Direction>.
     *
     * @param d
     *            direction the elevator is travelling when the stop is made
     * @updates this
     * @ensures !buttonsPressed.contains(currentFloor) &&
     *          [!(requests.hasKey(currentFloor) ||
     *          !requests.value(currentFloor).contains(d)]
     */
    void stop(Direction d);
}
