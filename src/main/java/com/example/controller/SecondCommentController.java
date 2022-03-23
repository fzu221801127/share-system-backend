package com.example.controller;


import com.example.entity.SecondComment;
import com.example.service.SecondCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/v1/secondComment")
public class SecondCommentController {
    @Autowired
    SecondCommentService secondCommentService;

    /**
     * 描述:通过一级评论id获取二级评论列表
     * 参数:一级评论id
     * 返回值:二级评论列表
     * @author zhuangweilong
     * @since
     */
    @GetMapping("/firstCommentId")
    public List<SecondComment> getSecondCommentByFistCommentId(@RequestParam("firstCommentId") Integer firstCommentId){
        return secondCommentService.getSecondCommentByFistCommentId(firstCommentId);
    }
}

