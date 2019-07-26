package com.osyunge2.rest.service.impl;

import com.osyunge2.dataobject.CatNode;
import com.osyunge2.dataobject.ItemCatResult;
import com.osyunge2.mapper.TbItemCatMapper;
import com.osyunge2.pojo.TbItemCat;
import com.osyunge2.pojo.TbItemCatExample;
import com.osyunge2.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public ItemCatResult getItemCatList() {
        List catList = getItemCatList(0l);
       ItemCatResult result=new ItemCatResult();
       result.setData(catList);
        return result;
    }
    public List getItemCatList(Long parentId){
        //查询模板
        TbItemCatExample tbItemExample=new TbItemCatExample();
        TbItemCatExample.Criteria criteria=tbItemExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list=itemCatMapper.selectByExample(tbItemExample);
        List result=new ArrayList<>();
        for(TbItemCat cat:list){
            //如果是父节点
            if(cat.getIsParent()){
                CatNode node=new CatNode();
                node.setUrl("/products/"+cat.getId()+".html");
                //如果为一级节点
                if(cat.getParentId()==0){
                    node.setName("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");

                }
                else{
                    node.setName(cat.getName());

                }
                //递归依次展开该节点下面的所有节点
                node.setItems(getItemCatList(cat.getId()));
                //把node添加到列表
                result.add(node);
            }
            //叶子节点
            else{
                String item="/products"+cat.getId()+".html"+cat.getName();
                result.add(item);
            }
        }

        return result;
    }
}
