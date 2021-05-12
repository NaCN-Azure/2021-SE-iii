package com.heap.oss.controller;

import com.heap.oss.service.OssService;
import com.heap.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ossservice/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public Result uploadOssFile(MultipartFile file){
        //获取上传文件
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);

        return Result.ok().data("url", url);
    }

}
