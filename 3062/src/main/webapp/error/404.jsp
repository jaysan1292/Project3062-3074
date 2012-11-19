<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Jason Recillo
  Date: 23/09/12
  Time: 8:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>

<t:base>
    <jsp:attribute name="page_title">404</jsp:attribute>
    <jsp:attribute name="show_navbar_items">false</jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="center-text well">
                    <h1>Whoops, that page doesn't exist!</h1>

                    <p>Sorry about that&mdash;looks like the server gremlins broke something again. We'll get right on
                        it and fix things up right away.</p>

                    <a href="<c:url value="/"/>" class="btn btn-info btn-large">
                        <i class="icon-home icon-white"></i> Home
                    </a>
                    <a href="javascript:window.history.back()" class="btn btn-large">
                        <i class="icon-arrow-left"></i> Go Back
                    </a>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
