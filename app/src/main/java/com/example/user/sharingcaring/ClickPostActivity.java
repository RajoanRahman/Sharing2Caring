package com.example.user.sharingcaring;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sharingcaring.BookCategoriesActivity.ElectricalComputerActivity;
import com.example.user.sharingcaring.MainPostsActivities.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ClickPostActivity extends AppCompatActivity {

    ImageView editPostImage;
    TextView editDescText,editLocationText,editPhnNumberText;
    Button editPostButton;
    Button deletePostButton;
    FirebaseAuth mAuth;
    DatabaseReference clickPostRef;
    String postKey,currentUserID,databaseUSerID,desc_text,location_text,phn_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();

        postKey=getIntent().getExtras().get("PostKey").toString();
        clickPostRef=FirebaseDatabase.getInstance().getReference().child("ECEBookPosts").child(postKey);

        editDescText=findViewById(R.id.edit_desc_Text);
        editLocationText=findViewById(R.id.edit_location_Text);
        editPhnNumberText=findViewById(R.id.edit_phoneNumber_Text);
        editPostImage=findViewById(R.id.edit_book_image_view);
        editPostButton=findViewById(R.id.edit_post_button);
        deletePostButton=findViewById(R.id.delete_post_button);

        deletePostButton.setVisibility(View.INVISIBLE);
        editPostButton.setVisibility(View.INVISIBLE);


        clickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    databaseUSerID=dataSnapshot.child("uid").getValue().toString();

                    desc_text=dataSnapshot.child("description").getValue().toString();
                    editDescText.setText(desc_text);

                    location_text=dataSnapshot.child("location").getValue().toString();
                    editLocationText.setText(location_text);

                    phn_text=dataSnapshot.child("phoneNumber").getValue().toString();
                    editPhnNumberText.setText(phn_text);

                    String image=dataSnapshot.child("postimage").getValue().toString();
                    Picasso.get().load(image).into(editPostImage);

                    if (currentUserID.equals(databaseUSerID)){

                        deletePostButton.setVisibility(View.VISIBLE);
                        //editPostButton.setVisibility(View.VISIBLE);
                    }
                    editPostButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editExistingPost(desc_text);
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        deletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCurrentPost();
            }
        });
    }

    private void editExistingPost(String desc_text) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(ClickPostActivity.this);
        builder.setTitle("Edit Post");

        final EditText inputField=new EditText(ClickPostActivity.this);
        inputField.setText(desc_text);
        builder.setView(inputField);

        builder.setPositiveButton("Update Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickPostRef.child("description").setValue(inputField.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });


        Dialog dialog=builder.create();
        dialog.show();

    }


    private void deleteCurrentPost() {
        clickPostRef.removeValue();
        bookActivity();

        Toast.makeText(this,"Post has been deleted",Toast.LENGTH_SHORT).show();
    }

    private void bookActivity() {
        Intent bookIntent=new Intent(ClickPostActivity.this,ElectricalComputerActivity.class);
        //bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(bookIntent);
        finish();
    }
}
