package com.arthi.moviebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    EditText e_email, pwd;
    Button forgot, signin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //start profile activity directly
            finish();
            startActivity(new Intent(this,PaymentsActivity.class));
            // PaymentInformation.class needs to be changed for the intent to start on HomePage
        }
        progressDialog=new ProgressDialog(this);
        e_email = findViewById(R.id.ed_signin_email);
        pwd = findViewById(R.id.ed_signin_password);
        forgot = findViewById(R.id.button_signin_forgotpassword);
        signin = findViewById(R.id.button_signin);


        signin.setOnClickListener(this);
    }
    private void userLogin(){
        String email= e_email.getText().toString().trim();
        String password = pwd.getText().toString().trim();
        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return; //stopping the function from executing further

        }

        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
//if validations are ok we will show a progress bar
        progressDialog.setMessage("Confirming Credentials...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //Toast.makeText(login.this, "Log In Successfull !!", Toast.LENGTH_SHORT).show();
                            //finish();
                            //startActivity(new Intent(SignInActivity.this, HomePage.class));
                            checkIfEmailVerified();
                        } else {
                            Toast.makeText(SignInActivity.this, "Log In Failed. Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view==signin){
            userLogin();
        }
        if (view ==forgot){
            finish();
            startActivity(new Intent(this,RegisterActivity.class));
        }
    }
    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want
            Toast.makeText(SignInActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(SignInActivity.this,HomePage.class));

        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.

            Toast.makeText(this, "Email Not verified", Toast.LENGTH_SHORT).show();
            // FirebaseAuth.getInstance().signOut();
            //Intent intent=getIntent();
            //finish();
            //startActivity(intent);

            //restart this activity

        }
    }

}


