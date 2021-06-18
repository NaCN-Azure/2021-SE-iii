package com.heap.coinservice.utils;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//python运行测试的脚本，没有意义
public class PythonTest {
    public static void main(String[] args) throws IOException {
        File file = new File("service/DomainDefault/pokemon.csv");
        List<List<String>> rowList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if(line.contains("形状B")){continue;}
            String[] rowArr = line.split(",");
            List<String> row = Arrays.asList(rowArr);
            rowList.add(row);
        }
    }
}
