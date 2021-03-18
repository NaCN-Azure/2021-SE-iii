package com.heap.coinservice.controller;

import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.FileService;
import com.heap.commonutils.FileUtil;
import com.heap.commonutils.Result;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return fileService.createGraphByCsv(content,domainId)?Result.ok().message("导入成功"):Result.error().message("导入失败");
    }

    @GetMapping(value="/exportXml/{domainId}",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result exportGraphXML(@PathVariable int domainId,final HttpServletRequest request, final HttpServletResponse response)
            throws FileNotFoundException, SAXException, TransformerConfigurationException {
        return fileService.exportGraphXml(domainId)?Result.ok().message("输出成功"):Result.error().message("输出失败");
    }

    @GetMapping("/download/{domainName}/{type}")
    public Result download(@PathVariable String domainName, @PathVariable int type, final HttpServletRequest request, final HttpServletResponse response){
        OutputStream os = null;
        InputStream is= null;
        String filename=domainName;
        if(type==1){
            filename=filename+".xml";
        }
        try {
            os = response.getOutputStream();
            response.reset();
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes("utf-8"), "iso-8859-1"));
            File f = new File(filename);
            is = new FileInputStream(f);
            if (is == null) {
                return Result.error().message("下载附件失败，请检查文件"+filename+"是否存在");
            }
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return Result.error().message("下载附件失败，请检查文件"+filename+"是否存在");
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
