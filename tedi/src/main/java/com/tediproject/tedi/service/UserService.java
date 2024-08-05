package com.tediproject.tedi.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.exceptions.UserAlreadyExists;
import com.tediproject.tedi.exceptions.UserDoesNotExist;
import com.tediproject.tedi.exceptions.WrongPassword;
import com.tediproject.tedi.model.User;
import com.tediproject.tedi.repo.UserRepo;

@Service
public class UserService /*implements UserDetailsService*/{
    @Autowired
    private UserRepo userRepo;


    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     User user = userRepo.findByEmail(email);
    //     if (user == null) {
    //         throw new UsernameNotFoundException("User not found with email: " + email);
    //     }
    //     // Assuming you have a method to convert your User entity to UserDetails
    //     return org.springframework.security.core.userdetails.User
    //             .withUsername(user.getEmail())
    //             .password(user.getPassword())
    //             .authorities(user.getAuthorities()) // Add your logic to convert User to GrantedAuthority list
    //             .build();
    // }

    public User createUser(String firstName, String lastName, String email, String password, Long phoneNumber, MultipartFile pfp, MultipartFile cv ) throws Exception {
        
        if (userRepo.findByEmail(email) != null) {
            throw new UserAlreadyExists("User already exists");
        }

        User user = new User();

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
       
        return userRepo.save(user);

    }

    public Long loginUser(String email, String password){

        User user;

        if ((user = userRepo.findByEmail(email)) == null) {
            throw new UserDoesNotExist("User Not Found");
        }

        String word = user.getPassword();

        if(word.equals(password) == false){
            throw new WrongPassword("Wrong Password");
        }
        
        return user.getID();
    }

    public User getUserById(long id) {
        return userRepo.findById(id);
    }

    public void changeUserEmail(long id, String email){
        try {
            User user = userRepo.findById(id);
            user.setEmail(email);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }
    public void changeUserPassword(long id, String password){
        try {
            User user = userRepo.findById(id);
            user.setPassword(password);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

    public void changeUserPfp(long id, MultipartFile img){
        try {
            User user = userRepo.findById(id);
            user.setProfilePicture(img.getBytes());
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    public void changeUserEdu(long id, String edu){
        try {
            User user = userRepo.findById(id);
            user.setEducation(edu);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    public void changeUserWork(long id, String work){
        try {
            User user = userRepo.findById(id);
            user.setWorkExperience(work);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    public void changeUserSkills(long id, String skills){
        try {
            User user = userRepo.findById(id);
            user.setSkills(skills);
            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("File save failed");
         }
    }

    @PostConstruct
    public void init() {
        if (userRepo.findByEmail("admin@gmail.com") == null) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setPassword("admin");
            user.setAdmin();
            userRepo.save(user);
        }
    }
}

