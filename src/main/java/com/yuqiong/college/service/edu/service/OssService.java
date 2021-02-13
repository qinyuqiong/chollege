package com.yuqiong.college.service.edu.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/11
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
