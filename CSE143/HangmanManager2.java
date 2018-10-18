/*
 * Brian Kang
 * 5/10/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #4 Bonus: HangmanManager2.java
 *
 * This class changes the original Evil Hangman game
 * to make it even more evil and protect the internal
 * data structures from the client.
 */

import java.util.*;

public class HangmanManager2 extends HangmanManager {

    public HangmanManager2(Collection<String> dictionary, int length, int max) {
        super(dictionary, length, max);
    }

    // If the client is down to one guess left, edit
    // the set of words available to immediately make the
    // client lose the game
    // post: if there is one guess left, the current set
    //       of words is now just the first word of the set
    //       that does not contain the letter being guessed
    public int record(char guess) {
        if (guessesLeft() != 1) {
            super.record(guess);
        } else {
            for (String lastWord : words()) {
                if (!lastWord.contains("" + guess)) {
                    words().clear();
                    words().add(lastWord);
                    return super.record(guess);
                }
            }
        }
        return 0;
    }

    // post: returns a unmodifiable version of the set if
    //       new objects are made, if not return the set
    public Set<String> words() {
        Set<String> newSet = super.words();
        if (newSet == super.words()) {
            return newSet;
        } else {
            return Collections.unmodifiableSet(newSet);
        }
    }
    
    // post: returns a unmodifiable version of the set if
    //       new objects are made, if not return the set
    public Set<Character> guesses() {
        Set<Character> newSet = super.guesses();
        if (newSet == super.guesses()) {
            return newSet;
        } else {
            return Collections.unmodifiableSet(newSet);
        }
    }
}
