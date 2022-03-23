package com.example.service;

import com.example.entity.SecondComment;
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
public interface SecondCommentService extends IService<SecondComment> {

    List<SecondComment> getSecondCommentByFistCommentId(Integer firstCommentId);

    boolean insertSecondComment(SecondComment secondComment);
}
