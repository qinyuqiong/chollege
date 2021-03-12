package com.yuqiong.college.service.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.Course;
import com.yuqiong.college.service.edu.entity.Teacher;
import com.yuqiong.college.service.edu.query.TeacherQuery;
import com.yuqiong.college.service.edu.service.CourseService;
import com.yuqiong.college.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-01
 */
@Api("讲师模块")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "显示讲师详情")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public ResultData getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        Teacher byId = teacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        List<Course> list = courseService.list(queryWrapper);
        return ResultData.ok().data("teacher", byId).data("courseList", list);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public ResultData getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> teacherMap = teacherService.getTeacherFrontList(teacherPage);
        return ResultData.ok().data(teacherMap);
    }

    @ApiOperation(value = "根据id修改")
    @PostMapping("updateTeacher")
    public ResultData updateTeacher(@ApiParam(name = "teacher", value = "讲师", required = true) @RequestBody Teacher teacher) {
        boolean update = teacherService.updateById(teacher);
        if (update == false) {
            return ResultData.error();
        }
        return ResultData.ok();
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("getTeacher/{id}")
    public ResultData getTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable("id") Long id) {
        Teacher byId = teacherService.getById(id);
        return ResultData.ok().data("teacher", byId);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public ResultData addTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save == true) {
            return ResultData.ok();
        }
        return ResultData.error();
    }

    @ApiOperation(value = "分页查询讲师列表")
    @PostMapping("{page}/{limit}")
    public ResultData pageList(
            @ApiParam(name = "page", value = "第几页", required = true) @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "每页的个数", required = true) @PathVariable("limit") Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false) @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> teacherPage = new Page<Teacher>(page, limit);
        teacherService.pageQuery(teacherPage, teacherQuery);
        long total = teacherPage.getTotal();//总数据
        List<Teacher> records = teacherPage.getRecords();//当前页的数据
        return ResultData.ok().data("records", records).data("total", total);
    }

    /**
     * 讲师删除
     *
     * @param id PathVariable：路径变量
     * @return
     */
    @ApiOperation(value = "根据讲师id删除")
    @DeleteMapping("/{id}")
    public ResultData removeById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable("id") String id) {
        teacherService.removeById(id);
        return ResultData.ok();
    }

    /**
     * 查询所有讲师
     *
     * @return
     */
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("findAll")
    public ResultData findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return ResultData.ok().data("items", list);
    }


}

