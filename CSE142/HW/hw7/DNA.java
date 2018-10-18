/*
 * Brian Kang
 * 2/26/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #7: DNA.java
 *
 * This program will process data from genome files containing named
 * sequences of nucleotides and produce information about them. The
 * program, for every sequence, counts the number of each 4 nucleotides,
 * computes the mass percentage by nucleotide type, prints the codons
 * present, and predicts whether the sequence is a protein gene. All
 * this data will be printed into a file the user wants to create.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class DNA {
    public static final int MIN_CODON = 5, VALID_PERCENT = 30,
    NUM_NUCLEOTIDE = 4, NUCLEOTIDE_PER_CODON = 3;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("This program reports information about DNA");
        System.out.println("nucleotide sequences that may encode proteins.");
        System.out.print("Input file name? ");
        String inputFile = keyboard.nextLine();
        System.out.print("Output file name? ");
        String outputFile = keyboard.nextLine();

        File file = new File(inputFile);
        Scanner input = new Scanner(file);
        PrintStream output = new PrintStream(new File(outputFile));

        //the given masses for each unique nucleotide
        double[] masses = {135.128, 111.103, 151.128, 125.107};
        //repeats for each DNA
        while (input.hasNextLine()) {
            String dnaName = input.nextLine();
            String nucleotideLine = input.nextLine();

            String nucleotide = readDNA(nucleotideLine);
            int[] count = nucCount(nucleotide);
            double totalMass = calculateTotalMass(nucleotide, count, masses);
            double[] massPercentage = calculateMass(count, masses, totalMass);
            String[] codons = listCodon(nucleotide);
            printStuff(dnaName, nucleotide, count, totalMass, massPercentage,
                    codons, output);
        }
    }

    //Reads all the nucleotides in a sequence region.
    //returns the nucleotides sequence in all uppercase
    //parameters needed:
    // nucleotideLine = the line containing the nucleotides
    public static String readDNA(String nucleotideLine) {
        Scanner lineScan = new Scanner(nucleotideLine);
        String nucleotides = "";
        while (lineScan.hasNext()) {
            String nucleotide = lineScan.next().toUpperCase();
            nucleotides += nucleotide;
        }
        return nucleotides;
    }

    //Counts the number of individual nucleotides (A, C, G, T)
    //returns the count in an array
    //parameters needed:
    // sequence = the nucleotide sequence
    public static int[] nucCount(String sequence) {
        int[] count = new int[NUM_NUCLEOTIDE];
        for (int i = 0; i < sequence.length(); i++) {
            char nucleotide = sequence.charAt(i);
            // will just ignore "-"s
            if (nucleotide == 'A') {
                count[0]++;
            } else if (nucleotide == 'C') {
                count[1]++;
            } else if (nucleotide == 'G') {
                count[2]++;
            } else if (nucleotide == 'T') {
                count[3]++;
            }
        }
        return count;
    }

    //Calculates the total mass of the DNA region
    //returns the total mass
    //parameters needed:
    // sequence = the nucleotide sequence
    // count = array of the count of individual nucleotides
    // masses = array of the given mass of the nucleotides
    public static double calculateTotalMass(String sequence, int[] count,
                                            double[] masses) {
        int junk = 0;
        for (int i = 0; i < sequence.length(); i++) {
            //count num of "-"
            char nucleotide = sequence.charAt(i);
            if (nucleotide == '-') {
                junk++;
            }
        }
        //given mass of the junk
        double junkMass = 100.000;
        double totalMass = junk * junkMass;
        for (int i = 0; i < masses.length; i++) {
            totalMass += count[i] * masses[i];
        }
        //double rounded = Math.round(totalMass * 10) / 10.0;
        return totalMass;
    }

    //Calculates the percentage of the total mass
    //an unique nucleotide takes up.
    //returns the array of percentages of each nucleotide type
    //parameters needed:
    // count = array of the count of individual nucleotides
    // masses = array of the given mass of the nucleotides
    // totalMass = total mass of the DNA region
    public static double[] calculateMass(int[] count, double[] masses,
                                         double totalMass) {
        double[] massPercent = new double[NUM_NUCLEOTIDE];
        for (int i = 0; i < massPercent.length; i++) {
            double percentage = count[i] * masses[i] / totalMass * 100;
            double rounded = Math.round(percentage * 10) / 10.0;
            massPercent[i] = rounded;
        }
        return massPercent;
    }

    //Separates the DNA region into codons
    //returns the array of codons
    //parameters needed:
    // sequence = the nucleotide sequence
    public static String[] listCodon(String sequence) {
        String nucleotide = sequence.replace("-", "");
        String[] codons = new String[nucleotide.length()/NUCLEOTIDE_PER_CODON];
        for (int i = 0; i < codons.length; i++) {
            //sequence is being cut into codons (3 nucleotides)
            codons[i] = nucleotide.substring(3 * i, 3 * i + NUCLEOTIDE_PER_CODON);
        }
        return codons;
    }

    //Prints all DNA informations of potential protein onto an output file
    //parameters needed:
    // dnaName = name of the DNA region
    // nucleotides = the nucleotide sequence
    // count = array of the count of individual nucleotides
    // totalMass = total mass of the DNA region
    // massPercentage = array of percentages of total mass
    //                  for each nucleotide type
    // codons = the array of codons
    // output = so the data can be printed in output file
    public static void printStuff(String dnaName, String nucleotides, int[] count,
                                  double totalMass, double[] massPercentage,
                                  String[] codons, PrintStream output) {
        output.println("Region Name: " + dnaName);
        output.println("Nucleotides: " + nucleotides);
        output.println("Nuc. Counts: " + Arrays.toString(count));
        double roundedTotalMass = Math.round(totalMass * 10) / 10.0;
        output.println("Total Mass%: " + Arrays.toString(massPercentage) +
                " of " + roundedTotalMass);
        output.println("Codons List: " + Arrays.toString(codons));
        output.print("Is Protein?: ");

        //ends with a valid stop codon
        boolean stopCodon = codons[codons.length-1].equals("TAA") ||
                codons[codons.length-1].equals("TAG") ||
                codons[codons.length-1].equals("TGA");
        //(C) and (G) combined account for at least 30% of its total mass
        boolean minMassPercent = massPercentage[1] +
                massPercentage[2] >= VALID_PERCENT;
        //above two conditions AND
        //begins with a valid start codon AND
        //contains at least (min requirement codon # for protein)
        //if ALL TRUE, is true, else false
        if (codons[0].equals("ATG") && stopCodon && (codons.length >= MIN_CODON)
                && minMassPercent) {
            output.println("YES");
        } else {
            output.println("NO");
        }
        output.println();
    }
}
