package com.project.exhibitions.dao;

import com.project.exhibitions.entity.User;
import com.project.exhibitions.repository.UserRepository;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(userRepository.findByUsername(username));
    }

}
