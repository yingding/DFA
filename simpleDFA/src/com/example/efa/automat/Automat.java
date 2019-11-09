package com.example.efa.automat;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * This is an example DFA automaton
 *
 * DFA State | input: 0        | input: 1
 * q0(start) | q0, output:1    | q1, output: 0
 * q1        | q1, output:0    | q2, output: 1
 * q2(stop)  | q0, output:1    | q2, output: 0
 */
public class Automat {
    private State state;

    public Automat() {
        this.state = Automat.startState();
    }

    // Q, a finit set of states
    public enum StateTypes {
        q0, // start state
        q1,
        q2
    }

    // Sigma: Σ
    public static class Alphabet {
        public static final int one = 1;
        public static final int zero = 0;
        public static final int error = -1;
    }

    /**
     * single start state: q0
     * @return
     */
    public static State startState() {
        return new State(StateTypes.q0);
    }

    // accept state F (end states)
    public static class EndStates {
        // https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction/37406054#37406054
        private static final Set<State> endStates = Collections.singleton(new State(StateTypes.q2));
        public static boolean isEndState(State state) {
            return endStates.contains(state);
        }
    }

    public static class State {
        StateTypes stateType;

        public State(StateTypes type) {
            this.stateType = type;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("state: ").append(this.stateType);
            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return stateType == state.stateType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(stateType);
        }
    }

    /**
     * using input method to implemnt the transition functions (Delta δ)
     *
     * DFA State | input: 0        | input: 1
     * q0(start) | q0, output:1    | q1, output: 0
     * q1        | q1, output:0    | q2, output: 1
     * q2(stop)  | q0, output:1    | q2, output: 0
     *
     * @param input
     * @return
     */
    public int transit(int input) {
        int output = 0;

        switch (this.state.stateType) {
            case q0:
                if (input == Alphabet.zero) {
                    // this.stateType = StateType.q0;
                    output = Alphabet.one;
                } else if(input == Alphabet.one) {
                    // input == 1
                    this.state.stateType = StateTypes.q1;
                    output = 0;
                } else {
                    output = Alphabet.error;
                }
                break;
            case q1:
                if (input == Alphabet.zero) {
                    // this.stateType = StateType.q1;
                    output = Alphabet.zero;
                } else if (input == Alphabet.one ){ // input == 1
                    this.state.stateType = StateTypes.q2;
                    output = Alphabet.one;
                } else {
                    output = Alphabet.error;
                }
                break;
            case q2:
                if (input == Alphabet.zero) {
                    this.state.stateType = StateTypes.q0;
                    output = Alphabet.one;
                } else if (input == Alphabet.one) { // input == 1
                    // this.stateType = StateType.q2
                    output = Alphabet.zero;
                } else {
                    output = Alphabet.error;
                }
                break;
            default:
                output = Alphabet.error;
                break;
        }
        return output;
    }

    @Override
    public String toString() {
        return this.state.toString();
    }

    public boolean isInEndState() {
        return EndStates.isEndState(this.state);
    }

    public String printTransitionDefinitions() {
         StringBuilder builder = new StringBuilder();
         builder.append("DFA State | input: 0        | input: 1").append("\n")
                 .append("q0(start) | q0, output:1    | q1, output: 0").append("\n")
                 .append("q1        | q1, output:0    | q2, output: 1").append("\n")
                 .append("q2(stop)  | q0, output:1    | q2, output: 0").append("\n");
         return builder.toString();
    }

    public boolean execute(String inputs) {
        System.out.println("Start state: " + this);
        int input, output;
        for (int i = 0; i < inputs.length(); i++) {
            if (Character.isDigit(inputs.charAt(i))) {
                // since the digits are continus, we can just substract char 0 to the the integer difference
                input = inputs.charAt(i) - '0';
                System.out.println("----------------");
                System.out.println("input:" + input);
                output = this.transit(input);
                if (output == Alphabet.error) {
                    // reset to start state or to error state
                    System.out.println(String.format("Input %d is not a valid alphabet!", input));
                    this.state = startState();
                    return false;
                }
                System.out.println("output: " + output);
                System.out.println("State after transition: " + this);
            } else {
                System.out.println("Input is ignored, not a digit!");
            }
        }
        return this.isInEndState();
    }

}
