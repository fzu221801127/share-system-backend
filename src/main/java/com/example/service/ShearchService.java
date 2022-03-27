package com.example.service;

import com.example.entity.Shearch;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-24
 */
public interface ShearchService extends IService<Shearch> {

    Integer getShearchCountInThisMonth();
}
