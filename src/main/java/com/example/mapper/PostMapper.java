package com.example.mapper;

import com.example.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-03-03
 */
public interface PostMapper extends BaseMapper<Post> {

    @Select("SELECT * FROM post order by click desc limit 10;")
    List<Post> getTopTenClickPost();
}
