package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.UserEntity;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    
    List<Article> findByAuthor(UserEntity author);
    List<Article> findByAuthorIn(List<UserEntity> connections);
    Article findById(long id);

    @Query("SELECT DISTINCT a.category FROM Article a")
    List <String> findCategories();

    @Query("select sum(views) from Article where category = ?1 group by category ")
    int findViewsPerCategory(String category);

    List<Article> findAllByOrderByIdAsc();

}
