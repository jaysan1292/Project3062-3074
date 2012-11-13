package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.util.PlaceholderContent;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/** @author Jason Recillo */
public class PostDbManager extends BaseDbManager<Post> {
    private static PostDbManager sharedInstance;
    private static final String TABLE_NAME = "post_t";
    private static final String ID_COLUMN = "post_id";
    private static final String DATE_COLUMN = "post_date";
    private static final String AUTHOR_ID_COLUMN = "post_author_id";
    private static final String CONTENT_COLUMN = "post_content";

    public static synchronized PostDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new PostDbManager();
        return sharedInstance;
    }

    private PostDbManager() {
        super(Post.class);
    }

    protected String tableName() {
        return TABLE_NAME;
    }

    protected String idColumnName() {
        return ID_COLUMN;
    }

    protected Post buildObject(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong(ID_COLUMN));
        post.setPostDate(rs.getDate(DATE_COLUMN));
        post.setPostAuthor(UserManager.getSharedInstance().get(rs.getLong(AUTHOR_ID_COLUMN)));
        post.setPostContent(rs.getString(CONTENT_COLUMN));
        return post;
    }

    protected int doUpdate(Connection conn, Post item) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                       DATE_COLUMN + "=?, " +
                       AUTHOR_ID_COLUMN + "=?, " +
                       CONTENT_COLUMN + "=? " +
                       "WHERE " + ID_COLUMN + "=?";

        return RUN.update(conn, query,
                          item.getPostDate(),
                          item.getPostAuthor().getId(),
                          item.getPostContent());
    }

    protected void doInsert(Connection conn, Post item) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                       DATE_COLUMN + ", " +
                       AUTHOR_ID_COLUMN + ", " +
                       CONTENT_COLUMN + ") VALUES (?, ?, ?)";
        RUN.update(conn, query,
                   item.getPostDate(),
                   item.getPostAuthor().getId(),
                   item.getPostContent());
    }

    ///////////////////////////////////////////////////////////////////////////

    @Deprecated
    public synchronized Post getPost(long id) {
        return PlaceholderContent.getPost(id);
    }

    @Deprecated
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
