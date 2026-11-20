import components.map.Map;
import components.set.Set;

/**
 * Layered implementations of secondary methods for {@code Elevator}.
 *
 * @author Connor Asari
 */
public abstract class ElevatorSecondary implements Elevator {
    /**
     * Finds the next floor to which the elevator should travel next.
     *
     * @return the next floor to which to travel
     * @ensures IF buttonsPressed is not empty OR requests is not empty,
     *          findNextFloor = the farthest floor from current in the direction
     *          the elevator is currently travelling that exists in
     *          buttonsPressed or requests. If no such value exists,
     *          findNextFloor = the farthest floor from current in the opposite
     *          direction the elevator is currently travelling. ELSE
     *          findNextFloor = currentFloor
     */
    @Override
    public int findNextFloor() {
        //assume the elevator doesn't need to go anywhere
        int result = this.getCurrentFloor();

        //get buttons pressed, requests, and direction
        Set<Integer> buttons = this.getButtonsPressed();
        Map<Integer, Set<Direction>> requests = this.getRequests();
        Direction d = this.getDirection();

        //if there are any buttons pressed or active requests
        if (buttons.size() > 0 || requests.size() > 0) {
            //if the elevator is currently travelling up,
            if (d == Direction.UP) {
                int maxButton = 1; //find the max button pressed (inside the elevator)
                int maxRequest = 1; //and the max floor requested (outside the elevator)

                //if there are buttons pressed, find the max button pressed
                if (buttons.size() > 0) {
                    maxButton = this.findMaxSet(buttons);
                }

                //if there are requests made, find the max floor that is being requested
                if (requests.size() > 0) {
                    maxRequest = this.findMaxMap(requests);
                }

                //set result as the higher of the 2 values
                result = Math.max(maxButton, maxRequest);

                //if result is a lower floor than current, then the elevator must
                //travel down. Recursive call with Direction=DOWN
                if (result < this.getCurrentFloor()) {
                    this.setDirection(Direction.DOWN); //reverse the elevator's direction
                    result = this.findNextFloor();
                }
            } else {
                int minButton = this.getTopFloor(); //find the min button pressed
                int minRequest = this.getTopFloor(); //find the min floor requested

                //if there are buttons pressed, find the mininimum
                if (buttons.size() > 0) {
                    minButton = this.findMinSet(buttons);
                }
                //if there are any active requests, find the minimum floor requested
                if (requests.size() > 0) {
                    minRequest = this.findMinMap(requests);
                }

                //set result as the lower of the 2 values
                result = Math.min(minButton, minRequest);

                //if result is a higher flor than current, then the elevator must
                //travel up. Recursive call with Direction=UP
                if (result > this.getCurrentFloor()) {
                    this.setDirection(Direction.UP); //reverse the elevator's direction
                    result = this.findNextFloor();
                }
            }
        }
        return result;
    }

    /**
     * Moves the elevator to floor {@code target}, making stops along the way at
     * each intermediate floor, if that floor is contained in buttonsPressed or
     * contained in requests, given that the value in requests corresponding to
     * that floor contains the direction the elevator is currently travelling.
     *
     * @param target
     *            the target floor to which to travel.
     * @updates this
     * @requires 1 <= target <= topFloor
     * @ensures currentFloor = target && for every floor x between #currentFloor
     *          and target, if x mandates a stop, such stop is made.
     */
    @Override
    public void goToNextFloor(int target) {
        assert 1 <= target : "Floor must be at least 1";
        assert target <= this
                .getTopFloor() : "Floor cannot be greater than topFloor";

        //get currentFloor, buttons, and requests
        int current = this.getCurrentFloor();
        Set<Integer> buttons = this.getButtonsPressed();
        Map<Integer, Set<Direction>> requests = this.getRequests();

        //if we need to travel up to get to target,
        if (current < target) {
            this.setDirection(Direction.UP);
            //while we haven't yet reached the target floor
            while (current != target) {
                current++; //increment the floor
                this.setCurrentFloor(current); //adjust internal variable

                //if the elevator needs to make a stop,
                if (buttons.contains(current) || (requests.hasKey(current)
                        && requests.value(current).contains(Direction.UP))) {
                    //make a stop at the current floor
                    this.stop(this.getDirection());
                }
            }
        } else {
            this.setDirection(Direction.DOWN);
            //while we haven't yet reached the target floor
            while (current != target) {
                current--; //decrement the floor
                this.setCurrentFloor(current); //adjust internal variable

                //if the elevator needs to make a stop
                if (buttons.contains(current) || (requests.hasKey(current)
                        && requests.value(current).contains(Direction.DOWN))) {
                    //make a stop at the current floor
                    this.stop(this.getDirection());
                }
            }
        }
        //now, we've reached our target floor
        this.stop(Direction.UP);
        this.stop(Direction.DOWN);
    }

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
    @Override
    public void stop(Direction d) {
        int floor = this.getCurrentFloor();
        Set<Integer> buttons = this.getButtonsPressed();
        Map<Integer, Set<Direction>> requests = this.getRequests();

        //if buttons contains floor, remove it
        if (buttons.contains(floor)) {
            this.unpressButton(floor);
        }
        //if requests contains floor and its Set<Direction> contains d,
        if (requests.hasKey(floor) && requests.value(floor).contains(d)) {
            //remove d from the Set<Direction>
            this.unmakeRequest(floor, d);
        }
    }

