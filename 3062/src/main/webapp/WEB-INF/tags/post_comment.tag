<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Post comment template" pageEncoding="UTF-8" %>
<%@attribute name="comment" type="com.jaysan1292.project.common.data.Comment" required="true" %>

<div class="comment-container">
    <img class="img-polaroid pull-left comment-author-img-thumb" src="http://placehold.it/50x50"/>
    <h5 class="comment-author-name">
        <a href="${pageContext.request.contextPath}/profile?id=${comment.commentAuthor.id}">${comment.commentAuthor.fullName}</a>
            <span class="label label-info"
                  style="-webkit-transform: scale(0.65);display: inline-block;">${comment.commentAuthor.program.programCode}</span>
    </h5>

    <div class="comment-content">
        <p>${comment.commentBody}</p>
    </div>
    <div class="comment-time">
        <span title="${comment.commentDate}">
            <i class="icon-time"></i> ${comment.commentDate}">
        </span>
    </div>
</div>
