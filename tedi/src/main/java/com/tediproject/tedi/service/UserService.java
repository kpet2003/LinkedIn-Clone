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
import com.tediproject.tedi.model.Role;
import com.tediproject.tedi.model.Skills;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ConnectionRepo;
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



    public void createUser(String firstName, String lastName, String email, String password, Long phoneNumber, MultipartFile pfp, MultipartFile cv ) throws Exception {
        
        if (userRepo.findByEmail(email) != null) {
            throw new UserAlreadyExists("User already exists");
        }

        UserEntity user = new UserEntity();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        System.out.println("HERE");
        user.setPassword(passwordEncoder.encode(password));
        System.out.println("HERE");
        user.setPhoneNumber(phoneNumber);
        if(pfp!=null) {
            user.setProfilePicture(pfp.getBytes());
        }
        if(cv!=null) {
            user.setResume(cv.getBytes());
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

        List <Connection> connection = connectionRepo.findByUsers(user_a.getID(), user_b.getID());
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

    public void changeUserEdu(String token, String edu){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setEducation(edu);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    public void changeUserWork(String token, String work){
        try {
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            user.setWorkExperience(work);
            userRepo.save(user);
        } catch (Exception e) {
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
            e.printStackTrace();  // Log the exception for debugging purposes
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

