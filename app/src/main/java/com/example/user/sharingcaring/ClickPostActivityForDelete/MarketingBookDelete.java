package com.example.user.sharingcaring.ClickPostActivityForDelete;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sharingcaring.BookCategoriesActivity.ElectricalComputerActivity;
import com.example.user.sharingcaring.BookCategoriesActivity.MarketingEconomicsActivity;
import com.example.user.sharingcaring.ClickPostActivity;
import com.example.user.sharingcaring.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MarketingBookDelete extends AppCompatActivity {

    ImageView editPostImage;
    TextView editDescText,editLocationText,editPhnNumberText;
    Button callNowButton;
    Button deletePostButton;
    FirebaseAuth mAuth;
    DatabaseReference clickPostRef;
    String postKey,currentUserID,databaseUSerID,desc_text,location_text,phn_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_book_delete);


        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();

        postKey=getIntent().getExtras().get("PostKey").toString();
        clickPostRef=FirebaseDatabase.getInstance().getReference().child("MarketingBookPosts").child(postKey);

        editDescText=findViewById(R.id.edit_mark_desc_Text);
        editLocationText=findViewById(R.id.edit_mark_location_Text);
        editPhnNumberText=findViewById(R.id.edit_mark_phoneNumber_Text);
        editPostImage=findViewById(R.id.edit_mark_book_image_view);
        callNowButton=findViewById(R.id.call_now_button);
        deletePostButton=findViewById(R.id.delete_mark_post_button);

        deletePostButton.setVisibility(View.INVISIBLE);
        //callNowButton.setVisibility(View.INVISIBLE);


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
                        callNowButton.setVisibility(View.INVISIBLE);
                    }
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


        callNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                String number=editPhnNumberText.getText().toString();
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });
    }

    private void deleteCurrentPost() {
        clickPostRef.removeValue();
        bookActivity();

        Toast.makeText(this,"Post has been deleted",Toast.LENGTH_SHORT).show();
    }

    private void bookActivity() {
        Intent bookIntent=new Intent(MarketingBookDelete.this,MarketingEconomicsActivity.class);
        //bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(bookIntent);
        finish();
    }
}
