package com.jaysan1292.project.c3074.db;

import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
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
        CollectionUtils.forAllDo(comments, new Closure() {
            public void execute(Object input) {
                Comment comment = (Comment) input;
                Post parent = PostProvider.getInstance().getPost(comment.getParentPostId());
                comment.setCommentDate(new Date((long) (parent.getPostDate().getTime() - (Math.random() * (long) (86400000 * 1.5)))));
            }
        });

        watch.stop();
        MobileAppCommon.log.info(String.format("Done getting comments! (%s)", watch.toString()));
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Comment> getComments(final long postId) {
        return new ArrayList<Comment>(CollectionUtils.select(comments, new Predicate() {
            public boolean evaluate(Object object) {
                return ((Comment) object).getParentPostId() == postId;
            }
        }));
    }
}
