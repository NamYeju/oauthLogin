package com.example.oauthlogin.config.oauth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * controller 중 SessionUser user = (SessionUser) HttpSession.getAttribute("user");
 * -> 다른 곳에서도 세션 값이 필요하다면 같은 코드가 반복된다.
 * 메소드 인자로 세샨값을 바로 받을 수 있도록 어노테이션 기반으로 개선해보쟈..!
 * */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
