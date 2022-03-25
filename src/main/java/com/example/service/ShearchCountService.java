package com.example.service;

import com.example.entity.ShearchCount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-25
 */
public interface ShearchCountService extends IService<ShearchCount> {

    List<ShearchCount> getTenHotShearchInThisMonth();
}
