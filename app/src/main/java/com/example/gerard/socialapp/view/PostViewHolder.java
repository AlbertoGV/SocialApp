package com.example.gerard.socialapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.gerard.socialapp.R;


public class PostViewHolder extends RecyclerView.ViewHolder {
    public ImageView photo;
    public TextView author;
    public TextView content;
    public ImageView image;
    public VideoView video;
    public TextView author_2;
    public ImageView like;
    public TextView numLikes;
    public TextView time;
    public LinearLayout likeLayout;

    public PostViewHolder(View itemView) {
        super(itemView);

        photo = itemView.findViewById(R.id.photo);
        author = itemView.findViewById(R.id.author);
        content = itemView.findViewById(R.id.content);
        image = itemView.findViewById(R.id.image);
        time = itemView.findViewById(R.id.hora);
        like = itemView.findViewById(R.id.like);
        video = itemView.findViewById(R.id.video);
        numLikes = itemView.findViewById(R.id.num_likes);
        likeLayout = itemView.findViewById(R.id.like_layout);
        author_2 = itemView.findViewById(R.id.author2);
    }
}