    /**
     * Finds the max value in a set where the max value can be no greater than
     * {@code topFloor}.
     *
     * @param s
     *            the set of which to search
     * @requires s /= empty_set
     * @return the max value in the set
     */
    private int findMaxSet(Set<Integer> s) {
        assert s.size() > 0 : "ERROR: set cannot be empty";

        int max = this.getTopFloor();
        while (!s.contains(max) && max > 0) {
            max--;
        }
        return max;
    }

    /**
     * Finds the min value in a set where the min value can be no less than 1.
     *
     * @param s
     *            the set of which to search
     * @requires s /= empty_set
     * @return the min value in the set
     */
    private int findMinSet(Set<Integer> s) {
        assert s.size() > 0 : "ERROR: set cannot be empty";

        int min = 1;
        while (!s.contains(min) && min <= this.getTopFloor()) {
            min++;
        }
        return min;
    }

    /**
     * Finds the max key in a Map where the max key can be no greater than
     * TOP_FLOOR.
     *
     * @param m
     *            the map to search
     * @requires m /= empty_map
     * @return the max key in the map
     */
    private int findMaxMap(Map<Integer, Set<Direction>> m) {
        assert m.size() > 0 : "ERROR: map cannot be empty";

        int max = this.getTopFloor();
        while (!m.hasKey(max) && max > 0) {
            max--;
        }
        return max;
    }

    /**
     * Finds the min key in a Map where the min key can be no less than 1.
     *
     * @param m
     *            the map to search
     * @requires m /= empty_map
     * @return the min key in the map
     */
    private int findMinMap(Map<Integer, Set<Direction>> m) {
        assert m.size() > 0 : "ERROR: map cannot be empty";

        int min = 1;
        while (!m.hasKey(min) && min <= this.getTopFloor()) {
            min++;
        }
        return min;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        //if this and obj are aliases, result is true
        if (this == obj) {
            result = true;
        } else if (obj == null || !(obj instanceof Elevator)) {
            //if obj is null or obj isn't an instance of Elevator, result is false
            result = false;
        } else {
            //cast obj as an Elevator
            Elevator other = (Elevator) obj;
            //compare all the internal variables
            result = (this.getCurrentFloor() == other.getCurrentFloor())
                    && (this.getDirection() == other.getDirection())
                    && (this.getButtonsPressed()
                            .equals(other.getButtonsPressed()))
                    && (this.getRequests().equals(other.getRequests())
                            && (this.getTopFloor() == other.getTopFloor()));
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Floor: ").append(this.getCurrentFloor());
        sb.append("\n");
        sb.append("Direction Travelling: ").append(this.getDirection());
        sb.append("\n");
        sb.append("Buttons Pressed Inside: ").append(this.getButtonsPressed());
        sb.append("\n");
        sb.append("Requests: ").append(this.getRequests());
        sb.append("\n");
        sb.append("Top Floor: ").append(this.getTopFloor());
        return sb.toString();
    }
}