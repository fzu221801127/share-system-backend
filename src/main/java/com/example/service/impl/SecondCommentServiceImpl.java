package com.example.service.impl;

import com.example.entity.SecondComment;
import com.example.mapper.FirstCommentMapper;
import com.example.mapper.SecondCommentMapper;
import com.example.service.SecondCommentService;
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
public class SecondCommentServiceImpl extends ServiceImpl<SecondCommentMapper, SecondComment> implements SecondCommentService {
    @Resource
    SecondCommentMapper secondCommentMapper;
    @Resource
    FirstCommentMapper firstCommentMapper;

    @Override
    public List<SecondComment> getSecondCommentByFistCommentId(Integer firstCommentId) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("first_comment_id",firstCommentId);
        List<SecondComment> secondComments = secondCommentMapper.selectByMap(columnMap);
        return secondComments;
    }

    @Override
    public boolean insertSecondComment(SecondComment secondComment) {
        if (firstCommentMapper.selectById(secondComment.getFirstCommentId()) == null) {
            return false;
        }
        int count = 0;
        count = secondCommentMapper.insert(secondComment);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
