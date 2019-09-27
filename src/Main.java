import java.util.Set;

public class Main {


    public static void main(String[] args) {
	// TODO
        ProblemGrid problemGrid = new ProblemGrid();
        Set<State> states = problemGrid.getStates();
        for (State s : states) {
            problemGrid.computeBeliefStateForSingleState(s.getCoordinates(), problemGrid.getObservations(), problemGrid.getActions());
        }
    }
}
