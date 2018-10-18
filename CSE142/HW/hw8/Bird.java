/*
 * Brian Kang
 * 3/2/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #8: Bird.java
 *
 * This program will create a critter object with the Bird
 * behaviors: is blue, never eats, always pounces (roars only to ants),
 * moves 3 times N, E, S, then W, and looks like a carrot facing the
 * direction is goes
 */

import java.awt.*;

public class Bird extends Critter{
    private int move;
    private int lastMove;

    // Gives value to the fields.
    public Bird() {
        move = 1;
        lastMove = 1;
    }

    // Determine animal color
    // return the color blue
    public Color getColor() {
        return Color.BLUE;
    }

    // Determine animal eating behavior
    // return never eat
    public boolean eat() {
        return false;
    }

    // Determine animal fighting behavior
    // return the attack scratch to ants,
    // else return the attack pounce
    // parameter(s) needed:
    //  opponent = other animal's shape
    public Attack fight(String opponent) {
        if (opponent.equals("%")) {
            return Attack.ROAR;
        } else {
            return Attack.POUNCE;
        }
    }

    // Determine animal movement
    // return the direction N,E,S, or W
    // 3 times each in order
    public Direction getMove() {
        if (0 < (move % 12) && (move % 12) <= 3) {
            move++;
            lastMove = 1;
            return Direction.NORTH;
        } else if (3 < (move % 12) && (move % 12) <= 6) {
            move++;
            lastMove = 2;
            return Direction.EAST;
        } else if (6 < (move % 12) && (move % 12) <= 9) {
            move++;
            lastMove = 3;
            return Direction.SOUTH;
        } else {
            move++;
            lastMove = 4;
            return Direction.WEST;
        }
    }

    // Determine animal shape
    // return the animal appearance
    public String toString() {
        if (lastMove == 1) {
            return "^";
        } else if (lastMove == 2) {
            return ">";
        } else if (lastMove == 3) {
            return "V";
        } else {
            return "<";
        }
    }
}
