package com.multimedia.algorithms.Algorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class decomperssData {
    static final int MAX_SIZE_RAED = 10000;
    Map<String, String> reversedDic = new HashMap<>();
    Map<String, String> dic = new HashMap<>();
    Map<String, Integer> mapping = new TreeMap<>();
    int n=0;
    int mappingLen=0;
    workingClass.myTreeNode trueRoot =null;
    ArrayList<Byte> decompressData= new ArrayList<>();
    int filesize =0;
    FileInputStream file;
    BufferedInputStream input;
    FileOutputStream decompressData1;
    int bytecounter =0;
    String str="";


    void decompress(File filePath , File outputFile) throws IOException {
        byte[] NumbersAsBytes= new byte[4];
        file = new FileInputStream(filePath);
        input = new BufferedInputStream(file);

        decompressData1 = new FileOutputStream(outputFile);



        input .read(NumbersAsBytes,0,4);
        filesize = getvlaue(NumbersAsBytes);

        input .read(NumbersAsBytes,0,4);
        n = getvlaue(NumbersAsBytes);

        input .read(NumbersAsBytes,0,4);
        mappingLen=getvlaue(NumbersAsBytes);



        byte[] dataAsBytes =new byte[n];
        int value;
        StringBuilder chunks;
        int numBytes;

          int size =(n*mappingLen+mappingLen*4);
        while(size>0){
             chunks = new StringBuilder();
            input .read(NumbersAsBytes,0,4);
            numBytes=getvlaue(NumbersAsBytes);
             input .read(NumbersAsBytes,0,4);
            value=getvlaue(NumbersAsBytes);
            input .read(dataAsBytes,0,n);
            for (int j=0;j<numBytes;j++){
                chunks.append(String.valueOf(dataAsBytes[j]));
                chunks.append(" ");
            }
            mapping.put(chunks.toString(),value);
            size=size-(n+4);
        }

        workingClass workingclss = new workingClass();
        trueRoot= workingclss.createTree(mapping);
        workingclss.makeDic(trueRoot,"",dic);


        for(Map.Entry<String, String> entry : dic.entrySet()){
            reversedDic.put(entry.getValue(), entry.getKey());
        }


        byte[] data =new byte[MAX_SIZE_RAED];
        int i =input .read(data,0,MAX_SIZE_RAED);
        while (i != -1) {
            writeData(i,data);
             i =input .read(data,0,MAX_SIZE_RAED);
        }

        decompressData1.close();
        input.close();
    }

    void writeData(int sizeread,byte[] data) throws IOException {
        for(int k=0;k<sizeread;k++){
            short mappedZeroOnes = data[k];
//            String mappedZeroOnes = String.format("%8s", Integer.toBinaryString(data[k] & 0xFF)).replace(' ', '0');
            for(int j=0; j<8;j++){
                if (  ( ( mappedZeroOnes >> (7 - j) ) & 1 ) > 0 ){
                    str+="1";
                }
                else {
                    str+="0";
                }
                if(reversedDic.containsKey(str)){
                    String originalByte = reversedDic.get(str);//"46"
                    String[] slpitter= originalByte.split(" ");
                    for(String b : slpitter){
                        byte originalbyteAsByte=(byte)Integer.parseInt(b);//46
                        if(bytecounter<filesize) {
                            decompressData.add(originalbyteAsByte);
                            bytecounter++;
                        }
                    }
                    str="";
                }
            }
        }

        byte[] writeData= new byte[decompressData.size()];
        for(int m=0;m<decompressData.size();m++){
            writeData[m]=decompressData.get(m);
        }
        decompressData1.write(writeData);
        decompressData=  new ArrayList<>();
    }

    int getvlaue(byte[] bytes){
        int value = 0;
        for (byte b : bytes) {
            value = (value << 8) + (b & 0xFF);
        }
        return  value ;

    }



}
