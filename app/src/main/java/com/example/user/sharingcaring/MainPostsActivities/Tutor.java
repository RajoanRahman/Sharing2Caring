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

import com.example.user.sharingcaring.Adapters.BloodPostModel;
import com.example.user.sharingcaring.Adapters.TutorPostModel;
import com.example.user.sharingcaring.BloodCategories.Opositive;
import com.example.user.sharingcaring.ClickPostActivityForDelete.OpositiveDelete;
import com.example.user.sharingcaring.ClickPostActivityForDelete.TutorDeletePost;
import com.example.user.sharingcaring.FragmentActivities.WelcomeActivity;
import com.example.user.sharingcaring.PostActivities.TutorPostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tutor extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    DatabaseReference postRef;
    RecyclerView tutorPostList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        postRef=FirebaseDatabase.getInstance().getReference().child("TutorPosts");

        toolbar=findViewById(R.id.tutor_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tutor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tutorPostList=findViewById(R.id.tutor_post_recyclerView);
        tutorPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        tutorPostList.setLayoutManager(linearLayoutManager);

        floatingActionButton=findViewById(R.id.openTutor_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Tutor.this,TutorPostActivity.class);
                startActivity(intent);
            }
        });

        displayAlluserPost();
    }

    private void displayAlluserPost() {

        FirebaseRecyclerAdapter<TutorPostModel,TutorPostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<TutorPostModel, TutorPostViewHolder>
                        (TutorPostModel.class,R.layout.tutor_post_layout,TutorPostViewHolder.class,postRef) {
            @Override
            protected void populateViewHolder(TutorPostViewHolder viewHolder, final TutorPostModel model, int position) {

                final String postKey=getRef(position).getKey();

                viewHolder.setAboutTuition(model.getAboutTuition());
                viewHolder.setWhichClass(model.getWhichClass());
                viewHolder.setSalaryMonthly(model.getSalaryMonthly());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setFullname(model.getFullname());
                viewHolder.setProfileimage(model.getProfileimage());
                viewHolder.setPhoneNumber(model.getPhoneNumber());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent=new Intent(Tutor.this,TutorDeletePost.class);
                        newIntent.putExtra("PostKey",postKey);
                        startActivity(newIntent);
                    }
                });

            }
        };

        tutorPostList.setAdapter(firebaseRecyclerAdapter);

    }



    public static class TutorPostViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TutorPostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setFullname(String fullname) {
            TextView userName = mView.findViewById(R.id.tutor_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage) {
            CircleImageView userImage = mView.findViewById(R.id.tutor_post_image_view);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);

        }

        public void setTime(String time){
            TextView postTime=mView.findViewById(R.id.tutor_post_time);
            postTime.setText(" " + time);
        }
        public void setDate(String date){
            TextView postDate=mView.findViewById(R.id.tutor_post_date);
            postDate.setText(date);
        }

        public void setLocation(String location){
            TextView postLocation=mView.findViewById(R.id.tutor_post_location);
            postLocation.setText(location);
        }

        public void setAboutTuition(String aboutTuition){
            TextView about=mView.findViewById(R.id.tuitor_post_about);
            about.setText(aboutTuition);
        }

        public void setWhichClass(String whichClass){
            TextView classOrsub=mView.findViewById(R.id.tuition_class_subject);
            classOrsub.setText(whichClass);
        }
        public void setSalaryMonthly(String salaryMonthly){
            TextView sal=mView.findViewById(R.id.tutor_salary);
            sal.setText(salaryMonthly);
        }
        public void setPhoneNumber(String phoneNumber){
            TextView tutor_number=mView.findViewById(R.id.tutor_phoneNumber);
            tutor_number.setText(phoneNumber);
        }

    }


   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent=new Intent(Tutor.this,WelcomeActivity.class);
        startActivity(backIntent);
    }*/
}
