package com.example.controller;


import com.example.entity.FirstComment;
import com.example.service.FirstCommentService;
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
@RequestMapping("/v1/firstComment")
public class FirstCommentController {
    @Autowired
    private FirstCommentService firstCommentService;

    /**
     * 描述:通过文章id获取一级评论列表
     * 参数:文章id
     * 返回值:一级评论列表
     * @author zhuangweilong
     * @since
     */
    @GetMapping("/postId")
    public List<FirstComment> getFirstCommentByPostId (@RequestParam("postId") Integer postId) {
        return firstCommentService.getFirstCommentByPostId(postId);
    }
}

