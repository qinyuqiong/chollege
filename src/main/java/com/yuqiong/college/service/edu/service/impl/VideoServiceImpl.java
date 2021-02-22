package com.yuqiong.college.service.edu.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.common.handler.GuliException;
import com.yuqiong.college.common.utils.ConstantPropertiesUtils;
import com.yuqiong.college.common.utils.InitVodCilent;
import com.yuqiong.college.service.edu.entity.Video;
import com.yuqiong.college.service.edu.mapper.VideoMapper;
import com.yuqiong.college.service.edu.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    /**
     * 删除小节
     * todo 删除视频
     *
     * @param courseId
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<Video> videos = baseMapper.selectList(queryWrapper);

        List<String> videosId = new ArrayList<>();
        videos.forEach(video -> {
            if (!video.getVideoSourceId().isEmpty()) {
                videosId.add(video.getVideoSourceId());
            }
        });
        if (!videosId.isEmpty()) {
            //删除视频
            deleteBatch(videosId);
        }
        baseMapper.delete(queryWrapper);
    }

    @Override
    public String uploadAlyVideo(MultipartFile file) {
        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteAliyunvod(String videoSourceId) {
        DefaultAcsClient defaultAcsClient = InitVodCilent.initVodClient(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoSourceId);
        try {
            defaultAcsClient.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除失败");
        }
    }

    @Override
    public boolean removeVideoById(String videoId) {
        Video video = baseMapper.selectById(videoId);
        //删除视频
        deleteAliyunvod(video.getVideoSourceId());
        int i = baseMapper.deleteById(videoId);
        if (i == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBatch(List<String> videoList) {
        DefaultAcsClient defaultAcsClient = InitVodCilent.initVodClient(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        String join = StringUtils.join(videoList.toArray(), ",");
        request.setVideoIds(join);
        try {
            defaultAcsClient.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除失败");
        }
    }
}
