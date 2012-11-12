package com.jaysan1292.project.common.data;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;

public class Timetable extends BaseEntity<Timetable> {
    private long timetableId;
    private User student;
    private ArrayList<Course> courses;

    public Timetable() {
        this(-1, new User(), new ArrayList<Course>());
    }

    public Timetable(long timetableId, User student, ArrayList<Course> courses) {
        this.timetableId = timetableId;
        this.student = student;
        this.courses = courses;
    }

    public Timetable(Timetable other) {
        this(other.timetableId, other.student, other.courses);
    }

    //region Getters and Setters

    public long getId() {
        return timetableId;
    }

    public User getStudent() {
        return student;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setId(long id) {
        this.timetableId = id;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    //endregion

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Timetable)) return false;
        Timetable other = (Timetable) obj;
        return student.equals(other.student) &&
               courses.equals(other.courses);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(57, 23)
                .append(timetableId)
                .append(student)
                .append(courses)
                .toHashCode();
    }
}
