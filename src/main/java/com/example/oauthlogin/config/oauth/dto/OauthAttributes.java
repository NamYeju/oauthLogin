package com.example.oauthlogin.config.oauth.dto;

import com.example.oauthlogin.domain.user.Role;
import com.example.oauthlogin.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * OauthUserService를 통해 가져온 oauthUser의 attribute를 담을 클래스
 * */
@Builder
@Getter
public class OauthAttributes{
    /**
     * attributes를 map으로 받는 이유 ??
     * */
    private Map<String, Object> attributes;
    // userNameAttributeName : 로그인 진행 시 키가 되는 필드 값 (pk같은) 구글에선 sub
    private String nameAttributeKey;
    private String name;
    private String email;

    // (OauthUserService)
    // OauthAttributes attributes = OauthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
    // registrationID : 구글인지 네이버인지 구분
    public static OauthAttributes of(String registrationID, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationID))
            return ofNaver("id", attributes);

        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OauthAttributes ofGoogle(String userNameAttributeKey, Map<String, Object> attributes){
        return OauthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeKey)
                .build();
    }

    public static OauthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
        return OauthAttributes.builder()
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // 시점: 처음 가입할 떄
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
