package com.osyunge2.controller;

import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;
    @GetMapping("/item/param/list")
    @ResponseBody
    public EasyUIDataGridResult getParamList(int page,int rows){
        System.out.println("--------------------------");
        return itemParamService.getItemParamList(page, rows);
    }

    @RequestMapping("/item/param/query/itemcatid/{cid}")
    @ResponseBody
    public FCResult getItemCatByCid(@PathVariable Long cid){
        FCResult result=itemParamService.getItemParamByCid(cid);
        return result;

    }
    @RequestMapping("/item/param/save/{cid}")
    @ResponseBody
    public FCResult insertItemParam(@PathVariable Long cid,String paramData){

        FCResult result=itemParamService.insertItemParam(cid, paramData);
        return  result;
    }
    @RequestMapping("/item/param/delete")
    @ResponseBody
    public  FCResult deleteItemParam(Long[] ids){
        FCResult result=itemParamService.deleteItemParam(ids);
        return result;
    }

}
