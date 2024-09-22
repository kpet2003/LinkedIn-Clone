package com.tediproject.tedi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.dto.ApplicantDto;
import com.tediproject.tedi.dto.JobDto;
import com.tediproject.tedi.dto.NewJobDto;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.service.JobService;

@RestController
public class JobController {

    //required components
    
    @Autowired
    private JobService jobService;
    @Autowired
    private JwtUtil jwtUtil;

    //create new job
    @PostMapping(value="/newJob")
    public ResponseEntity<?> createJob(@RequestBody NewJobDto job){
        try {
            jwtUtil.validateToken(job.getAuthorToken()); //make sure token is valid
            jobService.createJob(job); //create job in database
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) { //if error return error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //get jobs of user's connections
    @GetMapping(value = "/connectionjobs/{user}")
    public ResponseEntity<?> getConnectionJobs(@PathVariable String user){
        try {
            jwtUtil.validateToken(user); //make sure user is valid
            List<JobDto> allJobs = jobService.getJobs(user); //get all jobs
            List<JobDto> jobs = jobService.getConnectionsJobs(allJobs,user); //keep jobs that connections created
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //get jobs of users not in the user's connections
    @GetMapping(value = "/otherjobs/{user}")
    public ResponseEntity<?> getOtherJobs(@PathVariable String user){
        try {
            jwtUtil.validateToken(user); //make sure user is valid
            List<JobDto> allJobs = jobService.getJobs(user); //get all jobs
            List<JobDto> jobs = jobService.getOtherJobs(allJobs,user); //keep jobs that users not in connections created
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //get jobs user created
    @GetMapping(value = "/myjobs/{user}")
    public ResponseEntity<?> getMyJobs(@PathVariable String user){
        try {
            jwtUtil.validateToken(user); //make sure user is valid
            List<JobDto> allJobs = jobService.getJobs(user); //get all jobs
            List<JobDto> jobs = jobService.getMyJobs(allJobs,user); //keep jobs user created
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    //apply for a job
    @PostMapping(value = "/jobs/{jobId}/{token}")
    public ResponseEntity<?> apply(@PathVariable long jobId, @PathVariable String token){
        try {
            jwtUtil.validateToken(token); //make sure user is valid
            jobService.applyForJob(jobId, token); //save application
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //get jobs user has applied for
    @GetMapping(value="/applied/{token}")
    public ResponseEntity<?> getAppliedJobs(@PathVariable String token){
        try {
            jwtUtil.validateToken(token); //make sure user is valid
            List<JobDto> allJobs = jobService.getJobs(token); //get all jobs
            List<JobDto> jobs = jobService.getAppliedJobs(allJobs,token); //keep jobs that user has applied for
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //get users that have applied for a job the user posted
    @GetMapping(value="/applicants/{jobId}/{token}")
    public ResponseEntity<?> getApplicants(@PathVariable long jobId, @PathVariable String token){
        try {
            jwtUtil.validateToken(token); //make sure user is valid
            List<ApplicantDto> applicants = jobService.getApplicants(jobId); //get applicants of job
            return ResponseEntity.ok(applicants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //add a view to a job
    @PutMapping(value="/addView/{jobId}/{token}")
    public ResponseEntity<?> addView(@PathVariable long jobId, @PathVariable String token) {
        try {

            jwtUtil.validateToken(token);//make sure user is valid
            jobService.addView(jobId, token); //add a view to the job

            return ResponseEntity.ok(HttpStatus.OK);
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
