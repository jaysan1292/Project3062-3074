package com.jaysan1292.project.c3074.db;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/11/12
 * Time: 11:10 PM
 *
 * @author Jason Recillo
 */
public class CommentProvider {
    private static CommentProvider sharedInstance;
    private ArrayList<Comment> comments;

    public static CommentProvider getInstance() {
        if (sharedInstance == null) sharedInstance = new CommentProvider();
        return sharedInstance;
    }

    private CommentProvider() {
        MobileAppCommon.log.info("Getting all comments...");

        StopWatch watch = new StopWatch(); watch.start();

        try {
            comments = Comment.readJSONArray(Comment.class, MobileAppCommon.getContext().getResources().openRawResource(R.raw.comments));
        } catch (IOException e) {
            MobileAppCommon.log.error(e.getMessage(), e);
            comments = null;
        }

        // Randomize comment dates;
        assert comments != null;
        for (Comment comment : comments) {
            Post parent = PostProvider.getInstance().getPost(comment.getParentPostId());
            comment.setCommentDate(new Date((long) (parent.getPostDate().getTime() - (Math.random() * (long) (86400000 * 1.5)))));
        }

        watch.stop();
        MobileAppCommon.log.info(String.format("Done getting comments! (%s)", watch.toString()));
    }

    public ArrayList<Comment> getComments(final long postId) {
        return Lists.newArrayList(Iterables.filter(comments, new Predicate<Comment>() {
            public boolean apply(Comment input) {
                return input.getParentPostId() == postId;
            }
        }));
    }
}
