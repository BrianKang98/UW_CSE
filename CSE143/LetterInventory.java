/*
 * Brian Kang
 * 3/28/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #1: LetterInventory.java
 *
 * This program will process a string data to keep track of
 * an inventory of the number of each letters of the alphabet,
 * ignoring the case and all non-alphabetical characters, and
 * allows to carry out tasks using the inventory and count
 * (eg. 4 a's, 1 b's, 0 c's, etc.)
 */

public class LetterInventory {
    private int[] inventory;  // inventory of count of letters
    private int count;  // total count of the letters
    public static final int INVENTORY_SIZE = 26;

    // Constructs an inventory of counts and count of the alphabets in
    // a given string, ignoring the case and all non-alphabets
    // parameters needed:
    //  String data = the String being processed
    public LetterInventory(String data) {
        inventory = new int[INVENTORY_SIZE];
        for (int i = 0; i < data.toLowerCase().replaceAll("[^a-z]","").length(); i++) {
            inventory[data.toLowerCase().replaceAll("[^a-z]","").charAt(i) - 'a']++;
            count++;
        }
    }

    // Gets the count of a specific character in inventory
    // parameters needed:
    //  char letter = the character that is being searched for
    // pre: letter must be an alphabet, regardless of case
    //      if not, throw IllegalArgumentException
    // post: returns the int count of the character occurrence
    public int get(char letter) {
        letter = checkLetter(letter);
        return inventory[letter - 'a'];
    }

    // Sets the count of a specific character in inventory
    // parameters needed:
    //  char letter = the character that is being searched for
    //  int value = the value that will replace the count of letter
    // pre: letter must be an alphabet, regardless of case
    //      and value must be non-negative
    //      if not, throw IllegalArgumentException
    // post: the count of the character letter will be set to value
    public void set(char letter, int value) {
        letter = checkLetter(letter);
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        count += value - inventory[letter - 'a'];
        inventory[letter - 'a'] = value;
    }

    // Checks if a character letter is an alphabet or not
    // parameters needed:
    //  char letter = the character that is being checked
    // post: if the char letter is not an alphabet regardless of case
    //       throw IllegalArgumentException, if it is return the letter
    private char checkLetter(char letter) {
        letter = Character.toLowerCase(letter);
        if (letter < 'a' || letter > 'z') {
            throw new IllegalArgumentException();
        }
        return letter;
    }

    // post: return the int sum of all counts in inventory
    public int size() {
        return count;
    }

    // post: returns true if this inventory is empty
    //       if not return false
    public boolean isEmpty() {
        return count == 0;
    }

    // Represents how the inventory looks like with the letters in lowercase
    // and in alphabetical order, boxed with brackets
    // post: return String representation of inventory
    public String toString() {
        String represent = "[";
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            for (int j = 0; j < inventory[i]; j++) {
                represent += (char) (i + 'a');
            }
        }
        represent += "]";
        return represent;
    }

    // Constructs a new LetterInventory that is the sum of this inventory
    // and an other inventory
    // parameters needed:
    //  LetterInventory other = the other inventory being added to this one
    // post: the count is updated and returns the new LetterInventory newInventory
    public LetterInventory add(LetterInventory other) {
        LetterInventory newInventory = new LetterInventory("");
        newInventory.count = count + other.count;
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            newInventory.inventory[i] = inventory[i] + other.inventory[i];
        }
        return newInventory;
    }

    // Constructs a new LetterInventory that is the subtraction of
    // this inventory and an other inventory
    // parameters needed:
    //  LetterInventory other = the other inventory being subtracted to this one
    // post: the count is updated and returns the new LetterInventory newInventory,
    //       if any resulting count of a letter is negative return null
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory newInventory = new LetterInventory("");
        newInventory.count = count - other.count;
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            newInventory.inventory[i] = inventory[i] - other.inventory[i];
            if (newInventory.inventory[i] < 0) {
                return null;
            }
        }
        return newInventory;
    }
}
