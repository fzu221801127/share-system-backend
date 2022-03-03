package com.example.controller;


import com.example.entity.Post;
import com.example.entity.Student;
import com.example.service.PostService;
import com.example.service.StudentService;
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
 * @author admin
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 描述:获取全部文章列表
     * 参数:无
     * 返回值:文章列表
     * @author zhuangweilong
     * @since
     * @return
     */
    @GetMapping("")
    public List<Post> postList(){
        return this.postService.list();
    }


}

