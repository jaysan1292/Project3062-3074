package com.jaysan1292.project.c3074.utils;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Level;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/11/12
 * Time: 7:17 PM
 *
 * @author Jason Recillo
 */
public class Log4jConfigurator {
    private Log4jConfigurator() {}

    public static void configure() {
        LogConfigurator config = new LogConfigurator();

        config.setRootLevel(Level.ALL);
//        config.setLevel("com.jaysan1292", Level.ALL);
        config.setUseFileAppender(false);
        config.setLogCatPattern("(%F:%L): %m%n");

        config.configure();
    }
}
