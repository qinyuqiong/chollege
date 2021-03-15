package com.yuqiong.college.service.edu.controller;


import com.yuqiong.college.common.utils.JwtUtils;
import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.UcenterMember;
import com.yuqiong.college.service.edu.entity.chapter.RegisterVo;
import com.yuqiong.college.service.edu.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-24
 */
@RestController
@Api("登录注册")
@CrossOrigin
@RequestMapping("/eduservice/Msm")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 查询手机号，并发短信
     *
     * @param phone
     * @return
     */
    @GetMapping("send/{phone}")
    @ApiOperation(value = "发短信")
    public ResultData sendMsm(@PathVariable String phone) {
        return ResultData.ok();
    }

    /**
     * 注册
     *
     * @return
     */
    @PostMapping("register")
    @ApiOperation(value = "注册")
    public ResultData register(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return ResultData.ok();
    }

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public ResultData loginUser(@RequestBody UcenterMember ucenterMember) {
        String token = ucenterMemberService.login(ucenterMember);
        return ResultData.ok().data("token", token);

    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    @ApiOperation(value = "根据token获取用户信息")
    public ResultData getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return ResultData.ok().data("userInfo", member);
    }

    @ApiOperation(value = "查询某一天的注册人数")
    @GetMapping("countRegister/{day}")
    public ResultData countRegister(@PathVariable String day) {
        Integer count = ucenterMemberService.countRegister(day);
        return ResultData.ok().data("countRegister", count);
    }

}

