package com.osyunge2.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.dataobject.EasyUITreeNode;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.mapper.TbContentMapper;
import com.osyunge2.pojo.TbContent;
import com.osyunge2.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ContentServiceImpl implements ContentService{
    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public EasyUIDataGridResult getContentList(int page, int rows, Long id) {

        PageHelper.startPage(page,rows);
        TbContentExample tbContentExample=new TbContentExample();
        TbContentExample.Criteria criteria=tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(id);
        List<TbContent> list=tbContentMapper.selectByExample(tbContentExample);
        PageInfo<TbContent> pageInfo=new PageInfo<>(list);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public FCResult insertContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //插入数据
        tbContentMapper.insert(tbContent);
        return FCResult.ok();
    }

    @Override
    public List<TbContent> getContent(Long cid) {
        //根据cid查询内容列表
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria=example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list=tbContentMapper.selectByExampleWithBLOBs(example);
        return list;
    }
}
