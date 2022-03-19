package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Post;
import com.example.service.PostService;
import com.example.utils.SpringUtil.SpringUtil;
import com.example.utils.spider.HttpClientDownPage;
import com.example.utils.spider.Spider;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 描述:只提供部分文章信息更新文章
     * 参数:文章对象
     * 返回值:更新成功为true,失败为false
     * @author zhuangweilong
     * @since 2022-03-08
     */
    @PatchMapping("/id")
    public boolean reinstatePostById(Integer id){
        Post post = new Post();
        post.setId(id);
        post.setState("未被举报");
        return this.postService.updateById(post);
    }

    /**
     * 描述:举报文章(其实直接用上面update方法，前端post的state属性改为“被举报”再调用update效果也一样)
     * 参数:文章对象
     * 返回值:成功为true,失败为false
     * @author zhuangweilong
     * @since 2022-03-04
     */
    @PutMapping("/tipOffPost")
    public boolean tipOffPost(@RequestBody Post post) {
        return this.postService.tipOffPost(post);
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
    public  Object postPageList(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, @RequestParam(value = "pagesize", defaultValue = "10") int pagesize)
    {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                wrapper ->
                        wrapper.like("state", "未被举报")
        );
        //使用分页插件 教程地址:https://www.cnblogs.com/dongzi1997/p/15599500.html
        Page<Object> p = PageHelper.startPage(currentPage, pagesize);//最重要的一步
        List<Post> allGoods = this.postService.list(queryWrapper);//调用查询方法一定要放在startPage后面，不然分页不了
        PageInfo<Post> pageInfo = new PageInfo<Post>(allGoods,pagesize);
        Map<String,Object> data=new HashMap<>();
        data.put("page",pageInfo);
        return data;
    }

    /**
     * 描述:获取下架的文章列表
     * 参数:无
     * 返回值:下架的文章列表
     * @author zhuangweilong
     * @since 2022-03-04
     * @return
     */
    @GetMapping("/takedown")
    public List<Post> getPostTakeDownList() {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                wrapper ->
                        wrapper.like("state", "暂时下架")
        );
        return  this.postService.list(queryWrapper);
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

    @GetMapping("/spider")
    public boolean spider (Integer type,Integer firstPage,Integer lastPage) throws IOException {
        String baseurl = "https://www.ygdy8.net";
        for (int i = firstPage; i<=lastPage; i++) {
            String number = String.valueOf(i);
            String url = "https://www.ygdy8.net/html/gndy/dyzz/list_"+type.toString()+"_"+number+".html";
            spiderOnePage(baseurl, url);
        }
        return true;
    }

    @GetMapping("/spider/one")
    public boolean spiderOnePage(String baseurl, String url) throws IOException {
        String output = "";
        String content = HttpClientDownPage.sendGet(url);
        Document doc = Jsoup.parse(content);
        //#header > div > div.bd2 > div.bd3 > div.bd3r > div.co_area2 > div.co_content8 > ul > table:nth-child(1) > tbody > tr:nth-child(2) > td:nth-child(2) > b > a
        //#header > div > div.bd2 > div.bd3 > div.bd3r > div.co_area2 > div.co_content8 > ul > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(2) > b > a
        //#header > div > div.bd2 > div.bd3 > div.bd3r > div.co_area2 > div.co_content8 > ul > table:nth-child(25) > tbody > tr:nth-child(2) > td:nth-child(2) > b > a
        //#header > div > div.bd2 > div.bd3 > div.bd3r > div.co_area2 > div.co_content8 > ul > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(2) > b > a
        for (int i = 1; i<=25; i++) {
            String number = String.valueOf(i);
            Elements elements = doc.select("#header")
                    .select("div")
                    .select("div.bd2")
                    .select("div.bd3")
                    .select("div.bd3r")
                    .select("div.co_area2")
                    .select("div.co_content8")
                    .select("ul")
                    .select("table:nth-child("+number+")")
                    .select("tbody")
                    .select("tr:nth-child(2)")
                    .select("td:nth-child(2)")
                    .select("b")
                    .select("a");
            String link = elements.attr("href");
            String str = elements.toString();
            output = output + str + "\n";
            spiderDetail(baseurl+link,"src/detail.html");
        }
        FileWriter fileWriter = new FileWriter("src/moviefile.html",true);
        fileWriter.append(output);
        fileWriter.flush();
        fileWriter.close();
        return true;
    }

    public void spiderDetail (String url, String file) throws IOException {
        Post post = new Post();
        post.setReleasetime("2022-3-19 15:30:31");
        post.setType("官方资源");
        post.setClick(0);
        post.setUserId("admin");
        post.setState("未被举报");
        post.setTitle("");
        post.setContent("");

        String output = "";
        String content = HttpClientDownPage.sendGet(url);
        if (content != null) {
            Document doc = Jsoup.parse(content);
            //#Zoom > span > a
            //#Zoom > span > font:nth-child(31) > a
            //#Zoom > span

            Elements elements1 = doc.select("#Zoom")
                    .select("span");
            if (elements1 != null) {
                Elements elements2 = elements1.select("a");
                if (elements2 != null) {
                    String str2 = elements2.text();
                    if (str2 != null) {
                        String[] split = str2.split(" ");
                        String title = split[1];
                        post.setTitle(title);
//                        output = output + title + "\n";
                    }
                }
                String str1 = elements1.toString();
                if (str1 != null) {
                    post.setContent(str1);
//                    output = output + str1 + "\n";
                }
            }
        }
        if (post != null) {
            if (post.getTitle() != "" && post.getContent() != ""){
                System.out.println(post.getTitle()+"\n"+post.getContent());
                postService.save(post);
            }
        }

//        FileWriter fileWriter = new FileWriter(file,true);
//        fileWriter.append(output);
//        fileWriter.flush();
//        fileWriter.close();
    }

}

