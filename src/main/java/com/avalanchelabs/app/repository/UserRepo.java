package com.avalanchelabs.app.repository;

import com.avalanchelabs.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
