package com.tediproject.tedi.controllers;
import org.springframework.web.bind.annotation.RestController;
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.service.ArticleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class HomepageController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value="/HomePage/Articles")
    public List<Article> getRequests(@RequestParam(value="token", required = false)String token) {
        return articleService.findArticles(token);
    }

}
