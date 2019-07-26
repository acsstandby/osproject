package com.osyunge2.service;

import com.osyunge2.dataobject.EasyUIDataGridResult;
import com.osyunge2.pojo.TbItem;
public interface ItemService {
    //根据商品id查询商品信息
    TbItem selectItemInfoById(Long id);
    //查询商品信息列表
    EasyUIDataGridResult getItemList(int page, int rows);
    //添加商品
    int addItem(TbItem tbItem);

}
