package com.yuqiong.college.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.common.handler.GuliException;
import com.yuqiong.college.service.edu.entity.Chapter;
import com.yuqiong.college.service.edu.entity.Video;
import com.yuqiong.college.service.edu.entity.chapter.ChapterVO;
import com.yuqiong.college.service.edu.entity.chapter.VideoVo;
import com.yuqiong.college.service.edu.mapper.ChapterMapper;
import com.yuqiong.college.service.edu.query.CourseWebVo;
import com.yuqiong.college.service.edu.service.ChapterService;
import com.yuqiong.college.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        List<ChapterVO> list = new ArrayList<>();

        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Chapter> chapters = this.list(queryWrapper);

        QueryWrapper<Video> voQueryWrapperWrapper = new QueryWrapper<>();
        voQueryWrapperWrapper.eq("course_id", courseId);
        List<Video> videos = videoService.list(voQueryWrapperWrapper);

        chapters.forEach(chapter -> {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);

            List<VideoVo> videoVos = new ArrayList<>();
            videos.forEach(video -> {
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            });

            chapterVO.setChildren(videoVos);
            list.add(chapterVO);
        });

        return list;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(videoQueryWrapper);
        if (count > 0) {
            throw new GuliException(20001, "不能删除");

        } else {
            boolean b = this.removeById(chapterId);
            return b;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(videoQueryWrapper);
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
