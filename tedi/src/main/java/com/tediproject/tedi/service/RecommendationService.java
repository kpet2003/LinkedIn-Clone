package com.tediproject.tedi.service;

import java.util.Collections;
import java.util.List;
import java.lang.Math;

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
    

    public Double[][] usersTable(Double w0,Double w1,Double w2,Double w3, Double w4) {


        List <UserEntity> users = userRepo.findAll();
        List <String> categories = articleRepo.findCategories();

        Double[][] matrix = new Double[users.size()][categories.size()];

        for(int i=0; i<users.size(); i++) {
            List <UserEntity> connections = networkService.findUserConnections(users.get(i));
            for(int j=0; j<categories.size(); j++) {
                matrix[i][j] = -1.0;
                
                int likes = likeRepo.findLikesPerCategory(users.get(i),categories.get(j));
                int comments = commentRepo.findCommentsPerCategory(users.get(i),categories.get(j));
                int views  = articleRepo.findViewsPerCategory(categories.get(j));

                int networkLikes = likeRepo.findConnectionLikesPerCategory(connections,categories.get(j));
                int networkComments = commentRepo.findConnectionCommentsPerCategory(connections,categories.get(j));
                
                matrix[i][j] = w0*likes+w1*comments+w2*views + w3*networkLikes+w4*networkComments;

            }
        }

        return matrix;
    }

    public Double[][] postTable() {
        List<Article> articles = articleRepo.findAll();
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


    public double costFunction(Double[][] true_matrix,Double[][] prediction) {
        double error = 0.0;

        int size = true_matrix.length* true_matrix[0].length;
        double coefficient = 1.0/size;
        
        for(int i=0; i<true_matrix.length; i++) {
            for(int j=0; j<true_matrix[0].length; j++) {
                error+= Math.pow((true_matrix[i][j]-prediction[i][j]),2);
            }
        }

        error*=coefficient;

        return error;
    }

    public double derivative(Double[][] true_matrix,Double[][] prediction,Double[][] users_table) {
        double error = 0.0;

        int size = true_matrix.length* true_matrix[0].length;
        double coefficient = -2.0/size;

        for(int i=0; i<true_matrix.length; i++) {
            for(int j=0; j<true_matrix[0].length; j++) {
            
                error+= (true_matrix[i][j]-prediction[i][j]);
            }
        }

        error*=coefficient;

        return error;
    }

    public Double[][] gradientDescent(Double[][] matrix,double learning_rate, int iterations, double error) {
        Double [][] users_table = usersTable(2.0, 1.0, 0.08, 0.5, 0.3);
        Double[][] post_table = transpose(postTable());

        double precision_error = 100.0;

        Double[][] prediction = prediction = arrayMultiplication(users_table, post_table);

        double w0=2.0;
        double w1=1.0;
        double w2 = 0.08;
        double w3 = 0.5;
        double w4 = 0.3;

        for(int i=0; i<iterations; i++) {
            
            if(precision_error<=error) {
                break;
            }
            
            w0 -=(learning_rate*derivative(matrix, prediction,users_table));
            w1 -=(learning_rate*derivative(matrix, prediction,users_table));
            w2 -=(learning_rate*derivative(matrix, prediction,users_table));
            w3 -=(learning_rate*derivative(matrix, prediction,users_table));
            w4 -=(learning_rate*derivative(matrix, prediction,users_table));
            

            users_table = usersTable(w0, w1, w2, w3, w4);
            System.out.println("\nIteration: "+ i + "\n");
            prediction = arrayMultiplication(users_table, post_table);
            precision_error = costFunction(matrix,prediction);
        }

        return prediction;

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
