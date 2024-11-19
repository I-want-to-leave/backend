package com.travel.leave.login.oauth.service;

import com.travel.leave.entity.User;
import com.travel.leave.login.oauth.dto.OAuth2AuthenticationDTO;
import com.travel.leave.login.oauth.service.response.FaceBookResponse;
import com.travel.leave.login.oauth.service.response.GoogleResponse;
import com.travel.leave.login.oauth.service.response.NaverResponse;
import com.travel.leave.login.oauth.service.response.OAuth2Response;
import com.travel.leave.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);    //우선 DefaultOAuth2UserService의 loadUser를 실행 후 요청에서 OAuth2User를 가져옴
        log.info(oAuth2User.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();   //registrationId는 google, naver와 같이 인증 api의 이름을 나타냄
        OAuth2Response oAuth2Response = getOAuth2ResponseWithOAuth2UserRegistration(registrationId, oAuth2User);
        String oAuthCode = oAuth2Response.getUserId();    //userId = google {구글에서의 인증 고유 식별번호}, naver {네이버에서,,}
        User databaseExistUser = userRepository.findByUsername(oAuthCode);

        if(isUserExist(databaseExistUser)){
            OAuth2AuthenticationDTO oAuth2AuthenticationDTO = OAuth2AuthenticationDTO.of(databaseExistUser, oAuthCode);
            return CustomOAuth2User.of(oAuth2AuthenticationDTO, oAuth2User.getAttributes());
        }

        //처음 로그인이면 자동으로 데이터베이스에 회원 정보 저장
        User user = User.ofOAuth2Response(oAuth2Response);
        OAuth2AuthenticationDTO oAuth2AuthenticationDTO = OAuth2AuthenticationDTO.of(userRepository.save(user), oAuthCode);
        return CustomOAuth2User.of(oAuth2AuthenticationDTO, oAuth2User.getAttributes());
    }

    private OAuth2Response getOAuth2ResponseWithOAuth2UserRegistration(String registrationId, OAuth2User oAuth2User) {
        if (registrationId.equals("naver")) {
            return new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("Google")) {
            return new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("facebook")) {
            return new FaceBookResponse(oAuth2User.getAttributes());//계속해서 OAuth API 추가가능(facebook, amazone 등)
        }
        throw new OAuth2AuthenticationException(new OAuth2Error("알 수 없는 에러, 무조건 registrationId는 존재해야함"));
    }

    private boolean isUserExist(User existData) {
        return existData != null;
    }
}
