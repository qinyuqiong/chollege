package com.yuqiong.college.service.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/13
 */
@Data
public class ChapterVO {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
