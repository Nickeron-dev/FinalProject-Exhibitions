package com.project.exhibitions.repository;

import com.project.exhibitions.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
