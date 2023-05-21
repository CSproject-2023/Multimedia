package com.multimedia;

import com.multimedia.algorithms.CompressionAlgorithm;
import com.multimedia.algorithms.LZW;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        CompressionAlgorithm compressionAlgorithm=new LZW(new File("test.txt"));
        compressionAlgorithm.compress();
    }
}
