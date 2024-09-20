package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Likes;
import com.tediproject.tedi.model.UserEntity;

public interface LikeRepo extends JpaRepository<Likes, Long>{
    
    // find amount of likes in article
    long countByArticle(Article article);

    // find users that have liked the article
    @Query("SELECT l.user FROM Likes l WHERE l.article = ?1")
    List <UserEntity> findUserEntityByArticle(Article article);

    // find likes by user to article
    @Query("SELECT l FROM Likes l WHERE l.article = ?2 AND l.user=?1")
    List <Likes> findLikes(UserEntity user,Article article);

    // find the articles that the user has liked
    @Query("SELECT l.article FROM Likes l WHERE l.user = ?1")
    List<Article> findLikedArticles( UserEntity user);

    // find the articles that the users in the list have liked
    @Query("SELECT l.article FROM Likes l WHERE l.user IN ?1")
    List<Article> findLikedArticles( List<UserEntity> connections);

    // find the total amount of likes the user has made in articles of the category specified 
    @Query("SELECT COUNT(l) FROM Likes l JOIN l.article a WHERE l.user = ?1 AND a.category = ?2")    
    int findLikesPerCategory(UserEntity user, String category);

    // find the total amount of likes the users in the list have made in articles of the category specified 
    @Query("SELECT COUNT(l) FROM Likes l JOIN l.article a WHERE l.user IN ?1 AND a.category = ?2")    
    int findConnectionLikesPerCategory(List<UserEntity> connections, String string);
}

