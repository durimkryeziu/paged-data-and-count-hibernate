package com.example.hibernate;

import com.example.hibernate.HibernateUtils.ResultListAndCount;
import com.example.hibernate.pagination.Page;
import com.example.hibernate.pagination.Pageable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.Objects;

class JpaBookRepository implements BookRepository {

    private final EntityManager entityManager;

    JpaBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Book> findByTitle(String title, Pageable pageable) {
        Objects.requireNonNull(pageable, "pageable must not be null");
        String sql = """
                SELECT b.*, COUNT(*) OVER() AS total_count
                FROM books b
                WHERE b.title ILIKE :title
                ORDER BY b.id
                """;
        Query query = this.entityManager.createNativeQuery(sql, Book.class);
        query.setParameter("title", "%" + title + "%");
        if (pageable.isPaged()) {
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult((int) pageable.getOffset());
        }
        ResultListAndCount<Book> resultListAndCount = HibernateUtils.getResultListAndCount(query, "total_count");
        return new Page<>(resultListAndCount.resultList(), resultListAndCount.count());
    }

}
