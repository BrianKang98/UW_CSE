/*
 * Brian Kang
 * 3/2/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #8: Husky.java
 *
 * This program will create a critter object with the Husky
 * behavior
 */

import java.awt.*;
import java.util.Random;

public class Husky extends Critter {
    private int condition;
    private Random rand;
    private int move;
    private int n;

    public Husky() {
        condition = 0;
        rand = new Random();
        move = 0;
        n = 0;
    }

    public Attack fight(String opponent) {
        if (opponent.equals("%")) {
            condition = 1;
            return Attack.ROAR;
        } else if (opponent.equals("^") || opponent.equals(">") ||
                opponent.equals("V") || opponent.equals("<")) {
            condition = 2;
            return Attack.SCRATCH;
        } else {
            condition = 3;
            return Attack.SCRATCH;
        }
    }

    public Color getColor() {
        if (condition == 1) {
            return Color.PINK;
        } else if (condition == 2) {
            return Color.MAGENTA;
        } else if (condition == 3) {
            return Color.orange;
        }
        return Color.green;
    }

    public String toString() {
        String str = "$$";
        if (condition == 1) {
            str = "@@";
        } else if (condition == 2) {
            str = "#-#";
        } else if (condition == 3){
            str = "\\|/";
        }
        return str;
    }

    public boolean eat() {
        return true;
    }

    public Direction getMove() {
        if(move == 0) {
            n = rand.nextInt(4);
            move = rand.nextInt(25) + 1;
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
}