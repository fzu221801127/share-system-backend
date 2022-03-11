package com.example.controller;


import com.example.entity.Post;
import com.example.entity.User;
import com.example.service.CollectionService;
import com.example.service.PostService;
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
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/v1/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 描述:通过用户id获取该用户收藏文章的列表
     * 参数:
     * 返回值:
     * @author zhuangweilong
     * @since 2022-03-04
     */
    @GetMapping("/user")
    public List<Post> getCollectionPostListByUserId(User user) {
        return this.collectionService.getCollectionPostListByUserId(user);
    }

}

