package com.example.easylearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.*;
import android.view.*;
import android.content.*;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);

        FirebaseAuth.AuthStateListener mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null)//user exists
                {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);

                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = emailId.getText().toString();
                String psw = password.getText().toString();
                String pat = ".*?[(@nitc.ac.in)]$";
                boolean result = email.matches(pat);
                if (result==false) {
                    emailId.setError("Only NITC IDs");
                    emailId.requestFocus();
                }
                else {
                    if (email.isEmpty()) {
                        emailId.setError("Please enter email Id");
                        emailId.requestFocus();
                    } else if (psw.isEmpty()) {
                        password.setError("Please enter your password");
                        password.requestFocus();
                    } else if (email.isEmpty() && psw.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    } else if (!(email.isEmpty() && psw.isEmpty())) {
                        //creating user name and password
                        mFirebaseAuth.signInWithEmailAndPassword(email, psw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            public void setmFirebaseUser(FirebaseUser mFirebaseUser) {
                            }

                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intToHome);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }
}
