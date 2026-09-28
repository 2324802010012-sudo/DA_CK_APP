package com.example.da_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {

            // Không bắt buộc đăng nhập nữa
            // Đi thẳng vào trang chủ
            Intent intent = new Intent(
                    WelcomeActivity.this,
                    MainActivity.class
            );

            startActivity(intent);

            finish();
        });
    }
}