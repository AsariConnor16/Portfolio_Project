import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;

public abstract class ElevatorTest extends ElevatorKernelTest {

    /**
     * Test findNextFloor when elevator is on floor 5, travelling up, and no
     * buttons are pressed.
     */
    @Test
    public final void testFindNextFloorNoButtonsFloor5() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(5);

        int result = x.findNextFloor();

        assertEquals(5, result);
    }

    /**
     * Test findNextFloorArcher when elevator is on floor 5, travelling up, and
     * no buttons are pressed.
     */
    @Test
    public final void testFindNextFloorArcherNoButtonsFloor5() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(5);

        int result = x.findNextFloorArcher();

        assertEquals(1, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, and the
     * only button TOP_FLOOR is pressed.
     */
    @Test
    public final void testFindNextFloorOneButton() {
        Elevator x = this.constructorTest();

        x.pressButton(x.getTopFloor());

        int result = x.findNextFloor();

        assertEquals(x.getTopFloor(), result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, and
     * buttons 3 and 5 are pressed.
     */
    @Test
    public final void testFindNextFloor2Buttons() {
        Elevator x = this.constructorTest();

        x.pressButton(3);
        x.pressButton(5);

        int result = x.findNextFloor();

        assertEquals(5, result);
    }

    /**
     * Test findNextFloor when elevator is on floor TOP_FLOOR, travelling down,
     * and only button 1 is pressed.
     */
    @Test
    public final void testFindNextFloor1ButtonDown() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(x.getTopFloor());
        x.setDirection(Elevator.Direction.DOWN);

        x.pressButton(1);

        int result = x.findNextFloor();

        assertEquals(1, result);
    }

    /**
     * Test findNextFloor when elevator is on floor TOP_FLOOR, travelling down,
     * and buttons 2 and 4 are pressed.
     */
    @Test
    public final void testFindNextFloor2ButtonsDown() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(x.getTopFloor());
        x.setDirection(Elevator.Direction.DOWN);

        x.pressButton(2);
        x.pressButton(4);

        int result = x.findNextFloor();

        assertEquals(2, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, and only
     * button 1 is pressed.
     */
    @Test
    public final void testFindNextFloorCurrentUp() {
        Elevator x = this.constructorTest();

        x.pressButton(1);

        int result = x.findNextFloor();

        assertEquals(1, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling down, and only
     * button 1 is pressed.
     */
    @Test
    public final void testFindNextFloorCurrentDown() {
        Elevator x = this.constructorTest();
        x.setDirection(Elevator.Direction.DOWN);

        x.pressButton(1);

        int result = x.findNextFloor();

        assertEquals(1, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 2, travelling up, and
     * buttons 1 and TOP_FLOOR are pressed.
     */
    @Test
    public final void testFindNextFloorSplitMiddleUp() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(2);

        x.pressButton(1);
        x.pressButton(x.getTopFloor());

        int result = x.findNextFloor();

        assertEquals(x.getTopFloor(), result);
    }

    /**
     * Test findNextFloor when elevator is on floor 8, travelling down, and
     * buttons 1 and TOP_FLOOR are pressed.
     */
    @Test
    public final void testFindNextFloorSplitMiddleDown() {
        Elevator x = this.constructorTest();
        x.setDirection(Elevator.Direction.DOWN);
        x.setCurrentFloor(8);

        x.pressButton(1);
        x.pressButton(x.getTopFloor());

        int result = x.findNextFloor();

        assertEquals(1, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 5, travelling up, and
     * buttons 1 and 2 are pressed.
     */
    @Test
    public final void testFindNextFloorRecursiveDown() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(5);

        x.pressButton(1);
        x.pressButton(2);

        int result = x.findNextFloor();

        assertEquals(1, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 5, travelling down, and
     * buttons 8 and 9 are pressed.
     */
    @Test
    public final void testFindNextFloorRecursiveUp() {
        Elevator x = this.constructorTest();
        x.setDirection(Elevator.Direction.DOWN);
        x.setCurrentFloor(5);

        x.pressButton(8);
        x.pressButton(9);

        int result = x.findNextFloor();

        assertEquals(9, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, and only
     * floor 10,down is requested.
     */
    @Test
    public final void testFindNextFloor1RequestUp() {
        Elevator x = this.constructorTest();

        x.makeRequest(9, Elevator.Direction.DOWN);

        int result = x.findNextFloor();

        assertEquals(9, result);
    }

    /**
     * Test findNextFloor when elevator is on floor TOP_FLOOR, travelling up,
     * and only floor 1,up is requested.
     */
    @Test
    public final void testFindNextFloor1RequestDown() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(x.getTopFloor());

        x.makeRequest(1, Elevator.Direction.UP);

        int result = x.findNextFloor();

        assertEquals(1, result);
        assertEquals(Elevator.Direction.DOWN, x.getDirection());
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, and 6,up
     * and 7,up are requested.
     */
    @Test
    public final void testFindNextFloor2RequestedBothUp() {
        Elevator x = this.constructorTest();

        x.makeRequest(6, Elevator.Direction.UP);
        x.makeRequest(7, Elevator.Direction.UP);

        int result = x.findNextFloor();

        assertEquals(7, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, and 6,down
     * and 7,down are requested.
     */
    @Test
    public final void testFindNextFloor2RequestedBothDown() {
        Elevator x = this.constructorTest();

        x.makeRequest(6, Elevator.Direction.DOWN);
        x.makeRequest(7, Elevator.Direction.DOWN);

        int result = x.findNextFloor();

        assertEquals(7, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, and 6,down
     * and 7,up are requested.
     */
    @Test
    public final void testFindNextFloor2RequestedOppositeDirection() {
        Elevator x = this.constructorTest();

        x.makeRequest(6, Elevator.Direction.DOWN);
        x.makeRequest(7, Elevator.Direction.UP);

        int result = x.findNextFloor();

        assertEquals(7, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 5, travelling up, and 8,down
     * and 2,up are requested.
     */
    @Test
    public final void testFindNextFloorSplitMiddleDirectionUp() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(5);

        x.makeRequest(8, Elevator.Direction.DOWN);
        x.makeRequest(2, Elevator.Direction.UP);

        int result = x.findNextFloor();

        assertEquals(8, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 5, travelling down, and
     * 8,down and 2,up are requested.
     */
    @Test
    public final void testFindNextFloorSplitMiddleDirectionDown() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(5);
        x.setDirection(Elevator.Direction.DOWN);

        x.makeRequest(8, Elevator.Direction.DOWN);
        x.makeRequest(2, Elevator.Direction.UP);

        int result = x.findNextFloor();

        assertEquals(2, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 9, travelling down, and 3,up
     * and 2,down are requested.
     */
    @Test
    public final void testFindNextFloorPassMiddleFloor() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(2);
        x.setDirection(Elevator.Direction.DOWN);

        x.makeRequest(3, Elevator.Direction.UP);
        x.makeRequest(2, Elevator.Direction.DOWN);

        int result = x.findNextFloor();

        assertEquals(2, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 1, travelling up, button 7
     * is pressed and 9,down is requested.
     */
    @Test
    public final void testFindNextFloor1Button1Request() {
        Elevator x = this.constructorTest();

        x.pressButton(7);
        x.makeRequest(9, Elevator.Direction.DOWN);

        int result = x.findNextFloor();

        assertEquals(9, result);
    }

    /**
     * Test findNextFloor when elevator is on floor 4, travelling up, buttons 2
     * and 3 are pressed and 1,up is requested.
     */
    @Test
    public final void testFindNextFloor1Button1RequestRecrusive() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(4);

        x.pressButton(2);
        x.pressButton(3);
        x.makeRequest(1, Elevator.Direction.UP);

        int result = x.findNextFloor();

        assertEquals(1, result);
    }

    /**
     * Test goToFloor target=4 when elevator is on floor 1, travelling up, no
     * buttons are pressed and no requests are made.
     */
    @Test
    public final void testGoToFloorNoButtonsNoRequests() {
        Elevator x = this.constructorTest();
        Set<Integer> expectedButtons = new Set1L();
        Map<Integer, Set<Elevator.Direction>> expectedRequests = new Map1L();

        x.goToNextFloor(4);

        assertEquals(4, x.getCurrentFloor());
        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());
    }

    /**
     * Test goToFloor target=8 when elevator is on floor 1, travelling up,
     * buttons 4 and 5 are pressed, and 6,up is requested.
     */
    @Test
    public final void testGoToFloor2Buttons1RequestSameDirection() {
        Elevator x = this.constructorTest();

        Set<Integer> expectedButtons = new Set1L();
        Map<Integer, Set<Elevator.Direction>> expectedRequests = new Map1L();

        x.pressButton(4);
        x.pressButton(5);
        x.makeRequest(6, Elevator.Direction.UP);

        x.goToNextFloor(8);

        assertEquals(8, x.getCurrentFloor());
        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());
    }

    /**
     * Test goToFloor target=3 when elevator is on floor 8, travelling down,
     * buttons 1 and 4 are pressed, and 6,up is requested.
     */
    @Test
    public final void testGoToFloor2Buttons1RequestOppositeDirection() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(8);
        x.setDirection(Elevator.Direction.DOWN);

        Set<Integer> expectedButtons = new Set1L();
        expectedButtons.add(1);
        Map<Integer, Set<Elevator.Direction>> expectedRequests = new Map1L();
        Set<Elevator.Direction> directionSet = new Set1L();
        directionSet.add(Elevator.Direction.UP);
        expectedRequests.add(6, directionSet);

        x.pressButton(1);
        x.pressButton(4);
        x.makeRequest(6, Elevator.Direction.UP);

        x.goToNextFloor(3);

        assertEquals(3, x.getCurrentFloor());
        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());
    }

    /**
     * Test stop when elevator is on floor 1, travelling up, no buttons are
     * pressed and no requests are made.
     */
    @Test
    public final void testStopNoButtonsNoRequests() {
        Elevator x = this.constructorTest();

        Set<Integer> expectedButtons = new Set1L();
        Map<Integer, Set<Elevator.Direction>> expectedRequests = new Map1L();

        x.stop(Elevator.Direction.UP);

        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());
    }

    /**
     * Test stop when elevator is on floor 5, travelling up, button 5 is
     * pressed, and 6,up is requested.
     */
    @Test
    public final void testStop1Button1Request() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(5);

        Set<Integer> expectedButtons = new Set1L();
        Map<Integer, Set<Elevator.Direction>> expectedRequests = new Map1L();
        Set<Elevator.Direction> directionSet = new Set1L();
        directionSet.add(Elevator.Direction.UP);
        expectedRequests.add(6, directionSet);

        x.pressButton(5);
        x.makeRequest(6, Elevator.Direction.UP);
        x.stop(Elevator.Direction.UP);

        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());
    }

    /**
     * Test stop when elevator is on floor 8, travelling down, button 5 is
     * pressed, and 8,down is requested.
     */
    @Test
    public final void testStop1Button1RequestAgain() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(8);
        x.setDirection(Elevator.Direction.DOWN);

        Set<Integer> expectedButtons = new Set1L();
        expectedButtons.add(5);
        Map<Integer, Set<Elevator.Direction>> expectedRequests = new Map1L();

        x.pressButton(5);
        x.makeRequest(8, Elevator.Direction.DOWN);
        x.stop(Elevator.Direction.DOWN);

        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());
    }

    /**
     * Test full implementation when elevator is on floor 1, travelling up.
     * Buttons 5 and 7 are pressed. The next floor is found (and checked). Then,
     * the elevator goes to nextFloor, currentFloor and buttonsPressed are
     * checked.
     */
    @Test
    public final void fullTest1() {
        Elevator x = this.constructorTest();

        Set<Integer> expectedButtons = new Set1L();

        x.pressButton(5);
        x.pressButton(7);

        int nextFloor = x.findNextFloor();
        assertEquals(7, nextFloor);

        x.goToNextFloor(nextFloor);
        assertEquals(nextFloor, x.getCurrentFloor());
        assertEquals(expectedButtons, x.getButtonsPressed());
    }

    /**
     * Test full implementation when elevator is on floor 1, travelling up.
     * Button 7 is pressed, and (2,down), (9,down) are requested. The next floor
     * is found (and checked). Then, the elevator goes to nextFloor,
     * currentFloor, buttonsPressed, and requests are checked (buttonsPressed
     * should be empty and requests should only have (2,down)). Then, the next
     * floor is found again (and checked again). The el
     */
    @Test
    public final void fullTest2() {
        Elevator x = this.constructorTest();

        Set<Integer> expectedButtons = new Set1L();

        Map<Integer, Set<Elevator.Direction>> expectedRequests = new Map1L();
        Set<Elevator.Direction> directionSet = new Set1L();
        directionSet.add(Elevator.Direction.DOWN);
        expectedRequests.add(2, directionSet);

        x.makeRequest(2, Elevator.Direction.DOWN);
        x.pressButton(7);
        x.makeRequest(9, Elevator.Direction.DOWN);

        int nextFloor = x.findNextFloor();
        assertEquals(9, nextFloor);

        x.goToNextFloor(nextFloor);
        assertEquals(nextFloor, x.getCurrentFloor());
        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());

        int nextFloorAgain = x.findNextFloor();
        assertEquals(2, nextFloorAgain);

        expectedRequests = new Map1L();
        x.goToNextFloor(nextFloorAgain);
        assertEquals(nextFloorAgain, x.getCurrentFloor());
        assertEquals(expectedButtons, x.getButtonsPressed());
        assertEquals(expectedRequests, x.getRequests());
    }
}