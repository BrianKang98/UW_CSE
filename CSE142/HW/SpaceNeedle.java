/*
 * Brian Kang
 * 1/12/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #2: SpaceNeedle.java
 *
 * This program will print a Space Needle that
 * can change its size.
 */

public class SpaceNeedle {
    //The class constant represents the size of the pieces of the figure
    public static final int CONSTANT = 4;
    public static void main(String[] args) {
        skinnyBody();
        domeTop();
        domeMiddle();
        domeBottom();
        skinnyBody();
        midsectionBody();
        baseBody();
    }

    // Prints the part of the figure that only contains "|"
    public static void skinnyBody() {
        for (int i = 1; i <= CONSTANT; i++) {
            for (int j = 1; j <= CONSTANT; j++) {
                System.out.print("   ");
            }
            System.out.println("||");
        }
    }

    // Prints the part of the figure that only contains "__/", ":", "|", and "\__"
    public static void domeTop() {
        for (int i = 1; i <= CONSTANT; i++) {
            for (int k = 1; k <= -3 * i + 3 * CONSTANT; k++) {
                System.out.print(" ");
            }
            System.out.print("__/");

            for (int k = 1; k <= 3 * i - 3; k++) {
                System.out.print(":");
            }
            System.out.print("||");

            for (int k = 1; k <= 3 * i -3; k++) {
                System.out.print(":");
            }
            System.out.println("\\__");
        }
    }

    // Prints the part of the figure that only contains "|" and """
    public static void domeMiddle() {
        System.out.print("|");
        for (int i = 1; i <= 6 * CONSTANT; i++) {
            System.out.print("\"");
        }
        System.out.println("|");
    }

    // Prints the part of the figure that only contains "\_", "/\", and "_/"
    public static void domeBottom() {
        for (int i = 1; i <= CONSTANT; i++) {
            for (int j = 1; j <= 2 * i - 2; j++) {
                System.out.print(" ");
            }
            System.out.print("\\_");

            for (int j = 1; j <= -2 * i + (3 * CONSTANT + 1); j++) {
                System.out.print("/\\");
            }
            System.out.println("_/");
        }
    }

    // Prints the part of the figure that only contains "|" and "%"
    public static void midsectionBody() {
        for (int i = 1; i <= CONSTANT * CONSTANT; i++) {
            for (int j = 1; j <= CONSTANT * 2 + 1; j++) {
                System.out.print(" ");
            }

            for (int j = 1; j <= 2; j++) {
                System.out.print("|");
                for (int k = 1; k <= CONSTANT - 2; k++) {
                    System.out.print("%");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    // Prints the base of the figure that contains all "__/", ":", "|", "\__", and """
    public static void baseBody() {
        domeTop();
        domeMiddle();
    }
}
