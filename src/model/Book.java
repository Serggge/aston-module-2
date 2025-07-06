package model;

public class Book {

    private final String title;
    private final int pageSize;
    private final int publishYear;

    public Book(String title, int pageSize, int publishYear) {
        this.title = title;
        this.pageSize = pageSize;
        this.publishYear = publishYear;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPublishYear() {
        return publishYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            Book other = (Book) obj;
            return this.title.equals(other.title);
        }
    }
}