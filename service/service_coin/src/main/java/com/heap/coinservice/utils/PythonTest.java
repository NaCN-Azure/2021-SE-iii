package com.heap.coinservice.utils;
import java.io.*;

//python运行测试的脚本，没有意义
public class PythonTest {
    public static void main(String[] args) {
        try{
            String question = "电类型的宝可梦有哪些";
            String exe = "python";
            String command = "service/robot/ques/unit.py";
            String[] cmdArr = new String[]{exe,command,question};
            String wholeCommand="python service/robot/ques/unit.py "+question;
            Process process = Runtime.getRuntime().exec(wholeCommand);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                System.out.println(line);
            }
            in.close();
            int result=process.waitFor();
            System.out.println("结果:"+result);
        }catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
