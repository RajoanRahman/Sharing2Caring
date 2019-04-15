package com.example.user.sharingcaring.PostActivities;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.sharingcaring.BookCategoriesActivity.MarketingEconomicsActivity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.user.sharingcaring.PostActivities.BookPostActivity.galleryPick;

public class MarketingEcoPostAtivity extends AppCompatActivity {

    ImageView markt_bookImage;
    EditText markt_bookDescription,markt_bookLocation,markt_phone_Number;
    Button post_button;

    private Uri imageUri;
    final static int galleryPick=1;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef,postRef;
    private StorageReference storageReference;
    private String saveCurrentDate, saveCurrentTime,postRandomName,downloadUrl,currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_eco_post_ativity);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();

        storageReference=FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRef = FirebaseDatabase.getInstance().getReference().child("MarketingBookPosts");

        loadingBar=new ProgressDialog(this);

        markt_bookImage=findViewById(R.id.markting_book_image_view);
        markt_bookDescription=findViewById(R.id.markting_book_description);
        markt_bookLocation=findViewById(R.id.markting_book_location);
        markt_phone_Number=findViewById(R.id.markting_book_phoneNumber);

        post_button=findViewById(R.id.markting_post_button);

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePost();
            }
        });

        markt_bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void openGallery() {

        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);
    }


    private void validatePost() {

        String description=markt_bookDescription.getText().toString();
        String phone_number=markt_phone_Number.getText().toString();

        if (imageUri==null){
            Toast.makeText(MarketingEcoPostAtivity.this,"Please, select an Image",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(description)){
            Toast.makeText(MarketingEcoPostAtivity.this,"Please, write some description",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(phone_number)){
            Toast.makeText(MarketingEcoPostAtivity.this,"Please, write your Phone Number",Toast.LENGTH_LONG).show();
        }else {

            loadingBar.setTitle("Add New Post");
            loadingBar.setMessage("Please wait, while we are updating your new post...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            storeImageTofirebase();
        }

    }

    private void storeImageTofirebase() {

        Calendar calFordate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate=currentDate.format(calFordate.getTime());

        Calendar calForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm");
        saveCurrentTime=currentTime.format(calFordate.getTime());

        postRandomName=saveCurrentDate+saveCurrentTime;

        StorageReference filepath=storageReference.child("Markting book post image").child(imageUri.getLastPathSegment()+postRandomName+".jpg");

        filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){

                    downloadUrl=task.getResult().getDownloadUrl().toString();
                    Toast.makeText(MarketingEcoPostAtivity.this,"photo Updated Successfully",Toast.LENGTH_LONG).show();

                    savingPostInfoToDatabase();
                }else {
                    String message=task.getException().toString();
                    Toast.makeText(MarketingEcoPostAtivity.this,"Error"+message,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void savingPostInfoToDatabase() {


        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String book_post_description=markt_bookDescription.getText().toString();
                    String location=markt_bookLocation.getText().toString();
                    String phone_Number=markt_phone_Number.getText().toString();

                    String userName=  dataSnapshot.child("User Name").getValue().toString();
                    String userProfile=  dataSnapshot.child("profile image").getValue().toString();

                    HashMap postMap=new HashMap();
                    postMap.put("uid",currentUserId);
                    postMap.put("date", saveCurrentDate);
                    postMap.put("time", saveCurrentTime);
                    postMap.put("description", book_post_description);
                    postMap.put("location", location);
                    postMap.put("postimage", downloadUrl);
                    postMap.put("profileimage", userProfile);
                    postMap.put("fullname", userName);
                    postMap.put("phoneNumber", phone_Number);

                    postRef.child(currentUserId + postRandomName).updateChildren(postMap).addOnCompleteListener
                            (new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                        //sendUserToMain();
                                        sendUserToMarketingEcoActivity();
                                        Toast.makeText(MarketingEcoPostAtivity.this, "New Post is updated successfully.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }else {
                                        Toast.makeText(MarketingEcoPostAtivity.this, "Error Occured while updating your post.", Toast.LENGTH_SHORT).show();
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

    private void sendUserToMarketingEcoActivity() {
        Intent newIntent=new Intent(MarketingEcoPostAtivity.this,MarketingEconomicsActivity.class);
        startActivity(newIntent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==galleryPick && resultCode==RESULT_OK && data!=null){

            //Get the image from gallery, that is picked up ny User
            imageUri=data.getData();
            markt_bookImage.setImageURI(imageUri);
        }
    }


}
