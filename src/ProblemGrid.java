import javax.swing.*;
import java.util.*;

public class ProblemGrid {
    private Set<State> states = new HashSet<>();
    private Stack<Observation> observations = new Stack<>();
    private Stack<String> actions = new Stack<>();

    // constructs the grid and adds all states
    public ProblemGrid() {
        int[] state1Coord = new int[]{1,1};
        int[] state2Coord = new int[]{2,1};
        int[] state3Coord = new int[]{3,1};
        int[] state4Coord = new int[]{4,1};
        int[] state5Coord = new int[]{1,2};
        int[] state6Coord = new int[]{3,2};
        int[] state7Coord = new int[]{4,2};
        int[] state8Coord = new int[]{1,3};
        int[] state9Coord = new int[]{2,3};
        int[] state10Coord = new int[]{3,3};
        int[] state11Coord = new int[]{4,3};

        addState(state1Coord,false, state2Coord, null, state5Coord, null );
        addState(state2Coord, false, state3Coord, state1Coord, null, null);
        addState(state3Coord, false, state4Coord, state2Coord, state6Coord, null);
        addState(state4Coord, false, null, state3Coord, state7Coord, null);
        addState(state5Coord, false, null, null, state8Coord, state1Coord);
        addState(state6Coord, false, state7Coord, null, state10Coord, state3Coord);
        addState(state7Coord, true, null, state6Coord, state11Coord, state4Coord);
        addState(state8Coord, false, state9Coord, null, null, state5Coord);
        addState(state9Coord, false, state10Coord, state8Coord, null, null);
        addState(state10Coord, false, state11Coord, state9Coord, null, state6Coord);
        addState(state11Coord, true, null, state10Coord, null, state7Coord);
    }

    // inserts a state into grid
    public void addState(int[] coordinates, boolean isTerminal, int[] rightNeighbor, int[] leftNeighbor, int[] upNeighbor, int[] downNeighbor) {
        State state = new State(coordinates, isTerminal);
        state.specifyRightNeighbor(rightNeighbor);
        state.specifyLeftNeighbor(leftNeighbor);
        state.specifyUpNeighbor(upNeighbor);
        state.specifyDownNeighbor(downNeighbor);
        states.add(state);
    }


    // checks to see if the first state is the right neighbor of the second state, etc.
    public boolean isRightNeighborOf(int[] possibleNeighbor, int[] stateCoordinates) {
        for (State s : states) {
            if (Arrays.equals(s.getCoordinates(), stateCoordinates)) {
                return (Arrays.equals(s.getRightNeighbor(), possibleNeighbor));
            }
        }
        return false;
    }

    public boolean isLeftNeighborOf(int[] possibleNeighbor, int[] stateCoordinates) {
        for (State s : states) {
            if (Arrays.equals(s.getCoordinates(), stateCoordinates)) {
                return (Arrays.equals(s.getLeftNeighbor(), possibleNeighbor));
            }
        }
        return false;
    }

    public boolean isUpNeighborOf(int[] possibleNeighbor, int[] stateCoordinates) {
        for (State s : states) {
            if (Arrays.equals(s.getCoordinates(), stateCoordinates)) {
                return (Arrays.equals(s.getUpNeighbor(), possibleNeighbor));
            }
        }
        return false;
    }

    public boolean isDownNeighborOf(int[] possibleNeighbor, int[] stateCoordinates) {
        for (State s : states) {
            if (Arrays.equals(s.getCoordinates(), stateCoordinates)) {
                return (Arrays.equals(s.getDownNeighbor(), possibleNeighbor));
            }
        }
        return false;
    }

    // checks to see if state has a right/left/up/down wall
    public boolean hasRightWall(int[] coordinates) {
        for (State s: states) {
            if (Arrays.equals(coordinates, s.getCoordinates())) {
                return (s.getRightNeighbor() == null);
            }
        }
        return false;
    }

    public boolean hasLeftWall(int[] coordinates) {
        for (State s: states) {
            if (Arrays.equals(coordinates, s.getCoordinates())) {
                return (s.getLeftNeighbor() == null);
            }
        }
        return false;
    }

