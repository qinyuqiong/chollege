package com.yuqiong.college.service.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuqiong.college.service.edu.entity.Chapter;
import com.yuqiong.college.service.edu.query.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
public interface ChapterMapper extends BaseMapper<Chapter> {

    CourseWebVo getBaseCourseInfo(String courseId);
}
