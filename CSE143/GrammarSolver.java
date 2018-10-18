/*
 * Brian Kang
 * 4/29/2018
 * CSE143
 * TA: Preston Crowe
 * Assignment #5: GrammarSolver.java
 *
 * This class will take in a list of the english grammar
 * that is in BNF formation to allow the user to
 * randomly generate the elements of the grammar.
 */

import java.util.*;

public class GrammarSolver {
    private SortedMap<String, List<String>> grammarRule;    // the grammar elements

    // Stores the grammar in a way that connects the nonterminals
    // to their elements of grammar that includes a combination of
    // terminals and/or nonterminals
    // parameters needed:
    //  List<String> grammar = the english grammar in BNF form
    // pre: if the grammar is empty or if there are two or more
    //      entries in the grammar for the same nonterminal,
    //      throw IllegalArgumentException
    // post: the grammar is separated into nonterminals and their
    //       terminal or nonterminal rules in sorted order
    public GrammarSolver(List<String> grammar) {
        if (grammar.isEmpty()) {
            throw new IllegalArgumentException();
        }
        grammarRule = new TreeMap<String, List<String>>();
        for (String line : grammar) {
            String[] nonterminal = line.split("::=");
            if (grammarRule.containsKey(nonterminal[0])) {
                throw new IllegalArgumentException();
            }
            String[] terminal = nonterminal[1].split("[|]");
            List<String> terminalList = new ArrayList<String>();
            for (String rule : terminal) {
                terminalList.add(rule.trim());
            }
            grammarRule.put(nonterminal[0], terminalList);
        }
    }

    // Checks if grammar has a nonterminal with the
    // corresponding given symbol
    // parameters needed:
    //  String symbol = the given symbol to be checked
    // post: if symbol is a nonterminal return true,
    //       if not return false
    public boolean grammarContains(String symbol) {
        return grammarRule.containsKey(symbol);
    }

    // Randomly generates the given symbol a given number
    // of times.
    // parameters needed:
    //  String symbol: the nonterminal symbol to be generated
    //  int times: the number of times to generate symbol
    // pre: if grammar does not contain the symbol
    //      or if the number of times is less than 0,
    //      throw IllegalArgumentException
    // post: return the generated symbols result as
    //       a string array
    public String[] generate(String symbol, int times) {
        if (!grammarContains(symbol) || times < 0) {
            throw new IllegalArgumentException();
        }
        String[] resultArray = new String[times];
        for (int i = 0; i < times; i++) {
            resultArray[i] = recurseGenerate(symbol).trim();
        }
        return resultArray;
    }

    // Randomly generates the given symbol once
    // parameters needed:
    //  String symbol: the nonterminal symbol to be generated
    // post: returns the result string of the symbol generated once
    private String recurseGenerate(String symbol) {
        String result = "";
        List<String> symbolValue = grammarRule.get(symbol);
        String randRule = symbolValue.get((int) (Math.random() * symbolValue.size()));
        String[] ruleArray = randRule.split("[ \t]+");
        for (String rule : ruleArray) {
            if (!grammarRule.containsKey(rule)) {
                result += rule + " ";
            } else {
                result += recurseGenerate(rule);
            }
        }
        return result;
    }

    // post: returns a string representation of the
    //       nonterminal symbols in sorted order,
    //       comma-separated, and bracket enclosed
    public String getSymbols() {
        return grammarRule.keySet().toString();
    }
}
