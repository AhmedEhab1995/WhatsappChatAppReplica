package com.example.chattingwhatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chattingwhatsapp.databinding.ActivityOtpmessageverificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;

import java.util.concurrent.TimeUnit;

public class activity_otpmessageverification extends AppCompatActivity {

    ActivityOtpmessageverificationBinding OtpActivityOtpmessageverificationBinding;
    TextView phoneNumberMessage;
    FirebaseAuth auth;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OtpActivityOtpmessageverificationBinding = ActivityOtpmessageverificationBinding.inflate(getLayoutInflater());
        setContentView(OtpActivityOtpmessageverificationBinding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        String phoneNumber = getIntent().getStringExtra("phone number");
        phoneNumberMessage = findViewById(R.id.profileinfomessage);
        phoneNumberMessage.setText("Verify " + phoneNumber + " is your phone number");


        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity_otpmessageverification.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                    }
                }).build();

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

        OtpActivityOtpmessageverificationBinding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new Intent(activity_otpmessageverification.this, setupProfile.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else{
                            Toast.makeText(activity_otpmessageverification.this, "Wrong Otp", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }
}