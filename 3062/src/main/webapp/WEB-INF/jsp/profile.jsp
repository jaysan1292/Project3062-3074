<%--
  Created by IntelliJ IDEA.
  User: Jason Recillo
  Date: 22/09/12
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="profile" scope="request" type="com.jaysan1292.project.c3062.data.beans.ProfileBean"/>

<t:base>
    <jsp:attribute name="page_title">${profile.user.fullName} - myProfile</jsp:attribute>
    <jsp:body>
        <div class="container profile-container">
            <div class="row-fluid">
                <div class="hero-unit clearfix" id="profile-jumbotron">
                    <div class="pull-left">
                        <img src="http://placehold.it/200x200" class="img-polaroid pull-left"
                             alt="${profile.user.fullName}"/>
                    </div>

                    <div id="user-info">
                        <h1>${profile.user.fullName}</h1>

                        <h2>${profile.user.program}</h2>
                    </div>
                </div>
            </div>
            <div class="row-fluid">
                <div class="span3 hidden-phone">
                    <h3>classmates</h3>

                    <div class="row-fluid" id="classmate-images">
                        <c:forEach items="${profile.classmates}" var="classmate">
                            <a href="<c:url value="/profile?id=${classmate.id}"/>"
                               title="${classmate.fullName}"
                               style="text-decoration: none;
                                      width: 50px;
                                      height: 50px;">
                                <img src="http://placehold.it/50x50" class="img-polaroid"/>
                            </a>
                        </c:forEach>
                    </div>
                </div>
                <div class="span9" id="content">
                    <div id="profile-posts">
                        <c:choose>
                            <c:when test="${!(empty profile.posts)}">
                                <c:forEach items="${profile.posts}" var="post">
                                    <t:feed_post post="${post}"/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span id="no-posts">there doesn't seem to be anything here</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
