package com.multimedia;

import com.multimedia.algorithms.CompressionAlgorithm;
import com.multimedia.algorithms.LZW;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        CompressionAlgorithm compressionAlgorithm=new LZW(new File("Assignment1.pdf.lzw"));
//        compressionAlgorithm.compress();
        compressionAlgorithm.decompress();
    }
}
