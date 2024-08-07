package com.tediproject.tedi.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.exceptions.UserAlreadyExists;
import com.tediproject.tedi.exceptions.UserDoesNotExist;
import com.tediproject.tedi.exceptions.WrongPassword;
import com.tediproject.tedi.model.Role;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.repo.RoleRepo;

@Service
public class UserService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;




    public UserEntity createUser(String firstName, String lastName, String email, String password, Long phoneNumber, MultipartFile pfp, MultipartFile cv ) throws Exception {
        
        if (userRepo.findByEmail(email) != null) {
            throw new UserAlreadyExists("User already exists");
        }

        UserEntity user = new UserEntity();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        if(pfp!=null) {
            user.setProfilePicture(pfp.getBytes());
        }
        if(cv!=null) {
            user.setResume(cv.getBytes());
        }

        Role new_role = roleRepo.findByRole("user");
        user.setRoles(new_role);

       
        
        return userRepo.save(user);

    }

    public Long loginUser(String email, String password){

        UserEntity user;

        if ((user = userRepo.findByEmail(email)) == null) {
            throw new UserDoesNotExist("User Not Found");
        }

        String word = user.getPassword();

        if(word.equals(password) == false){
            throw new WrongPassword("Wrong Password");
        }
        
        return user.getID();
    }

    public UserEntity getUserById(long id) {
        return userRepo.findById(id);
    }

    public void changeUserEmail(long id, String email){
        try {
            UserEntity user = userRepo.findById(id);
            user.setEmail(email);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }
    public void changeUserPassword(long id, String password){
        try {
            UserEntity user = userRepo.findById(id);
            user.setPassword(password);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

    public void changeUserPfp(long id, MultipartFile img){
        try {
            UserEntity user = userRepo.findById(id);
            user.setProfilePicture(img.getBytes());
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    public void changeUserEdu(long id, String edu){
        try {
            UserEntity user = userRepo.findById(id);
            user.setEducation(edu);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    public void changeUserWork(long id, String work){
        try {
            UserEntity user = userRepo.findById(id);
            user.setWorkExperience(work);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    public void changeUserSkills(long id, String skills){
        try {
            UserEntity user = userRepo.findById(id);
            user.setSkills(skills);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    @PostConstruct
    public void init() {

        // set admin role
        if (roleRepo.findById(1) == null) {
            Role role = new Role();
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
            user.setPassword("admin");
            user.setAdmin();

            Role role = roleRepo.findByRole("admin");
            user.setRoles(role);
            role.addUser(user);
            
            userRepo.save(user);
            
          



        }
        


    }
}

