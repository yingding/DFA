package com.example.efa;

import com.example.efa.automat.Automat;

public class Main {

    public static void main(String[] args) {
        Automat automat = new Automat();
	    // input strings, string is implement with an char array
        String inputs = "010111";
        // get the transition definition printed
        System.out.println(automat.printTransitionDefinitions());
        // print the current automat state
        boolean accepted = automat.execute(inputs);
        System.out.println(String.format("Input Word %s is ", inputs) + (accepted ? "": "Not ") + "Accepted!" );

        Automat automat1 = new Automat();
        System.out.println("======================");
        inputs = "010141"; // no digits will be ignored by the execute() method, but 4 is not an valid alphabet
        accepted = automat1.execute(inputs);
        System.out.println(String.format("Input Word %s is ", inputs) + (accepted ? "": "Not ") + "Accepted!" );
        System.out.println("Automat1: " + automat1);
    }
}
