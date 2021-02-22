package com.yuqiong.college.service.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuqiong.college.common.utils.ResultData;
import com.yuqiong.college.service.edu.entity.CrmBanner;
import com.yuqiong.college.service.edu.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-22
 */
@RestController
@CrossOrigin
@Api("首页")
@RequestMapping("/edu/crmbanner")
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "分页查询")
    @GetMapping("/pageBanner/{page}/{limit}")
    public ResultData pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        crmBannerService.page(bannerPage, null);
        return ResultData.ok().data("total", bannerPage.getTotal()).data("items", bannerPage.getRecords());
    }

    @ApiOperation(value = "修改BANNER")
    @PutMapping("update")
    public ResultData updateById(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return ResultData.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public ResultData remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return ResultData.ok();
    }

    @ApiOperation(value = "获取")
    @PutMapping("get/{id}")
    public ResultData get(@PathVariable String id) {
        CrmBanner byId = crmBannerService.getById(id);
        return ResultData.ok().data("items", byId);
    }

    @ApiOperation(value = "查询所有")
    @GetMapping("getAllBanner")
    public ResultData getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return ResultData.ok().data("list", list);
    }


}

