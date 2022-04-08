package com.example.service;

import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.query.UserQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-08
 */
public interface UserService extends IService<User> {

    UserQuery userLogin(User user);

    UserQuery adminLogin(User user);

    UserQuery getUserInfoById(User user);

    UserQuery userRegister(User user);
}
