package com.osyunge2.portal.service.impl;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.dataobject.SearchResult;
import com.osyunge2.portal.service.SearchService;
import com.osyunge2.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Override
    public SearchResult search(String queryString, int page) {

         try{
             Map<String,String> paramMap=new HashMap<>();
             paramMap.put("q",queryString);
             paramMap.put("page",page+"");
             String json=HttpClientUtil.doGet("http://localhost:8083/search/query",paramMap);
             FCResult result=FCResult.formatToPojo(json,SearchResult.class);
             if(result.getStatus()==200){
                 SearchResult searchResult=((SearchResult) result.getData());
                 return searchResult;
             }
         }
         catch (Exception e){
               e.printStackTrace();
         }

        return null;
    }
}
