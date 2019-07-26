package com.osyunge2.search.controller;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.search.service.ItemService;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/impoart/all")
    @ResponseBody
    public FCResult importAllItemInfo() throws IOException, SolrServerException {
        FCResult itemListResult=itemService.getItemList();
        return itemListResult;
    }
}
