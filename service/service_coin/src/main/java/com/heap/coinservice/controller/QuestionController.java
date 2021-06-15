package com.heap.coinservice.controller;

import com.heap.coinservice.service.QuestionService;
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

    @PostMapping("/ask/{domainId}/{question}")
    public Result AskQuestion(@PathVariable int domainId,@PathVariable String question){
        List<String> commands = questionService.dealByPython(question);
        String answer = questionService.getAnswer(domainId,commands);
        return Result.ok().message(question+":"+answer);
    }
}
