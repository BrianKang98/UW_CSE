/*
 * Brian Kang
 * 2/3/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #5: GuessingGame.java
 *
 * This program will greet and allow the user to play a random integer
 * guessing game that continues until the user guesses the right
 * number. The user will be able to play until one wants to quit.
 * Statistics from the game played will be displayed for the user.
 */

import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    public static final int MAXNUMBER = 100;    //change guessing boundary

    public static void main(String[] args) {
        Random rand = new Random();
        Scanner kb = new Scanner(System.in);
        System.out.println("Hello my good friend,");
        System.out.println("Be boundless and fail forward");
        System.out.println("And struggle through this.");
        System.out.println();
        
        //the first round of game played
        int totalGuesses = 0;
        //adds the first number of guesses
        totalGuesses += playGame(rand, kb);
        //the first guess is the first best yet
        int bestGuesses = totalGuesses;
        System.out.print("Do you want to play again? ");
        String reply = kb.next();
        System.out.println();
        
        
        int totalGames = 1;
        //true when reply starts with Y or y and plays game
        while (reply.substring(0, 1).equalsIgnoreCase("Y")) {
            //sums the number of total games
            totalGames++;
            int value = playGame(rand, kb);
            //updates to the least number guesses in a game round
            bestGuesses = Math.min(bestGuesses, value);
            //updates the total number of guesses
            totalGuesses += value;
            System.out.print("Do you want to play again? ");
            reply = kb.next();
            System.out.println();
        }
        gameStats(totalGames, totalGuesses, bestGuesses);
    }

    //Allows the user to play one game.
    //User enters the guessed number until correct and will
    //receive hints to approach answer.
    //returns the number of attempts it took to get the answer
    //parameters needed:
    // rand = generates a random number within boundary
    // kb = asks for inputs
    public static int playGame(Random rand, Scanner kb) {
        System.out.println("I'm thinking of a number between 1 and " +
                MAXNUMBER + "...");
        //boundary is 1 - MAXNUMBER inclusive
        int answer = rand.nextInt(MAXNUMBER) + 1;
        System.out.print("Your guess? ");
        int guess = kb.nextInt();
        int attempts = 1;
        while (guess != answer) {
            attempts++;
            if (guess < answer) {
                System.out.println("It's higher.");
            } else {
                System.out.println("It's lower.");
            }
            System.out.print("Your guess? ");
            guess = kb.nextInt();
        }
        if (attempts == 1) {
            System.out.println("You got it right in " + attempts + " guess!");
        } else {
            System.out.println("You got it right in " + attempts + " guesses!");
        }
        return attempts;
    }

    //Reports the overall statistics to the user
    //parameters:
    // games = number of total game rounds
    // guesses = number of total guesses
    // best = the least (best) number of guesses it took in a game round
    public static void gameStats(int games, int guesses, int best) {
        System.out.println("Overall results: ");
        System.out.println("Total games   = " + games);
        System.out.println("Total guesses = " + guesses);
        double ratio = (double) guesses / games;
        System.out.printf("Guesses/game  = %.1f\n", ratio);
        System.out.println("Best game     = " + best);
    }
}
