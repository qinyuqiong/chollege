package com.yuqiong.college.service.edu.controller;


import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.Course;
import com.yuqiong.college.service.edu.entity.CoursePublishVo;
import com.yuqiong.college.service.edu.entity.chapter.ChapterVO;
import com.yuqiong.college.service.edu.entity.chapter.CourseQueryVo;
import com.yuqiong.college.service.edu.query.CourseInfoVo;
import com.yuqiong.college.service.edu.query.CourseWebVo;
import com.yuqiong.college.service.edu.service.ChapterService;
import com.yuqiong.college.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
@Api("课程管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "课程详情")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public ResultData getFrontCourseInfo(@PathVariable String courseId) {
        CourseWebVo courseWebVo = chapterService.getBaseCourseInfo(courseId);
        List<ChapterVO> chapterVideoByCourseId = chapterService.getChapterVideoByCourseId(courseId);
        return ResultData.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoByCourseId);

    }

    @ApiOperation(value = "条件查询带分页查询")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public ResultData getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseQueryVo queryVo) {

        Map<String, Object> map = courseService.getFrontCourseList(page, limit, queryVo);
        return ResultData.ok().data(map);

    }

    @ApiOperation(value = "删除所有课程")
    @DeleteMapping("removeCourse/{courseId}")
    public ResultData removeCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return ResultData.ok();
    }

    /**
     * todo
     *
     * @return
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("getCourseList")
    public ResultData getCourseList() {
        List<Course> list = courseService.list(null);
        return ResultData.ok().data("list", list);
    }

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public ResultData addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return ResultData.ok().data("courseId", id);
    }

    @ApiOperation(value = "根据id查询基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public ResultData getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return ResultData.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation(value = "修改基本信息")
    @PostMapping("updateCourseInfo")
    public ResultData updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return ResultData.ok();
    }

    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public ResultData getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return ResultData.ok().data("publishCourse", coursePublishVo);

    }

    @ApiOperation(value = "修改课程发布")
    @PostMapping("publishCourse/{id}")
    public ResultData publishCourse(@PathVariable String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");

        courseService.updateById(course);
        return ResultData.ok();
    }


}

