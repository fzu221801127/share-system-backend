package com.example.controller;


import com.example.entity.FirstComment;
import com.example.entity.SecondComment;
import com.example.service.SecondCommentService;
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
@RequestMapping("/v1/secondComment")
public class SecondCommentController {
    @Autowired
    SecondCommentService secondCommentService;

    /**
     * 描述:插入一条二级评论
     * 参数:二级评论对象
     * 返回值:插入成功为true,失败为false
     * @author zhuangweilong
     * @since
     */
    @PostMapping("")
    public boolean insertSecondComment(@RequestBody SecondComment secondComment) {
        return this.secondCommentService.insertSecondComment(secondComment);
    }

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

