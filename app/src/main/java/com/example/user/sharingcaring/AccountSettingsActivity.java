package com.example.user.sharingcaring;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sharingcaring.FragmentActivities.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSettingsActivity extends AppCompatActivity {

    Toolbar toolbar;

    CircleImageView userProfileImage;
    EditText username,userStatus,userCellNo,userAddress;
    Button userInfoUpdateButton;
    DatabaseReference settingUerRef;
    FirebaseAuth mAuth;
    String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        settingUerRef=FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);


        toolbar=findViewById(R.id.accountSettignBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blood");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userProfileImage=findViewById(R.id.user_profile_circle_image);
        username=findViewById(R.id.user_profileName_edit_text);
       // userStatus=findViewById(R.id.user_status_edit_text);
        userCellNo=findViewById(R.id.user_cellNo_edit_text);;
        userAddress=findViewById(R.id.user_address_edit_text);
        userInfoUpdateButton=findViewById(R.id.user_accnt_update_button);

        settingUerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String userProIamge=dataSnapshot.child("profile image").getValue().toString();
                    String userProName=dataSnapshot.child("User Name").getValue().toString();
                    String userProAddress=dataSnapshot.child("Address").getValue().toString();
                    String userProPhoneNumber=dataSnapshot.child("Phone Number").getValue().toString();

                    Picasso.get().load(userProIamge).into(userProfileImage);



                    username.setText(userProName);
                    userCellNo.setText(userProPhoneNumber);
                    userAddress.setText(userProAddress);

                    //userProfileImage.setImageResource(us);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userInfoUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateUserInfo();
            }
        });


    }

    private void validateUserInfo() {
        String profile_user_name=username.getText().toString();
        String profile_user_address=userAddress.getText().toString();
        String profile_user_cellNo=userCellNo.getText().toString();


        if (TextUtils.isEmpty(profile_user_name)){
            Toast.makeText(this,"Please write your User Name",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(profile_user_cellNo)){
            Toast.makeText(this,"Please write your Cell Number",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(profile_user_address)){
            Toast.makeText(this,"Please write your Address",Toast.LENGTH_SHORT).show();
        }
        else {
            updateExistingUserInfo(profile_user_name,profile_user_address,profile_user_cellNo);
        }
    }

    private void updateExistingUserInfo(String profile_user_name, String profile_user_address, String profile_user_cellNo) {
        HashMap userMap=new HashMap();
            userMap.put("User Name",profile_user_name);
            userMap.put("Address",profile_user_address);
            userMap.put("Phone Number",profile_user_cellNo);

            settingUerRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        sendUsertoWelcomeActivity();
                        Toast.makeText(AccountSettingsActivity.this,"Account is updated is successfully",Toast.LENGTH_SHORT).show();
                    }else {
                        String message=task.getException().toString();
                        Toast.makeText(AccountSettingsActivity.this,"Error: " + message,Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void sendUsertoWelcomeActivity() {

        Intent newIntent=new Intent(AccountSettingsActivity.this,WelcomeActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newIntent);
        finish();
    }


}
