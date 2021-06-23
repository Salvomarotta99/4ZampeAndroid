package com.unimib.App4ZampeAndroid.views.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.unimib.App4ZampeAndroid.R;

import org.jetbrains.annotations.NotNull;

public class UserActivity extends AppCompatActivity {
    public static final String TAG = "tag";
    TextView fullName, email, bio, verifyMsg, changeProfile;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button resendCode, resetPassLocal;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;
    //Dialog dialog;

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fullName = findViewById(R.id.nomeProfilo);
        email = findViewById(R.id.mailProfilo);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);
        profileImage = findViewById(R.id.profileImage);
        changeProfile = findViewById(R.id.changeProfile);
        bio = findViewById(R.id.Bio);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        resendCode = findViewById(R.id.resendCode);
        verifyMsg = findViewById(R.id.verifyMsg);

        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        //dialog = new Dialog(UserActivity.this);
        //dialog.setContentView(R.layout.dialog_update_password);
        //missing line
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);




        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        //back button in toolbar
        getSupportActionBar().setTitle("Profilo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!user.isEmailVerified()) {
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });


        }


        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName.setText(documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("email"));
                bio.setText(documentSnapshot.getString("bio"));

            }
        });

        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText resetPassword = new EditText(v.getContext());
                View view = getLayoutInflater().inflate(R.layout.dialog_update_password, null);
                // AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                final EditText passwordEt = (EditText) view.findViewById(R.id.passwordEt);
                final EditText newPasswordEt = (EditText) view.findViewById(R.id.newPasswordEt);
                Button updatePasswordBtn = (Button) view.findViewById(R.id.updatePasswordBtn);

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(UserActivity.this);

                //passwordResetDialog.setTitle("Reset Password ?");
                //passwordResetDialog.setMessage("Enter new Password");
                passwordResetDialog.setView(view);


                AlertDialog dialog = passwordResetDialog.create();
                dialog.show();

                updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPassword = passwordEt.getText().toString().trim();
                        String newPassword = newPasswordEt.getText().toString().trim();
                        if (TextUtils.isEmpty(oldPassword)) {
                            Toast.makeText(UserActivity.this, "Enter your current password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (newPassword.length() < 6) {
                            Toast.makeText(UserActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dialog.dismiss();
                        updatePassword(oldPassword, newPassword);
                    }
                });


               /*passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract email and send the reset link
                        String newPassword = newPasswordEt.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UserActivity.this,"Password reset successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(UserActivity.this,"Password reset failed", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });*/


            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("email", email.getText().toString());
                i.putExtra("fullName", fullName.getText().toString());
                i.putExtra("bio", bio.getText().toString());
                startActivity(i);
            }
        });
    }


    //update password
    private void updatePassword(String oldPassword, String newPassword) {
        //get current user
        FirebaseUser user = fAuth.getCurrentUser();
        //before change password re authentication
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                 user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(UserActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull @NotNull Exception e) {
                         Toast.makeText(UserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(UserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}