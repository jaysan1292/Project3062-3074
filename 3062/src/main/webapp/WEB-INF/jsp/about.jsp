<%@ page import="com.jaysan1292.project.c3062.WebAppCommon" %>
<%--
  Created by IntelliJ IDEA.
  User: Jason Recillo
  Date: 03/10/12
  Time: 8:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base>
    <jsp:attribute name="page_title">About</jsp:attribute>
    <jsp:attribute name="show_navbar_items">false</jsp:attribute>
    <jsp:attribute name="show_navbar_login">false</jsp:attribute>
    <jsp:attribute name="optional_footer">
        <script type="text/javascript">
            $(document).ready(function () {
                $("#about-info").css("height", $("#about-libraries").css("height"));
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="container" id="splash-container">
                <div class="row-fluid">
                    <div class="hero-unit well" id="about-splash-well">
                        <div class="row">
                            <div id="splash">
                                <div id="splash-logo-bg"></div>
                                <h1 id="splash-head"><c:out value="<%=WebAppCommon.AppName%>"/></h1>

                                <p id="splash-tag"><c:out value="<%=WebAppCommon.AppTagline%>"/></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row-fluid">
                <div id="about-info" class="span5">
                    <div>
                        <h4>COMP 3062 Project: <c:out value="<%=WebAppCommon.AppName%>"/></h4>
                        <b>Submitted by</b>: Jason Recillo (100726948)<br/>
                        <b>Submitted to</b>: Przemyslaw Pawluk<br/>
                        <b>Semester</b>: Fall 2012
                    </div>
                </div>
                <div id="about-libraries" class="span7">
                    <div>
                        gbc.myCommunity uses the following open-source libraries/frameworks (all are released
                        under the Apache License, version 2.0 unless otherwise specified):
                        <jsp:useBean id="libraries" scope="request"
                                     type="java.util.List<com.jaysan1292.project.c3062.util.AboutPageLibrary>"/>
                        <ul>
                            <c:forEach items="${libraries}" var="lib">
                                <li>
                                    <a href="${lib.href}" title="${lib.title}">${lib.text}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
