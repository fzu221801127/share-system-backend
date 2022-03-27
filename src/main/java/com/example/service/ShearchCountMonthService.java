package com.example.service;

import com.example.entity.ShearchCountMonth;
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
public interface ShearchCountMonthService extends IService<ShearchCountMonth> {

    List<ShearchCountMonth> getTenHotShearchInThisMonth();

    Integer getShearchCountInThisMonth();
}
