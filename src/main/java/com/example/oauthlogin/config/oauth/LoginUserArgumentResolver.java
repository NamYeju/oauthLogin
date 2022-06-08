package com.example.oauthlogin.config.oauth;

import com.example.oauthlogin.config.oauth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/**
 * HandelrMethodArgumentResolver: 조건에 맞는 메소드가 있다면 해당 인터페이스의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있다.
 *
 * 항상 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가해줘야함
 * */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    /**
     * supportsParameter() : resolver가 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
     * 파라미터에 @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true 반환
     * */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    /**
     * 파라미터에 전달할 객체 생성 (실제로 바인딩 할 객체 리턴)
     * 여기서는 세션에서 객체를 가져온다.
     * */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
