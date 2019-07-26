package com.osyunge2.portal.controller;

import com.osyunge2.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;
@RequestMapping("/")
    public  String showIndex(){
        return "index";
    }
    @RequestMapping("/index")
    public String showIndex(Model model){
        String json=contentService.getAD1List();
        model.addAttribute("ad1",json);
        return "index";
    }

}
