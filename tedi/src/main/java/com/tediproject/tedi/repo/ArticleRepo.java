package com.tediproject.tedi.repo;

import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.UserEntity;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    List<Article> findByAuthor(UserEntity author);
}
