package com.yuqiong.college.service.edu.controller;


import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.subject.OneSubject;
import com.yuqiong.college.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-12
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
@Api("课程科目")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "excel课程批量导入")
    @PostMapping("addSubject")
    public ResultData addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return ResultData.ok();
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("getAllSubject")
    public ResultData getAllSubject() {
        List<OneSubject> list = subjectService.getALLOneTweSubject();
        return ResultData.ok().data("list", list);
    }

}

