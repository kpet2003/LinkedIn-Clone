package com.tediproject.tedi.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.exceptions.UserAlreadyExists;
import com.tediproject.tedi.exceptions.UserDoesNotExist;
import com.tediproject.tedi.exceptions.WrongPassword;
import com.tediproject.tedi.model.User;
import com.tediproject.tedi.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

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
        return userRepo.findById(id).orElse(null);
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

