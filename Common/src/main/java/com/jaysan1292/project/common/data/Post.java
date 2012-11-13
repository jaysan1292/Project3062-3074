package com.jaysan1292.project.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaysan1292.project.common.util.DateUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

public class Post extends BaseEntity<Post> implements Comparable<Post> {
    private long postId;
    private Date postDate;
    private User postAuthor;
    private String postContent;

    public Post() {
        postId = -1;
        postDate = new Date();
        postAuthor = new User();
        postContent = "(null)";
    }

    public Post(long id) {
        this();
        postId = id;
    }

    public Post(long pid, Date pdate, User pauthor, String pcontent) {
        this.postId = pid;
        this.postDate = pdate;
        this.postAuthor = pauthor;
        this.postContent = pcontent;
    }

    public Post(Date pdate, User pauthor, String pcontent) {
        this(-1, pdate, pauthor, pcontent);
    }

    public Post(Post other) {
        this(other.postId, other.postDate, other.postAuthor, other.postContent);
    }

    //region Getters and Setters

    public long getId() {
        return postId;
    }

    public Date getPostDate() {
        return postDate;
    }

    @JsonIgnore
    public String getRelativePostDateString() {
        return DateUtils.getRelativeDateString(postDate);
    }

    public void setPostDate(Date date) {
        this.postDate = date;
    }

    public User getPostAuthor() {
        return postAuthor;
    }

    public void setId(long id) {
        this.postId = id;
    }

    public void setPostAuthor(User author) {
        this.postAuthor = author;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String content) {
        this.postContent = content;
    }

    //endregion

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Post)) return false;
        Post other = (Post) obj;
        return (postId == other.postId) &&
               postDate.equals(other.postDate) &&
               postAuthor.equals(other.postAuthor) &&
               postContent.equals(other.postContent);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 23)
                .append(postId)
                .append(postDate)
                .append(postAuthor)
                .append(postContent)
                .toHashCode();
    }

    @Override
    public int compareTo(Post o) {
        return o.postDate.compareTo(this.postDate);
    }
}
