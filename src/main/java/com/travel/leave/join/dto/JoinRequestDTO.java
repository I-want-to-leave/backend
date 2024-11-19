package com.travel.leave.join.dto;

import com.travel.leave.join.model.validator.email.EmailFormat;
import com.travel.leave.join.model.validator.email.EmailLength;
import com.travel.leave.join.model.validator.nickname.NicknameFormat;
import com.travel.leave.join.model.validator.nickname.NicknameLength;
import com.travel.leave.join.model.validator.password.PasswordFormat;
import com.travel.leave.join.model.validator.password.PasswordLength;
import com.travel.leave.join.model.validator.username.UsernameFormat;
import com.travel.leave.join.model.validator.username.UsernameLength;

public record JoinRequestDTO(
        @UsernameLength
        @UsernameFormat
        String username,

        @PasswordLength
        @PasswordFormat
        String password,

        @NicknameLength
        @NicknameFormat
        String nickname,

        @EmailLength
        @EmailFormat
        String email
) {
}
