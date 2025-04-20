BEGIN;
DO
$$
BEGIN
INSERT INTO books (title, author, publication_year, isbn)
VALUES ('Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 2008, '9780132350884'),
       ('Design Patterns: Elements of Reusable Object-Oriented Software',
        'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', 1994, '9780201633610'),
       ('The Pragmatic Programmer: Your Journey to Mastery', 'Andrew Hunt, David Thomas', 1999, '9780201616224'),
       ('Refactoring: Improving the Design of Existing Code', 'Martin Fowler', 1999, '9780201485677'),
       ('Code Complete: A Practical Handbook of Software Construction', 'Steve McConnell', 2004, '9780735619678'),
       ('Working Effectively with Legacy Code', 'Michael Feathers', 2004, '9780131177055'),
       ('Domain-Driven Design: Tackling Complexity in the Heart of Software', 'Eric Evans', 2003, '9780321125217'),
       ('The Mythical Man-Month: Essays on Software Engineering', 'Frederick P. Brooks Jr.', 1975, '9780201835953');
END
$$;
