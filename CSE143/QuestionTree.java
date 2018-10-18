/*
 * Brian Kang
 * 5/21/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #7: QuestionTree.java
 *
 * This class constructs the binary tree needed to play the
 * question guessing game with a user, reads the old tree from
 * a file if wanted for the game, and writes the result of
 * a tree to a file just in case that information maybe wanted
 * to used later.
 */

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionTree {
    private Scanner console;    // takes user input
    private QuestionNode overallRoot;   // the overall binary tree

    // post: constructs a question tree with one leaf node
    //       representing the object "computer" and a
    //       single console for user input
    public QuestionTree() {
        console = new Scanner(System.in);
        overallRoot = new QuestionNode("computer");
    }

    // Reads a text file in standard format and preorder to
    // replace the current tree with another tree from the file
    // parameters needed:
    //  Scanner input = reads data from a text file
    // pre: assume the file is legal and in standard format
    // post: current tree is replaced with a new binary tree
    public void read(Scanner input) {
        overallRoot = readPreorder(input);
    }

    // Reads a text file line by line in preorder to replace
    // the current tree with a new one from the file
    // parameters needed:
    //  Scanner input = reads data from a text file
    // pre: assume the file is legal and in standard format
    // post: current tree is replaced with a new binary tree
    private QuestionNode readPreorder(Scanner input) {
        String format = input.nextLine();
        String line = input.nextLine();
        QuestionNode root = new QuestionNode(line);
        if (format.equals("Q:")) {
            root.yes = readPreorder(input);
            root.no = readPreorder(input);
        }
        return root;
    }

    // Stores the current tree to an output file in standard
    // format and preorder
    // parameters needed:
    //  PrintStream output = print data in output file
    // post: tree is printed into a file in standard format
    //       and in preorder
    public void write(PrintStream output) {
        writePreorder(output, overallRoot);
    }

    // Stores the current tree to an output file in standard
    // format and preorder
    // parameters needed:
    //  PrintStream output = print data in output file
    //  QuestionNode root = the tree to be traversed through
    // post: tree is printed into a file in standard format
    //       and in preorder
    private void writePreorder(PrintStream output, QuestionNode root) {
        if (root.yes == null && root.no == null) {
            output.println("A:");
            output.println(root.data);
        } else {
            output.println("Q:");
            output.println(root.data);
            writePreorder(output, root.yes);
            writePreorder(output, root.no);
        }
    }

    // Uses current tree to ask user yes/no questions until
    // user guess the object correctly or until failure.
    // If failure to guess, grow the current tree to include
    // a new question and correct object to distinguish from
    // the incorrectly guessed answer
    // post: if correct object is guessed, keep the tree,
    //       if not, replace that answer leaf with a question
    //       node with two answer leafs that answer yes/no
    //       to the question
    public void askQuestions() {
        overallRoot = askQuestions(overallRoot);
    }

    // Uses current tree to ask user yes/no questions until
    // user guess the object correctly or until failure.
    // If failure to guess, grow the current tree to include
    // a new question and correct object to distinguish from
    // the incorrectly guessed answer
    // parameters needed:
    //  QuestionNode root = the tree to be traversed through
    // post: if correct object is guessed, keep the tree,
    //       if not, replace that answer leaf with a question
    //       node branching to two answer leafs that answer
    //       yes/no to the question
    private QuestionNode askQuestions(QuestionNode root) {
        if (root.yes == null && root.no == null) {
            if (yesTo("Would your object happen to be " + root.data + "?")) {
                System.out.println("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                String object = console.nextLine();
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String question = console.nextLine();
                if (yesTo("And what is the answer for your object?")) {
                    root = new QuestionNode(question, new QuestionNode(object), root);
                } else {
                    root = new QuestionNode(question, root, new QuestionNode(object));
                }
            }
        } else {
            if (yesTo(root.data)) {
                root.yes = askQuestions(root.yes);
            } else {
                root.no = askQuestions(root.no);
            }
        }
        return root;
    }

    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
