package com.yuqiong.college.service.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqiong.college.service.edu.entity.Chapter;
import com.yuqiong.college.service.edu.entity.chapter.ChapterVO;
import com.yuqiong.college.service.edu.query.CourseWebVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
