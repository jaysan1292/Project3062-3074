package com.jaysan1292.project.common.data.beans;

import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.util.SortedArrayList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 13/11/12
 * Time: 6:02 PM
 *
 * @author Jason Recillo
 */
public class PostBean implements Comparable<PostBean> {
    private Post post;
    private SortedArrayList<Comment> comments;

    public Post getPost() {
        return post;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setComments(List<Comment> comments) {
        this.comments = new SortedArrayList<Comment>(comments);
    }

    public int getPostCommentCount() {
        return comments.size();
    }

    public int compareTo(PostBean o) {
        return post.compareTo(o.post);
    }
}
