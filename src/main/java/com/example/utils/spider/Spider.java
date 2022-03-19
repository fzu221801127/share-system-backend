package com.example.utils.spider;


import com.example.controller.PostController;
import com.example.entity.Post;
import com.example.service.PostService;
import com.example.service.impl.PostServiceImpl;
import com.example.utils.SpringUtil.SpringUtil;
import com.example.utils.spider.HttpClientDownPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;

public class Spider {
    @Autowired
    private PostService postService;

    public static void main(String[] args) throws IOException {
        String baseurl = "https://www.ygdy8.net";
        FileWriter fileWriter = new FileWriter("src/moviefile.html",false);
        fileWriter.append("https://www.ygdy8.net/html/gndy/dyzz/list_23_?.html\n");
        fileWriter.flush();
        fileWriter.close();
        for (int i = 1; i<=2; i++) {
            String number = String.valueOf(i);
            String url = "https://www.ygdy8.net/html/gndy/dyzz/list_23_"+number+".html";
            Spider spider = new Spider();
            spider.spiderOnePage(baseurl, url);
        }
//       spiderDetail("https://www.ygdy8.net/html/gndy/dyzz/20220317/62408.html","src/detail2.html");
    }

    public void spiderOnePage(String baseurl, String url) throws IOException {
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
                        output = output + title + "\n";
                    }
                }
                String str1 = elements1.toString();
                if (str1 != null) {
                    post.setContent(str1);
                    output = output + str1 + "\n";
                }
            }
        }
        if (post != null) {
            if (post.getTitle() != "" && post.getContent() != ""){
                System.out.println(post.getTitle()+"\n"+post.getContent());
                postService.save(post);
            }
        }

        FileWriter fileWriter = new FileWriter(file,true);
        fileWriter.append(output);
        fileWriter.flush();
        fileWriter.close();
    }
}
