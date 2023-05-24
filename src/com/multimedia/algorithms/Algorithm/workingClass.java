package com.multimedia.algorithms.Algorithm;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class workingClass {
    static final int MAX_SIZE_RAED = 10000;
     // key chuck value bits
    Map<String, String> dic = new HashMap<>();
    myTreeNode trueRoot =null;
    ArrayList<Byte> compressData = new ArrayList<>();
    StringBuilder myByte=new StringBuilder();
    String CompressPath ;
    FileOutputStream writter ;


    public void working(Map<String, Integer>  arrCharFreq, int n, File filePath , File outputFile) throws IOException {
        myTreeNode root = createTree(arrCharFreq);
        makeDic(root,"",dic);
        writter = new FileOutputStream(outputFile) ;

        appendwrite(intToBytes((int)filePath.length()));//4bytes
        appendwrite(intToBytes(n));//4bytes
        appendwrite(intToBytes(arrCharFreq.size()));//4bytes
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        for (Map.Entry<String,Integer> entry : arrCharFreq.entrySet()){
            int i=0;
            byte[] data= new byte[n];
            byte[] freq =intToBytes(entry.getValue());
            String[] spilter = entry.getKey().split(" ");
            if(spilter.length<n){
                byte[] numBytes= intToBytes(spilter.length);//4bytes
                appendwrite(numBytes);
                for (String b : spilter) {
                    byte byteString = (byte) Integer.parseInt(b);
                    data[i]=byteString;
                    i++;
                }
                while (i<n){
                    data[i]=0;
                    i++;
                }
            }
            else {
                byte[] numBytes= intToBytes(spilter.length);//4bytes
                appendwrite(numBytes);
                for (String b : spilter) {
                    byte byteString = (byte) Integer.parseInt(b);
                    data[i] = byteString;
                    i++;
                }
            }
            appendwrite(freq);
            appendwrite(data);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////

        BytesToArray(filePath,n);
        if (!myByte.equals("")) {
            while (myByte.length() != 8) {
                myByte.append('0');
            }
            short x = Short.parseShort(String.valueOf(myByte), 2);
            compressData.add((byte) x);
        }
        if(compressData.size()>0){
            appendwrite(convertArraylistArray());
        }



    }



    public  void comperssing(byte[] bytes, int n, int size) throws IOException {
        StringBuilder chunks = new StringBuilder();

        for (int i = 0; i < size; i = i + n) {
            for (int j = i; j < i + n && j < size; j++) {
                chunks.append(String.valueOf(bytes[j]));
                chunks.append(" ");
            }
            String zeroOnes=dic.get(chunks.toString());
            for(int k=0;k<zeroOnes.length();k++){
                myByte.append(zeroOnes.charAt(k));
                if(myByte.length()==8){
                    short x = Short.parseShort(String.valueOf(myByte),2);
                    compressData.add((byte)x);
                    myByte=new StringBuilder();
                }
            }
            chunks = new StringBuilder();
        }
        if(compressData.size()>MAX_SIZE_RAED){
            appendwrite(convertArraylistArray());
        }
    }


    public myTreeNode createTree (Map<String, Integer> arrCharFreq){
        int size = arrCharFreq.size();
        myTreeNode root ;
        PriorityQueue<myTreeNode> myQueue= new PriorityQueue<>();
        for (Map.Entry<String,Integer> entry : arrCharFreq.entrySet()){
            String str = entry.getKey();
            Integer freq =  entry.getValue();
            myTreeNode node = new myTreeNode(str, freq);
            myQueue.add(node);
        }
        for (int i=0;i<size-1;i++){
            myTreeNode left = myQueue.poll();
            myTreeNode right = myQueue.poll();
            int zfreq = left.freq + right.freq;
            myTreeNode z = new myTreeNode(" ", zfreq);
            z.setLeft(left);
            z.setRight(right);
            myQueue.add(z);
        }
        root = myQueue.peek();
        trueRoot =root;
        return root;
    }


    public void makeDic(myTreeNode root , String mappedString ,Map<String, String> dic ){
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (mappedString.length() > 0){
                dic.put(root.Chunck,  mappedString );
            }
            else{
                dic.put(root.Chunck,  "1");
            }
        }
        makeDic(root.left, mappedString + '0',dic);
        makeDic(root.right, mappedString + '1',dic);
    }


    private void BytesToArray(File filePath, int n) throws IOException {
        byte[] dataAsBytes= new byte[MAX_SIZE_RAED*n];
        FileInputStream file = new FileInputStream(filePath);
        BufferedInputStream input = new BufferedInputStream(file);
        int i = input .read(dataAsBytes,0,MAX_SIZE_RAED);
        while (i != -1) {
            comperssing (dataAsBytes,n,i);
            i = input .read(dataAsBytes,0,MAX_SIZE_RAED);

        }
        input.close();
    }


    static class myTreeNode implements Comparable<myTreeNode> {
        String Chunck;
        int freq;
        myTreeNode left=null;
        myTreeNode right=null;

        public myTreeNode(String String, int freq) {
            this.Chunck = String;
            this.freq = freq;
        }

        public void setLeft(myTreeNode left) {
            this.left = left;
        }

        public void setRight(myTreeNode right) {
            this.right = right;
        }

        @Override
        public int compareTo(myTreeNode x) {
            return this.freq - x.freq;
        }
    }

    public void appendwrite(byte[] bytes ) throws IOException {
            writter.write(bytes);

    }


    public byte[] intToBytes( int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    byte[] convertArraylistArray(){
        int n = compressData.size();
        byte[] out = new byte[n];
        for (int i = 0; i < n; i++) {
            out[i] = compressData.get(i);
        }
        compressData= new ArrayList<>();
        return out;

    }
}
