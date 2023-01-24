import edu.duke.*;
import java.io.*;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        //find stopCodon starting from (startIndex + 3)
        dna = dna.toUpperCase();
        int currIndex = dna.indexOf(stopCodon, startIndex);
        
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
    

    public String findGene(String dna, int where) {
        //Find the index of the first occurrence of the start codon "ATG"
        dna = dna.toUpperCase();
        //System.out.println("DNA is: " + dna);
        int dnaLength = dna.length();
        int atgIndex = dna.indexOf("ATG", where);
        //System.out.println("Index of ATG (findGene) is: " + atgIndex);
        if (atgIndex == -1) { //if there is no "ATG", return the empty string
            return "";
        }
        
        //Find the index of the first occurrence of the stop codon “TAA” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”. Hint: call findStopCodon.
        int taaIndex = findStopCodon(dna, atgIndex, "TAA");
        //System.out.println("TAA Index is: " + taaIndex);
        
        //Find the index of the first occurrence of the stop codon “TAG” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”.
        int tagIndex = findStopCodon(dna, atgIndex, "TAG");
        //System.out.println("TAG Index is: " + tagIndex);
        
        //Find the index of the first occurrence of the stop codon “TGA” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”.
        int tgaIndex = findStopCodon(dna, atgIndex, "TGA");
        //System.out.println("TGA Index is: " + tgaIndex);
        
        //Return the gene formed from the “ATG” and the closest stop codon that is a multiple of three away. If there is no valid stop codon and therefore no gene, return the empty string.
        int temp = Math.min(taaIndex, tagIndex);
        int minIndex = Math.min(temp, tgaIndex);
        //System.out.println("Min Index is: " + minIndex);
        
        if (minIndex == dna.length()) {
            return "";
        } else {
            //System.out.println("DNA Length is: " + dnaLength);
            //System.out.println("Starting index is: " + atgIndex);
            //System.out.println("Remaining DNA substring is: " + dna.substring(minIndex + 3));
            //System.out.println("Ending index is: " + (minIndex + 3));
            //System.out.println("Gene substring: " + dna.substring(atgIndex, minIndex + 3));
            return dna.substring(atgIndex, minIndex + 3);
        }
        
    }
    
    public void printAllGenes(String dna) {
        
        int startIndex = 0; //set startIndex to 0
        String gene = "";
        
        while (true) {
            gene = findGene(dna, startIndex); //find the next gene after startIndex
            if (gene.isEmpty()) {
                break;
            }
            
            System.out.println(gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();

        }
    }
    
    public StorageResource getAllGenes(String dna) {
        
        StorageResource geneList = new StorageResource();
        
        int startIndex = 0; //set startIndex to 0
        String gene = "";
        
        while (true) {
            //System.out.println("Start Index is: " + startIndex);
            gene = findGene(dna, startIndex); //find the next gene after startIndex
            if (gene.isEmpty()) {
                break;
            }
            
            geneList.add(gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
            //System.out.println("Starting index (getAllGenes) is: " + startIndex);

        }
        
        return geneList;
    }

    public double cgRatio (String dna) {
        double cgCount = 0.0;
        
        String dnaC = dna.toUpperCase();
        String dnaG = dna.toUpperCase();
        int currIndexC = 0;
        int currIndexG = 0;
        
        
        while (true) {
            //check if C is in dnaC
            currIndexC = dnaC.indexOf("C");
            if (currIndexC == -1) {
                break;
            }
            
            cgCount++;
            dnaC = dnaC.substring(currIndexC + 1);
            
        }
        
        while (true) {
            currIndexG = dnaG.indexOf("G");
            if (currIndexG == -1) {
                break;
            }
            
            cgCount++;
            dnaG = dnaG.substring(currIndexG + 1);
            
        }
        
        return cgCount/dna.length();
        
    }
    
    public int countCTG(String dna) {
        //find stopCodon starting from (startIndex + 3)
        dna = dna.toUpperCase();
        int currIndex = 0;
        int count = 0;
        
        while (true) { //While currIndex != -1
            
            currIndex = dna.indexOf("CTG");
            if (currIndex == -1) {
                break;
            }
            
            count++;
            dna = dna.substring(currIndex + 3);
            
        }

        return count;
    }
    
    public void processGenes(StorageResource sr) {

        int numGenes = 0;
        int counterCTG = 0;
        
        System.out.println("ALL STRINGS");
        for (String s : sr.data()) { //print all the Strings in sr that are longer than 9 characters
            numGenes++;
            System.out.println(s);
        }
        System.out.println();
        System.out.println("Total number of genes: " + numGenes);
        
        System.out.println();
        System.out.println("Strings in sr that are longer than 60 characters:");
        
        int numStrings1 = 0; //strings longer than 60 chars
        int numStrings2 = 0; //strings whose C-G-ratio > 0.35
        int maxLength = 0;
        

        for (String s : sr.data()) { //print all the Strings in sr that are longer than 9 characters

            if (s.length() > 60) {
                System.out.println(s);
                numStrings1++;
            }
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }

        System.out.println();
        System.out.println("Number of Strings in sr that are longer than 60 characters: " + numStrings1);
        

        System.out.println();
        System.out.println("Strings in sr whose C-G-ratio is higher than 0.35");
        
        for (String s: sr.data()) {
            double ratio = cgRatio(s);
            if (ratio > 0.35) {
                System.out.println(s);
                numStrings2++;
            }
        }
        
        System.out.println();
        System.out.println("number of strings in sr whose C-G-ratio is higher than 0.35: " + numStrings2);
        
        System.out.println();
        System.out.println("Length of the longest gene in sr: " + maxLength);
        
    }
    
    public void test() {
        /*
        //StorageResource genes = getAllGenes("AATGCTAACTAGCTGACTAAT");
        StorageResource genes = getAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        System.out.println("Gene List: ");
        for (String s : genes.data()) {
            System.out.println(s);
        }
        */
       //FileResource fr = new FileResource("brca1line.fa");
       FileResource fr = new FileResource("GRch38dnapart.fa");
       String dna = fr.asString();
       
       System.out.println("CTG Count: " + countCTG(dna));
       
       //StorageResource sr = getAllGenes("ATGCCCTTTCCATAGCCCATGTTTCCATAGGGG");
       StorageResource sr = getAllGenes(dna);
       
       //for (String s : sr.data()) {
       //    System.out.println(s);
       //}
       processGenes(sr);
    }
    
}
