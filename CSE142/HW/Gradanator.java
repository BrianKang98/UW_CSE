/*
 * Brian Kang
 * 1/27/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #4: Gradanator.java
 *
 * This program will compute the user's course grade from
 * homework and two exams.
 */

import java.util.Scanner;

public class Gradanator {
    public static double WEIGHTEDMIDTERMSCORE, WEIGHTEDFINALEXAMSCORE,
            WEIGHTEDHOMEWORKSCORE, GRADESCORE;

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("This program reads exam/homework scores" +
                "\nand reports your overall course grade.\n");
        midterm(kb);
        System.out.println();
        finalExam(kb);
        System.out.println();
        homework(kb);
        System.out.println();
        grade();
    }

    //Displays the score details for the midterm and
    //returns the weighted midterm score
    //parameters needed:
    //  kb = asks for the numbers
    public static double midterm(Scanner kb) {
        System.out.println("Midterm: ");
        WEIGHTEDMIDTERMSCORE = calculate(WEIGHTEDMIDTERMSCORE, kb);
        return WEIGHTEDMIDTERMSCORE;
    }

    //Displays the score details for the final exam and
    //returns the weighted final exam score
    //parameters needed:
    //  kb = asks for the numbers
    public static double finalExam(Scanner kb) {
        System.out.println("Final: ");
        WEIGHTEDFINALEXAMSCORE = calculate(WEIGHTEDFINALEXAMSCORE, kb);
        return WEIGHTEDFINALEXAMSCORE;
    }

    //Collects the score details from the exams and
    //returns the calculated weighted score
    //parameters needed:
    //  weightedScore = the calculated weighted score
    //  kb = asks for the numbers
    public static double calculate(double weightedScore, Scanner kb) {
        System.out.print("Weight (0-100)? ");
        int weight = kb.nextInt();
        System.out.print("Score earned? ");
        int score = kb.nextInt();
        int scoreCap = 100;
        System.out.print("Were scores shifted (1=yes, 2=no)? ");
        int shift = kb.nextInt();
        //if there is a shift in score
        if (shift == 1) {
            System.out.print("Shift amount? ");
            int amount = kb.nextInt();
            //if the total score exceeds 100 points
            if (((score + amount) > scoreCap) || (score > scoreCap)) {
                score = scoreCap;
                System.out.println("Total points = " + score + " / " + scoreCap);
            } else {
                score += amount;
                System.out.println("Total points = " + score + " / " + scoreCap);
            }
            //if there is no shift in score
        } else {
            if (score > scoreCap) {
                score = scoreCap;
            }
            System.out.println("Total points = " + score + " / " + scoreCap);
        }
        weightedScore = ((double) score / scoreCap) * weight;
        System.out.printf("Weighted score = %.1f / " + weight, weightedScore);
        System.out.println();
        return weightedScore;
    }

    //Collects score details from homework assignments
    //and section attendance to calculate and
    //returns the weighted homework score
    //parameters needed:
    //  kb = asks for the numbers
    public static double homework(Scanner kb) {
        System.out.println("Homework:");
        System.out.print("Weight (0-100)? ");
        int weight = kb.nextInt();
        System.out.print("Number of assignments? ");
        int assignmentNum = kb.nextInt();
        int score = 0;
        int max = 0;
        //cumulative sum for assignments
        for (int i = 1; i <= assignmentNum; i++) {
            System.out.print("Assignment " + i + " score and max? ");
            int scoreNext = kb.nextInt();
            int maxNext = kb.nextInt();
            score += scoreNext;
            max += maxNext;
        }
        System.out.print("How many sections did you attend? ");
        int section = kb.nextInt();
        int sectionPoint = section * 5;
        int sectionCap = 30;
        if (sectionPoint > sectionCap) {
            sectionPoint = sectionCap;
        }
        int totalPoint = score + sectionPoint;
        int totalMax = max + sectionCap;
        if (totalPoint > totalMax) {
            //total point is capped by total max
            totalPoint = Math.min(totalPoint, totalMax);
        }   else {
            totalPoint = score + sectionPoint;
        }
        System.out.println("Section points = " + sectionPoint + " / " + sectionCap);
        System.out.println("Total points = " + (totalPoint) + " / " + (totalMax));
        WEIGHTEDHOMEWORKSCORE = (double) (totalPoint) / (totalMax) * weight;
        System.out.printf("Weighted score = %.1f / " + weight, WEIGHTEDHOMEWORKSCORE);
        System.out.println();
        return WEIGHTEDHOMEWORKSCORE;
    }

    //Uses the weighed scores to calculate the final course score
    //and prints the guaranteed minimum grade with a note
    public static void grade() {
        GRADESCORE = WEIGHTEDMIDTERMSCORE + WEIGHTEDFINALEXAMSCORE +
                WEIGHTEDHOMEWORKSCORE;
        System.out.printf("Overall percentage = %.1f", GRADESCORE);
        System.out.print("\nYour grade will be at least: ");
        //possible minimum grade results
        if (GRADESCORE >= 85) {
            System.out.println(3.0);
            System.out.println("Keep up the good work!");
        }   else if (GRADESCORE >= 75) {
            System.out.println(2.0);
            System.out.println("Let's try a little bit harder!");
        }   else if (GRADESCORE >= 60) {
            System.out.println(0.7);
            System.out.println("Please come see me after class.");
        }   else {
            System.out.println(0.0);
            System.out.println("I'll meet you at my office after school.");
        }
    }
}