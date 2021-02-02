package com.yuqiong.college.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqiong.college.service.edu.entity.Teacher;
import com.yuqiong.college.service.edu.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-01
 */
public interface TeacherService extends IService<Teacher> {

    void pageQuery(Page<Teacher> teacherPage, TeacherQuery teacherQuery);
}
