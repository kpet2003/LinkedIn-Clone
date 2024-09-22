package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.ApplicantDto;
import com.tediproject.tedi.dto.JobDto;
import com.tediproject.tedi.dto.NewJobDto;
import com.tediproject.tedi.model.Job;
import com.tediproject.tedi.model.Skills;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.JobRepo;
import com.tediproject.tedi.repo.SkillsRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.types.ArticlePair;

import jakarta.transaction.Transactional;

@Service
public class JobService {

    //needed components
    
    @Autowired
    UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired 
    JobRepo jobRepo;

    @Autowired
    SkillsRepo skillsRepo;

    @Autowired
    NetworkService networkService;

    @Autowired
    RecommendationService recommendationService;

    //function to create a new job
    public void createJob(NewJobDto job) {
        Job j = new Job(); //make a new job and set its data
        j.setAuthor(userRepo.findByEmail(jwtUtil.getEmailFromJWT(job.getAuthorToken())));
        j.setJob_title(job.getJobTitle());
        j.setJob_description(job.getJobDesc());
        List<Skills> jobSkills = new ArrayList<>();
        for(String skill: job.getJobSkills()){ //for every skill in the new job
            Skills s = skillsRepo.findBySkill(skill); //look for it in the existing skills
            if(s==null){ //if it does not exist add it
                s = new Skills();
                s.setSkill(skill);
                skillsRepo.save(s);
            }
            jobSkills.add(s); //add it to the new job's skills list in both cases
        }
        j.setRelevant_skills(jobSkills); //set the skills of the new job
        jobRepo.save(j); //save the new job
    }

