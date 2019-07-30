package com.osyunge2.sso.controller;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.sso.pojo.TbUser;
import com.osyunge2.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired UserService userService;
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){
        FCResult result=null;
        //参数有效性检验
        if(StringUtils.isBlank(param)){
            result=FCResult.build(400,"校验内容不能为空");
        }
        if(type==null){
            result=FCResult.build(400,"校验内容类型不能为空");
        }
        if(type!=1&&type!=2&&type!=3){
            result=FCResult.build(400,"校验内容类型错误");
        }
        //校验出错
        if (null!=result) {
            if (null != callback) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return result;
            }
        }
            //调用服务
            try{
                result=userService.checkData(param,type);

            }
            catch (Exception e){
                result=FCResult.build(500, String.valueOf(e.getStackTrace()));
            }
            if (null!=callback){
                MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            else {
                return result;
            }
        }
    @RequestMapping("/register")
    public FCResult createUser(TbUser user){
        try{
            FCResult result=userService.createUser(user);
            return result;
        }
        catch (Exception e){
            return FCResult.build(500, String.valueOf(e.getStackTrace()));
        }
    }
    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public FCResult userLogin(String username,String password){
        try{
            FCResult result=userService.userLogin(username,password);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return FCResult.build(500, String.valueOf(e.getStackTrace()));

        }
    }
    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        FCResult result=null;
        try{
            result=userService.getUserByToken(token);

        }catch (Exception e){
            e.printStackTrace();
            result=FCResult.build(500, String.valueOf(e.getStackTrace()));
        }
        if (StringUtils.isBlank(callback)){
            return result;
        }
        else {
            MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }


}
