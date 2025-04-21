package com.example.hibernate;

import com.example.hibernate.HibernateUtils.ResultListAndCount;
import com.example.hibernate.pagination.Page;
import com.example.hibernate.pagination.Pageable;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.Objects;

class HibernateBookRepository implements BookRepository {

    private final Session session;

    HibernateBookRepository(Session session) {
        this.session = session;
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
        NativeQuery<Book> query = this.session.createNativeQuery(sql, Book.class);
        query.setParameter("title", "%" + title + "%");
        if (pageable.isPaged()) {
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult((int) pageable.getOffset());
        }
        ResultListAndCount<Book> resultListAndCount = HibernateUtils.getResultListAndCount(query, "total_count");
        return new Page<>(resultListAndCount.resultList(), resultListAndCount.count());
    }

}
