/*
 * Brian Kang
 * 2/15/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #6: MadLibs.java
 *
 * This program will present three choices to user in a menu:
 * create a new mad lib, view an existing mad lib or quit.
 * A mad lib is a short story with blanks called placeholders
 * for the user to fill in.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MadLibs {
    public static void main(String[] args)
            throws FileNotFoundException{
        Scanner keyboard = new Scanner(System.in);
        intro();
        menuBar(keyboard);
    }

    //Prints a welcoming introduction for the user.
    public static void intro() {
        System.out.println("Welcome to the game of Mad Libs.");
        System.out.println("I will ask you to provide various words");
        System.out.println("and phrases to fill in a story.");
        System.out.println("The result will be written to an output file.");
        System.out.println();
    }

    //Presents the menu bar to create, view, or quit.
    //User can repeat as many times as desired.
    //parameters needed:
    // keyboard = asks for inputs
    public static void menuBar(Scanner keyboard)
            throws FileNotFoundException{
        System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");
        String menu = keyboard.nextLine();
        //true as long as not Q
        while (!menu.equalsIgnoreCase("q")) {
            if (menu.equalsIgnoreCase("c") || menu.equalsIgnoreCase("v")) {
                inputFileExists(keyboard, menu);
            }
            // reprompt question
            System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");
            menu = keyboard.nextLine();
        }
    }

    //If the input file the user is looking for is existent
    //either create or view mad lib depending on menu choice.
    //parameters needed:
    // keyboard = asks for inputs
    // menu = the choice the user selected
    public static void inputFileExists(Scanner keyboard, String menu)
            throws FileNotFoundException{
        System.out.print("Input file name: ");
        String fileName = keyboard.nextLine();
        File file = new File(fileName);
        //reprompt for existing file name
        while (!file.exists()) {
            System.out.print("File not found. Try again: ");
            fileName = keyboard.nextLine();
            file = new File(fileName);
        }
        Scanner input = new Scanner(file);
        if (menu.equalsIgnoreCase("c")) {
            createMadlib(keyboard, input);
            System.out.println();
        } else if (menu.equalsIgnoreCase("v")) {
            System.out.println();
            viewMadlib(input);
            System.out.println();
        }
    }

    //Creates a new or overwrites an existing mad lib
    //the user made. User will be able to
    //enter words into the placeholders. But will not
    //be able to view the mad lib.
    //parameters needed:
    // keyboard = asks for inputs
    // input = reads the info within a file
    public static void createMadlib(Scanner keyboard, Scanner input)
            throws FileNotFoundException{
        System.out.print("Output file name: ");
        String fileName = keyboard.nextLine();
        System.out.println();
        PrintStream output = new PrintStream(new File(fileName));
        while (input.hasNextLine()) {
            //line by line
            String line = input.nextLine();
            //token by token on the line
            Scanner lineScan = new Scanner(line);
            String fullLine = "";
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                //works with placeholder
                if (token.startsWith("<") && token.endsWith(">")) {
                    //extracts the placeholder inside the < > and modifies it
                    String placeholder = token.substring(token.indexOf("<") + 1,
                            token.indexOf(">"));
                    placeholder = placeholder.replace("-", " ");
                    //grammar between "a" and "an"
                    if (placeholder.substring(0, 1).equalsIgnoreCase("a") ||
                            placeholder.substring(0, 1).equalsIgnoreCase("e") ||
                            placeholder.substring(0, 1).equalsIgnoreCase("i") ||
                            placeholder.substring(0, 1).equalsIgnoreCase("o") ||
                            placeholder.substring(0, 1).equalsIgnoreCase("u")) {
                        System.out.print("Please type an " + placeholder + ": ");
                    } else {
                        System.out.print("Please type a " + placeholder + ": ");
                    }
                    //placeholder is replaced with an user input
                    token = keyboard.nextLine();
                }
                fullLine += token + " ";
            }
            output.println(fullLine);
        }
        System.out.println("Your mad-lib has been created!");
    }

    public static String editPlaceholder(Scanner lineScan, Scanner keyboard, String fullLine) {
        while (lineScan.hasNext()) {
            String token = lineScan.next();
            //works with placeholder
            if (token.startsWith("<") && token.endsWith(">")) {
                //extracts the placeholder inside the < > and modifies it
                String placeholder = token.substring(token.indexOf("<") + 1,
                        token.indexOf(">"));
                placeholder = placeholder.replace("-", " ");
                //grammar between "a" and "an"
                if (placeholder.substring(0, 1).equalsIgnoreCase("a") ||
                        placeholder.substring(0, 1).equalsIgnoreCase("e") ||
                        placeholder.substring(0, 1).equalsIgnoreCase("i") ||
                        placeholder.substring(0, 1).equalsIgnoreCase("o") ||
                        placeholder.substring(0, 1).equalsIgnoreCase("u")) {
                    System.out.print("Please type an " + placeholder + ": ");
                } else {
                    System.out.print("Please type a " + placeholder + ": ");
                }
                //placeholder is replaced with an user input
                token = keyboard.nextLine();
            }
            fullLine += token + " ";
        }
        return fullLine;
    }

    //User can view the mad lib file.
    //parameters needed:
    // input = reads the info within a file
    public static void viewMadlib(Scanner input) {
        // scans and prints all lines by line
        while(input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println(line);
        }
    }
}