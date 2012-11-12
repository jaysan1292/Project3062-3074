package com.jaysan1292.project.c3062.util;

import java.util.ArrayList;
import java.util.List;

/** @author Jason Recillo */
public class AboutPageLibrary {
    public static final List<AboutPageLibrary> LIBRARIES;

    static {
        LIBRARIES = new ArrayList<AboutPageLibrary>() {{
            add(new AboutPageLibrary("http://twitter.github.com/bootstrap",
                                     "Twitter Bootstrap",
                                     "Twitter Bootstrap (HTML5, CSS3, JavaScript front-end framework)"));

            add(new AboutPageLibrary("http://lesscss.org",
                                     "LESS CSS",
                                     "LESS CSS (Dynamic CSS)"));

            add(new AboutPageLibrary("https://github.com/marceloverdijk/lesscss-java",
                                     "LESS CSS Compiler for Java",
                                     "LESS CSS Compiler for Java (org.lesscss)"));

            add(new AboutPageLibrary("http://commons.apache.org/collections/",
                                     "Apache Commons Collections",
                                     "Apache Commons Collections 3.2.1 (org.apache.commons.collections)"));

            add(new AboutPageLibrary("http://commons.apache.org/io/",
                                     "Apache Commons IO",
                                     "Apache Commons IO 2.4 (org.apache.commons.io)"));

            add(new AboutPageLibrary("http://commons.apache.org/lang/",
                                     "Apache Commons Lang",
                                     "Apache Commons Lang 3.1 (org.apache.commons.lang3"));

            add(new AboutPageLibrary("http://logging.apache.org/log4j/1.2/",
                                     "Apache log4j 1.2",
                                     "Apache log4j 1.2 (org.apache.log4j)"));

            add(new AboutPageLibrary("http://jackson.codehaus.org/",
                                     "Jackson JSON Processor",
                                     "Jackson JSON Processor (org.codehaus.jackson"));

            add(new AboutPageLibrary("http://www.jasypt.org",
                                     "Java simplified encryption",
                                     "Jasypt: Java simplified encryption (org.jasypt)"));
        }};
    }

    private String href;
    private String title;
    private String text;

    public AboutPageLibrary() {}

    public AboutPageLibrary(String href, String title, String text) {
        this.href = href;
        this.title = title;
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
