package com.tediproject.tedi.controllers;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.dto.BoolChangeDto;
import com.tediproject.tedi.dto.EmailChangeDto;
import com.tediproject.tedi.dto.LoginDto;
import com.tediproject.tedi.dto.PasswordChangeDto;
import com.tediproject.tedi.dto.UserDto;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.RoleRepo;
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
    private RoleRepo roleRepo;


    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/SignUp/signup")
    public ResponseEntity<?> createUser(
        @RequestParam(value="email", required = false) String email,
        @RequestParam(value="firstName", required = false) String firstName,
        @RequestParam(value="lastName", required = false) String lastName,
        @RequestParam(value="password", required = false) String password,
        @RequestParam(value="phoneNumber", required = false) Long phoneNumber,
        @RequestPart(value = "profilePicture", required = false) MultipartFile pfp,
        @RequestPart(value = "resume", required = false) MultipartFile cv) {
        try {
            userService.createUser(firstName, lastName, email, password, phoneNumber, pfp, cv);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value= "/Login")
    public ResponseEntity<?> login( @RequestBody LoginDto user) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        } 
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        
        try {
            Long id = userService.loginUser(user.getEmail(), user.getPassword());
            String token = jwtUtil.generateToken(auth);
            return ResponseEntity.ok(token);
        } 
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        
        
    }
  

    @PutMapping(value= "/NewEmail")
    public ResponseEntity<?> changeEmail(@RequestBody EmailChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            Authentication auth;
            try {
                auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtUtil.getEmailFromJWT(change.getToken()), change.getPassword())
                );
                userService.changeUserEmail(change);
            } 
            catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
            String token = jwtUtil.generateToken(auth);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping(value= "/NewPassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeUserPassword(change);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value= "/Profile/pfpchange")
    public ResponseEntity<?> changePfp(
        @RequestParam(value="profilePicture", required = false) MultipartFile image,
        @RequestParam(value ="id", required= false) Long id){
        try {
            userService.changeUserPfp(id, image);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value= "/Profile/educhange")
    public ResponseEntity<?> changeEdu(
        @RequestParam(value="education", required = false) String edu,
        @RequestParam(value ="id", required= false) Long id){
        try {
            userService.changeUserEdu(id, edu);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value= "/Profile/workchange")
    public ResponseEntity<?> changeWork(
        @RequestParam(value="workExperience", required = false) String work,
        @RequestParam(value ="id", required= false) Long id){
        try {
            userService.changeUserWork(id, work);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value= "/Profile/skillchange")
    public ResponseEntity<?> changeSkill(
        @RequestParam(value="skills", required = false) String skills,
        @RequestParam(value ="id", required= false) Long id){
        try {
            userService.changeUserSkills(id, skills);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value= "/Profile/edubool")
    public ResponseEntity<?> changeEduBool(@RequestBody BoolChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeEduBool(change.getToken());
            System.out.println("DID IT");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/Profile/workbool")
    public ResponseEntity<?> changeWorkBool(@RequestBody BoolChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeWorkBool(change.getToken());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value= "/Profile/skillsbool")
    public ResponseEntity<?> changeSkillsBool(@RequestBody BoolChangeDto change){
        try {
            jwtUtil.validateToken(change.getToken());
            userService.changeSkillsBool(change.getToken());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
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

    @GetMapping(value = "/Profile", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserProfile(@RequestParam(value="id", required = false) Long id) {
        try{
            
            UserEntity user = userService.getUserById(id);
        
        if(user != null){
            UserDto userDto = new UserDto();
            userDto.setId(user.getID());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setResume(user.getResume());
            userDto.setWorkExperience(user.getWorkExperience());
            userDto.setEducation(user.getEducation());
            userDto.setSkills(user.getSkills());
            userDto.setPublicWork(user.getPublicWork());
            userDto.setPublicEducation(user.getPublicEducation());
            userDto.setPublicSkills(user.getPublicSkills());
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
            return ResponseEntity.badRequest().body("ID is required");
        }
    }
}