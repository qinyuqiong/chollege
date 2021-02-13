package com.yuqiong.college.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.common.listener.SubjectExcelListener;
import com.yuqiong.college.service.edu.entity.Subject;
import com.yuqiong.college.service.edu.entity.subject.OneSubject;
import com.yuqiong.college.service.edu.entity.subject.TwoSubject;
import com.yuqiong.college.service.edu.excel.SubjectData;
import com.yuqiong.college.service.edu.mapper.SubjectMapper;
import com.yuqiong.college.service.edu.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-12
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getALLOneTweSubject() {
        //最终数据结构
        List<OneSubject> finalSubjects = new ArrayList<>();
        //查询所有一级分类
        QueryWrapper<Subject> oneWrapper = new QueryWrapper();
        oneWrapper.eq("parent_id", 0);
        List<Subject> oneSubjects = baseMapper.selectList(oneWrapper);

        //查询所有二级分类
        QueryWrapper<Subject> twoWrapper = new QueryWrapper();
        twoWrapper.ne("parent_id", 0);
        List<Subject> twoSubjects = baseMapper.selectList(twoWrapper);

        //封装一级分类
        oneSubjects.forEach(subject -> {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);

            //二级分类
            List<TwoSubject> twoSubjects1 = new ArrayList<>();
            twoSubjects.forEach(subject1 -> {
                if (subject1.getParentId().equals(subject.getId())) {
                    TwoSubject subject2 = new TwoSubject();
                    BeanUtils.copyProperties(subject1, subject2);
                    twoSubjects1.add(subject2);
                }
            });

            oneSubject.setChildren(twoSubjects1);
            finalSubjects.add(oneSubject);
        });
        //封装二级分类


        return finalSubjects;
    }
}
