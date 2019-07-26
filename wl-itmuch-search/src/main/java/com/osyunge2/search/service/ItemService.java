package com.osyunge2.search.service;

import com.osyunge2.dataobject.FCResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface ItemService {
    FCResult getItemList() throws IOException, SolrServerException;
}
