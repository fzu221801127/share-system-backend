package com.example.service.impl;

import com.example.entity.FirstComment;
import com.example.mapper.FirstCommentMapper;
import com.example.service.FirstCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-23
 */
@Service
public class FirstCommentServiceImpl extends ServiceImpl<FirstCommentMapper, FirstComment> implements FirstCommentService {
    @Resource
    FirstCommentMapper firstCommentMapper;

    @Override
    public List<FirstComment> getFirstCommentByPostId(Integer postId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("post_id",postId);
        List<FirstComment> firstComments = firstCommentMapper.selectByMap(columnMap);
        return firstComments;
    }
}
