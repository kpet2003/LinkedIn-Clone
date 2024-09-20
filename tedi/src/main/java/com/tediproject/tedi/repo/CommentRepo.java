package com.tediproject.tedi.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Comments;
import com.tediproject.tedi.model.UserEntity;



public interface CommentRepo extends JpaRepository<Comments, Long>{

    // find amount of comments in the article
    long countByArticle(Article article);
    
    // find users that have posted a comment to the article
    @Query("SELECT c.poster FROM Comments c WHERE c.article = ?1")
    List <UserEntity> findUserEntityByArticle(Article article);

    // find comments posted to the article
    @Query("SELECT c FROM Comments c WHERE c.article = ?1")
    List <Comments> findCommentsByArticle(Article article);

    // find articles to which there is at least a comment posted by a user in the list
    @Query("SELECT c.article FROM Comments c  WHERE c.poster IN ?1")
    List<Article> findCommentedArticles( List<UserEntity> connections);

    // find comments posted by user
    @Query("SELECT c FROM Comments c  WHERE c.poster = ?1 ")
    public List<Comments> findByPoster(UserEntity user);

    // find articles to which the user has posted
    @Query("SELECT c.article FROM Comments c WHERE c.poster = ?1")
    public List<Article> findArticles(UserEntity user);

    // find the amount of comments posted by the user, in articles that have the category specified
    @Query("SELECT COUNT(c) FROM Comments c JOIN c.article a WHERE c.poster = ?1 AND a.category = ?2")    
    int findCommentsPerCategory(UserEntity user, String category);

    // find the amount of comments posted by users in the list, in articles that have the category specified
    @Query("SELECT COUNT(c) FROM Comments c JOIN c.article a WHERE c.poster IN ?1 AND a.category = ?2")    
    int findConnectionCommentsPerCategory(List<UserEntity> connections, String category);

}

