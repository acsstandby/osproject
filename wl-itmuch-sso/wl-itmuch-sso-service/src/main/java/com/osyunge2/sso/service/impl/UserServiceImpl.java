package com.osyunge2.sso.service.impl;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.sso.service.jedis.JedisClient;
import com.osyunge2.sso.mapper.TbUserMapper;
import com.osyunge2.sso.pojo.TbUser;
import com.osyunge2.sso.pojo.TbUserExample;
import com.osyunge2.sso.service.UserService;
import com.osyunge2.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Override
    public FCResult checkData(String content, Integer type) {
        //创建查询条件
        TbUserExample userExample=new TbUserExample();
        TbUserExample.Criteria criteria=userExample.createCriteria();
        //对数据进行校验：1，2，3分别代表username,phone,email
        //用户名校验
        if(1==type){
            criteria.andUsernameEqualTo(content);
        }
        //电话校验
        if(2==type){
            criteria.andPhoneEqualTo(content);
        }
        //email校验
        if(3==type){
            criteria.andEmailEqualTo(content);
        }
        List<TbUser> list=userMapper.selectByExample(userExample);
        if(list==null || list.size()==0){
            return FCResult.ok(true);
        }
        return FCResult.ok(false);
    }

    @Override
    public FCResult createUser(TbUser user) {
        user.setUpdated(new Date());
        user.setCreated(new Date());
        //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return FCResult.ok();
    }

    @Override
    public FCResult userLogin(String username, String password) {

        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list=userMapper.selectByExample(example);
        //如果没有此用户
        if (null==list || list.size()==0){
            return FCResult.build(400,"用户信息不存在");
        }
        TbUser user=list.get(0);
        //比对密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return FCResult.build(400,"密码错误");
        }
        //生成token(SessionId)
        String token= UUID.randomUUID().toString();
        //保存用户之前，把用户对象中的密码清空。
        user.setPassword(null);
        //把用户信息写入redis
        jedisClient.set("REDIS_USER_SESSION_KEY:"+token, JsonUtils.objectToJson(user));
        jedisClient.expire("REDIS_USER_SESSION_KEY:"+token,1000);

        //返回token
        return FCResult.ok(token);
    }

    @Override
    public FCResult getUserByToken(String token) {
        String json=jedisClient.get("REDIS_USER_SESSION_KEY:"+token);
        if (StringUtils.isBlank(json)){
            return FCResult.build(400,"此session已经过期，请重新登录");
        }
        jedisClient.expire("REDIS_USER_SESSION_KEY:"+token,1000);

        return FCResult.ok(JsonUtils.jsonToPojo(json,TbUser.class));
    }
}
