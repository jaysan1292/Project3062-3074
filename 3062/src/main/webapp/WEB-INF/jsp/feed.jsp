<%--
  Created by IntelliJ IDEA.
  User: Jason Recillo
  Date: 19/09/12
  Time: 8:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="userFeed" class="com.jaysan1292.project.c3062.data.beans.FeedBean" scope="request"/>

<t:base>
    <jsp:attribute name="page_title">Home</jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="row-fluid the-container">
                <div class="span2 hidden-phone">
                    <div id="l-sidebar">
                        <ul class="nav nav-list">
                            <li class="nav-header">Menu Header 1</li>
                            <li class="active"><a href="javascript:void(0)">Home</a></li>
                            <li><a href="javascript:void(0)">Item 1</a></li>
                            <li><a href="javascript:void(0)">Item 2</a></li>
                            <li><a href="javascript:void(0)">Item 3</a></li>
                            <li><a href="javascript:void(0)">Item 4</a></li>
                            <li><a href="javascript:void(0)">Item 5</a></li>
                            <li><a href="javascript:void(0)">(more options coming soon)</a></li>
                        </ul>
                    </div>
                </div>
                <div class="span7" id="content">
                    <div id="posts">
                        <c:choose>
                            <c:when test="${!empty userFeed.postsWithOffset}">
                                <c:forEach items="${userFeed.postsWithOffset}" var="post">
                                    <t:feed_post post="${post}"/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span id="no-posts">there doesn't seem to be anything here</span>
                            </c:otherwise>
                        </c:choose>
                        <center>
                            <div id="pager" class="btn-group">
                                <c:if test="${userFeed.offset > 0}">
                                    <a class="btn"
                                       href="${pageContext.servletContext.contextPath}/feed?order=${userFeed.offset - userFeed.postsPerPage}">Previous</a>
                                </c:if>
                                <c:if test="${(userFeed.offset < userFeed.postCount) && ((userFeed.offset + userFeed.postsPerPage) < userFeed.postCount)}">
                                    <a class="btn"
                                       href="${pageContext.servletContext.contextPath}/feed?order=${userFeed.offset + userFeed.postsPerPage}">Next</a>
                                </c:if>
                            </div>
                        </center>
                    </div>
                </div>
                <div class="span3 hidden-phone" id="r-sidebar">
                    <div class="">
                        <h4>What's new</h4>
                        (more stuff coming soon)
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
