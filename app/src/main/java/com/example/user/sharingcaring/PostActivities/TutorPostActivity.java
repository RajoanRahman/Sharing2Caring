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

import com.example.user.sharingcaring.MainPostsActivities.Tutor;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class TutorPostActivity extends AppCompatActivity {

    EditText aboutTuition,subject_class,location, salary,phnNumber;
    Button tuition_post;

    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef,postRef;
    private StorageReference storageReference;
    private String saveCurrentDate, saveCurrentTime,postRandomName,downloadUrl,currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_post);

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();

        storageReference=FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRef = FirebaseDatabase.getInstance().getReference().child("TutorPosts");

        loadingBar=new ProgressDialog(this);

        aboutTuition=findViewById(R.id.about_tuition);
        subject_class=findViewById(R.id.subjectclass);
        location=findViewById(R.id.tutor_location);
        salary=findViewById(R.id.tutor_salary);
        phnNumber=findViewById(R.id.tutor_phoneNumber);

        tuition_post=findViewById(R.id.post_button);

        tuition_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    validatePostData();
            }
        });
    }

    private void validatePostData() {
        String about=aboutTuition.getText().toString();
        String tuitionSubject=subject_class.getText().toString();
        String tuitionLocation=location.getText().toString();
        String salary_tuition=salary.getText().toString();
        String phn_Number=phnNumber.getText().toString();

        if (TextUtils.isEmpty(about)){
            Toast.makeText(TutorPostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(tuitionSubject)){
            Toast.makeText(TutorPostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(tuitionLocation)){
            Toast.makeText(TutorPostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(salary_tuition)){
            Toast.makeText(TutorPostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(phn_Number)){
            Toast.makeText(TutorPostActivity.this,"Please, fill up the blank segment",Toast.LENGTH_LONG).show();
        }else {
            loadingBar.setTitle("Add New Post");
            loadingBar.setMessage("Please wait, while we are updating your new post...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            savingPostToDatabase();
        }
    }

    private void savingPostToDatabase() {
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
                    String tuitionAbout=aboutTuition.getText().toString();
                    String which_class=subject_class.getText().toString();
                    String monthly_salary=salary.getText().toString();
                    String location_of_tuition=location.getText().toString();
                    String phone_Number=phnNumber.getText().toString();

                    String userName=  dataSnapshot.child("User Name").getValue().toString();
                    String userProfile=  dataSnapshot.child("profile image").getValue().toString();


                    HashMap postMap=new HashMap();
                    postMap.put("uid",currentUserId);
                    postMap.put("date", saveCurrentDate);
                    postMap.put("time", saveCurrentTime);
                    postMap.put("aboutTuition", tuitionAbout);
                    postMap.put("location", location_of_tuition);
                    postMap.put("salaryMonthly", monthly_salary);
                    //postMap.put("profileimage", userProfile);
                    postMap.put("whichClass", which_class);
                    postMap.put("profileimage", userProfile);
                    postMap.put("fullname", userName);
                    postMap.put("phoneNumber", phone_Number);

                    postRef.child(currentUserId + postRandomName).updateChildren(postMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                //sendUserToMain();
                                sendUserToTutor();
                                Toast.makeText(TutorPostActivity.this, "New Post is updated successfully.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }else {
                                Toast.makeText(TutorPostActivity.this, "Error Occured while updating your post.", Toast.LENGTH_SHORT).show();
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

    private void sendUserToTutor() {
        Intent tutorIntent=new Intent(TutorPostActivity.this,Tutor.class);
        startActivity(tutorIntent);
    }
}
