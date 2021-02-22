package com.yuqiong.college.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuqiong.college.service.edu.entity.CrmBanner;
import com.yuqiong.college.service.edu.mapper.CrmBannerMapper;
import com.yuqiong.college.service.edu.service.CrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author yuqiong
 * @since 2021-02-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    //缓存，第一次去数据库，后面去redis
    @Cacheable(key = "'selectIndexList'", value = "banner")
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(queryWrapper);
        return crmBanners;
    }
}
