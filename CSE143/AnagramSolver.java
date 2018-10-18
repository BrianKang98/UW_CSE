/*
 * Brian Kang
 * 5/15/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #6: AnagramSolver.java
 *
 * This class uses a dictionary to find all or a given
 * number of combinations of words (anagrams) that have
 * the same letters as a given phrase.
 */

import java.util.*;

public class AnagramSolver {
    private Map<String, LetterInventory> dictionary;    // words in the dictionary
                                            // are mapped to their LetterInventory
    private List<String> dictionaryCopy;    // a copy of the dictionary

    // Constructs an anagram solver that uses a dictionary
    // parameters needed:
    //  List<String> list = the dictionary
    // pre: assume that the dictionary is a nonempty
    //      collection of nonempty sequences of letters
    //      and contains no duplicates
    // post: mapped dictionary and its copy is made
    public AnagramSolver(List<String> list) {
        dictionary = new HashMap<String, LetterInventory>();
        dictionaryCopy = new ArrayList<String>();
        for (String word : list) {
            dictionaryCopy.add(word);
            dictionary.put(word, new LetterInventory(word));
        }
    }

    // Find all anagrams of a phrase that include
    // a max (or unlimited) number of words and prints them
    // parameters needed:
    //  String s = the given phrase
    //  int max = the max number of words that form the phrase
    // pre: if max is less than 0, throw IllegalArgumentException
    // post: makes a smaller dictionary of "relevant" words for
    //       efficiency, then prints all anagrams
    public void print(String s, int max) {
        if (max < 0) {
            throw new IllegalArgumentException();
        }
        LetterInventory phrase = new LetterInventory(s);
        List<String> printList = new ArrayList<String>();
        // smaller dictionary only contains words that can be
        // subtracted from the given phrase a.k.a "relevant"
        List<String> newDictionary = new ArrayList<String>();
        for (String choose : dictionaryCopy) {
            if (phrase.subtract(dictionary.get(choose)) != null) {
                newDictionary.add(choose);
            }
        }
        explore(max, phrase, printList, newDictionary);
    }

    // Explores all possible anagrams of the given phrase
    // parameters needed:
    //  int max = the max number of words that form the phrase
    //      if max = 0, search for all anagrams
    //  LetterInventory word = LetterInventory form of given phrase
    //  List<String> printList = builds up the anagrams
    //  List<String> newDictionary = the smaller dictionary 
    //      of "relevant" words for efficiency
    // post: explores all possible anagrams and prints those
    //       with max number of words and fits the phrase
    private void explore(int max, LetterInventory word,
                         List<String> printList, List<String> newDictionary) {
        if (word.isEmpty() && (max == 0 || printList.size() <= max)) {
            System.out.println(printList);
        } else {
            for (String choose : newDictionary) {
                LetterInventory remainWord = word.subtract(dictionary.get(choose));
                if (remainWord != null) {
                    printList.add(choose);
                    explore(max, remainWord, printList, newDictionary);
                    printList.remove(choose);
                }
            }
        }
    }
}
