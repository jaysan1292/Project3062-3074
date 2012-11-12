<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Post template" pageEncoding="UTF-8" %>
<%@ attribute name="post" type="com.jaysan1292.project.common.data.Post" required="true" %>

<div class="post-container clearfix">
    <img class="img-polaroid pull-left post-author-img-thumb" src="http://placehold.it/50x50"/>
    <h5 class="post-author-name">
        <a href="${pageContext.request.contextPath}/profile?id=${post.postAuthor.id}">
            ${post.postAuthor.fullName}
            <span class="label label-info"
                  style="-webkit-transform: scale(0.65);display: inline-block;">${post.postAuthor.program.programCode}</span>
        </a>
    </h5>

    <div class="post-content">
        ${post.postContent}
    </div>
    <div class="post-comment-link">
        <span title="${post.postDate}">
            <i class="icon-time"></i> ${post.relativePostDateString}
        </span>
        <span class="post-comment-link-separator">|</span>
        <a href="${pageContext.request.contextPath}/post?id=${post.id}">
            <i class="icon-comment"></i>
            <%--${post.postCommentCount} ${post.postCommentCount != 1 ? "comments":"comment"}--%>
            n/a
            <i class="icon-chevron-right"></i>
        </a>
    </div>
</div>
