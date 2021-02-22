package com.yuqiong.college.service.edu.controller;


import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.Chapter;
import com.yuqiong.college.service.edu.entity.chapter.ChapterVO;
import com.yuqiong.college.service.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-13
 */
@CrossOrigin
@RestController
@Api("课程")
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "根据id查课")
    @GetMapping("getChapterVideo/{courseId}")
    public ResultData getChapterVideo(@PathVariable String courseId) {
        List<ChapterVO> list = chapterService.getChapterVideoByCourseId(courseId);
        return ResultData.ok().data("allChapterVideo", list);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public ResultData addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return ResultData.ok();
    }

    @ApiOperation(value = "根据id查询章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public ResultData getChapterInfo(@PathVariable String chapterId) {
        Chapter byId = chapterService.getById(chapterId);
        return ResultData.ok().data("chapter", byId);
    }

    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public ResultData updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return ResultData.ok();
    }

    /**
     * 如果有小节就不删
     *
     * @param chapterId
     * @return
     */
    @ApiOperation(value = "删除章节")
    @DeleteMapping("deleteChapter/{chapterId}")
    public ResultData deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag == false) {
            return ResultData.error();
        }
        return ResultData.ok();
    }

}

