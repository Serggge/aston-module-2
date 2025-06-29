package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import structures.CustomMap;
import structures.impl.CustomHashMap;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.*;

class CustomHashMapTest {

    static CustomMap<Integer, String> hashMap;

    @BeforeAll
    static void beforeAll() {
        hashMap = new CustomHashMap<>();
    }

    @AfterEach
    void afterEach() {
        hashMap.clear();
    }

    @Test
    void whenPutEntryThenMapSizeIsOne() {
        int key = 1;
        String value = "One";

        hashMap.put(key, value);

        assertEquals(1, hashMap.size());
    }

    @Test
    void whenPutEntryAndGettingValueByKeyThenResultIsNotEmptyAndReturnValueByKey() {
        int key = 1;
        String value = "One";

        hashMap.put(key, value);
        Optional<String> result = hashMap.get(key);

        assertTrue(result.isPresent());
        assertEquals(value, result.orElse("different string"));
    }

    @Test
    void whenPutTwoEntriesThenMapSizeIsTwoAndBothEntryAdded() {
        int firstKey = 1;
        int secondKey = 2;
        String firstValue = "first value";
        String secondValue = "second value";

        hashMap.put(firstKey, firstValue);
        hashMap.put(secondKey, secondValue);

        assertEquals(2, hashMap.size());
        assertEquals(Set.of(firstValue, secondValue), hashMap.values());
    }

    @Test
    void whenPutTwoEntriesThenSecondEntryCanBeGettingByKey() {
        int firstKey = 1;
        int secondKey = 2;
        String firstValue = "first value";
        String secondValue = "second value";

        hashMap.put(firstKey, firstValue);
        hashMap.put(secondKey, secondValue);

        assertEquals(secondValue, hashMap.get(secondKey)
                                         .orElse("different string"));
    }

    @Test
    void whenPutEntryWithSomeKeyButDifferentValueThenValueUpdatedAndMapSizeIsOne() {
        int firstKey = 1;
        String oldValue = "old value";
        String newValue = "new value";

        hashMap.put(firstKey, oldValue);
        hashMap.put(firstKey, newValue);

        assertEquals(newValue, hashMap.get(firstKey)
                                      .orElse("different string"));
        assertEquals(1, hashMap.size());
    }

    @Test
    void whenPutEntryWithSomeKeyButDifferentValueThenMethodPutReturnOldValue() {
        int key = 1;
        String oldValue = "old value";
        String newValue = "new value";

        hashMap.put(key, oldValue);
        Optional<String> returnedValue = hashMap.put(key, newValue);

        assertEquals(oldValue, returnedValue.orElse("different string"));
    }

    @Test
    void whenRemoveEntryByKeyThenMapSizeReducedByOne() {
        int key = 1;
        String value = "value";

        hashMap.put(key, value);
        hashMap.remove(key);

        assertEquals(0, hashMap.size());
    }

    @Test
    void whenRemoveEntryByKeyThenMethodReturnOldValue() {
        int key = 1;
        String value = "value";

        hashMap.put(key, value);
        String actualValue = hashMap.remove(key)
                                    .orElse("different string");

        assertEquals(value, actualValue);
    }

    @Test
    void whenPutEntryAndCheckContainsByKeyThenResultMustBeTrue() {
        int key = 1;
        String value = "value";

        hashMap.put(key, value);

        assertTrue(hashMap.contains(key));
    }

    @Test
    void whenPutEntryAndCheckContainsByKeyWhichNotExistsThenMustReturnFalseValue() {
        int key = 1;
        int wrongKey = 2;
        String value = "value";

        hashMap.put(key, value);

        assertFalse(hashMap.contains(wrongKey));
    }

    @Test
    void whenPutEntryAndRemoveItByKeyThenMapSizeMustBeZero() {
        int key = 1;
        String value = "value";

        hashMap.put(key, value);
        hashMap.remove(key);

        assertEquals(0, hashMap.size());
    }

    @Test
    void whenPutEntryAndRemoveItByKeyThenMethodReturnOldValue() {
        int key = 1;
        String value = "value";

        hashMap.put(key, value);
        String oldValue = hashMap.remove(key).orElse("different string");

        assertEquals(value, oldValue);
    }

    @Test
    void whenPutEntryAndRemoveItByKeyThenGettingValueByKeyMustBeEmptyValue() {
        int key = 1;
        String value = "value";

        hashMap.put(key, value);
        hashMap.remove(key);
        Optional<String> emptyValue = hashMap.get(key);

        assertTrue(emptyValue.isEmpty());
    }

    @Test
    void testForLargeInputDataCheckMapSize() {
        int dataSize = 100;
        for (int i = 0; i < dataSize; i++) {
            hashMap.put(i, String.valueOf(i));
        }
        assertEquals(dataSize, hashMap.size());
    }

    @Test
    void testForLargeInputDataCheckGettingValueByKey() {
        int dataSize = 100;
        int numberOfChecks = 1000;
        boolean wasError = false;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < dataSize; i++) {
            hashMap.put(i, String.valueOf(i));
        }

        for (int i = 0; i < numberOfChecks; i++) {
            int randomKey = random.nextInt(dataSize);
            String expectedValue = String.valueOf(randomKey);
            String actualValue = hashMap.get(randomKey)
                                          .orElse("different string");
            if (!actualValue.equals(expectedValue)) {
                wasError = true;
            }
        }

        assertFalse(wasError);
    }

    @Test
    void testForLargeInputDataCheckAllEntriesExist() {
        int dataSize = 100;
        Set<String> expectedValues = new HashSet<>();
        for (int i = 0; i < dataSize; i++) {
            String generatedValue = String.valueOf(i);
            expectedValues.add(generatedValue);
            hashMap.put(i, generatedValue);
        }

        Set<String> actualValues = hashMap.values();

        assertEquals(expectedValues.size(), actualValues.size());
        assertEquals(expectedValues, actualValues);
    }

    @Test
    void testForLargeInputDataCheckAllEntriesRemoved() {
        int dataSize = 100;
        int numberOfChecks = 200;
        boolean wasError = false;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Set<Integer> keys = new HashSet<>();
        for (int i = 0; i < dataSize; i++) {
            keys.add(i);
            hashMap.put(i, String.valueOf(i));
        }

        for (int i = 0; i < numberOfChecks; i++) {
            int randomKey = random.nextInt(dataSize);
            hashMap.remove(randomKey);
            keys.remove(randomKey);
        }
        for (Integer key : keys) {
            if (!hashMap.contains(key)) {
                wasError = true;
            }
        }

        assertEquals(keys.size(), hashMap.size());
        assertFalse(wasError);
    }

    @Test
    void whenPutEntryWithNullKeyThenThrowNullPointException() {
        String errorMessage = "Key can't be a null value";
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
                () -> hashMap.put(null, ""));

        assertEquals(IllegalArgumentException.class, exception.getClass());
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void whenGettingEntryByNullKeyThenThrowNullPointException() {
        String errorMessage = "Key can't be a null value";
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
                () -> hashMap.get(null));

        assertEquals(IllegalArgumentException.class, exception.getClass());
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void whenRemoveEntryWithNullKeyThenThrowNullPointException() {
        String errorMessage = "Key can't be a null value";
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
                () -> hashMap.remove(null));

        assertEquals(IllegalArgumentException.class, exception.getClass());
        assertEquals(errorMessage, exception.getMessage());
    }

}