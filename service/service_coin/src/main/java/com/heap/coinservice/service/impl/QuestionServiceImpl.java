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
                    finalAnswer = getNodeDescription(domainId,commands.get(1));
                    break;
                case 2:
                    finalAnswer = getRelationWithTwoNodes(domainId,commands.get(1),commands.get(2));
                    break;
                case 3:
                    finalAnswer = getTargetNodes(domainId,commands.get(1),commands.get(2));
                    break;
                default:
                    finalAnswer = "我不知道哎~或许你可以换个问法。";
            }
            return finalAnswer;
        }
        catch (Exception e){
            return "我不知道哎~或许你可以换个问法。";
        }
    }

    private String getNodesNear(int domainId,String nodeName){
        List<String> results = questionMapper.getNodesNear(domainId,nodeName);
        String finalAnswer ="";
        for(String r:results){
            finalAnswer=finalAnswer+r+",";
        }
        finalAnswer = finalAnswer.substring(0,finalAnswer.length()-1)+"。";
        return finalAnswer;
    }

    private String getNodeDescription(int domainId,String nodeName){
        String finalAnswer ="我得到的信息如下：\n";
        Entity entity= questionMapper.getNodeDesciption(nodeName,domainId);
        finalAnswer = finalAnswer + "节点名称："+entity.getName()+"\n";
        finalAnswer = finalAnswer + "节点类型："+entity.getType()+"\n";
        finalAnswer = finalAnswer + "节点描述："+entity.getDescription()+"\n";
        return finalAnswer;
    }

    private String getRelationWithTwoNodes(int domainId,String name1,String name2){
        return questionMapper.getLinkWithTwoNodes(domainId,name1,name2);
    }

    private String getTargetNodes(int domainId,String entityName,String linkName){
        List<String> names = questionMapper.getTargetNodes(domainId,entityName,linkName);
        String finalAnswer ="";
        for(String name:names){
            finalAnswer=finalAnswer+name+",";
        }
        finalAnswer = finalAnswer.substring(0,finalAnswer.length()-1)+"。";
        return finalAnswer;
    }

}
