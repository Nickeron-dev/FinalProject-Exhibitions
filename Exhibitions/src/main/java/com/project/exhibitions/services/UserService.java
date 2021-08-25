package com.project.exhibitions.services;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.entity.Role;
import com.project.exhibitions.entity.User;
import com.project.exhibitions.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: '" + username + "' was not found!"));

    }

    public void saveNewUser(UserDTO user) throws DataIntegrityViolationException {
        userRepository.save(User.builder()
                .password(user.getPassword())
                .role(Role.USER)
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .build());
    }

    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }
}
