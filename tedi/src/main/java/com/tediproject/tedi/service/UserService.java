package com.tediproject.tedi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.dto.EmailChangeDto;
import com.tediproject.tedi.dto.PasswordChangeDto;
import com.tediproject.tedi.exceptions.UserAlreadyExists;
import com.tediproject.tedi.exceptions.UserDoesNotExist;
import com.tediproject.tedi.exceptions.WrongPassword;
import com.tediproject.tedi.model.Connection;
import com.tediproject.tedi.model.Education;
import com.tediproject.tedi.model.Experience;
import com.tediproject.tedi.model.Role;
import com.tediproject.tedi.model.Skills;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ConnectionRepo;
import com.tediproject.tedi.repo.EducationRepo;
import com.tediproject.tedi.repo.ExperienceRepo;
import com.tediproject.tedi.repo.RoleRepo;
import com.tediproject.tedi.repo.SkillsRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired 
    private ConnectionRepo connectionRepo;

    @Autowired
    private SkillsRepo skillRepo;

    @Autowired
    private EducationRepo eduRepo;

    @Autowired
    private ExperienceRepo expRepo;


    // create a new UserEntity
    public void createUser(String firstName, String lastName, String email, String password, Long phoneNumber, MultipartFile pfp ) throws Exception {
        
        // if the user is found, throw exception user already exists
        if (userRepo.findByEmail(email) != null) {
            throw new UserAlreadyExists("User already exists");
        }

        // create new user instance
        UserEntity user = new UserEntity();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        if(pfp!=null) {
            user.setProfilePicture(pfp.getBytes());
        }
        user.setLastChatUserId(Long.valueOf(0));

        // set new user's role to user
        Role new_role = roleRepo.findByRole("user");
        user.setRoles(new_role);

        // save the new user to the database
        userRepo.save(user);

    }

    // handles login logic
    public void loginUser(String email, String password){

        UserEntity user;
        // find the user using the email
        if ((user = userRepo.findByEmail(email)) == null) {
            throw new UserDoesNotExist("User Not Found");
        }
        
        // check if the given password matches the user's password in the database
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPassword("Wrong Password");
        }
    }

    public UserEntity getUserById(long id) {
        return userRepo.findById(id);
    }

    // check if the user with the token and the user with id are connected
    public Boolean checkIfConnected(long id,String token) {
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        UserEntity user_b = getUserById(id);

        List <Connection> connection = connectionRepo.findByUsers(user_a, user_b);
        return !connection.isEmpty();

    }

    // change the user's email and store the new email to the database
    public void changeUserEmail(EmailChangeDto change){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(change.getToken()));
            user.setEmail(change.getNewEmail());
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

     // change the user's password and store the new password to the database
    public void changeUserPassword(PasswordChangeDto change){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(change.getToken()));
            user.setPassword(passwordEncoder.encode(change.getNewPassword()));
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

     // change the user's profile picture and store the new picture to the database
    public void changeUserPfp(String token, MultipartFile img){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setProfilePicture(img.getBytes());
            userRepo.save(user);
        } catch (IOException e) {
            throw new RuntimeException("File save failed");
         }
    }

    
    @Transactional
    // add an education title to the user's education list
    public void changeUserEdu(String token, String edu){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            
            Education education = eduRepo.findByEducation(edu);

            // if there is no such education in the database, store it
            if(education == null) {
                education = new Education();
                education.setEducation(edu);
            }

            
            // if the user's education doesn't have this education title add it to the education list
            if (!user.getUser_education().contains(education)) {
                user.getUser_education().add(education);
                education.getEducated_users().add(user);
            }
 
            eduRepo.save(education);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    @Transactional
    // add a work title to the user's work experience title
    public void changeUserWork(String token, String experience){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

            Experience exp = expRepo.findByExperience(experience);
            // if the experience title doesn't exist, store it to the database 
            if(exp == null) {
                exp = new Experience();
                exp.setExperience(experience);
               

            }
            
            if (!user.getUser_experience().contains(exp)) {
                user.getUser_experience().add(exp);
                exp.getExperiencedUsers().add(user);
            }

            expRepo.save(exp);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

   
    @Transactional
    // add a skill to the user's skill list
    public void changeUserSkills(String token, String skills){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            
            Skills skill = skillRepo.findBySkill(skills);

            // if the skill doesn't exist, save it to the database
            if(skill == null) {
                skill = new Skills();
                skill.setSkill(skills);
               

            }
            
            if (!user.getUser_skills().contains(skill)) {
                user.getUser_skills().add(skill);
                skill.getSkilled_users().add(user);
            }
 

  
            skillRepo.save(skill);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }
    // change the education section visibility (public or private)
    public void changeEduBool(String token){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setPublicEducation(!user.getPublicEducation());
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }
    // change the work experience section visibility (public or private)
    public void changeWorkBool(String token){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setPublicWork(!user.getPublicWork());
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    // change the skills section visibility (public or private)
    public void changeSkillsBool(String token){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setPublicSkills(!user.getPublicSkills());
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    // change the user's work title and store it to the database
    public void changeWorkTitle(String token, String title){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setWorkTitle(title);
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    // change the user's work place and store it to the database
    public void changeWorkplace(String token, String place){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setWorkplace(place);
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    // find the last user with whom the user has chatted
    public void setTab(String token, long tab){
        System.out.println("EMAIL IS "+jwtUtil.getEmailFromJWT(token));
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        user.setLastChatUserId(tab);
        userRepo.save(user);
    }

     // find education list of user based on skills
    public List <Skills> findSkills(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        return user.getUser_skills();
    }

    // find education list of user based on token
    public List <Education> findEducation(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        return user.getUser_education();
    }

    // find education list of user based on id
    public List<Education> findEducationByID(Long id) {
        UserEntity user = userRepo.findById(id).get();
        return user.getUser_education();
    }

    // remove education from user
    public void deleteEducation(Long edu_id,String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        Education education = eduRepo.findById(edu_id).get();
        user.getUser_education().remove(education);
        education.getEducated_users().remove(user);

        userRepo.save(user);
        eduRepo.save(education);
    }


    // remove skill from user's skill list
    public void deleteSkill(Long skill_id,String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        Skills skill = skillRepo.findById(skill_id).get();
        user.getUser_skills().remove(skill);

        skill.getSkilled_users().remove(user);
        skillRepo.save(skill);
        userRepo.save(user);

    }

     // find skills list based on user's id
    public List<Skills> findSkillsByID(Long id) {
        UserEntity user = userRepo.findById(id).get();
        return user.getUser_skills();
    }

     // find experience list based on user's token
    public List<Experience> findExperience(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        return user.getUser_experience();
    }

    // find experience list based on user's id
    public List<Experience> findExperienceByID(Long id) {
        UserEntity user = userRepo.findById(id).get();
        return user.getUser_experience();
    }

    // remove experience from user's experience list
    public void deleteExperience(Long experience_id, String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        Experience exp = expRepo.findById(experience_id).get();
        user.getUser_experience().remove(exp);

        exp.getExperiencedUsers().remove(user);
        expRepo.save(exp);
        userRepo.save(user);

    }




    @PostConstruct
    public void init() {
       
        // encode all passwords at installation
        UserEntity admin = userRepo.findByEmail("admin@gmail.com");
  
        if(admin.getPassword().equals("admin")) {
            List <UserEntity> users = userRepo.findAll();
            
        
            for(int i=0; i<users.size(); i++) {
                UserEntity user = users.get(i);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(user);
            }
        }
       
    }

   



    
}

