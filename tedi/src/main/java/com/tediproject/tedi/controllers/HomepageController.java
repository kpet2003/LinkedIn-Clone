package com.tediproject.tedi.controllers;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.dto.ArticleDto;
import com.tediproject.tedi.dto.CommentDto;
import com.tediproject.tedi.dto.NewArticleDto;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.service.ArticleService;



@RestController
public class HomepageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtUtil jwtUtil;


    // send the article data back to the frontend
    @GetMapping(value="/HomePage/ArticleData")
    public List<ArticleDto> getArticles(@RequestParam(value="token", required = false)String token) {
        jwtUtil.validateToken(token);
        return articleService.fetchArticles(token);
    }

    // send the article categories to the frontend
    @GetMapping(value="/HomePage/GetCategories")
    public List<String> getCategories(@RequestParam("token") String token) {
        jwtUtil.validateToken(token);
        return articleService.getCategories();
    }

    // fetch the new article and store it to the database 
    @PostMapping(value = "/HomePage/newArticle")
    public ResponseEntity<?> newArticle(  @RequestParam("author_token") String authorToken,
            @RequestParam("title") String title,
            @RequestParam("article_content") String articleContent,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "video", required = false) MultipartFile video,
            @RequestParam(value="category", required = false) String category
            ) {


        try {
            jwtUtil.validateToken(authorToken);
            NewArticleDto article = new NewArticleDto();
            article.setAuthor_token(authorToken);
            article.setTitle(title);
            article.setArticle_content(articleContent);
            article.setCategory(category);
            
            if(image!=null) {
                article.setImage(image.getBytes());
            }

            if(video!=null) {
                article.setVideo(video.getBytes());
            }


           
           
            articleService.newArticle(article);
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
    
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }   
    }

    // add a new like to the article
    @PostMapping("/HomePage/AddLike")
    public ResponseEntity<?> newLike(@RequestParam("token") String token, @RequestParam("article_id") Long article_id) {
        try {
            jwtUtil.validateToken(token);
            articleService.AddLike(token, article_id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    
    // add a new comment to the article
    @PostMapping("/HomePage/AddComment")
    public ResponseEntity<?> newComment(@RequestParam("token") String token, @RequestParam("article_id") Long article_id,@RequestParam("comment") String comment) {
        try {
            jwtUtil.validateToken(token);
            articleService.AddComment(token, article_id, comment);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // fetch comments of article
    @GetMapping(value="/HomePage/GetComments/{article_id}")
    public List<CommentDto> GetComments(@PathVariable Long article_id, @RequestParam("token") String token) {
        jwtUtil.validateToken(token);
        return articleService.findComments(article_id);
    }

}
