package com.heap.coinservice.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTest {
    public static void main(String[] args) {
        try{
            List<String> x = new ArrayList<>();
            File file = new File("service/robot/ques/data/extract.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                if(str.split(" ")[0].equals("皮卡丘")){
                    continue;
                }
                x.add(str+"\n");
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(String a:x) {
                bw.write(a);
            }
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
