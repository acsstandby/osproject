package com.osyunge2.sso.service;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.sso.pojo.TbUser;

public interface UserService {
  FCResult checkData(String content,Integer type);
  FCResult createUser(TbUser user);
  FCResult userLogin(String username,String password);
  FCResult getUserByToken(String token);
}
