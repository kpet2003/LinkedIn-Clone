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


    public Double[][] transpose(Double[][] array) {
        Double [][] transpose_matrix = new Double[array[0].length][array.length];
        
        for(int i=0; i<array.length; i++) {
            for(int j=0; j<array[0].length; j++) {
                transpose_matrix[j][i] = array[i][j];
            }
        }
        

        return transpose_matrix;
    }
    

    public Double dot(Double[]row, Double[] column) {
        
        if(row.length != column.length) {
            throw new IllegalArgumentException("Vectors must be of the same length.");
        }

        double product = 0;

        for(int i=0; i<row.length; i++) {
            product+=row[i]*column[i];
        }



        return product;
    }




    public Double[][] arrayMultiplication(Double[][] a, Double[][] b ) {
        if(a[0].length != b.length) {
            throw new IllegalArgumentException("Arrays should have matching dimensions.");
        }

        Double[][] matrix = new Double[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                matrix[i][j]=0.0;
                for (int k = 0; k < b.length; k++)
                matrix[i][j] += a[i][k] * b[k][j];
            }
        }


        return matrix;
    }

    public void print(Double[][] array) {
        for(int i=0; i<array.length; i++) {
            for(int j=0; j<array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.print("\n");
        }
        
    }
    

    public Double[][] usersTable() {


        List <UserEntity> users = userRepo.findAllByOrderByIdAsc();
        List <String> categories = articleRepo.findCategories();

        Double[][] matrix = new Double[users.size()][categories.size()];

        for(int i=0; i<users.size(); i++) {
            for(int j=0; j<categories.size(); j++) {
                matrix[i][j] = Math.random();
            }
        }

        return matrix;
    }

    public Double[][] postTable() {
        List<Article> articles = articleRepo.findAllByOrderByIdAsc();
        List <String> categories = articleRepo.findCategories();

        Double[][] matrix = new Double[articles.size()][categories.size()];

        for(int i=0; i<articles.size(); i++) {
            for(int j=0; j<categories.size();j++) {
                matrix[i][j] = 0.0;
                if(articles.get(i).getCategory().equals(categories.get(j))) {
                    matrix[i][j] = 1.0;
                }
            }
        }

        return matrix;

    }


    public double costFunction(Double[][] ratings,Double[][] users_table,Double[][] post_table) {
        double error = 0.0;

        int size = ratings.length* ratings[0].length;
        double coefficient = 1.0/size;
        
        for(int i=0; i<ratings.length; i++) {
            for(int j=0; j<ratings[0].length; j++) {
                double prediction = dot(users_table[i], post_table[j]);
                double cost = ratings[i][j] - prediction;
                error += cost * cost;
            }
        }

        error*=coefficient;

        return error;
    }

    public double finalCost(Double[][] ratings, Double[][] matrix) {
        int size = ratings.length * ratings[0].length;

        double coefficient = 1.0/size;

        double error = 0.0;
        for(int i=0; i<ratings.length; i++) {
            for(int j=0; j<ratings[0].length; j++) {
                error+= (matrix[i][j]-ratings[i][j])* (matrix[i][j]-ratings[i][j]);
            }
        }


        return error*coefficient;
    }



    public Double[][] gradientDescent(Double[][] matrix,double learning_rate, int iterations,double max_error) {
        Double [][] users_table = usersTable();
        Double[][] post_table = postTable();


        double precision_error = 100.0;

        for(int i=0; i<iterations; i++) {
            
            if(precision_error<=max_error) {
                break;
            }
 

            for(int j=0; j<users_table.length;j++) {
                for(int k=0; k<post_table.length; k++) {
                    
                    double prediction = dot(users_table[j],post_table[k]);
                    double error = matrix[j][k]-prediction;

                    for(int f = 0; f<users_table[0].length; f++) {
                        users_table[j][f] += learning_rate*error*post_table[k][f];
                        post_table[k][f] += learning_rate*error*users_table[j][f];
                    }

                }
            }

            precision_error = costFunction(matrix,users_table ,post_table);

        }

        return arrayMultiplication(users_table, transpose(post_table));

    }

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
