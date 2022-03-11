package com.example.service;

import com.example.entity.Collection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Post;
import com.example.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-11
 */
public interface CollectionService extends IService<Collection> {

    List<Post> getCollectionPostListByUserId(User user);
}
