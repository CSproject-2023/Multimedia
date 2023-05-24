package com.multimedia.algorithms.Algorithm;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class HuffmanMain {


    public static void compress(File inputFile, File outputFile) throws Exception{
            long startTime = System.currentTimeMillis();
            calculateFreq x = new calculateFreq();
            workingClass y= new workingClass();
            File file = inputFile;
            int n =512;
            Map<String, Integer>  o= x.convertIntoTwoArrays(file, n);
            y.working(o,n,file,outputFile);
            long endTime = System.currentTimeMillis();
            float time=(endTime-startTime)/ 1000F;
            System.out.println("compress Time : "+time + " sec");
    }

    public static void decompress(File inputFile,File outputFile) throws Exception{
            long startTime = System.currentTimeMillis();
            decomperssData dc = new decomperssData();
            File file = inputFile;
            dc.decompress(file,outputFile);
            long endTime = System.currentTimeMillis();
            float time=(endTime-startTime)/ 1000F;
            System.out.println("Decompress Time : "+time+" sec");
    }

}
