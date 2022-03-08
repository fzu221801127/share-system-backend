package com.example.service.impl;

import com.example.entity.Post;
import com.example.mapper.PostMapper;
import com.example.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-03-03
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Resource
    PostMapper postMapper;

    @Override
    public boolean tipOffPost(Post post) {
        post.setState("暂时下架");
        int count = this.postMapper.updateById(post);
        boolean success = count != 0;
        return success;
    }
}
