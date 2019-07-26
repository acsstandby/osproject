package com.osyunge2.search.service;

import com.osyunge2.dataobject.SearchResult;

public interface SearchService {
    SearchResult search(String queryString,int page,int rows) throws Exception;
}
