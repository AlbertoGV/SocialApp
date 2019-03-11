package com.example.gerard.socialapp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.request.RequestOptions;
import com.example.gerard.socialapp.GlideApp;
import com.example.gerard.socialapp.R;
import com.example.gerard.socialapp.model.Post;
import com.example.gerard.socialapp.view.PostViewHolder;
import com.example.gerard.socialapp.view.activity.MediaActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class SearchFragment extends Fragment {
    public DatabaseReference mReference;
    public FirebaseUser mUser;
    Context c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_posts, container, false);

        mReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Post>()
                .setIndexedQuery(setQuery(), mReference.child("posts/data"), Post.class)
                .setLifecycleOwner(this)
                .build();

        RecyclerView recycler = view.findViewById(R.id.rvPosts);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL));

        recycler.setAdapter(new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new PostViewHolder(inflater.inflate(R.layout.item_search_post, viewGroup, false));
            }


            @Override
            protected void onBindViewHolder(final PostViewHolder viewHolder, int position, final Post post) {
                final String postKey = getRef(position).getKey();

                if (post.mediaUrl != null) {
                        viewHolder.video.setVisibility(View.GONE);
                GlideApp.with(SearchFragment.this).load(post.mediaUrl).apply(RequestOptions.centerCropTransform()).into(viewHolder.image);
            }
                }




        });
        return view;

    }


        Query setQuery () {
            return mReference.child("posts/all-posts").limitToFirst(100);
        }
    }

