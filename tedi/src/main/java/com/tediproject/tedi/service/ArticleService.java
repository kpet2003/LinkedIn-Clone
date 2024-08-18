package com.tediproject.tedi.service;

import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ArticleRepo;
import com.tediproject.tedi.repo.ConnectionRepo;
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


    public void newArticle() {
        
    }

}
