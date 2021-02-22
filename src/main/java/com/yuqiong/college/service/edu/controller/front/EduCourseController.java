package com.yuqiong.college.service.edu.controller.front;

import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.Course;
import com.yuqiong.college.service.edu.entity.Teacher;
import com.yuqiong.college.service.edu.service.CourseService;
import com.yuqiong.college.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/22
 */
@RestController
@CrossOrigin
@Api("客户")
@RequestMapping("/eduservice/indexfront")
public class EduCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "查询8个热门课程，和4个名师")
    @GetMapping("index")
    public ResultData index() {

        List<Course> list = courseService.getList();
        List<Teacher> list1 = teacherService.getList();

        return ResultData.ok().data("eduList", list).data("teacherList", list1);
    }
}
