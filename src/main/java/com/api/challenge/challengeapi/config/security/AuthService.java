package com.api.challenge.challengeapi.config.security;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.api.challenge.challengeapi.model.Responsible;
import com.api.challenge.challengeapi.repository.ResponsibleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class AuthService implements UserDetailsService{

    @Autowired
    private ResponsibleRepository responsibleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Responsible> responsible = responsibleRepository.findByUsrName(username);
        if(responsible.isPresent()) {
            return responsible.get();
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }
    
}
