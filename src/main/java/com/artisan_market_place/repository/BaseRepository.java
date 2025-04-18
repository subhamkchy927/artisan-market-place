package com.artisan_market_place.repository;

import java.util.List;
import java.util.Map;

public interface BaseRepository {

        List<Object[]> findUsingNativeSQLQueryList(String sql);

        List<Object[]> findUsingNativeSQLQuery(String sql, List<Object> params, int pageSize, int pageNumber);
}
