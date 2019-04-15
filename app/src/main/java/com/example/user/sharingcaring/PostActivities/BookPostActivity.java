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
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sharingcaring.MainActivity;
import com.example.user.sharingcaring.MainPostsActivities.Book;
import com.example.user.sharingcaring.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class BookPostActivity extends AppCompatActivity {

    ImageView bookImage;
    EditText bookDescription,bookLocation;
    TextView free;

    private Uri imageUri;
    Button postButton;
    final static int galleryPick=1;

    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef,postRef;
    private StorageReference storageReference;
    private String saveCurrentDate, saveCurrentTime,postRandomName,downloadUrl,currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_post);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();

        storageReference=FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        loadingBar=new ProgressDialog(this);


        bookImage=findViewById(R.id.book_image_view);
        bookDescription=findViewById(R.id.book_description);
        bookLocation=findViewById(R.id.book_location);

        postButton=findViewById(R.id.post_button);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePost();
            }
        });


        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void validatePost() {
        String description=bookDescription.getText().toString();
        if (imageUri==null){
            Toast.makeText(BookPostActivity.this,"Please, select an Image",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(description)){
            Toast.makeText(BookPostActivity.this,"Please, write some description",Toast.LENGTH_LONG).show();
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

        StorageReference filepath=storageReference.child("post image").child(imageUri.getLastPathSegment()+postRandomName+".jpg");

        filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){

                    downloadUrl=task.getResult().getDownloadUrl().toString();
                    Toast.makeText(BookPostActivity.this,"photo Updated Successfully",Toast.LENGTH_LONG).show();

                    savingPostInfoToDatabase();
                }else {
                    String message=task.getException().toString();
                    Toast.makeText(BookPostActivity.this,"Error"+message,Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void savingPostInfoToDatabase() {


        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String book_post_description=bookDescription.getText().toString();
                    String location=bookLocation.getText().toString();

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

                    postRef.child(currentUserId + postRandomName).updateChildren(postMap).addOnCompleteListener
                            (new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    //sendUserToMain();
                                    sendUserToBook();
                                    Toast.makeText(BookPostActivity.this, "New Post is updated successfully.", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }else {
                                    Toast.makeText(BookPostActivity.this, "Error Occured while updating your post.", Toast.LENGTH_SHORT).show();
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

    private void sendUserToMain() {
        Intent mainIntent = new Intent(BookPostActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void openGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==galleryPick && resultCode==RESULT_OK && data!=null){

            //Get the image from gallery, that is picked up ny User
            imageUri=data.getData();
            bookImage.setImageURI(imageUri);
        }
    }

    public void sendUserToBook(){
        Intent intent=new Intent(BookPostActivity.this,Book.class);
        startActivity(intent);
    }


}
