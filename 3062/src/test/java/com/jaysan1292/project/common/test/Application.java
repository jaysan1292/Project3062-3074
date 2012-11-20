package com.jaysan1292.project.common.test;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.CommentDbManager;
import com.jaysan1292.project.c3062.db.PostDbManager;
import com.jaysan1292.project.c3062.db.UserDbManager;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

@SuppressWarnings("ALL")
public class Application {
    private Application() {}

    //Note: the purpose of this method is only to create the placeholder data used by the mobile app for gbc.myCommunity
    public static void main(String[] args) throws Exception {
        WebAppCommon.initializeApplication();

        Set<Post> posts = new TreeSet<Post>(Arrays.asList(PostDbManager.getSharedInstance().getAll()));
        Set<User> users = new TreeSet<User>(Arrays.asList(UserDbManager.getSharedInstance().getAll()));
        Set<Comment> comments = new TreeSet<Comment>(Arrays.asList(CommentDbManager.getSharedInstance().getAll()));

        String postFilepath = "Project3074/res/raw/posts.json";
        String userFilepath = "Project3074/res/raw/users.json";
        String commentFilepath = "Project3074/res/raw/comments.json";

        posts = new HashSet<Post>(new ArrayList<Post>(posts).subList(0, 25));

        for (Post p : posts) {
            p.setPostContent(p.getPostContent().replace("<br/>", "\n").replace("</p><p>", "\n\n").replace("<p>", "").replace("</p>", ""));
        }

        final Set<Long> postIds = new HashSet<Long>();
        for (Post post : posts) {
            postIds.add(post.getId());
        }

        Iterables.removeIf(comments, new Predicate<Comment>() {
            public boolean apply(final Comment comment) {
                return Iterables.any(postIds, new Predicate<Long>() {
                    public boolean apply(Long input) {
                        return comment.getParentPostId() == input;
                    }
                });
            }
        });

        Post.writeJSONArray(posts, new BufferedWriter(new FileWriter(postFilepath)));
        User.writeJSONArray(users, new BufferedWriter(new FileWriter(userFilepath)));
        Comment.writeJSONArray(comments, new BufferedWriter(new FileWriter(commentFilepath)));

        WebAppCommon.shutdownApplication();
    }
}
