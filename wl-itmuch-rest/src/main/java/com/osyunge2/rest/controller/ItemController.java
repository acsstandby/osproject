package com.osyunge2.rest.controller;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.pojo.TbItem;
import com.osyunge2.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public FCResult getItemBaseInfo(@PathVariable Long itemId){
        FCResult result=itemService.getBaseItemInfo(itemId);
        return result;
    }
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public FCResult getItemDesc(@PathVariable Long itemId){
        FCResult result=itemService.getItemDesc(itemId);
        return result;
    }
    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public FCResult getItemParam(@PathVariable Long itemId){
        FCResult result=itemService.getItemParam(itemId);
        return result;
    }
    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        TbItem item=itemService.getItemById(itemId);
        model.addAttribute("item",item);
        return "item";
    }
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc2(@PathVariable Long itemId){
        String string=itemService.getItemDescById(itemId);
        return string;
    }
    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemParamById(@PathVariable Long itemId){
        String string=itemService.getItemParamById(itemId);
        return string;
    }

}
