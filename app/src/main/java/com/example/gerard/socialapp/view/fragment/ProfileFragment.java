package com.example.gerard.socialapp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.gerard.socialapp.model.User;
import com.example.gerard.socialapp.view.activity.EditProfileActivity;
import com.example.gerard.socialapp.GlideApp;
import com.example.gerard.socialapp.R;
import com.example.gerard.socialapp.model.Post;
import com.example.gerard.socialapp.view.PostViewHolder;
import com.example.gerard.socialapp.view.activity.MainActivity;
import com.example.gerard.socialapp.view.activity.PostsActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.firebase.ui.auth.AuthUI.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    public DatabaseReference mReference;
    public FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private int mPostsCount = 0;
//
    ImageView imageView;
    TextView tvPosts;
    TextView tvEdit;
    TextView tvProfileName;
    TextView tvweb;
    TextView tvbio;
    ImageView salirsesion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
         imageView = view.findViewById(R.id.image3) ;

        mReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        tvPosts = view.findViewById(R.id.tvPosts);
        tvEdit = view.findViewById(R.id.textEditProfile);
        tvProfileName = view.findViewById(R.id.display_name);
        tvweb = view.findViewById(R.id.tv_web);
        tvbio = view.findViewById(R.id.tv_bio);
        salirsesion = view.findViewById(R.id.salirsesion);

        salirsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        });
            }
        });



        FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tvProfileName.setText(user.name);
                tvweb.setText(user.webperfil);
                tvbio.setText(user.biography);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(i);
            }
        });
        RecyclerView recycler = view.findViewById(R.id.rvPosts1);
        GlideApp.with(ProfileFragment.this).load(mUser.getPhotoUrl()).into(imageView);

        getPostsCount();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Post>()
                .setIndexedQuery(setQuery(), mReference.child("posts/data"), Post.class)
                .setLifecycleOwner(this)
                .build();




        recycler.setLayoutManager(new StaggeredGridLayoutManager(3,GridLayoutManager.VERTICAL));
        recycler.setAdapter(new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new PostViewHolder(inflater.inflate(R.layout.item_profile_post, viewGroup, false));
            }


            @Override
            protected void onBindViewHolder(final PostViewHolder viewHolder, int position, final Post post) {
                final String postKey = getRef(position).getKey();
                if (post.mediaUrl != null) {
                    GlideApp.with(ProfileFragment.this).load(post.mediaUrl).apply(RequestOptions.centerCropTransform()).into(viewHolder.image);
                    viewHolder.image.setVisibility(view.VISIBLE);
                    viewHolder.video.setVisibility(view.GONE);


                }

            }


        });
        return view;

    }

    Query setQuery () {
        return mReference.child("posts/user-posts").child(mUser.getUid()).limitToFirst(100);
    }
    private void getPostsCount(){
        mPostsCount = 0;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("posts/user-posts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found post:" + singleSnapshot.getValue());
                    mPostsCount++;
                }
                tvPosts.setText(String.valueOf(mPostsCount));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}


