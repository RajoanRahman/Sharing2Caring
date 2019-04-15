package com.example.user.sharingcaring;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sharingcaring.Adapters.Comments_post_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {

    RecyclerView commentRecyclerView;
    ImageButton commentButton;
    EditText commentInputText;
    DatabaseReference userRef,postRef;
    String postKey,current_User_Id;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mAuth=FirebaseAuth.getInstance();
        current_User_Id=mAuth.getCurrentUser().getUid();
        postKey=getIntent().getExtras().get("PostKey").toString();
        userRef=FirebaseDatabase.getInstance().getReference().child("Users");

        postRef=FirebaseDatabase.getInstance().getReference().child("Posts").child(postKey).child("Comments");

        commentRecyclerView=findViewById(R.id.commentList);
        commentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        commentButton=findViewById(R.id.post_comment );
        commentInputText=findViewById(R.id.comment_edit_text);


        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRef.child(current_User_Id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String user_Name=dataSnapshot.child("User Name").getValue().toString();
                            validateComment(user_Name);

                            commentInputText.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Comments_post_model,CommentViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Comments_post_model, CommentViewHolder>
                        (Comments_post_model.class,R.layout.all_comment_layout,CommentViewHolder.class,postRef) {
                    @Override
                    protected void populateViewHolder(CommentViewHolder viewHolder, Comments_post_model model, int position) {

                         viewHolder.setUserName(model.getUserName());
                         viewHolder.setComment(model.getComment());
                    }
                };
        commentRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setUserName(String userName){

            TextView cmmnt_user_name=(TextView)mView.findViewById(R.id.comment_user_name);
            cmmnt_user_name.setText("@"+userName+" ");
        }

        public void setComment(String comment){

            TextView cmmnts_of_user=(TextView)mView.findViewById(R.id.user_comments);
            cmmnts_of_user.setText(comment);
        }
    }


    private void validateComment(String user_name) {
        String commentText=commentInputText.getText().toString();

        if (TextUtils.isEmpty(commentText)){
            Toast.makeText(this,"please write your comment...",Toast.LENGTH_SHORT).show();
        }else {
            HashMap commentMap=new HashMap();

            commentMap.put("uid",current_User_Id);
            commentMap.put("comment",commentText);
            commentMap.put("userName",user_name);

            postRef.child(current_User_Id).updateChildren(commentMap).
                    addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CommentActivity.this,"You have commented sucessfully",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(CommentActivity.this,"Error: " + task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
