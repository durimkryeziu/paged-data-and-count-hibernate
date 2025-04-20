package com.example.hibernate;

import com.example.hibernate.HibernateUtils.ResultListAndCount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional(readOnly = true)
class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final EntityManager entityManager;

    BookRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Book> findByTitle(String title, Pageable pageable) {
        Objects.requireNonNull(pageable, "pageable must not be null");
        String sql = "SELECT *, COUNT(*) OVER() AS total_count FROM books WHERE title ILIKE :title";
        Query query = this.entityManager.createNativeQuery(sql, Book.class);
        query.setParameter("title", "%" + title + "%");
        if (pageable.isPaged()) {
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult((int) pageable.getOffset());
        }
        ResultListAndCount<Book> resultListAndCount = HibernateUtils.getResultListAndCount(query, "total_count");
        return new PageImpl<>(resultListAndCount.getResultList(), pageable, resultListAndCount.getCount());
    }
}
