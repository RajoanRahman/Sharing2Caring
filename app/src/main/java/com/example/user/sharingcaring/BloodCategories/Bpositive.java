package com.example.user.sharingcaring.BloodCategories;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.user.sharingcaring.Adapters.BloodPostModel;
import com.example.user.sharingcaring.ClickPostActivityForDelete.BPositiveDelete;
import com.example.user.sharingcaring.ClickPostActivityForDelete.BnegBloodDelete;
import com.example.user.sharingcaring.PostActivities.BPosBloodPostActivity;
import com.example.user.sharingcaring.PostActivities.BloodPostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Bpositive extends AppCompatActivity {

    Toolbar bPosBloodBar;
    FloatingActionButton floatingActionButton;
    RecyclerView bPosBloodPostList;
    DatabaseReference postRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpositive);

        bPosBloodBar=findViewById(R.id.bPos_blood_app_bar);
        setSupportActionBar(bPosBloodBar);
        getSupportActionBar().setTitle("B Positive");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        postRef=FirebaseDatabase.getInstance().getReference().child("BPositiveBloodPosts");

        bPosBloodPostList=findViewById(R.id.bPos_blood_post_recyclerView);
        bPosBloodPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        bPosBloodPostList.setLayoutManager(linearLayoutManager);

        floatingActionButton=findViewById(R.id.bPos_openBlood_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookPostIntent=new Intent(Bpositive.this,BPosBloodPostActivity.class);
                startActivity(bookPostIntent);
            }
        });

        displayAlluserPost();

    }

    private void displayAlluserPost() {
        FirebaseRecyclerAdapter<BloodPostModel,BPosBloodPostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<BloodPostModel, BPosBloodPostViewHolder>
                        (BloodPostModel.class,R.layout.blood_post_layout,BPosBloodPostViewHolder.class,postRef) {
                    @Override
                    protected void populateViewHolder(BPosBloodPostViewHolder viewHolder, final BloodPostModel model, int position) {

                        final String postKey=getRef(position).getKey();

                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setAboutBlood(model.getAboutBlood());
                        viewHolder.setBloodGroup(model.getBloodGroup());
                        viewHolder.setLocation(model.getLocation());
                        viewHolder.setWhenNeed(model.getWhenNeed());
                        viewHolder.setProfileimage(model.getProfileimage());
                        viewHolder.setPhoneNumber(model.getPhoneNumber());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent newIntent=new Intent(Bpositive.this,BPositiveDelete.class);
                                newIntent.putExtra("PostKey",postKey);
                                startActivity(newIntent);
                            }
                        });
                    }
                };
        bPosBloodPostList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class BPosBloodPostViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BPosBloodPostViewHolder(@NonNull View itemView) {
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

        public void setPhoneNumber(String phoneNumber){
            TextView number=mView.findViewById(R.id.blood_post_phoneNumber);
            number.setText(phoneNumber);
        }

    }

}
