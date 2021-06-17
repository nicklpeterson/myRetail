package com.myRetail.service;

import com.myRetail.exceptions.UserNotFoundException;
import com.myRetail.models.User;
import com.myRetail.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public User getUserByUsername(String username) throws UserNotFoundException {
        final Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        return userOptional.get();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final User user = getUserByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(username);
        }
    }

}
