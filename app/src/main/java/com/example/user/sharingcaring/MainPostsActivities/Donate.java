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

import com.example.user.sharingcaring.Adapters.DonatePostModel;
import com.example.user.sharingcaring.Adapters.TutorPostModel;
import com.example.user.sharingcaring.CallDialgue;
import com.example.user.sharingcaring.ClickPostActivityForDelete.DonateDeletePost;
import com.example.user.sharingcaring.ClickPostActivityForDelete.TutorDeletePost;
import com.example.user.sharingcaring.DonationActivity;
import com.example.user.sharingcaring.PostActivities.BloodPostActivity;
import com.example.user.sharingcaring.PostActivities.DonatePostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Donate extends AppCompatActivity {

    Toolbar donateBar;
    FloatingActionButton floatingActionButton;
    RecyclerView donatePostList;
    DatabaseReference postRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        donateBar=findViewById(R.id.donate_app_bar);
        setSupportActionBar(donateBar);
        getSupportActionBar().setTitle("Donate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        postRef=FirebaseDatabase.getInstance().getReference().child("DonatePosts");

        donatePostList=findViewById(R.id.donate_post_recyclerView);
        donatePostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        donatePostList.setLayoutManager(linearLayoutManager);

        floatingActionButton=findViewById(R.id.openDonate_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookPostIntent=new Intent(Donate.this,DonatePostActivity.class);
                startActivity(bookPostIntent);
            }
        });

        displayAlluserPost();

    }

    private void displayAlluserPost() {

        FirebaseRecyclerAdapter<DonatePostModel,DonatePostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<DonatePostModel, DonatePostViewHolder>
                        (DonatePostModel.class,R.layout.donate_post_layout,DonatePostViewHolder.class,postRef) {
                    @Override
                    protected void populateViewHolder(DonatePostViewHolder viewHolder, final DonatePostModel model, int position) {


                        final String postKey=getRef(position).getKey();

                        viewHolder.setAboutDonation(model.getAboutDonation());
                        viewHolder.setCellNumber(model.getCellNumber());
                        viewHolder.setOthersInfo(model.getOthersInfo());
                        //viewHolder.setLocation(model.getLocation());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setProfileimage(model.getProfileimage());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*Intent newIntent=new Intent(Donate.this,DonateDeletePost.class);
                                newIntent.putExtra("PostKey",postKey);
                                startActivity(newIntent);*/
                                Intent intent=new Intent(Intent.ACTION_DIAL);
                                String number=model.getCellNumber().toString();
                                intent.setData(Uri.parse("tel:"+number));
                                startActivity(intent);

                            }
                        });

                    }
                };

        donatePostList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class DonatePostViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public DonatePostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setFullname(String fullname) {
            TextView userName = mView.findViewById(R.id.donate_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage) {
            CircleImageView userImage = mView.findViewById(R.id.donate_post_image_view);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);

        }

        public void setTime(String time){
            TextView postTime=mView.findViewById(R.id.donate_post_time);
            postTime.setText(" " + time);
        }
        public void setDate(String date){
            TextView postDate=mView.findViewById(R.id.donate_post_date);
            postDate.setText(date);
        }
        public void setAboutDonation(String aboutDonation){
            TextView about=mView.findViewById(R.id.donate_post_about);
            about.setText(aboutDonation);
        }
        public void setCellNumber(String cellNumber){
            TextView about=mView.findViewById(R.id.donation_cell_number);
            about.setText(cellNumber);
        }
        public void setOthersInfo(String othersInfo){
            TextView about=mView.findViewById(R.id.donation_info);
            about.setText(othersInfo);
        }

    }


    public void openDialogue(){
        CallDialgue callDialgue=new CallDialgue();
        callDialgue.show(getSupportFragmentManager(),"call dialog");
    }
}
