package com.osyunge2.portal.controller;

import com.osyunge2.dataobject.SearchResult;
import com.osyunge2.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1")Integer page, Model model){
        SearchResult result=null;
        try{
            if(queryString!=null){
                queryString=new String(queryString.getBytes("iso8859-1"),"utf-8");


            }
            result=searchService.search(queryString,page);

        }
        catch (Exception e){
                 e.printStackTrace();
        }
           model.addAttribute("page",page);
           model.addAttribute("itemList",result.getItemList());
           model.addAttribute("totalPages",result.getPageCount());
           model.addAttribute("query",queryString);



        return "search";
    }
}
