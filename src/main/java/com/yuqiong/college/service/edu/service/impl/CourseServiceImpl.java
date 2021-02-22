package com.yuqiong.college.service.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.common.handler.GuliException;
import com.yuqiong.college.service.edu.entity.Course;
import com.yuqiong.college.service.edu.entity.CourseDescription;
import com.yuqiong.college.service.edu.entity.CoursePublishVo;
import com.yuqiong.college.service.edu.mapper.CourseMapper;
import com.yuqiong.college.service.edu.query.CourseInfoVo;
import com.yuqiong.college.service.edu.service.ChapterService;
import com.yuqiong.college.service.edu.service.CourseDescriptionService;
import com.yuqiong.college.service.edu.service.CourseService;
import com.yuqiong.college.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean save = this.save(course);
        String cid = course.getId();

        CourseDescription description = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        description.setId(cid);
        boolean save1 = courseDescriptionService.save(description);

        if (save == false || save1 == false) {
            throw new GuliException(20001, "添加失败");
        }

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        Course course = baseMapper.selectById(courseId);
        CourseDescription byId = courseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(course, courseInfoVo);
        courseInfoVo.setDescription(byId.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int i = baseMapper.updateById(course);
        CourseDescription description = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        boolean b = courseDescriptionService.updateById(description);
        if (i == 0 && b == false) {
            throw new GuliException(20001, "修改失败");
        }

    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        //删小节
        videoService.removeVideoByCourseId(courseId);

        //删章节
        chapterService.removeChapterByCourseId(courseId);

        //删描述
        courseDescriptionService.removeById(courseId);

        //删课程
        int i = baseMapper.deleteById(courseId);

        if (i == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }


}
