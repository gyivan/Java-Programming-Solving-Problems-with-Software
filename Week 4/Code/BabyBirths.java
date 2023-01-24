/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalGirlsNames = 0;
        int totalBoysNames = 0;
        int totalNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                totalBoysNames++;
            }
            else {
                totalGirls += numBorn;
                totalGirlsNames++;
            }
        }
        totalNames = totalBoysNames + totalGirlsNames;
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("total names = " + totalNames);
        System.out.println("total girls names = " + totalGirlsNames);
        System.out.println("total boys names = " + totalBoysNames);
    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        //FileResource fr = new FileResource("data/yob2014.csv");
        //FileResource fr = new FileResource("yob1900.csv");
        FileResource fr = new FileResource("yob1905.csv");
        totalBirths(fr);
    }
    
    public CSVParser getParser(int year) {
        String filename = "yob" + String.valueOf(year) + ".csv";
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser(false);
        return parser;
    }
    
    public int getRank(int year, String name, String gender) {

        int rank = -1;
        int tracker = 0;
        String prevGender = "F";
        String currGender = "F";

        CSVParser parser = getParser(year);
        
        for (CSVRecord rec : parser) { //look through all the records
            
            prevGender = currGender;
            currGender = rec.get(1);
            
            if ( (prevGender.equals("F")) && (currGender.equals("M")) ) {
                tracker = 1;
            } else {
                tracker++;
            }
            
            //System.out.println("Tracker: " + tracker);

            if ( (rec.get(0).equals(name)) && (rec.get(1).equals(gender)) ) {
                rank = tracker;
                //System.out.println("Rank: " + rank);
            }
        }
        return rank;
    }
    
    public void testGetRank() {
        //int test = getRank(2012, "Mason", "M");
        //int test = getRank(1960, "Emily", "F");
        int test = getRank(1971, "Frank", "M");
        System.out.println(test);
    }
    
    public String getName(int year, int rank, String gender) {
        
        int tracker = 0;
        String prevGender = "F";
        String currGender = "F";
        
        CSVParser parser = getParser(year);
        
        for (CSVRecord rec : parser) {
            
            prevGender = currGender;
            currGender = rec.get(1);
            
            if ( (prevGender.equals("F")) && (currGender.equals("M")) ) {
                tracker = 1;
            } else {
                tracker++;
            }
            
            if ( (rank == tracker) && (rec.get(1).equals(gender)) ) {
                return rec.get(0);
            }
            
        }
        
        return "NO NAME";
    }
    
    public void testGetName() {
        //System.out.println(getName(2012, 2, "M"));
        //System.out.println(getName(1980, 350, "F"));
        System.out.println(getName(1982, 450, "M"));
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        String pronoun = "";
        if (gender == "M") {
            pronoun = "he";
        } else {
            pronoun = "she";
        }
        
        System.out.println(name + " born in " + year + " would be " + getName(newYear, getRank(year, name, gender), gender) + " if " + pronoun + " was born in " + newYear);
    }
    
    public void testWhatIsNameInYear() {
        //whatIsNameInYear("Isabella", 2012, 2014, "F");
        //whatIsNameInYear("Susan", 1972, 2014, "F");
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public int yearOfHighestRank(String name, String gender) {
        int result = -1;
        int highestSoFar = 99999999;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) { //for each file
            String yearStr = f.getName().substring(3, 7);
            int yearInt = Integer.parseInt(yearStr); //get the year of the current file
            
            int currRank = getRank(yearInt, name, gender); //find the rank of the name in the current file
            //if the rank is higher in this year than in previous years
            if ( (currRank != -1) &&  (currRank < highestSoFar) ) {
                highestSoFar = currRank; //update the highest rank
                result = yearInt;
            }
            //getRank(int year, String name, String gender)
        }
        
        //if name and gender are not found in any of the years
        if (highestSoFar == 99999999) {
            return -1;
        }
        
        return result;
    }
    
    public void testYearOfHighestRank() {
        //int test = yearOfHighestRank("Mason", "M");
        //int test = yearOfHighestRank("Genevieve", "F");
        int test = yearOfHighestRank("Mich", "M");
        System.out.println(test);
    }
    
    
    public double getAverageRank(String name, String gender) {
        double sumRank = 0.0;
        int numYears = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            numYears++;
            String yearStr = f.getName().substring(3, 7);
            int yearInt = Integer.parseInt(yearStr);
            
            CSVParser parser = getParser(yearInt);
            int currRank = getRank(yearInt, name, gender);
            if (currRank != -1) {
                sumRank += currRank;
            }
            
        }
        return sumRank/numYears;
    }
    
    public void testGetAverageRank() {
        //double test = getAverageRank("Mason", "M");
        //double test = getAverageRank("Jacob", "M");
        //double test = getAverageRank("Susan", "F");
        double test = getAverageRank("Robert", "M");
        System.out.println(test);
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int result = 0;
        //look for same gender and same year but ranked higher
        
        //first find rank in that year for name provided
        int targetRank = getRank(year, name, gender);
        
        //look through all records of the same gender and get their rank
        CSVParser parser = getParser(year);
        
        for (CSVRecord rec : parser) {
            if (rec.get(1).equals(gender)) { //if that entry has the same gender
                int currRank = getRank(year, rec.get(0), gender); //get the rank of that entry
                if ( (currRank != -1) && (currRank < targetRank) ) { //if that entry is ranked higher than the target entry
                    result += Integer.parseInt(rec.get(2)); //add the number of births
                }
            }
        }
        
        return result;
    }
    
    public void testGetTotalBirthsRankedHigher() {
        //System.out.println(getTotalBirthsRankedHigher(2012, "Ethan", "M"));
        System.out.println(getTotalBirthsRankedHigher(1990, "Emily", "F"));
    }
    
}
