package com.project.exhibitions.services;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.entity.Role;
import com.project.exhibitions.entity.User;
import com.project.exhibitions.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * This method loads User by username
     * @param username String of a User's username
     * @return UserDetails object with User
     * @throws UsernameNotFoundException in case if this user was not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username: '" + username + "' was not found!"));

    }

    /**
     * This method creates new record of a new User in database
     * @param user UserDTO object
     */
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

    /**
     * This method finds User by id
     * @param userId Id of a User
     * @return Optional of a User
     */
    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }
}
