package modulo;

import java.util.*;

public class LZW {
    /** 
     * 
     * Compression d'une chaine de carractere en une list d'output.
     * 
     * */
    public static List<Integer> compression(String in) {
        // Creation du dictionnaire.
        int taille = 256;
        Map<String,Integer> dictionnaire = new HashMap<String,Integer>();
        
        for (int i = 0; i < 256; i++){
        	dictionnaire.put("" + (char)i, i);
        }
        
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : in.toCharArray()) {
            String wc = w + c;
            if (dictionnaire.containsKey(wc))
                w = wc;
            else {
                result.add(dictionnaire.get(w));
                dictionnaire.put(wc, taille++);
                w = "" + c;
            }
        }
 
        if (!w.equals(""))
            result.add(dictionnaire.get(w));
        return result;
    }
    
    
 
    /**
     * 
     *  decompression...
     *  
     * */
    public static String decompression(List<Integer> compresse) {
        int taille = 256;
        Map<Integer,String> dictionnaire = new HashMap<Integer,String>();
        for (int i = 0; i < 256; i++)
        	dictionnaire.put(i, "" + (char)i);
 
        String w = "" + (char)(int)compresse.remove(0);
        StringBuffer result = new StringBuffer(w);
        for (int k : compresse) {
            String entry;
            if (dictionnaire.containsKey(k))
                entry = dictionnaire.get(k);
            else if (k == taille)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Mauvaise compression de k: " + k);
 
            result.append(entry);
 
            dictionnaire.put(taille++, w + entry.charAt(0));
 
            w = entry;
        }
        return result.toString();
    }
 
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	String input = new String();
    	String c;
    	c = sc.nextLine();
    	input = input + c;
    	sc.close();
    	//System.out.println(input);
        List<Integer> compresse = compression(input);
        System.out.println(compresse);
        String decompresse = decompression(compresse);
        System.out.println(decompresse);
        
    }
}