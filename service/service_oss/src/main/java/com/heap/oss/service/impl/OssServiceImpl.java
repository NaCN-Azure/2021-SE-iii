package com.heap.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.heap.oss.service.OssService;
import com.heap.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        //通过ConstantPropertiesUtils工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String filename = file.getOriginalFilename();

            //在文件名称里添加随机唯一的值，用来实现唯一文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;

            //将文件按照日期分类   2021/01/31/edswsdeqwq01.jpg
                //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
                //拼接
            filename = datePath + "/" + filename;


            //调用oss方法实现上传
            //第二个参数：上传到oss文件路径和文件名称
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient
            ossClient.shutdown();

            //将上传之后的文件路径返回
            //需要将上传到oss的路径手动拼接出来
            //例：https://heap-coin-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
