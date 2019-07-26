package com.osyunge2.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.mapper.TbItemMapper;
import com.osyunge2.pojo.TbItem;
import com.osyunge2.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.lang.Long.parseLong;

@Service
public class ItemServiceImpl implements ItemService{


   @Autowired
   private TbItemMapper itemMapper;
   @Override
    public TbItem selectItemInfoById(Long itemid) {
       TbItemExample itemExample=new TbItemExample();
       TbItemExample.Criteria criteria=itemExample.createCriteria();
       //设置条件
       criteria.andIdEqualTo(itemid);
       //执行查询
       List<TbItem> tbItems=itemMapper.selectByExample(itemExample);
       TbItem item=null;
       if(tbItems.size()>0){
           item=tbItems.get(0);
       }
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        List<TbItem> itemList=itemMapper.selectByExample(new TbItemExample());
        //取分页信息
        PageInfo<TbItem> info=new PageInfo<>(itemList);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(itemList);
        result.setTotal(info.getTotal());
       return result;
    }

    @Override
    public int addItem(TbItem tbItem) {
       Random ra= new Random();
       Long id=ra.nextLong();
       tbItem.setId(id);

           int num=itemMapper.insert(tbItem);


       return num;
    }
}
