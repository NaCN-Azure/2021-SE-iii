package com.heap.coinservice.controller;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.FileService;
import com.heap.commonutils.FileUtil;
import com.heap.commonutils.Result;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/coinservice/file")
@CrossOrigin
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    DomainService domainService;

    @PostMapping("getCsv")
    public Result getCsvFile(@RequestParam(value="file",required = true) MultipartFile file) throws IOException {
        List<List<String>> content=FileUtil.readCsv(file);
        Domain csvDomain=Domain.builder().name(file.getOriginalFilename()).build();
        domainService.createDomain(csvDomain);
        return Result.ok().data("csvGraph",fileService.createGraphByCsv(content,csvDomain.getId()));
    }
}
