package com.jaysan1292.project.c3062.util;

import com.jaysan1292.jdcommon.Extensions;
import com.jaysan1292.jdcommon.range.IntegerRange;
import com.jaysan1292.jdcommon.range.LongRange;
import com.jaysan1292.project.c3062.WebAppCommon;
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
    private final static int numUsers = new IntegerRange(500, 1000).getRandomValue();
    private final static int numPosts = new IntegerRange(5000, 7000).getRandomValue();
    private final static int maxComments = 10;
    private final static String progressIndicator = "%.2f%% complete.\r";

    private PlaceholderContent() {}

    private static void reportProgress(int current, int max) {
        System.out.printf(progressIndicator, (current * 100.0) / max);
    }

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

            int progress, max;

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.debug("Populating program table...");
            ArrayList<Program> pro = Program.readJSONArray(Program.class, PlaceholderContent.class.getClassLoader().getResourceAsStream("programs.json"));
            max = pro.size();
            progress = 0;
            for (Program program : pro) {
                p.insert(program);
                reportProgress(progress, max);
                progress++;
            }
            System.out.print("\r");
            programs = p.getAll();
            watch.stop();
            WebAppCommon.log.debug(String.format("Populating program table took %s.", watch.toString()));
            watch.reset();
            watch.start();

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.debug("Generating users.");
            User me = new User("Jason",                  // first name
                               "Recillo",                // last name
                               "jaysan1292@example.com", // email
                               "100726948",              // student number
                               p.getProgram("T127"),     // program
                               "");                      // password
            me.setPassword(me.getStudentNumber());
            u.insert(me);

            for (int i = 1; i < numUsers; i++) {
                // There is a small chance there could be a duplicate student number
                // generated. So, continually generate student numbers until insert
                // method executes without exception, i.e., the student number was
                // unique.
                while (true) {
                    try {
                        String studentId = RandomStringUtils.randomNumeric(9);
                        User user = new User("User",                         // first name
                                             "Number " + i,                  // last name
                                             "n" + i + "@example.com",       // email
                                             studentId,                      // student number
                                             Extensions.getRandom(programs), // program
                                             studentId);                     // password
                        u.insert(user);
                        break;
                    } catch (SQLException e) {
                        WebAppCommon.log.error(e.getMessage());
                    }
                }
                reportProgress(i, numUsers);
            }
            System.out.print("\r");
            users = u.getAll();
            watch.stop();
            WebAppCommon.log.debug(String.format("Generating %d users took %s.", u.getCount(), watch.toString()));
            watch.reset();
            watch.start();

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.debug("Generating posts.");
            for (int i = 0; i < numPosts; i++) {
                Post post = new Post(new Date(((long) (System.currentTimeMillis() - (Math.random() * maxTime)))),
                                     Extensions.getRandom(users),
                                     PlaceholderGenerator.generateRandomContent(2, (Math.random() > 0.05) ?
                                                                                   PlaceholderGenerator.ContentType.Sentence :
                                                                                   PlaceholderGenerator.ContentType.Paragraph));
                po.insert(post);
                reportProgress(i, numPosts);
            }
            System.out.print("\r");
            posts = po.getAll();
            watch.suspend();
            WebAppCommon.log.debug(String.format("Generating %d posts took %s.", po.getCount(), watch.toString()));
            watch.reset();
            watch.start();

            ///////////////////////////////////////////////////////////////////

            WebAppCommon.log.debug("Generating post comments.");
            max = posts.length;
            progress = 0;
            for (Post post : posts) {
                int iterations = Extensions.randomInt(0, maxComments);
                for (int i = 0; i < iterations; i++) {
                    Comment comment = new Comment(Extensions.getRandom(users),
                                                  PlaceholderGenerator.generateRandomContent(
                                                          new IntegerRange(1, 4).getRandomValue(),
                                                          PlaceholderGenerator.ContentType.Sentence),
                                                  new Date(NumberUtils.toLong(
                                                          new LongRange(post.getPostDate().getTime(),
                                                                        new Date().getTime()).getRandomValue().toString())),
                                                  post.getId());
                    c.insert(comment);
                }
                reportProgress(progress, max);
                progress++;
            }
            System.out.print("\r");
            comments = c.getAll();
            watch.stop();
            WebAppCommon.log.debug(String.format("Generating %d post comments took %s.", c.getCount(), watch.toString()));

            ///////////////////////////////////////////////////////////////////

            try {
                watch.stop();
            } catch (IllegalStateException ignored) {}
            watchTotal.stop();
            WebAppCommon.log.info(String.format("Placeholder content generation (%d posts, %d users, %d comments) took %s.",
                                                p.getCount(),
                                                u.getCount(),
                                                c.getCount(),
                                                watchTotal.toString()));
        } catch (Exception e) {
            WebAppCommon.log.error("There was an error generating placeholder content.. ABANDON SHIP", e);
            WebAppCommon.shutdownApplication();
            System.exit(-1);
        }
    }
}
