package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Collection;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.mapper.CollectionMapper;
import com.example.mapper.PostMapper;
import com.example.service.CollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-11
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Resource
    CollectionMapper collectionMapper;
    @Resource
    PostMapper postMapper;

    @Override
    public List<Post> getCollectionPostListByUserId(User user) {
        QueryWrapper<Collection> wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        List<Collection> collectionList = collectionMapper.selectList(wrapper);
        if (collectionList.size() > 0){
            List<Post> postList = new ArrayList<>();
            Post post = new Post();
            for(Collection collection : collectionList){
                post = postMapper.selectById(collection.getPostId());
                postList.add(post);
            }
            return postList;
        } else {
            return null;
        }
    }
}
