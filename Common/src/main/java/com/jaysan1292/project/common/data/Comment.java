package com.jaysan1292.project.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jaysan1292.project.common.util.DateUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

public class Comment extends BaseEntity<Comment> implements Comparable<Comment> {
    private long commentId;
    private User commentAuthor;
    private String commentBody;
    private Date commentDate;
    private long parentPostId;

    public Comment() {
        this(-1, new User(), "(null)", new Date(), -1);
    }

    public Comment(long id, User author, String body, Date date, long parentPostId) {
        this.commentId = id;
        this.commentAuthor = author;
        this.commentBody = body;
        this.commentDate = date;
        this.parentPostId = parentPostId;
    }

    public Comment(Comment other) {
        this(other.commentId, other.commentAuthor, other.commentBody, other.commentDate, other.parentPostId);
    }

    //region Getters and Setters

    public long getId() {
        return commentId;
    }

    public User getCommentAuthor() {
        return commentAuthor;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public long getParentPostId() {
        return parentPostId;
    }

    public void setId(long id) {
        this.commentId = id;
    }

    public void setCommentAuthor(User author) {
        this.commentAuthor = author;
    }

    public void setCommentBody(String body) {
        this.commentBody = body;
    }

    public void setCommentDate(Date date) {
        this.commentDate = date;
    }

    public void setParentPostId(long parentPostId) {
        this.parentPostId = parentPostId;
    }

    @JsonIgnore
    public String getRelativeCommentDateString() {
        return DateUtils.getRelativeDateString(commentDate);
    }

    @JsonIgnore
    public long getDateMillis() {
        return commentDate.getTime();
    }

    //endregion

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Comment)) return false;
        Comment other = (Comment) obj;
        return (commentId == other.commentId) && commentAuthor.equals(other.commentAuthor) && commentBody.equals(other.commentBody) && commentDate.equals(other.commentDate);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(97, 13)
                .append(commentId)
                .append(commentAuthor)
                .append(commentBody)
                .append(commentDate)
                .toHashCode();
    }

    @Override
    public int compareTo(Comment o) {
        return this.commentDate.compareTo(o.commentDate);
    }
}
