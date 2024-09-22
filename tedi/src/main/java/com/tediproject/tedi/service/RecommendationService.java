package com.tediproject.tedi.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Job;
import com.tediproject.tedi.model.Skills;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ArticleRepo;
import com.tediproject.tedi.repo.CommentRepo;
import com.tediproject.tedi.repo.JobRepo;
import com.tediproject.tedi.repo.LikeRepo;
import com.tediproject.tedi.repo.SkillsRepo;
import com.tediproject.tedi.repo.UserRepo;

@Service
public class RecommendationService {
    @Autowired
    LikeRepo likeRepo;
    
    @Autowired
    CommentRepo commentRepo;

    @Autowired
    NetworkService networkService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    JobRepo jobRepo;

    @Autowired
    SkillsRepo skillsRepo;

    // find the transpose of a matrix
    public Double[][] transpose(Double[][] array) {
        Double [][] transpose_matrix = new Double[array[0].length][array.length];
        
        for(int i=0; i<array.length; i++) {
            for(int j=0; j<array[0].length; j++) {
                transpose_matrix[j][i] = array[i][j];
            }
        }
        

        return transpose_matrix;
    }
    
    // calculate the dot product of two vectors
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



    // perform matrix multiplication
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
    
    // build the users table(rows:users, columns: article categories); 
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

    // build the post table(rows:posts, columns: article categories) if the post i has j category set matrix[i][j] = 1, else matrix[i][j]=0
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

    // build the users table(rows:users, columns: skills)
    public Double[][] usersTableJobs() {

        List <UserEntity> users = userRepo.findAllByOrderByIdAsc();
        List <Skills> skills = skillsRepo.findAll();

        Double[][] matrix = new Double[users.size()][skills.size()];

        for(int i=0; i<users.size(); i++) {
            for(int j=0; j<skills.size(); j++) {
                matrix[i][j] = Math.random();
            }
        }

        return matrix;
    }


    // build the jobs table(rows:jobs, columns: skills) if the job i has j relevant skill set matrix[i][j] = 1, else matrix[i][j]=0
    public Double[][] jobsTable() {
        List<Job> jobs =  jobRepo.findAllByOrderByIdAsc();
        List <Skills> skills = skillsRepo.findAll();

        Double[][] matrix = new Double[jobs.size()][skills.size()];

        for(int i=0; i<jobs.size(); i++) {
            for(int j=0; j<skills.size();j++) {
                matrix[i][j] = 0.0;
                if(jobs.get(i).getRelevant_skills().contains(skills.get(j))){
                    matrix[i][j] = 1.0;
                }
            }
        }
        return matrix;

    }

    // calculate the error of the gradient descent function
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

    // approximate the post ratings matrix using gradient descent algorithm
    public Double[][] gradientDescentPosts(Double[][] matrix,double learning_rate, int iterations,double max_error) {
        Double [][] users_table = usersTable();
        Double[][] post_table = postTable();


        double precision_error = 100.0;

        // the algorithm runs for a specified number of iterations, or until the error is less or equal to max_error
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

        // the approximated ratings table is calculated by multiplying the users table and the post table after gradient descent 
        return arrayMultiplication(users_table, transpose(post_table));

    }

     // approximate the job ratings matrix using gradient descent algorithm
    public Double[][] gradientDescentJobs(Double[][] matrix,double learning_rate, int iterations,double max_error) {
        Double [][] users_table = usersTableJobs();
        Double[][] jobs_table = jobsTable();


        double precision_error = 100.0;

        // the algorithm runs for a specified number of iterations, or until the error is less or equal to max_error
        for(int i=0; i<iterations; i++) {
            
            if(precision_error<=max_error) {
                break;
            }
 

            for(int j=0; j<users_table.length;j++) {
                for(int k=0; k<jobs_table.length; k++) {
                    
                    double prediction = dot(users_table[j],jobs_table[k]);
                    double error = matrix[j][k]-prediction;

                    for(int f = 0; f<users_table[0].length; f++) {
                        users_table[j][f] += learning_rate*error*jobs_table[k][f];
                        jobs_table[k][f] += learning_rate*error*users_table[j][f];
                    }

                }
            }

            precision_error = costFunction(matrix,users_table ,jobs_table);

        }
        // the approximated ratings table is calculated by multiplying the users table and the job table after gradient descent 
        return arrayMultiplication(users_table, transpose(jobs_table));

    }

    // populate the ratings table(with users for rows and articles for columns)
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
                // every cell is initialized to -1
                matrix[i][j] = -1.0;

                // if the article j is liked by user i, matrix[i][j] += 2.0
                if(likedPosts.contains(articles.get(j))){
                    matrix[i][j] += 2.0;
                }
                // if the article j has comments posted by user i, matrix[i][j] += 1.0
                if(commentedPosts.contains(articles.get(j))){
                    matrix[i][j] += 1.0;
                }

                // matrix[i][j]+= #(connections_who_liked_article_j)*0.5;
                if(connectionsLiked.contains(articles.get(j))){
                    int occurrencies = Collections.frequency(connectionsLiked, articles.get(j));
                    matrix[i][j] += (occurrencies*0.5);
                }
                // matrix[i][j]+= #(connection_comments_on_article_j)*0.2;
                if(connectionsCommented.contains(articles.get(j))){
                    int occurrencies = Collections.frequency(connectionsCommented, articles.get(j));
                    matrix[i][j] += (occurrencies*0.2);
                }
            }
        }
      

        return matrix;
    }

    //  populate the ratings table(with users for rows and jobs for columns)
    public Double[][] recommendationMatrixJobs(){
        List<UserEntity> users = userRepo.findAll();
        List<Job> jobs = jobRepo.findAll();
        Double[][] matrix = new Double[users.size()][jobs.size()];

        for(int i=0; i<users.size(); i++){
            List<Skills> userSkills = users.get(i).getUser_skills();
            List<Job> jobsViewed = users.get(i).getJobs_viewed();
            for(int j=0; j<jobs.size(); j++){
                // every cell is initialized to -1
                matrix[i][j] = -1.0;
                List<Skills> jobSkills = jobs.get(j).getRelevant_skills();
                // if user i has skill j, matrix[i][j]+=2
                for(Skills skill:userSkills){
                    if(jobSkills.contains(skill)){
                        matrix[i][j] += 2.0;
                    }
                }
                // find how many times user i has viewed job j
                int views = Collections.frequency(jobsViewed, jobs.get(j));

                matrix[i][j] += 0.5*views;
            }
        }

        return matrix;
    }
}
