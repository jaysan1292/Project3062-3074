package com.jaysan1292.project.c3074.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.activity.PostCommentActivity;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.util.DateUtils;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Date: 05/11/12
 * Time: 11:49 PM
 *
 * @author Jason Recillo
 */
public class CommentListAdapter extends BaseAdapter {
    private static LayoutInflater inflater;
    private final ArrayList<Comment> comments;
    private final PostCommentActivity activity;

    public CommentListAdapter(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.activity = (PostCommentActivity) context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) vi = inflater.inflate(R.layout.feed_row, null);

        ImageView thumbnail = (ImageView) vi.findViewById(R.id.profile_image);
        TextView author = (TextView) vi.findViewById(R.id.name);
        TextView content = (TextView) vi.findViewById(R.id.post_content);
        TextView time = (TextView) vi.findViewById(R.id.post_time);

        Comment c = (Comment) getItem(position);

        thumbnail.setImageResource(R.drawable.user);
        author.setText(c.getCommentAuthor().getFullName());
        content.setText(c.getCommentBody());
        time.setText(DateUtils.getRelativeDateString(c.getCommentDate()));

        thumbnail.setOnClickListener(activity);
        thumbnail.setTag(c.getCommentAuthor().getId());

        vi.setTag(R.id.comment_id, c.getId());
        thumbnail.setTag(R.id.comment_author, c.getCommentAuthor());

        return vi;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return comments.get(i).getId();
    }
}
