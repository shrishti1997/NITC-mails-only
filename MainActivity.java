package com.example.easylearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.*;
import android.view.*;

public class MainActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignUP;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        btnSignUP = findViewById(R.id.button);
        tvSignIn = findViewById(R.id.textView);
        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                String email = emailId.getText().toString();
                String psw = password.getText().toString();
                    if (email.isEmpty()) {
                        emailId.setError("Please enter email Id");
                        emailId.requestFocus();
                    } else if (psw.isEmpty()) {
                        password.setError("Please enter your password");
                        password.requestFocus();
                    } else if (email.isEmpty() && psw.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    } else if (!(email.isEmpty() && psw.isEmpty())) {
                        //creating user name and password
                        String pat = "^[a-z].*?[(@nitc.ac.in)]$";
                        String psw_pat = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                        boolean result = email.matches(pat);
                        boolean res_psw = psw.matches(psw_pat);
                        if (result==false) {
                            emailId.setError("Only NITC IDs");
                            emailId.requestFocus();
                        }
                        else if(res_psw==false){
                            password.setError("Password must contain 8 letters, one upper case, one lower case, one special character and one number");
                            password.requestFocus();
                        }
                        else {
                            mFirebaseAuth.createUserWithEmailAndPassword(email, psw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Signup unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intToHome = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intToHome);
                                    }
                                }
                            });
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });

    }
}