    public boolean hasUpWall(int[] coordinates) {
        for (State s: states) {
            if (Arrays.equals(coordinates, s.getCoordinates())) {
                return (s.getUpNeighbor() == null);
            }
        }
        return false;
    }

    public boolean hasDownWall(int[] coordinates) {
        for (State s: states) {
            if (Arrays.equals(coordinates, s.getCoordinates())) {
                return (s.getDownNeighbor() == null);
            }
        }
        return false;
    }

    // returns the probability of ending up in curState, given a prevState and the action performed to get to curState
    // remember there are multiple ways to end up in the same state again by bouncing on walls
    public double getTransitionProbability(int[] prevState, int[] curState, String action) {
        double prob = 0;

        if (doCoordinatesBelongToTerminal(prevState)) { // if prevState is terminal, cannot exit to any other state
            if (!Arrays.equals(prevState, curState)) {
                prob = 0;
            } else if (Arrays.equals(prevState, curState)) { // if you stay in the same terminal state
                prob = 1;
            }
        } else if (action.equals("right")) {
            if (isRightNeighborOf(curState, prevState)) {
                prob = 0.8;
            } else if (isUpNeighborOf(curState, prevState) | isDownNeighborOf(curState, prevState)) {
                prob = 0.1;
            } else if (Arrays.equals(prevState, curState)) { // bounce on wall scenario
                if (hasRightWall(curState)) {
                    prob = prob + 0.8;
                }
                if (hasUpWall(curState)) {
                    prob = prob + 0.1;
                }
                if (hasDownWall(curState)) {
                    prob = prob + 0.1;
                }
            }
        } else if (action.equals("left")) {
            if (isLeftNeighborOf(curState, prevState)) {
                prob = 0.8;
            } else if (isUpNeighborOf(curState, prevState) | isDownNeighborOf(curState, prevState)) {
                prob = 0.1;
            } else if (Arrays.equals(prevState, curState)) { // bounce on wall scenario
                if (hasLeftWall(curState)) {
                    prob = prob + 0.8;
                }
                if (hasUpWall(curState)) {
                    prob = prob + 0.1;
                }
                if (hasDownWall(curState)) {
                    prob = prob + 0.1;
                }
            }
        } else if (action.equals("up")) {
            if (isUpNeighborOf(curState, prevState)) {
                prob = 0.8;
            } else if (isLeftNeighborOf(curState, prevState) | isRightNeighborOf(curState, prevState)) {
                prob = 0.1;
            } else if (Arrays.equals(prevState, curState)) { // bounce on wall scenario
                if (hasUpWall(curState)) {
                    prob = prob + 0.8;
                }
                if (hasLeftWall(curState)) {
                    prob = prob + 0.1;
                }
                if (hasRightWall(curState)) {
                    prob = prob + 0.1;
                }
            }
        } else if (action.equals("down")) {
            if (isDownNeighborOf(curState, prevState)) {
                prob = 0.8;
            } else if (isLeftNeighborOf(curState, prevState) | isRightNeighborOf(curState, prevState)) {
                prob = 0.1;
            } else if (Arrays.equals(prevState, curState)) { // bounce on wall scenario
                if (hasDownWall(curState)) {
                    prob = prob + 0.8;
                }
                if (hasLeftWall(curState)) {
                    prob = prob + 0.1;
                }
                if (hasRightWall(curState)) {
                    prob = prob + 0.1;
                }
            }
        }
        return  prob;
    }

    // gets the probability of an observation given the state
    public double getProbOfObservationGivenState(Observation e, int[] coordinates) {
        double prob = 0;
        for (State s : states) {
            if (Arrays.equals(s.getCoordinates(), coordinates)) {
                if (e.equals(Observation.ONE_WALL)) {
                    if (s.isTerminal()) {
                        prob = 0;
                    } else if (coordinates[0] == 3 ) {
                        prob = 0.9;
                    } else {
                        prob = 0.1;
                    }
                } else if (e.equals(Observation.TWO_WALLS)) {
                    if (s.isTerminal()) {
                        prob = 0;
                    } else if (coordinates[0] == 3) {
                        prob = 0.1;
                    } else {
                        prob = 0.9;
                    }
                } else if (e.equals(Observation.END)) {
                    if (s.isTerminal()) {
                        prob = 1;
                    } else {
                        prob = 0;
                    }
                }
            }
        }
        return prob;
    }

