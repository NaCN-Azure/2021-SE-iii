package com.heap.commonutils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    public static List<List<String>> readCsv(MultipartFile file) throws IOException {
        List<List<String>> rowList = new ArrayList<List<String>>();
        String charset = "utf-8";
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),charset));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] rowArr = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            List<String> row = Arrays.asList(rowArr);
            rowList.add(row);
        }
        return rowList;
    }
}
