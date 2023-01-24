/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord hottestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldest = null;
        
        for (CSVRecord currentRow : parser) {
            coldest = getSmallestOfTwo(currentRow, coldest);
        }
        
        return coldest;
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVRecord lowest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + lowest.get("TemperatureF") +
                   " at " + lowest.get("TimeEST"));
    }
    
    public void testHottestInDay () {
        FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("TimeEST"));
    }
    
    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }
    
    public String fileWithColdestTemperature() {
        CSVRecord coldestSoFar = null;
        CSVRecord temp = null;
        String coldestFileName = null;
        DirectoryResource dr= new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            File currentFile = f;
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            coldestSoFar = getSmallestOfTwo(currentRow, coldestSoFar);
            if (coldestSoFar != temp) { //if there is a change in coldest data so far
                temp = coldestSoFar; //update temp to the new coldest
                coldestFileName = f.getName(); //get the filename with the current coldest
            }
        }
        
        return coldestFileName;
    }
    
    public void testFileWithColdestTemperature() {
        String coldestFilename = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + coldestFilename);
        
        FileResource fr = new FileResource(coldestFilename);
        CSVRecord lowest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature on that day was " + lowest.get("TemperatureF") );
        
        System.out.println("All the Temperatures on the coldest day were:");
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            System.out.println(record);
        }
    }
    
    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
        //If largestSoFar is nothing
        if (largestSoFar == null) {
            largestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
            if (currentTemp > largestTemp) {
                //If so update largestSoFar to currentRow
                largestSoFar = currentRow;
            }
        }
        return largestSoFar;
    }

    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        } else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
            
            if ( (currentTemp < smallestTemp) && (currentTemp != -9999) ) {
                smallestSoFar = currentRow;
            }
        }
        return smallestSoFar;
    }
    
    public void testHottestInManyDays () {
        CSVRecord largest = hottestInManyDays();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("DateUTC"));
    }
    
    
}
