package com.tediproject.tedi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.model.User;
import com.tediproject.tedi.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User createUser(String firstName, String lastName, String email, String password, Long phoneNumber, MultipartFile pfp, MultipartFile cv ) throws Exception {
        
        if (userRepo.findByEmail(email) != null) {
            throw new Exception("User already exists");
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
        if(pfp!=null) {
            user.setResume(cv.getBytes());
        }

        
       
        return userRepo.save(user);

    }
}

