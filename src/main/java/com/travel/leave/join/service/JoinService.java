package com.travel.leave.join.service;

import com.travel.leave.entity.User;
import com.travel.leave.exception.UsernameDuplicationException;
import com.travel.leave.exception.message.ExceptionMessage;
import com.travel.leave.join.dto.JoinRequestDTO;
import com.travel.leave.join.repository.JoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

@Slf4j
@Service
public class JoinService {
    private final JoinRepository joinRepository;

    public JoinService(JoinRepository joinRepository) {
        this.joinRepository = joinRepository;
    }

    public void join(JoinRequestDTO joinRequestDTO) {
        String bCryptPassword = getBCryptPassword(joinRequestDTO.password());
        User userForSave = User.ofJoinRequestDTO(joinRequestDTO, bCryptPassword);
        joinRepository.save(userForSave);
    }

    public void validateDuplication(String username) {
        if(joinRepository.findByUsername(username) == null){
            throw new UsernameDuplicationException(ExceptionMessage.DUPLICATE_USERNAME.getMessageWithOneARG(username));
        }
    }

    private String getBCryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
