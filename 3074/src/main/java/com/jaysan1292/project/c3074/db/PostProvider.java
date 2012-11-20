package com.jaysan1292.project.c3074.db;

import android.content.res.Resources;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class PostProvider {
    private static PostProvider sharedInstance;
    private SortedArrayList<Post> posts;

    public static PostProvider getInstance() {
        if (sharedInstance == null) sharedInstance = new PostProvider();
        return sharedInstance;
    }

    private void setArrays() {
        posts = getPosts();
    }

    private PostProvider() {
        setArrays();
    }

    public Post getPost(final long id) {
        return Iterables.find(posts, new Predicate<Post>() {
            public boolean apply(Post input) {
                return input.getId() == id;
            }
        });
    }

    public synchronized SortedArrayList<Post> getPosts() {
        if (posts != null) {
            return posts;
        } else {
            try {
                SortedArrayList<Post> output;
                MobileAppCommon.log.debug("Getting posts...");

                StopWatch watch = new StopWatch();
                watch.start();

//                BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.posts)));

                try {
                    output = new SortedArrayList<Post>(Post.readJSONArray(Post.class, MobileAppCommon.getContext().getResources().openRawResource(R.raw.posts)));
                } catch (IOException e) {
                    MobileAppCommon.log.error(e.getMessage(), e);
                    output = null;
                } catch (Resources.NotFoundException e) {
                    MobileAppCommon.log.error(e.getMessage(), e);
                    output = null;
                }

//                reader.close();

                MobileAppCommon.log.debug("Finished getting posts, now doing some post-processing (:");

                //randomize the post dates
                for (Post post : output) {
                    post.setPostDate(new Date((long) (System.currentTimeMillis() - (Math.random() * (long) (86400000 * 1.5)))));
                }

                Collections.sort(output);

                watch.stop();

                MobileAppCommon.log.debug(String.format("Done, now returning the result. (%s)\n", watch.toString()));
                return output;
            } catch (Exception e) {
                MobileAppCommon.log.error(e.getMessage(), e);
                return null;
            }
        }
    }

    public SortedArrayList<Post> getPosts(User author) {
        if (posts == null) setArrays();
        SortedArrayList<Post> authorPosts = new SortedArrayList<Post>();
        for (Post post : posts) {
            if (post.getPostAuthor().getId() == author.getId()) authorPosts.insertSorted(post);
        }
        return authorPosts;
    }

    public void submitPost(Post post) {
        // ensure the post author is the same as the currently logged in user
        post.setPostAuthor(MobileAppCommon.getLoggedInUser());

        // In the production version, here we would send the post to the web service.
        // here, we're just going to save it to memory; these changes won't persist between
        // application restarts though.
        posts.insertSorted(post);
    }
}
