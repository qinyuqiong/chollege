package com.yuqiong.college.service.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuqiong.college.service.edu.entity.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-22
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllBanner();
}
