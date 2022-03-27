package com.example.mapper;

import com.example.entity.ShearchCountMonth;
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
public interface ShearchCountMonthMapper extends BaseMapper<ShearchCountMonth> {

    @Select("SELECT * FROM shearch_count_month where month like CONCAT('%',#{month},'%') order by count desc")
    List<ShearchCountMonth> getHotShearchInThisMonth(String month);

    @Select("SELECT sum(count) FROM shearch_count_month where month like CONCAT('%',#{month},'%')")
    Integer getShearchCountInThisMonth(String month);
}
