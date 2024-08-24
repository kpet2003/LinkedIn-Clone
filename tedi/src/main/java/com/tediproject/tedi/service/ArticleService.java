package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.ArticleDto;
import com.tediproject.tedi.dto.NewArticleDto;
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Comments;
import com.tediproject.tedi.model.Likes;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ArticleRepo;
import com.tediproject.tedi.repo.CommentRepo;
import com.tediproject.tedi.repo.ConnectionRepo;
import com.tediproject.tedi.repo.LikeRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class ArticleService {
    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired 
    ConnectionRepo connectionRepo;

    @Autowired 
    LikeRepo likeRepo;

    @Autowired 
    CommentRepo commentRepo;



    public List<Article> findArticles(String token) {
        
        UserEntity author = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        
        // find articles written by user
        List <Long> network = connectionRepo.findByUser(author.getID());
        List <Article> articles = articleRepo.findByAuthor(author);
        
        // find articles that the user's connections have written
        List<UserEntity> connections = userRepo.findAllById(network);
        articles.addAll(articleRepo.findByAuthorIn(connections));



        // sort the articles from newest to oldest
        articles.sort(Comparator.comparing(Article::getDate_posted).reversed());


        return articles;
    }


    public void newArticle(NewArticleDto article) {
        UserEntity author = userRepo.findByEmail(jwtUtil.getEmailFromJWT(article.getAuthor_token()));
        Article new_article = new Article();

        new_article.setAuthor(author);
        new_article.setContent(article.getArticle_content());
        new_article.setTitle(article.getTitle());
        new_article.setDate_posted();
        new_article.setPicture(article.getImage());
        new_article.setVideo(article.getVideo());
        articleRepo.save(new_article);
    }

    public long findAmountofLikes(Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        return likeRepo.countByArticle(article);  
    }

    public List<UserEntity> findLikeUsersArticle(Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        return likeRepo.findUserEntityByArticle(article);
    }

    @Transactional
    public void AddLike(String token,Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        List <Likes> likes_found = likeRepo.findLikes(user, article);
        if(likes_found.isEmpty()) {
            Likes like = new Likes();
            like.setArticle(article);
            like.setUser(user);
            likeRepo.save(like);
            return;
        }

        likeRepo.deleteAll(likes_found);
    }

    @Transactional
    public void AddComment(String token,Long article_id,String Comment) {
        
        Article article = articleRepo.findById(article_id).get();
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        Comments new_comment = new Comments();

        new_comment.setArticle(article);
        new_comment.setPoster(user);
        new_comment.setComment(Comment);

        
        commentRepo.save(new_comment);
        
    }

    public long findAmountofComments(Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        return commentRepo.countByArticle(article); 
    }

    public List<Comments> findComments(Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        return commentRepo.findCommentsByArticle(article);
    }

    public List<ArticleDto> fetchArticles(String token) {
        
        List<Article> articles = findArticles(token);
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        List<ArticleDto> articles_data = new ArrayList<>();
        for(int i=0; i<articles.size(); i++) {
            
            ArticleDto article_data = new ArticleDto();
            article_data.setArticle(articles.get(i));

            Long article_id = articles.get(i).getId();
            
            article_data.setComments(findComments(article_id));
            article_data.setComments_count(findAmountofComments(article_id));
            article_data.setLikes_count(findAmountofLikes(article_id));

            List<UserEntity> users_who_liked = findLikeUsersArticle(article_id);


            Boolean hasLiked = users_who_liked.stream().anyMatch(likedUser -> likedUser.getID() == user.getID());



            article_data.setIsLikedByUser(hasLiked);
            article_data.setShowComments(false);
            articles_data.addLast(article_data);

        }

        



        return articles_data;
    }

    

}
