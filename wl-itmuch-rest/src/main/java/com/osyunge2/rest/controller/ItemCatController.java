package com.osyunge2.rest.controller;

import com.osyunge2.dataobject.ItemCatResult;
import com.osyunge2.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class ItemCatController {

  @Autowired
    private ItemCatService itemCatService;

    @ResponseBody
    @RequestMapping("/all")
    public Object getItemCatList(String callback){
        ItemCatResult result=itemCatService.getItemCatList();
        if(StringUtils.isBlank(callback)){
            return  result;
        }
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return  mappingJacksonValue;
    }
}
