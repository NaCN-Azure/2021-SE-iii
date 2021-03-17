package com.heap.coinservice.controller;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.FileService;
import com.heap.commonutils.FileUtil;
import com.heap.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/coinservice/file")
@CrossOrigin
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    DomainService domainService;

    @PostMapping("/getCsv")
    public Result getCsvFile(@RequestParam(value="file",required = true) MultipartFile file) throws IOException {
        List<List<String>> content=FileUtil.readCsv(file);
        Domain csvDomain=Domain.builder().name(file.getOriginalFilename()).build();
        int domainId=domainService.createDomain(csvDomain);
        return Result.ok().data("csvGraph",fileService.createGraphByCsv(content,domainId));
    }

    @GetMapping(value="/exportXml/{domainId}",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result exportGraphXML(@PathVariable int domainId) throws FileNotFoundException, SAXException, TransformerConfigurationException {
        return fileService.exportGraphXml(domainId)?Result.ok().message("输出成功"):Result.error().message("输出失败");
    }
}
