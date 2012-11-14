package com.jaysan1292.project.c3062.db;

import com.jaysan1292.jdcommon.Extensions;
import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.util.PlaceholderGenerator;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.PostBean;
import com.jaysan1292.project.common.data.beans.UserBean;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.dbutils.DbUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    private static UserManager userManager;
    private static CommentDbManager commentManager;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        WebAppCommon.log.info("----------------------------------------");
        WebAppCommon.log.info("Test setup");
        WebAppCommon.log.info("----------------------------------------");
        try {
            WebAppCommon.initializeApplication();

            programManager = ProgramDbManager.getSharedInstance();
            postManager = PostDbManager.getSharedInstance();
            userManager = UserManager.getSharedInstance();
            commentManager = CommentDbManager.getSharedInstance();
        } catch (Exception e) {
            WebAppCommon.log.error("There was an error somewhere D:", e);
            tearDownOnce();
            System.exit(-1);
        }
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        WebAppCommon.log.info("----------------------------------------");
        WebAppCommon.log.info("Test teardown");
        WebAppCommon.log.info("----------------------------------------");

        WebAppCommon.shutdownApplication();

        WebAppCommon.log.info("----------------------------------------");
        WebAppCommon.log.info("Test complete!");
        WebAppCommon.log.info("----------------------------------------");
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
    }

    @Test
    public void testGetUserByStudentNumber() throws Exception {
        WebAppCommon.log.info("Test: Get user by student number");

        User user = userManager.getUser("100726948");
        assertEquals("Jason Recillo", user.getFullName());
    }

    @Test
    public void testGetUserBeanInstance() throws Exception {
        WebAppCommon.log.info("Test: Get UserBean instance");

        UserBean user = userManager.getUserBean(0);
        assertEquals("Jason Recillo", user.getFullName());
    }

    @Test
    public void testGetUsersByProgram() throws Exception {
        WebAppCommon.log.info("Test: Get users by program");

        Program program = programManager.getProgram("T127");
        ArrayList<User> users = new ArrayList<User>(Arrays.asList(userManager.getUsers(program)));
        ArrayList<User> testUsers = new ArrayList<User>(Arrays.asList(userManager.getUsers(program)));

        assertEquals(users.hashCode(), testUsers.hashCode());
    }

    @Test
    public void testCreateUser() throws Exception {
        WebAppCommon.log.info("Test: Create new user");

        UserBean user = new UserBean();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setEmail("jsmith@example.ca");
        user.setStudentNumber("100777777");
        user.setProgram(programManager.getProgram("T127"));
        user.setPassword(user.getStudentNumber());

        userManager.insert(user);

        User user2 = userManager.getUser("100777777");
        assertEquals(user.getStudentNumber(), user2.getStudentNumber());
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
    }

    @Test(expected = SQLException.class)
    public void testCreateUserThatAlreadyExists() throws Exception {
        WebAppCommon.log.info("Test: Create user with duplicate student number");

        UserBean user = new UserBean();
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
        try {
            comments = commentManager.getComments(post);
        } catch (SQLException e) {
            // Because some posts might have no comments, and these are generated randomly,
            // we may have some bad luck where there might be no comments for this specific
            // post. So, get a new post and try one more time.
            post = Extensions.getRandom(postManager.getAll());
            comments = commentManager.getComments(post);
        }
        assertTrue(comments.length != 0);
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
        comment.setParentPostId(pid);

        commentManager.insert(comment);

        Connection conn = null;
        try {
            conn = BaseDbManager.openDatabaseConnection();
            String query = "SELECT * FROM comment_t WHERE comment_date=?";
            Comment comment2 = BaseDbManager.RUN.query(conn, query, CommentDbManager.getSharedInstance().getResultSetHandler(), comment.getCommentDate().getTime());

            comment.setId(comment2.getId());

            assertEquals(comment, comment2);
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

        SortedArrayList<PostBean> posts = postManager.getPostBeans(user);

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
            Post post2 = BaseDbManager.RUN.query(conn, query, PostDbManager.getSharedInstance().getResultSetHandler(), post.getPostDate().getTime());

            post.setId(post2.getId());

            assertEquals(post, post2);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    //endregion
}
