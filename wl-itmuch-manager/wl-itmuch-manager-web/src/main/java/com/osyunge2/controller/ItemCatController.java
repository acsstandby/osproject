package com.osyunge2.controller;

import com.osyunge2.dataobject.EasyUITreeNode;
import com.osyunge2.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> list=itemCatService.getItemCatList(parentId);
        System.out.println(list);
        return list;
    }

}
