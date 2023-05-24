package com.multimedia.algorithms;

import java.io.File;

import com.multimedia.algorithms.CompressionAlgorithm;
import com.multimedia.algorithms.Algorithm.HuffmanMain;

public class Huffman extends CompressionAlgorithm {

    public Huffman(File inputFile) {
        super(inputFile);
    }

    @Override
    public void compress() {
        try {
            super.setOutputFile(".hc");
            HuffmanMain.compress(super.getInputFile(), super.getOutputFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decompress() {
        try{

            super.setOutputFile();
            HuffmanMain.decompress(super.getInputFile(), super.getOutputFile());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