    // checks if state with these coordinates is a terminal state
    public boolean doCoordinatesBelongToTerminal(int[] coordinates) {
        for (State s : states) {
            if (Arrays.equals(s.getCoordinates(), coordinates)) {
                return (s.isTerminal());
            }
        }
        return false;
    }

    public State getState(int[] coordinates) {
        for (State s : states) {
            if (Arrays.equals(s.getCoordinates(), coordinates)) {
                return s;
            }
        }
        return null;
    }

    // computes the belief state 
    public ArrayList<Double> computeBeliefState(double[] initBeliefState, Stack<Observation> observations, Stack<String> actions, double curResult) {
        ArrayList<Double> unNormalizedBeliefState = new ArrayList<>();
        ArrayList<Double> normalizedBeliefState = new ArrayList<>();

        if (!actions.empty() && !observations.empty()) {
            for (State s : states) {
                Observation lastObservation = observations.pop();
                String lastAction = actions.pop();
                int[] stateCoordinates = s.getCoordinates();

                Set<int[]> possiblePrevStates = new HashSet<>();
                int[] rightNeighbor = getState(stateCoordinates).getRightNeighbor();
                int[] leftNeighbor = getState(stateCoordinates).getLeftNeighbor();
                int[] upNeighbor = getState(stateCoordinates).getUpNeighbor();
                int[] downNeighbor = getState(stateCoordinates).getDownNeighbor();

                possiblePrevStates.add(stateCoordinates);
                if (rightNeighbor != null) {
                    possiblePrevStates.add(rightNeighbor);
                }
                if (leftNeighbor != null) {
                    possiblePrevStates.add(leftNeighbor);
                }
                if (upNeighbor != null) {
                    possiblePrevStates.add(upNeighbor);
                }
                if (downNeighbor != null) {
                    possiblePrevStates.add(downNeighbor);
                }

                double probObservationGivenState = getProbOfObservationGivenState(lastObservation, stateCoordinates);

                double thisSum = curResult;
                for (int[] prev : possiblePrevStates) {
                    if (actions.empty()) {
                        thisSum = thisSum + getTransitionProbability(prev, stateCoordinates, lastAction) * initBeliefState[getIndexOfState(stateCoordinates)];
                    } else { // if action list not empty
                        thisSum = thisSum + getTransitionProbability(prev, stateCoordinates, lastAction) * computeBeliefState(initBeliefState, observations, actions, thisSum).get(getIndexOfState(stateCoordinates));
                    }
                }
                unNormalizedBeliefState.add(probObservationGivenState * thisSum);
            }
            double reciprocalOfAlpha = 0;
            for (double d : unNormalizedBeliefState) {
                reciprocalOfAlpha = reciprocalOfAlpha + d;
            }
            double alpha = 1/reciprocalOfAlpha;

            for (double d : unNormalizedBeliefState) {
                normalizedBeliefState.add(alpha * d); // normalize

            }
        }
        return normalizedBeliefState;
    }

    public int getIndexOfState(int[] coordinates) {
        if (Arrays.equals(coordinates, new int[] {1,1})) {
            return 0;
        } else if (Arrays.equals(coordinates, new int[] {2,1})) {
            return 1;
        } else if (Arrays.equals(coordinates, new int[] {3,1})) {
            return 2;
        } else if (Arrays.equals(coordinates, new int[] {4,1})) {
            return 3;
        } else if (Arrays.equals(coordinates, new int[] {1,2})) {
            return 4;
        } else if (Arrays.equals(coordinates, new int[] {3,2})) {
            return 5;
        } else if (Arrays.equals(coordinates, new int[] {4,2})) {
            return 6;
        } else if (Arrays.equals(coordinates, new int[] {1,3})) {
            return 7;
        } else if (Arrays.equals(coordinates, new int[] {2,3})) {
            return 8;
        } else if (Arrays.equals(coordinates, new int[] {3,3})) {
            return 9;
        } else if (Arrays.equals(coordinates, new int[] {4,3})) {
            return 10;
        }
        else return 0;
    }
}
