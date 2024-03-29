package com.jaysan1292.project.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaysan1292.project.common.util.EncryptionUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User extends BaseEntity<User> implements Comparable<User> {
    public static final User NOT_LOGGED_IN = new User(-5, null, null, null, null, null, null);

    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String studentNumber;
    private Program program;
    private String password;

    public User() {
        this(-1, "(null)", "(null)", "(null)", "(null)", new Program(), "(null)");
    }

    public User(long uid, String fn, String ln, String em, String sid, Program p, String pw) {
        this.userId = uid;
        this.firstName = fn;
        this.lastName = ln;
        this.email = em;
        this.studentNumber = sid;
        this.program = p;
        setPassword(pw);
    }

    public User(String fn, String ln, String em, String sid, Program p, String pw) {
        this(-1, fn, ln, em, sid, p, pw);
    }

    public User(User other) {
        this(other.userId, other.firstName, other.lastName, other.email, other.studentNumber, other.program, "");
        setPasswordDirect(other.password);
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

    @JsonIgnore
    public String getPassword() {
        return this.password;
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
        if (studentNumber.length() != 9) {
            throw new IllegalArgumentException("Student number must be exactly 9 characters.");
        }
        this.studentNumber = studentNumber;
    }

    public void setProgram(Program p) {
        this.program = p;
    }

    @JsonIgnore
    public void setPassword(String plainPassword) {
        if (plainPassword == null) plainPassword = "";
        this.password = EncryptionUtils.encryptPassword(plainPassword);
    }

    @JsonIgnore
    public void setPasswordDirect(String encryptedPassword) {
        if (encryptedPassword.length() != 64) {
            throw new IllegalArgumentException("The given password doesn't appear to be encrypted! Input: " + encryptedPassword);
        }

        this.password = encryptedPassword;
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
               program.equals(other.program) &&
               password.equals(other.password);
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
                .append(password)
                .toHashCode();
    }

    @Override
    public int compareTo(User o) {
        return o.studentNumber.compareTo(this.studentNumber);
    }

    public boolean comparePassword(String plainPassword) {
        return EncryptionUtils.getPasswordEncryptor().checkPassword(plainPassword, this.password);
    }
}
