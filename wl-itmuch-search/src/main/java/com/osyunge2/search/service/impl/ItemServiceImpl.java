package com.osyunge2.search.service.impl;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.search.mapper.ItemMapper;
import com.osyunge2.search.pojo.Item;
import com.osyunge2.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public FCResult getItemList(){
        List<Item> list = itemMapper.importAllItemList();
        try {
            for (Item item : list) {
                //创建一个SolrInputDocument对象
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSell_point());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategory_name());
                document.setField("item_desc", item.getItem_des());
                //写入索引库
                solrServer.add(document);

            }
            solrServer.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            return FCResult.build(500,"异常");
        }
        return FCResult.ok();
    }
}
