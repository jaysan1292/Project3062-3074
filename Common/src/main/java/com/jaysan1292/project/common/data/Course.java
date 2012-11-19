package com.jaysan1292.project.common.data;

import com.jaysan1292.jdcommon.range.IntegerRange;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.text.WordUtils;

public class Course extends BaseEntity<Course> {
    private long courseId;
    private String courseCode;
    private String courseName;
    private Weekday classDay;
    private String classRoom;
    private ClassType classType;
    private IntegerRange classTime;

    public Course() {
        this(-1, "(null)", "(null)", Weekday.MONDAY, "(null)", ClassType.LECTURE, null);
    }

    public Course(long courseId, String courseCode, String courseName, Weekday classDay, String classRoom, ClassType classType, IntegerRange classTime) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.classDay = classDay;
        this.classRoom = classRoom;
        this.classType = classType;
        this.classTime = classTime;
    }

    public Course(Course other) {
        this(other.courseId, other.courseCode, other.courseName, other.classDay, other.classRoom, other.classType, other.classTime);
    }

    //region Getters and Setters

    public long getId() {
        return courseId;
    }

    public void setId(long id) {
        this.courseId = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Weekday getClassDay() {
        return classDay;
    }

    public void setClassDay(Weekday classDay) {
        this.classDay = classDay;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public IntegerRange getClassTime() {
        return classTime;
    }

    public void setClassTime(IntegerRange classTime) {
        this.classTime = classTime;
    }
    //endregion

    @Override
    public String toString() {
        return String.format("%s: %s | %s on %s, %s in %s", courseCode, courseName, classType, classDay, classTime.toString("%s to %s"), classRoom);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return courseCode.equals(other.courseCode) &&
               courseName.equals(other.courseName) &&
               (classDay == other.classDay) &&
               classRoom.equals(other.classRoom) &&
               (classType == other.classType) &&
               classTime.equals(other.classTime);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 25)
                .append(courseCode)
                .append(courseName)
                .append(classDay)
                .append(classRoom)
                .append(classType)
                .toHashCode();
    }

    public enum ClassType {
        LECTURE, LAB;

        @Override
        public String toString() {
            return WordUtils.capitalizeFully(super.toString());
        }
    }
}
