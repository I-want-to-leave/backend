package com.travel.leave.domain.mypage.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.domain.mypage.dto.MyPageCommentInfoResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageCommentInfosResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageInfoCountsResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPagePostInfoResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPagePostInfosResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageTravelResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageTravelsResponseDTO;
import com.travel.leave.subdomain.post.entity.QPost;
import com.travel.leave.subdomain.postcomment.entity.QPostComment;
import com.travel.leave.subdomain.travel.entity.QTravel;
import com.travel.leave.subdomain.user.entity.QUser;
import com.travel.leave.subdomain.usertravel.entity.QUserTravel;
import com.travel.leave.utility.QueryDslUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class MyPageReadQueryImpl implements MyPageReadQuery{
    private final JPAQueryFactory jpaQueryFactory;

    private final QUser qUser = QUser.user;
    private final QPost qPost = QPost.post;
    private final QPostComment qPostComment = QPostComment.postComment;
    private final QUserTravel qUserTravel = QUserTravel.userTravel;
    private final QTravel qTravel = QTravel.travel;

    @Override
    public MyPagePostInfosResponseDTO findMyPagePostInfo(Long userCode, Pageable pageable) {
        return new MyPagePostInfosResponseDTO(
                jpaQueryFactory.select(Projections.fields(
                                MyPagePostInfoResponseDTO.class,
                                qPost.postCode.as("postCode"),
                                qPost.createdAt.as("createdAt"),
                                qTravel.startDate.as("startDate"),
                                qTravel.endDate.as("endDate"),
                                qPost.postContent.as("content")))
                        .from(qUser)
                        .where(qUser.code.eq(userCode))
                        .leftJoin(qUserTravel).on(qUserTravel.userCode.eq(userCode))
                        .leftJoin(qTravel).on(qTravel.code.eq(qUserTravel.travelCode))
                        .leftJoin(qPost).on(qPost.userCode.eq(userCode))
                        .orderBy(QueryDslUtils.getOrderSpecifiers(pageable, qPost))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch());
    }

    @Override
    public MyPageCommentInfosResponseDTO findMyPageCommentInfo(Long userCode, Pageable pageable) {
        return new MyPageCommentInfosResponseDTO(
                jpaQueryFactory.select(Projections.fields(
                                MyPageCommentInfoResponseDTO.class,
                                qPostComment.code.as("commentCode"),
                                qPostComment.content.as("content"),
                                qPostComment.createdAt.as("createdAt"),
                                qPost.postCode.as("postCode"),
                                qPost.postTitle.as("postTitle")))
                        .from(qPost)
                        .where(qPost.userCode.eq(userCode))
                        .leftJoin(qPostComment).on(qPostComment.post.postCode.eq(qPost.postCode))
                        .orderBy(QueryDslUtils.getOrderSpecifiers(pageable, qPostComment))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch()
        );
    }

    @Override
    public MyPageInfoCountsResponseDTO findMyPageCountsInfo(Long userCode) {
        return jpaQueryFactory.select(Projections.fields(
                        MyPageInfoCountsResponseDTO.class,
                        ExpressionUtils.as(getPostCommentCountSubQuery(qPostComment, qPost, qUser), "commentCount"),
                        ExpressionUtils.as(getPostCountSubQuery(qPost, qUser), "postCount"),
                        ExpressionUtils.as(getTravelCountSubQuery(qUserTravel, qUser), "travelCount")))
                .from(qUser)
                .where(qUser.code.eq(userCode))
                .fetchOne();
    }

    @Override
    public MyPageTravelsResponseDTO findMyPageTravelInfo(Long userCode, Pageable pageable) {
        //UserTravel 을 구해옴 -> 내 여행들
        //거기에 Travel 을 travelCode 로 조인
        //거기에 UserTravel 을 travelCode 로 조인 -> 같이 여행가는 사람들 조인 준비
        //거기에 User 를 userCode 로 조인

        //내 여행 정보를 기준으로 시작
        List<Tuple> tuples = jpaQueryFactory
                .select(
                        qTravel.code, // 여행 코드
                        qTravel.name, // 여행 이름
                        qTravel.content, // 여행 내용
                        qTravel.imageUrl,
                        qTravel.startDate,
                        qTravel.endDate,
                        qUser.nickname // 여행에 참여한 사용자 닉네임
                )
                .from(qUserTravel)
                .join(qTravel).on(qTravel.code.eq(qUserTravel.travelCode)) // UserTravel -> Travel
                .join(qUserTravel).on(qUserTravel.travelCode.eq(qTravel.code)) // 동일 TravelCode 로 다른 UserTravel 과 조인
                .join(qUser).on(qUser.code.eq(qUserTravel.userCode)) // UserTravel -> User
                .where(qUserTravel.userCode.eq(userCode)) // 내 여행들만 필터링
                .orderBy(QueryDslUtils.getOrderSpecifiers(pageable, qTravel))
                .offset(pageable.getOffset()) // 페이징 처리
                .limit(pageable.getPageSize())
                .fetch();

        //Tuple 을 DTO 로 매핑
        Map<Long, MyPageTravelResponseDTO> travelMap = new HashMap<>();

        for (Tuple tuple : tuples) {
            Long travelCode = tuple.get(qTravel.code);
            travelMap.computeIfAbsent(travelCode, code -> new MyPageTravelResponseDTO(
                    tuple.get(qTravel.code),
                    tuple.get(qTravel.name),
                    tuple.get(qTravel.content),
                    new ArrayList<>(),
                    tuple.get(qTravel.imageUrl),
                    Objects.requireNonNull(tuple.get(qTravel.startDate)).toLocalDate(),
                    Objects.requireNonNull(tuple.get(qTravel.endDate)).toLocalDate()
            )).userNicknames().add(tuple.get(qUser.nickname));
        }

        return new MyPageTravelsResponseDTO(travelMap.values().stream().toList());
    }


    private Expression<Long> getPostCommentCountSubQuery(QPostComment qPostComment, QPost qPost, QUser qUser){
        return JPAExpressions
                .select(qPostComment.count())
                .from(qPostComment)
                .join(qPost).on(qPostComment.post.postCode.eq(qPost.postCode))
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