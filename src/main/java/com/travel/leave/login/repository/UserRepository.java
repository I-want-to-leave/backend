package com.travel.leave.login.repository;

import com.travel.leave.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u.nickname FROM User u WHERE u.code = :userCode")
    Optional<String> findNicknameByUserCode(@Param("userCode") Long userCode);
}