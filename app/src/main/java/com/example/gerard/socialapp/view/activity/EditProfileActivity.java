package com.example.gerard.socialapp.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gerard.socialapp.R;
import com.example.gerard.socialapp.model.User;
import com.example.gerard.socialapp.view.fragment.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    Button editButton;
    EditText et_biography;
    EditText et_web;
    EditText et_profileName;
    ImageView iv_imageperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editButton=findViewById(R.id.aceptarAnuncio);
        et_biography=findViewById(R.id.et_biography);
        et_web=findViewById(R.id.et_web);
        et_profileName=findViewById(R.id.name);



                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user!=null) {

                            user.name = et_profileName.getText().toString();
                            user.webperfil=et_web.getText().toString();
                            user.biography=et_biography.getText().toString();

                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProfileActivity.this,MainActivity.class);
                startActivity(i);
            }
        });





    }
}
