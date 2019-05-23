package com.example.user.sharingcaring.FragmentActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.user.sharingcaring.AccountSettingsActivity;
import com.example.user.sharingcaring.Category.Blood_Category;
import com.example.user.sharingcaring.Category.Department_Book_categorry;
import com.example.user.sharingcaring.ExpandableDepartmentName.Department_Category_List_Activty;
import com.example.user.sharingcaring.ExpnadablePlaceName.WhereToGoActivity;
import com.example.user.sharingcaring.LogInActivity;
import com.example.user.sharingcaring.MainActivity;
import com.example.user.sharingcaring.MainPostsActivities.Blood;
import com.example.user.sharingcaring.MainPostsActivities.Book;
import com.example.user.sharingcaring.MainPostsActivities.Donate;
import com.example.user.sharingcaring.MainPostsActivities.RideShare;
import com.example.user.sharingcaring.MainPostsActivities.Tutor;
import com.example.user.sharingcaring.NsuAllClubs;
import com.example.user.sharingcaring.ProfileActivity;
import com.example.user.sharingcaring.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity{

    CardView books,blood,tutor,clubs,ride,donate;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Animation anim,anim2;
    Toolbar toolbar;

    private DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toolbar=findViewById(R.id.welcome_bar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(" ");

        mAuth=FirebaseAuth.getInstance();


        //Under this "User"  node, every data of user will be saved
        userRef=FirebaseDatabase.getInstance().getReference().child("Users");

        anim=AnimationUtils.loadAnimation(this,R.anim.animforbutton);
        anim2=AnimationUtils.loadAnimation(this,R.anim.animforbuttontwo);

        books=findViewById(R.id.books_cardView);
        blood=findViewById(R.id.blood_card_view);
        tutor=findViewById(R.id.tutor_card_view);
        clubs=findViewById(R.id.club_card_view);
        ride=findViewById(R.id.ride_share);
        donate=findViewById(R.id.donate_share);

        books.startAnimation(anim2);
        tutor.startAnimation(anim2);
        ride.startAnimation(anim2);

        blood.startAnimation(anim);
        clubs.startAnimation(anim);
        donate.startAnimation(anim);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendUserToMain();
                Intent bookIntent=new Intent(WelcomeActivity.this,Department_Category_List_Activty.class);
                startActivity(bookIntent);
            }
        });
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // sendUserToMain();
                Intent bloodIntent=new Intent(WelcomeActivity.this,Blood_Category.class);
                startActivity(bloodIntent);
            }
        });
        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendUserToMain();
                Intent bloodIntent=new Intent(WelcomeActivity.this,Tutor.class);
                startActivity(bloodIntent);
            }
        });
        clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendUserToMain();
                Intent clubIntent=new Intent(WelcomeActivity.this,NsuAllClubs.class);
                startActivity(clubIntent);
            }
        });
        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendUserToMain();
                Intent rideIntent=new Intent(WelcomeActivity.this,WhereToGoActivity.class);
                startActivity(rideIntent);
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendUserToMain();
                Intent rideIntent=new Intent(WelcomeActivity.this,Donate.class);
                startActivity(rideIntent);
            }
        });


    }

    private void sendUserToMain() {
        Intent mainIntent=new Intent(WelcomeActivity.this,MainActivity.class);

        startActivity(mainIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser=mAuth.getCurrentUser();

        if (currentUser==null){
            gotoLoginActivity();
        }else {
            checkUserExistence();
        }
    }

    private void checkUserExistence() {
        final String current_userId=mAuth.getCurrentUser().getUid();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //If current User id is not exist on firebase database,then send user to setUpActivity
                if (!dataSnapshot.hasChild(current_userId)){

                    sendUsertoSetupActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendUsertoSetupActivity() {
        Intent userIntent=new Intent(WelcomeActivity.this,UserProfile.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(userIntent);
        finish();
    }

    private void gotoLoginActivity() {

        Intent loginIntent=new Intent(WelcomeActivity.this,LogInActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if (id==R.id.userProfileId){
            gotoUserProfile();
        }else if (id==R.id.logOutId){
            mAuth.signOut();
            sendUserToLogIn();
        }else if (id==R.id.profile){
            sendUserToProfile();
        }else if(id==R.id.profile_user){
            sendUserToProfileActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendUserToProfileActivity() {
        Intent profileIntent=new Intent(WelcomeActivity.this,ProfileActivity.class);
        startActivity(profileIntent);
    }

    private void sendUserToProfile() {
        Intent proIntent=new Intent(WelcomeActivity.this,AccountSettingsActivity.class);
        startActivity(proIntent);
    }


    private void sendUserToLogIn() {
        Intent logInIntent=new Intent(WelcomeActivity.this,LogInActivity.class);
        logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInIntent);
    }

    private void gotoUserProfile() {
        Intent userIntent=new Intent(WelcomeActivity.this,UserProfile.class);
        startActivity(userIntent);

    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(WelcomeActivity.this);
        builder.setMessage("Are you sure want to exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
               // WelcomeActivity.super.onBackPressed();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();


    }
}
