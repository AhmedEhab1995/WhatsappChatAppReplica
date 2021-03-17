package com.example.chattingwhatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class activity_phone_number_entry extends AppCompatActivity {

    Button send;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_entry);
        getSupportActionBar().hide();

        send = findViewById(R.id.SetupButton);
        phoneNumber = findViewById(R.id.nameentry);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_phone_number_entry.this, activity_otpmessageverification.class);
                intent.putExtra("phone number", phoneNumber.getText().toString());
                startActivity(intent);
            }
        });
    }
}