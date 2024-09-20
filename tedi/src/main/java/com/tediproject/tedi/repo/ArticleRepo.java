package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.UserEntity;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    // find the articles written by user
    List<Article> findByAuthor(UserEntity author);
    
    // find articles written by users in the list 
    List<Article> findByAuthorIn(List<UserEntity> connections);
    
    // find article by id
    Article findById(long id);

    // find all the possible categories of an article
    @Query("SELECT DISTINCT a.category FROM Article a")
    List <String> findCategories();

    // find the total views per category 
    @Query("select sum(views) from Article where category = ?1 group by category ")
    int findViewsPerCategory(String category);

    // find all articles sorted by their ids in ascending order
    List<Article> findAllByOrderByIdAsc();

}
