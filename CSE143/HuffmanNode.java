/*
 * Brian Kang
 * 5/27/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #8: HuffmanNode.java
 *
 * This class stores a single node of a binary tree of integers.
 * Each node stores an integer value for a character and an integer
 * value of its frequency.
 */

public class HuffmanNode implements Comparable<HuffmanNode> {
    public int chr;    // assigned character
    public int frq;    // frequency of character
    public HuffmanNode zero;    // binary code zero = branches left
    public HuffmanNode one;    // binary code one= branches right

    // Constructs a leaf node with given character and frequence
    public HuffmanNode(int chr, int frq) {
        this(chr, frq, null, null);
    }

    // Constructs a branch node with given character, frequency,
    // left (zero) subtree, right (one) subtree
    public HuffmanNode(int chr, int frq, HuffmanNode before, HuffmanNode after) {
        this.chr = chr;
        this.frq = frq;
        this.zero = before;
        this.one = after;
    }

    // Compares this object with the other object for order.
    // Returns a negative integer, zero, or a positive integer
    // as this object is less than, equal to, or greater than
    // the other object.
    public int compareTo(HuffmanNode other) {
        return frq - other.frq;
    }
}
