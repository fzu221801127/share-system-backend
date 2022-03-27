package com.example.controller;


import com.example.entity.ShearchCountMonth;
import com.example.service.ShearchCountMonthService;
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
@RequestMapping("/v1/shearchCountMonth")
public class ShearchCountMonthController {

    @Autowired
    ShearchCountMonthService shearchCountMonthService;

    @GetMapping("/thisMonth")
    public List<ShearchCountMonth> getTenHotShearchInThisMonth () {
        return  shearchCountMonthService.getTenHotShearchInThisMonth();
    }

    @GetMapping("/thisMonth/count")
    public Integer getShearchCountInThisMonth () {
        return shearchCountMonthService.getShearchCountInThisMonth();
    }

}

