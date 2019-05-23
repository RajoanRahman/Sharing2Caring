package com.example.user.sharingcaring;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sharingcaring.FragmentActivities.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class LogInActivity extends AppCompatActivity {

    Button login;
    TextView register;
    EditText login_emailText,login_passwordText;
    ImageView logoImage;
    Animation anim1,anim2,anim3;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        anim1=AnimationUtils.loadAnimation(this,R.anim.animforbuttontwo);
        anim2=AnimationUtils.loadAnimation(this,R.anim.animforbuttontwo);
        anim3=AnimationUtils.loadAnimation(this,R.anim.animforbutton);

        mAuth=FirebaseAuth.getInstance();

        loadingBar=new ProgressDialog(this);

        login=findViewById(R.id.logIn_button);
        register=findViewById(R.id.register_text);
        login_emailText=findViewById(R.id.logIn_email);
        login_passwordText=findViewById(R.id.logIn_password);
        logoImage=findViewById(R.id.nsu_logo);

        logoImage.startAnimation(anim3);
        login_emailText.startAnimation(anim1);
        login_passwordText.startAnimation(anim1);
        register.startAnimation(anim1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowUserToLogIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegister();
            }
        });
    }

    private void allowUserToLogIn() {

        String email=login_emailText.getText().toString();
        String pass=login_passwordText.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(LogInActivity.this,"Please write your email..",Toast.LENGTH_SHORT).show();
            if(!email.contains("northsouth.edu")){
                Toast.makeText(LogInActivity.this,"Please write your NSU email..",Toast.LENGTH_SHORT).show();
            }
        }else if (TextUtils.isEmpty(pass)){
            Toast.makeText(LogInActivity.this,"Please write your password..",Toast.LENGTH_SHORT).show();
        }else {

            loadingBar.setTitle("Log In");
            loadingBar.setMessage("Wait, while you are allowing to log in in your account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()){

                        gotoMain();

                        Toast.makeText(LogInActivity.this,"You are logged in successfully",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }else {
                        String message=task.getException().toString();
                        Toast.makeText(LogInActivity.this,"Error Occurred"+message,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void gotoRegister() {

        Intent registerActivity=new Intent(LogInActivity.this,RegisterActvity.class);
        startActivity(registerActivity);
        //finish();
    }

    private void gotoMain() {
        Intent mainIntent=new Intent(LogInActivity.this,WelcomeActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser=mAuth.getCurrentUser();

        if (currentUser!=null){
            sendUsertoWelcomeActivity();
        }

    }

    private void sendUsertoWelcomeActivity() {
        Intent welcomeIntent=new Intent(LogInActivity.this,WelcomeActivity.class);
        startActivity(welcomeIntent);
    }


}
