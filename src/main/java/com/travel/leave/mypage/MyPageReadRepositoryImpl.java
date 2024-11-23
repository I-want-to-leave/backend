package com.travel.leave.mypage;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLExpressions;
import com.travel.leave.entity.QPost;
import com.travel.leave.entity.QPostComment;
import com.travel.leave.entity.QTravel;
import com.travel.leave.entity.QUser;
import com.travel.leave.entity.QUserTravel;
import java.util.List;

public class MyPageReadRepositoryImpl implements MyPageReadRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MyPageReadRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public MyPageTravelsResponseDTO findMyPageTravelsResponseDTO(Long userCode, int page, int size) {
        QTravel travel = QTravel.travel;
        QUser user = QUser.user;
        QUserTravel userTravel = QUserTravel.userTravel;

        List<Long> travelCodes = jpaQueryFactory
                .select(userTravel.travelCode)
                .from(userTravel)
                .where(userTravel.userCode.eq(userCode))
                .offset((long) page * size)
                .limit(size)
                .fetch();

        List<MyPageTravelResponseDTO> myPageTravelResponseDTOS = jpaQueryFactory
                .select(Projections.fields(MyPageTravelResponseDTO.class,
                        travel.code.as("travelCode"),
                        travel.name.as("name"),
                        travel.content.as("content"),
                        travel.created_at.as("createdAt"),
                        ExpressionUtils.as(SQLExpressions.groupConcat(user.nickname, ", "),"userNicknames")
                ))
                .from(travel)
                .join(userTravel).on(travel.code.eq(userTravel.travelCode))
                .join(user).on(userTravel.userCode.eq(user.code))
                .where(travel.code.in(travelCodes))
                .groupBy(travel.code)
                .fetch();

        return new MyPageTravelsResponseDTO(myPageTravelResponseDTOS);
    }

    @Override
    public MyPageProfileResponseDTO findMyPageProfileResponseDTO(Long userCode) {
        QUser user = QUser.user;
        QUserTravel userTravel = QUserTravel.userTravel;
        QPost post = QPost.post;
        QPostComment postComment = QPostComment.postComment;

        return jpaQueryFactory
                .select(Projections.fields(MyPageProfileResponseDTO.class,
                        user.nickname.as("nickname"),
                        user.email.as("email"),
                        Expressions.asNumber(userTravel.countDistinct()).as("travelCount"),
                        Expressions.asNumber(post.countDistinct().as("postCount")),
                        Expressions.asNumber(postComment.countDistinct().as("postCommentCount"))
                        ))
                .from(user)
                .join(userTravel).on(userTravel.userCode.eq(user.code))
                .join(post).on(post.userCode.eq(user.code))
                .join(postComment).on(post.userCode.eq(user.code))
                .where(user.code.eq(userCode))
                .groupBy(user.code)
                .fetchOne();
    }
}
