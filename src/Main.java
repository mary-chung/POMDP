import java.util.*;

public class Main {


    public static void main(String[] args) {
        ProblemGrid problemGrid = new ProblemGrid();
        // example 1
        Stack<Observation> ex1Observations = new Stack<Observation>();
        ex1Observations.push(Observation.TWO_WALLS);
        ex1Observations.push(Observation.TWO_WALLS);
        ex1Observations.push(Observation.TWO_WALLS);
        Stack<String> ex1Actions = new Stack<>();
        ex1Actions.push("up");
        ex1Actions.push("up");
        ex1Actions.push("up");
        ArrayList<Double> ex1Result = problemGrid.computeBeliefState(new double[] {1/11, 1/11, 1/11, 1/11, 1/11, 1/11, 1/11, 1/11, 1/11, 1/11, 1/11}, ex1Observations, ex1Actions, 0);
        System.out.println(ex1Result);
    }
}
