package com.example.user.sharingcaring.FragmentActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sharingcaring.MainActivity;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    EditText nsuNameOrClub,nsuAddress,cellNo;
    Button saveButton,callButton;
    CircleImageView circleImageView;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private StorageReference userProfileRef;
    String currentUserId;
    private ProgressDialog loadingBar;
    final static int galleryPick=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        userRef=FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        userProfileRef=FirebaseStorage.getInstance().getReference().child("Profile Images");

        loadingBar=new ProgressDialog(this);


        nsuNameOrClub=findViewById(R.id.nsuName_clubName);
        nsuAddress=findViewById(R.id.nsuId);
        cellNo=findViewById(R.id.phone_number);

        circleImageView=findViewById(R.id.profileImage);
        saveButton=findViewById(R.id.saveInfoButton);
        callButton=findViewById(R.id.callButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,galleryPick);
            }
        });


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    if (dataSnapshot.hasChild("profile image")){

                        String image=dataSnapshot.child("profile image").getValue().toString();
                        //String userName=dataSnapshot.child("User Name").getValue().toString();
                        //String userAddress=dataSnapshot.child("Address").getValue().toString();
                        //String number=dataSnapshot.child("Phone Number").getValue().toString();

                        Picasso.get().load(image).placeholder(R.drawable.userprofile).into(circleImageView);

                        //nsuNameOrClub.setText(userName);
                        //nsuAddress.setText(userAddress);
                        //cellNo.setText(number);

                    }else {
                        Toast.makeText(UserProfile.this,"Profile image does not exist",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void saveUserInfo() {

        String nsuName=nsuNameOrClub.getText().toString();
        String address=nsuAddress.getText().toString();
        String phoneNumber=cellNo.getText().toString();

        if(TextUtils.isEmpty(nsuName)){
            Toast.makeText(UserProfile.this,"Please write your user name..",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(address)){
            Toast.makeText(UserProfile.this,"Please write your address..",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(UserProfile.this,"Please write your phone number..",Toast.LENGTH_SHORT).show();
        }else {

            loadingBar.setTitle("Saving Info");
            loadingBar.setMessage("Wait, Your information is saving...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap=new HashMap();
            userMap.put("User Name", nsuName);
            userMap.put("Address",address);
            userMap.put("Phone Number",phoneNumber);
            userRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        //sendUserMainActivity();
                        sendUsertoWelcome();
                        Toast.makeText(UserProfile.this,"Saving Info Successfully",Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }else {
                        String message=task.getException().toString();
                        Toast.makeText(UserProfile.this,"Error occurred"+message,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void sendUsertoWelcome() {
        Intent welcomeIntent=new Intent(UserProfile.this,WelcomeActivity.class);
        startActivity(welcomeIntent);
    }

    private void sendUserMainActivity() {
        Intent mainIntent=new Intent(UserProfile.this,MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==galleryPick && resultCode==RESULT_OK && data!=null){
            Uri imageUri=data.getData();

            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode==RESULT_OK){

                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Wait, while setting your profile image");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();

                //TODO: Put the selected images file on Firebase storage
                final StorageReference filePath=userProfileRef.child(currentUserId+".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(UserProfile.this,"Profile Image stored successfully",Toast.LENGTH_LONG).show();

                            //Get the link of firebase storage, where all the photos will be stored
                            final String downloadUrl=task.getResult().getDownloadUrl().toString();
                            userRef.child("profile image").setValue(downloadUrl).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                Intent userIntent=new Intent(UserProfile.this,UserProfile.class);
                                                startActivity(userIntent);

                                                Toast.makeText(UserProfile.this,"Profile Image stored to database",Toast.LENGTH_LONG).show();
                                                loadingBar.dismiss();

                                            }else {
                                                String error=task.getException().toString();
                                                Toast.makeText(UserProfile.this,"Error"+error,Toast.LENGTH_LONG).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });
                        }
                    }
                });


            }

        }

        else {
            Toast.makeText(UserProfile.this,"Image can not be croped,Try againa",Toast.LENGTH_LONG).show();
            loadingBar.dismiss();
        }
    }


}
