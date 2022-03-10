package com.example.service.impl;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.pojo.query.UserQuery;
import com.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserQuery userLogin(User user) {
        User userMessage;
        UserQuery userQuery = new UserQuery();
        userMessage = userMapper.selectById(user.getId());
        if (userMessage == null) {
            userQuery.setMessage("Can not found this user");
            return userQuery;
        }
        String permission = "user";
        if (!permission.equals(userMessage.getPermission())) {
            userQuery.setMessage("Can not found this user");
            userQuery.setId(userMessage.getId());
            return userQuery;
        }
        String state = "封禁";
        if (state.equals(userMessage.getState())) {
            userQuery.setMessage("This account has been banned");
            userQuery.setId(userMessage.getId());
            return userQuery;
        }
        if (!userMessage.getPassword().equals(user.getPassword())) {
            userQuery.setMessage("Wrong password");
            userQuery.setId(userMessage.getId());
            return userQuery;
        }
        userQuery.setMessage("Success");
        userQuery.setId(userMessage.getId());
        userQuery.setNickname(userMessage.getNickname());
        userQuery.setBirthday(userMessage.getBirthday());
        userQuery.setSignature(userMessage.getSignature());
        userQuery.setPhoneNumber(userMessage.getPhoneNumber());
        userQuery.setCollectione(userMessage.getCollectione());
        userQuery.setState(userMessage.getState());
        userQuery.setHeadPortraitUrl(userMessage.getHeadPortraitUrl());
        userQuery.setPermission(userMessage.getPermission());
        return userQuery;
    }
}
