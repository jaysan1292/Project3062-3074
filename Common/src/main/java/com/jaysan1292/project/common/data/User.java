package com.jaysan1292.project.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User extends BaseEntity<User> implements Comparable<User> {
    public static final User NOT_LOGGED_IN = new User(-5, null, null, null, null, null);

    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String studentNumber;
    private Program program;

    public User() {
        this(-1, "(null)", "(null)", "(null)", "(null)", new Program());
    }

    public User(long uid, String fn, String ln, String em, String sid, Program p) {
        this.userId = uid;
        this.firstName = fn;
        this.lastName = ln;
        this.email = em;
        this.studentNumber = sid;
        this.program = p;
    }

    public User(User other) {
        this(other.userId, other.firstName, other.lastName, other.email, other.studentNumber, other.program);
    }

    //region Getters and Setters

    public long getId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public Program getProgram() {
        return program;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public void setFirstName(String fn) {
        this.firstName = fn;
    }

    public void setLastName(String ln) {
        this.lastName = ln;
    }

    public void setEmail(String em) {
        this.email = em;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setProgram(Program p) {
        this.program = p;
    }

    //endregion Getters and Setters

    @JsonIgnore
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return (userId == other.userId) &&
               firstName.equals(other.firstName) &&
               lastName.equals(other.lastName) &&
               email.equals(other.email) &&
               studentNumber.equals(other.studentNumber) &&
               program.equals(other.program);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 67)
                .append(userId)
                .append(firstName)
                .append(lastName)
                .append(email)
                .append(studentNumber)
                .append(program)
                .toHashCode();
    }

    @Override
    public int compareTo(User o) {
        return o.studentNumber.compareTo(this.studentNumber);
    }
}
