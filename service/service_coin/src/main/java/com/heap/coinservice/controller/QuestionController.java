package com.heap.coinservice.controller;

import com.heap.coinservice.service.QuestionService;
import com.heap.commonutils.DefaultUtil;
import com.heap.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coinservice/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/ask/{commands}")
    public Result askQuestion(@PathVariable int commands, @RequestBody List<String> members){
        //List<String> commands = questionService.dealByPython(question);
        System.out.println("解析问题代号:" + commands);
        System.out.println(members);
        String answer = questionService.getAnswer(DefaultUtil.NOW_DOMAIN_ID, commands,members);
        System.out.println(answer);
//        return Result.ok().data("result", result);
        return Result.ok().data("result", answer);
    }

}
