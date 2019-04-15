package com.example.user.sharingcaring.PostActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sharingcaring.MainPostsActivities.Blood;
import com.example.user.sharingcaring.MainPostsActivities.RideShare;
import com.example.user.sharingcaring.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RideSharePostActivity extends AppCompatActivity {

    EditText writeAboutUrRide,whereToGo,rideTime;
    Button ridePostButton;
    private String saveCurrentDate, saveCurrentTime,postRandomName,downloadUrl,currentUserId;

    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef,postRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_share_post);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();

       // storageReference=FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRef = FirebaseDatabase.getInstance().getReference().child("RideSharePosts");

        loadingBar=new ProgressDialog(this);

        writeAboutUrRide=findViewById(R.id.ride_about);
        whereToGo=findViewById(R.id.ride_location);
        rideTime=findViewById(R.id.ride_time);
        ridePostButton=findViewById(R.id.ride_post_button);

        ridePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePost();
            }
        });

    }

    private void validatePost() {

        String ride_desc=writeAboutUrRide.getText().toString();
        String ride_location=whereToGo.getText().toString();
        String ride_time=rideTime.getText().toString();


        if (TextUtils.isEmpty(ride_desc)){
            Toast.makeText(RideSharePostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(ride_location)){
            Toast.makeText(RideSharePostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(ride_time)){
            Toast.makeText(RideSharePostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else {
            loadingBar.setTitle("Add New Post");
            loadingBar.setMessage("Please wait, while we are updating your new post...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            savingPostIntoDatabase();
        }


    }

    private void savingPostIntoDatabase() {

        Calendar calFordate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate=currentDate.format(calFordate.getTime());

        Calendar calForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm");
        saveCurrentTime=currentTime.format(calFordate.getTime());

        postRandomName=saveCurrentDate+saveCurrentTime;

        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String aboutRide=writeAboutUrRide.getText().toString();
                    String locationRide=whereToGo.getText().toString();
                    String timeofRide=rideTime.getText().toString();

                    String userName=  dataSnapshot.child("User Name").getValue().toString();
                    String userProfile=  dataSnapshot.child("profile image").getValue().toString();

                    HashMap postMap=new HashMap();
                    postMap.put("uid",currentUserId);
                    postMap.put("date", saveCurrentDate);
                    postMap.put("time", saveCurrentTime);
                    postMap.put("aboutRide", aboutRide);
                    postMap.put("location", locationRide);
                    postMap.put("rideTime",timeofRide );
                    //postMap.put("profileimage", userProfile);

                    postMap.put("profileimage", userProfile);
                    postMap.put("fullname", userName);

                    postRef.child(currentUserId + postRandomName).updateChildren(postMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                //sendUserToMain();
                                sendUserToRide();
                                Toast.makeText(RideSharePostActivity.this, "New Post is updated successfully.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }else {
                                Toast.makeText(RideSharePostActivity.this, "Error Occured while updating your post.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void sendUserToRide() {
        Intent intent=new Intent(RideSharePostActivity.this,RideShare.class);
        startActivity(intent);
    }
}
