package com.yuqiong.college.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuqiong.college.common.handler.GuliException;
import com.yuqiong.college.service.edu.entity.Subject;
import com.yuqiong.college.service.edu.excel.SubjectData;
import com.yuqiong.college.service.edu.service.SubjectService;

/**
 * excel监听器
 *
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/12
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为subjectlistener 不能交给spring进行管理，需要自己new ,不能注入其他对象
    //就不能实现数据库操作
    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null) {
            throw new GuliException(20001, "添加失败");
        }
        Subject existOneSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        if (existOneSubject == null) {//没有相同的
            existOneSubject = new Subject();
            existOneSubject.setTitle(data.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
            //获取一级分类id值
            String pid = existOneSubject.getId();

            //添加二级分类
            Subject existTwoSubject = this.existTwoSubject(subjectService, data.getOneSubjectName(), pid);
            if (existTwoSubject == null) {
                existTwoSubject = new Subject();
                existTwoSubject.setTitle(data.getTwoSubjectName());
                existTwoSubject.setParentId(pid);
                subjectService.save(existTwoSubject);
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    //判断二级不能重复添加
    private Subject existTwoSubject(SubjectService subjectService, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        Subject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    //判读一级不能重复添加
    private Subject existOneSubject(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        Subject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }


}
