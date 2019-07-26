package com.osyunge2.rest.service;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.pojo.TbItem;

public interface ItemService {
    FCResult getBaseItemInfo(Long itemId);
    FCResult getItemDesc(Long itemId);
    FCResult getItemParam(Long itemId);
    TbItem getItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParamById(Long itemId);


}
