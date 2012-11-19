<%@ page import="com.jaysan1292.project.c3062.WebAppCommon, com.jaysan1292.project.c3062.util.Tutorial" %>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Boolean loggedIn = (Boolean) pageContext.getSession().getAttribute(WebAppCommon.ATTR_LOGGED_IN);
    if (loggedIn == null) loggedIn = false;

    if (loggedIn) {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/feed"));
    }
%>

<t:base>
    <jsp:attribute name="page_title"><c:out value="<%=WebAppCommon.AppName%>"/></jsp:attribute>
    <jsp:attribute name="show_navbar_items">false</jsp:attribute>
    <jsp:attribute name="optional_footer">
        <script type="text/javascript">
            $(document).ready(function () {
                // Set a popover to show, giving a hint as to the possible user ids
                $('#login_username')
                        .attr('data-title', 'Note')
                        .attr('data-content', 'For the purposes of this assignment, the username and password are the same.<hr/><b>IDs to try (refresh to get a new set):</b><br/><c:out value="<%=Tutorial.getUserIds(3)%>" escapeXml="false"/>')
                        .attr('data-placement', 'bottom')
                        .attr('data-trigger', 'manual');
                $('#login_username').focus(function () {
                    $('#login_username').popover('show');
                });
                $('#login_username').blur(function () {
                    $('#login_username').popover('hide');
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container" id="splash-container">
            <div class="row-fluid">
                <div class="hero-unit well splash-well">
                    <div class="row">
                        <div id="splash">
                            <div id="splash-logo-bg"></div>
                            <h1 id="splash-head"><c:out value="<%=WebAppCommon.AppName%>"/></h1>

                            <p id="splash-tag"><c:out value="<%=WebAppCommon.AppTagline%>"/></p>

                            <form action="login">
                                <button class="btn btn-success btn-large" type="submit" id="splash-signup-btn">Sign
                                    up
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
