package com.avalanchelabs.app.repository;

import com.avalanchelabs.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
