/*
 * Brian Kang
 * 1/12/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #2: AsciiArt.java
 *
 * This program will print my own ascii art of a mushroom.
 */

public class AsciiArt {
    public static void main(String[] args) {
        topTop();
        topBottom();
        Middle();
        bottomTop();
        bottomBottom();
        mushroomFace();
    }

    // Prints the top-top of the mushroom
    public static void topTop() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= -6 * i + 29; j++) {
                System.out.print(" ");
            }

            for (int j = 1; j <= 12 * i + 10; j++) {
                System.out.print("8");
            }
            System.out.println();
        }
    }

    // Prints the top-bottom of the mushroom
    public static void topBottom() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 5 - i; j++) {
                System.out.print(" ");
            }

            for (int j = 1; j <= 58 + 2 * i; j++) {
                System.out.print("8");
            }
            System.out.println();
        }
    }

    // Prints the middle of the mushroom
    public static void Middle() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 68; j++) {
                System.out.print("8");
            }
            System.out.println();
        }
    }

    //Prints the bottom-top of the mushroom
    public static void bottomTop() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(" ");
            }

            for (int j = 1; j <= 68 - 2 * i; j++) {
                System.out.print("8");
            }
            System.out.println();
        }
    }

    //Prints the bottom-bottom of the mushroom
    public static void bottomBottom() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j < 6 * i; j++) {
                System.out.print(" ");
            }

            for (int j = 1; j <= 70 - 12 * i; j++) {
                System.out.print("8");
            }
            System.out.println();
        }
    }

    // Prints the mushroom's face
    public static void mushroomFace() {
        System.out.println("                       [     .    .         ]");
        System.out.println("                       [       --           ]");
        System.out.println("                        --------------------");
    }
}
