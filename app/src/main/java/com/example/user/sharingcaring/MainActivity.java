package com.example.user.sharingcaring;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.sharingcaring.FragmentActivities.ClubsFragment;
import com.example.user.sharingcaring.FragmentActivities.BooksFragment;
import com.example.user.sharingcaring.FragmentActivities.TutorFragment;
import com.example.user.sharingcaring.FragmentActivities.BloodFragment;
import com.example.user.sharingcaring.FragmentActivities.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

        BottomNavigationView bottomNavigationView;
        Toolbar myToolbar;
        FirebaseUser currentUser;
        FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            mAuth=FirebaseAuth.getInstance();

            myToolbar=findViewById(R.id.toolbarId);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setTitle("Home");

            bottomNavigationView = findViewById(R.id.bottom_nav_bar);

            final BooksFragment booksFragment = new BooksFragment();
            final BloodFragment bloodFragment = new BloodFragment();
            final ClubsFragment clubsFragment = new ClubsFragment();
            final TutorFragment tutorFragment = new TutorFragment();


            setFragment(booksFragment);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.book:
                            setFragment(booksFragment);
                            return true;
                        case R.id.blood:
                            setFragment(bloodFragment);
                            return true;
                        case R.id.tutor:
                            setFragment(tutorFragment);
                            return true;
                        case R.id.clubs:
                            setFragment(clubsFragment);
                            return true;
                        default:
                            return false;
                    }

                }
            });

        }

        private void setFragment(Fragment fragment){
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

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
            }

            return super.onOptionsItemSelected(item);
        }

    private void sendUserToLogIn() {
        Intent logInIntent=new Intent(MainActivity.this,LogInActivity.class);
        logInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInIntent);
    }

    private void gotoUserProfile() {
            Intent userIntent=new Intent(MainActivity.this,UserProfile.class);
            startActivity(userIntent);

        }
    }