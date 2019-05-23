package com.example.user.sharingcaring.ClickPostActivityForDelete;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sharingcaring.MainPostsActivities.MirpurRidePosts;
import com.example.user.sharingcaring.MainPostsActivities.Tutor;
import com.example.user.sharingcaring.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MirpurDelete extends AppCompatActivity {

    TextView delAboutText,delLocationText,delPhnNumberText,delTimeText;
    Button callNowButton;
    Button deletePostButton;
    FirebaseAuth mAuth;
    DatabaseReference clickPostRef;
    String postKey,currentUserID,databaseUSerID,desc_text,location_text,phn_text,time_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirpur_delete);


        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();

        postKey=getIntent().getExtras().get("PostKey").toString();
        clickPostRef=FirebaseDatabase.getInstance().getReference().child("MirpurPosts").child(postKey);


        delAboutText=findViewById(R.id.mirpur_ride_about_del);
        delLocationText=findViewById(R.id.mirpur_location_del);
        delPhnNumberText=findViewById(R.id.mirpur_phn_del);
        delTimeText=findViewById(R.id.mirpur_time_del);

        callNowButton=findViewById(R.id.mirpur_callNow);
        deletePostButton=findViewById(R.id.mirpur_delete);

        deletePostButton.setVisibility(View.INVISIBLE);

        clickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    databaseUSerID=dataSnapshot.child("uid").getValue().toString();

                    desc_text=dataSnapshot.child("aboutRide").getValue().toString();
                    delAboutText.setText(desc_text);

                    location_text=dataSnapshot.child("location").getValue().toString();
                    delLocationText.setText(location_text);

                    phn_text=dataSnapshot.child("phoneNumber").getValue().toString();
                    delPhnNumberText.setText(phn_text);

                    time_text=dataSnapshot.child("rideTime").getValue().toString();
                    delTimeText.setText(time_text);

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
                String number=delPhnNumberText.getText().toString();
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });

    }

    private void deleteCurrentPost() {
        clickPostRef.removeValue();
        postActivity();
        Toast.makeText(this,"Post has been deleted",Toast.LENGTH_SHORT).show();
    }

    private void postActivity() {
        Intent bookIntent=new Intent(MirpurDelete.this,MirpurRidePosts.class);
        //bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(bookIntent);
        finish();
    }
}
