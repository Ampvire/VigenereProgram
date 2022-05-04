import java.util.*;
import edu.duke.*;

public class Tester {
    public void testSliceString(){
        VigenereBreaker vb = new VigenereBreaker();
        String nM = vb.sliceString("abcdefghijklm",1,3);
        System.out.println(nM);
        
    }
    public void testTryKeyLength(){
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        //String kLength = "flute";
        //int kL = kLength.length();
        int kL = 38;
        int [] key = vb.tryKeyLength(encrypted,kL,'e');
        System.out.println(Arrays.toString(key));
        
        
    }
    public void testHashSet(){
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> mySet = new HashSet<String>();
        FileResource fr = new FileResource();
        mySet = vb.readDictionary(fr);
        System.out.println(mySet);
    }
    
    public void testCountWords(){
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> mySet = new HashSet<String>();
        FileResource fr = new FileResource();
        String message = "I don't understand you, play, game, the,it,русский, нет";
        mySet = vb.readDictionary(fr);
        System.out.println(vb.countWords(message, mySet));
        
    }
    
    public void testMostCommonCharIn(){
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource("dictionaries/Italian");
        HashSet<String> mySet = new HashSet<String>();
        mySet = vb.readDictionary(fr);
        char mostFreq = vb.mostCommonCharIn(mySet);
        System.out.println(mostFreq);
    }
    //La chambre à coucher de Juliette.
    //Drei Hexen treten auf. 
}   
