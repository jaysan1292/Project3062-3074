<%@ tag import="com.jaysan1292.project.c3062.WebAppCommon" %>
<%--
  Created by IntelliJ IDEA.
  User: Jason Recillo
  Date: 18/09/12
  Time: 5:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ tag description="Base page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="page_title" required="true" %>
<%@ attribute name="optional_header" fragment="true" required="false" %>
<%@ attribute name="optional_footer" fragment="true" required="false" %>
<%@ attribute name="show_navbar_items" type="java.lang.Boolean" required="false" %>
<%@ attribute name="show_navbar_login" type="java.lang.Boolean" required="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <%--<jsp:invoke fragment="page_title"/>--%>
        <c:out value="${page_title}"/>
    </title>
    <!--Bootstrap-->
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value="/css/bootstrap-responsive.min.css"/>" rel="stylesheet">

    <link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700|Droid+Sans+Mono' rel='stylesheet'
          type='text/css'>
    <link href="<c:url value="/css/application.css"/>" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.8.2.js" type="text/javascript"></script>
    <script src="<c:url value="/js/application.js"/>" type="text/javascript"></script>

    <jsp:invoke fragment="optional_header"/>
</head>
<body>

<t:navbar show_items="${show_navbar_items != null ? show_navbar_items : true}"
          show_login="${show_navbar_login != null ? show_navbar_login : true}"/>

<jsp:doBody/>
<br/>
<%--TODO: Fix the tablet-size mobile layout--%>
<div class="navbar navbar-fixed-bottom" id="footer">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <li>
                    <a href="<c:url value="/about"/>">
                        COMP 3062 Project (Fall 2012): "<%=WebAppCommon.AppName%>" by Jason Recillo, submitted to
                        Przemyslaw Pawluk.
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js" type="text/javascript"></script>
<jsp:invoke fragment="optional_footer"/>
</body>
</html>
