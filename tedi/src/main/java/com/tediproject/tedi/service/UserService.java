package com.tediproject.tedi.service;
import java.io.InputStream;
import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;

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
        
        if (userRepo.findUser(email) != null) {
            throw new Exception("User already exists");
        }

        InputStream fileInputStream = pfp.getInputStream();
        byte[] fileBytes = fileInputStream.readAllBytes();
        Blob pfpBlob = new SerialBlob(fileBytes);

        fileInputStream = cv.getInputStream();
        fileBytes = fileInputStream.readAllBytes();
        Blob cvBlob = new SerialBlob(fileBytes);

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setProfilePicture(pfpBlob.getBytes(1, (int) pfpBlob.length()));
        user.setResume(cvBlob.getBytes(1, (int) cvBlob.length()));

        return userRepo.save(user);
    }
}

