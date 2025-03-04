package com.artisan_market_place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class BaseRepositoryImpl implements BaseRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> findUsingNativeSQLQueryList(String sql) {
        Query q = entityManager.createNativeQuery(sql);
        return q.getResultList();
    }

    @Override
    public List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int pageSize, int pageNumber) {
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
        if (pageSize > 0) {
            q.setMaxResults(pageSize);
        }
        if (pageNumber > 0) {
            q.setFirstResult(pageNumber);
        }
        return q.getResultList();
    }

    @Override
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
        return q.getResultList();
    }

    @Override
    public List<Object[]> findUsingNativeSQLQuery(String sql, Map<String, Object> paramsMap, int maxRecordCount) {
        Query q = entityManager.createNativeQuery(sql);
        if (paramsMap != null && !paramsMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (entry.getValue() != null) {
                    q.setParameter(entry.getKey(), entry.getValue());
                } else {
                    q.setParameter(entry.getKey(), Types.NULL);
                }
            }
        }
        if (maxRecordCount > 0) {
            q.setMaxResults(maxRecordCount);
        }
        return q.getResultList();
    }
}