    //function to get all jobs in the database
    public List<JobDto> getJobs(String token){
        List<JobDto> jobs = new ArrayList<>(); //create a list to keep jobs
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token)); //find user in database
        List<UserEntity> connections = networkService.findUserConnections(token); //get user's connections
        Double[][] matrix = recommendationService.recommendationMatrixJobs(); //make recommandation matrix
        //get recommendations
        Double[][] recommendation = recommendationService.gradientDescentJobs(matrix, 0.001, 10000, 0.001);

        long id = user.getID(); //get user id

        Double[] user_jobs = recommendation[(int)(id-1)]; //get the recommended jobs for this user

        List<ArticlePair> job_data = new ArrayList<>(); //list of jobs

        for(int i=0; i<user_jobs.length; i++) { //for all jobs create a pair of their id and their rating
            ArticlePair pair = new ArticlePair();
            pair.setId(i+1);
            pair.setRating(user_jobs[i]);
            job_data.addLast(pair);
        }
        
        //sort jobs based on rating
         List<ArticlePair> topRatedJobs = job_data.stream()
        .sorted(Comparator.comparingDouble(ArticlePair::getRating).reversed())
        .collect(Collectors.toList());

        //keep the 40 top rated jobs
        int count = 0;
        for(ArticlePair job:topRatedJobs){
            Job j = jobRepo.findById(job.getId()); //create  and keep a dto for each job added
            String author = j.getAuthor().getFirstName()+" "+j.getAuthor().getLastName();
            JobDto jobDto = new JobDto(author, j.getJob_title(), j.getJob_description(), j.getDate_posted(), j.getId(), j.getAuthor().getID());
            jobs.add(jobDto);
            if(connections.contains(j.getAuthor()) || (j.getAuthor().getID() == id)){
                continue; //jobs the user or their connections have posted do not add to the count
            }
            count++;
            if(count >= 40){ //once count reaches 40 stop adding jobs
                break;
            }
        }

        return jobs;
    }

    //function to return the jobs the user's connections have posted
    public List<JobDto> getConnectionsJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token)); //find the user
        List<UserEntity> connectedUsers = networkService.findUserConnections(token); //find their connections
        //get the id of each connection
        List<Long> connectionIds = connectedUsers.stream()
                                                .map(UserEntity::getID)
                                                .collect(Collectors.toList());
        List<JobDto> connectedJobs = new ArrayList<>(); //list to keep the jobs we want
        for(JobDto job:allJobs){ //for each job
            Job j = jobRepo.findById(job.getJobId());
            if(connectionIds.contains(job.getAuthorId()) && !connectedJobs.contains(job)){ //if the author is in the connections and job has not been added yet
                if(!j.getApplicants().contains(user)){ //and if the user has not applied for it
                    connectedJobs.add(job); //add the job to the list
                }
            }
        }
        return connectedJobs;
    }

    //function to get the jobs that users not in the user's connections have posted
    public List<JobDto> getOtherJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token)); //find user
        List<UserEntity> connectedUsers = networkService.findUserConnections(token); //find user's connections
        //keep connections ids
        List<Long> connectionIds = connectedUsers.stream()
                                                .map(UserEntity::getID)
                                                .collect(Collectors.toList());
        List<JobDto> otherJobs = new ArrayList<>(); //list to keep the jobs we want
        for(JobDto job:allJobs){ //for each job
            Job j = jobRepo.findById(job.getJobId());
            if(!connectionIds.contains(job.getAuthorId()) && !otherJobs.contains(job)){ //if the author is not in the connections and job has not been added yet
                if(!j.getApplicants().contains(user) && j.getAuthor().getID() != user.getID()){ //if the user has not applied for it and is not the author of it
                    otherJobs.add(job); //add the job
                }
            }
        }
        return otherJobs;
    }

    //function to get the jobs a user has posted
    public List<JobDto> getMyJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token)); //find user
        List<JobDto> myJobs = new ArrayList<>(); //list to keep the jobs we want
        for(JobDto job:allJobs){ //for each job
            if(job.getAuthorId() == user.getID()){ //if the user is the author
                myJobs.add(job); //add it
            }
        }
        return myJobs;
    }

    //function to get the jobs the user has applier for
    public List<JobDto> getAppliedJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token)); //find user
        List<JobDto> appliedJobs = new ArrayList<>(); //list for the jobs we want
        for(JobDto job:allJobs){ //for each job
            Job j = jobRepo.findById(job.getJobId());
            if(j.getApplicants().contains(user)){ //if the user is in the applicants
                appliedJobs.add(job); //add it
            }
        }
        return appliedJobs;
    }

    //function to add an application to a job
    @Transactional
    public void applyForJob(long jobId, String userToken) {
        Job job = jobRepo.findById(jobId); //find job
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(userToken)); //find user applying
        
        if (!job.getApplicants().contains(user)) { //if user is not in the applicants
            job.getApplicants().add(user); //add them
        }

        if (!user.getJobs_applied().contains(job)) { //if job is not in the jobs the user has applied for
            user.getJobs_applied().add(job); //add it
        }
        
        //save the changes
        jobRepo.save(job);
        userRepo.save(user);
    }

    //function to return the applicants of a job
    public List<ApplicantDto> getApplicants(long jobId){
        Job job = jobRepo.findById(jobId); //find job
        List<ApplicantDto> applicants = new ArrayList<>(); //list to keep applicants
        List<UserEntity> temp = new ArrayList<>(); //list of users
        temp = job.getApplicants(); //keep the applicants in the users list
        for(UserEntity user: temp){ //for each user
            //create a dto and add it to the applicants list
            String name = user.getFirstName()+" "+user.getLastName();
            String base64Image = null;
            if (user.getProfilePicture() != null) {
                base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
            }
            ApplicantDto app = new ApplicantDto(user.getID(), name, base64Image);
            applicants.add(app);
        }
        return applicants;
    }
 
    //function to add a view to a job
    public void addView(long jobId, String token) {
       Job job = jobRepo.findById(jobId); //find the job
       UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token)); //get the user that viewed it

       if (!job.getViews().contains(user)) { //if not already added add user to job's views
        job.getViews().add(user);
        }

        if (!user.getJobs_viewed().contains(job)) { //if not already added add job to user's views
            user.getJobs_viewed().add(job);
        }
        
        //save changes
        jobRepo.save(job);
        userRepo.save(user);
    }

}
