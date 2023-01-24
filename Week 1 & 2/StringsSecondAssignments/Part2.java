import edu.duke.*;
import java.io.*;
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    
    public int howMany(String stringA, String stringB) {
        
        //Each time stringA appears, slice stringB
        int counter = 0;
        int indexA = stringB.indexOf(stringA);
        
        while (indexA != -1) { //if stringA can still be found in stringB
            counter++; //add one to the counter
            //slice stringB
            stringB = stringB.substring(indexA + stringA.length());
            
            //update indexA to look for the next occurrence of A
            indexA = stringB.indexOf(stringA);
        }
        
        return counter;
    }
    
    public void test() {
        System.out.println(howMany("GAA", "ATGAACGAATTGAATC")); //returns 3
        System.out.println(howMany("AA", "ATAAAA")); //returns 2
    }

}
