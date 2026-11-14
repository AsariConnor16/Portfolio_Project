/**
 * {@code Elevator} enhanced with secondary methods.
 *
 * @author Connor Asari
 */
public interface Elevator extends ElevatorKernel {
    /**
     * Finds the next floor to which the elevator should travel next.
     *
     * @requires buttonsPressed /= empty_set || requests /= empty_map
     * @return the next floor to which to travel
     * @ensures findNextFloor = the farthest floor from current in the direction
     *          the elevator is currently travelling that exists in
     *          buttonsPressed or requests. If no such value exists,
     *          findNextFloor = the farthest floor from current in the opposite
     *          direction the elevator is currently travelling.
     */
    int findNextFloor();

    /**
     * Moves the elevator to floor {@code target}, making stops along the way at
     * each intermediate floor, if that floor is contained in buttonsPressed or
     * contained in requests, given that the value in requests corresponding to
     * that floor contains the direction the elevator is currently travelling.
     *
     * @param target
     *            the target floor to which to travel.
     * @updates this
     * @requires 1 <= target <= TOP_FLOOR
     * @ensures currentFloor = target && for every floor x between #currentFloor
     *          and target, if x mandates a stop, such stop is made.
     */
    void goToNextFloor(int target);

    /**
     * 'stops' the elevator at {@code floor}. If {@code floor} is in
     * buttonsPressed, it is removed from buttonsPressed. If {@code floor} is in
     * requests and {@code d} is in the Set<Direction> associated with
     * {@code floor}, {@code d} is removed from the Set<Direction>.
     * 
     * @param the
     *            direction the elevator is travelling when the stop is made
     * @updates this
     * @requires 1 <= floor <= TOP_FLOOR
     * @ensures buttonsPressed.contains(floor) = false &&
     *          (requests.hasKey(floor) = false ||
     *          requests.value(floor).contains(d) = false)
     */
    void stop(Direction d);
}
