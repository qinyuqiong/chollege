package com.yuqiong.college.service.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuqiong.college.service.edu.entity.Course;
import com.yuqiong.college.service.edu.entity.CoursePublishVo;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
@MapperScan
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo getPublishCourseInfo(String courseId);
}
