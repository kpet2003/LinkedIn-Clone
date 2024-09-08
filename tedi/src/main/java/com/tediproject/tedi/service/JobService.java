package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.JobDto;
import com.tediproject.tedi.dto.NewJobDto;
import com.tediproject.tedi.model.Job;
import com.tediproject.tedi.model.Skills;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ConnectionRepo;
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
    ConnectionRepo connectionRepo;

    @Autowired 
    JobRepo jobRepo;

    @Autowired
    SkillsRepo skillsRepo;

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
                                .sorted(Map.Entry.<Job, Integer>comparingByValue().reversed())
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toList());

        for (Job job : sortedJobs) {
            String author = user.getFirstName() + " " + user.getLastName();
            JobDto j = new JobDto(author, job.getJob_title(), job.getJob_description(), job.getDate_posted(), job.getId());
            jobs.add(j);
        }
        return jobs;
    }

    @Transactional
    public void applyForJob(long jobId, String userToken) {
        // Retrieve the job and user entities
        Job job = jobRepo.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
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

}
