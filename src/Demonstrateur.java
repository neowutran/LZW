import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by yukikoo on 5/28/14.
 */
public class Demonstrateur {

    public static void main(String[] args) {
        String input = "";
        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("./input.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                input += strLine+"\n";
            }
            //Close the input stream
            in.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("contenu origine:"+input);
        Binary[] compresse = LZW.compression(input);
        Path path = Paths.get("./output.txt");
        try {
            Files.write(path, Binary.toBytes(compresse));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String stringCompresser = "";
        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("./output.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                stringCompresser += strLine;
            }
            //Close the input stream
            in.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("contenu compresser:"+stringCompresser);
        String decompresse = LZW.decompression(compresse);
        System.out.println("contenu decompresser:"+decompresse);

    }
}
