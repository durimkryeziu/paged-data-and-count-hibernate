package com.example.hibernate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BookRepository extends CrudRepository<Book, Long>, BookRepositoryCustom {
}
