package com.tediproject.tedi.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.dto.NewArticleDto;
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.service.ArticleService;



@RestController
public class HomepageController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value="/HomePage/Articles")
    public List<Article> getRequests(@RequestParam(value="token", required = false)String token) {
        return articleService.findArticles(token);
    }


    @PostMapping(value = "/HomePage/newArticle")
    public ResponseEntity<?> newArticle(  @RequestParam("author_token") String authorToken,
            @RequestParam("title") String title,
            @RequestParam("article_content") String articleContent,
            @RequestPart(value = "image", required = false) MultipartFile image) {


        try {
            NewArticleDto article = new NewArticleDto();
            article.setAuthor_token(authorToken);
            article.setTitle(title);
            article.setArticle_content(articleContent);
            articleService.newArticle(article);
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
    
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
            
        
    }



}
