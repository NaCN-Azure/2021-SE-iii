package com.heap.coinservice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.entity.Relationship;
import com.heap.coinservice.mapper.EntityMapper;
import com.heap.coinservice.mapper.QuestionMapper;
import com.heap.coinservice.service.*;
import com.heap.coinservice.utils.FileUtil;
import com.heap.commonutils.DefaultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 纳思彧
 * @since 2021-06-01
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    EntityMapper entityMapper;

    @Override
    public void clean(List<Entity> entity,List<Relationship> relation){
        List<String> s = new ArrayList<>();
        for(Entity node:entity){s.add(node.getName());s.add(node.getType());}
        for(Relationship link:relation){s.add(link.getName());}
        try{
            List<String> x = new ArrayList<>();
            File file = new File("service/robot/ques/data/extract.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                if(s.contains(str.split(" ")[0])){
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

    @Override
    public void AddDict(String x,String type){
        try{
            String data = x+" 15 "+type+"\n";
            File file = new File("service/robot/ques/data/extract.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public String getAnswer(int domainId,int commands,List<String> members){
        if(domainId ==-1) return "还没选中图谱哦~";
        try {
            int AnswerType = commands;
            String finalAnswer = "";
            switch (AnswerType) {
                case 0:
                    finalAnswer = getNodesNear(domainId, members.get(0));
                    break;
                case 1:
                    finalAnswer = getNodeDescription(domainId,members.get(0));
                    break;
                case 2:
                    finalAnswer = getRelationWithTwoNodes(domainId,members.get(0),members.get(1));
                    break;
                case 3:
                    finalAnswer = getTargetNodes(domainId,members.get(0),members.get(1));
                    break;
                case 4:
                    finalAnswer = getTypesNodes(domainId,members.get(0));
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
        if(name1.equals(name2)){
            return getNodeDescription(domainId,name1);
        }
        Entity entity = entityMapper.findByName(name2,domainId);
        if(entity==null){
            return getNodesNear(domainId,name1);
        }
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

    private String getTypesNodes(int domainId,String type){
        Entity entity = entityMapper.findByName(type,domainId);
        if(entity!=null){
            return getNodesNear(domainId,type);
        }
        List<String> results = questionMapper.getTypeNodes(type,domainId);
        String finalAnswer ="";
        for(String r:results){
            finalAnswer=finalAnswer+r+",";
        }
        finalAnswer = finalAnswer.substring(0,finalAnswer.length()-1)+"。";
        return finalAnswer;
    }

}
