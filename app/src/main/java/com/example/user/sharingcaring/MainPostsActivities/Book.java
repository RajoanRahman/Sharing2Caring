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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sharingcaring.Adapters.BookPostAdapter;
import com.example.user.sharingcaring.ClickPostActivity;
import com.example.user.sharingcaring.CommentActivity;
import com.example.user.sharingcaring.FragmentActivities.WelcomeActivity;
import com.example.user.sharingcaring.PostActivities.BookPostActivity;
import com.example.user.sharingcaring.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Book extends AppCompatActivity {

    Toolbar bookBar;
    FloatingActionButton floatingActionButton;
    RecyclerView bookPostList;
    DatabaseReference postRef,likeRef;
    Boolean likeChecker=false;
    String currentUserId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        bookBar=findViewById(R.id.book_app_bar);
        setSupportActionBar(bookBar);
        getSupportActionBar().setTitle("Books");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        postRef=FirebaseDatabase.getInstance().getReference().child("Posts");
        likeRef=FirebaseDatabase.getInstance().getReference().child("Likes");

        bookPostList=findViewById(R.id.book_post_recyclerView);
        bookPostList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        bookPostList.setLayoutManager(linearLayoutManager);

        floatingActionButton=findViewById(R.id.openBook_post);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookPostIntent=new Intent(Book.this,BookPostActivity.class);
                startActivity(bookPostIntent);
            }
        });

        displayAlluserPost();
    }

    private void displayAlluserPost() {

        FirebaseRecyclerAdapter<BookPostAdapter,PostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<BookPostAdapter, PostViewHolder>(BookPostAdapter.class,R.layout.book_post_layout,
                        PostViewHolder.class,postRef) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, BookPostAdapter model, int position) {

                final String postKey=getRef(position).getKey();

                viewHolder.setFullname(model.getFullname());
                viewHolder.setPostimage(model.getPostimage());
                viewHolder.setProfileimage(model.getProfileimage());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTime(model.getTime());
                viewHolder.setDate(model.getDate());
                viewHolder.setLocation(model.getLocation());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent commentIntent=new Intent(Book.this,ClickPostActivity.class);
                        commentIntent.putExtra("PostKey",postKey);
                        startActivity(commentIntent);
                    }
                });

                viewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent clickPostIntent=new Intent(Book.this,CommentActivity.class);
                        clickPostIntent.putExtra("PostKey",postKey);
                        startActivity(clickPostIntent);
                    }
                });

                /*viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeChecker=true;

                        likeRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(postKey).hasChild(currentUserId)){
                                    likeRef.child(postKey).child(currentUserId).removeValue();
                                }else {
                                    likeRef.child(postKey).child(currentUserId).setValue(true);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });*/


            }
        };
      bookPostList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;

        ImageButton likeButton,commentButton;
        TextView textCount;
        int countLike;
        String currentUSerID;
        DatabaseReference likeRef;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

            //likeButton=(ImageButton)mView.findViewById(R.id.like_button);
            commentButton=(ImageButton)mView.findViewById(R.id.comment_button);
            textCount=(TextView)mView.findViewById(R.id.like_count_text);

        }

        public void setFullname(String fullname){
            TextView userName=mView.findViewById(R.id.book_post_user_name);
            userName.setText(fullname);
        }

        public void setProfileimage(String profileimage){
            CircleImageView userImage=mView.findViewById(R.id.book_post_image_view);
            //userImage.setImageResource(profileimage);
            Picasso.get().load(profileimage).into(userImage);
        }
        public void setTime(String time){
            TextView postTime=mView.findViewById(R.id.book_post_time);
            postTime.setText(" " + time);
        }
        public void setDate(String date){
            TextView postDate=mView.findViewById(R.id.book_post_date);
            postDate.setText(date);
        }
        public void setDescription(String description){
            TextView postDesc=mView.findViewById(R.id.book_post_description);
            postDesc.setText(description);
        }
        public void setPostimage(String postimage){
            ImageView post_Image=mView.findViewById(R.id.book_post_image);
            Picasso.get().load(postimage).into(post_Image);
        }
        public void setLocation(String location){
            TextView postLocation=mView.findViewById(R.id.book_post_location);
            postLocation.setText(location);
        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent=new Intent(Book.this,WelcomeActivity.class);
        startActivity(backIntent);
    }*/
}
