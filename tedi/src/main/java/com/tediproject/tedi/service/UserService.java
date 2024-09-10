package com.tediproject.tedi.service;

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



    public void createUser(String firstName, String lastName, String email, String password, Long phoneNumber, MultipartFile pfp ) throws Exception {
        
        if (userRepo.findByEmail(email) != null) {
            throw new UserAlreadyExists("User already exists");
        }

        UserEntity user = new UserEntity();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        if(pfp!=null) {
            user.setProfilePicture(pfp.getBytes());
        }


        Role new_role = roleRepo.findByRole("user");
        user.setRoles(new_role);

        userRepo.save(user);

    }

    public Long loginUser(String email, String password){

        UserEntity user;

        if ((user = userRepo.findByEmail(email)) == null) {
            throw new UserDoesNotExist("User Not Found");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPassword("Wrong Password");
        }
        
        return user.getID();
    }

    public UserEntity getUserById(long id) {
        return userRepo.findById(id);
    }


    public Boolean checkIfConnected(long id,String token) {
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        UserEntity user_b = getUserById(id);

        List <Connection> connection = connectionRepo.findByUsers(user_a, user_b);
        return !connection.isEmpty();

    }

    public void changeUserEmail(EmailChangeDto change){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(change.getToken()));
            user.setEmail(change.getNewEmail());
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

    public void changeUserPassword(PasswordChangeDto change){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(change.getToken()));
            user.setPassword(passwordEncoder.encode(change.getNewPassword()));
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

    public void changeUserPfp(String token, MultipartFile img){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setProfilePicture(img.getBytes());
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    @Transactional
    public void changeUserEdu(String token, String edu){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            
            Education education = eduRepo.findByEducation(edu);

            if(education == null) {
                education = new Education();
                education.setEducation(edu);
            }

            

            if (!user.getUser_education().contains(education)) {
                user.getUser_education().add(education);
                education.getEducated_users().add(user);
            }
 
            eduRepo.save(education);
            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new RuntimeException("File save failed");
         }
    }

    @Transactional
    public void changeUserWork(String token, String experience){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

            Experience exp = expRepo.findByExperience(experience);

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
            e.printStackTrace(); 
            throw new RuntimeException("File save failed");
         }
    }

   
    @Transactional
    public void changeUserSkills(String token, String skills){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            
            Skills skill = skillRepo.findBySkill(skills);

            
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
            e.printStackTrace(); 
            throw new RuntimeException("File save failed");
         }
    }

    public void changeEduBool(String token){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setPublicEducation(!user.getPublicEducation());
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    public void changeWorkBool(String token){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setPublicWork(!user.getPublicWork());
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    public void changeSkillsBool(String token){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setPublicSkills(!user.getPublicSkills());
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    public void changeWorkTitle(String token, String title){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setWorkTitle(title);
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    public void changeWorkplace(String token, String place){
        try{
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setWorkplace(place);
            userRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Error changing bool");
        }
    }

    public List <Skills> findSkills(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        return user.getUser_skills();
    }

    public List <Education> findEducation(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        return user.getUser_education();
    }

    public List<Education> findEducationByID(Long id) {
        UserEntity user = userRepo.findById(id).get();
        return user.getUser_education();
    }

    public void deleteEducation(Long edu_id,String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        Education education = eduRepo.findById(edu_id).get();
        user.getUser_education().remove(education);
        education.getEducated_users().remove(user);

        userRepo.save(user);
        eduRepo.save(education);
    }



    public void deleteSkill(Long skill_id,String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        Skills skill = skillRepo.findById(skill_id).get();
        user.getUser_skills().remove(skill);

        skill.getSkilled_users().remove(user);
        skillRepo.save(skill);
        userRepo.save(user);

    }

    public List<Skills> findSkillsByID(Long id) {
        UserEntity user = userRepo.findById(id).get();
        return user.getUser_skills();
    }

    public List<Experience> findExperience(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        return user.getUser_experience();
    }

    public List<Experience> findExperienceByID(Long id) {
        UserEntity user = userRepo.findById(id).get();
        return user.getUser_experience();
    }

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
       
        // set admin role
        if (roleRepo.findById(1) == null) {
            Role role = new Role();
            System.out.println("add admin role");
            role.setRole("admin");
            roleRepo.save(role);
        }
        
            // set user role
        if (roleRepo.findById(2) == null) {
            Role role = new Role();
            role.setRole("user");
            roleRepo.save(role);
        }

        if (userRepo.findByEmail("admin@gmail.com") == null) {

            
            List <UserEntity> users = userRepo.findAll();
            System.out.println("\n\n in here \n\n");
            for(int i=0; i<users.size(); i++) {
                UserEntity user = users.get(i);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(user);
            }


            UserEntity user = new UserEntity();
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setAdmin();

            Role role = roleRepo.findByRole("admin");
            user.setRoles(role);
            role.addUser(user);

            System.out.println("add admin in user table");
            
            userRepo.save(user);


            


        }
        
    }

   



    
}

