package com.yuqiong.college.service.edu.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yuqiong.college.common.utils.ConstantPropertiesUtils;
import com.yuqiong.college.service.edu.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/11
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //文件路径
        String originalFilename = file.getOriginalFilename();
        //文件名称添加唯一值
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        //把文件按日期分类
        String datePath = new DateTime().toString("yyyy/MM/dd");
        originalFilename = datePath + "/" + s + originalFilename;

        try {
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //调用oss上传
            ossClient.putObject(bucketName, originalFilename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传的路径返回
            String url = "https://" + bucketName + "." + endpoint + "/" + originalFilename;

            return url;
        } catch (IOException e) {
            return null;
        }


    }
}
