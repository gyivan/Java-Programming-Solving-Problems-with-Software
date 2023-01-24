
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    
    public String findSimpleGene(String dna, String startCodon, String stopCodon) {
        int start = dna.indexOf(startCodon);
        int stop = dna.indexOf(stopCodon, start+3);
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
        String test6 = "cccatgaaagggccctaagggccc"; //test 4 with lowercase
        System.out.println("DNA String: " + test1);
        System.out.println("Gene is: " + findSimpleGene(test1, "ATG", "TAA")); //no output
        System.out.println("DNA String: " + test2);
        System.out.println("Gene is: " + findSimpleGene(test2, "ATG", "TAA")); //no output
        System.out.println("DNA String: " + test3);
        System.out.println("Gene is: " + findSimpleGene(test3, "ATG", "TAA")); //no output
        System.out.println("DNA String: " + test4);
        System.out.println("Gene is: " + findSimpleGene(test4, "ATG", "TAA"));
        System.out.println("DNA String: " + test5);
        System.out.println("Gene is: " + findSimpleGene(test5, "ATG", "TAA")); //no output
        System.out.println("DNA String: " + test6);
        System.out.println("Gene is: " + findSimpleGene(test6, "atg", "taa")); //no output
    }
    
}
