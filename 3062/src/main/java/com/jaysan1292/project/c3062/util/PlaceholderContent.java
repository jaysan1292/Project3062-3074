package com.jaysan1292.project.c3062.util;

import com.jaysan1292.jdcommon.Extensions;
import com.jaysan1292.jdcommon.Range;
import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.data.beans.UserBean;
import com.jaysan1292.project.c3062.db.CommentDbManager;
import com.jaysan1292.project.c3062.db.PostDbManager;
import com.jaysan1292.project.c3062.db.ProgramDbManager;
import com.jaysan1292.project.c3062.db.UserDbManager;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Date: 12/11/12
 * Time: 11:31 PM
 *
 * @author Jason Recillo
 */
@SuppressWarnings("ObjectAllocationInLoop")
public class PlaceholderContent {
    private final static long maxTime = (long) (86400000 * 1.5);
    private final static int numUsers = (Integer) new Range<Integer>(700, 1200).getRandomValue();
    private final static int numPosts = (Integer) new Range<Integer>(7000, 9000).getRandomValue();
    private final static int maxComments = 5;

    private PlaceholderContent() {}

    static {
        StopWatch watch = new StopWatch(); watch.start();
        StopWatch watchTotal = new StopWatch(); watchTotal.start();

        WebAppCommon.log.info("Generating placeholder content (may take a while)...");
        try {
            UserDbManager u = UserDbManager.getSharedInstance();
            ProgramDbManager p = ProgramDbManager.getSharedInstance();
            PostDbManager po = PostDbManager.getSharedInstance();
            CommentDbManager c = CommentDbManager.getSharedInstance();

            Program[] programs;
            User[] users;
            Post[] posts;
            Comment[] comments;

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.trace("Populating program table...");
            ArrayList<Program> pro = Program.readJSONArray(Program.class, PlaceholderContent.class.getClassLoader().getResourceAsStream("programs.json"));
            for (Program program : pro) {
                p.insert(program);
            }
            programs = p.getAll();
            watch.stop();
            WebAppCommon.log.trace(String.format("Populating program table took %s.", watch.toString()));
            watch.reset();
            watch.start();

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.trace("Generating users.");
            UserBean ub = new UserBean(new User("Jason",
                                                "Recillo",
                                                "jaysan1292@example.com",
                                                "100726948",
                                                p.getProgram("T127")));
            ub.setPassword(ub.getStudentNumber());
            u.insert(ub);

            for (int i = 1; i < numUsers; i++) {
                while (true) {
                    try {
                        String studentId = RandomStringUtils.randomNumeric(9);
                        UserBean user = new UserBean(new User("User",
                                                              "Number " + i,
                                                              "n" + i + "@example.com",
                                                              studentId,
                                                              Extensions.getRandom(programs)));
                        user.setPassword(user.getStudentNumber());
                        u.insert(user);
                        break;
                    } catch (SQLException e) {
                        WebAppCommon.log.error(e.getMessage());
                    }
                }
            }
            users = u.getAll();
            watch.stop();
            WebAppCommon.log.trace(String.format("Generating %d users took %s.", u.getCount(), watch.toString()));
            watch.reset();
            watch.start();

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.trace("Generating posts.");
            for (int i = 0; i < numPosts; i++) {
                Post post = new Post(new Date(((long) (System.currentTimeMillis() - (Math.random() * maxTime)))),
                                     Extensions.getRandom(users),
                                     PlaceholderGenerator.generateRandomContent(2, (Math.random() > 0.05) ?
                                                                                   PlaceholderGenerator.ContentType.Sentence :
                                                                                   PlaceholderGenerator.ContentType.Paragraph));
                po.insert(post);
            }
            posts = po.getAll();
            watch.suspend();
            WebAppCommon.log.trace(String.format("Generating %d posts took %s.", po.getCount(), watch.toString()));
            watch.reset();
            watch.start();

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.trace("Generating post comments.");
            for (Post post : posts) {
                int iterations = Extensions.randomInt(0, maxComments);
                for (int i = 0; i < iterations; i++) {
                    Comment comment = new Comment(Extensions.getRandom(users),
                                                  PlaceholderGenerator.generateRandomContent(5, PlaceholderGenerator.ContentType.Sentence),
                                                  new Date(NumberUtils.toLong(
                                                          new Range<Long>(post.getPostDate().getTime(),
                                                                          new Date().getTime()).getRandomValue().toString())),
                                                  post.getId());
                    c.insert(comment);
                }
            }
            comments = c.getAll();
            watch.stop();
            WebAppCommon.log.trace(String.format("Generating %d post comments took %s.", c.getCount(), watch.toString()));

            ///////////////////////////////////////////////////////////////////

            try {
                watch.stop();
            } catch (IllegalStateException ignored) {}
            watchTotal.stop();
            WebAppCommon.log.info(String.format("Placeholder content generation (%d posts, %d users, %d comments) took %s.",
                                                posts.length,
                                                users.length,
                                                comments.length,
                                                watchTotal.toString()));
        } catch (Exception e) {
            WebAppCommon.log.error("There was an error generating placeholder content.. ABANDON SHIP", e);
            System.exit(-1);
        }
    }
}
