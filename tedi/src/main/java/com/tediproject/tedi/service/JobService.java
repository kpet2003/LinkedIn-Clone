package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Job> allJobs = jobRepo.findAll();
        List<String> userSkills = user.getUser_skills().stream()
                                  .map(Skills::getSkill)
                                  .collect(Collectors.toList());

        Map<Job, Integer> jobSkillCountMap = new HashMap<>();

        for(Job job: allJobs){
            List<String> jobSkills = job.getRelevant_skills().stream()
                                    .map(Skills::getSkill)
                                    .collect(Collectors.toList());
            
            long matchingSkillsCount = jobSkills.stream()
                                        .filter(userSkills::contains)
                                        .count();
            
            jobSkillCountMap.put(job, (int) matchingSkillsCount);
            
        }
        List<Job> sortedJobs = jobSkillCountMap.entrySet().stream()
        .sorted((entry1, entry2) -> {
            // First, compare by matching skills (descending)
            int skillCompare = entry2.getValue().compareTo(entry1.getValue());
            
            // If skill counts are equal, compare by date posted (descending)
            if (skillCompare == 0) {
                return entry2.getKey().getDate_posted().compareTo(entry1.getKey().getDate_posted());
            }
            return skillCompare;
        })
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

        for (Job job : sortedJobs) {
            String author = job.getAuthor().getFirstName() + " " + job.getAuthor().getLastName();
            JobDto j = new JobDto(author, job.getJob_title(), job.getJob_description(), job.getDate_posted(), job.getId(), job.getAuthor().getID());
            jobs.add(j);
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
            System.out.println("WHEN JOB AUTHOR IS "+job.getAuthorId());
            if(job.getAuthorId() == user.getID()){
                System.out.println("MYJOBS: ADDED "+user.getID());
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
