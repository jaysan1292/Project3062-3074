package com.jaysan1292.project.c3062.data.beans;

import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;

import java.util.List;

/** @author Jason Recillo */
public class ProfileBean {
    private User user;
    private SortedArrayList<PostBean> userPosts;
    private User[] classmates;

    public User getUser() {
        return user;
    }

    public List<PostBean> getPosts() {
        return userPosts;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPosts(List<PostBean> userPosts) {
        this.userPosts = new SortedArrayList<PostBean>(userPosts);
    }

    public User[] getClassmates() {
        return classmates;
    }

    public void setClassmates(User[] classmates) {
        this.classmates = classmates;
    }
}
