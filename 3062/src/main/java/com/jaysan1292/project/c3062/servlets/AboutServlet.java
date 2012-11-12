package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.util.AboutPageLibrary;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author Jason Recillo */
@WebServlet(WebAppCommon.SRV_ABOUT)
public class AboutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("libraries", AboutPageLibrary.LIBRARIES);
        request.getRequestDispatcher(WebAppCommon.JSP_ABOUT).forward(request, response);
    }
}
