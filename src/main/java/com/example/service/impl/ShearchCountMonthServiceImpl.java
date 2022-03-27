package com.example.service.impl;

import com.example.entity.ShearchCount;
import com.example.entity.ShearchCountMonth;
import com.example.mapper.ShearchCountMapper;
import com.example.mapper.ShearchCountMonthMapper;
import com.example.service.ShearchCountMonthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-25
 */
@Service
public class ShearchCountMonthServiceImpl extends ServiceImpl<ShearchCountMonthMapper, ShearchCountMonth> implements ShearchCountMonthService {

    @Resource
    ShearchCountMonthMapper shearchCountMonthMapper;

    @Override
    public List<ShearchCountMonth> getTenHotShearchInThisMonth() {
        int sum = 10;
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String month = formatter.format(date).substring(0,7);
        List<ShearchCountMonth> shearchCountMonths = shearchCountMonthMapper.getHotShearchInThisMonth(month);
        List<ShearchCountMonth> shearchCountMonths1 = new ArrayList<>();
        if (shearchCountMonths.size() > 0) {
            if (shearchCountMonths.size() > sum) {
                for (int i = 0; i < sum; i++) {
                    shearchCountMonths1.add(shearchCountMonths.get(i));
                }
            } else {
                for (ShearchCountMonth shearchCountMonth : shearchCountMonths) {
                    shearchCountMonths1.add(shearchCountMonth);
                }
            }
        }
        return shearchCountMonths1;
    }

    @Override
    public Integer getShearchCountInThisMonth() {
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String month = formatter.format(date).substring(0,7);
        return shearchCountMonthMapper.getShearchCountInThisMonth(month);
    }
}
