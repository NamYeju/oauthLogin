package com.example.oauthlogin.config.oauth;

import com.example.oauthlogin.domain.user.Role;
import com.example.oauthlogin.service.CustomOauthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity // spring security 설정들을 활성화시켜줌
@Configuration
public class OauthConfig{

    private final CustomOauthUserService customOauthUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                //authorizeHttpRequest : URL별 권한 관리를 설정하는 옵션의 시작점
                //antMatchers : 권한 관리 대상을 지정하는 옵션
                // URL, HTTP 메소드별로 관리 가능
                // anyRequest : 설정된 url 이외의 나머지 url들
                .authorizeHttpRequests((authz)->authz
                        .antMatchers("/", "/css/**", "/images/**").permitAll()
                        .antMatchers("/user").hasRole(Role.USER.name())
                        .anyRequest().authenticated())
                // 로그아웃 기능에 대한 여러 설정의 진입점
                .logout()
                .logoutSuccessUrl("/")
                .and()
                // Oauth2 로그인 기능에 대란 여러 설정의 진입점
                .oauth2Login()
                // Oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                .userInfoEndpoint()
                // 소셜 로그인 성공 시 후속 조치를 진행 할 UserService 인터페이스의 구현체를 등록
                // 리소스 서버 (구글과 같은 소셜 서비스들)에서 사용자 정보를 가져온 후 추가로 진행하고자 하는 기능 명시
                .userService(customOauthUserService);

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return (web -> web.ignoring().antMatchers("/", "/css/**", "images/**"));
//    }



}
