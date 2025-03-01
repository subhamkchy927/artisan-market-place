package com.artisan_market_place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

public interface BaseRepository {
    public List<Object[]> findUsingNativeSQLQueryList(String sql);
    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int pageSize, int pageNumber);
    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int maxRecordCount);
    public List<Object[]> findUsingNativeSQLQuery(String sql, Map<String, Object> paramsMap, int maxRecordCount);
}
