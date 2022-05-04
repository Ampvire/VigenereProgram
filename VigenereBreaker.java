import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices){
            char currChar = message.charAt(i);
            sb.append(currChar);
        }
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc;
        for (int i = 0; i < klength; i++){
            cc = new CaesarCracker(mostCommon);
            String sS = sliceString(encrypted,i,klength);
            key[i] = cc.getKey(sS);
        }
        
        return key;
    }
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> mySet = new HashSet<String>();
        for (String line : fr.lines()){
            line = line.toLowerCase();
            mySet.add(line);
        }
        return mySet;
    }
    
    public int countWords(String message, HashSet<String> dicrionary){
        int count = 0;
        for (String word : message.split("\\W")){
            word = word.toLowerCase();
            if (dicrionary.contains(word)){
                count++;
            }
        }
        //System.out.println(count);
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        VigenereCipher vc;
        int maxCount = 0;
        String decrypted = "";
        for (int i = 1 ; i < 100 ; i++){
            vc = new VigenereCipher(tryKeyLength(encrypted,i,mostCommonCharIn(dictionary)));
            String decrypt = vc.decrypt(encrypted);
            int count = countWords(decrypt,dictionary);
            if (count > maxCount){
                maxCount = count;
                decrypted = decrypt;
                /*System.out.println(Arrays.toString(tryKeyLength(encrypted,i,
                          mostCommonCharIn(dictionary)).length));*/
            }
        }
        System.out.println(maxCount);
        return decrypted;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> chFreq = new HashMap<Character, Integer>();
        chFreq.clear();
        for (String word : dictionary){
            for (int i = 0 ; i < word.length(); i++){
                char currChar = word.charAt(i);
                if (!chFreq.containsKey(currChar)){
                    chFreq.put(currChar, 1);
                }
                else {
                    chFreq.put(currChar,chFreq.get(currChar)+1);
                }
            }
        }
        int maxFreq = 0;
        Character maxChar = null;
        for (char currChar : chFreq.keySet()){
            int freq = chFreq.get(currChar);
            if (freq > maxFreq){
                maxFreq = freq;
                maxChar = currChar;
            }
        }
        return maxChar;
    }
    
    public String breakForAllLangs(String encrypted, HashMap<String,HashSet<String>>languages){
        int maxCount = 0;
        String decrypted = "";
        HashSet<String> mySet;
        for (String language : languages.keySet()){
            mySet = languages.get(language);
            String decrypt = breakForLanguage(encrypted,mySet);
            int count = countWords(decrypt,mySet);
            breakForLanguage(encrypted,mySet);
            if (maxCount < count){
                maxCount = count;
                decrypted = decrypt;
                System.out.println(language);
            }
        }
        return decrypted;
    }
    
    public void breakVigenere () {
        /*VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource();
        VigenereCipher vc = new VigenereCipher(tryKeyLength(fr.asString(),4,'e'));
        System.out.println(vc.decrypt(fr.asString()));
        /*String encrypted = fr.asString();
        int[] key = tryKeyLength(encrypted,5,'e');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);
        System.out.println(decrypted);
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        FileResource frE = new FileResource("dictionaries/English");
        HashSet<String> mySet = readDictionary(frE);
        String decrypted = breakForLanguage(encrypted,mySet);
        System.out.println(decrypted);*/
        DirectoryResource dr = new DirectoryResource();
        FileResource fr;
        HashSet<String> mySet;
        HashMap<String,HashSet<String>> languages = new HashMap<String,HashSet<String>>();
        for (File f : dr.selectedFiles()){
            String fName = f.getName();
            fr = new FileResource("dictionaries/"+ fName);
            mySet = readDictionary(fr);
            languages.put(fName,mySet);
            System.out.println("Dictionary "+fName+" is loaded");
        }
        FileResource frE = new FileResource();
        String encrypted = frE.asString();
        String decrypted = breakForAllLangs(encrypted,languages);
        System.out.println(decrypted);
    }
    
}
    

 