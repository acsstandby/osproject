package com.osyunge2.search.Test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SolrJTest {
    @Test
    public void addDocument() throws IOException, SolrServerException {
        SolrServer solrServer=new HttpSolrServer("http://192.168.150.129:8080/solr");
        SolrInputDocument document=new SolrInputDocument();
        document.addField("id","test001");
        document.addField("item_title","测试商品2");
        document.addField("item_price",53533);
        solrServer.add(document);
        solrServer.commit();

    }


    public void deleteDocument() throws IOException, SolrServerException {
        SolrServer solrServer=new HttpSolrServer("http://192.168.150.129:8080/solr");
        solrServer.deleteById("test001");
        solrServer.commit();
    }
}
