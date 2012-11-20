package com.jaysan1292.project.c3062.db;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jaysan1292.jdcommon.Extensions;
import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.data.beans.PostBean;
import com.jaysan1292.project.c3062.util.PlaceholderGenerator;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.dbutils.DbUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 12/11/12
 * Time: 7:58 PM
 *
 * @author Jason Recillo
 */
public class DbManagerTest {

    private static ProgramDbManager programManager;
    private static PostDbManager postManager;
    private static UserDbManager userManager;
    private static CommentDbManager commentManager;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        WebAppCommon.log.info("------------------------------------------------------------");
        try {
            WebAppCommon.initializeApplication();

            programManager = ProgramDbManager.getSharedInstance();
            postManager = PostDbManager.getSharedInstance();
            userManager = UserDbManager.getSharedInstance();
            commentManager = CommentDbManager.getSharedInstance();
            WebAppCommon.log.info("------------------------------------------------------------");
        } catch (Exception e) {
            WebAppCommon.log.error("There was an error somewhere D:", e);
            tearDownOnce();
            System.exit(-1);
        }
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        WebAppCommon.log.info("------------------------------------------------------------");

        WebAppCommon.shutdownApplication();

        WebAppCommon.log.info("------------------------------------------------------------");
        WebAppCommon.log.info("Test complete!");
        WebAppCommon.log.info("------------------------------------------------------------");
    }

    //region ProgramDbManager

    @Test
    public void testGetProgramById() throws Exception {
        WebAppCommon.log.info("Test: Get program by database ID");
        Program p = programManager.get(0);
        assertEquals("A101", p.getProgramCode());
    }

    @Test
    public void testGetProgramByCode() throws Exception {
        WebAppCommon.log.info("Test: Get program by program code");
        Program p2 = programManager.getProgram("T127");
        assertEquals("T127", p2.getProgramCode());
    }

    @Test
    public void testGetAllPrograms() throws Exception {
        WebAppCommon.log.info("Test: Get all programs");
        ArrayList<Program> programs = new ArrayList<Program>(Program.AllPrograms.values());
        ArrayList<Program> testPrograms = new ArrayList<Program>(Arrays.asList(programManager.getAll()));

        Collections.sort(programs);
        Collections.sort(testPrograms);

        assertEquals(programs.hashCode(), testPrograms.hashCode());
    }

    @Test
    public void testGetProgramCount() throws Exception {
        WebAppCommon.log.info("Test: Get number of programs");

        int count = programManager.getCount();
        assertEquals(140, count);
    }

    //endregion

    //region UserDbManager

    @Test
    public void testGetUserById() throws Exception {
        WebAppCommon.log.info("Test: Get user by database ID");

        User user = userManager.get(0);
        assertEquals("Jason Recillo", user.getFullName());
        assertNotNull(user.getPassword());
    }

    @Test
    public void testGetUserByStudentNumber() throws Exception {
        WebAppCommon.log.info("Test: Get user by student number");

        User user = userManager.getUser("100726948");
        assertEquals("Jason Recillo", user.getFullName());
        assertNotNull(user.getPassword());
    }

    @Test
    public void testGetUsersByProgram() throws Exception {
        WebAppCommon.log.info("Test: Get users by program");

        final Program program = programManager.getProgram("T127");
        ArrayList<User> users = new ArrayList<User>(Arrays.asList(userManager.getUsers(program)));
        ArrayList<User> testUsers = new ArrayList<User>(Arrays.asList(userManager.getAll()));

        Iterables.removeIf(testUsers, new Predicate<User>() {
            public boolean apply(User input) {
                return !input.getProgram().equals(program);
            }
        });

        assertEquals(users, testUsers);
    }

    @Test
    public void testCreateUser() throws Exception {
        WebAppCommon.log.info("Test: Create new user");

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail("jsmith@example.ca");
        user.setStudentNumber("100777777");
        user.setProgram(programManager.getProgram("T127"));
        user.setPassword(user.getStudentNumber());

        userManager.insert(user);

        User user2 = userManager.getUser("100777777");
        user.setId(user2.getId());
        assertEquals(user, user2);
    }

    @Test
    public void testUpdateUser() throws Exception {
        WebAppCommon.log.info("Test: Update existing user");

        User user = userManager.getUser("100726948");
        assertEquals("jaysan1292@example.com", user.getEmail());

        user.setEmail("jaysan1292@gmail.com");
        userManager.update(user);

        User user2 = userManager.getUser("100726948");
        assertEquals("jaysan1292@gmail.com", user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
    }

    @Test
    public void testGetClassmates() throws Exception {
        WebAppCommon.log.info("Test: Get classmates");

        User user = userManager.getUser("100726948");

        User[] classmates = userManager.getClassmates(user);

        assertTrue(classmates.length <= 16);

        boolean valid = true;
        for (User classmate : classmates) {
            valid &= classmate.getProgram().equals(user.getProgram());
        }

        assertTrue(valid);
    }

    @Test
    public void testCheckUniqueStudentNumber() throws Exception {
        WebAppCommon.log.info("Test: Check unique student number");

        boolean shouldBeFalse = userManager.isStudentNumberUnique("100726948");
        assertFalse(shouldBeFalse);

        boolean shouldBeTrue = userManager.isStudentNumberUnique("000000000");
        assertTrue(shouldBeTrue);
    }

    @Test
    public void testCheckUniqueEmail() throws Exception {
        WebAppCommon.log.info("Test: Check unique email address");

        boolean shouldBeFalse = userManager.isEmailUnique("n1@example.com");
        assertFalse(shouldBeFalse);

        boolean shouldBeTrue = userManager.isEmailUnique("asdfghjkl@example.com");
        assertTrue(shouldBeTrue);
    }

    @Test(expected = SQLException.class)
    public void testCreateUserThatAlreadyExists() throws Exception {
        WebAppCommon.log.info("Test: Create user with duplicate student number");

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail("jsmith@example.ca");
        user.setStudentNumber("100726948"); // This student number should already exist in database
        user.setProgram(programManager.getProgram("T127"));
        user.setPassword(user.getStudentNumber());

        // This should fail because of the duplicate student number
        userManager.insert(user);
    }

    @Test(expected = SQLException.class)
    public void testGetNonExistingUser() throws Exception {
        WebAppCommon.log.info("Test: Get non-existing user");

        // This should fail because it shouldn't exist in the database; (there
        // is an extremely small chance it could because the student numbers
        // are randomly generated
        userManager.getUser("000000000");
    }

    //endregion

    //region CommentDbManager

    @Test
    public void testGetCommentsForPost() throws Exception {
        Post post = postManager.get(0);
        Comment[] comments;
        comments = commentManager.getComments(post);

        boolean valid = true;
        for (Comment comment : comments) {
            valid &= post.getId() == comment.getParentPostId();
        }
        assertTrue(valid);
    }

    @Test
    public void testCreateComment() throws Exception {
        long pid = 0;
        Post post = postManager.get(pid);
        User user = userManager.get(0);

        Comment comment = new Comment();
        comment.setCommentAuthor(user);
        comment.setCommentDate(new Date());
        comment.setCommentBody("Trolololololololol");
        comment.setParentPostId(post.getId());

        commentManager.insert(comment);

        Connection conn = null;
        try {
            conn = BaseDbManager.openDatabaseConnection();
            String query = "SELECT * FROM comment_t WHERE comment_date=?";
            Comment comment2 = BaseDbManager.RUN.query(conn,
                                                       query,
                                                       CommentDbManager.getSharedInstance().getResultSetHandler(),
                                                       comment.getCommentDate().getTime());

            comment.setId(comment2.getId());

            assertEquals(comment, comment2);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    @Test
    public void testEditComment() throws Exception {
        long pid = 0;
        Post post = postManager.get(pid);
        User user = userManager.get(0);

        Comment comment = new Comment();
        comment.setCommentAuthor(user);
        comment.setCommentDate(new Date());
        comment.setCommentBody("Trololololol");
        comment.setParentPostId(post.getId());

        commentManager.insert(comment);

        Connection conn = null;
        try {
            conn = BaseDbManager.openDatabaseConnection();
            String query = "SELECT * FROM comment_t WHERE comment_date=?";
            Comment comment2 = BaseDbManager.RUN.query(conn,
                                                       query,
                                                       CommentDbManager.getSharedInstance().getResultSetHandler(),
                                                       comment.getCommentDate().getTime());

            comment.setId(comment2.getId());

            assertEquals(comment, comment2);

            comment2.setCommentBody("Edited comment");
            commentManager.update(comment2);

            query = "SELECT * FROM comment_t WHERE comment_date=?";
            Comment comment3 = BaseDbManager.RUN.query(conn,
                                                       query,
                                                       CommentDbManager.getSharedInstance().getResultSetHandler(),
                                                       comment.getCommentDate().getTime());

            assertEquals(comment2, comment3);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    //endregion

    //region PostDbManager

    @Test
    public void testGetPostsForUser() throws Exception {
        WebAppCommon.log.info("Test: Get posts for a specific user");

        User user = userManager.getUser("100726948");
        Post post = new Post();
        post.setPostAuthor(user);
        post.setPostDate(new Date());
        post.setPostContent("Testing testing testing...");

        postManager.insert(post);

        SortedArrayList<PostBean> posts = new SortedArrayList<PostBean>(postManager.getPostBeans(user));

        assertTrue(posts.size() >= 1);
    }

    @Test
    public void testCreatePost() throws Exception {
        WebAppCommon.log.info("Test: Create a new post");

        Post post = new Post();
        post.setPostAuthor(userManager.getUser("100726948"));
        post.setPostDate(new Date());
        post.setPostContent(PlaceholderGenerator.generateRandomContent(5, PlaceholderGenerator.ContentType.Sentence));

        postManager.insert(post);

        Connection conn = null;
        try {
            conn = BaseDbManager.openDatabaseConnection();
            String query = "SELECT * FROM post_t WHERE post_date=?";
            Post post2 = BaseDbManager.RUN.query(conn,
                                                 query,
                                                 PostDbManager.getSharedInstance().getResultSetHandler(),
                                                 post.getPostDate().getTime());

            post.setId(post2.getId());

            assertEquals(post, post2);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    @Test
    public void testEditPost() throws Exception {
        WebAppCommon.log.info("Test: Edit an existing post");

        Post post = new Post();
        post.setPostAuthor(userManager.get(0));
        post.setPostDate(new Date());
        post.setPostContent("This is a test post.");

        postManager.insert(post);

        Connection conn = null;
        try {
            conn = BaseDbManager.openDatabaseConnection();
            String query = "SELECT * FROM post_t WHERE post_date=?";

            // Get the post that we just inserted
            Post post2 = BaseDbManager.RUN.query(conn,
                                                 query,
                                                 PostDbManager.getSharedInstance().getResultSetHandler(),
                                                 post.getPostDate().getTime());

            // Ensure that the content is the same
            assertEquals(post.getPostContent(), post2.getPostContent());
            post2.setPostContent("This is a test post. EDITED!");

            postManager.update(post2);

            // Get the edited post
            Post post3 = BaseDbManager.RUN.query(conn,
                                                 query,
                                                 PostDbManager.getSharedInstance().getResultSetHandler(),
                                                 post.getPostDate().getTime());

            // Ensure that the edited post has the same ID as the one we
            // just retrieved, and that the content was edited successfully
            assertEquals(post2.getId(), post3.getId());
            assertEquals(post2.getPostContent(), post3.getPostContent());
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    @Test
    public void testGetUserFeedPosts() throws Exception {
        WebAppCommon.log.info("Test: Get user feed posts");

        final User user = userManager.get(0);
        Collection<Post> feedPosts = Arrays.asList(postManager.getUserFeedPosts(user));
        assertTrue(Lists.newArrayList(Iterables.filter(feedPosts, new Predicate<Post>() {
            public boolean apply(Post input) {
                return !user.getProgram().equals(input.getPostAuthor().getProgram());
            }
        })).isEmpty() && !feedPosts.isEmpty());
    }

    @Test
    public void testGetPostCommentCount() throws Exception {
        WebAppCommon.log.info("Test: Get post comment count");

        Post post = Extensions.getRandom(postManager.getAll());
        Comment[] postComments = commentManager.getComments(post);

        int commentCount = postManager.getCommentCount(post);

        assertEquals(postComments.length, commentCount);
    }

    //endregion
}
