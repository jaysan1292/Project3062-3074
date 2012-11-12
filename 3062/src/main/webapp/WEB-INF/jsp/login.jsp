<%@ page import="com.jaysan1292.project.c3062.util.Tutorial" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="programs" scope="request" type="java.util.List<com.jaysan1292.project.common.data.Program>"/>
<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>

<t:base>
    <jsp:attribute name="page_title">Log In</jsp:attribute>
    <jsp:attribute name="show_navbar_items">false</jsp:attribute>
    <jsp:attribute name="show_navbar_login">false</jsp:attribute>
    <jsp:attribute name="optional_header">
        <script type="text/javascript"
                src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.10.0/jquery.validate.min.js"></script>
    </jsp:attribute>
    <jsp:attribute name="optional_footer">
        <script type="text/javascript">
            $(document).ready(function () {
                // Set the login form to the same height as the signup form
                $('#login-form').css('height', $('#signup-form').css('height'));

                // Set a popover to show, giving a hint as to the possible user ids
                $('#login_username')
                        .attr('data-title', 'Note')
                        .attr('data-content', 'For the purposes of this assignment, the username and password are the same.<hr/><b>IDs to try (refresh to get a new set):</b><br/><c:out value="<%=Tutorial.getUserIds(3)%>" escapeXml="false"/>')
                        .attr('data-placement', 'left')
                        .attr('data-trigger', 'manual');
                $('#login_username').focus(function () {
                    $('#login_username').popover('show');
                });
                $('#login_username').blur(function () {
                    $('#login_username').popover('hide');
                });
            });
        </script>
        <script type="text/javascript">
            function getRegisterUrl() {
                return '${pageContext.servletContext.contextPath}/register';
            }
        </script>
        <script type="text/javascript" src="<c:url value="/js/register.js"/>"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="row-fluid">
                <div class="well span6" id="signup-form">
                    <form class="form-horizontal"
                          id="register-form"
                          action="<c:url value="/register"/>"
                          method="post">
                        <legend>Sign up</legend>
                        <div class="control-group">
                            <label class="control-label" for="firstName">First Name</label>

                            <div class="controls">
                                <input class="span12"
                                       type="text"
                                       id="firstName"
                                       name="firstName"
                                       placeholder="First Name">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="lastName">Last Name</label>

                            <div class="controls">
                                <input class="span12"
                                       type="text"
                                       id="lastName"
                                       name="lastName"
                                       placeholder="Last Name">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="studentNumber">Student Number</label>

                            <div class="controls">
                                <input class="span12"
                                       type="text"
                                       id="studentNumber"
                                       name="studentNumber"
                                       placeholder="Student Number">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="program">Program</label>

                            <div class="controls">
                                <select class="span12" id="program" name="program">
                                    <option value="invalid">---Select your Program---</option>
                                    <c:forEach items="${programs}" var="program">
                                        <option value="${program.programCode}">${program}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="password">Password</label>

                            <div class="controls">
                                <input class="span12"
                                       type="password"
                                       id="password"
                                       name="password"
                                       placeholder="Password"><br/><br/>
                                <input class="span12"
                                       type="password"
                                       id="password-confirm"
                                       name="password-confirm"
                                       placeholder="Confirm Password">
                            </div>
                        </div>
                        <div class="form-actions">
                            <button class="btn btn-primary" type="submit">Sign Up</button>
                        </div>
                    </form>
                </div>
                <div class="well span6" id="login-form">
                    <form class="form-horizontal" method="post" action="login">
                        <legend>Log in</legend>
                        <div class="control-group">
                            <label class="control-label" for="login_username">Username</label>

                            <div class="controls">
                                <input class="span12" type="text" id="login_username" name="login_username"
                                       placeholder="Username">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="login_password">Password</label>

                            <div class="controls">
                                <input class="span12" type="password" id="login_password" name="login_password"
                                       placeholder="Password">
                            </div>
                        </div>

                        <div class="form-actions">
                            <div class="control-group error">
                                <label>
                                        ${errorMessage}
                                </label>
                            </div>
                            <label class="checkbox" for="remember_me" id="remember_me_label">
                                Remember me
                                <input type="checkbox" class="checkbox" id="remember_me">
                            </label>
                            <button type="submit" class="btn btn-primary">Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
