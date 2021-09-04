package com.project.exhibitions.repository;

import com.project.exhibitions.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Illia Koshkin
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * This method finds by user by username
     * @param username String username
     * @return Optional of User
     */
    Optional<User> findByUsername(String username);

}
