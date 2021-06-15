package com.heap.coinservice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.QuestionMapper;
import com.heap.coinservice.service.*;
import com.heap.coinservice.utils.FileUtil;
import com.heap.commonutils.DefaultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 车一晗
 * @since 2021-03-07
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<String> dealByPython(String question){
        List<String> r = new ArrayList<>();
        try{
            String exe = "python";
            String command = "service/robot/ques/unit.py";//改路径
            String[] cmdArr = new String[]{exe,command,question};
            Process process = Runtime.getRuntime().exec(cmdArr);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                r.add(line);
            }
            in.close();
            int result = process.waitFor();
            System.out.println("执行结果："+result);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public String getAnswer(int domainId,List<String> commands){
        try {
            int AnswerType = Integer.parseInt(commands.get(0));
            String finalAnswer = "";
            switch (AnswerType) {
                case 0:
                    finalAnswer = getNodesNear(domainId, commands.get(1));
                    break;
                case 1:
                    finalAnswer = "dd";
                    break;
                default:
                    finalAnswer = "爷不知道";
            }
            return finalAnswer;
        }
        catch (Exception e){
            return "爷不知道";
        }
    }

    private String getNodesNear(int domainId,String nodeName){
        List<Relationship> relationships = questionMapper.getNodesNear(domainId,nodeName);
        String finalAnswer = nodeName+"的临近节点分别为：";
        for(Relationship r:relationships){
            finalAnswer=finalAnswer+r.getEndEntity().getName()+",";
        }
        finalAnswer = finalAnswer.substring(0,finalAnswer.length()-1)+"。";
        return finalAnswer;
    }

}
