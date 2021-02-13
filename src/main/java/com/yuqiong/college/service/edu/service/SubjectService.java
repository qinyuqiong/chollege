package com.yuqiong.college.service.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqiong.college.service.edu.entity.Subject;
import com.yuqiong.college.service.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-12
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getALLOneTweSubject();
}
