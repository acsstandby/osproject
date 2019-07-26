package com.osyunge2.portal.service;

import com.osyunge2.dataobject.SearchResult;

public interface SearchService {
    SearchResult search(String queryString,int page);
}
