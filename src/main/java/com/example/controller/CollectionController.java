package com.example.controller;


import com.example.entity.Collection;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.service.CollectionService;
import com.example.service.PostService;
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
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/v1/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 描述:用户收藏文章
     * 参数:用户对象，文章对象
     * 返回值:是否成功
     * @author zhuangweilong
     * @since 2022-03-11
     */
    @PostMapping("")
    public boolean userCollectPost(@RequestBody Collection collection) {
        return  this.collectionService.userCollectPost(collection);
    }

    /**
     * 描述:用户取消收藏文章
     * 参数:用户对象，文章对象
     * 返回值:是否成功
     * @author zhuangweilong
     * @since 2022-03-11
     */
    @DeleteMapping("")
    public boolean userUnCollectPost(@RequestBody Collection collection) {
        return  this.collectionService.userUnCollectPost(collection);
    }

    /**
     * 描述:通过用户id获取该用户收藏文章的列表
     * 参数:用户对象
     * 返回值:文章列表
     * @author zhuangweilong
     * @since 2022-03-11
     */
    @GetMapping("/user")
    public List<Post> getCollectionPostListByUserId(User user) {
        return this.collectionService.getCollectionPostListByUserId(user);
    }

}

