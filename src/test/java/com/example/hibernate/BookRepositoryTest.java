package com.example.hibernate;

import com.example.hibernate.pagination.Page;
import com.example.hibernate.pagination.Pageable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class BookRepositoryTest {

    @Container
    static final PostgreSQLContainer<?> postgreSql = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.15"));

    private Session session;

    @BeforeEach
    void setup() {
        while (!validateDatabaseConnection()) {
            // Waiting for PostgreSQL to be ready...
        }
        this.session = openHibernateSession();
        seedBooksData();
    }

    private static boolean validateDatabaseConnection() {
        try (Connection connection = DriverManager.getConnection(postgreSql.getJdbcUrl(), postgreSql.getUsername(), postgreSql.getPassword())) {
            PreparedStatement preparedStatement = connection.prepareStatement(postgreSql.getTestQueryString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private static Session openHibernateSession() {
        Configuration hibernateConfiguration = hibernateConfiguration();
        SessionFactory sessionFactory = hibernateConfiguration.buildSessionFactory();
        return sessionFactory.openSession();
    }

    private static Configuration hibernateConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperty("connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("jakarta.persistence.jdbc.url", postgreSql.getJdbcUrl());
        configuration.setProperty("jakarta.persistence.jdbc.user", postgreSql.getUsername());
        configuration.setProperty("jakarta.persistence.jdbc.password", postgreSql.getPassword());
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.highlight_sql", "true");
        configuration.addAnnotatedClass(Book.class);
        return configuration;
    }

    private void seedBooksData() {
        String sql = """
                INSERT INTO books (title, author, publication_year, isbn)
                VALUES ('Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 2008, '9780132350884'),
                       ('Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', 1994, '9780201633610'),
                       ('The Pragmatic Programmer: Your Journey to Mastery', 'Andrew Hunt, David Thomas', 1999, '9780201616224'),
                       ('Refactoring: Improving the Design of Existing Code', 'Martin Fowler', 1999, '9780201485677'),
                       ('Code Complete: A Practical Handbook of Software Construction', 'Steve McConnell', 2004, '9780735619678'),
                       ('Working Effectively with Legacy Code', 'Michael Feathers', 2004, '9780131177055'),
                       ('Domain-Driven Design: Tackling Complexity in the Heart of Software', 'Eric Evans', 2003, '9780321125217'),
                       ('The Mythical Man-Month: Essays on Software Engineering', 'Frederick P. Brooks Jr.', 1975, '9780201835953');
                """;
        this.session.getTransaction().begin();
        this.session.createNativeMutationQuery(sql).executeUpdate();
        this.session.getTransaction().commit();
    }

    @Test
    void findByTitle() {
        BookRepository bookRepository = new HibernateBookRepository(this.session);
        Page<Book> booksPage = bookRepository.findByTitle("code", Pageable.ofSize(2));
        assertThat(booksPage.size()).isEqualTo(2);
        assertThat(booksPage.totalElements()).isEqualTo(4);
        assertThat(booksPage.content().stream().map(Book::getTitle))
                .containsExactly("Clean Code: A Handbook of Agile Software Craftsmanship",
                        "Refactoring: Improving the Design of Existing Code");
    }

    @AfterEach
    void tearDown() {
        if (this.session != null && this.session.isOpen()) {
            this.session.close();
        }
    }
}
