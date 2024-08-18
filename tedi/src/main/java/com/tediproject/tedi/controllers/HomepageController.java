package com.tediproject.tedi.controllers;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.dto.NewArticleDto;
import com.tediproject.tedi.dto.NewRequestDto;
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.service.ArticleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class HomepageController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value="/HomePage/Articles")
    public List<Article> getRequests(@RequestParam(value="token", required = false)String token) {
        return articleService.findArticles(token);
    }


    @PostMapping(value = "/HomePage/newArticle")
    public ResponseEntity<?> newArticle(@RequestBody NewArticleDto article) {


        try {
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}
