package model;

import java.util.Objects;

public class Student {
    private static int count = 0;
    private final int id;
    private final String name;

    public Student(String name) {
        id = ++count;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Student.class) {
            return false;
        } else {
            Student other = (Student) obj;
            return id == other.getId();
        }
    }

    @Override
    public String toString() {
        return String.format("%s {id:%d, name:%s}", getClass().getSimpleName(), id, name);
    }
}
