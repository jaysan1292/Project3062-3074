package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.util.PlaceholderContent;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/** @author Jason Recillo */
public class PostDbManager {
    private static PostDbManager sharedInstance;

    public static synchronized PostDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new PostDbManager();
        return sharedInstance;
    }

    private PostDbManager() {}

    public synchronized Post getPost(long id) {
        return PlaceholderContent.getPost(id);
    }

    @SuppressWarnings("unchecked")
    public synchronized SortedArrayList<Post> getPosts(final User user) {
        return new SortedArrayList<Post>() {{
            addAll(CollectionUtils.select(PlaceholderContent.Posts, new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    return ((Post) object).getPostAuthor().equals(user);
                }
            }));
        }};
    }
}
