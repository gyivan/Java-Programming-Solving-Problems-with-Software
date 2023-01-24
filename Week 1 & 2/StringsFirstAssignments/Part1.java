/**
 * Finds a protein within a strand of DNA represented as a string of c,g,t,a letters.
 * A protein is a part of the DNA strand marked by start and stop codons (DNA triples)
 * that is a multiple of 3 letters long.
 *
 * @author Duke Software Team 
 */
import edu.duke.*;
import java.io.*;

public class Part1 {
    
    public String findSimpleGene(String dna) {
        int start = dna.indexOf("ATG");
        int stop = dna.indexOf("TAA", start+3);
        if (((stop-start) % 3 != 0) || (start == -1) || (stop == -1)) {
            return "";
        } else {
            return dna.substring(start, stop+3);
        }
    }
    
    public void testSimpleGene() {
        String test1 = "CCCAAATAATAATAAGGA"; //DNA with no "ATG"
        String test2 = "ATGAAAATGCCCGGA"; //DNA with no "TAA"
        String test3 = "AAAAAAAAAAAAAAAAAAAAA"; //DNA with no "ATG" or "TAA"
        String test4 = "CCCATGAAAGGGCCCTAAGGGCCC"; //DNA with "ATG", "TAA" and the substring between them is a multiple of 3 (a gene)
        String test5 = "AAAATGCCCGGCAATAA"; //DNA with "ATG", "TAA" and the substring between them is not a multiple of 3
        System.out.println("DNA String: " + test1);
        System.out.println("Gene is: " + findSimpleGene(test1)); //no output
        System.out.println("DNA String: " + test2);
        System.out.println("Gene is: " + findSimpleGene(test2)); //no output
        System.out.println("DNA String: " + test3);
        System.out.println("Gene is: " + findSimpleGene(test3)); //no output
        System.out.println("DNA String: " + test4);
        System.out.println("Gene is: " + findSimpleGene(test4));
        System.out.println("DNA String: " + test5);
        System.out.println("Gene is: " + findSimpleGene(test5)); //no output
    }

}
