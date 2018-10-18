/*
 * Brian Kang
 * 5/21/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #7: QuestionNode.java
 *
 * This class stores a single node of a binary tree of strings
 */

public class QuestionNode {
    public String data;    // consists either Answer or Question
    public QuestionNode yes;    // branches to the left (input was yes)
    public QuestionNode no;    // branches to the right (input was no)

    // Constructs a leaf node with given data
    public QuestionNode(String data) {
        this(data, null, null);
    }

    // Constructs a branch node with given data,
    // left (yes) subtree, right (no) subtree
    public QuestionNode(String data, QuestionNode yes, QuestionNode no) {
        this.data = data;
        this.yes = yes;
        this.no = no;
    }
}
