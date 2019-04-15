package com.example.user.sharingcaring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView userName,userLocation,userPhoneNumber;
    CircleImageView userImageView;
    DatabaseReference userRef;
    FirebaseAuth mAuth;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        userRef=FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        userName=findViewById(R.id.my_name_text);
        userLocation=findViewById(R.id.my_location_text);
        userPhoneNumber=findViewById(R.id.my_number_text);
        userImageView=findViewById(R.id.my_image_view);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String myImage=dataSnapshot.child("profile image").getValue().toString();
                    String myName=dataSnapshot.child("User Name").getValue().toString();
                    String myAddress=dataSnapshot.child("Address").getValue().toString();
                    String myPhoneNumber=dataSnapshot.child("Phone Number").getValue().toString();

                    Picasso.get().load(myImage).into(userImageView);


                    userName.setText("@" + myName);
                    userLocation.setText("Phone: " + myAddress);
                    userPhoneNumber.setText("Address: " + myPhoneNumber);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
