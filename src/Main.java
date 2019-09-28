import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Main {


    public static void main(String[] args) {
	// TODO
        ProblemGrid problemGrid = new ProblemGrid();
        Set<State> states = problemGrid.getStates();
        List<Double> unNormalizedBeliefState = new ArrayList<Double>();
        for (State s : states) {
            double result = problemGrid.computeBeliefStateForSingleState(s.getCoordinates(), problemGrid.getObservations(), problemGrid.getActions());
            // System.out.println("b(" + s.getCoordinates()+ ")" + " = " + result);
            unNormalizedBeliefState.add(result);
        }
        double sum = 0;
        for (Double d : unNormalizedBeliefState) {
            sum = sum + d;
        }
        double alpha = 1/sum;

        List<Double> normalizedBeliefState = new ArrayList<>();
        for (Double d : unNormalizedBeliefState) {
            normalizedBeliefState.add(d*alpha);
        }
    }
}
