package com.jaysan1292.project.common.test;

import com.jaysan1292.project.c3062.util.PlaceholderContent;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("ALL")
public class Application {
    private Application() {}

    //Note: the purpose of this method is only to create the placeholder data used by the mobile app for gbc.myCommunity
    public static void main(String[] args) throws Exception {
        Set<Post> posts = PlaceholderContent.Posts;
        Set<User> users = PlaceholderContent.Users;
        Set<Comment> comments = PlaceholderContent.Comments;

        String postFilepath = "Project3074/res/raw/posts.json";
        String userFilepath = "Project3074/res/raw/users.json";
        String commentFilepath = "Project3074/res/raw/comments.json";

        posts = new HashSet<Post>(new ArrayList<Post>(posts).subList(0, 25));

        for (Post p : posts) {
            p.setPostContent(p.getPostContent().replace("<br/>", "\n").replace("</p><p>", "\n\n").replace("<p>", "").replace("</p>", ""));
        }

        final Set<Long> postIds = new HashSet<Long>();
        CollectionUtils.forAllDo(posts, new Closure() {
            public void execute(Object input) {
                postIds.add(((Post) input).getId());
            }
        });
        CollectionUtils.filter(comments, new Predicate() {
            public boolean evaluate(Object object) {
                final Comment comment = (Comment) object;
                return CollectionUtils.exists(postIds, new Predicate() {
                    public boolean evaluate(Object object) {
                        long postId = (Long) object;
                        return comment.getParentPostId() == postId;
                    }
                });
            }
        });

        Post.writeJSONArray(posts, new BufferedWriter(new FileWriter(postFilepath)));
        User.writeJSONArray(users, new BufferedWriter(new FileWriter(userFilepath)));
        Comment.writeJSONArray(comments, new BufferedWriter(new FileWriter(commentFilepath)));
    }
}
