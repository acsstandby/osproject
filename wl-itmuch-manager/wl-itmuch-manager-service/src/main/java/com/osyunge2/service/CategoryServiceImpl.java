package com.osyunge2.service;

import com.osyunge2.dataobject.EasyUITreeNode;
import com.osyunge2.dataobject.FCResult;
import com.osyunge2.mapper.TbContentCategoryMapper;
import com.osyunge2.pojo.TbContentCategory;
import com.osyunge2.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;


    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        TbContentCategoryExample tbContentCategoryExample=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list=tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        List<EasyUITreeNode> result=new ArrayList<>();
        for (TbContentCategory tbcc:list
             ) {
            //
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(tbcc.getId());
            node.setText(tbcc.getName());
            node.setState(tbcc.getIsParent()?"closed":"open");
            result.add(node);
        }


        return result;
    }

    @Override
    public FCResult insertContentCat(String name, Long parentId) {
        TbContentCategory tbContentCategory=new TbContentCategory();
        //设置参数
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        tbContentCategory.setUpdated(new Date());
        //插入数据
        tbContentCategoryMapper.insert(tbContentCategory);
        //取插入的主键
        Long id=tbContentCategory.getId();
        //根据parentId查询父节点
        TbContentCategory tb=tbContentCategoryMapper.selectByPrimaryKey(parentId);
        //判断自身是否为父节点
        if(!tb.getIsParent()){//不是父节点，则自身为父节点，说明查到的是自己
            //将自身设置为父节点
            tb.setIsParent(true);
            //更新数据
            tbContentCategoryMapper.updateByPrimaryKey(tb);


        }

        //返回插入id
        return FCResult.ok(id);
    }

    @Override
    public void deleteCategory(Long parentId, Long id) {
        //查询要删除的节点
        TbContentCategory tbContentCategory=tbContentCategoryMapper.selectByPrimaryKey(id);
        //如果要删除的节点是父节点
        if(tbContentCategory.getIsParent()){
            //查询该父节点下的所有子节点
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria=example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> list=tbContentCategoryMapper.selectByExample(example);
            //循环删除子节点，有可能父节点也在其中
            for (TbContentCategory tb:list
                 ) {
                tbContentCategoryMapper.deleteByPrimaryKey(tb.getId());
            }

        }
        //检查父节点有没有被删除
        //或者是该节点不是父节点没有进if
        TbContentCategory tbContentCategory2=tbContentCategoryMapper.selectByPrimaryKey(id);
        if(tbContentCategory2!=null){
            //判断下是否为子节点
            if(!tbContentCategory2.getIsParent()){
                //如果是子节点并且父节点下没有子节点，则将父节点变为子节点
                System.out.println(id);
                System.out.println(parentId);
                TbContentCategory tbParent=tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory2.getParentId());
                //寻找该父节点下的节点,先删除该子节点
                tbContentCategoryMapper.deleteByPrimaryKey(id);
                TbContentCategoryExample example2=new TbContentCategoryExample();
                TbContentCategoryExample.Criteria criteria2=example2.createCriteria();
                criteria2.andParentIdEqualTo(tbParent.getId());
                List<TbContentCategory> list=tbContentCategoryMapper.selectByExample(example2);
                if(list==null||list.size()<=0){//为空说明该节点下没有子节点
                    tbParent.setIsParent(false);
                    //更新
                    int rows=tbContentCategoryMapper.updateByPrimaryKey(tbParent);
                    System.out.println(rows);
                }

            }
            //不是就直接删除
             tbContentCategoryMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public void renameCategory(Long id, String name) {
        //查找要修改的节点id
        TbContentCategory contentCategory=tbContentCategoryMapper.selectByPrimaryKey(id);
        //将名字放入节点
        contentCategory.setName(name);
        //更新
        tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
    }


}
