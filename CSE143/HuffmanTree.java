/*
 * Brian Kang
 * 5/27/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #8: HuffmanTree.java
 *
 * This class constructs the Huffman tree needed to use the Huffman method,
 * which is a method to compress files. Instead of using 7/8 bits per char,
 * this method uses only a few bits for chars used often, and more for those
 * used rarely. This class can print encoded file and print decoded file from
 * a previously encoded file. The tree is constructed by either feeding the
 * data of characters and their frequencies or passing an encoded file.
 */

import java.io.*;
import java.util.*;

public class HuffmanTree {
    private HuffmanNode overallRoot;    // the overall Huffman tree

    // parameters needed:
    //  Scanner input = inputted data from encoded file
    // pre: assume that input contains a tree stored in standard format
    // post: constructs Huffman tree using an encoded file
    public HuffmanTree(Scanner input) {
        while (input.hasNextLine()) {
            int chr = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallRoot = rewrite(overallRoot, chr, code);
        }
    }

    // Complete Huffman tree is reconstructed. Zeros from the code
    // indicate a left branch. Ones from the code indicate a right
    // branch
    // parameters needed:
    //  HuffmanNode root = the tree to be traversed through
    //  int chr = the integer value of a character
    //  String code = the binary code for the character
    // post: makes new leaf nodes with given character values and
    //       reconstructs the Huffman tree using the nodes. the
    //       frequencies are irrelevant because the tree has already
    //       been constructed once from the inputted encoded file
    private HuffmanNode rewrite(HuffmanNode root, int chr, String code) {
        if (root == null) {
            root = new HuffmanNode(-1, -1);
        }
        if (code.isEmpty()) {
            root.chr = chr;
        } else if (code.charAt(0) == '0') {
            root.zero = rewrite(root.zero, chr, code.substring(1));
        } else {
            root.one = rewrite(root.one, chr, code.substring(1));
        }
        return root;
    }

    // Constructs initial Huffman tree using the Huffman method.
    // To use this method, all characters will be ordered from
    // lower frequency to higher frequency in decreasing hierarchy.
    // Within tree there is a single "pseudo-end-of-file" character
    // that will indicate the end of a file.
    // parameters needed:
    //  int[] count = indices indicate character's integer value
    //                and the data indicates its frequency
    // post: tree is made using characters that have a positive number
    //       frequency, including one indicating end of file (its character
    //       value is one higher than the largest character value available,
    //       it has a frequency of 1)
    public HuffmanTree(int[] count) {
        Queue<HuffmanNode> q = new PriorityQueue<HuffmanNode>();
        for(int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                q.add(new HuffmanNode(i, count[i]));
            }
        }
        // pseudo-eof
        q.add(new HuffmanNode(count.length, 1));
        while (q.size() > 1) {
            HuffmanNode first = q.remove();
            HuffmanNode second = q.remove();
            int totalFrq = first.frq + second.frq;
            q.add(new HuffmanNode(-1, totalFrq, first, second));
        }
        overallRoot = q.remove();
    }

    // parameters needed:
    //  PrintStream output = print data in output file
    // post: prints Huffman tree into an encoded code file
    //       in standard format and traversing order
    public void write(PrintStream output) {
        write(output, overallRoot, "");
    }

    // parameters needed:
    //  PrintStream output = print data in output file
    //  HuffmanNode root = the tree to be traversed through
    //  String code = the binary code of the character
    // post: tree is printed in standard format in traversing order
    private void write(PrintStream output, HuffmanNode root, String code) {
        if (root != null) {
            if (root.zero != null || root.one != null) {
                write(output, root.zero, code + "0");
                write(output, root.one, code + "1");
            } else {
                output.println(root.chr);
                output.println(code);
            }
        }
    }
    
    // Decodes an existing encoded file to print the resulting characters
    // parameters needed:
    //  BitInputStream input = reads the data in encoded file
    //  PrintStream output = print data in output file
    //  int eof = integer value of the end-of-file indicating character
    // pre: assume that input stream contains a legal encoding of characters
    //      for this tree's Huffman code
    // post: continuously, encoded file is read and prints the data's
    //       corresponding characters until the end-of-file character
    //       is found, which is not printed
    public void decode(BitInputStream input, PrintStream output, int eof) {
        HuffmanNode root = overallRoot;
        while (root.chr != eof) {
            if (root.zero == null && root.one == null) {
                output.write(root.chr);
                root = overallRoot;
            } else if (input.readBit() == 0) {
                root = root.zero;
            } else {
                root = root.one;
            }
        }
    }
}
