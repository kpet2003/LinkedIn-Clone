package com.tediproject.tedi.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.AdminDto;
import com.tediproject.tedi.dto.NetworkDto;
import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Comments;
import com.tediproject.tedi.model.Education;
import com.tediproject.tedi.model.Experience;
import com.tediproject.tedi.model.Job;
import com.tediproject.tedi.model.Skills;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ArticleRepo;
import com.tediproject.tedi.repo.CommentRepo;
import com.tediproject.tedi.repo.JobRepo;
import com.tediproject.tedi.repo.LikeRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.types.CommentInfo;


@Service
public class AdminService {
   
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private NetworkService networkService;

    @Autowired
    private CommentRepo commentRepo;




    public List<AdminDto> findAllUsers() {
        List<UserEntity> users =  userRepo.findAll();  

        List<AdminDto> userData = new ArrayList<>();

        for(int i=0; i<users.size(); i++) {
            AdminDto user = new AdminDto();
            user.setFirstName(users.get(i).getFirstName());
            user.setLastName(users.get(i).getLastName());
            user.setId(users.get(i).getID());
            user.setEmail(users.get(i).getEmail());
            user.setPhone_number(users.get(i).getPhoneNumber());
            user.setWorkTitle(users.get(i).getWorkTitle());
            user.setWorkplace(users.get(i).getWorkplace());
            user.setSkills(this.getUserSkills(users.get(i)));
            user.setEducation(this.getUserEducation(users.get(i)));
            user.setExperience(this.getUserExperience(users.get(i)));
            user.setArticles_posted(this.getUserArticles(users.get(i)));
            user.setArticles_liked(this.getUserLikes(users.get(i)));
            user.setComments_posted(this.getUserComments(users.get(i)));
            user.setJobs_posted(this.getUserJobs(users.get(i)));
            user.setConnections(this.getUserConnections(users.get(i)));
           


            userData.addLast(user);
        }

        return userData;

    }

    private List<String> getUserLikes(UserEntity user) {
        List<Article> articles = likeRepo.findLikedArticles(user);
        
        List<String> titles = new ArrayList<>();
        
        for(int i=0; i<articles.size(); i++) {
            titles.addLast(articles.get(i).getTitle());
        } 
        
        return titles;
    }

    private List<String> getUserArticles(UserEntity user) {
        List<Article> articles = articleRepo.findByAuthor(user);

         
        List<String> titles = new ArrayList<>();
        
        for(int i=0; i<articles.size(); i++) {
            titles.addLast(articles.get(i).getTitle());
        } 
        
        return titles;

    }

    private List<String> getUserJobs(UserEntity user) {
        List<Job> jobs  = jobRepo.findByAuthor(user);
        List<String> job_titles = new ArrayList<>();

        for(int i=0; i<jobs.size(); i++) {
            job_titles.addLast(jobs.get(i).getJob_title());
        } 


        return job_titles;
    }

    private List<String> getUserConnections(UserEntity user) {
        List <NetworkDto> connections = networkService.findConnectionsById(user.getID());
        List <String> connection_names = new ArrayList<>();

        for(int i=0; i<connections.size(); i++) {
            String name = connections.get(i).getFirstName() + " " + connections.get(i).getLastName();
            connection_names.addLast(name);
        }

        return connection_names;
    }

    private List<String> getUserSkills(UserEntity user) {
        List<Skills> skills =  user.getUser_skills();
        List <String> user_skills = new ArrayList<>();
        for(int i=0; i<skills.size(); i++) {
            user_skills.addLast(skills.get(i).getSkill());
        }
        return user_skills;
    }

    private List<String> getUserEducation(UserEntity user) {
        List<Education> edu = user.getUser_education();

        List <String> user_education = new ArrayList<>();
        for(int i=0; i<edu.size(); i++) {
            user_education.addLast(edu.get(i).getEducation());
        }
        return user_education;
    }

    private List<String> getUserExperience(UserEntity user) {
        List<Experience> exp = user.getUser_experience();
        List <String> experience = new ArrayList<>();

        for(int i=0; i<exp.size(); i++) {
            experience.addLast(exp.get(i).getExperience());
        }
        return experience;

    }

    private List<CommentInfo> getUserComments(UserEntity user) {
        List<Comments> comments =  commentRepo.findByPoster(user);

        List <CommentInfo> user_comments = new ArrayList<>();

        for(int i=0; i<comments.size(); i++) {
            CommentInfo comment = new CommentInfo();
            comment.setComment(comments.get(i).getComment());
            comment.setArticle_title(comments.get(i).getArticle().getTitle());
            user_comments.addLast(comment);
        }

        return user_comments;
    }

}
