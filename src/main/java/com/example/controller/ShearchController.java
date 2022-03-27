package com.example.controller;


import com.example.service.ShearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-24
 */
@RestController
@RequestMapping("/v1/shearch")
public class ShearchController {

    @Autowired
    ShearchService shearchService;

    @GetMapping("/count")
    public Integer getShearchCount () {
        return shearchService.count();
    }

    @GetMapping("/month/count")
    public Integer getShearchCountInThisMonth () {
        return shearchService.getShearchCountInThisMonth();
    }

}

