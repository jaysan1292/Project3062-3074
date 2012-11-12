package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import org.apache.commons.lang3.time.StopWatch;
import org.lesscss.LessCompiler;
import org.lesscss.LessException;
import org.lesscss.LessSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(WebAppCommon.SRV_CSS)
public class LessCssServlet extends HttpServlet {
    private static String css;

    public void init() throws ServletException {
        if (css == null) {
            try {
                String path = getServletContext().getRealPath("/less/application.less");

                File source = new File(path);
                LessCompiler compiler = new LessCompiler();

                try {
                    StopWatch watch = new StopWatch(); watch.start();
                    WebAppCommon.log.info("Building CSS.");
                    css = compiler.compile(new LessSource(source));
                    watch.stop();
                    WebAppCommon.log.info(String.format("Building CSS complete! Took %s.", watch.toString()));
                } catch (LessException ex) {
                    WebAppCommon.log.error(ex.getMessage(), ex);
                }
            } catch (IOException e) {
                throw new ServletException("Failed to load CSS.", e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/css");
        response.getWriter().print(css);
    }
}
