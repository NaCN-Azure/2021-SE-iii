package com.heap.coinservice.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTest {
    public static void main(String[] args) {
        try{
            String path = "service/DomainDefault/";
            String filename ="";
            filename="pokemon.csv";
            File file = new File(path+filename);
            List<List<String>> rowList = new ArrayList<List<String>>();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] rowArr = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                List<String> row = Arrays.asList(rowArr);
                rowList.add(row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
