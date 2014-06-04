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

        for(int i = 0; i < in.length(); i++){
            result[i] = new Binary(17);
        }
        int indexResult = 0;
        for (char c : in.toCharArray()) {
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

        Integer decalage = 0;
        for(int i = 0; i < in.length(); i++){
            if(result[i - decalage].isEmpty()) {
                result = ArrayUtils.remove(result, i-decalage);
                decalage++;
            }
        }

        return result;
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
        compresse = ArrayUtils.removeElement(compresse, compresse[0]);

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