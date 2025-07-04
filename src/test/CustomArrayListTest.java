package test;

import model.Student;
import org.junit.jupiter.api.*;
import structures.impl.*;
import structures.CustomList;
import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

    static CustomList<Student> students;

    @BeforeAll
    static void init() {
        students = new CustomArrayList<>();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        students.clear();
    }

    @Test
    void tryAddNullValueTiListThenRejectAdditionAndReturnFalseValue() {
        boolean isAdded = students.add(null);

        assertEquals(0, students.size());
        assertFalse(isAdded);
    }

    @Test
    void addStudentToListThenSizeMustBeOneAndReturnTrueValue() {
        Student john = new Student("John");

        boolean added = students.add(john);

        assertEquals(1, students.size());
        assertTrue(added);
    }


    @Test
    void addTwoStudentToListThenSizeMustBeTwo() {
        Student john = new Student("John");
        Student peter = new Student("Peter");

        students.add(john);
        students.add(peter);

        assertEquals(2, students.size());
    }

    @Test
    void addStudentToListThenGetStudentByIndex() {
        Student john = new Student("John");

        students.add(john);
        Student result = students.get(0);

        assertEquals(john, result);
    }

    @Test
    void addTwoStudentsToListThenGetSecondStudentByIndex() {
        Student john = new Student("John");
        Student bill = new Student("Bill");

        students.add(john);
        students.add(bill);
        Student result = students.get(1);

        assertEquals(bill, result);
    }

    @Test
    void addStudentToListAndAfterRemoveStudentByIndexThenSizeMustBeZero() {
        Student john = new Student("John");

        students.add(john);
        students.remove(0);

        assertEquals(0, students.size());
    }

    @Test
    void addStudentToListAndRemoveStudentByIndexAndTryGetStudentByIndexThenThrowArrayIndexOfBoundsException() {
        Student john = new Student("John");

        students.add(john);
        students.remove(0);
        RuntimeException exception = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> students.get(0));

        assertEquals(ArrayIndexOutOfBoundsException.class, exception.getClass());
    }

    @Test
    void tryGetStudentFromListByIndexMinusOneMustThrowArrayIndexOfBoundsException() {
        RuntimeException exception = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> students.get(-1));

        assertEquals(ArrayIndexOutOfBoundsException.class, exception.getClass());
    }

    @Test
    void tryGetStudentFromListByIndexHigherThanListSizeThenThrowArrayIndexOfBoundsException() {
        Student first = new Student("John");
        students.add(first);

        RuntimeException exception = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> students.get(1));

        assertEquals(ArrayIndexOutOfBoundsException.class, exception.getClass());
    }

    @Test
    void whenArrayCapacityOneAddSecondStudentToListThenCapacityAutoIncrease() {
        CustomList<Student> list = new CustomArrayList<>(1);
        Student john = new Student("John");
        Student bill = new Student("Bill");

        list.add(john);
        list.add(bill);

        assertEquals(2, list.size());
    }

}