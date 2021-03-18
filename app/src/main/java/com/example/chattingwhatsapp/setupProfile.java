package com.example.chattingwhatsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chattingwhatsapp.databinding.ActivitySetupProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class setupProfile extends AppCompatActivity {

    ActivitySetupProfileBinding activitySetupProfileBinding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySetupProfileBinding = ActivitySetupProfileBinding.inflate(getLayoutInflater());
        setContentView(activitySetupProfileBinding.getRoot());
        getSupportActionBar().hide();


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        activitySetupProfileBinding.profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });

        activitySetupProfileBinding.SetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = activitySetupProfileBinding.nameentry.getText().toString();
                if (userName.isEmpty()) {
                    activitySetupProfileBinding.nameentry.setError("The name can not be empty");
                    return;
                }
                if (selectedProfileImage != null) {
                    StorageReference storageReference = storage.getReference().child("profiles").child(auth.getUid());
                    storageReference.putFile(selectedProfileImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        // creating a user to insert in database
                                        String name = activitySetupProfileBinding.nameentry.getText().toString();
                                        String uId = auth.getUid();
                                        String phoneNumber = auth.getCurrentUser().getPhoneNumber();
                                        User user = new User(name, uId, phoneNumber, imageUrl);

                                        database.getReference()
                                                .child("users")
                                                .child(uId)
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(setupProfile.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
                else{
                    // creating a user to insert in database with no image
                    String name = activitySetupProfileBinding.nameentry.getText().toString();
                    String uId = auth.getUid();
                    String phoneNumber = auth.getCurrentUser().getPhoneNumber();
                    User user = new User(name, uId, phoneNumber, "No Image");

                    database.getReference()
                            .child("users")
                            .child(uId)
                            .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(setupProfile.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {
                activitySetupProfileBinding.profileimage.setImageURI(data.getData());
                selectedProfileImage = data.getData();
            }
        }
    }
}