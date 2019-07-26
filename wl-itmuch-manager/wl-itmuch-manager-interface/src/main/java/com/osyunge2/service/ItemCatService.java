package com.osyunge2.service;

import com.osyunge2.dataobject.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
