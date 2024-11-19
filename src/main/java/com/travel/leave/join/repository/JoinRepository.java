package com.travel.leave.join.repository;

import com.travel.leave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
