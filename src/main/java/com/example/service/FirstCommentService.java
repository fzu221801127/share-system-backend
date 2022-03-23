package com.example.service;

import com.example.entity.FirstComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-23
 */
public interface FirstCommentService extends IService<FirstComment> {

    List<FirstComment> getFirstCommentByPostId(Integer postId);

    boolean insertFirstComment(FirstComment firstComment);
}
