package com.tediproject.tedi.security;



import java.util.Collection;
import java.util.Collections;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Role;
import com.tediproject.tedi.model.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.tediproject.tedi.repo.UserRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity myUser  =  userRepo.findByEmail(email);
        return new User(myUser.getEmail(), myUser.getPassword(),  mapRolesToAuthorities(myUser.getRole()));
    }


    // convert roles to authority
    private Collection<GrantedAuthority> mapRolesToAuthorities(Role role) {
        
        if (role == null) {
            return Collections.emptyList();
        }

        return Collections.singletonList(new SimpleGrantedAuthority(role.getRole()));
    }
}
