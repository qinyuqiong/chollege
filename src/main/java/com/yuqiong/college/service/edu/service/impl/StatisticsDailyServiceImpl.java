package com.yuqiong.college.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.service.edu.entity.StatisticsDaily;
import com.yuqiong.college.service.edu.mapper.StatisticsDailyMapper;
import com.yuqiong.college.service.edu.service.StatisticsDailyService;
import com.yuqiong.college.service.edu.service.UcenterMemberService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yuqiong
 * @since 2021-03-15
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Override
    public void registerCount(String day) {

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);

        Integer integer = ucenterMemberService.countRegister(day);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(integer);
        statisticsDaily.setDateCalculated(day);

        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(statisticsDaily);

    }
}
