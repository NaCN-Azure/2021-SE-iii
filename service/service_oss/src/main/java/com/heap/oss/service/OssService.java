package com.heap.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 车一晗
 * @since 2021-05-12
 */
public interface OssService {
    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    public String uploadFileAvatar(MultipartFile file);
}
