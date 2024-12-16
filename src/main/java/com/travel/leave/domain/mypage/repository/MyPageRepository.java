package com.travel.leave.domain.mypage.repository;

import com.travel.leave.subdomain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<User, Long>, MyPageReadQuery {
}
