package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Likes;
import com.tediproject.tedi.model.UserEntity;

public interface LikeRepo extends JpaRepository<Likes, Long>{
    long countByArticle(Article article);

    @Query("SELECT l.user FROM Likes l WHERE l.article = ?1")
    List <UserEntity> findUserEntityByArticle(Article article);

    @Query("SELECT l FROM Likes l WHERE l.article = ?2 AND l.user=?1")
    List <Likes> findLikes(UserEntity user,Article article);

    @Query("SELECT l.article FROM Likes l WHERE l.user = ?1")
    List<Article> findLikedArticles( UserEntity user);

    @Query("SELECT l.article FROM Likes l WHERE l.user IN ?1")
    List<Article> findLikedArticles( List<UserEntity> connections);
}

