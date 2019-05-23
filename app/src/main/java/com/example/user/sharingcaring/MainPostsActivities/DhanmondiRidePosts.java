package com.example.user.sharingcaring.MainPostsActivities;

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

import com.example.user.sharingcaring.Adapters.RideSharePostModel;
import com.example.user.sharingcaring.CallDialgue;
import com.example.user.sharingcaring.ClickPostActivityForDelete.DhanmondiDelete;
import com.example.user.sharingcaring.ExpnadablePlaceName.Dhanmondi;
import com.example.user.sharingcaring.PostActivities.RideSharePostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DhanmondiRidePosts extends AppCompatActivity {

    Toolbar rideShareBar;
    FloatingActionButton floatingActionButton;
    RecyclerView rideSharePostList;
    DatabaseReference postRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhanmondi_ride_posts);

        rideShareBar=findViewById(R.id.dhanmondi_ride_app_bar);
        setSupportActionBar(rideShareBar);
        getSupportActionBar().setTitle("Dhanmondi Area");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        postRef=FirebaseDatabase.getInstance().getReference().child("DhanmondiPosts");


        rideSharePostList=findViewById(R.id.dhanmondi_rideShare_post_recyclerView);
        rideSharePostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rideSharePostList.setLayoutManager(linearLayoutManager);

        floatingActionButton=findViewById(R.id.dhanmondi_openRide_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DhanmondiRidePosts.this,Dhanmondi.class);
                startActivity(intent);
            }
        });

        displayAlluserPost();
    }

    private void displayAlluserPost() {
        FirebaseRecyclerAdapter<RideSharePostModel,DhanmondiRidePostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<RideSharePostModel, DhanmondiRidePostViewHolder>
                        (RideSharePostModel.class,R.layout.ride_share_post_layout,DhanmondiRidePostViewHolder.class,postRef) {
                    @Override
                    protected void populateViewHolder(DhanmondiRidePostViewHolder viewHolder, final RideSharePostModel model, final int position) {

                        final String postKey=getRef(position).getKey();

                        viewHolder.setAboutRide(model.getAboutRide());
                        viewHolder.setRideTime(model.getRideTime());
                        viewHolder.setPhoneNumber(model.getPhoneNumber());
                        viewHolder.setLocation(model.getLocation());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setProfileimage(model.getProfileimage());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(DhanmondiRidePosts.this,DhanmondiDelete.class);
                                intent.putExtra("PostKey",postKey);
                                startActivity(intent);

                            }
                        });
                    }
                };

        rideSharePostList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class DhanmondiRidePostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public DhanmondiRidePostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setFullname(String fullname) {
            TextView userName = mView.findViewById(R.id.ride_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage) {
            CircleImageView userImage = mView.findViewById(R.id.ride_post_image_view);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);

        }

        public void setTime(String time){
            TextView postTime=mView.findViewById(R.id.ride_post_time);
            postTime.setText(" " + time);
        }
        public void setDate(String date){
            TextView postDate=mView.findViewById(R.id.ride_post_date);
            postDate.setText(date);
        }

        public void setLocation(String location){
            TextView postLocation=mView.findViewById(R.id.ride_location);
            postLocation.setText(location);
        }

        public void setRideTime(String rideTime){
            TextView ride_Time=mView.findViewById(R.id.ride_time_post);
            ride_Time.setText(rideTime);
        }

        public void setAboutRide(String aboutRide){
            TextView about_ride_post=mView.findViewById(R.id.ride_post_about);
            about_ride_post.setText(aboutRide);
        }
        public void setPhoneNumber(String phoneNumber){
            TextView rider_number=mView.findViewById(R.id.ride_number_post);
            rider_number.setText(phoneNumber);
        }


    }

    public void openDialogue(){
        CallDialgue callDialgue=new CallDialgue();
        callDialgue.show(getSupportFragmentManager(),"call dialog");
    }

}
