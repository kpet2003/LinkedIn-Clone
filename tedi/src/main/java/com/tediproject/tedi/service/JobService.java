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

    public void createJob(NewJobDto job) {
        Job j = new Job();
        j.setAuthor(userRepo.findByEmail(jwtUtil.getEmailFromJWT(job.getAuthorToken())));
        j.setJob_title(job.getJobTitle());
        j.setJob_description(job.getJobDesc());
        List<Skills> jobSkills = new ArrayList<>();
        for(String skill: job.getJobSkills()){
            Skills s = skillsRepo.findBySkill(skill);
            if(s==null){
                s = new Skills();
                s.setSkill(skill);
                skillsRepo.save(s);
            }
            jobSkills.add(s);
        }
        j.setRelevant_skills(jobSkills);
        jobRepo.save(j);
    }

    public List<JobDto> getJobs(String token){
        List<JobDto> jobs = new ArrayList<>();
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> connections = networkService.findUserConnections(token);
        Double[][] matrix = recommendationService.recommendationMatrixJobs();
        Double[][] recommendation = recommendationService.gradientDescentJobs(matrix, 0.001, 10000, 0.001);

        long id = user.getID();

        Double[] user_jobs = recommendation[(int)(id-1)];

        List<ArticlePair> job_data = new ArrayList<>();

        for(int i=0; i<user_jobs.length; i++) {
            ArticlePair pair = new ArticlePair();
            pair.setId(i+1);
            pair.setRating(user_jobs[i]);
            job_data.addLast(pair);
        }
        
         List<ArticlePair> topRatedJobs = job_data.stream()
        .sorted(Comparator.comparingDouble(ArticlePair::getRating).reversed())
        .collect(Collectors.toList());

        int count = 0;
        for(ArticlePair job:topRatedJobs){
            Job j = jobRepo.findById(job.getId());
            String author = j.getAuthor().getFirstName()+" "+j.getAuthor().getLastName();
            JobDto jobDto = new JobDto(author, j.getJob_title(), j.getJob_description(), j.getDate_posted(), j.getId(), j.getAuthor().getID());
            jobs.add(jobDto);
            if(connections.contains(j.getAuthor()) || (j.getAuthor().getID() == id)){
                continue;
            }
            count++;
            if(count >= 40){
                break;
            }
        }

        return jobs;
    }

    public List<JobDto> getConnectionsJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> connectedUsers = networkService.findUserConnections(token);
        List<Long> connectionIds = connectedUsers.stream()
                                                .map(UserEntity::getID)
                                                .collect(Collectors.toList());
        List<JobDto> connectedJobs = new ArrayList<>();
        for(JobDto job:allJobs){
            Job j = jobRepo.findById(job.getJobId());
            if(connectionIds.contains(job.getAuthorId()) && !connectedJobs.contains(job)){
                if(!j.getApplicants().contains(user)){
                    connectedJobs.add(job);
                }
            }
        }
        return connectedJobs;
    }

    public List<JobDto> getOtherJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> connectedUsers = networkService.findUserConnections(token);
        List<Long> connectionIds = connectedUsers.stream()
                                                .map(UserEntity::getID)
                                                .collect(Collectors.toList());
        List<JobDto> otherJobs = new ArrayList<>();
        for(JobDto job:allJobs){
            Job j = jobRepo.findById(job.getJobId());
            if(!connectionIds.contains(job.getAuthorId()) && !otherJobs.contains(job)){
                if(!j.getApplicants().contains(user) && j.getAuthor().getID() != user.getID()){
                    otherJobs.add(job);
                }
            }
        }
        return otherJobs;
    }

    public List<JobDto> getMyJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<JobDto> myJobs = new ArrayList<>();
        for(JobDto job:allJobs){
            if(job.getAuthorId() == user.getID()){
                myJobs.add(job);
            }
        }
        return myJobs;
    }

    public List<JobDto> getAppliedJobs(List<JobDto> allJobs, String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<JobDto> appliedJobs = new ArrayList<>();
        for(JobDto job:allJobs){
            Job j = jobRepo.findById(job.getJobId());
            if(j.getApplicants().contains(user)){
                appliedJobs.add(job);
            }
        }
        return appliedJobs;
    }

    @Transactional
    public void applyForJob(long jobId, String userToken) {
        // Retrieve the job and user entities
        Job job = jobRepo.findById(jobId);
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(userToken));
        
        if (!job.getApplicants().contains(user)) {
            job.getApplicants().add(user);
        }

        if (!user.getJobs_applied().contains(job)) {
            user.getJobs_applied().add(job);
        }
        
        // Save the updated entities
        jobRepo.save(job);
        userRepo.save(user);
    }

    public List<ApplicantDto> getApplicants(long jobId){
        Job job = jobRepo.findById(jobId);
        List<ApplicantDto> applicants = new ArrayList<>();
        List<UserEntity> temp = new ArrayList<>();
        temp = job.getApplicants();
        for(UserEntity user: temp){
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

    public void addView(long jobId, String token) {
       Job job = jobRepo.findById(jobId);
       UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

       if (!job.getViews().contains(user)) {
        job.getViews().add(user);
        }

        if (!user.getJobs_viewed().contains(job)) {
            user.getJobs_viewed().add(job);
        }
        
        // Save the updated entities
        jobRepo.save(job);
        userRepo.save(user);
    }

}
