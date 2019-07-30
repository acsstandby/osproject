package com.osyunge2.sso.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LoginController {
    @RequestMapping("/showLogin")
    public  String showLoginPage(){
        return "login";
    }

}
