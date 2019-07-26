package com.osyunge2.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.mapper.TbItemParamMapper;
import com.osyunge2.pojo.TbItemParam;
import com.osyunge2.pojo.TbItemParamExample;
import com.osyunge2.pojo.TbItemParamPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService{
    @Autowired
    private TbItemParamMapper tbItemParamMapper;
    @Override
    public EasyUIDataGridResult getItemParamList(int page, int rows) {
        System.out.println("开始");
        PageHelper.startPage(page, rows);
        List<TbItemParamPlus> list=tbItemParamMapper.selectAllTbItemParamItem();
        PageInfo<TbItemParamPlus> pageInfo=new PageInfo<>(list);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        System.out.println(result);
        return result;
    }

    @Override
    public FCResult getItemParamByCid(Long cid) {
        TbItemParamExample example=new TbItemParamExample();
        TbItemParamExample.Criteria criteria=example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list=tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(list!=null&&list.size()>0){
            TbItemParam itemParam=list.get(0);
            return FCResult.ok(itemParam);
        }
        return FCResult.ok();
    }

    @Override
    public FCResult insertItemParam(Long cid, String paramData) {
        TbItemParam itemParam=new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        tbItemParamMapper.insert(itemParam);
        return  FCResult.ok();


    }

    @Override
    public FCResult deleteItemParam(Long[] cid) {
        int rows=0;
        for(int i=0;i<cid.length;i++) {
            rows+= tbItemParamMapper.deleteByPrimaryKey(cid[i]);
        }
        if(rows==cid.length){
            return FCResult.ok();
        }
        return null;
    }
}
