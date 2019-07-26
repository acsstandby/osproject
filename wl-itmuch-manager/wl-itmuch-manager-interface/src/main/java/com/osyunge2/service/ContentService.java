package com.osyunge2.service;

import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.pojo.TbContent;

import java.util.List;

public interface ContentService {
    //分页查询所有内容列表
    public EasyUIDataGridResult getContentList(int page,int rows,Long id);
    //新增内容项
    public FCResult insertContent(TbContent tbContent);
    //查询所有内容
    public List<TbContent> getContent(Long cid);
    //删除所选内容

}
