package com.tediproject.tedi.controllers;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.dto.BoolChangeDto;
import com.tediproject.tedi.dto.EducationDto;
import com.tediproject.tedi.dto.EmailChangeDto;
import com.tediproject.tedi.dto.ExperienceDto;
import com.tediproject.tedi.dto.InfoChangeDto;
import com.tediproject.tedi.dto.LoginDto;
import com.tediproject.tedi.dto.PasswordChangeDto;
import com.tediproject.tedi.dto.SkillsDto;
import com.tediproject.tedi.dto.UserDto;
import com.tediproject.tedi.model.Education;
import com.tediproject.tedi.model.Experience;
import com.tediproject.tedi.model.Skills;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.service.UserService;


@RestController
public class UserControllers {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserRepo userRepo;
    


    @Autowired
    private JwtUtil jwtUtil;

    // authenticate the user
    @GetMapping(value="/auth/")
    public ResponseEntity<?> authenticate(@RequestParam(value="token", required = false)String token) {
        try {
            jwtUtil.validateToken(token);
            return ResponseEntity.ok().body("Token is valid");
        }
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // create a new UserEntity
    @PostMapping(value = "/SignUp/signup")
    public ResponseEntity<?> createUser(
        @RequestParam(value="email", required = false) String email,
        @RequestParam(value="firstName", required = false) String firstName,
        @RequestParam(value="lastName", required = false) String lastName,
        @RequestParam(value="password", required = false) String password,
        @RequestParam(value="phoneNumber", required = false) Long phoneNumber,
        @RequestPart(value = "profilePicture", required = false) MultipartFile pfp)
        {
        try {
            userService.createUser(firstName, lastName, email, password, phoneNumber, pfp );
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // login the user
    @PostMapping(value= "/Login")
    public ResponseEntity<?> login( @RequestBody LoginDto user) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        } 
        catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        
        try {
            userService.loginUser(user.getEmail(), user.getPassword());
            String token = jwtUtil.generateToken(auth);
            return ResponseEntity.ok(token);
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        
        
    }
  
    // change the user's email
    @PutMapping(value= "/NewEmail")
    public ResponseEntity<?> changeEmail(@RequestBody EmailChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            Authentication auth;
            try {

                userService.changeUserEmail(change);

                auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(change.getNewEmail(), change.getPassword())
                );
                
                String token = jwtUtil.generateToken(auth);
                
                return ResponseEntity.ok(token);
            }
            catch (AuthenticationCredentialsNotFoundException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            }
            catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
            
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // change the user's password
    @PutMapping(value= "/NewPassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeUserPassword(change);
            return ResponseEntity.ok(HttpStatus.OK);
        } 
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // change the user's profile picture
    @PutMapping(value= "/Profile/pfpchange")
    public ResponseEntity<?> changePfp(
        @RequestParam(value="profilePicture", required = false) MultipartFile image,
        @RequestParam(value ="token", required= false) String token){
        try {
            jwtUtil.validateToken(token);
            userService.changeUserPfp(token, image);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // change the user's education
    @PutMapping(value= "/Profile/educhange")
    public void changeEdu(@RequestBody InfoChangeDto change) {
        jwtUtil.validateToken(change.getToken());
        userService.changeUserEdu(change.getToken(), change.getInfo());
    }   

     // change the user's work experience
    @PutMapping(value= "/Profile/workchange")
    public ResponseEntity<?> changeWork(@RequestBody InfoChangeDto change){


        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeUserWork(change.getToken(), change.getInfo());
            return ResponseEntity.ok(HttpStatus.OK);
        } 
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

     // change the user's skills
    @PutMapping(value= "/Profile/skillchange")
    public ResponseEntity<?> changeSkill(@RequestBody InfoChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            
            userService.changeUserSkills(change.getToken(), change.getInfo());
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // change the user's education status (public or private)
    @PutMapping(value= "/Profile/edubool")
    public ResponseEntity<?> changeEduBool(@RequestBody BoolChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeEduBool(change.getToken());
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // change the user's work experience status (public or private)
    @PutMapping(value = "/Profile/workbool")
    public ResponseEntity<?> changeWorkBool(@RequestBody BoolChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeWorkBool(change.getToken());
            return ResponseEntity.ok(HttpStatus.OK);
        } 
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // change the user's skills status (public or private)
    @PutMapping(value= "/Profile/skillsbool")
    public ResponseEntity<?> changeSkillsBool(@RequestBody BoolChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeSkillsBool(change.getToken());
            return ResponseEntity.ok(HttpStatus.OK);
        } 
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // change the user's work title
    @PutMapping(value= "/Profile/worktitle")
    public ResponseEntity<?> changeWorkTitle(@RequestBody InfoChangeDto change){
        try {
            if(jwtUtil.validateToken(change.getToken())){
            userService.changeWorkTitle(change.getToken(), change.getInfo());
            return ResponseEntity.ok(HttpStatus.OK);
            }
            else{
                return ResponseEntity.badRequest().body("Invalid Token");
            }
        }
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    // change the user's work place
    @PutMapping(value= "/Profile/workplace")
    public ResponseEntity<?> changeWorkplace(@RequestBody InfoChangeDto change){
        try {
            if(jwtUtil.validateToken(change.getToken())){
            userService.changeWorkplace(change.getToken(), change.getInfo());
            return ResponseEntity.ok(HttpStatus.OK);
            }
            else{
                return ResponseEntity.badRequest().body("Invalid Token");
            }
        } 
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // find the last person with whom the user has chatted
    @PutMapping(value = "/tab/{user}/{tab}")
    public ResponseEntity<?> setLastChat(@PathVariable String user, @PathVariable long tab){
        try {
            jwtUtil.validateToken(user);
            System.out.println("TAB IS "+tab);
            userService.setTab(user,tab);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    // returns user info to the frontend
    @GetMapping(value = "/NewEmail", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@RequestParam(value="id", required = false) Long id) {
        try{
            
            UserEntity user = userService.getUserById(id);
        
        if(user != null){
            return ResponseEntity.ok(user);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("ID is required");
        }
    }

    // Return user's profile information to the frontend
    @GetMapping(value = "/Profile", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserProfile(@RequestParam(value="token", required = false) String token) {
        try{
            jwtUtil.validateToken(token);
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
 
            if(user != null){
                UserDto userDto = new UserDto();
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setEmail(user.getEmail());
                userDto.setPhoneNumber(user.getPhoneNumber());
                userDto.setPublicWork(user.getPublicWork());
                userDto.setPublicEducation(user.getPublicEducation());
                userDto.setPublicSkills(user.getPublicSkills());
                userDto.setWorkTitle(user.getWorkTitle());
                userDto.setWorkplace(user.getWorkplace());
        
                userDto.setId(user.getID());
                
                if (user.getProfilePicture() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
                    userDto.setProfilePicture(base64Image);
                }
                return ResponseEntity.ok(userDto);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch (AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("token is required");
        }
    }


    // check if the user viewing the profile is connected to the profile's owner
    @GetMapping(value = "/ViewProfile")
    public Boolean checkConnection(@RequestParam(value="user_id", required = false)Long id,@RequestParam(value="token", required = false)String token) {
        return userService.checkIfConnected(id,token);
    }

    // Return user's skills to the frontend
    @GetMapping(value = "/Profile/GetSkills")
    public List<SkillsDto> getUserSkills(String token) {
        List<Skills> skills =  userService.findSkills(token);

        List <SkillsDto> user_skills = new ArrayList<>();

        for(int i=0; i<skills.size(); i++) {
            SkillsDto skill = new SkillsDto();
            skill.setId(skills.get(i).getId());
            skill.setSkill(skills.get(i).getSkill());
            user_skills.add(skill);
        }

        return user_skills;
    }

    // Return user's education to the frontend
    @GetMapping(value="/Profile/GetEducation")
    public List<EducationDto> getUserEducation(String token) {
        List<Education> edu =  userService.findEducation(token);

        List<EducationDto> education = new ArrayList<>();

        for(int i=0; i<edu.size(); i++) {
            EducationDto user_edu = new EducationDto();
            user_edu.setId(edu.get(i).getId());
            user_edu.setEducation(edu.get(i).getEducation());

            education.add(user_edu);
        }
        return education;
    }

    // Return user's experience to the frontend
    @GetMapping(value="/Profile/GetExperience")
    public List<ExperienceDto> getUserExperience(String token) {
        List<Experience> exp = userService.findExperience(token);

        List <ExperienceDto> user_exp = new ArrayList<>();
        for(int i=0; i<exp.size(); i++) {
            ExperienceDto experience = new ExperienceDto();
            experience.setId(exp.get(i).getId());
            experience.setExperience(exp.get(i).getExperience());
            user_exp.add(experience);
        }


        return user_exp;
    }

    // Return user with id=id profile information to the frontend
    @GetMapping(value = "/VisitProfile/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile(@PathVariable Long id) {

        try{
            if(id == null){
                return ResponseEntity.badRequest().body("id is required");
            }
            UserEntity user = userService.getUserById(id);

            if(user != null){
                UserDto userDto = new UserDto();
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setEmail(user.getEmail());
                userDto.setPhoneNumber(user.getPhoneNumber());
                userDto.setPublicWork(user.getPublicWork());
                userDto.setPublicEducation(user.getPublicEducation());
                userDto.setPublicSkills(user.getPublicSkills());
                userDto.setWorkTitle(user.getWorkTitle());
                userDto.setWorkplace(user.getWorkplace());

                
                if (user.getProfilePicture() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
                    userDto.setProfilePicture(base64Image);
                }
                return ResponseEntity.ok(userDto);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        
       
        catch(Exception e){
            return ResponseEntity.badRequest().body("token is required");
        }
    }

    // Return user's skills to the frontend
    @GetMapping(value = "/VisitProfile/getSkills/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<SkillsDto> getSkillsByID(@PathVariable Long id) {
        List <Skills> skills =  userService.findSkillsByID(id);

        List <SkillsDto> user_skills = new ArrayList<>();

        for(int i=0; i<skills.size(); i++) {
            SkillsDto skill = new SkillsDto();
            skill.setId(skills.get(i).getId());
            skill.setSkill(skills.get(i).getSkill());
            user_skills.add(skill);
        }

        return user_skills;
    }

    // Return user's education to the frontend
    @GetMapping(value = "/VisitProfile/getEducation/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<EducationDto> getEducationByID(@PathVariable Long id) {
   
        List<Education> edu =  userService.findEducationByID(id);

        List<EducationDto> education = new ArrayList<>();

        for(int i=0; i<edu.size(); i++) {
            EducationDto user_edu = new EducationDto();
            user_edu.setId(edu.get(i).getId());
            user_edu.setEducation(edu.get(i).getEducation());

            education.add(user_edu);
        }
        return education;
    }

    // Return user's experience to the frontend
    @GetMapping(value = "/VisitProfile/getExperience/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<ExperienceDto> getExperienceByID(@PathVariable Long id) {
        List<Experience> exp =  userService.findExperienceByID(id);
        List <ExperienceDto> user_exp = new ArrayList<>();
        for(int i=0; i<exp.size(); i++) {
            ExperienceDto experience = new ExperienceDto();
            experience.setId(exp.get(i).getId());
            experience.setExperience(exp.get(i).getExperience());
            user_exp.add(experience);
        }


        return user_exp;
    }
    // remove a skill from the user's skill list 
    @DeleteMapping(value = "/Profile/DeleteSkill")
    public ResponseEntity<?> removeSkill(@RequestParam(value="skill_id") Long skill_id,@RequestParam(value="token") String token) {
        try {
            userService.deleteSkill(skill_id,token);
            return ResponseEntity.ok("OK");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("ID is required");
        }
    }

    // remove education from user's education list
    @DeleteMapping(value = "/Profile/DeleteEdu")
    public ResponseEntity<?> removeEducation(@RequestParam(value="education_id") Long education_id,@RequestParam(value="token") String token) {
        try {
            userService.deleteEducation(education_id, token);
            return ResponseEntity.ok("OK");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("ID is required");
        }
    }
    // remove experience from user's experience list
    @DeleteMapping(value = "/Profile/DeleteExp")
    public ResponseEntity<?> removeExperience(@RequestParam(value="experience_id") Long experience_id,@RequestParam(value="token") String token) {
        try {
            userService.deleteExperience(experience_id, token);
            return ResponseEntity.ok("OK");
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().body("ID is required");
        }
    }

}


