package com.heap.coinservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.heap.coinservice.entity.Domain;
import com.heap.coinservice.service.DomainService;
import com.heap.coinservice.service.FileService;
import com.heap.coinservice.utils.FileUtil;
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
    private FileService fileService;

    @Autowired
    private DomainService domainService;

    @PostMapping("/getCsv/{user_id}")
    public Result getCsvFile(@RequestParam(value="file", required = true) MultipartFile file,String user_id) throws IOException {
        List<List<String>> content = FileUtil.readCsv(file);
        Domain csvDomain = Domain.builder().name(file.getOriginalFilename()).userId(user_id).build();
        int domainId = domainService.createDomain(csvDomain);
        return fileService.createGraphByCsv(content, domainId) ? Result.ok().message("导入成功") : Result.error().message("导入失败");
    }

    @PostMapping("/getJsonCompany")
    public Result getJsonCompany (@RequestParam(value="file", required = true) MultipartFile file) throws IOException{
        JSONArray jsonArray = FileUtil.readJsonCompany(file);
        Domain jsonDomain = Domain.builder().name(file.getOriginalFilename()).build();
        int domainId = domainService.createDomain(jsonDomain);
        return fileService.createJsonCompany(jsonArray,domainId)?
                Result.ok().message("导入成功") : Result.error().message("导入失败");

    }

    @GetMapping(value="/exportXml/{domainId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result exportGraphXML(@PathVariable int domainId, final HttpServletRequest request, final HttpServletResponse response)
            throws FileNotFoundException, SAXException, TransformerConfigurationException {
        return fileService.exportGraphXml(domainId) ? Result.ok().message("输出成功") : Result.error().message("输出失败");
    }

    @GetMapping("/download/{domainName}/{type}")
    public Result download(@PathVariable String domainName, @PathVariable int type, final HttpServletRequest request, final HttpServletResponse response){
        OutputStream os = null;
        InputStream is = null;
        String filename = FileUtil.getFileName(domainName,type);
        try {
            os = response.getOutputStream();
            response.reset();
            response.setContentType("application/x-download;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            //response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            // 解决预请求（发送2次请求），此问题也可在 nginx 中作相似设置解决。
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Cache-Control,Pragma,Content-Type,Token, Content-Type");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes("utf-8"), "iso-8859-1"));
            File f = new File(filename);
            is = new FileInputStream(f);
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return Result.error().message("下载附件失败，请检查文件" + filename + "是否存在");
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
        return Result.ok().message("下载成功");
    }

    @PostMapping("/deleteCache/{domainName}/{type}")
    public Result deleteCache(@PathVariable String domainName, @PathVariable int type){
        return fileService.deleteFile(domainName,type) ? Result.ok().message("清除缓存") : Result.error().message("清除失败");
    }
}
