package com.example.demo.service;

import com.example.demo.controller.dto.UserRequestDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.NullServiceException;
import org.hibernate.validator.internal.constraintvalidators.bv.NullValidator;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InvalidObjectException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new UsernameNotFoundException("User not found");
        }

        return userDetails.getUsername();
    }

    @Bean
    public static String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().toString();
        } else {
            throw new NullPointerException("User Role not found");
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

        return org.springframework.security.core.userdetails.User
                .withUsername(user.get().getUsername())
                .password(user.get().getPassword())
                .roles(user.get().getRole())
                .build();
    }
}
