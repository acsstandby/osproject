package com.osyunge2.service;

import com.osyunge2.dataobject.EasyUITreeNode;
import com.osyunge2.dataobject.FCResult;

import java.util.List;

public interface CategoryService {
    //查询所有类目
    public List<EasyUITreeNode> getContentCatList(Long parentId);

    //新增节点
    public FCResult insertContentCat(String name, Long parentId);

    //删除节点
    public void deleteCategory(Long parentId,Long id);

    //重命名
    public void renameCategory(Long id,String name);
}