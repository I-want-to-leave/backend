package com.travel.leave.mypage.repository;

import com.travel.leave.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<User, Long>, MyPageReadQuery {
}
