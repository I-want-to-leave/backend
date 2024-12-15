package com.travel.leave.board.service.like_sync;

import com.travel.leave.board.entity.PostLike;
import com.travel.leave.board.mapper.PostLikeMapper;
import com.travel.leave.board.repository.post_like.PostLikeRepository;
import com.travel.leave.board.service.like_sync.redis_sync.RedisUserFieldParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncLikesToDBManager {

    private final PostLikeRepository postLikeRepository;
    private final RedisUserFieldParser redisUserFieldParser;

    @Transactional
    public void syncLikesToDB(Long postCode, Set<Object> userFields) {
        Set<Long> dbUserCodes = getUserCodesFromDB(postCode);
        Set<Long> redisUserCodes = getUserCodesFromRedis(userFields);

        Set<Long> usersToAdd = calculateUsersToAdd(redisUserCodes, dbUserCodes);
        Set<Long> usersToRemove = calculateUsersToRemove(redisUserCodes, dbUserCodes);
        processChanges(postCode, usersToAdd, usersToRemove);
    }

    private Set<Long> getUserCodesFromDB(Long postCode) {
        return postLikeRepository.findAllByPostCode(postCode).stream()
                .map(PostLike::getUserCode)
                .collect(Collectors.toSet());
    } // DB 에서 목표 게시글의 좋아요 데이터 중 user_code 가져오기

    private Set<Long> getUserCodesFromRedis(Set<Object> userFields) {
        return userFields.stream()
                .map(redisUserFieldParser::extractUserCodeFromRedisField)
                .collect(Collectors.toSet());
    } // redis 에서 목표 게시글의 좋아요 데이터 중 user_code 가져오기

    private Set<Long> calculateUsersToAdd(Set<Long> redisUserCodes, Set<Long> dbUserCodes) {
        return redisUserCodes.stream()
                .filter(userCode -> !dbUserCodes.contains(userCode))
                .collect(Collectors.toSet());
    } // 비교하여서 db 에는 없는 user_code 모아서 리스트 Set 변수 처리 -> db에 추가할 것

    private Set<Long> calculateUsersToRemove(Set<Long> redisUserCodes, Set<Long> dbUserCodes) {
        return dbUserCodes.stream()
                .filter(userCode -> !redisUserCodes.contains(userCode))
                .collect(Collectors.toSet());
    } // 비교하여서 redis 에는 없는 user_code 모아서 리스트 Set 변수 처리 -> db에 없애야 할 것

    private void processChanges(Long postCode, Set<Long> usersToAdd, Set<Long> usersToRemove) {
        if (!usersToAdd.isEmpty() || !usersToRemove.isEmpty()) {
            removeUsers(postCode, usersToRemove);
            addUsers(postCode, usersToAdd);
        }
    } // db 와 redis 중 변경 사항이 있으면 작동

    private void removeUsers(Long postCode, Set<Long> usersToRemove) {
        usersToRemove.forEach(userCode ->
                postLikeRepository.deleteByPostCodeAndUserCode(postCode, userCode)
        );
    }

    private void addUsers(Long postCode, Set<Long> usersToAdd) {
        usersToAdd.forEach(userCode -> {
            PostLike postLike = PostLikeMapper.toPostLikeEntity(postCode, userCode);
            postLikeRepository.save(postLike);
        });
    }
}