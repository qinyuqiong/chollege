package com.yuqiong.college.common.schedule;

import com.yuqiong.college.common.utils.DateUtil;
import com.yuqiong.college.service.edu.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 *
 * @author LENOVO
 * @version 1.0
 * @date 2021/3/15
 */
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //每天凌晨一点，执行方法，把数据查询进行添加
    @Scheduled(cron = "1 0 1 1/1 * ?")
    @Async
    public void task2() {
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
