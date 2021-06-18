package com.heap.coinservice.utils;

import com.heap.coinservice.entity.Relationship;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//本地测试使用的类，没有意义
public class FileTest {
    public static void main(String[] args) throws IOException {
        String filename ="AAA"+".csv";
        new File(filename).createNewFile();
        try{
            File file = new File(filename);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i=0;i<3;i++){
                bw.write("S"+','+"S"+','+"A"+','+
                        "F"+','+"F"+','+
                        "Q"+','+"Q"+'\n');
                bw.flush();
            }
            bw.close();
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
