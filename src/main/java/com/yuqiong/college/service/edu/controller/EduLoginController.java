package com.yuqiong.college.service.edu.controller;

import com.yuqiong.college.common.utils.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/9
 */
@CrossOrigin
@Api("登录")
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @ApiOperation("登录")
    @PostMapping("login")
    public ResultData login() {

        return ResultData.ok().data("token", "admin");
    }

    @ApiOperation("获取数据")
    @GetMapping("info")
    public ResultData info() {

        return ResultData.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
