package com.example.hibernate;

import com.example.hibernate.pagination.Page;
import com.example.hibernate.pagination.Pageable;

public interface BookRepository {

    Page<Book> findByTitle(String title, Pageable pageable);
}
