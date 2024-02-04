package com.amadeus.flightapi.security;


import com.amadeus.flightapi.model.User;
import com.amadeus.flightapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by:"+userId));

        return new org.springframework.security.core.userdetails.User(
                userId, user.getPassword(), List.of(user.getRole()));
    }


}
