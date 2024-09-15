package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.ArticleDto;
import com.tediproject.tedi.dto.CommentDto;
import com.tediproject.tedi.dto.NewArticleDto;
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Comments;
import com.tediproject.tedi.model.Likes;
import com.tediproject.tedi.model.Notification;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ArticleRepo;
import com.tediproject.tedi.repo.CommentRepo;
import com.tediproject.tedi.repo.LikeRepo;
import com.tediproject.tedi.repo.NotificationRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.types.ArticlePair;

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
    private NetworkService networkService;


    @Autowired 
    LikeRepo likeRepo;

    @Autowired 
    CommentRepo commentRepo;

    @Autowired 
    NotificationRepo notificationRepo;

    @Autowired
    RecommendationService recommendationService;

    public List<Article> findArticles(String token) {
        UserEntity author = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        Set<Article> articles = new HashSet<>();
        
        // find articles written by user
        articles.addAll(articleRepo.findByAuthor(author));
        
        // find articles that the user's connections have written
        List<UserEntity> connections = networkService.findUserConnections(token);
        articles.addAll(articleRepo.findByAuthorIn(connections));

        // find articles that the user's connections have liked
        articles.addAll(likeRepo.findLikedArticles(connections));

        // find articles that the user's connections have liked
        articles.addAll(commentRepo.findCommentedArticles(connections));

        
        Double[][] matrix = recommendationService.recommendationMatrixArticles();
        Double[][] prediction = recommendationService.gradientDescent(matrix, 0.001, 10000, 0.001);


        long id = author.getID();

        Double[] user_articles = prediction[(int)(id-1)];

        List<ArticlePair> article_data = new ArrayList<>();

        for(int i=0; i<user_articles.length; i++) {
            ArticlePair pair = new ArticlePair();
            pair.setId(i+1);
            pair.setRating(user_articles[i]);
            article_data.addLast(pair);
        }
        
         List<ArticlePair> topRatedArticles = article_data.stream()
        .sorted(Comparator.comparingDouble(ArticlePair::getRating).reversed())
        .limit(40)
        .collect(Collectors.toList());


        for(int i=0; i<topRatedArticles.size(); i++) {
            Article article = articleRepo.findById(topRatedArticles.get(i).getId());
            articles.add(article);
        }

        // sort the articles from newest to oldest
        List<Article> final_articles = new ArrayList<>(articles);

        // add a view for all articles fetched
        for(int i=0; i<final_articles.size(); i++) {
            Article article = final_articles.get(i);
            article.setViews(article.getViews()+1);
            articleRepo.save(article);
        }

        final_articles.sort(Comparator.comparing(Article::getDate_posted).reversed());



        return final_articles;
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
        new_article.setCategory("Computer Science");
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
            
            Notification notification = new Notification();
            notification.setIsComment(false);
            notification.setReceiver(article.getAuthor());
            notification.setSender(user);
            notification.setArticle(article);
            notificationRepo.save(notification);


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


        Notification notification = new Notification();
        notification.setIsComment(true);
        notification.setReceiver(article.getAuthor());
        notification.setSender(user);
        notification.setMessage(Comment);
        notification.setArticle(article);
        notificationRepo.save(notification);

        
        
        
    }

    public long findAmountofComments(Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        return commentRepo.countByArticle(article); 
    }

    public List<CommentDto> findComments(Long article_id) {
        Article article = articleRepo.findById(article_id).get();
        List<CommentDto> comments = new ArrayList<>();

        List <Comments> article_comments = commentRepo.findCommentsByArticle(article);

        for(int i=0; i<article_comments.size(); i++) {
            CommentDto comment = new CommentDto();
            comment.setId(article_comments.get(i).getId());
            comment.setPoster_id(article_comments.get(i).getPoster().getID());
            comment.setArticle_id(article_id);
            comment.setFirstName(article_comments.get(i).getPoster().getFirstName());
            comment.setLastName(article_comments.get(i).getPoster().getLastName());
            comment.setProfilePicture(article_comments.get(i).getPoster().getProfilePicture());
            comment.setContent(article_comments.get(i).getComment());
            comments.addLast(comment);
        }
        
        return comments;
    }

    public List<ArticleDto> fetchArticles(String token) {
        
        List<Article> articles = findArticles(token);
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        List<ArticleDto> articles_data = new ArrayList<>();
        for(int i=0; i<articles.size(); i++) {
            
            ArticleDto article_data = new ArticleDto();
            article_data.setId(articles.get(i).getId());
            article_data.setAuthorId(articles.get(i).getAuthor().getID());
            article_data.setAuthorFirstName(articles.get(i).getAuthor().getFirstName());
            article_data.setAuthorLastName(articles.get(i).getAuthor().getLastName());
            article_data.setProfilePicture(articles.get(i).getAuthor().getProfilePicture());
            article_data.setContent(articles.get(i).getContent());
            article_data.setTitle(articles.get(i).getTitle());
            article_data.setPicture(articles.get(i).getPicture());
            article_data.setVideo(articles.get(i).getVideo());
            

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
