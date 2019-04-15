package com.example.user.sharingcaring.BookCategoriesActivity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sharingcaring.Adapters.ECEBookPostModel;
import com.example.user.sharingcaring.Adapters.PharmacyBookPostModel;
import com.example.user.sharingcaring.ClickPostActivityForDelete.MarketingBookDelete;
import com.example.user.sharingcaring.ClickPostActivityForDelete.PharmacyBookDelete;
import com.example.user.sharingcaring.PostActivities.MarketingEcoPostAtivity;
import com.example.user.sharingcaring.PostActivities.PharamacyPostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PharmacyActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    RecyclerView pharmacy_bookPostList;
    DatabaseReference postRef,likeRef;
    Boolean likeChecker=false;
    String currentUserId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        postRef=FirebaseDatabase.getInstance().getReference().child("PharmacyBookPosts");

        toolbar=findViewById(R.id.pharmacy_book_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Department of Pharmacy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pharmacy_bookPostList=findViewById(R.id.pharmacy_book_post_recyclerView);
        pharmacy_bookPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        pharmacy_bookPostList.setLayoutManager(linearLayoutManager);

        floatingActionButton=findViewById(R.id.pharmacy_openBook_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookPostIntent=new Intent(PharmacyActivity.this,PharamacyPostActivity.class);
                startActivity(bookPostIntent);
            }
        });
        displayAlluserPost();
    }

    private void displayAlluserPost() {

        FirebaseRecyclerAdapter<PharmacyBookPostModel,PharmacyPostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<PharmacyBookPostModel, PharmacyPostViewHolder>
                        (PharmacyBookPostModel.class,R.layout.pharmacy_book_post_layout,PharmacyPostViewHolder.class,postRef) {
                    @Override
                    protected void populateViewHolder(PharmacyPostViewHolder viewHolder, final PharmacyBookPostModel model, int position) {

                        final String postKey=getRef(position).getKey();
                        viewHolder.setFullname(model.getFullname());
                        viewHolder.setPostimage(model.getPostimage());
                        viewHolder.setProfileimage(model.getProfileimage());
                        viewHolder.setDescription(model.getDescription());
                        viewHolder.setTime(model.getTime());
                        viewHolder.setDate(model.getDate());
                        viewHolder.setLocation(model.getLocation());
                        viewHolder.setPhoneNumber(model.getPhoneNumber());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent newIntent=new Intent(PharmacyActivity.this,PharmacyBookDelete.class);
                                newIntent.putExtra("PostKey",postKey);
                                startActivity(newIntent);
                            }
                        });

                    }
                };

        pharmacy_bookPostList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class PharmacyPostViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public PharmacyPostViewHolder(@NonNull View itemView) {
            super(itemView);

            mView=itemView;
        }

        public void setFullname(String fullname){
            TextView userName=mView.findViewById(R.id.phr_book_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage){
            CircleImageView userImage=mView.findViewById(R.id.phr_post_image_profile);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);
        }
        public void setTime(String time){
            TextView postTime=mView.findViewById(R.id.phr_book_post_time);
            postTime.setText(" " + time);
        }
        public void setDate(String date){
            TextView postDate=mView.findViewById(R.id.phr_book_post_date);
            postDate.setText(date);
        }
        public void setDescription(String description){
            TextView postDesc=mView.findViewById(R.id.phr_book_post_description);
            postDesc.setText(description);
        }
        public void setPostimage(String postimage){
            ImageView post_Image=mView.findViewById(R.id.phr_book_post_image);
            Picasso.get().load(postimage).into(post_Image);
        }
        public void setLocation(String location){
            TextView postLocation=mView.findViewById(R.id.phr_book_post_location);
            postLocation.setText(location);
        }

        public void setPhoneNumber(String phoneNumber){
            TextView postNumber=mView.findViewById(R.id.phr_book_post_number);
            postNumber.setText(phoneNumber);
        }
    }



    }
