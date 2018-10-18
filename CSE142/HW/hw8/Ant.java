/*
 * Brian Kang
 * 3/2/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #8: Ant.java
 *
 * This program will create a critter object with the Ant
 * behaviors: is red, eats everything, always scratches,
 * moves diagonally only SE or NE, and looks like a %
 */

import java.awt.*;

public class Ant extends Critter {
    private boolean walkSouth;
    private int move;

    // Gives value to the fields.
    // parameters needed:
    //  walkSouth = whether Ant goes S or N
    public Ant(boolean walkSouth) {
        this.walkSouth = walkSouth;
        move = 0;
    }

    // Determine animal color
    // return the color red
    public Color getColor() {
        return Color.RED;
    }

    // Determine animal eating behavior
    // return always eat
    public boolean eat() {
        return true;
    }

    // Determine animal fighting behavior
    // return the attack scratch
    // parameter(s) needed:
    //  opponent = other animal's shape
    public Attack fight(String opponent) {
        return Attack.SCRATCH;
    }

    // Determine animal movement
    // return the direction S,E, or N
    public Direction getMove() {
        move++;
        if (walkSouth && (move % 2 == 0)) {
            return Direction.SOUTH;
        } else if (move % 2 == 1) {
            return Direction.EAST;
        } else {
            return Direction.NORTH;
        }
    }

    // Determine animal shape
    // return the animal appearance
    public String toString() {
        return "%";
    }
}
