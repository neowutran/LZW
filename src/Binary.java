import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yukikoo on 5/27/14.
 */
public class Binary {

    private boolean[] value;
    private boolean empty = true;

    public boolean isEmpty(){
        return empty;
    }

    public Binary(Integer numberOfBits){
        this.value = new boolean[numberOfBits];
    }

    public void fill(String binaryString){
        empty = false;
        int j = value.length -1;
        for(int i = binaryString.length() -1; i >= 0; i-- ){
            if(binaryString.charAt(i) == '1'){
                value[j] = true;
            }else if(binaryString.charAt(i) == '0'){
                value[j] = false;
            }else{
                throw new RuntimeException("Valeur binaire impossible");
            }
            j--;
        }
        for(;j >= 0; j--){
            value[j] = false;
        }

    }

    public Integer toInt(){
        Integer end = 0;
        for(int i = 0; i < value.length; i++){
            if(value[i]) {
                end += Double.valueOf(Math.pow(2, value.length - i - 1)).intValue();
            }
        }
        return end;

    }

    public static byte[] toBytes(Binary[] binaries){
        Integer binarySize = binaries[0].value.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.valueOf(Math.ceil(binarySize * binaries.length)/8).intValue());

        for(Binary binary: binaries) {
            byteBuffer = byteBuffer.putInt(binary.toInt());
            byteBuffer = byteBuffer.compact();
        }
        return byteBuffer.array();
    }

    @Override
    public String toString(){
        return Arrays.toString(value);
    }

}
