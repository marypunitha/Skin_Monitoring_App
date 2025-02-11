package com.example.tms;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Delay for 3 seconds (3000ms)
        new Handler().postDelayed(() -> {
            // Navigate to MainActivity
            Intent intent = new Intent(splash_screen.this, sign_in.class);
            startActivity(intent);
            finish(); // Close SplashActivity
        }, 3000); // 3000 milliseconds = 3 seconds

    }
}