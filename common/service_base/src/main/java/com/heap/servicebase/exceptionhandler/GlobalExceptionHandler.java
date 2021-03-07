package com.heap.servicebase.exceptionhandler;

import com.heap.commonutils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常会执行这个方法
    @ExceptionHandler(Exception.class)  //全局异常处理
    @ResponseBody  //为了返回数据
    public Result error(Exception e) {   //在pom.xml里引入了common_utils的依赖
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)  //全局异常处理
    @ResponseBody  //为了返回数据
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(COINException.class)  //全局异常处理
    @ResponseBody  //为了返回数据
    public Result error(COINException e) {
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}
