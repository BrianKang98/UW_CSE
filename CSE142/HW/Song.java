/*
 * Brian Kang
 * 1/5/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #1: Song.java
 *
 * This program will print a cumulative song.
 */

public class Song {
    public static void main(String[] args) {
        verse1();
        System.out.println();
        verse2();
        System.out.println();
        verse3();
        System.out.println();
        verse4();
        System.out.println();
        verse5();
        System.out.println();
        verse6();
        System.out.println();
        finalverse();
    }

    //The following 7 static methods prints the verses in the poem
    //in the format of the poem
    public static void verse1() {
        System.out.println("There was an old woman who swallowed a fly.");
        die();
    }

    public static void verse2() {
        System.out.println("There was an old woman who swallowed a spider,");
        System.out.println("That wriggled and iggled and jiggled inside her.");
        spiderFly();
        die();
    }

    public static void verse3() {
        System.out.println("There was an old woman who swallowed a bird,");
        System.out.println("How absurd to swallow a bird.");
        birdSpider();
        die();
    }

    public static void verse4() {
        System.out.println("There was an old woman who swallowed a cat,");
        System.out.println("Imagine that to swallow a cat.");
        catBird();
        die();
    }

    public static void verse5() {
        System.out.println("There was an old woman who swallowed a dog,");
        System.out.println("What a hog to swallow a dog.");
        dogCat();
        die();
    }

    //prints out my verse
    public static void verse6() {
        System.out.println("There was an old woman who swallowed a UW student,");
        System.out.println("Boundlessly imprudent to swallow a UW student.");
        studentDog();
        die();
    }

    public static void finalverse() {
        System.out.println("There was an old woman who swallowed a horse,");
        System.out.println("She died of course.");
    }

    //The "swallowed..." is a redundancy
    //The following 5 static methods prints the "cumulative" part of poem
    public static void spiderFly() {
        System.out.println("She swallowed the spider to catch the fly,");
    }

    public static void birdSpider() {
        System.out.println("She swallowed the bird to catch the spider,");
        spiderFly();
    }

    public static void catBird() {
        System.out.println("She swallowed the cat to catch the bird,");
        birdSpider();
    }

    public static void dogCat() {
        System.out.println("She swallowed the dog to catch the cat,");
        catBird();
    }

    //prints out my own object
    public static void studentDog() {
        System.out.println("She swallowed the UW student to catch the dog,");
        dogCat();
    }

    //prints the redundant final two lines in a verse
    public static void die() {
        System.out.println("I don't know why she swallowed that fly,");
        System.out.println("Perhaps she'll die.");
    }
}
