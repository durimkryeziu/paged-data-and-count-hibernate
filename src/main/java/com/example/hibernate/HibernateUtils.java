package com.example.hibernate;

import jakarta.persistence.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public final class HibernateUtils {

    private HibernateUtils() {
        // Prevent instantiation
    }

    @SuppressWarnings("unchecked")
    public static <T> ResultListAndCount<T> getResultListAndCount(Query query, String countColumnAlias) {
        NativeQuery<Object[]> nativeQuery = query.unwrap(NativeQuery.class);
        nativeQuery.addScalar(countColumnAlias, StandardBasicTypes.LONG);
        List<Object[]> results = nativeQuery.getResultList();
        long count = !CollectionUtils.isEmpty(results) ? count(results) : 0;
        List<T> resultList = new ArrayList<>();
        for (Object[] result : results) {
            resultList.add((T) result[0]);
        }
        return new ResultListAndCount<>(resultList, count);
    }

    private static long count(List<Object[]> results) {
        Object[] row = results.get(0);
        return (long) row[row.length - 1];
    }

    public static final class ResultListAndCount<T> {

        private final List<T> resultList;
        private final long count;

        private ResultListAndCount(List<T> resultList, long count) {
            this.resultList = resultList;
            this.count = count;
        }

        public List<T> getResultList() {
            return this.resultList;
        }

        public long getCount() {
            return this.count;
        }
    }
}

