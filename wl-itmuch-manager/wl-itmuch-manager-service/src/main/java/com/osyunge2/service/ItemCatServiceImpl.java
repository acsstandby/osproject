package com.osyunge2.service;

import com.osyunge2.dataobject.EasyUITreeNode;
import com.osyunge2.mapper.TbItemCatMapper;
import com.osyunge2.pojo.TbItemCat;
import com.osyunge2.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        TbItemCatExample itemCatExample=new TbItemCatExample();
        TbItemCatExample.Criteria criteria=itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> itemCatList=itemCatMapper.selectByExample(itemCatExample);
        List<EasyUITreeNode> treeNodeList=new ArrayList<>();
        for (TbItemCat itemCat:itemCatList
             ) {
            EasyUITreeNode treeNode=new EasyUITreeNode();
            treeNode.setId(itemCat.getId());
            treeNode.setText(itemCat.getName());
            treeNode.setState(itemCat.getIsParent()?"closed":"open");
            treeNodeList.add(treeNode);


        }


        return treeNodeList;
    }
}
