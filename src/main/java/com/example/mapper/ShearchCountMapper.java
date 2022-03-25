package com.example.mapper;

import com.example.entity.ShearchCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-25
 */
public interface ShearchCountMapper extends BaseMapper<ShearchCount> {
    @Select("SELECT * FROM shearch_count order by count desc")
    List<ShearchCount> getTenHotShearchInThisMonth();
}
