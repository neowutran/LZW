import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LZW {
    /** 
     * 
     * Compression d'une chaine de carractere en une list d'output.
     * 
     * */
    public static Binary[] compression(String in) {
        // Creation du dictionnaire.
        int taille = Double.valueOf(Math.pow(2, 16)).intValue();
        Map<String,Integer> dictionnaire = new HashMap<String,Integer>();

        for (int i = 0; i < taille; i++){
        	dictionnaire.put("" + (char)i, i);
        }

        String w = "";
        Binary result[] = new Binary[in.length()];

        int indexResult = 0;
        int i = 0;
        for (char c : in.toCharArray()) {
            result[i] = new Binary(17);
            i++;
            String wc = w + c;
           if (dictionnaire.containsKey(wc)) {
                w = wc;
            }else {

                result[indexResult].fill(Integer.toBinaryString(dictionnaire.get(w)));
                indexResult++;
               if(taille != Double.valueOf(Math.pow(2, 17)).intValue()) {
                   dictionnaire.put(wc, taille++);
               }
                w = "" + c;
            }
        }

        if (!w.equals("")){
            result[indexResult].fill(Integer.toBinaryString(dictionnaire.get(w)));
        }

        Binary resultCleaned[] = null;
        for(i = 0; i < in.length(); i++){
            if(result[i].isEmpty()) {
                resultCleaned = new Binary[i];
                for(int j = 0; j < i; j++){
                    resultCleaned[j] = result[j];
                }
                break;
            }
        }

        return resultCleaned;
    }
    
    
 
    /**
     * 
     *  decompression...
     *  
     * */
    public static String decompression(Binary[] compresse) {
        int taille = Double.valueOf(Math.pow(2, 16)).intValue();;
        Map<Integer,String> dictionnaire = new HashMap<Integer,String>();
        for (int i = 0; i < taille; i++) {
            dictionnaire.put(i, "" + (char) i);
        }

        String w = "" + (char)(int)compresse[0].toInt();
        compresse = ArrayUtils.remove(compresse, 0);

        StringBuffer result = new StringBuffer(w);
        for (Binary k : compresse) {
            String entry;
            if (dictionnaire.containsKey(k.toInt())) {
                entry = dictionnaire.get(k.toInt());
            }else if (k.toInt() == taille) {
                entry = w + w.charAt(0);
            }else {
                throw new IllegalArgumentException("Mauvaise compression de k: " + k.toInt());
            }

            result.append(entry);
 
            dictionnaire.put(taille++, w + entry.charAt(0));
 
            w = entry;
        }
        return result.toString();
    }

}