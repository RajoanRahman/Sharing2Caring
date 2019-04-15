package com.example.user.sharingcaring.MainPostsActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sharingcaring.Adapters.BloodPostModel;
import com.example.user.sharingcaring.Adapters.BookPostAdapter;
import com.example.user.sharingcaring.FragmentActivities.WelcomeActivity;
import com.example.user.sharingcaring.PostActivities.BloodPostActivity;
import com.example.user.sharingcaring.PostActivities.BookPostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Blood extends AppCompatActivity {

    Toolbar bloodBar;
    FloatingActionButton floatingActionButton;
    RecyclerView bloodPostList;
    DatabaseReference postRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood);

        bloodBar=findViewById(R.id.blood_app_bar);
        setSupportActionBar(bloodBar);
        getSupportActionBar().setTitle("Blood");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        postRef=FirebaseDatabase.getInstance().getReference().child("BloodPosts");

        bloodPostList=findViewById(R.id.blood_post_recyclerView);
        bloodPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        bloodPostList.setLayoutManager(linearLayoutManager);


        floatingActionButton=findViewById(R.id.openBlood_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookPostIntent=new Intent(Blood.this,BloodPostActivity.class);
                startActivity(bookPostIntent);
            }
        });

        displayAlluserPost();

    }

    private void displayAlluserPost() {


        FirebaseRecyclerAdapter<BloodPostModel,BloodPostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<BloodPostModel, BloodPostViewHolder>
                        (BloodPostModel.class,R.layout.blood_post_layout,BloodPostViewHolder.class,postRef) {
                    @Override
                    protected void populateViewHolder(BloodPostViewHolder viewHolder, BloodPostModel model, int position) {

                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setAboutBlood(model.getAboutBlood());
                        viewHolder.setBloodGroup(model.getBloodGroup());
                        viewHolder.setLocation(model.getLocation());
                        viewHolder.setWhenNeed(model.getWhenNeed());
                        viewHolder.setProfileimage(model.getProfileimage());
                    }

                };

        bloodPostList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BloodPostViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BloodPostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setFullname(String fullname){
            TextView userName=mView.findViewById(R.id.blood_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage){
            CircleImageView userImage=mView.findViewById(R.id.blood_post_image_view);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);
        }
        public void setTime(String time){
            TextView postTime=mView.findViewById(R.id.blood_post_time);
            postTime.setText(" " + time);
        }
        public void setDate(String date){
            TextView postDate=mView.findViewById(R.id.blood_post_date);
            postDate.setText(date);
        }
        public void setAboutBlood(String aboutBlood){
            TextView about_Blood=mView.findViewById(R.id.blood_post_about);
            about_Blood.setText(aboutBlood);
        }

        public void setLocation(String location){
            TextView postLocation=mView.findViewById(R.id.blood_post_location);
            postLocation.setText(location);
        }

        public void setBloodGroup(String bloodGroup){
            TextView group=mView.findViewById(R.id.blood_post_group);
            group.setText(bloodGroup);
        }

        public void setWhenNeed(String whenNeed){
            TextView need=mView.findViewById(R.id.blood_post_needed);
            need.setText(whenNeed);
        }


    }

/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent=new Intent(Blood.this,WelcomeActivity.class);
        startActivity(backIntent);
    }*/


}
