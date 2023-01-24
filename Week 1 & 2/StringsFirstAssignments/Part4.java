import edu.duke.*;
import java.io.*;
/**
 * Write a description of Part4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part4 {

    public void test() {
        
        URLResource ur = new URLResource("https://www.dukelearntoprogram.com//course2/data/manylinks.html");
        for (String word : ur.words()) {
            //process each word in turn
            String word2 = word.toLowerCase();
            //System.out.println(word);
            
            //check to see if "youtube.com" is in it
            int pos = word2.indexOf("youtube.com");
            if (pos != -1) { //word contains "youtube.com"
                int start = word2.lastIndexOf("\"", pos);
                int end = word2.lastIndexOf("\"");
                String result = word.substring(start + 1, end);
                System.out.println(result);
            }
            
        }
        
    }
    
    
    public void test2() {
        
        URLResource file = new  URLResource("https://www.dukelearntoprogram.com//course2/data/manylinks.html");
           	for (String item : file.words()) {
               	   String itemLower = item.toLowerCase();
               	   int pos = itemLower.indexOf("youtube.com");
               	   if (pos != -1) {
               	       int beg = item.lastIndexOf("\"",pos);
                       int end = item.indexOf("\"", pos+1);
                       System.out.println(item.substring(beg+1,end));
                       }
           	}
        
    }
}
