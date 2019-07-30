package com.osyunge2.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class RegisterController {
    @RequestMapping("/{page}")
    public String showRegisterPage(@PathVariable String page) {
        return page;
    }
}