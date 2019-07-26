package com.osyunge2.controller;

import com.osyunge2.dataobject.EasyUITreeNode;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentListController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> list=categoryService.getContentCatList(parentId);
        return list;
    }
    @RequestMapping("/content/category/create")
    @ResponseBody
    public FCResult createNode(String name,Long parentId){
        return categoryService.insertContentCat(name, parentId);
    }

    @RequestMapping("/content/category/delete")
    @ResponseBody
    public void deleteNode(Long parentId,Long id){
        categoryService.deleteCategory(parentId, id);
    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    public void updateNode(Long id,String name){
        categoryService.renameCategory(id,name);
    }
}
