package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Shearch;
import com.example.mapper.ShearchMapper;
import com.example.service.ShearchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-24
 */
@Service
public class ShearchServiceImpl extends ServiceImpl<ShearchMapper, Shearch> implements ShearchService {

    @Resource
    ShearchMapper shearchMapper;

    @Override
    public Integer getShearchCountInThisMonth() {
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String month = formatter.format(date).substring(0,7);
        QueryWrapper<Shearch> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("shearch_time",month);
        return shearchMapper.selectCount(queryWrapper);
    }
}
