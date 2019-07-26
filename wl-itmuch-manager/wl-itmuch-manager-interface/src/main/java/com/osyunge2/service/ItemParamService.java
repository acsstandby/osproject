package com.osyunge2.service;

import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.pojo.TbItemParamPlus;

import java.util.List;

public interface ItemParamService {
    //分页查询所有参数列表
    public EasyUIDataGridResult getItemParamList(int page,int rows);
    //根据cid查询是否有分类属性
    public FCResult getItemParamByCid(Long cid);
    //插入参数信息
    public FCResult insertItemParam(Long cid,String paramData);
    //删除参数信息
    public FCResult deleteItemParam(Long[] cid);
}
