package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Likes;

public interface LikeRepo extends JpaRepository<Likes, Long>{
    long countByArticle(Article article);
}
