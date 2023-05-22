package Algorithm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


public class calculateFreq {
    static final int MAX_SIZE_RAED = 10000;
    Map<String, Integer> mappingFreq = new TreeMap<>();

    public Map<String, Integer> convertIntoTwoArrays( File filePath, int n) throws IOException {
         BytesToArray(filePath, n);
        return mappingFreq;
    }


    private void readAndPharse(int n, byte[] bytes, int size) {
        StringBuilder chunks = new StringBuilder();

        for(int i=0 ; i<size;i=i+n){
            for (int j=i;j<i+n&&j<size;j++){
                chunks.append(String.valueOf(bytes[j]));
                chunks.append(" ");
            }
            countChars(chunks.toString(),mappingFreq);
            chunks = new StringBuilder();
        }
    }

    private void countChars(String  subStr, Map<String, Integer> mapping) {
        if (mapping.containsKey(subStr)) {
            mapping.put(subStr, mapping.get(subStr) + 1);
        }
        else {
            mapping.put(subStr, 1);
        }
    }

    private void BytesToArray(File filePath, int n) throws IOException {
        byte[] dataAsBytes= new byte[MAX_SIZE_RAED];
        FileInputStream file = new FileInputStream(filePath);
        BufferedInputStream input = new BufferedInputStream(file);
        int i = input .read(dataAsBytes,0,MAX_SIZE_RAED);
        while (i != -1) {
            readAndPharse(n,dataAsBytes,i);
            i = input .read(dataAsBytes,0,MAX_SIZE_RAED);
        }
        input.close();
    }



}
