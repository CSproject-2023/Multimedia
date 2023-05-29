package com.multimedia.algorithms;
import java.io.*;

public abstract class CompressionAlgorithm {
    private File inputFile;
    private File outputFile;
    private DataInputStream inputStream;
    private DataOutputStream writer;

    public CompressionAlgorithm(File inputFile){
        this.inputFile=inputFile;
    }

    protected File getInputFile(){
        return  this.inputFile;
    }
    protected File getOutputFile(){
        return this.outputFile;
    }

    protected void setOutputFile(String extension){
        this.outputFile=new File(inputFile.getPath()+extension);
//        System.out.println(file);
    }

    /**
     * Removes last extension only.
     */
    protected void setOutputFile(){
        String[] extensionRemover= inputFile.getPath().split("[.]");
        System.out.println(inputFile);
        this.outputFile=new File(extensionRemover[0]+"."+extensionRemover[1]);
        System.out.println(outputFile);
    }

    protected void openFile(){
        try {
            writer = new DataOutputStream(new FileOutputStream(this.outputFile));
            inputStream= new DataInputStream(new FileInputStream(this.inputFile));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected boolean isEOF(){
        try {
            return this.inputStream.available()==0;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected byte[] readByte(int length){
        try{
            return this.inputStream.readNBytes(length);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    protected byte readByte(){
        try{
            return this.inputStream.readNBytes(1)[0];
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    protected int readInt(){
        try{
            return this.inputStream.readInt();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected void write(int code){
        try {
            this.writer.writeInt(code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void writeByte(int length){
        try {
            if(length > Byte.MAX_VALUE)
                throw new RuntimeException("Failed to compress, length = "+length);
            writer.writeByte(length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void write(String bytes){
        try {
            writer.writeBytes(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void write(byte[] bytes){
        try {
            writer.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void closeFile(){
        try {
            writer.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public abstract void compress();
    public abstract  void decompress();

    public double getCompressionRation(){
        return outputFile.length()/inputFile.length();

    }

}
