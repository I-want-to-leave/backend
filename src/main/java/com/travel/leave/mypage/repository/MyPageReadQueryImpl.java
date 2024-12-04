package com.travel.leave.mypage.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.board.entity.QPost;
import com.travel.leave.board.entity.QPostComment;
import com.travel.leave.entity.QUser;
import com.travel.leave.entity.QUserTravel;
import com.travel.leave.mypage.dto.MyPageTravelResponseDTO;
import com.travel.leave.mypage.dto.MyPageTravelsResponseDTO;
import com.travel.leave.travel.entity.QTravel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class MyPageReadQueryImpl implements MyPageReadQuery{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MyPageUserResponseDTO findMyPageUserInfo(Long userCode) {
        QUser qUser = QUser.user;
        QUserTravel qUserTravel = QUserTravel.userTravel;
        QPost qPost = QPost.post;
        QPostComment qPostComment = QPostComment.postComment;

        return jpaQueryFactory
                .select(Projections.fields(
                        MyPageUserResponseDTO.class,
                        qUser.code.as("userCode"),
                        qUser.nickname.as("nickname"),
                        ExpressionUtils.as(getPostCommentCountSubQuery(qPostComment, qPost, qUser), "postCommentCount"),
                        ExpressionUtils.as(getPostCountSubQuery(qPost, qUser), "postCount"),
                        ExpressionUtils.as(getTravelCountSubQuery(qUserTravel, qUser), "travelCount")
                        )
                )
                .from(qUser)
                .where(qUser.code.eq(userCode))
                .fetchOne();
    }

    @Override
    public MyPageTravelsResponseDTO findMyPageTravelInfo(Long userCode, Pageable pageable) {
        QUserTravel qUserTravel = QUserTravel.userTravel;
        QUser qUser = QUser.user;
        QTravel qTravel = QTravel.travel;

        // 조인 및 데이터 조회
        List<Tuple> results = jpaQueryFactory
                .select(
                        qUser.nickname, // 사용자 닉네임
                        Projections.fields(
                                MyPageTravelResponseDTO.class,
                                qTravel.code.as("travelCode"),
                                qTravel.name.as("travelName"),
                                qTravel.content.as("travelContent")
                        )
                )
                .from(qUser)
                .leftJoin(qUserTravel).on(qUserTravel.userCode.eq(qUser.code))
                .leftJoin(qTravel).on(qTravel.code.eq(qUserTravel.travelCode))
                .where(qUser.code.eq(userCode))
                //.orderBy() 집가서 PathBuilder와 Pageable, Order 알아보자.
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 사용자 닉네임 매핑
        String nickname = results.get(0).get(qUser.nickname);

        // 여행 목록 매핑
        List<MyPageTravelResponseDTO> travels = results.stream()
                .map(tuple -> tuple.get(1, MyPageTravelResponseDTO.class))
                .toList();

        return new MyPageTravelsResponseDTO(nickname, travels);
    }

    private Expression<Long> getPostCommentCountSubQuery(QPostComment qPostComment, QPost qPost, QUser qUser){
        return JPAExpressions
                .select(qPostComment.count())
                .from(qPostComment)
                .join(qPost).on(qPostComment.postCode.eq(qPost.postCode))
                .where(qPost.userCode.eq(qUser.code));
    }

    private Expression<Long> getPostCountSubQuery(QPost qPost, QUser qUser){
        return JPAExpressions
                .select(qPost.count())
                .from(qPost)
                .where(qPost.userCode.eq(qUser.code));
    }

    private Expression<Long> getTravelCountSubQuery(QUserTravel qUserTravel, QUser qUser){
        return JPAExpressions
                .select(qUserTravel.count())
                .from(qUserTravel)
                .where(qUserTravel.code.eq(qUser.code));
    }

}
