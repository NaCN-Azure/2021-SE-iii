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

    @GetMapping("/ask/{question}")
    public Result AskQuestion(@PathVariable String question){
        List<String> commands = questionService.dealByPython(question);
        System.out.println("解析问题代号:" + commands.get(0));
        String answer = questionService.getAnswer(DefaultUtil.NOW_DOMAIN_ID, commands);
        String result = question + ":\n" + answer;
        System.out.println(result);
        return Result.ok().data("result", result);
    }
}
