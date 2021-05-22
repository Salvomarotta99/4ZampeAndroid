package com.unimib.App4ZampeAndroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.jetbrains.annotations.NotNull;

public class activity_registration extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mPasswordConfirm;
    Button mButton;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFullName=findViewById(R.id.editNameRegistration);
        mEmail=findViewById(R.id.editMailRegistration);
        mPassword=findViewById(R.id.editPswRegistration);
        mPasswordConfirm=findViewById(R.id.editPswConfirmRegistration);

        mButton=findViewById(R.id.registrati_btn);

        fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome=mFullName.getText().toString().trim();
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String passwordConfirm=mPasswordConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(nome)){
                    mFullName.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }

                if(!password.equals(passwordConfirm)){
                    mPasswordConfirm.setError("Password must be confirmed");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be >=6 characters");
                    return;
                }

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity_registration.this, "user created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(activity_registration.this, "error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}