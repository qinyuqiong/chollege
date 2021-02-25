package com.yuqiong.college.service.edu.controller;


import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.chapter.RegisterVo;
import com.yuqiong.college.service.edu.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

