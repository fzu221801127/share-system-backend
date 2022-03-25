package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Post;
import com.example.entity.Shearch;
import com.example.entity.ShearchCount;
import com.example.entity.ShearchCountMonth;
import com.example.service.PostService;
import com.example.service.ShearchCountMonthService;
import com.example.service.ShearchCountService;
import com.example.service.ShearchService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private ShearchService shearchService;
    @Autowired
    private ShearchCountService shearchCountService;
    @Autowired
    private ShearchCountMonthService shearchCountMonthService;

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
                        wrapper.like("title", post.getTitle()).eq("state","未被举报")
        );
        if (this.postService.list(queryWrapper).size() > 0) {
            //保存到该搜索记录表
            Date date = new Date(); // this object contains the current date value
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Shearch shearch = new Shearch();
            shearch.setContent(post.getTitle());
            shearch.setShearchTime(formatter.format(date));
            shearchService.save(shearch);
            //搜索内容次数统计表插入新搜索内容或更新该搜索内容的搜索次数
            QueryWrapper<ShearchCount> qw= new QueryWrapper<>();
            qw.and(
                    wrapper ->
                            wrapper.eq("content", post.getTitle())
            );
            if (shearchCountService.getOne(qw) != null) {
                ShearchCount shearchCount = shearchCountService.getOne(qw);
                shearchCount.setCount(shearchCount.getCount()+1);
                shearchCountService.update(shearchCount,qw);
            } else {
                ShearchCount shearchCount = new ShearchCount();
                shearchCount.setCount(1);
                shearchCount.setContent(post.getTitle());
                shearchCountService.save(shearchCount);
            }
            System.out.println("！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
            //按月份的搜索内容次数统计表插入新搜索内容或更新该搜索内容的搜索次数
            QueryWrapper<ShearchCountMonth> qw2= new QueryWrapper<>();
            qw2.and(
                    wrapper ->
                            wrapper.eq("content", post.getTitle()).eq("month",formatter.format(date).substring(0,7))
            );
            System.out.println("00000000000000000000000000000000000000000000000000");
            if (shearchCountMonthService.getOne(qw2) != null) {
                System.out.println("1111111111111111111111111111111111111111111111111111");
                ShearchCountMonth shearchCountMonth = shearchCountMonthService.getOne(qw2);
                shearchCountMonth.setCount(shearchCountMonth.getCount()+1);
                shearchCountMonthService.update(shearchCountMonth,qw2);
            } else {
                System.out.println("22222222222222222222222222222222222222222");
                ShearchCountMonth shearchCountMonth = new ShearchCountMonth();
                shearchCountMonth.setCount(1);
                shearchCountMonth.setContent(post.getTitle());
                shearchCountMonth.setMonth(formatter.format(date).substring(0,7));
                shearchCountMonthService.save(shearchCountMonth);
            }
        }
        return  this.postService.list(queryWrapper);
    }

    /**
     * 描述:通过标题模糊搜索下架文章
     * 参数:文章标题
     * 返回值:文章列表
     * @author zhuangweilong
     * @since 2022-03-04
     */
    @GetMapping("/takedown/title")
    public List<Post> getTakedownPostListByName(Post post) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(
                wrapper ->
                        wrapper.like("title", post.getTitle()).eq("state","暂时下架")
        );
        return  this.postService.list(queryWrapper);
    }

    /**
     * 描述:通过id获取文章
     * 参数:文章id
     * 返回值:文章
     * @author zhuangweilong
     * @since 2022-03-04
     */
    @GetMapping("/id")
    public Post getPostById(Post post) {
        Post post1 =  postService.getById(post.getId());
        if (post1 != null) {
            post1.setClick(post1.getClick()+1);
            postService.updateById(post1);
        }
        return post1;
    }

    @GetMapping("/spider")
    public String spider (Integer type,Integer firstPage,Integer lastPage) throws IOException {
        Integer count = 0;
        String baseurl = "https://www.ygdy8.net";
        for (int i = firstPage; i<=lastPage; i++) {
            String number = String.valueOf(i);
            String url = "https://www.ygdy8.net/html/gndy/dyzz/list_"+type.toString()+"_"+number+".html";
            count += spiderOnePage(baseurl, url);
        }
        String message = "成功爬取"+count+"条信息";
        return message;
    }

    @GetMapping("/spider/one")
    public Integer spiderOnePage(String baseurl, String url) throws IOException {
        Integer count = 0;
        String output = "";
        String content = HttpClientDownPage.sendGet(url);
        if (content != null) {
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
                String movieName = elements.text();
                output = output + str + "\n";
                boolean success = spiderDetail(baseurl+link,"src/detail.html",movieName);
                if (success) {
                    count++;
                }
            }
            FileWriter fileWriter = new FileWriter("src/moviefile.html",true);
            fileWriter.append(output);
            fileWriter.flush();
            fileWriter.close();
            return count;
        }
        return count;
    }

    public boolean spiderDetail (String url, String file,String movieName) throws IOException {
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
                        if (movieName != null && movieName != "")
                        {
                            post.setTitle(movieName);
                        } else {
                            post.setTitle(title);
                        }
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
                return postService.save(post);
            }
        }
        return false;
//        FileWriter fileWriter = new FileWriter(file,true);
//        fileWriter.append(output);
//        fileWriter.flush();
//        fileWriter.close();
    }

}

