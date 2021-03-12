package com.yuqiong.college.service.edu.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yuqiong.college.common.handler.GuliException;
import com.yuqiong.college.common.utils.ConstantPropertiesUtils;
import com.yuqiong.college.common.utils.InitVodCilent;
import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.Video;
import com.yuqiong.college.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
@CrossOrigin
@Api("课程小节")
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "视频播放")
    @GetMapping("getPlayAuth/{id}")
    public ResultData getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return ResultData.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }

    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public ResultData addVideo(@RequestBody Video video) {
        boolean b = videoService.save(video);
        if (b == false) {
            return ResultData.error();
        }
        return ResultData.ok();
    }

    @DeleteMapping("deleterVideo/{videoId}")
    @ApiOperation(value = "删除小节")
    public ResultData deleterVideo(@PathVariable String videoId) {
        boolean b = videoService.removeVideoById(videoId);
        if (b == false) {
            return ResultData.error();
        }
        return ResultData.ok();
    }

    @PostMapping("updateVideo")
    @ApiOperation(value = "修改小节")
    public ResultData updateVideo(@RequestBody Video video) {
        boolean b = videoService.updateById(video);
        if (b == false) {
            return ResultData.error();
        }
        return ResultData.ok();
    }

    @GetMapping("getVideo/{videoId}")
    @ApiOperation(value = "根据id查询小节")
    public ResultData getVideo(@PathVariable String videoId) {
        Video byId = videoService.getById(videoId);
        return ResultData.ok().data("video", byId);
    }

    @ApiOperation(value = "上传视频")
    @PostMapping("uploadAlyVideo")
    public ResultData uploadAlyVideo(MultipartFile file) {
        String videoId = videoService.uploadAlyVideo(file);
        return ResultData.ok().data("videoId", videoId);
    }

    @DeleteMapping("deleteAliyunvod/{videoSourceId}")
    @ApiOperation(value = "删除视频")
    public ResultData deleteAliyunvod(@PathVariable String videoSourceId) {
        boolean b = videoService.deleteAliyunvod(videoSourceId);
        if (b == false) {
            return ResultData.error();
        }
        return ResultData.ok();
    }

    @ApiOperation("删除多条视频")
    @DeleteMapping("deleteBatch")
    public ResultData deleteBatch(@RequestParam("videoList") List<String> videoList) {
        boolean b = videoService.deleteBatch(videoList);
        if (b == false) {
            return ResultData.error();
        }
        return ResultData.ok();
    }
}

