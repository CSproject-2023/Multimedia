package com.multimedia.algorithms;
import java.io.*;

public abstract class CompressionAlgorithm {
    private File inputFile;
    private File outputFile;
    private FileInputStream inputStream;
    private FileOutputStream writer;

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
    protected void openFile(){
        try {
            writer = new FileOutputStream(this.outputFile);
            inputStream= new FileInputStream(this.inputFile);
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
    protected byte readByte(){
        try{
            return this.inputStream.readNBytes(1)[0];
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected void write(int code){
        try {
            writer.write(code);
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

}
