package com.example.demo.service;

import com.example.demo.controller.dto.UserRequestDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(@Lazy BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            return null;
        }
    }

    @Bean
    public static String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().toString();
        } else {
            return "null";
        }
    }

    public void saveUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO.getUsername(), passwordEncoder.encode(userRequestDTO.getPassword()), "USER");
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return org.springframework.security.core.userdetails.User.withUsername(user.get().getUsername()).password(user.get().getPassword()).roles(user.get().getRole()).build();
    }
}
