import edu.duke.*;
import java.io.*;
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {
    
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        //find stopCodon starting from (startIndex + 3)
        dna = dna.toUpperCase();
        int currIndex = dna.indexOf(stopCodon);
        
        while (currIndex != -1) { //While currIndex != -1
            //check if currIndex - startIndex is a multiple of 3
            if ( (currIndex - startIndex) % 3 == 0 ) {
                return currIndex;
            } else {
                //update currIndex, looking for stopCodon again starting from (currIndex + 1)
                currIndex = dna.indexOf(stopCodon, currIndex + 1);
            }
        }
        //while loop exited, stopCodon is not found
        return dna.length();
    }
    
    public void test() {
        System.out.println(countGenes("ATGTAAGATGCCCTAGT"));
    }
    
    public String findGene(String dna) {
        //Find the index of the first occurrence of the start codon "ATG"
        dna = dna.toUpperCase();
        int atgIndex = dna.indexOf("ATG");
        if (atgIndex == -1) { //if there is no "ATG", return the empty string
            return "";
        }
        
        //Find the index of the first occurrence of the stop codon “TAA” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”. Hint: call findStopCodon.
        int taaIndex = findStopCodon(dna, atgIndex, "TAA");
        
        //Find the index of the first occurrence of the stop codon “TAG” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”.
        int tagIndex = findStopCodon(dna, atgIndex, "TAG");
        
        //Find the index of the first occurrence of the stop codon “TGA” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”.
        int tgaIndex = findStopCodon(dna, atgIndex, "TGA");
        
        //Return the gene formed from the “ATG” and the closest stop codon that is a multiple of three away. If there is no valid stop codon and therefore no gene, return the empty string.
        int temp = Math.min(taaIndex, tagIndex);
        int minIndex = Math.min(temp, tgaIndex);
        
        if (minIndex == dna.length()) {
            return "";
        } else {
            return dna.substring(atgIndex, minIndex + 3);
        }
        
    }
    
    public void printAllGenes(String dna) {
        
        int startIndex = 0; //set startIndex to 0
        String gene = "";
        
        while (true) {
            gene = findGene(dna); //find the next gene after startIndex
            if (gene.isEmpty()) {
                break;
            }
            
            System.out.println(gene);
            startIndex = dna.indexOf(gene) + gene.length();
            dna = dna.substring(startIndex);
            
        }
    }
    
    
    public int countGenes(String dna) {
        
        int counter = 0; //have a counter
        int startIndex = 0; //set startIndex to 0
        String gene = "";
        
        while (true) {
            gene = findGene(dna); //find the next gene after startIndex
            if (gene.isEmpty()) {
                break;
            }

            counter++;
            startIndex = dna.indexOf(gene) + gene.length();
            dna = dna.substring(startIndex);
        
        }
        
        
        return counter; //returns the number of genes found in dna
    }
    
}
