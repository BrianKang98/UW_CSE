/*
 * Brian Kang
 * 3/2/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #8: Hippo.java
 *
 * This program will create a critter object with the Hippo
 * behaviors: is gray when hungry (white when full), eats until full,
 * scratches when hungry (pounce when full), moves 5 times in a random
 * direction, and looks like the number of how much Hippo has to eat
 * to be full
 */

import java.awt.*;
import java.util.Random;

public class Hippo extends Critter {
    private int hunger;
    private int number;
    private Random rand;
    private int move;
    private int n;
    private boolean condition;

    // Gives value to the fields.
    // parameters needed:
    //  hunger = how hungry hippo is
    public Hippo (int hunger) {
        this.hunger = hunger;
        number = 0;
        rand = new Random();
        move = 0;
        n = 0;
        condition = true;
    }

    // Determine animal color
    // return the color gray if hungry
    // else return color white
    public Color getColor() {
        if (hunger > number) {
            return Color.GRAY;
        }
        return Color.WHITE;
    }

    // Determine animal eating behavior
    // return eat when hungry,
    // else return don't eat
    public boolean eat() {
        number++;
        if (hunger >= number) {
            return condition;
        } else {
            condition = false;
            return condition;
        }
    }

    // Determine animal fighting behavior
    // return the attack scratch if hungry,
    // else return the attack pounce
    // parameter(s) needed:
    //  opponent = other animal's shape
    public Attack fight(String opponent) {
        if (condition) {
            return Attack.SCRATCH;
        }
        return Attack.POUNCE;
    }

    // Determine animal movement
    // return the direction N,S,E or W
    // 5 times each in random order
    public Direction getMove() {
        if(move == 0) {
            n = rand.nextInt(4);
            move = 5;
        }
        if (n == 0) {
            move--;
            return Direction.NORTH;
        } else if (n == 1) {
            move--;
            return Direction.SOUTH;
        } else if (n == 2) {
            move--;
            return Direction.EAST;
        } else {
            move--;
            return Direction.WEST;
        }
    }

    // Determine animal shape
    // return the animal appearance
    public String toString() {
        if ((hunger - number) > 0) {
            return "" + (hunger - number);
        } else {
            return "0";
        }
    }
}
