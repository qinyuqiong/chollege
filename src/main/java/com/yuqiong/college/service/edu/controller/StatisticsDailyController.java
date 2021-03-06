package com.yuqiong.college.service.edu.controller;


import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/edu/statistics-daily")
@CrossOrigin
@Api("统计日数据")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation(value = "统计注册人数")
    @PostMapping("registerCount/{day}")
    public ResultData registerCount(@PathVariable String day) {
        statisticsDailyService.registerCount(day);
        return ResultData.ok();
    }

    @ApiOperation(value = "图表显示")
    @GetMapping("show-chart/{type}/{begin}/{end}")
    public ResultData showData(@PathVariable String begin, @PathVariable String end, @PathVariable String type) {
        Map<String, Object> map = statisticsDailyService.showData(begin, end, type);
        return ResultData.ok().data(map);
    }

}

