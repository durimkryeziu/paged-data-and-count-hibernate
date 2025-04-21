package com.example.hibernate.pagination;

public interface Pageable {

    int getPageSize();

    long getOffset();

    boolean isPaged();

    static Pageable ofSize(int size) {
        return PageRequest.of(0, size);
    }
}
