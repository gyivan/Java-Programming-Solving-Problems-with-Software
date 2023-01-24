
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {
    
    public boolean twoOccurrences(String stringa, String stringb) {
        int indexA = stringb.indexOf(stringa);
        if (indexA != -1) { //first occurrence
            int lengthA = stringa.length();
            if (stringb.indexOf(stringa, indexA + lengthA) != -1) { //second occurrence
                return true;
            }
        }
        return false;
    }
    
    public String lastPart(String stringa, String stringb) {
        int indexA = stringb.indexOf(stringa);
        if (indexA != -1) {
            int lengthA = stringa.length();
            return stringb.substring(indexA + lengthA);
        } else {
            return stringb;
        }
    }
    
    public void testing() {
        String a = "by";
        String b = "A story by Abby Long";
        System.out.println("String A: " + a);
        System.out.println("String B: " + b);
        System.out.println("Result: " + twoOccurrences(a, b)); //true
        
        a = "a";
        b = "banana";
        System.out.println("String A: " + a);
        System.out.println("String B: " + b);
        System.out.println("Result: " + twoOccurrences(a, b)); //true
        
        a = "atg";
        b = "ctgtatgta";
        System.out.println("String A: " + a);
        System.out.println("String B: " + b);
        System.out.println("Result: " + twoOccurrences(a, b)); //false
        
        System.out.println("LAST PART");
        a = "an";
        b = "banana";
        System.out.println("String A: " + a);
        System.out.println("String B: " + b);
        System.out.println("Result: " + lastPart(a, b)); //"ana"
        
        a = "zoo";
        b = "forest";
        System.out.println("String A: " + a);
        System.out.println("String B: " + b);
        System.out.println("Result: " + lastPart(a, b)); //"forest"
    }
    
}
