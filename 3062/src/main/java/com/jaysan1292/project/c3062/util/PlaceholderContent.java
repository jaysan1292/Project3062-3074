package com.jaysan1292.project.c3062.util;

import com.jaysan1292.jdcommon.Extensions;
import com.jaysan1292.jdcommon.Range;
import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.security.UserPasswordPair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.*;

@SuppressWarnings("ObjectAllocationInLoop")
public class PlaceholderContent {
    private final static long maxTime = (long) (86400000 * 1.5);
    private final static int numUsers = (Integer) new Range<Integer>(700, 1200).getRandomValue();
    private final static int numPosts = (Integer) new Range<Integer>(7000, 9000).getRandomValue();
    private final static int maxComments = 10;

    public static Set<User> Users;
    public static Set<Post> Posts;
    public static Set<Comment> Comments;
    public static Set<UserPasswordPair> UserPasswords;

    private PlaceholderContent() {}

    static {
        StopWatch watch = new StopWatch();
        StopWatch watchTotal = new StopWatch();
        watch.start();
        watchTotal.start();
        WebAppCommon.log.info("Generating placeholder content (may take a while)...");
        Users = null; Posts = null; UserPasswords = null;
        try {

            //<editor-fold desc="Generate Users">

            //The user "database"
            WebAppCommon.log.trace("Generating users.");
            Users = Collections.synchronizedSet(new TreeSet<User>() {{
                add(new User(0, "Jason", "Recillo", "jaysan1292@example.com", "100726948", Program.AllPrograms.get("T127")));

                for (int i = 1; i < numUsers; i++) {
                    // Make sure we have a unique student number
                    String studentId;
                    while (true) {
                        final String tempId = RandomStringUtils.randomNumeric(9);

                        Predicate checkStudentIdPredicate = new Predicate() {
                            public boolean evaluate(Object object) {
                                User user = (User) object;
                                return tempId.equals(user.getStudentNumber());
                            }
                        };

                        if (CollectionUtils.countMatches(this, checkStudentIdPredicate) == 0) {
                            studentId = tempId;
                            break;
                        }
                    }
                    add(new User(i, "User", "Number " + i, "n" + i + "@example.com", studentId, Extensions.getRandom(Program.AllPrograms.values())));
                }
            }});
            watch.stop();
            WebAppCommon.log.trace(String.format("Generating %d users took %s.", Users.size(), watch.toString()));
            watch.reset();
            watch.start();

            //</editor-fold>

            //<editor-fold desc="Generate Posts">

            //The post "database"
            WebAppCommon.log.trace("Generating posts.");
            Posts = Collections.synchronizedSet(new TreeSet<Post>() {{
                //create random posts
                for (int i = 0; i < numPosts; i++) {
                    add(new Post(i, new Date(((long) (System.currentTimeMillis() - (Math.random() * maxTime)))), Extensions.getRandom(Users), PlaceholderGenerator.generateRandomContent(2, (Math.random() > 0.05) ? PlaceholderGenerator.ContentType.Sentence : PlaceholderGenerator.ContentType.Paragraph)));
                }
            }});
            watch.suspend();
            WebAppCommon.log.trace(String.format("Generating %d posts took %s.", Posts.size(), watch.toString()));
            watch.reset();
            watch.start();

            //</editor-fold>

            //<editor-fold desc="Generate Passwords">

            //The password "database"
            WebAppCommon.log.trace("Generating user passwords.");
            UserPasswords = Collections.synchronizedSet(new TreeSet<UserPasswordPair>() {{
                //For now just set the password to be the same as the student ID
                for (User user : Users) {
                    UserPasswordPair pair = new UserPasswordPair();
                    pair.setUser(user);
                    pair.setPassword(user.getStudentNumber());
                    add(pair);
                }
            }});
            watch.suspend();
            WebAppCommon.log.trace(String.format("Generating %d user passwords took %s.", UserPasswords.size(), watch.toString()));
            watch.reset();
            watch.start();

            //</editor-fold>

            //<editor-fold desc="Generate Comments">

            //The comment "database"
            WebAppCommon.log.trace("Generating post comments.");
            Comments = Collections.synchronizedSet(new TreeSet<Comment>());
            long commentCount = 1;
            for (Post post : Posts) {
                int iterations = Extensions.randomInt(0, maxComments);
                for (int i = 0; i < iterations; i++) {
                    Comments.add(new Comment(commentCount,
                                             Extensions.getRandom(Users),
                                             PlaceholderGenerator.generateRandomContent(5, PlaceholderGenerator.ContentType.Sentence),
                                             new Date(NumberUtils.toLong(new Range<Long>(post.getPostDate().getTime(), new Date().getTime()).getRandomValue().toString())),
                                             post.getId()));
                    commentCount++;
                }
            }
            watch.stop();
            WebAppCommon.log.trace(String.format("Generating %d post comments took %s.", commentCount, watch.toString()));

            //</editor-fold>

            saveAllToDatabase();
        } catch (Exception e) {
            WebAppCommon.log.error("There was an error initializing content ABORT ABORT", e);
            System.exit(1);
        } finally {
            try {
                watch.stop();
            } catch (IllegalStateException ignored) {}
            watchTotal.stop();
            assert (Posts != null) && (Users != null) && (UserPasswords != null);
            WebAppCommon.log.info(String.format("Placeholder content generation (%d posts, %d users) took %s.", Posts.size(), Users.size(), watchTotal.toString()));
        }
    }

    private static void saveAllToDatabase() {

    }

    public static User getUser(final long id) {
        return (User) CollectionUtils.find(Users, new Predicate() {
            public boolean evaluate(Object object) {
                return ((User) object).getId() == id;
            }
        });
    }

    public static User getUserByStudentId(final String studentId) {
        return (User) CollectionUtils.find(Users, new Predicate() {
            public boolean evaluate(Object object) {
                return ((User) object).getStudentNumber().equals(studentId);
            }
        });
    }

    public static UserPasswordPair getPasswordPair(final User user) {
        return (UserPasswordPair) CollectionUtils.find(UserPasswords, new Predicate() {
            public boolean evaluate(Object object) {
                return ((UserPasswordPair) object).getUser().equals(user);
            }
        });
    }

    public static Post getPost(final long id) {
        return (Post) CollectionUtils.find(Posts, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Post) object).getId() == id;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Comment> getComments(final long postId) {
        return new ArrayList<Comment>(CollectionUtils.select(Comments, new Predicate() {
            public boolean evaluate(Object object) {
                return ((Comment) object).getParentPostId() == postId;
            }
        }));
    }
}
