package com.tediproject.tedi.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.NewArticleDto;
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Likes;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ArticleRepo;
import com.tediproject.tedi.repo.ConnectionRepo;
import com.tediproject.tedi.repo.LikeRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;

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

    public void AddLike(String token,Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        List <Likes> likes_found = likeRepo.findLikes(user, article);

        if(likes_found.size() == 0 ) {
            Likes like = new Likes();
            like.setArticle(article);
            like.setUser(user);
            likeRepo.save(like);
            return;
        }

        likeRepo.deleteAll(likes_found);

    }

}
