package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Student;
import com.example.entity.User;
import com.example.pojo.query.UserQuery;
import com.example.service.PostService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhuangweilong
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 描述:登录
     * 参数:账号、密码
     * 返回值:用户对象
     * @author zhuangweilong
     * @since
     */
    @PostMapping("userLogin")
    public UserQuery userLogin (@RequestBody User user) {
        return this.userService.userLogin(user);
    }

    /**
     * 描述:插入新的用户对象
     * 参数:用户对象
     * 返回值:插入成功为true,失败为false
     * @author zhuangweilong
     * @since
     */
    @PostMapping("")
    public boolean insertUser(@RequestBody User user) {
        user.setPermission("user");
        user.setHeadPortraitUrl("图片链接");
        user.setState("未封禁");
        user.setCollectione("");
        return  this.userService.save(user);
    }

    /**
     * 描述:通过id删除用户
     * 参数:id
     * 返回值:删除成功为true,失败为false
     * @author zhuangweilong
     * @since
     */
    @DeleteMapping("/id")
    public boolean deleteUserById(String id) {
        return this.userService.removeById(id);
    }

    /**
     * 描述:更新用户对象
     * 参数:用户对象
     * 返回值:更新成功为true,失败为false
     * @author zhuangweilong
     * @since
     */
    @PutMapping("/id")
    public boolean update(@RequestBody User user){
        return this.userService.updateById(user);
    }

    /**
     * 描述:获取全部用户列表
     * 参数:无
     * 返回值:用户列表
     * @author zhuangweilong
     * @since
     * @return
     */
    @GetMapping("")
    public List<User> getUserList(){
        return this.userService.list();
    }

    /**
     * 描述:通过名字模糊搜索用户
     * 参数:用户名
     * 返回值:用户列表
     * @author zhuangweilong
     * @since
     */
    @GetMapping("/name")
    public List<User> searchUserByName(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                wrapper ->
                        wrapper.like("nickname", user.getNickname())
        );
        return  this.userService.list(queryWrapper);
    }

    /**
     * 描述:通过id搜索用户
     * 参数:用户id
     * 返回值:用户
     * @author zhuangweilong
     * @since
     */
    @GetMapping("/id")
    public User getUserById (User user) {
        return  this.userService.getById(user.getId());
    }

    /**
     * 描述:通过id模糊搜索用户
     * 参数:用户id
     * 返回值:用户
     * @author zhuangweilong
     * @since
     */
    @GetMapping("/idshearch")
    public List<User> shearchUserById (User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                wrapper ->
                        wrapper.like("id", user.getId())
        );
        return  this.userService.list(queryWrapper);
    }

}

