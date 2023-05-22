package Algorithm;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter c for Compressing or d for Decompressing ");
        String a= sc.nextLine();
        if (a.equals("c")){
            System.out.println("Enter File Path ");
            String path=sc.nextLine();
            System.out.println("Enter number of bytes ");
            String numberOfBytes =sc.nextLine();
            long startTime = System.currentTimeMillis();
            calculateFreq x = new calculateFreq();
            workingClass y= new workingClass();
            File file = new File(path);
            int n =Integer.parseInt(numberOfBytes);
            Map<String, Integer>  o= x.convertIntoTwoArrays(file, n);
            y.working(o,n,file,path);
            long endTime = System.currentTimeMillis();
            float time=(endTime-startTime)/ 1000F;
            System.out.println("compress Time : "+time + " sec");
            String wholePath = path.substring(0, path.lastIndexOf("\\")+1);
            wholePath+=(n+".");
            wholePath+=path.substring(path.lastIndexOf("\\")+1);
            wholePath+=".hc";
            File compressedFile = new File(wholePath);
            float ratio =((float)compressedFile.length()/(float)file.length())*100;
            System.out.println("CompressRatio : "+ratio );

        }
        else if (a.equals("d")){
            System.out.println("Enter File Path  ");
            String path=sc.nextLine();
            long startTime = System.currentTimeMillis();
            decomperssData dc = new decomperssData();
            File file = new File(path);
            dc.decompress(file,path);
            long endTime = System.currentTimeMillis();
            float time=(endTime-startTime)/ 1000F;
            System.out.println("Decompress Time : "+time+" sec");
        }
        else {
            System.out.println("invalid number of args");
        }
    }

}
