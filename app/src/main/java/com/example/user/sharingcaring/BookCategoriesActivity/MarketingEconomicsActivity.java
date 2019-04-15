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

import com.example.user.sharingcaring.Adapters.MarktingBookPostModel;
import com.example.user.sharingcaring.ClickPostActivity;
import com.example.user.sharingcaring.ClickPostActivityForDelete.MarketingBookDelete;
import com.example.user.sharingcaring.MainPostsActivities.Book;
import com.example.user.sharingcaring.PostActivities.BookPostActivity;
import com.example.user.sharingcaring.PostActivities.MarketingEcoPostAtivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MarketingEconomicsActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    RecyclerView marketing_bookPostList;
    DatabaseReference postRef,likeRef;
    Boolean likeChecker=false;
    String currentUserId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketin_economics);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        postRef=FirebaseDatabase.getInstance().getReference().child("MarketingBookPosts");

        toolbar=findViewById(R.id.marketing_book_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Marketing Department");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        marketing_bookPostList=findViewById(R.id.marketing_book_post_recyclerView);
        marketing_bookPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        marketing_bookPostList.setLayoutManager(linearLayoutManager);

        floatingActionButton=findViewById(R.id.marketing_openBook_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookPostIntent=new Intent(MarketingEconomicsActivity.this,MarketingEcoPostAtivity.class);
                startActivity(bookPostIntent);
            }
        });

        displayAlluserPost();
    }

    private void displayAlluserPost() {

        FirebaseRecyclerAdapter<MarktingBookPostModel,MarketingPostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<MarktingBookPostModel, MarketingPostViewHolder>
                        (MarktingBookPostModel.class,R.layout.marketing_book_post_layout,MarketingPostViewHolder.class,postRef) {
                    @Override
                    protected void populateViewHolder(MarketingPostViewHolder viewHolder, final MarktingBookPostModel model, int position) {

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
                                /*Intent intent=new Intent(Intent.ACTION_DIAL);
                                String number=model.getPhoneNumber().toString();
                                intent.setData(Uri.parse("tel:"+number));
                                startActivity(intent);*/

                                Intent newIntent=new Intent(MarketingEconomicsActivity.this,MarketingBookDelete.class);
                                newIntent.putExtra("PostKey",postKey);
                                startActivity(newIntent);
                            }
                        });
                    }
                };

        marketing_bookPostList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class MarketingPostViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public MarketingPostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setFullname(String fullname){
            TextView userName=mView.findViewById(R.id.marktng_book_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage){
            CircleImageView userImage=mView.findViewById(R.id.marktbg_post_image_profile);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);
        }
        public void setTime(String time){
            TextView postTime=mView.findViewById(R.id.marktng_book_post_time);
            postTime.setText(" " + time);
        }
        public void setDate(String date){
            TextView postDate=mView.findViewById(R.id.marktng_book_post_date);
            postDate.setText(date);
        }
        public void setDescription(String description){
            TextView postDesc=mView.findViewById(R.id.marktng_book_post_description);
            postDesc.setText(description);
        }
        public void setPostimage(String postimage){
            ImageView post_Image=mView.findViewById(R.id.marktng_book_post_image);
            Picasso.get().load(postimage).into(post_Image);
        }
        public void setLocation(String location){
            TextView postLocation=mView.findViewById(R.id.marktng_book_post_location);
            postLocation.setText(location);
        }
        public void setPhoneNumber(String phoneNumber){
            TextView postNumber=mView.findViewById(R.id.marktng_book_post_number);
            postNumber.setText(phoneNumber);
        }
    }
}
