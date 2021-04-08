package com.example.chattingwhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chattingwhatsapp.databinding.ActivityChatPageBinding;

public class ChatPage extends AppCompatActivity {

    ActivityChatPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}