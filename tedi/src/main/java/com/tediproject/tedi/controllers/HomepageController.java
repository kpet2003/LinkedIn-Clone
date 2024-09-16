package com.tediproject.tedi.controllers;
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
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.service.ArticleService;



@RestController
public class HomepageController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value="/HomePage/Articles")
    public List<Article> getRequests(@RequestParam(value="token", required = false)String token) {
        return articleService.findArticles(token);
    }

    @GetMapping(value="/HomePage/ArticleData")
    public List<ArticleDto> getArticles(@RequestParam(value="token", required = false)String token) {
        return articleService.fetchArticles(token);
    }

    @GetMapping(value="/HomePage/GetCategories")
    public List<String> getCategories() {
        return articleService.getCategories();
    }
    @PostMapping(value = "/HomePage/newArticle")
    public ResponseEntity<?> newArticle(  @RequestParam("author_token") String authorToken,
            @RequestParam("title") String title,
            @RequestParam("article_content") String articleContent,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "video", required = false) MultipartFile video,
            @RequestParam(value="category", required = false) String category
            ) {


        try {
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
    
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }   
    }

    @GetMapping(value="/HomePage/LikesPerArticle/{article_id}")
    public long AmountOfLikes(@PathVariable Long article_id) {
        return articleService.findAmountofLikes(article_id);
    }

    @GetMapping(value="/HomePage/Likes/{article_id}")
    public List<UserEntity> UserLikes(@PathVariable Long article_id) {
        return articleService.findLikeUsersArticle(article_id);
    }

    @PostMapping("/HomePage/AddLike")
    public ResponseEntity<?> newLike(@RequestParam("token") String token, @RequestParam("article_id") Long article_id) {
        try {
            articleService.AddLike(token, article_id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value="/HomePage/CommentsPerPost/{article_id}")
    public long AmountOfComments(@PathVariable Long article_id) {
        return articleService.findAmountofComments(article_id);
    }

    @PostMapping("/HomePage/AddComment")
    public ResponseEntity<?> newComment(@RequestParam("token") String token, @RequestParam("article_id") Long article_id,@RequestParam("comment") String comment) {
        try {
            articleService.AddComment(token, article_id, comment);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value="/HomePage/GetComments/{article_id}")
    public List<CommentDto> GetComments(@PathVariable Long article_id) {
        return articleService.findComments(article_id);
    }

}
