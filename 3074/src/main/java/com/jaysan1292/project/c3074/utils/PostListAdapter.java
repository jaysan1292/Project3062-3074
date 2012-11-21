package com.jaysan1292.project.c3074.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jaysan1292.project.c3074.MobileAppCommon;
import com.jaysan1292.project.c3074.R;
import com.jaysan1292.project.c3074.activity.MainActivity;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.util.DateUtils;

import java.util.ArrayList;

public class PostListAdapter extends BaseAdapter implements Disposable {
    private static LayoutInflater inflater;
    private ArrayList<Post> posts;
    private final MainActivity activity;

    public PostListAdapter(Context context, ArrayList<Post> posts) {
        this.posts = posts;
        this.activity = (MainActivity) context;
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

        Post p = (Post) getItem(position);

        thumbnail.setImageResource(R.drawable.user);
        author.setText(p.getPostAuthor().getFullName());
        content.setText(p.getPostContent());
        time.setText(DateUtils.getRelativeDateString(p.getPostDate()));

        thumbnail.setOnClickListener(activity);
        thumbnail.setTag(p.getPostAuthor().getId());

        vi.setTag(R.id.post_id, p.getId());
        thumbnail.setTag(R.id.post_author, p.getPostAuthor());

        return vi;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return posts.get(i).getId();
    }

    public void dispose() {
        MobileAppCommon.log.trace("PostListAdapter dispose");
        posts = null;
    }
}
