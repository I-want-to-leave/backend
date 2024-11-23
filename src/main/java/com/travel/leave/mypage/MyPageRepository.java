package com.travel.leave.mypage;

import com.travel.leave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<User, Long>, MyPageReadRepository {
}
