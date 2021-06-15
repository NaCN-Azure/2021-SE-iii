package com.heap.coinservice.utils;

import com.heap.commonutils.DefaultUtil;

import java.io.*;

public class PythonTest {
    public static void main(String[] args) {
        try{
            String question = "电系类型的宝可梦有哪些";
            String exe = "python";
            String command = "service/robot/ques/unit.py";
            String[] cmdArr = new String[]{exe,command,question};
            Process process = Runtime.getRuntime().exec(cmdArr);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                System.out.println(line);
            }
            in.close();
            int result = process.waitFor();
            System.out.println("执行结果："+result);
        }catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
