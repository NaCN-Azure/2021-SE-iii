package com.heap.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传头像到oss
    public String uploadFileAvatar(MultipartFile file);
}
