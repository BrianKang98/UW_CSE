/*
 * Brian Kang
 * 4/7/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #2: Guitar37.java
 *
 * This program models a guitar with 37 strings and allows
 * to carry out tasks on the strings, each designated with a
 * specific character that can be played
 */

public class Guitar37 implements Guitar {
    private GuitarString[] allStrings;  // an array of strings
    private int ticCount;   // number of tics that advanced time
    public static final String KEYBOARD =
            "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout

    // Constructs an array of 37 guitar strings each with
    // a different frequency
    public Guitar37() {
        allStrings = new GuitarString[37];
        for (int i = 0; i < allStrings.length; i++) {
            allStrings[i] = new GuitarString(440 * Math.pow(2, (i - 24) / 12.0));
        }
        ticCount = 0;
    }

    // Specifies which note to play with a given pitch
    // parameters needed:
    //  int pitch = the pitch of a note
    // pre: pitch is an integer 0 represents concert-A (char v) and
    //      all other notes are relative to concert-A
    // post: plays the note with the pitch but not every pitch can
    //       be played, if cannot be played then ignore
    public void playNote(int pitch) {
        if (pitch >= -24 && pitch <= 12) {
            allStrings[pitch + 24].pluck();
        }
    }

    // Verifies if a character has a corresponding
    // string for this guitar
    // parameter needed:
    //  char key = specific char to be checked
    // post: returns true if char has a corresponding string,
    //       if not return false
    public boolean hasString(char key) {
        return KEYBOARD.indexOf(key) != -1;
    }

    // Specifies which note to play with a given character
    // parameters needed:
    //  char key: specific key to be played
    // pre: if key is not one of the 37 keys designed to play
    //      throws IllegalArgumentException
    // post: plays the note with corresponding key
    public void pluck(char key) {
        if (!hasString(key)) {
            throw new IllegalArgumentException();
        }
        allStrings[KEYBOARD.indexOf(key)].pluck();
    }

    // post: returns the current sound sample double sampleSum
    // (the sum of all samples of the strings of the guitar)
    public double sample() {
        double sampleSum = 0;
        for (int i = 0; i < allStrings.length; i++) {
            sampleSum += allStrings[i].sample();
        }
        return sampleSum;
    }

    // Advances the time forward by one "tic"
    // post: all guitar strings and time is "tic"-ed
    public void tic() {
        for (int i = 0; i < allStrings.length; i++) {
            allStrings[i].tic();
        }
        ticCount++;
    }

    // Determines the current time
    // post: returns the integer number of times "tic"-ed ticCount
    public int time() {
        return ticCount;
    }
}