package com.example.controller;


import com.example.entity.ShearchCount;
import com.example.service.ShearchCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-25
 */
@RestController
@RequestMapping("/v1/shearchCount")
public class ShearchCountController {

    @Autowired
    ShearchCountService shearchCountService;

    /**
     * 描述:获取热搜前十
     * 参数:
     * 返回值:
     * @author zhuangweilong
     * @since 2022/3/27 19:14
     */
    @GetMapping("")
    public List<ShearchCount> getTenHotShearch () {
        return shearchCountService.getTenHotShearch();
    }

    /**
     * 描述:获取历史总搜索次数
     * 参数:
     * 返回值:
     * @author zhuangweilong
     * @since 2022/3/27 19:14
     */
    @GetMapping("/count")
    public Integer getShearchCount () {
        return shearchCountService.getShearchCount();
    }
}

