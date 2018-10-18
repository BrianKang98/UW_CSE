/*
 * Brian Kang
 * 3/2/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #8: Vulture.java
 *
 * This program will create a critter object with the Vulture
 * behaviors: is black, initially eats only once until fighting,
 * attacks, moves, and looks like a Bird (always pounces (roars only to ants),
 * moves 3 times N, E, S, then W, looks like a carrot facing the
 * direction is goes)
 */

import java.awt.*;

public class Vulture extends Bird {
    private int condition;

    // Gives value to the fields.
    public Vulture() {
        super();
        condition = 0;
    }

    // Determine animal color
    // return the color black
    public Color getColor() {
        return Color.BLACK;
    }

    // Determine animal eating behavior
    // return eat or don't eat
    public boolean eat() {
        if (condition <= 0) {
            condition = 1;
            return true;
        } else {
            condition = 1;
            return false;
        }
    }

    // Determine animal fighting behavior
    // return the attack scratch to ants,
    // else return the attack pounce
    // parameter(s) needed:
    //  opponent = other animal's shape
    public Attack fight(String opponent) {
        if (opponent.equals("%")) {
            condition--;
            return Attack.ROAR;
        }
        return Attack.POUNCE;
    }

    // Determine animal movement
    // return the direction N,E,S, or W
    // 3 times each in order
    public Direction getMove() {
        return super.getMove();
    }

    // Determine animal shape
    // return the animal appearance
    public String toString() {
        return super.toString();
    }
}
