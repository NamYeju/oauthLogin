package com.example.oauthlogin.controller;

import com.example.oauthlogin.config.oauth.LoginUser;
import com.example.oauthlogin.config.oauth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class OauthController {

    @GetMapping("/")
    public String login(Model model, @LoginUser SessionUser user){
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "login";
    }
}
