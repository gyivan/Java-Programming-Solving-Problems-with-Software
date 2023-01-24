
/**
 * Write a description of Assignment here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;


public class Assignment {
    
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //System.out.println(countryInfo(parser, "Germany"));
        //System.out.println(countryInfo(parser, "Nauru"));
        //listExportersTwoProducts(parser, "gold", "diamonds");
        //listExportersTwoProducts(parser, "fish", "nuts");
        //listExportersTwoProducts(parser, "cotton", "flowers");
        //System.out.println(numberOfExporters(parser, "gold"));
        //System.out.println(numberOfExporters(parser, "cocoa"));
        bigExporters(parser, "$999,999,999,999");
    }
    
    public String countryInfo(CSVParser parser, String country) {
        for (CSVRecord record : parser) {
            if (record.get("Country").equals(country)) {
                String exports = record.get("Exports");
                return (country + ": " + exports);
            }
        }
        return ("NOT FOUND");
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if ( (exports.contains(exportItem1)) && (exports.contains(exportItem2)) ) {
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int counter = 0;
        
        for (CSVRecord record : parser) {
            if (record.get("Exports").contains(exportItem)) {
                counter++;
            }
        }
        return counter;
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord record : parser) {
            if (record.get("Value (dollars)").length() > amount.length()) {
                System.out.println(record.get("Country") + " " + record.get("Value (dollars)") );
            }
        }
    }
    
}
