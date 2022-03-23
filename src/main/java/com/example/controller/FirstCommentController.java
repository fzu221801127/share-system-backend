package com.example.controller;


import com.example.entity.FirstComment;
import com.example.entity.Post;
import com.example.service.FirstCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
     * 描述:插入一条一级评论
     * 参数:一级评论对象
     * 返回值:插入成功为true,失败为false
     * @author zhuangweilong
     * @since
     */
    @PostMapping("")
    public boolean insertFirstComment(@RequestBody FirstComment firstComment) {
        return this.firstCommentService.insertFirstComment(firstComment);
    }

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

