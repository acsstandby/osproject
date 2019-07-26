package com.osyunge2.controller;

import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.pojo.TbContent;
import com.osyunge2.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(int page,int rows,Long categoryId){
        System.out.println("----------------");
        System.out.println(categoryId);
        return contentService.getContentList(page, rows, categoryId);
    }
    @RequestMapping("/content/save")
    @ResponseBody
    public FCResult insertContent(TbContent tbContent){
        FCResult result=contentService.insertContent(tbContent);
        return result;
    }
    @RequestMapping("/content/{cid}")
    @ResponseBody
    public  FCResult getContent(@PathVariable Long cid){
        try{
            List<TbContent> list=contentService.getContent(cid);
            return  FCResult.ok(list);
        }
        catch (Exception e){
            e.printStackTrace();
            return FCResult.build(500,"服务器内部异常");
        }
    }
}
