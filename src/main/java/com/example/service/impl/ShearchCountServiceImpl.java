package com.example.service.impl;

import com.example.entity.ShearchCount;
import com.example.mapper.ShearchCountMapper;
import com.example.service.ShearchCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-25
 */
@Service
public class ShearchCountServiceImpl extends ServiceImpl<ShearchCountMapper, ShearchCount> implements ShearchCountService {

    @Resource
    ShearchCountMapper shearchCountMapper;

    @Override
    public List<ShearchCount> getTenHotShearch() {
        int sum = 10;
        List<ShearchCount> shearchCounts = shearchCountMapper.getHotShearch();
        List<ShearchCount> shearchCounts1 = new ArrayList<>();
        if (shearchCounts.size() > 0) {
            if (shearchCounts.size() > sum) {
                for (int i = 0; i < sum; i++) {
                    shearchCounts1.add(shearchCounts.get(i));
                }
            } else {
                for (ShearchCount shearchCount : shearchCounts) {
                    shearchCounts1.add(shearchCount);
                }
            }
        }
        return shearchCounts1;
    }
}
