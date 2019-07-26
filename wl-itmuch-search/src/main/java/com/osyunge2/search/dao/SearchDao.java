package com.osyunge2.search.dao;

import com.osyunge2.dataobject.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {
     SearchResult search(SolrQuery query) throws Exception;
}
