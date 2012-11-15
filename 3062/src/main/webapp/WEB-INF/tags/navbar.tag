<%--suppress HtmlFormInputWithoutLabel --%>
<%@ tag import="com.jaysan1292.project.c3062.WebAppCommon" %>
<%@ tag description="Base page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="show_items" type="java.lang.Boolean" %>
<%@ attribute name="show_login" type="java.lang.Boolean" %>

<jsp:useBean id="user" scope="session" type="com.jaysan1292.project.common.data.User"/>
<jsp:useBean id="loggedIn" scope="session" type="java.lang.Boolean"/>

<div class="navbar navbar-fixed-top" id="main-nav">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <i class="icon-bar"></i>
                <i class="icon-bar"></i>
                <i class="icon-bar"></i>
            </a>

            <a class="brand" href="<c:url value="/"/>">
                <i id="logo"></i><%=WebAppCommon.AppName%>
            </a>

            <c:if test="${show_items || show_login}">
                <div class="nav-collapse">
                    <ul class="nav" role="navigation">
                        <c:if test="${show_items}">
                            <%
                                StringBuilder sb = new StringBuilder();
                                String requestUri = WebAppCommon.REGEX_JSP.matcher(request.getRequestURI()).replaceAll("$1$2");

                                for (String[] linkData : WebAppCommon.HeaderMenu) {
                                    String linkText = linkData[0].toLowerCase();
                                    String linkUri = request.getContextPath() + linkData[1];
                                    String linkIcon = linkData[2];

                                    if (!linkIcon.isEmpty()) {
                                        linkText = String.format("<i class=\"%s\"></i> %s", linkIcon, linkText);
                                    }

                                    if ((requestUri).equals(linkUri)) {
                                        sb.append(String.format("<li class=\"active\"><a href=\"%s\">%s</a>", linkUri, linkText));
                                    } else {
                                        sb.append(String.format("<li><a href=\"%s\">%s</a>", linkUri, linkText));
                                    }
                                }

                                out.print(sb.toString());
                            %>
                        </c:if>
                    </ul>
                    <ul class="nav pull-right visible-desktop">
                        <li class="divider-vertical"></li>
                        <c:if test="${show_login}">
                            <c:choose>
                                <%--<c:when test="<%=loggedIn%>">--%>
                                <c:when test="${loggedIn}">
                                    <%--<c:if test="<%=user != null%>">--%>
                                    <c:if test="${user != null}">
                                        <li>
                                            <a href="javascript:void(0)" style="cursor: default;">
                                                    <%--<c:out value="<%=user.getFullName()%>"/>--%>
                                                    ${user.fullName}
                                            </a>
                                        </li>
                                    </c:if>
                                    <li class="divider-vertical"></li>
                                    <li class="dropdown">
                                        <a class="dropdown-toggle" id="settingDropdown" data-toggle="dropdown"
                                           href="javascript:void(0)">
                                            <i class="icon-cog"></i>
                                            settings
                                            <i class="icon-chevron-down"></i>
                                        </a>
                                        <ul class="dropdown-menu" role="menu" aria-labelledby="settingDropdown">
                                            <li><a href="javascript:void(0)">Item 1</a></li>
                                            <li><a href="javascript:void(0)">Item 2</a></li>
                                            <li><a href="javascript:void(0)">Item 3</a></li>
                                            <li class="divider"></li>
                                            <li>
                                                <a href="<c:out value="${pageContext.request.contextPath}"/>/login?logout=1">Logout</a>
                                            </li>
                                        </ul>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <form class="navbar-form form-inline" action="login" method="post">
                                            <input style="width:75px;" type="text" placeholder="username"
                                                   name="login_username" id="login_username"/>
                                            <input style="width:75px;" type="password" placeholder="password"
                                                   name="login_password" id="login_password"/>
                                            <label class="checkbox">
                                                <input type="checkbox"> Remember me
                                            </label>
                                            <button type="submit" class="btn btn-info">login</button>
                                        </form>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </ul>
                    <ul class="nav pull-right hidden-desktop">
                            <%--TODO: mobile login/logout menu--%>
                        <li>
                            <c:choose>
                                <%--<c:when test="<%=loggedIn%>">--%>
                                <c:when test="${loggedIn}">
                                    <a href="<c:url value="/login?logout=1"/>"><i class="icon-off"></i> logout</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/login?sendBackUrl=${pageContext.request.requestURL}"/>"><i
                                            class="icon-off"></i> login</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</div>
