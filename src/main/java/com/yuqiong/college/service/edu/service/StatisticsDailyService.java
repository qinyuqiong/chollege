package com.yuqiong.college.service.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqiong.college.service.edu.entity.StatisticsDaily;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author yuqiong
 * @since 2021-03-15
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);
}
