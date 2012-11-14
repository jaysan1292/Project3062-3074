<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: Jason Recillo
  Date: 05/10/12
  Time: 2:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="post" scope="request" type="com.jaysan1292.project.common.data.beans.PostBean"/>
<jsp:useBean id="user" scope="session" type="com.jaysan1292.project.common.data.beans.UserBean"/>

<t:base>
    <jsp:attribute name="page_title">
        <c:out value="<%=StringUtils.abbreviate(post.getPost().getPostContent(), 50)%>"/>
    </jsp:attribute>
    <jsp:attribute name="optional_header">
        <script type="text/javascript" src="<c:url value="/js/comments.js"/>"></script>
    </jsp:attribute>
    <jsp:attribute name="optional_footer">
        <script type="text/javascript">
            $(document).ready(function () {
                $('#comment-button').click(function () {
                    if (!$('#comment-button').hasClass('disabled')) {
                        $('#comment-button').addClass('disabled');
                        sendComment(
                                "${pageContext.servletContext.contextPath}/post/comment",
                                ${user.id},
                                ${post.post.id}
                        );
                    }
                });
                $('#comment-box').keyup(function () {
                    var button = $('#comment-button');
                    if ($('#comment-box').val().length == 0) {
                        button.addClass('disabled');
                    } else {
                        if (button.hasClass('disabled')) {
                            button.removeClass('disabled');
                        }
                    }
                });
                $('#comment-button').addClass('disabled');
                updatePostTimes();

                const updateTimer = setInterval(updatePostTimes, 1000);
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container the-container">
            <div class="row-fluid">
                <div class="span8 offset2">
                    <div class="post-container clearfix">
                        <img class="img-polaroid pull-left post-author-img-thumb" src="http://placehold.it/50x50"/>
                        <h5 class="post-author-name">
                            <a href="${pageContext.request.contextPath}/profile?id=${post.post.postAuthor.id}">
                                    ${post.post.postAuthor.fullName}
                            </a>
                            <span class="label label-info"
                                  style="-webkit-transform: scale(0.65);display: inline-block;">
                                    ${post.post.postAuthor.program.programCode}
                            </span>
                        </h5>

                        <div class="post-content">
                                ${post.post.postContent}
                        </div>
                        <div class="post-comment-link">
                            <span title="${post.post.postDate}"><i class="icon-time"></i>
                                    ${post.post.relativePostDateString}</span>
                            <span class="post-comment-link-separator">|</span>
                            <i class="icon-comment"></i>
                            <span id="post-comment-count">${post.postCommentCount} ${post.postCommentCount != 1 ? "comments":"comment"}</span>
                        </div>
                    </div>
                    <div class="row-fluid span10 offset1" id="comment-area">
                        <c:choose>
                            <c:when test="${post.postCommentCount !=0}">
                                <div class="container-fluid">
                                    <c:forEach items="${post.comments}" var="comment">
                                        <t:post_comment comment="${comment}"/>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <span id="no-posts">there doesn't seem to be anything here</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="row-fluid" id="comment-form">
                        <form id="comment">
                            <label>
                                <textarea id="comment-box" class="span8 offset2"
                                          placeholder="Write a comment!"></textarea>
                            </label>
                            <a href="javascript:void(0)" id="comment-button" type="submit"
                               class="btn btn-primary span2 offset8">
                                Comment
                            </a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
