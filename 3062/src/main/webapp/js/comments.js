/**
 * Created with IntelliJ IDEA.
 * User: Jason Recillo
 * Date: 02/11/12
 * Time: 1:57 AM
 * To change this template use File | Settings | File Templates.
 */

function sendComment(url, userId, postId) {
    $.ajax({
        type:     "post",
        url:      url,
        dataType: "html",
        data:     {
            "comment":        $('#comment-box').val(),
            "comment-poster": userId,
            "comment-date":   new Date().getTime(),
            "post-id":        postId
        },
        success:  function (msg) {
            var commentArea = $('#comment-area');
            var newComment = $(msg).hide();
            if (commentArea.children('.container-fluid').size() !== 0) {
//                commentArea.children('.container-fluid').append(msg);
                newComment.appendTo(commentArea.children('.container-fluid'))
                    .show(500, function () {
                        $('html, body').animate({scrollTop: $(document).height()}, 1000);
                    });
                $('#comment-box').val('');
                updatePostTimes();
            } else {
                commentArea.children('#no-posts').detach();
                $('#comment-area').append('<div class="container-fluid"></div>');
                commentArea.children('.container-fluid').append(msg);
                $('#comment-box').val('').change();
                updatePostTimes();
            }

            var commentCount = commentArea.children('.container-fluid').children().size();
            $('#post-comment-count').text(commentCount + ' ' + (commentCount !== 1 ? 'comments' : 'comment'));
        },
        error:    function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status + ': ' + thrownError);
        }
    });
}

function updatePostTimes() {
    $('.comment-time span').each(function () {
        var theDate = $(this).attr('title');
        var dateString = relativeDateString(theDate);
        $(this).html('<i class="icon-time"></i> ' + dateString);
    });
}
