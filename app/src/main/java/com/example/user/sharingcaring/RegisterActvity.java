package com.example.user.sharingcaring;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sharingcaring.FragmentActivities.UserProfile;
import com.example.user.sharingcaring.FragmentActivities.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActvity extends AppCompatActivity {

    Button createAccount;
    EditText regEmail,regPass,regConfirmPass;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter_actvity);

        mAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);

        anim=AnimationUtils.loadAnimation(this,R.anim.animforbutton);

        createAccount=findViewById(R.id.create_account_button);
        regConfirmPass=findViewById(R.id.reg_confirm_password);
        regEmail=findViewById(R.id.reg_email);
        regPass=findViewById(R.id.reg_password);

        createAccount.startAnimation(anim);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {

        String email=regEmail.getText().toString();
        String password=regPass.getText().toString();
        String confirmPass=regConfirmPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActvity.this,"Please write your email..",Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActvity.this,"Please write your password..",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(confirmPass)){
            Toast.makeText(RegisterActvity.this,"Please confirm your password..",Toast.LENGTH_SHORT).show();
        }else if (!password.equals(confirmPass)){
            Toast.makeText(RegisterActvity.this,"Password do  not match",Toast.LENGTH_SHORT).show();
        }else if (!email.contains("@northsouth.edu")){
            Toast.makeText(RegisterActvity.this,"Please write your NSU email..",Toast.LENGTH_SHORT).show();
        }
        else {

            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Wait, while creating new account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sendUserToUserActivity();
                        Toast.makeText(RegisterActvity.this,"You are authenticated successfully",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }else {
                        String message=task.getException().toString();
                        Toast.makeText(RegisterActvity.this,"Error Occurred"+message,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }


                }
            });
        }
    }

    private void sendUserToUserActivity() {

        Intent userIntent=new Intent(RegisterActvity.this,UserProfile.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(userIntent);

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
            Intent welcomeIntent=new Intent(RegisterActvity.this,WelcomeActivity.class);
            startActivity(welcomeIntent);
        }



}
