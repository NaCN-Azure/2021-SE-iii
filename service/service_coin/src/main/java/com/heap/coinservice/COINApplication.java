package com.heap.coinservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.heap"})   //从给出的包开始扫描
public class COINApplication {
    public static void main(String[] args) {
        SpringApplication.run(COINApplication.class, args);
    }
}
