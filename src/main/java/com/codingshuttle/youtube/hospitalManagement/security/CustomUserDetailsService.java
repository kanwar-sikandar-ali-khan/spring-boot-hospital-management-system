package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

// this  CustomUserDetailsService is only for hardcoded, in-memory login.,Allow login without a database,Useful for testing
// he use thhis for teaching purpose
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }
}
