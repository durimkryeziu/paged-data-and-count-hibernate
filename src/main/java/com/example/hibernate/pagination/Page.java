package com.example.hibernate.pagination;

import java.util.List;

public record Page<T>(List<T> content, long totalElements) {

    public int size() {
        return content.size();
    }

}
