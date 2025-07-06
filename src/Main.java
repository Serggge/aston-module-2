import model.Book;
import model.Student;
import java.util.*;

public class Main {

    static List<Student> students = new ArrayList<>();

    static {
        List<Book> johnBooks = List.of(
                new Book("Pinocchio", 200, 1999),
                new Book("Algebra", 150, 2005),
                new Book("Biology", 100, 1990));
        students.add(new Student("John", johnBooks));
        List<Book> bobBooks = List.of(
                new Book("English", 180, 1999),
                new Book("Algebra", 150, 1980),
                new Book("Biology", 100, 2010),
                new Book("Physics", 300, 1970));
        students.add(new Student("Bob", bobBooks));
    }

    public static void main(String[] args) {
        students.stream()
                .peek(System.out::println)
                .map(Student::getBooks)
                .flatMap(List::stream)
                .sorted(Comparator.comparingInt(Book::getPageSize))
                .distinct()
                .filter(book -> book.getPublishYear() > 2000)
                .limit(3)
                .mapToInt(Book::getPublishYear)
                .findFirst()
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("The book is missing"));
    }
}