package com.yuqiong.college.service.edu.controller;

import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/11
 */
@RestController
@CrossOrigin
@Api("上传文件")
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation("上传头像")
    @PostMapping("upload")
    public ResultData uploadOssFile(@RequestParam("file") MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return ResultData.ok().data("url", url);
    }
}
