package com.tediproject.tedi.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Job;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ArticleRepo;
import com.tediproject.tedi.repo.CommentRepo;
import com.tediproject.tedi.repo.ConnectionRepo;
import com.tediproject.tedi.repo.JobRepo;
import com.tediproject.tedi.repo.LikeRepo;
import com.tediproject.tedi.repo.UserRepo;

@Service
public class RecommendationService {
    @Autowired
    LikeRepo likeRepo;
    
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ConnectionRepo connectionRepo;

    @Autowired
    NetworkService networkService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    JobRepo jobRepo;

    public Double[][] recommendationMatrixArticles(){
        List<UserEntity> users = userRepo.findAll();
        List<Article> articles = articleRepo.findAll();
        Double[][] matrix = new Double[users.size()][articles.size()];
        for(int i=0; i<users.size(); i++){
            List<Article> likedPosts = likeRepo.findLikedArticles(users.get(i));
            List<Article> commentedPosts = commentRepo.findArticles(users.get(i));
            List<UserEntity> connections = networkService.findUserConnections(users.get(i));
            List<Article> connectionsLiked = likeRepo.findLikedArticles(connections);
            List<Article> connectionsCommented = commentRepo.findCommentedArticles(connections);
            for(int j=0; j<articles.size(); j++){
                matrix[i][j] = -1.0;
                if(likedPosts.contains(articles.get(j))){
                    matrix[i][j] += 2.0;
                }
                if(commentedPosts.contains(articles.get(j))){
                    matrix[i][j] += 1.0;
                }
                if(connectionsLiked.contains(articles.get(j))){
                    int occurrencies = Collections.frequency(connectionsLiked, articles.get(j));
                    matrix[i][j] += (occurrencies*0.5);
                }
                if(connectionsCommented.contains(articles.get(j))){
                    int occurrencies = Collections.frequency(connectionsCommented, articles.get(j));
                    matrix[i][j] += (occurrencies*0.2);
                }
            }
        }
        
        // for(int i=0; i<users.size(); i++){
        //     for(int j=0; j<articles.size(); j++){
        //         System.out.print(matrix[i][j]+" ");
        //     }
        //     System.out.print('\n');
        // }

        return matrix;
    }

    public Double[][] recommendationMatrixJobs(){
        List<UserEntity> users = userRepo.findAll();
        List<Job> jobs = jobRepo.findAll();
        Double[][] matrix = new Double[users.size()][jobs.size()];
        for(int i=0; i<users.size(); i++){
            List<UserEntity> connections = networkService.findUserConnections(users.get(i));
            
            for(int j=0; j<jobs.size(); j++){
                
            }
        }
        return matrix;
    }
}
