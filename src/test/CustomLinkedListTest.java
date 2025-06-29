package test;

import model.Student;
import org.junit.jupiter.api.*;
import structures.CustomList;
import structures.impl.CustomArrayList;
import structures.impl.CustomLinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {

    static CustomList<Student> students;

    @BeforeAll
    static void init() {
        students = new CustomLinkedList<>();
    }

    @AfterEach
    void tearDown() {
        students.clear();
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
    void testForBigDataInputWhenAddedElementsThenListSizeIsCorrect() {
        int inputDataSize = 50;
        CustomList<Integer> numbers = new CustomLinkedList<>();

        for (int i = 0; i < inputDataSize; i++) {
            numbers.add(i);
        }

        assertEquals(inputDataSize, numbers.size());
    }

    @Test
    void whenGettingElementFromLowerHalfListThenFindFromTailAndGetCorrectElement() {
        int listSize = 5;
        int expectedValue = 4;
        CustomList<Integer> numbers = new CustomLinkedList<>();
        for (int i = 0; i < listSize; i++) {
            numbers.add(i);
        }

        Integer result = numbers.get(expectedValue);

        assertEquals(expectedValue, result);
    }

    @Test
    void testForBigDataWhenRandomDeletedElementsThenListSizeIsCorrect() {
        int inputDataSize = 50;
        int numberOfChecks = 50;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Integer> libraryList = new ArrayList<>();
        CustomList<Integer> customList = new CustomLinkedList<>();
        for (int i = 0; i < inputDataSize; i++) {
            customList.add(i);
            libraryList.add(i);
        }

        for (int i = 0; i < numberOfChecks; i++) {
            int randomNumber = random.nextInt(inputDataSize);
            try {
                customList.remove(randomNumber);
                libraryList.remove(randomNumber);
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }

        assertEquals(libraryList.size(), customList.size());
    }

    @Test
    void whenAddedNullValueThenThrowIllegalArgumentException() {
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
                () -> students.add(null));

        assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Test
    void addStudentToListAndRemoveStudentByIndexAndTryGetStudentByIndexThenThrowArrayIndexOutOfBoundsException() {
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