import java.awt.*;

public class State {
    private int[] coordinates;
    private int[] rightNeighbor; // neighbors are null if neighbor is wall
    private int[] leftNeighbor;
    private int[] upNeighbor;
    private int[] downNeighbor;
    private boolean isTerminal;

    // constructs a lone state, no neighbors specified yet
    // coordinates expected to be array size 2
    public State(int[] coordinates, boolean isTerminal) {
        this.coordinates = coordinates;
        this.isTerminal = isTerminal;
    }

    // these methods set the right/left/up/down neighbors
    public void specifyRightNeighbor(int[] rightNeighbor) {
        this.rightNeighbor = rightNeighbor;
    }

    public void specifyLeftNeighbor(int[] leftNeighbor) {
        this.leftNeighbor = leftNeighbor;
    }

    public void specifyUpNeighbor(int[] upNeighbor) {
        this.upNeighbor = upNeighbor;
    }

    public void specifyDownNeighbor(int[] downNeighbor) {
        this.downNeighbor = downNeighbor;
    }

    // returns coordinates of the state
    public int[] getCoordinates() {
        return coordinates;
    }

    // these methods get the coordinates of the right/left/up/down neighbors
    public int[] getRightNeighbor() {
        return rightNeighbor;
    }

    public int[] getLeftNeighbor() {
        return leftNeighbor;
    }

    public int[] getUpNeighbor() {
        return upNeighbor;
    }

    public int[] getDownNeighbor() {
        return downNeighbor;
    }

    // returns true if the state is a terminal state
    public boolean isTerminal() {
        return isTerminal;
    }


}
