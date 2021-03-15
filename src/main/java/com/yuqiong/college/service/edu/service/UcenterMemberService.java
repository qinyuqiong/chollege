package com.yuqiong.college.service.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqiong.college.service.edu.entity.UcenterMember;
import com.yuqiong.college.service.edu.entity.chapter.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-24
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    String login(UcenterMember ucenterMember);

    Integer countRegister(String day);
}
