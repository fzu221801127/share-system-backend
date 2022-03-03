package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Post;
import com.example.entity.Student;
import com.example.service.PostService;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 描述:插入新的文章
     * 参数:文章对象
     * 返回值:插入成功为true,失败为false
     * @author zhuangweilong
     * @since 2022-03-03
     */
    @PostMapping("")
    public boolean insertPost(@RequestBody Post post) {
        return  this.postService.save(post);
    }

    /**
     * 描述:通过id删除文章
     * 参数:id
     * 返回值:删除成功为true,失败为false
     * @author zhuangweilong
     * @since 2022-03-03
     */
    @DeleteMapping ("/id")
    public boolean deletePostById(Integer id) {
        return this.postService.removeById(id);
    }

    /**
     * 描述:更新文章
     * 参数:文章对象
     * 返回值:更新成功为true,失败为false
     * @author zhuangweilong
     * @since 2022-03-04
     */
    @PutMapping("/id")
    public boolean update(@RequestBody Post post){
        return this.postService.updateById(post);
    }

    /**
     * 描述:获取全部文章列表
     * 参数:无
     * 返回值:文章列表
     * @author zhuangweilong
     * @since 2022-03-04
     * @return
     */
    @GetMapping("")
    public List<Post> postList(){
        return this.postService.list();
    }

    /**
     * 描述:通过标题模糊搜索文章
     * 参数:文章标题
     * 返回值:文章列表
     * @author zhuangweilong
     * @since 2022-03-04
     */
    @GetMapping("/title")
    public List<Post> getPostListByName(Post post) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                wrapper ->
                        wrapper.like("title", post.getTitle())
        );
        return  this.postService.list(queryWrapper);
    }

    /**
     * 描述:通过id搜索文章
     * 参数:文章id
     * 返回值:文章
     * @author zhuangweilong
     * @since 2022-03-04
     */
    @GetMapping("/id")
    public Post getPostById(Post post) {
        return this.postService.getById(post.getId());
    }

}

