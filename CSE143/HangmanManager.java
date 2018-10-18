/*
 * Brian Kang
 * 4/18/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #4: HangmanManager.java
 *
 * This class keeps track of the state of a game of
 * a cheating hangman. Unlike a normal hangman, picking
 * a word will be delayed until forced to by considering
 * all possible words in a dictionary and choosing the
 * answer with the largest number of possible words.
 */

import java.util.*;

public class HangmanManager {
    private SortedSet<Character> lettersGuessed;    // set of letters guessed
    private SortedSet<String> allWords;    // all words being considered
    private int maxGuess;    // max number of guesses left
    private String pattern;    // current pattern of word

    // Constructs a hangman manager with the initial
    // state of the game set up.
    // parameters needed:
    //  Collection<String> dictionary = dictionary of words
    //  int length = target word length
    //  int max = max number of wrong guesses allowed
    // pre: if length is less than 1 or max is less than 0,
    //      throw IllegalArgumentException
    //      assume that the dictionary is nonempty and its
    //      words are all lower cased
    // post: state of game is initialized by saving a set
    //       of the words with the length and no duplicates
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        maxGuess = max;
        pattern = "-";
        for (int i = 1; i < length; i++) {
            pattern += " -";
        }
        allWords = new TreeSet<String>();
        for (String word : dictionary) {
            if (word.length() == length) {
                allWords.add(word);
            }
        }
        lettersGuessed = new TreeSet<Character>();
    }

    // post: returns the current set of allWords being
    //       considered by the hangman manager
    public Set<String> words() {
        return allWords;
    }

    // post: returns the int maxGuess, the number of guesses left
    public int guessesLeft() {
        return maxGuess;
    }

    // post: returns the set of lettersGuessed already
    public Set<Character> guesses() {
        return lettersGuessed;
    }

    // pre: if the set of words being considered is empty,
    //      throw IllegalStateException
    // post: returns the current String pattern to be displayed,
    //       correctly guessed letters will show and ones not
    //       yet guessed will be displayed as a dash
    public String pattern() {
        if (allWords.isEmpty()) {
            throw new IllegalStateException();
        }
        return pattern;
    }

    // Records the next guess made by the user. What set of words
    // to use is decided.
    // parameters needed:
    //  char guess = the letter user guessed
    // pre: if number of guesses left is not at least one
    //      or if the set of words being considered is empty
    //      throw IllegalStateException
    //      if the set of words being considered is not empty
    //      and the character guessed has been guessed already
    //      throw IllegalArgumentException
    //      assume that the letter guessed is lower cased
    // post: returns integer number of how many times the guess
    //       letter occurs in the newly updated pattern, and
    //       new set of words to be considered is chosen, and
    //       number of guesses left is updated
    public int record(char guess) {
        if (allWords.isEmpty() || !(maxGuess >= 1)) {
            throw new IllegalStateException();
        }
        if (!allWords.isEmpty() && lettersGuessed.contains(guess)) {
            throw new IllegalArgumentException();
        }
        Map<String, SortedSet<String>> wordFamilyMap = new TreeMap<String, SortedSet<String>>();
        mapPatterns(guess, wordFamilyMap);
        pickPatternSet(wordFamilyMap);
        return countOccur(guess);
    }

    // Maps sets of words to be considered correspondingly with
    // the patterns that the words match to, in consideration
    // of the letter user guessed
    // parameters needed:
    //  char guess = the letter user guessed
    //  Map<String, SortedSet<String>> wordFamilyMap
    //   = map of patterns to sets of words
    // post: all possible patterns of words with or without
    //       the guessed letter is mapped with the separated
    //       sets of words that correspond to the patterns
    private void mapPatterns(char guess, Map<String, SortedSet<String>> wordFamilyMap) {
        for(String word : allWords) {
            String patternFamily = pattern + " ";
            if (word.charAt(0) == guess) {
                patternFamily = guess + patternFamily.substring(1);
            }
            for (int i = 1; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    patternFamily = patternFamily.substring(0, 2 * i - 1)
                            + " " + guess + patternFamily.substring(2 * i + 1);
                    if (patternFamily.charAt(patternFamily.length() - 1) == ' ') {
                        patternFamily = patternFamily.substring(0, patternFamily.length() - 1);
                    }
                }
            }
            if (!wordFamilyMap.containsKey(patternFamily)) {
                wordFamilyMap.put(patternFamily, new TreeSet<String>());
            }
            wordFamilyMap.get(patternFamily).add(word);
        }
    }

    // Chooses the pattern and corresponding set of words
    // the hangman manager should consider
    // parameters needed:
    //  Map<String, SortedSet<String>> wordFamilyMap
    //   = map of patterns to sets of words 
    // post: chooses the pattern and set of words with
    //       the largest number of words in that set of word
    //       if there are a set of words with equal number of
    //       words in the set and has the choose the final one
    //       set, choose the one earlier in the map
    private void pickPatternSet(Map<String, SortedSet<String>> wordFamilyMap) {
        int mostWords = 0;
        for (String patternFamily : wordFamilyMap.keySet()) {
            if (wordFamilyMap.get(patternFamily).size() > mostWords) {
                mostWords = wordFamilyMap.get(patternFamily).size();
                allWords = wordFamilyMap.get(patternFamily);
                pattern = patternFamily;
            }
        }
    }

    // Counts how many times the letter guessed
    // occurs in the pattern and updates the number of
    // remaining guesses allowed
    // parameters needed:
    //  char guess = the letter user guessed
    // post: returns int count of how many times the guessed
    //       letter occurs in the pattern and updates 
    //       the number of guesses left
    private int countOccur(char guess) {
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == guess) {
                count++;
            }
        }
        if (!lettersGuessed.contains(guess) && !pattern.contains("" + guess)) {
            maxGuess--;
        }
        if (!lettersGuessed.contains(guess)) {
            lettersGuessed.add(guess);
        }
        return count;
    }
}
