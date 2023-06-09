package com.multimedia.algorithms;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LZW extends CompressionAlgorithm{

    private HashMap<String,Integer> dictionary;
    private HashMap<Integer,byte[]> dictionaryDecompressor;

    private int counter= 1;
    public LZW(File inputFile){
        super(inputFile);
        dictionary=new HashMap<>();
    }
    @Override
    public void compress() {
        super.setOutputFile(".lzw");
        super.openFile();
        compressFile(false);
        super.openFile();
        compressDict();
        System.out.println(this.dictionary.size());
        this.dictionary=new HashMap<>();
        counter=1;
        compressFile(true);
        super.closeFile();
        System.out.println("Done");
    }

    private void compressDict() {
        super.write(this.dictionary.size()); //First the size of the dictionary.
        for(Map.Entry<String,Integer> entry : this.dictionary.entrySet()){
            super.writeByte(entry.getKey().length()); //size of string.
            super.write(entry.getKey());
            super.write(entry.getValue());//write code.
        }
    }

    @Override
    public void decompress() {
        super.setOutputFile();
        super.openFile();
        setDictionary();
        writeOutputFile();
        super.closeFile();
        System.out.println("Done");
    }
    private void compressFile(boolean manageWrite){
        String s= "";
        s= (char)super.readByte()+"";

        while (!super.isEOF()){
            if(!this.dictionary.containsKey(s)){
                this.dictionary.put(s,counter);
                counter++;
            }
            char c= (char)super.readByte();
            if (this.dictionary.containsKey(s+c))
                s+=c;

            else{
                if(manageWrite)
                    super.write(this.dictionary.get(s));
                this.dictionary.put(s+c,counter);
                counter++;
                s=""+c;
            }
        }
        if (manageWrite)
            super.write(this.dictionary.get(s));
    }

    private void setDictionary(){
        this.dictionaryDecompressor=new HashMap<>();
        int dictionarySize= super.readInt();
        for(int i= 0;i<dictionarySize;i++){
            int numberOfBytesToRead= super.readByte();
            byte[] bytes=super.readByte(numberOfBytesToRead);
            int code= super.readInt();
            this.dictionaryDecompressor.put(code,bytes);
        }
    }

    private void writeOutputFile(){
        while(!super.isEOF()){
            int current= super.readInt();
            super.write(this.dictionaryDecompressor.get(current));
        }
    }
}
