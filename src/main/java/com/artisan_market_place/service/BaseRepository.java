package com.artisan_market_place.service;


import java.util.List;
import java.util.Map;

public interface BaseRepository {
    public List<Object[]> findUsingNativeSQLQueryList(String sql);
    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int pageSize, int pageNumber);
    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int maxRecordCount);
    public List<Object[]> findUsingNativeSQLQuery(String sql, Map<String, Object> paramsMap, int maxRecordCount);
}
