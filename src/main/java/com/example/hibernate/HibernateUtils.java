package com.example.hibernate;

import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;

import java.util.ArrayList;
import java.util.List;

public final class HibernateUtils {

    private HibernateUtils() {
        // Prevent instantiation
    }

    @SuppressWarnings("unchecked")
    public static <T> ResultListAndCount<T> getResultListAndCount(NativeQuery nativeQuery, String countColumnAlias) {
        nativeQuery.addScalar(countColumnAlias, StandardBasicTypes.LONG);
        List<Object[]> results = nativeQuery.getResultList();
        long count = results != null && !results.isEmpty() ? count(results) : 0;
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

    public record ResultListAndCount<T>(List<T> resultList, long count) {
    }
}
