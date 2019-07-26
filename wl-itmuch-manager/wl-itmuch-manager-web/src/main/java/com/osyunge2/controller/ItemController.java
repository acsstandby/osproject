package com.osyunge2.controller;

import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.pojo.TbItem;
import com.osyunge2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @GetMapping("/{itemId}")
    @ResponseBody
    public TbItem selectItemInfoById(@PathVariable long itemId){

        return itemService.selectItemInfoById(itemId);

    }
    @GetMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(int page,int rows){

        return itemService.getItemList(page, rows);
    }
}
