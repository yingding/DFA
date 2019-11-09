package com.example.efa;

import com.example.efa.automat.Automat;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // get the transition definition printed
        System.out.println(Automat.printTransitionDefinitions());

        String[] inputsArray = {"010111", "0101101", "010141", "1b111"};
        List<Automat> automatRuns = new ArrayList<Automat>();
        boolean accepted;
        for (String inputs : inputsArray) {
            // input strings, string is implement with an char array
            Automat automat = new Automat();
            System.out.println("======================");
            System.out.println(String.format("Run %s", inputs));
            System.out.println("======================");
            // execute the current automat with input sequences
            accepted = automat.execute(inputs);
            System.out.println(String.format("\nInput Word %s is ", inputs) + (accepted ? "": "Not ") + "Accepted!\n" );
            // cache the current run of automat for later output
            automatRuns.add(automat);
        }

        System.out.println("\n######################");
        System.out.println("States of all automatons:");
        int idx = 0;
        for (Automat automat : automatRuns) {
            System.out.println(String.format("Automat%d: ",idx) + automat);
            idx++;
        }
    }
}
