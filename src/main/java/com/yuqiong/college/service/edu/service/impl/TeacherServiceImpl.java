package com.yuqiong.college.service.edu.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.service.edu.entity.Teacher;
import com.yuqiong.college.service.edu.mapper.TeacherMapper;
import com.yuqiong.college.service.edu.query.TeacherQuery;
import com.yuqiong.college.service.edu.service.TeacherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-01
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public void pageQuery(Page<Teacher> teacherPage, TeacherQuery teacherQuery) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        if (teacherQuery == null) {
            baseMapper.selectPage(teacherPage, queryWrapper);
            return;
        }
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (level != null) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(teacherPage, queryWrapper);

    }

    @Override
    @Cacheable(value = "teacherList", key = "'selectTeacher'")
    public List<Teacher> getList() {
        QueryWrapper<Teacher> queryWrapper1 = new QueryWrapper();
        queryWrapper1.orderByDesc("id");
        queryWrapper1.last("limit 4");
        List<Teacher> list1 = this.list(queryWrapper1);
        return list1;
    }
}
