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
                   " at " + lowest.get("DateUTC"));
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
            System.out.println(record.get("DateUTC") + ": " + record.get("TemperatureF"));
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
    
    public CSVRecord getLowerHumidity (CSVRecord currentRow, CSVRecord smallestSoFar) {
        int currentH = 0;
        int smallestH = 0;
        
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        } else {
            
            if (!currentRow.get("Humidity").contains("N/A")) {
                currentH = Integer.parseInt(currentRow.get("Humidity"));
                smallestH = Integer.parseInt(smallestSoFar.get("Humidity"));
            }
            
            if (currentH < smallestH) {
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
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestH = null;
        
        for (CSVRecord currentRow : parser) {
            lowestH = getLowerHumidity(currentRow, lowestH);
        }
        
        return lowestH;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get lowest in file.
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            // use method to compare two records
            lowestSoFar = getLowerHumidity(currentRow, lowestSoFar);
        }
        //The lowestSoFar is the answer
        return lowestSoFar;
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC"));
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        double sumTemp = 0.0;
        int numRecords = 0;
        double avgTemp = 0.0;
        
        for (CSVRecord record : parser) {
            //check if humidity for that record is >= value
            int humidity = Integer.parseInt(record.get("Humidity"));
            if (humidity >= value) {
                //if the humidity >= value
                numRecords++;
                sumTemp += Double.parseDouble(record.get("TemperatureF"));
            }
        }
        
        if (numRecords > 0) {
            avgTemp = sumTemp/numRecords;
            return avgTemp; //returns a double that represents the avg temp of only those temp when the humidity was >= value
        }
        
        return avgTemp;
        
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemp = averageTemperatureWithHighHumidityInFile(parser, 80);
        if (avgTemp == 0) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidiy is " + avgTemp);
        }
        
    }
    
    public double averageTemperatureInFile (CSVParser parser) {
        double sumTemp = 0.0;
        int numRecords = 0;
        double avgTemp = 0.0;
        
        for (CSVRecord record : parser) {
            sumTemp += Double.parseDouble(record.get("TemperatureF"));
            numRecords++;
        }
        avgTemp = sumTemp/numRecords;
        return avgTemp;
    }
    
    public void testAverageTempInFile () {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println("Average temperature is: " + averageTemperatureInFile(parser));
    }
}
