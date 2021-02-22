package com.yuqiong.college.service.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqiong.college.service.edu.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String courseId);

    String uploadAlyVideo(MultipartFile file);

    boolean deleteAliyunvod(String videoSourceId);

    boolean removeVideoById(String videoId);

    boolean deleteBatch(List<String> videoList);
}
