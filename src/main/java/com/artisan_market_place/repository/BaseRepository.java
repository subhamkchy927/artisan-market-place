package com.artisan_market_place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class BaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params) {
        return findUsingNativeSQLQuery(sql, params, -1);
    }

    public List<Object[]> findUsingNativeSQLQuery(String sql, Map<String, Object> paramsMap) {
        return findUsingNativeSQLQuery(sql, paramsMap, -1);
    }

    public List<Object[]> findUsingNativeSQLQueryList(String sql) {
        Query q = entityManager.createNativeQuery(sql);
        List<Object[]> result =  q.getResultList();
        return result;
    }

    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int pageSize, int pageNumber) {
        Query q = entityManager.createNativeQuery(sql);
        if(params != null){
            int i = 1;
            for(Object param : params){
                if (param != null) {
                    q.setParameter(i, param);
                } else {
                    q.setParameter(i, Types.NULL);
                }
                i++ ;
            }
        }
        if(pageSize > 0){
            q.setMaxResults(pageSize);
        }
        if(pageNumber > 0){
            q.setFirstResult(pageNumber);
        }
        return q.getResultList();
    }

    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int maxRecordCount) {
        Query q = entityManager.createNativeQuery(sql);
        if (params != null) {
            int i = 1;
            for (Object param : params) {
                if (param != null) {
                    q.setParameter(i, param);
                } else {
                    q.setParameter(i, Types.NULL);
                }
                i++;
            }
        }
        if (maxRecordCount > 0) {
            q.setMaxResults(maxRecordCount);
        }
        List<Object[]> result = q.getResultList();

        return result;
    }

    public List<Object[]> findUsingNativeSQLQuery(String sql, Map<String, Object> paramsMap, int maxRecordCount) {
        Query q = entityManager.createNativeQuery(sql);
        if (paramsMap != null && paramsMap.size() > 0) {
            for (String param : paramsMap.keySet()) {
                if (param != null) {
                    q.setParameter(param, paramsMap.get(param));
                } else {
                    q.setParameter(param, Types.NULL);
                }
            }
        }
        if (maxRecordCount > 0) {
            q.setMaxResults(maxRecordCount);
        }
        List<Object[]> result = q.getResultList();

        return result;
    }


}
