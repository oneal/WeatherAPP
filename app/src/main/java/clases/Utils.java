package clases;

/**
 * Created by g on 17/02/2016.
 */
public class Utils {
    public static String firstToUpperCase(String word){
        char[] elemntsWord = word.toCharArray();
        return word.replace(elemntsWord[0],Character.toUpperCase(elemntsWord[0]));
    }
}
