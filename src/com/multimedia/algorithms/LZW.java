package com.multimedia.algorithms;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LZW extends CompressionAlgorithm{

    private HashMap<String,Integer> dictionary;
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
        this.dictionary=new HashMap<>();
        counter=1;
        compressFile(true);
        super.closeFile();
    }

    private void compressDict() {
        super.write(this.dictionary.size()); //First the size of the dictionary.
        for(Map.Entry<String,Integer> entry : this.dictionary.entrySet()){
            super.write(entry.getKey().length()); //size of string.
            super.write(entry.getKey().getBytes(StandardCharsets.UTF_8));
            super.write(entry.getValue());//write code.
        }
    }

    @Override
    public void decompress() {

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
}
