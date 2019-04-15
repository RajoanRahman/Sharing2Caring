package com.example.user.sharingcaring;

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

import com.example.user.sharingcaring.Adapters.BookPostAdapter;
import com.example.user.sharingcaring.MainPostsActivities.Blood;
import com.example.user.sharingcaring.PostActivities.BloodPostActivity;
import com.example.user.sharingcaring.PostActivities.ClubPostActivty;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NsuAllClubs extends AppCompatActivity {

    Toolbar clubBar;
    FloatingActionButton floatingActionButton;
    RecyclerView clubPostList;
    DatabaseReference postRef;
    String currentUserId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsu_all_clubs);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        postRef = FirebaseDatabase.getInstance().getReference().child("ClubPosts");

        clubBar = findViewById(R.id.club_app_bar);
        setSupportActionBar(clubBar);
        getSupportActionBar().setTitle("ClubPost");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        clubPostList = findViewById(R.id.club_post_recyclerView);
        clubPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        clubPostList.setLayoutManager(linearLayoutManager);

        floatingActionButton = findViewById(R.id.openClub_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookPostIntent = new Intent(NsuAllClubs.this, ClubPostActivty.class);
                startActivity(bookPostIntent);
            }
        });
        displayAlluserPost();
    }

    private void displayAlluserPost() {
        FirebaseRecyclerAdapter<BookPostAdapter, ClubPostViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<BookPostAdapter, ClubPostViewHolder>
                        (BookPostAdapter.class, R.layout.club_post_layout, ClubPostViewHolder.class, postRef) {
                    @Override
                    protected void populateViewHolder(ClubPostViewHolder viewHolder, BookPostAdapter model, int position) {

                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setPostimage(model.getPostimage());
                        viewHolder.setProfileimage(model.getProfileimage());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setDate(model.getDate());
                        //viewHolder.setLocation(model.getLocation());
                    }
                };
        clubPostList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ClubPostViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ClubPostViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setFullname(String fullname) {
            TextView userName = mView.findViewById(R.id.club_book_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage) {
            CircleImageView userImage = mView.findViewById(R.id.club_post_image_profile);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);
        }

        public void setTime(String time) {
            TextView postTime = mView.findViewById(R.id.club_post_time);
            postTime.setText(" " + time);
        }

        public void setDate(String date) {
            TextView postDate = mView.findViewById(R.id.club_post_date);
            postDate.setText(date);
        }

        public void setDescription(String description) {
            TextView postDesc = mView.findViewById(R.id.club_post_description);
            postDesc.setText(description);
        }

        public void setPostimage(String postimage) {
            ImageView post_Image = mView.findViewById(R.id.club_post_image);
            Picasso.get().load(postimage).into(post_Image);
        }

        /*public void setLocation(String location) {
            TextView postLocation = mView.findViewById(R.id.phr_book_post_location);
            postLocation.setText(location);
        }*/
    }

}

