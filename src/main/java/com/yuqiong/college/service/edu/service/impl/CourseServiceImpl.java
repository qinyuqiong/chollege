package com.yuqiong.college.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.common.handler.GuliException;
import com.yuqiong.college.service.edu.entity.Course;
import com.yuqiong.college.service.edu.entity.CourseDescription;
import com.yuqiong.college.service.edu.entity.CoursePublishVo;
import com.yuqiong.college.service.edu.entity.chapter.CourseQueryVo;
import com.yuqiong.college.service.edu.mapper.CourseMapper;
import com.yuqiong.college.service.edu.query.CourseInfoVo;
import com.yuqiong.college.service.edu.service.ChapterService;
import com.yuqiong.college.service.edu.service.CourseDescriptionService;
import com.yuqiong.college.service.edu.service.CourseService;
import com.yuqiong.college.service.edu.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    @Cacheable(value = "CourseList", key = "'selectIndexFront'")
    public List<Course> getList() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");
        List<Course> list = this.list(queryWrapper);
        return list;
    }

    @Override
    public Map<String, Object> getFrontCourseList(long page, long limit, CourseQueryVo queryVo) {
        Page<Course> coursePage = new Page<>(page, limit);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryVo.getSubjectParentId())) {
            courseQueryWrapper.eq("subject_parent_id", queryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(queryVo.getSubjectId())) {
            courseQueryWrapper.eq("subject_id", queryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(queryVo.getBuyCountSort())) {
            courseQueryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(queryVo.getPriceSort())) {
            courseQueryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, courseQueryWrapper);

        List<Course> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }


}
