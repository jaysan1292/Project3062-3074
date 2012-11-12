package com.jaysan1292.project.common.data.beans;

import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;

import java.util.List;

/** @author Jason Recillo */
public class FeedBean {
    public static final int POSTS_PER_PAGE = 20;

    private SortedArrayList<Post> posts;
    private User user;
    private int offset;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = new SortedArrayList<Post>(posts);
    }

    public List<Post> getPostsWithOffset() {
        try {
            return new SortedArrayList<Post>(posts.subList(offset, offset + POSTS_PER_PAGE));
        } catch (IndexOutOfBoundsException e) {
            try {
                return new SortedArrayList<Post>(posts.subList(offset, getPostCount()));
            } catch (IllegalArgumentException e1) {
                return new SortedArrayList<Post>();
            }
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        if (offset > 0) {
            this.offset = offset;
        } else {
            offset = 0;
        }
    }

    public int getPostCount() {
        return posts.size();
    }

    public int getPostsPerPage() {
        return POSTS_PER_PAGE;
    }
}
