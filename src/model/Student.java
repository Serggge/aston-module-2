package model;

import java.util.Collections;
import java.util.List;

public class Student {
    private final String name;
    private List<Book> books;

    public Student(String name) {
        this.name = name;
        this.books = Collections.emptyList();
    }

    public Student(String name, List<Book> books) {
        this(name);
        this.books = books;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public String toString() {
        return name;
    }
}
