import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;

public abstract class ElevatorKernelTest {

    /**
     * Constructor fixture to be implemented by the concrete test class.
     */
    protected abstract Elevator constructorTest();

    @Test
    public final void testDefaultConstructor() {
        Elevator x = this.constructorTest();

        //check all of the internal variables
        assertEquals(1, x.getCurrentFloor());
        assertEquals(Elevator.Direction.UP, x.getDirection());
        assertEquals(new Set1L<Integer>(), x.getButtonsPressed());
        assertEquals(new Map1L<Integer, Set<Elevator.Direction>>(),
                x.getRequests());
    }

    @Test
    public final void testCurrentFloor3() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(3);

        assertEquals(3, x.getCurrentFloor());
    }

    @Test
    public final void testCurrentFloor9() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(9);

        assertEquals(9, x.getCurrentFloor());
    }

    @Test
    public final void testCurrentFloorTopFloor() {
        Elevator x = this.constructorTest();
        x.setCurrentFloor(x.getTopFloor());

        assertEquals(x.getTopFloor(), x.getCurrentFloor());
    }

    @Test
    public final void testCurrentFloorTwice() {
        Elevator x = this.constructorTest();

        //set current floor, check it
        x.setCurrentFloor(8);
        assertEquals(8, x.getCurrentFloor());

        //set current floor to something different, check again
        x.setCurrentFloor(7);
        assertEquals(7, x.getCurrentFloor());
    }

    @Test
    public final void testDirectionUp() {
        Elevator x = this.constructorTest();
        x.setDirection(Elevator.Direction.UP);

        assertEquals(Elevator.Direction.UP, x.getDirection());
    }

    @Test
    public final void testDirectionDown() {
        Elevator x = this.constructorTest();
        x.setDirection(Elevator.Direction.DOWN);

        assertEquals(Elevator.Direction.DOWN, x.getDirection());
    }

    @Test
    public final void testDirectionTwice() {
        Elevator x = this.constructorTest();

        //set direction to down, check it
        x.setDirection(Elevator.Direction.DOWN);
        assertEquals(Elevator.Direction.DOWN, x.getDirection());

        //set direction to up, check again
        x.setDirection(Elevator.Direction.UP);
        assertEquals(Elevator.Direction.UP, x.getDirection());
    }

    @Test
    public final void testPressButton2() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L();
        expected.add(2);

        x.pressButton(2);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testPressButtonTopFloor() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L();
        expected.add(x.getTopFloor());

        x.pressButton(x.getTopFloor());

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testPressButtonTwice() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L();
        expected.add(4);
        expected.add(5);

        x.pressButton(4);
        x.pressButton(5);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testPressButtonManyTimes() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L();
        expected.add(2);
        expected.add(4);
        expected.add(6);
        expected.add(8);

        x.pressButton(8);
        x.pressButton(6);
        x.pressButton(4);
        x.pressButton(2);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testUnpressButtonSize1() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L(); //empty set

        x.pressButton(2);

        x.unpressButton(2);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testUnpressButtonSize2() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L(); //empty set

        x.pressButton(8);
        x.pressButton(9);

        x.unpressButton(8);
        x.unpressButton(9);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testUnpressButtonSize3() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L();
        expected.add(1);

        x.pressButton(1);
        x.pressButton(8);
        x.pressButton(9);

        x.unpressButton(8);
        x.unpressButton(9);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testPressAndUnpressButton() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L();
        expected.add(5);

        x.pressButton(5);

        x.unpressButton(5);

        x.pressButton(5);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testPressAndUnpressButtonAgain() {
        Elevator x = this.constructorTest();

        Set<Integer> expected = new Set1L();
        expected.add(1);
        expected.add(2);

        x.pressButton(1);
        x.pressButton(3);

        x.unpressButton(3);

        x.pressButton(2);

        assertEquals(expected, x.getButtonsPressed());
    }

    @Test
    public final void testMakeRequest1() {
        Elevator x = this.constructorTest();

        Map<Integer, Set<Elevator.Direction>> expected = new Map1L();
        Set<Elevator.Direction> directionSet = new Set1L();

        directionSet.add(Elevator.Direction.UP);
        expected.add(8, directionSet);

        x.makeRequest(8, Elevator.Direction.UP);

        assertEquals(expected, x.getRequests());
    }

    @Test
    public final void testMakeRequest2() {
        Elevator x = this.constructorTest();

        Map<Integer, Set<Elevator.Direction>> expected = new Map1L();
        Set<Elevator.Direction> directionSet1 = new Set1L();
        Set<Elevator.Direction> directionSet2 = new Set1L();

        directionSet1.add(Elevator.Direction.UP);
        expected.add(8, directionSet1);

        directionSet2.add(Elevator.Direction.DOWN);
        expected.add(9, directionSet2);

        x.makeRequest(8, Elevator.Direction.UP);
        x.makeRequest(9, Elevator.Direction.DOWN);

        assertEquals(expected, x.getRequests());
    }

    @Test
    public final void testMakeRequest3() {
        Elevator x = this.constructorTest();

        Map<Integer, Set<Elevator.Direction>> expected = new Map1L();
        Set<Elevator.Direction> directionSet = new Set1L();

        directionSet.add(Elevator.Direction.UP);
        directionSet.add(Elevator.Direction.DOWN);
        expected.add(2, directionSet);

        x.makeRequest(2, Elevator.Direction.UP);
        x.makeRequest(2, Elevator.Direction.DOWN);

        assertEquals(expected, x.getRequests());
    }

    @Test
    public final void testUnmakeRequest1() {
        Elevator x = this.constructorTest();

        Map<Integer, Set<Elevator.Direction>> expected = new Map1L();
        Set<Elevator.Direction> directionSet = new Set1L();

        directionSet.add(Elevator.Direction.UP);
        expected.add(9, directionSet);

        x.makeRequest(9, Elevator.Direction.UP);
        x.makeRequest(9, Elevator.Direction.DOWN);
        x.unmakeRequest(9, Elevator.Direction.DOWN);

        assertEquals(expected, x.getRequests());
    }

    @Test
    public final void testUnmakeRequestAgain() {
        Elevator x = this.constructorTest();

        Map<Integer, Set<Elevator.Direction>> expected = new Map1L();
        Set<Elevator.Direction> directionSet = new Set1L();

        x.makeRequest(5, Elevator.Direction.UP);
        x.unmakeRequest(5, Elevator.Direction.UP);

        assertEquals(expected, x.getRequests());
    }

    // Add other kernel method tests here (pressButton, requestElevator, etc.)
}
