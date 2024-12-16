package com.travel.leave.mypage.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.board.entity.QPost;
import com.travel.leave.board.entity.QPostComment;
import com.travel.leave.login.entity.QUser;
import com.travel.leave.login.entity.QUserTravel;
import com.travel.leave.mypage.dto.MyPageCommentInfoResponseDTO;
import com.travel.leave.mypage.dto.MyPageCommentInfosResponseDTO;
import com.travel.leave.mypage.dto.MyPageInfoCountsResponseDTO;
import com.travel.leave.mypage.dto.MyPagePostInfoResponseDTO;
import com.travel.leave.mypage.dto.MyPagePostInfosResponseDTO;
import com.travel.leave.mypage.dto.MyPageTravelResponseDTO;
import com.travel.leave.mypage.dto.MyPageTravelsResponseDTO;
import com.travel.leave.ai_travel.entity.QTravel;
import com.travel.leave.utility.QueryDslUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
                // .leftJoin(qPostComment).on(qPostComment.postCode.eq(qPost.postCode))
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
        //UserTravel을 구해옴 -> 내 여행들
        //거기에 Travel을 travelCode로 조인
        //거기에 UserTravel을 travelCode로 조인 -> 같이 여행가는 사람들 조인 준비
        //거기에 User를 userCode로 조인

        //내 여행 정보를 기준으로 시작
        List<Tuple> tuples = jpaQueryFactory
                .select(
                        qTravel.code, // 여행 코드
                        qTravel.name, // 여행 이름
                        qTravel.content, // 여행 내용
                        qUser.nickname // 여행에 참여한 사용자 닉네임
                )
                .from(qUserTravel)
                .join(qTravel).on(qTravel.code.eq(qUserTravel.travelCode)) // UserTravel -> Travel
                .join(qUserTravel).on(qUserTravel.travelCode.eq(qTravel.code)) // 동일 TravelCode로 다른 UserTravel과 조인
                .join(qUser).on(qUser.code.eq(qUserTravel.userCode)) // UserTravel -> User
                .where(qUserTravel.userCode.eq(userCode)) // 내 여행들만 필터링
                .orderBy(QueryDslUtils.getOrderSpecifiers(pageable, qTravel))
                .offset(pageable.getOffset()) // 페이징 처리
                .limit(pageable.getPageSize())
                .fetch();

        //Tuple을 DTO로 매핑
        Map<Long, MyPageTravelResponseDTO> travelMap = new HashMap<>();

        for (Tuple tuple : tuples) {
            Long travelCode = tuple.get(qTravel.code);
            travelMap.computeIfAbsent(travelCode, code -> new MyPageTravelResponseDTO(
                    tuple.get(qTravel.code),
                    tuple.get(qTravel.name),
                    tuple.get(qTravel.content),
                    new ArrayList<>()
            )).userNicknames().add(tuple.get(qUser.nickname));
        }

        return new MyPageTravelsResponseDTO(travelMap.values().stream().toList());
    }


    private Expression<Long> getPostCommentCountSubQuery(QPostComment qPostComment, QPost qPost, QUser qUser){
        return JPAExpressions
                .select(qPostComment.count())
                .from(qPostComment)
                // .join(qPost).on(qPostComment.postCode.eq(qPost.postCode))
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