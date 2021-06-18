package com.heap.coinservice.service.impl;

import com.heap.coinservice.entity.Entity;
import com.heap.coinservice.service.EntityService;
import com.heap.coinservice.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceImplTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    EntityService entityService;

    @Test
    void clean() {
        try {
            List<String> x = new ArrayList<>();
            File file = new File("service/robot/ques/data/extract.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                x.add(str);
            }
            int byCheck = x.size();
//            List<Entity> entities = entityService.getNodeByDomainId(35);
//            questionService.clean(entities,null);
            List<String> x2 = new ArrayList<>();
            while ((str = in.readLine()) != null) {
                x2.add(str);
            }
            int nowCheck = x2.size();
//            assertEquals(byCheck,nowCheck+entities.size());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void addDict() {
//        questionService.AddDict("皮卡丘","nr");
        try {
            File file = new File("service/robot/ques/data/extract.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str,result = null;
            while ((str = in.readLine()) != null) {
                result =str;
            }
//            assertEquals("皮卡丘 15 nr",result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void dealByPython() {
//        List<String> results = questionService.dealByPython("皮卡丘的信息");
//        assertNotSame(results,null);
    }

    @Test
    void getAnswer() {
        List<String> answers = new ArrayList<>();
        List<String> asks = new ArrayList<>();
        asks.add("羧酸盐");
//        String a1 = questionService.getAnswer(35,0,asks);
        asks.clear();asks.add("成龙");
//        String a2 = questionService.getAnswer(34,1,asks);
        asks.clear();asks.add("关都地区");asks.add("皮卡丘");
//        String a3 = questionService.getAnswer(33,2,asks);
        asks.clear();asks.add("雷丘");asks.add("子代");
//        String a4 = questionService.getAnswer(33,3,asks);
        asks.clear();asks.add("超能");
//        String a5 = questionService.getAnswer(33,4,asks);
        List<String> result = new ArrayList<>();
        result.add("羧酸");
        result.add("我得到的信息如下：\n节点名称：成龙\n节点类型：人\n节点描述:\n");
        result.add("分布");
        result.add("皮卡丘");
        result.add("凯西,超梦");
//        answers.add(a1);answers.add(a2);answers.add(a3);answers.add(a4);answers.add(a5);
//        assertEquals(answers,result);
    }
}
